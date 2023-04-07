package com.dfernandezaller.authentication;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import jakarta.inject.Singleton;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Optional;

@Singleton
public class GoogleTokenVerifier implements TokenVerifier {

    private final String clientId = "814902779569-fhqsi7036j4a3jc0v52bf0n4bfchj997.apps.googleusercontent.com";
    private static final Logger logger = LoggerFactory.getLogger(GoogleTokenVerifier.class);

    private final GoogleIdTokenVerifier verifier;

    public GoogleTokenVerifier() {
        NetHttpTransport httpTransport = new NetHttpTransport();
        this.verifier = new GoogleIdTokenVerifier.Builder(httpTransport, GsonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(clientId))
                .build();
        logger.warn("CLIENT ID: {}", clientId);
    }

    @SneakyThrows // TODO: 16/01/2023 REMOVE THIS
    @Override
    public VerificationResult verify(String token) {
        GoogleIdToken idToken = verifier.verify(token);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);

            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");

            // Use or store profile information
            // ...
            logger.info("name is: {}", name);

            return new VerificationResult(true, Optional.ofNullable(email), Optional.empty());

        } else {
            logger.warn("Invalid ID token.");
            return new VerificationResult(false, Optional.empty(), Optional.of("Invalid ID token"));
        }
    }

}
