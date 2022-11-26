package com.dfernandezaller.controller;

import com.dfernandezaller.service.CalendarQuickstart;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;

import java.io.IOException;

@Controller
public class PetitionController {

    @Inject
    private CalendarQuickstart calendarQuickstart;


    @Get
    public HttpResponse<HttpStatus> testGet() throws IOException {
        calendarQuickstart.listEvents();
        return HttpResponse.ok();
    }

}
