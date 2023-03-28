package com.dfernandezaller.controller;

import com.dfernandezaller.authentication.google.AuthorizationCodeFlowFactory;
import com.dfernandezaller.controller.dto.Credential;
import com.dfernandezaller.repository.GoogleCredentialRepository;
import com.dfernandezaller.repository.entity.GoogleCredential;
import com.dfernandezaller.service.GoogleCalendarService;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Map;

// TODO: 20/02/2023 Eliminar @SneakyThrows
// TODO: 20/02/2023 Eliminar System.out.println
// TODO: 20/02/2023 Pedir info del user a google y luego guardar ese user en la base de datos. Mirar metodos de la libreria de Google.

@Controller("/test")
@SerdeImport(DateTime.class)
public class TestController {

    private final AuthorizationCodeFlowFactory authorizationCodeFlowFactory;
    private final GoogleCredentialRepository repository;
    private final GoogleCalendarService calendarService;

    public TestController(AuthorizationCodeFlowFactory authorizationCodeFlowFactory, GoogleCredentialRepository repository, GoogleCalendarService calendarService) {
        this.authorizationCodeFlowFactory = authorizationCodeFlowFactory;
        this.repository = repository;
        this.calendarService = calendarService;
    }

    @Get(uri = "/test")
    public Iterable<GoogleCredential> test() {
        return repository.findAll();
    }

    @Post(uri = "/auth", consumes = "application/json")
    public String testGoogleAuth(@Body Credential object) {
        System.out.println(object);
        //verifier.verify(object.credential());
        return "Hello World";
    }


    @SneakyThrows
    @Post(uri = "/google", consumes = "application/json")
    public MutableHttpResponse<List<Event>> testGoogle(@Body Map<String, String> code) {
        System.out.println(code);
        System.out.println(code.get("code"));

        AuthorizationCodeFlow authorizationCodeFlow = authorizationCodeFlowFactory.getAuthorizationCodeFlow();

        if (authorizationCodeFlow.getCredentialDataStore().containsKey("test")) {
            System.out.println("Ya existe");
            var accessToken = authorizationCodeFlow.loadCredential("test").getAccessToken();
            if (accessToken == null) {
                System.out.println("AccessToken null");
                return HttpResponse.badRequest();
            }
            return HttpResponse.ok(calendarService.getCalendarEvents(accessToken));
        }

        TokenResponse token = authorizationCodeFlow.newTokenRequest(code.get("code"))
                .setRedirectUri("postmessage") // Encontrado en: https://stackoverflow.com/questions/11485271/google-oauth-2-authorization-error-redirect-uri-mismatch
                .execute();

        authorizationCodeFlow.createAndStoreCredential(token, "test"); // TODO: 27/03/2023 Implementar flow correcto (recibir email -> checkear si existe en bbdd -> si existe ya esta, si no pedir accessToken desde front)

        System.out.println("Access token: " + token.getAccessToken());
        System.out.println("Refresh token: " + token.getRefreshToken());

        var events = calendarService.getCalendarEvents(token.getAccessToken());

        return HttpResponse.ok(events);
    }

}
