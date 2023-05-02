package com.dfernandezaller.controller;

import com.dfernandezaller.controller.dto.GoogleSignupData;
import com.dfernandezaller.controller.dto.UserDTO;
import com.dfernandezaller.service.AuthenticationService;
import com.dfernandezaller.service.UserService;
import com.dfernandezaller.service.imp.GoogleTokenVerifier;
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

@Controller("/auth")
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final GoogleTokenVerifier verifier;

    public AuthController(AuthenticationService authenticationService, UserService userService, GoogleTokenVerifier verifier) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.verifier = verifier;
    }

    @Post(uri = "/login", consumes = "application/json")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public HttpResponse<UserDTO> loginGoogle(Authentication authentication) {
        return userService.getUser(authentication.getName())
                .map(HttpResponse::ok)
                .orElseGet(() -> HttpResponse.status(HttpStatus.UNAUTHORIZED, "User not found"));
    }

    @Post(uri = "/signup", consumes = "application/json")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public HttpResponse<UserDTO> signupGoogle(@Body GoogleSignupData signupData) {
        var verificationResult = verifier.verify(signupData.idToken());
        if (!verificationResult.isValid()) {
            LOG.warn("Invalid ID token");
            return HttpResponse.status(HttpStatus.UNAUTHORIZED, "Invalid ID token");
        }

        return HttpResponse.created(
                authenticationService.signupUser(verificationResult.getPayload(), signupData.codeToken())
        );
    }
}
