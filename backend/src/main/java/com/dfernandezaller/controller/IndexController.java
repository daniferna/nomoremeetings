package com.dfernandezaller.controller;

import com.dfernandezaller.repository.GoogleCredentialRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

@Controller
@Secured(SecurityRule.IS_ANONYMOUS)
public class IndexController {

    private final GoogleCredentialRepository repository;

    public IndexController(GoogleCredentialRepository repository) {
        this.repository = repository;
    }

    @Get
    public String index() {
        return "Hello World! This is nomoremeetings project's API.";
    }

}
