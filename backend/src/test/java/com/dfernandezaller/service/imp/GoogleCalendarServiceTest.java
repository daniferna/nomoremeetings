package com.dfernandezaller.service.imp;

import com.dfernandezaller.authentication.google.GoogleAuthorizationCodeFlowFactory;
import com.dfernandezaller.controller.dto.UserDTO;
import com.dfernandezaller.exceptions.BusinessException;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import io.micronaut.core.convert.ConversionService;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.security.GeneralSecurityException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

@MicronautTest
class GoogleCalendarServiceTest {

    @Inject
    private ConversionService<?> conversionService;

    @Mock
    private GoogleAuthorizationCodeFlowFactory googleAuthorizationCodeFlowFactory;

    private GoogleCalendarService googleCalendarService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        googleCalendarService = new GoogleCalendarService(conversionService, googleAuthorizationCodeFlowFactory);
    }

    @Test
    void testGetCalendarServiceWithGeneralSecurityException() {
        UserDTO user = UserDTO.builder()
                .email("test@example.com")
                .build();

        try (MockedStatic<GoogleNetHttpTransport> mocked = mockStatic(GoogleNetHttpTransport.class)) {
            mocked.when(GoogleNetHttpTransport::newTrustedTransport).thenThrow(new GeneralSecurityException());
            assertThatThrownBy(() -> googleCalendarService.getCalendarService(user.email()))
                    .isInstanceOf(BusinessException.class)
                    .hasMessageContaining("Error occurred when trying to create a secured HTTP connection with Google");
            mocked.verify(GoogleNetHttpTransport::newTrustedTransport, times(1));
        }

    }
}
