package com.dfernandezaller.authentication.google;

import com.dfernandezaller.service.imp.GoogleTokenVerifier;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationFailureReason;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

import java.util.Map;

@Singleton
public class GoogleAuthenticationProvider implements AuthenticationProvider {

    private final GoogleTokenVerifier verifier;
    private final GoogleAuthorizationCodeFlowFactory googleAuthorizationCodeFlowFactory;

    public GoogleAuthenticationProvider(GoogleTokenVerifier verifier, GoogleAuthorizationCodeFlowFactory googleAuthorizationCodeFlowFactory) {
        this.verifier = verifier;
        this.googleAuthorizationCodeFlowFactory = googleAuthorizationCodeFlowFactory;
    }

    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {

        return Flux.create(emitter -> {
            try {
                var result = verifier.verify((String) authenticationRequest.getSecret());
                if (result.isValid()) {
                    var authorizationCodeFlow = googleAuthorizationCodeFlowFactory.getAuthorizationCodeFlow();
                    var googleUserData = authorizationCodeFlow.getCredentialDataStore().get(result.getPayload().getEmail());

                    if (googleUserData == null) {
                        emitter.next(AuthenticationResponse.failure(AuthenticationFailureReason.USER_NOT_FOUND));
                        return;
                    }

                    emitter.next(AuthenticationResponse.success(result.getPayload().getEmail(),
                            Map.of("accessToken", googleUserData.getAccessToken())));
                } else {
                    emitter.next(AuthenticationResponse.failure(result.getFailureReason()));
                }
            } catch (Exception e) {
                emitter.error(e);
            }
        });

    }

}
