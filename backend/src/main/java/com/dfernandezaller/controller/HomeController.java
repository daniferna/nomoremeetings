package com.dfernandezaller.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;

import java.util.Map;

@Controller
@Secured(SecurityRule.IS_AUTHENTICATED)
public class HomeController {

    @Produces(MediaType.APPLICATION_JSON)
    @Get
    public Map<String, Object> index(Authentication principal) {
        return Map.of("Testing", "this out", "User:", principal.getName());
    }

}