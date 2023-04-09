package com.dfernandezaller.service.imp;

import com.dfernandezaller.authentication.google.GoogleAuthorizationCodeFlowFactory;
import com.dfernandezaller.exceptions.BussinessException;
import com.dfernandezaller.service.AuthenticationService;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.TokenResponse;
import io.micronaut.data.exceptions.DataAccessException;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Singleton
public class GoogleAuthenticationService implements AuthenticationService {

    private final Logger LOG = LoggerFactory.getLogger(GoogleAuthenticationService.class);
    AuthorizationCodeFlow authorizationCodeFlow;

    public GoogleAuthenticationService(GoogleAuthorizationCodeFlowFactory googleAuthorizationCodeFlowFactory) throws IOException {
        this.authorizationCodeFlow = googleAuthorizationCodeFlowFactory.getAuthorizationCodeFlow();
    }

    @Override
    public String signupUser(String email, String token) {
        TokenResponse tokenResponse;
        try {
            tokenResponse = authorizationCodeFlow.newTokenRequest(token)
                    .setRedirectUri("postmessage") // Encontrado en: https://stackoverflow.com/questions/11485271/google-oauth-2-authorization-error-redirect-uri-mismatch
                    .execute();
        } catch (IOException exception) {
            throw new BussinessException("Error while trying to get token response", exception);
        }

        try {
            if (authorizationCodeFlow.getCredentialDataStore().containsKey(email)) {
                LOG.trace("Found credential for email: {}", email);
                throw new BussinessException("User already exists");
            }
            authorizationCodeFlow.createAndStoreCredential(tokenResponse, email);
        } catch (IOException e) {
            throw new DataAccessException(e.getMessage(), e);
        }

        LOG.trace("Stored credential for email: {}", email);
        return email;
    }

}
