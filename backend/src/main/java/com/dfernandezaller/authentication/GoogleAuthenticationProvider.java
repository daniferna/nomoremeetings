package com.dfernandezaller.authentication;

import com.dfernandezaller.authentication.google.AuthorizationCodeFlowFactory;
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
                    var authorizationCodeFlow = authorizationCodeFlowFactory.getAuthorizationCodeFlow();
                    var googleUserData = authorizationCodeFlow.getCredentialDataStore().get(result.email().orElseThrow());

                    if (googleUserData == null) {
                        emitter.next(AuthenticationResponse.failure(AuthenticationFailureReason.USER_NOT_FOUND));
                        return;
                    }

                    emitter.next(AuthenticationResponse.success(result.email().get(), Map.of("accessToken", googleUserData.getAccessToken())));
                } else {
                    emitter.next(AuthenticationResponse.failure(result.failureReason().orElse("Unknown reason")));
                }
            } catch (Exception e) {
                emitter.error(e);
            }
        });

    }

}
