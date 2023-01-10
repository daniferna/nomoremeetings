package com.dfernandezaller.controller;

import com.dfernandezaller.service.VerifierService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.PathVariable;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class VerifierController {

    private final VerifierService verifierService;

    public VerifierController(VerifierService verifierService) {
        this.verifierService = verifierService;
    }

    public HttpResponse<HttpStatus> verifyToken(@PathVariable String idToken) throws GeneralSecurityException, IOException {
        return verifierService.verify(idToken) ? HttpResponse.ok() : HttpResponse.badRequest();
    }

}
