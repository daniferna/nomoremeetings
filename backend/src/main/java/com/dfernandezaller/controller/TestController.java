package com.dfernandezaller.controller;

import com.dfernandezaller.model.TimeAnalysisResults;
import com.dfernandezaller.service.AnalyzeTimeService;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

@Controller("/test")
@Requires(env = "dev")
@Secured(SecurityRule.IS_ANONYMOUS)
public class TestController {

    private final AnalyzeTimeService analyzeTimeService;

    public TestController(AnalyzeTimeService analyzeTimeService) {
        this.analyzeTimeService = analyzeTimeService;
    }

    @Get
    public TimeAnalysisResults getTimeSpentInMeetings() {
        return analyzeTimeService.getTimeSpentInMeetings("danixe.ferna@gmail.com");
    }

}
