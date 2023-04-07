package com.dfernandezaller.controller;

import com.dfernandezaller.repository.GoogleCredentialRepository;
import com.dfernandezaller.repository.entity.GoogleCredential;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/test")
public class TestController {

    private final GoogleCredentialRepository repository;

    public TestController(GoogleCredentialRepository repository) {
        this.repository = repository;
    }

    @Get(uri = "/test")
    public Iterable<GoogleCredential> test() {
        return repository.findAll();
    }

}
