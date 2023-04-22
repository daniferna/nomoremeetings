package com.dfernandezaller.controller;

import com.dfernandezaller.repository.GoogleCredentialRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import java.util.Set;

@Controller("/test")
@Secured(SecurityRule.IS_ANONYMOUS)
public class TestController {

    private final GoogleCredentialRepository repository;

    public TestController(GoogleCredentialRepository repository) {
        this.repository = repository;
    }

    @Get(uri = "/test")
    public Set<String> test() {
        return repository.findKey();
    }

}
