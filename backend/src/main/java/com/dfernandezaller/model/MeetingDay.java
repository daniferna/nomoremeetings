package com.dfernandezaller.model;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class MeetingDay {

    private List<Meeting> meetings;

    public void addMeeting(Meeting meeting) {
        if (meetings == null) {
            meetings = new ArrayList<>();
        }
        meetings.add(meeting);
    }

    public Meeting[] getBusyMeetings() {
        return meetings.stream()
                .filter(meeting -> meeting.type().equals(MeetingType.ACCEPTED))
                .filter(meeting -> !meeting.isAllDay())
                .toArray(Meeting[]::new);
    }

    public Meeting[] getTentativeMeetings() {
        return meetings.stream()
                .filter(meeting -> meeting.type().equals(MeetingType.TENTATIVE))
                .filter(meeting -> !meeting.isAllDay())
                .toArray(Meeting[]::new);
    }

    public Meeting[] getOooMeetings() {
        return meetings.stream()
                .filter(meeting -> meeting.type().equals(MeetingType.OOO))
                .toArray(Meeting[]::new);
    }
}
