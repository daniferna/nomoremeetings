package com.dfernandezaller.service;

import com.dfernandezaller.model.Meeting;

import java.time.LocalDate;
import java.util.List;

public interface CalendarService {

    List<Meeting> getCalendarMeetings(String calendarId, LocalDate startDate, LocalDate endDate);

}