package com.dfernandezaller.service.imp;

import com.dfernandezaller.authentication.google.AuthorizationCodeFlowFactory;
import com.dfernandezaller.controller.dto.UserDTO;
import com.dfernandezaller.exceptions.BusinessException;
import com.dfernandezaller.repository.UserRepository;
import com.dfernandezaller.repository.entity.User;
import com.dfernandezaller.service.AuthenticationService;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import io.micronaut.core.convert.ConversionService;
import io.micronaut.data.exceptions.DataAccessException;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.io.IOException;

@Singleton
@Transactional
public class GoogleAuthenticationService implements AuthenticationService {

    private final Logger LOG = LoggerFactory.getLogger(GoogleAuthenticationService.class);
    private final AuthorizationCodeFlow authorizationCodeFlow;
    private final UserRepository userRepository;
    private final ConversionService<?> conversionService;

    public GoogleAuthenticationService(AuthorizationCodeFlowFactory authorizationCodeFlowFactory, UserRepository userRepository, ConversionService<?> conversionService) throws IOException {
        this.authorizationCodeFlow = authorizationCodeFlowFactory.getGoogleAuthorizationCodeFlow();
        this.userRepository = userRepository;
        this.conversionService = conversionService;
    }

    @Override
    public UserDTO signupUser(GoogleIdToken.Payload payload, String token) {
        TokenResponse tokenResponse = getTokenResponse(token);
        saveGoogleCredentials(payload.getEmail(), tokenResponse);
        User userToSave = User.builder()
                .name(payload.get("name").toString())
                .email(payload.getEmail())
                .urlPhoto(payload.get("picture").toString())
                .build();
        var userSaved = userRepository.save(userToSave);
        return conversionService.convertRequired(userSaved, UserDTO.class);
    }

    void saveGoogleCredentials(String email, TokenResponse tokenResponse) {
        try {
            if (authorizationCodeFlow.getCredentialDataStore().containsKey(email)) {
                LOG.trace("Found credential for email: {}", email);
                throw new BusinessException("User already exists");
            }
            authorizationCodeFlow.createAndStoreCredential(tokenResponse, email);
            LOG.trace("Stored credential for email: {}", email);
        } catch (IOException e) {
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    TokenResponse getTokenResponse(String token) {
        TokenResponse tokenResponse;
        try {
            tokenResponse = authorizationCodeFlow.newTokenRequest(token)
                    .setRedirectUri("postmessage")
                    .execute();
        } catch (IOException exception) {
            throw new BusinessException("Error while trying to get token response", exception);
        }
        return tokenResponse;
    }

}
