package com.dfernandezaller.authentication.google;

import com.dfernandezaller.authentication.google.client.GoogleApiClient;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.oauth2.endpoint.authorization.state.State;
import io.micronaut.security.oauth2.endpoint.token.response.OauthAuthenticationMapper;
import io.micronaut.security.oauth2.endpoint.token.response.TokenResponse;
import java.util.Collections;
import java.util.List;

import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

@Named("google") // (1)
@Singleton
public class GoogleAuthenticationMapper implements OauthAuthenticationMapper {

    private final GoogleApiClient apiClient;

    GoogleAuthenticationMapper(GoogleApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public Publisher<AuthenticationResponse> createAuthenticationResponse(TokenResponse tokenResponse, @Nullable State state) { // (3)
        return Flux.from(apiClient.getUserInfo("token " + tokenResponse.getAccessToken()))
                .map(user -> {
                    List<String> roles = Collections.singletonList("ROLE_GOOGLE");
                    System.out.println("User: " + user.name() + " Roles: " + roles);
                    return AuthenticationResponse.success(user.name(), roles);
                });
    }
}