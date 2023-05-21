package com.dfernandezaller.service.imp;

import com.dfernandezaller.authentication.google.GoogleAuthorizationCodeFlowFactory;
import com.dfernandezaller.exceptions.BusinessException;
import com.dfernandezaller.model.Meeting;
import com.dfernandezaller.service.CalendarService;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Events;
import io.micronaut.core.convert.ConversionService;
import jakarta.inject.Singleton;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Singleton
public class GoogleCalendarService implements CalendarService {

    private final ConversionService<?> conversionService;
    private final GoogleAuthorizationCodeFlowFactory googleAuthorizationCodeFlowFactory;

    private static final String APPLICATION_NAME = "nomoremeetings";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    public GoogleCalendarService(ConversionService<?> conversionService, GoogleAuthorizationCodeFlowFactory googleAuthorizationCodeFlowFactory) {
        this.conversionService = conversionService;
        this.googleAuthorizationCodeFlowFactory = googleAuthorizationCodeFlowFactory;
    }

    public List<Meeting> getCalendarMeetings(String calendarId, LocalDate startDate, LocalDate endDate) {
        Events events;
        try {
            Calendar service = getCalendarService();
            events = service.events().list(calendarId)
                    .setTimeMin(new DateTime(startDate.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()))
                    .setTimeMax(new DateTime(endDate.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()))
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
        } catch (IOException e) {
            throw new BusinessException("Error occurred when trying to get calendar events", e);
        }

        return events.getItems().stream()
                .map(googleEvent -> conversionService.convert(googleEvent, Meeting.class))
                .map(Optional::orElseThrow)
                .toList();
    }

    private Calendar getCalendarService() throws IOException {
        final NetHttpTransport httpTransport;
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException e) {
            throw new BusinessException("Error occurred when trying to create a secured HTTP connection with Google", e);
        }
        return new Calendar.Builder(httpTransport, JSON_FACTORY,
                googleAuthorizationCodeFlowFactory.getAuthorizationCodeFlow()
                        .loadCredential("danixe.ferna@gmail.com"))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}