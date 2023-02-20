package com.dfernandezaller.controller;

import com.dfernandezaller.authentication.GoogleTokenVerifier;
import com.dfernandezaller.controller.dto.Credential;
import com.dfernandezaller.service.GoogleCalendarService;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.SneakyThrows;

import java.util.Collections;
import java.util.List;
import java.util.Map;

// TODO: 20/02/2023 Eliminar @SneakyThrows
// TODO: 20/02/2023 Eliminar System.out.println
// TODO: 20/02/2023 Pedir info del user a google y luego guardar ese user en la base de datos. Mirar metodos de la libreria de Google.

@Controller("/test")
@SerdeImport(DateTime.class)
public class TestController {

    private final GoogleTokenVerifier verifier;
    private final GoogleCalendarService calendarService;

    public TestController(GoogleTokenVerifier verifier, GoogleCalendarService calendarService) {
        this.verifier = verifier;
        this.calendarService = calendarService;
    }

    @Post(uri = "/auth", consumes = "application/json")
    public String testGoogleAuth(@Body Credential object) {
        System.out.println(object);
        //verifier.verify(object.credential());
        return "Hello World";
    }

    @Get
    public String test() {
        return "Hello World";
    }


    @SneakyThrows
    @Post(uri = "/google", consumes = "application/json")
    public MutableHttpResponse<List<Event>> testGoogle(@Body Map<String, String> code) {
        System.out.println(code);
        System.out.println(code.get("code"));

        AuthorizationCodeFlow authorizationCodeFlow = getAuthorizationCodeFlow();
        TokenResponse token = authorizationCodeFlow.newTokenRequest(code.get("code"))
                .setRedirectUri("postmessage") // A mi que me expliquen donde pone esto en la docu de google. Encontrado en: https://stackoverflow.com/questions/11485271/google-oauth-2-authorization-error-redirect-uri-mismatch
                .execute();

        System.out.println("Access token: " + token.getAccessToken());
        System.out.println("Refresh token: " + token.getRefreshToken());

        var events = calendarService.getCalendarEvents(token);

        return HttpResponse.ok(events);
    }

    @SneakyThrows
    private AuthorizationCodeFlow getAuthorizationCodeFlow() {
        return new GoogleAuthorizationCodeFlow.Builder(
                new NetHttpTransport(), GsonFactory.getDefaultInstance(),
                "814902779569-fhqsi7036j4a3jc0v52bf0n4bfchj997.apps.googleusercontent.com",
                "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",
                Collections.singleton(CalendarScopes.CALENDAR_READONLY)).setDataStoreFactory(
                new MemoryDataStoreFactory()).setAccessType("offline").build();
    }

}
