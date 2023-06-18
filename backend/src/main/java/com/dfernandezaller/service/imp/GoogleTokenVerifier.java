package com.dfernandezaller.service.imp;

import com.dfernandezaller.authentication.google.dto.GoogleVerificationResult;
import com.dfernandezaller.configuration.GoogleConfiguration;
import com.dfernandezaller.exceptions.BusinessException;
import com.dfernandezaller.service.TokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.common.annotations.VisibleForTesting;
import io.micronaut.core.util.StringUtils;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Singleton
public class GoogleTokenVerifier implements TokenVerifier {

    private static final Logger logger = LoggerFactory.getLogger(GoogleTokenVerifier.class);

    @VisibleForTesting
    GoogleIdTokenVerifier verifier;

    public GoogleTokenVerifier(GoogleConfiguration googleConfiguration) {
        NetHttpTransport httpTransport = new NetHttpTransport();
        this.verifier = new GoogleIdTokenVerifier.Builder(httpTransport, GsonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(googleConfiguration.getClientId()))
                .build();
    }

    @Override
    public GoogleVerificationResult verify(String token) {
        GoogleIdToken idToken;
        try {
            idToken = verifier.verify(token);
        } catch (GeneralSecurityException e) {
            throw new SecurityException("There is a problem with the signature of the provided token", e);
        } catch (IOException e) {
            throw new BusinessException("There has been an I/O problem while verifying the token", e);
        }

        if (idToken != null) {
            return new GoogleVerificationResult(true, StringUtils.EMPTY_STRING, idToken.getPayload());
        } else {
            logger.warn("Invalid token {}", token);
            return new GoogleVerificationResult(false, "Invalid token", null);
        }
    }

}
