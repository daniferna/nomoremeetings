package com.dfernandezaller.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import io.micronaut.context.annotation.Bean;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Bean
public class VerifierService {

    private final NetHttpTransport httpTransport;
    private final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    public VerifierService() throws GeneralSecurityException, IOException {
        this.httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    }

    public boolean verify(String idTokenString) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(httpTransport, JSON_FACTORY)
                .setAudience(Collections.singletonList("814902779569-fhqsi7036j4a3jc0v52bf0n4bfchj997.apps.googleusercontent.com"))// TODO: 28/12/22 Set in variables
                .build();

        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);

            // Get profile information from payload
            String email = payload.getEmail();
            Boolean emailVerified = payload.getEmailVerified();
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");

            // Use or store profile information
            // ...

            System.out.println(payload);

            return true;
        } else {
            System.out.println("Invalid ID token.");
            return false;
        }
    }
}
