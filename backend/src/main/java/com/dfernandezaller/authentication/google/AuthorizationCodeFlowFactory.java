package com.dfernandezaller.authentication.google;

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
public class AuthorizationCodeFlowFactory {

    private final DataStoreFactory dataStoreFactory;

    public AuthorizationCodeFlowFactory(DataStoreFactory dataStoreFactory) {
        this.dataStoreFactory = dataStoreFactory;
    }

    public AuthorizationCodeFlow getAuthorizationCodeFlow() throws IOException {
        return new GoogleAuthorizationCodeFlow.Builder(
                new NetHttpTransport(), GsonFactory.getDefaultInstance(),
                "814902779569-fhqsi7036j4a3jc0v52bf0n4bfchj997.apps.googleusercontent.com",
                "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",
                Collections.singleton(CalendarScopes.CALENDAR_READONLY))
                .setDataStoreFactory(dataStoreFactory)
                .setAccessType("offline").build();
    }

}
