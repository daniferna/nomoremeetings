package com.dfernandezaller.authentication;

import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Singleton
public class AuthenticationProviderGoogleToken implements AuthenticationProvider {

    @Inject
    GoogleTokenVerifier googleTokenVerifier;

    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        return Flux.create(emitter -> {
            var verificationResult = googleTokenVerifier.verify(String.valueOf(authenticationRequest.getSecret()));
            if (verificationResult.isValid()) {
                emitter.next(AuthenticationResponse.success(verificationResult.name().orElseThrow()));
                emitter.complete();
            } else {
                emitter.error(AuthenticationResponse.exception(
                        verificationResult.failureReason().orElse("Unknown failure during token verification"))
                );
            }
        }, FluxSink.OverflowStrategy.ERROR);
    }
}
