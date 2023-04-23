package com.dfernandezaller.controller;

import com.dfernandezaller.controller.dto.UserDTO;
import com.dfernandezaller.service.UserService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;

@Controller("/user")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Get
    public UserDTO getUser(Authentication authentication) {
        return userService.getUser(authentication.getName()).orElseThrow();
    }

}
