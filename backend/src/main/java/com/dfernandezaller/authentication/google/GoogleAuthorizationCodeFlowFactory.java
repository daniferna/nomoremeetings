package com.dfernandezaller.authentication.google;

import com.dfernandezaller.configuration.GoogleConfiguration;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;

import java.io.IOException;
import java.util.Collections;

@Singleton
@Factory
public class GoogleAuthorizationCodeFlowFactory {

    private final DataStoreFactory dataStoreFactory;
    private final GoogleConfiguration googleConfiguration;

    public GoogleAuthorizationCodeFlowFactory(DataStoreFactory dataStoreFactory, GoogleConfiguration googleConfiguration) {
        this.dataStoreFactory = dataStoreFactory;
        this.googleConfiguration = googleConfiguration;
    }

    public AuthorizationCodeFlow getAuthorizationCodeFlow() throws IOException {
        return new GoogleAuthorizationCodeFlow.Builder(
                new NetHttpTransport(), GsonFactory.getDefaultInstance(),
                googleConfiguration.getClientId(),
                googleConfiguration.getClientSecret(),
                Collections.singleton(CalendarScopes.CALENDAR_READONLY))
                .setDataStoreFactory(dataStoreFactory)
                .setAccessType("offline").build();
    }

}
