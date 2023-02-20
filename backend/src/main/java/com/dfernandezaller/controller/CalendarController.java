package com.dfernandezaller.controller;
import com.dfernandezaller.service.GoogleCalendarService;
import com.google.api.services.calendar.model.Event;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import java.util.List;
@Controller("/calendar/")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class CalendarController {
    private final GoogleCalendarService calendarService;
    public CalendarController(GoogleCalendarService calendarService) {
        this.calendarService = calendarService;
    }
    @Get("events/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Event> getCalendarEvents(@PathVariable String token) {
        //return calendarService.getCalendarEvents(token);
        return List.of();
        // TODO: 20/02/2023 Fix this.
    }
}