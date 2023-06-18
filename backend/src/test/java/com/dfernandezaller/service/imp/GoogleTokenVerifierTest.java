package com.dfernandezaller.service.imp;

import com.dfernandezaller.authentication.google.dto.GoogleVerificationResult;
import com.dfernandezaller.configuration.GoogleConfiguration;
import com.dfernandezaller.exceptions.BusinessException;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.webtoken.JsonWebSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class GoogleTokenVerifierTest {

    private GoogleIdTokenVerifier verifierMock;
    private GoogleTokenVerifier googleTokenVerifier;

    @BeforeEach
    public void setup() {
        verifierMock = mock(GoogleIdTokenVerifier.class);

        GoogleConfiguration googleConfiguration = new GoogleConfiguration();
        googleConfiguration.setClientId("clientId");

        googleTokenVerifier = new GoogleTokenVerifier(googleConfiguration);
        googleTokenVerifier.verifier = verifierMock;
    }

    @Test
    public void testVerify() throws GeneralSecurityException, IOException {
        String token = "token";
        GoogleIdToken idToken = new GoogleIdToken(new JsonWebSignature.Header(), new GoogleIdToken.Payload(),
                "".getBytes(StandardCharsets.UTF_8), "".getBytes(StandardCharsets.UTF_8));

        when(verifierMock.verify(token)).thenReturn(idToken);

        GoogleVerificationResult result = googleTokenVerifier.verify(token);

        assertThat(result).isNotNull();
        assertThat(result.isValid()).isTrue();
        assertThat(result.getFailureReason()).isEmpty();
        assertThat(result.getPayload()).isEqualTo(idToken.getPayload());

        verify(verifierMock, times(1)).verify(token);
    }

    @Test
    public void testVerifyInvalidToken() throws GeneralSecurityException, IOException {
        String token = "invalidToken";

        when(verifierMock.verify(token)).thenReturn(null);

        GoogleVerificationResult result = googleTokenVerifier.verify(token);

        assertThat(result).isNotNull();
        assertThat(result.isValid()).isFalse();
        assertThat(result.getFailureReason()).isEqualTo("Invalid token");
        assertThat(result.getPayload()).isNull();

        verify(verifierMock, times(1)).verify(token);
    }

    @Test
    public void testVerifyWithGeneralSecurityException() throws GeneralSecurityException, IOException {
        String token = "token";

        when(verifierMock.verify(token)).thenThrow(new GeneralSecurityException());

        assertThatThrownBy(() -> googleTokenVerifier.verify(token))
                .isInstanceOf(SecurityException.class)
                .hasMessageContaining("There is a problem with the signature of the provided token");

        verify(verifierMock, times(1)).verify(token);
    }

    @Test
    public void testVerifyWithIOException() throws GeneralSecurityException, IOException {
        String token = "token";

        when(verifierMock.verify(token)).thenThrow(new IOException());

        assertThatThrownBy(() -> googleTokenVerifier.verify(token))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("There has been an I/O problem while verifying the token");

        verify(verifierMock, times(1)).verify(token);
    }

}
