package com.dfernandezaller.service;

import com.dfernandezaller.model.TimeAnalysisResults;

public interface AnalyzeTimeService {

    TimeAnalysisResults getTimeSpentInMeetings(String userEmail);

}
