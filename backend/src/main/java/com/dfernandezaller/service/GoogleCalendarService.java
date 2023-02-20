package com.dfernandezaller.service;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import io.micronaut.serde.annotation.SerdeImport;
import jakarta.inject.Singleton;
import lombok.SneakyThrows;

import java.util.Date;
import java.util.List;
@Singleton
public class GoogleCalendarService {
    /**
     * Application name.
     */
    private static final String APPLICATION_NAME = "NoMoreMeetings";
    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    @SneakyThrows
    public List<Event> getCalendarEvents(TokenResponse token) {
        GoogleCredentials credentials = GoogleCredentials.newBuilder()
                .setAccessToken(new AccessToken(token.getAccessToken(), null))
                .build();
        var requestInitializer = new HttpCredentialsAdapter(credentials);
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service =
                new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, requestInitializer)
                        .setApplicationName(APPLICATION_NAME)
                        .build();
        // List the next 10 events from the primary calendar.
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
                .setMaxResults(10)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        System.out.println(events.getItems());

        return items;

        /*if (items.isEmpty()) {
            System.out.println("No upcoming events found.");
        } else {
            System.out.println("Upcoming events");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                System.out.printf("%s (%s)\n", event.getSummary(), start);
            }
        }*/

    }
}
