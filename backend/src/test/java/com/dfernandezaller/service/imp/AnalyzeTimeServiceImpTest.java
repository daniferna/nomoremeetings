package com.dfernandezaller.service.imp;

import com.dfernandezaller.controller.dto.UserDTO;
import com.dfernandezaller.model.Meeting;
import com.dfernandezaller.model.MeetingType;
import com.dfernandezaller.model.TimeAnalysisResults;
import com.dfernandezaller.service.AnalyzeTimeService;
import com.dfernandezaller.service.CalendarService;
import com.dfernandezaller.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AnalyzeTimeServiceImpTest {

    @Mock
    private UserService userService;
    @Mock
    private CalendarService calendarService;
    private AnalyzeTimeService analyzeTimeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        analyzeTimeService = new AnalyzeTimeServiceImp(userService, calendarService);
    }

    @Test
    public void getTimeSpentInMeetingsTest() {
        String userEmail = "test@example.com";
        UserDTO user = UserDTO.builder()
                .email(userEmail)
                .daysToAnalyze(5)
                .startWorkingTime(LocalTime.of(8, 0))
                .endWorkingTime(LocalTime.of(17, 0))
                .startLunchTime(LocalTime.of(12, 0))
                .endLunchTime(LocalTime.of(13, 0))
                .timeBetweenMeetings(15)
                .build();

        when(userService.getUser(userEmail)).thenReturn(java.util.Optional.of(user));

        LocalDate startDate = LocalDate.of(2022, 6, 20);
        LocalDate endDate = LocalDate.of(2022, 6, 25);

        List<Meeting> meetings = getTestMeetings();

        when(calendarService.getCalendarMeetings(user, startDate, endDate)).thenReturn(meetings);

        TimeAnalysisResults expectedResults = new TimeAnalysisResults(2, 1, 8, 40);

        TimeAnalysisResults result;
        try (MockedStatic<LocalDate> mocked = mockStatic(LocalDate.class)) {
            mocked.when(LocalDate::now).thenReturn(endDate);
            assertThat(LocalDate.now()).isEqualTo(endDate);
            result = analyzeTimeService.getTimeSpentInMeetings(userEmail);
            mocked.verify(LocalDate::now, times(3));
        }

        assertThat(result).usingRecursiveComparison().isEqualTo(expectedResults);
    }

    private List<Meeting> getTestMeetings() {
        return Arrays.asList(
                Meeting.builder()
                        .type(MeetingType.ACCEPTED)
                        .startTime(LocalDateTime.of(2022, 6, 20, 9, 0))
                        .endTime(LocalDateTime.of(2022, 6, 20, 11, 0))
                        .isAllDay(false)
                        .build(),
                Meeting.builder()
                        .type(MeetingType.TENTATIVE)
                        .startTime(LocalDateTime.of(2022, 6, 21, 9, 0))
                        .endTime(LocalDateTime.of(2022, 6, 21, 10, 0))
                        .isAllDay(false)
                        .build(),
                Meeting.builder()
                        .type(MeetingType.OOO)
                        .startTime(LocalDateTime.of(2022, 6, 22, 0, 0))
                        .endTime(LocalDateTime.of(2022, 6, 22, 23, 59))
                        .isAllDay(true)
                        .build(),
                Meeting.builder()
                        .type(MeetingType.ACCEPTED)
                        .startTime(LocalDateTime.of(2022, 6, 23, 18, 0))
                        .endTime(LocalDateTime.of(2022, 6, 23, 20, 0))
                        .isAllDay(false)
                        .build(),
                Meeting.builder()
                        .type(MeetingType.CANCELLED)
                        .startTime(LocalDateTime.of(2022, 6, 24, 9, 0))
                        .endTime(LocalDateTime.of(2022, 6, 24, 13, 0))
                        .isAllDay(false)
                        .build()
        );
    }
}