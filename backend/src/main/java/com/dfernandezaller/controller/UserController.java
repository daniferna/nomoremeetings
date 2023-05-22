package com.dfernandezaller.controller;

import com.dfernandezaller.controller.dto.CalendarDTO;
import com.dfernandezaller.controller.dto.UpdateUserDTO;
import com.dfernandezaller.controller.dto.UserDTO;
import com.dfernandezaller.model.TimeAnalysisResults;
import com.dfernandezaller.service.AnalyzeTimeService;
import com.dfernandezaller.service.UserService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Patch;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;

import java.util.List;

@Controller("/user")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class UserController {

    private final UserService userService;
    private final AnalyzeTimeService analyzeTimeService;

    public UserController(UserService userService, AnalyzeTimeService analyzeTimeService) {
        this.userService = userService;
        this.analyzeTimeService = analyzeTimeService;
    }

    @Get
    public UserDTO getUser(Authentication authentication) {
        return userService.getUser(authentication.getName()).orElseThrow();
    }

    @Patch
    public UserDTO updateUser(Authentication authentication, UpdateUserDTO requestDTO) {
        return userService.updateUser(authentication.getName(), requestDTO).orElseThrow();
    }

    @Get("/calendars")
    public List<CalendarDTO> getUserCalendars(Authentication authentication) {
        return userService.getUserCalendars(authentication.getName());
    }

    @Get("/timeAnalysis")
    public TimeAnalysisResults getTimeSpentInMeetings(Authentication authentication) {
        return analyzeTimeService.getTimeSpentInMeetings(authentication.getName());
    }

}
