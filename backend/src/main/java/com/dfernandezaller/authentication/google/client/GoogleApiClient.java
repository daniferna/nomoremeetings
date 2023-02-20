package com.dfernandezaller.authentication.google.client;

import com.dfernandezaller.authentication.google.GoogleUser;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.client.annotation.Client;
import org.reactivestreams.Publisher;

@Header(name = "User-Agent", value = "Micronaut")
@Client("https://www.googleapis.com/oauth2/v3")
public interface GoogleApiClient {

    @Get("/userinfo?alt=json")
    Publisher<GoogleUser> getUserInfo(@Header("Authorization") String authorization);

}
