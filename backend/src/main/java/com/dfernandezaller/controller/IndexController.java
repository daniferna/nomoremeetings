package com.dfernandezaller.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

@Controller
@Secured(SecurityRule.IS_ANONYMOUS)
public class IndexController {

    @Get
    public HttpResponse<String> index() {
        return HttpResponse.ok("Hello World! This is nomoremeetings project's API.");
    }

}
