package com.dfernandezaller.controller;

import com.dfernandezaller.authentication.GoogleTokenVerifier;
import com.dfernandezaller.authentication.google.AuthorizationCodeFlowFactory;
import com.dfernandezaller.controller.dto.GoogleSignupData;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.TokenResponse;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Controller("/auth")
@Secured(SecurityRule.IS_ANONYMOUS)
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);
    private final AuthorizationCodeFlowFactory authorizationCodeFlowFactory;
    private final GoogleTokenVerifier verifier;

    public AuthController(AuthorizationCodeFlowFactory authorizationCodeFlowFactory, GoogleTokenVerifier verifier) {
        this.authorizationCodeFlowFactory = authorizationCodeFlowFactory;
        this.verifier = verifier;
    }

    @Post(uri = "/login", consumes = "application/json")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public HttpResponse<String> loginGoogle(Authentication authentication) {
        return HttpResponse.ok(authentication.getName() + " - " + authentication.getAttributes().get("accessToken"));
    }

    // TODO [Daniel Fernandez, 06/04/2023] Move logic out of controller into service/filter.
    @Post(uri = "/signup", consumes = "application/json")
    public HttpResponse<String> signupGoogle(@Body GoogleSignupData signupData) throws IOException, GeneralSecurityException {

        var result = verifier.verify(signupData.idToken());
        if (!result.isValid()) {
            LOG.warn("Invalid ID token");
            return HttpResponse.status(HttpStatus.UNAUTHORIZED, "Invalid ID token");
        }
        var email = result.email().orElseThrow();

        AuthorizationCodeFlow authorizationCodeFlow = authorizationCodeFlowFactory.getAuthorizationCodeFlow();

        TokenResponse token = authorizationCodeFlow.newTokenRequest(signupData.codeToken())
                .setRedirectUri("postmessage") // Encontrado en: https://stackoverflow.com/questions/11485271/google-oauth-2-authorization-error-redirect-uri-mismatch
                .execute();

        if (authorizationCodeFlow.getCredentialDataStore().containsKey(email)) {
            LOG.trace("Found credential for email: {}", email);
            return HttpResponse.status(HttpStatus.CONFLICT, "User already exists");
        }

        authorizationCodeFlow.createAndStoreCredential(token, email);
        LOG.trace("Stored credential for email: {}", email);

        return HttpResponse.created(email);
    }

}
