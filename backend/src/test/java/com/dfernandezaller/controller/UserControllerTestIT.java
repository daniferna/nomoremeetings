package com.dfernandezaller.controller;

import io.micronaut.security.authentication.Authentication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class UserControllerTestIT {

    @Inject
    private UserController userController;

    @Test
    @Ignore("WIP")
    void shouldGetUser() {
        userController.getUser(Authentication.build("test@gmail.com"));
    }

}