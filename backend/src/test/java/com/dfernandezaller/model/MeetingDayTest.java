package com.dfernandezaller.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MeetingDayTest {

    private MeetingDay meetingDay;

    @BeforeEach
    public void setup() {
        meetingDay = new MeetingDay();
        Meeting meeting1 = Meeting.builder().type(MeetingType.ACCEPTED).isAllDay(false).startTime(LocalDateTime.now()).endTime(LocalDateTime.now().plusHours(2)).build();
        Meeting meeting2 = Meeting.builder().type(MeetingType.TENTATIVE).isAllDay(false).startTime(LocalDateTime.now()).endTime(LocalDateTime.now().plusHours(2)).build();
        Meeting meeting3 = Meeting.builder().type(MeetingType.OOO).isAllDay(true).startTime(LocalDateTime.now().withHour(0)).endTime(LocalDateTime.now().withHour(23).withMinute(59)).build();

        meetingDay.addMeeting(meeting1);
        meetingDay.addMeeting(meeting2);
        meetingDay.addMeeting(meeting3);
    }

    @Test
    public void testGetBusyMeetings() {
        Meeting[] busyMeetings = meetingDay.getBusyMeetings();
        assertEquals(1, busyMeetings.length);
        assertEquals(MeetingType.ACCEPTED, busyMeetings[0].type());
        assertFalse(busyMeetings[0].isAllDay());
    }

    @Test
    public void testGetTentativeMeetings() {
        Meeting[] tentativeMeetings = meetingDay.getTentativeMeetings();
        assertEquals(1, tentativeMeetings.length);
        assertEquals(MeetingType.TENTATIVE, tentativeMeetings[0].type());
        assertFalse(tentativeMeetings[0].isAllDay());
    }

    @Test
    public void testGetOooMeetings() {
        Meeting[] oooMeetings = meetingDay.getOooMeetings();
        assertEquals(1, oooMeetings.length);
        assertEquals(MeetingType.OOO, oooMeetings[0].type());
    }
}
