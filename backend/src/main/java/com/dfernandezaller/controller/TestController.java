package com.dfernandezaller.controller;

import com.dfernandezaller.service.imp.CalendarQuickstart;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Controller("/test")
@Secured(SecurityRule.IS_ANONYMOUS)
public class TestController {

    private final CalendarQuickstart calendarQuickstart;

    public TestController(CalendarQuickstart calendarQuickstart) {
        this.calendarQuickstart = calendarQuickstart;
    }

    @Get
    public void getUser() throws GeneralSecurityException, IOException {
        calendarQuickstart.getEvents();
    }

}
