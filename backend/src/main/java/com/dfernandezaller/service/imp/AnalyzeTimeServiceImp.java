package com.dfernandezaller.service.imp;

import com.dfernandezaller.controller.dto.UserDTO;
import com.dfernandezaller.model.Meeting;
import com.dfernandezaller.model.MeetingDay;
import com.dfernandezaller.model.TimeAnalysisResults;
import com.dfernandezaller.model.TimeDistribution;
import com.dfernandezaller.service.AnalyzeTimeService;
import com.dfernandezaller.service.CalendarService;
import com.dfernandezaller.service.UserService;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class AnalyzeTimeServiceImp implements AnalyzeTimeService {

    private static final Logger LOG = LoggerFactory.getLogger(AnalyzeTimeServiceImp.class);
    public static final double MINUTES_IN_HOUR = 60.0;

    private final UserService userService;
    private final CalendarService calendarService;

    public AnalyzeTimeServiceImp(UserService userService, CalendarService calendarService) {
        this.userService = userService;
        this.calendarService = calendarService;
    }

    @Override
    public TimeAnalysisResults getTimeSpentInMeetings(String userEmail) {
        var user = userService.getUser(userEmail).orElseThrow();
        var intervalOfTimeToAnalyze = user.daysToAnalyze();
        var startDate = LocalDate.now().minus(intervalOfTimeToAnalyze, ChronoUnit.DAYS);
        var endDate = LocalDate.now();
        List<Meeting> meetings = getMeetings(user, startDate, endDate);
        Map<LocalDate, MeetingDay> meetingsMap = getMeetingsMap(meetings);

        var result = calculateBusyTentativeOooHours(meetingsMap, user, startDate, endDate);

        double workedDays = calculateWorkedDays(startDate, endDate);

        return new TimeAnalysisResults(result.busy(), result.tentative(), result.ooo(), workedDays * getWorkingHours(user));
    }

    private List<Meeting> getMeetings(UserDTO user, LocalDate startDate, LocalDate endDate) {
        return calendarService.getCalendarMeetings(user, startDate, endDate);
    }

    private double calculateWorkedDays(LocalDate startDate, LocalDate endDate) {
        double workedDays = 0.0;
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            if (date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                workedDays++;
            }
        }
        return workedDays;
    }

    private int getWorkingHours(UserDTO user) {
        return Math.toIntExact(user.startWorkingTime().until(user.endWorkingTime(), ChronoUnit.HOURS)
                - user.startLunchTime().until(user.endLunchTime(), ChronoUnit.HOURS));
    }

    private Map<LocalDate, MeetingDay> getMeetingsMap(List<Meeting> meetings) {
        Map<LocalDate, MeetingDay> meetingsMap = new HashMap<>();
        meetings.forEach(meeting -> {
            var date = meeting.startTime().toLocalDate();
            var meetingDay = meetingsMap.get(date);
            if (meetingDay == null) {
                meetingDay = new MeetingDay();
                meetingsMap.put(date, meetingDay);
            }
            meetingDay.addMeeting(meeting);
        });
        return meetingsMap;
    }

    private TimeDistribution calculateBusyTentativeOooHours(Map<LocalDate, MeetingDay> meetingsMap, UserDTO user, LocalDate startDate, LocalDate endDate) {
        int resolutionMinutes = user.timeBetweenMeetings();
        int workingHours = getWorkingHours(user);
        LocalTime startHour = user.startWorkingTime();
        LocalTime endHour = user.endWorkingTime();

        // Estos son minutos
        int busy = 0;
        int tentative = 0;
        int ooo = 0;

        for (LocalDate day = startDate; !day.isAfter(endDate); day = day.plusDays(1)) {
            var dayMinutesDistribution = calculateBusyTentativeOooMinutesForDay(meetingsMap, day, resolutionMinutes, workingHours, startHour, endHour);
            busy += dayMinutesDistribution.busy();
            tentative += dayMinutesDistribution.tentative();
            ooo += dayMinutesDistribution.ooo();
        }

        return new TimeDistribution(busy / MINUTES_IN_HOUR, tentative / MINUTES_IN_HOUR, ooo / MINUTES_IN_HOUR);
    }

    private TimeDistribution calculateBusyTentativeOooMinutesForDay(Map<LocalDate, MeetingDay> meetings, LocalDate day, int resolutionMinutes, int workingHours, LocalTime startHour, LocalTime endHour) {
        MeetingDay meetingsForDay = meetings.get(day);
        if (day.getDayOfWeek().getValue() > DayOfWeek.FRIDAY.getValue() || meetingsForDay == null) {
            return new TimeDistribution(0, 0, 0);
        }

        int busyDay = 0;
        int tentativeDay = 0;
        int oooDay = 0;

        Meeting[] busyMeetings = meetingsForDay.getBusyMeetings();
        Meeting[] tentativeMeetings = meetingsForDay.getTentativeMeetings();
        Meeting[] oooMeetings = meetingsForDay.getOooMeetings();

        LocalTime previousInterval = null;
        for (LocalTime interval = startHour; interval.isBefore(endHour.plus(1, ChronoUnit.SECONDS)); interval = interval.plusMinutes(resolutionMinutes)) {
            if (previousInterval == null) {
                previousInterval = interval;
                continue;
            }

            TimeDistribution meetingMinutes = calculateBusyTentativeOooMinutesForInterval(busyMeetings, tentativeMeetings, oooMeetings, previousInterval, interval, resolutionMinutes);
            busyDay += meetingMinutes.busy();
            tentativeDay += meetingMinutes.tentative();
            oooDay += meetingMinutes.ooo();

            previousInterval = interval;
        }

        // Máximo esto a las horas de trabajo
        int workingMinutes = workingHours * 60;
        return adjustDayMinutesToWorkingMinutes(busyDay, tentativeDay, oooDay, workingMinutes, day);
    }

    private TimeDistribution calculateBusyTentativeOooMinutesForInterval(Meeting[] busyMeetings, Meeting[] tentativeMeetings, Meeting[] oooMeetings, LocalTime previousInterval, LocalTime interval, int resolutionMinutes) {
        boolean busyInInterval = hasMeetingInInterval(busyMeetings, previousInterval, interval);
        boolean tentativeInInterval = hasMeetingInInterval(tentativeMeetings, previousInterval, interval);
        boolean oooInInterval = hasMeetingInInterval(oooMeetings, previousInterval, interval);

        // La precedencia es OOO, ocupado, tentativo
        if (oooInInterval) {
            return new TimeDistribution(0, 0, resolutionMinutes);
        } else if (busyInInterval) {
            return new TimeDistribution(resolutionMinutes, 0, 0);
        } else if (tentativeInInterval) {
            return new TimeDistribution(0, resolutionMinutes, 0);
        } else {
            return new TimeDistribution(0, 0, 0);
        }
    }

    private TimeDistribution adjustDayMinutesToWorkingMinutes(int busyDay, int tentativeDay, int oooDay,
                                                              int workingMinutes, LocalDate day) {
        int totalMinutes = busyDay + tentativeDay + oooDay;

        if (totalMinutes > workingMinutes) {
            LOG.debug("More minutes in meetings (busy: {}, tentative: {}, ooo: {}) than working minutes ({}) on day {}",
                    busyDay, tentativeDay, oooDay, workingMinutes, day);
            int difference = totalMinutes - workingMinutes;
            return subtractMinutesFromMeetings(busyDay, tentativeDay, oooDay, difference);
        }

        return new TimeDistribution(busyDay, tentativeDay, oooDay);
    }

    private TimeDistribution subtractMinutesFromMeetings(int busyDay, int tentativeDay, int oooDay, int difference) {
        int remainingDifference = difference;

        // Intenta eliminar primero de ooo
        if (oooDay >= remainingDifference) {
            oooDay -= remainingDifference;
            remainingDifference = 0;
        } else {
            remainingDifference -= oooDay;
            oooDay = 0;
        }

        // Ahora intenta eliminar de tentativo ya que es más posible que consuma tiempo
        if (remainingDifference > 0 && tentativeDay >= remainingDifference) {
            tentativeDay -= remainingDifference;
            remainingDifference = 0;
        } else {
            remainingDifference -= tentativeDay;
            tentativeDay = 0;
        }

        // Ahora elimina de ocupado
        if (remainingDifference > 0 && busyDay >= remainingDifference) {
            busyDay -= remainingDifference;
        } else {
            busyDay = 0;
        }

        return new TimeDistribution(busyDay, tentativeDay, oooDay);
    }

    private static boolean hasMeetingInInterval(Meeting[] meetings, LocalTime start, LocalTime end) {
        return Arrays.stream(meetings)
                .anyMatch(meeting -> meeting.startTime().toLocalTime().isBefore(end)
                        && meeting.endTime().toLocalTime().isAfter(start)
                );
    }

}