package com.dfernandezaller.authentication.google;

import com.dfernandezaller.service.imp.GoogleTokenVerifier;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationFailureReason;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.Map;

@Singleton
public class GoogleAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOG = LoggerFactory.getLogger(GoogleAuthenticationProvider.class);
    private final GoogleTokenVerifier verifier;
    private final AuthorizationCodeFlowFactory authorizationCodeFlowFactory;

    public GoogleAuthenticationProvider(GoogleTokenVerifier verifier, AuthorizationCodeFlowFactory authorizationCodeFlowFactory) {
        this.verifier = verifier;
        this.authorizationCodeFlowFactory = authorizationCodeFlowFactory;
    }

    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {

        return Flux.create(emitter -> {
            try {
                var result = verifier.verify((String) authenticationRequest.getSecret());
                if (result.isValid()) {
                    var authorizationCodeFlow = authorizationCodeFlowFactory.getGoogleAuthorizationCodeFlow();

                    var userCredentials = authorizationCodeFlow.loadCredential(result.getPayload().getEmail());
                    if (userCredentials == null) {
                        emitter.next(AuthenticationResponse.failure(AuthenticationFailureReason.USER_NOT_FOUND));
                        return;
                    }

                    //Check if token is outdated
                    var accessToken = userCredentials.getAccessToken();
                    if (accessToken == null) {
                        LOG.debug("Access token is null for user {}", result.getPayload().getEmail());
                        var refreshResult = userCredentials.refreshToken();
                        if (!refreshResult) {
                            LOG.warn("Refresh token failed for user {}", result.getPayload().getEmail());
                            emitter.next(AuthenticationResponse.failure(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH));
                            return;
                        }
                    }

                    emitter.next(AuthenticationResponse.success(result.getPayload().getEmail(),
                            Map.of("accessToken", userCredentials.getAccessToken())));
                } else {
                    emitter.next(AuthenticationResponse.failure(result.getFailureReason()));
                }
            } catch (Exception e) {
                emitter.error(e);
            }
        });

    }

}
