package com.dfernandezaller.controller;

import com.dfernandezaller.authentication.GoogleTokenVerifier;
import com.dfernandezaller.controller.dto.Credential;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

@Controller("/test")
@Secured(value = SecurityRule.IS_ANONYMOUS)
public class TestController {

    private final GoogleTokenVerifier verifier;

    public TestController(GoogleTokenVerifier verifier) {
        this.verifier = verifier;
    }

    @Post(uri = "/auth", consumes = "application/json")
    public String testGoogleAuth(@Body Credential object) {
        System.out.println(object);
        verifier.verify(object.credential());
        return "Hello World";
    }

}
