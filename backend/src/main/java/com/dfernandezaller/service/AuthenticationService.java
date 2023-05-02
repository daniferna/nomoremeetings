package com.dfernandezaller.service;

import com.dfernandezaller.controller.dto.UserDTO;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

public interface AuthenticationService {

    UserDTO signupUser(GoogleIdToken.Payload payload, String token);

}
