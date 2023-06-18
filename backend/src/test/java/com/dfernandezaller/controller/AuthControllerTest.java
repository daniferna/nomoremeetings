package com.dfernandezaller.controller;

import com.dfernandezaller.authentication.google.dto.GoogleVerificationResult;
import com.dfernandezaller.controller.dto.GoogleSignupData;
import com.dfernandezaller.controller.dto.UserDTO;
import com.dfernandezaller.service.AuthenticationService;
import com.dfernandezaller.service.UserService;
import com.dfernandezaller.service.imp.GoogleTokenVerifier;
import io.micronaut.http.HttpStatus;
import io.micronaut.security.authentication.Authentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.mock;
import static org.mockito.Mockito.when;

public class AuthControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private UserService userService;

    @Mock
    private GoogleTokenVerifier verifier;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoginGoogle() {
        String username = "test";
        UserDTO userDTO = UserDTO.builder().build();
        when(userService.getUser(username)).thenReturn(Optional.of(userDTO));
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(username);

        var result = authController.loginGoogle(auth);

        assertThat(result.getBody().orElseThrow()).isEqualTo(userDTO);
    }

    @Test
    public void testLoginGoogleUserNotFound() {
        String username = "testUserNotExist";
        when(userService.getUser(username)).thenReturn(Optional.empty());
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(username);

        var result = authController.loginGoogle(auth);

        assertThat(result.getStatus().getCode()).isEqualTo(HttpStatus.UNAUTHORIZED.getCode());
    }

    @Test
    public void testSignupGoogle() {
        String idToken = "idToken";
        String codeToken = "codeToken";
        GoogleSignupData signupData = new GoogleSignupData(codeToken, idToken);
        GoogleVerificationResult validResult = new GoogleVerificationResult(true, null, null);
        UserDTO userDTO = UserDTO.builder().build();
        when(verifier.verify(idToken)).thenReturn(validResult);
        when(authenticationService.signupUser(validResult.getPayload(), codeToken)).thenReturn(userDTO);

        var result = authController.signupGoogle(signupData);

        assertThat(result.getBody().orElseThrow()).isEqualTo(userDTO);
    }

    @Test
    public void testSignupGoogleInvalidIdToken() {
        String idToken = "idToken";
        String codeToken = "codeToken";
        GoogleSignupData signupData = new GoogleSignupData(codeToken, idToken);
        GoogleVerificationResult invalidResult = new GoogleVerificationResult(false, null, null);
        when(verifier.verify(idToken)).thenReturn(invalidResult);

        var result = authController.signupGoogle(signupData);

        assertThat(result.getStatus().getCode()).isEqualTo(HttpStatus.UNAUTHORIZED.getCode());
    }
}
