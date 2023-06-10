package com.dfernandezaller.service;

import com.dfernandezaller.controller.dto.UserDTO;
import com.dfernandezaller.model.Meeting;

import java.time.LocalDate;
import java.util.List;

public interface CalendarService {

    List<Meeting> getCalendarMeetings(UserDTO user, LocalDate startDate, LocalDate endDate);

}