package com.dfernandezaller.service.imp;

import com.dfernandezaller.service.AnalyzeTimeService;
import com.dfernandezaller.service.UserService;
import jakarta.inject.Singleton;

@Singleton
public class AnalyzeTimeServiceImp implements AnalyzeTimeService {

    private final UserService userService;

    public AnalyzeTimeServiceImp(UserService userService) {
        this.userService = userService;
    }

    @Override
    public double getTimeSpentInMeetings(String userEmail) {
        return 0;
    }
}
