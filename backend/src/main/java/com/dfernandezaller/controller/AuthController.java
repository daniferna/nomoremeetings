package com.dfernandezaller.controller;

import com.dfernandezaller.authentication.GoogleTokenVerifier;
import com.dfernandezaller.authentication.google.AuthorizationCodeFlowFactory;
import com.dfernandezaller.controller.dto.GoogleLoginData;
import com.dfernandezaller.controller.dto.GoogleSignupData;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.TokenResponse;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Controller("/auth")
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);
    private final AuthorizationCodeFlowFactory authorizationCodeFlowFactory;
    private final GoogleTokenVerifier verifier;

    public AuthController(AuthorizationCodeFlowFactory authorizationCodeFlowFactory, GoogleTokenVerifier verifier) {
        this.authorizationCodeFlowFactory = authorizationCodeFlowFactory;
        this.verifier = verifier;
    }

    // TODO [Daniel Fernandez, 06/04/2023] Move logic out of controller into service/filter.
    @Post(uri = "/login", consumes = "application/json")
    public HttpResponse<String> loginGoogle(@Body GoogleLoginData loginData) throws IOException {

        var resultVerifier = verifier.verify(loginData.idToken());
        if (!resultVerifier.isValid()) {
            LOG.trace("Token {} is not valid", loginData.idToken());
            return HttpResponse.badRequest(resultVerifier.failureReason().orElse("Unknown reason"));
        }

        AuthorizationCodeFlow authorizationCodeFlow = authorizationCodeFlowFactory.getAuthorizationCodeFlow();
        if (authorizationCodeFlow.getCredentialDataStore().containsKey(resultVerifier.email().orElseThrow())) {
            LOG.trace("Found credential for email: {}", resultVerifier.email().get());
            return HttpResponse.ok();
        }

        return HttpResponse.status(HttpStatus.FORBIDDEN, "User not found").body(resultVerifier.email().get());
    }

    @Post(uri = "/signup", consumes = "application/json")
    public HttpResponse<String> signupGoogle(@Body GoogleSignupData signupData) throws IOException {
        AuthorizationCodeFlow authorizationCodeFlow = authorizationCodeFlowFactory.getAuthorizationCodeFlow();

        TokenResponse token = authorizationCodeFlow.newTokenRequest(signupData.codeToken())
                .setRedirectUri("postmessage") // Encontrado en: https://stackoverflow.com/questions/11485271/google-oauth-2-authorization-error-redirect-uri-mismatch
                .execute();

        if (authorizationCodeFlow.getCredentialDataStore().containsKey(signupData.email())) {
            LOG.trace("Found credential for email: {}", signupData.email());
            return HttpResponse.status(HttpStatus.CONFLICT, "User already exists");
        }

        authorizationCodeFlow.createAndStoreCredential(token, signupData.email());
        LOG.trace("Stored credential for email: {}", signupData.email());

        return HttpResponse.created(signupData.email());
    }

}
