package com.dfernandezaller.controller;

import com.dfernandezaller.controller.dto.CalendarDTO;
import com.dfernandezaller.controller.dto.UpdateUserDTO;
import com.dfernandezaller.controller.dto.UserDTO;
import com.dfernandezaller.service.UserService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Patch;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

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

    @Get("/calendars")
    public List<CalendarDTO> getUserCalendars(Authentication authentication) throws GeneralSecurityException, IOException {
        return userService.getUserCalendars(authentication.getName());
    }

    @Patch
    public UserDTO updateUser(Authentication authentication, UpdateUserDTO requestDTO) {
        return userService.updateUser(authentication.getName(), requestDTO).orElseThrow();
    }

}
