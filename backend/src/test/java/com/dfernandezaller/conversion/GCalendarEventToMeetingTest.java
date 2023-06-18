package com.dfernandezaller.conversion;

import com.dfernandezaller.model.Meeting;
import com.dfernandezaller.model.MeetingType;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
public class GCalendarEventToMeetingTest {

    @Inject
    GCalendarEventToMeeting gCalendarEventToMeeting;

    @Test
    public void testConvertConfirmedDefault() {
        testConvertEvent(null, "confirmed", MeetingType.ACCEPTED);
    }

    @Test
    public void testConvertTentativeDefault() {
        testConvertEvent(null, "tentative", MeetingType.TENTATIVE);
    }

    @Test
    public void testConvertCancelledDefault() {
        testConvertEvent(null, "cancelled", MeetingType.CANCELLED);
    }

    @Test
    public void testConvertOtherStatusDefault() {
        testConvertEvent(null, "otherStatus", MeetingType.OTHER);
    }

    @Test
    public void testConvertOutOfOffice() {
        testConvertEvent("outOfOffice", "confirmed", MeetingType.OOO);
    }

    @Test
    public void testConvertOtherEventType() {
        testConvertEvent("otherType", "confirmed", MeetingType.OTHER);
    }

    private void testConvertEvent(String eventType, String status, MeetingType expectedType) {
        Event event = new Event();
        event.setStart(new EventDateTime().setDateTime(getDateTime("2023-05-18T12:00:00Z")).setTimeZone("UTC"));
        event.setEnd(new EventDateTime().setDateTime(getDateTime("2023-05-18T13:00:00Z")).setTimeZone("UTC"));
        event.setEventType(eventType);
        event.setStatus(status);

        Meeting meeting = gCalendarEventToMeeting.convert(event, Meeting.class).orElse(null);

        assertThat(meeting)
                .isEqualTo(
                        Meeting.builder()
                                .type(expectedType)
                                .isAllDay(false)
                                .startTime(LocalDateTime.of(2023, 5, 18, 12, 0))
                                .endTime(LocalDateTime.of(2023, 5, 18, 13, 0))
                                .build()
                );
    }

    private com.google.api.client.util.DateTime getDateTime(String dateTimeString) {
        Instant instant = Instant.parse(dateTimeString);
        return new com.google.api.client.util.DateTime(instant.toEpochMilli());
    }
}
