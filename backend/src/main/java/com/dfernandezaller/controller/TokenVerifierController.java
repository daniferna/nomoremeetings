package com.dfernandezaller.controller;

import com.dfernandezaller.authentication.GoogleTokenVerifier;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

import java.util.Map;

@Controller("/verifier/token")
public class TokenVerifierController {

    private GoogleTokenVerifier verifier;

    public TokenVerifierController(GoogleTokenVerifier verifier) {
        this.verifier = verifier;
    }

    @Post(consumes = MediaType.APPLICATION_JSON)
    public HttpResponse<HttpStatus> googleTokenVerifier(@Body Map<String, String> token) {
        verifier.verify(token.get("token"));
        // TODO: 17/01/2023 Crear sesión y guardar el token en la sesión
        return HttpResponse.ok();
    }

}
