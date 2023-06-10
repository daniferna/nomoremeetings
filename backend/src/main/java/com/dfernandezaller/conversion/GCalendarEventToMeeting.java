package com.dfernandezaller.conversion;

import com.dfernandezaller.model.Meeting;
import com.dfernandezaller.model.MeetingType;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import io.micronaut.core.convert.ConversionContext;
import io.micronaut.core.convert.TypeConverter;
import jakarta.inject.Singleton;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.util.Objects;
import java.util.Optional;

@Singleton
public class GCalendarEventToMeeting implements TypeConverter<Event, Meeting> {
    @Override
    public Optional<Meeting> convert(Event object, Class<Meeting> targetType, ConversionContext context) {
        return Optional.of(
                Meeting.builder()
                        .type(getEventType(object.getEventType(), object.getStatus()))
                        .isAllDay(getStartDateTime(object).isDateOnly())
                        .startTime(getStartTime(object))
                        .endTime(getEndTime(object))
                        .build()
        );
    }

    private static LocalDateTime getStartTime(Event object) {
        var startDateTime = getStartDateTime(object);

        if (startDateTime.isDateOnly()) {
            var instant = Instant.ofEpochMilli(startDateTime.getValue());
            return LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
        }

        return Instant.ofEpochMilli(startDateTime.getValue())
                .atZone(ZoneId.of(object.getStart().getTimeZone()))
                .toLocalDateTime();
    }

    private static LocalDateTime getEndTime(Event event) {
        var endDateTime = getEndDateTime(event);

        if (endDateTime.isDateOnly()) {
            var instant = Instant.ofEpochMilli(endDateTime.getValue()).minus(1, ChronoUnit.SECONDS);
            return LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
        }

        return Instant.ofEpochMilli(endDateTime.getValue())
                .atZone(ZoneId.of(event.getEnd().getTimeZone()))
                .toLocalDateTime();
    }

    private static DateTime getStartDateTime(Event object) {
        if (object.getStart().getDateTime() == null) {
            return object.getStart().getDate();
        }
        return object.getStart().getDateTime();
    }

    private static DateTime getEndDateTime(Event object) {
        if (object.getEnd().getDateTime() == null) {
            return object.getEnd().getDate();
        }
        return object.getEnd().getDateTime();
    }

    private MeetingType getEventType(String eventType, String eventStatus) {
        eventType = eventType == null ? "default" : eventType;
        return switch (eventType) {
            case "outOfOffice" -> MeetingType.OOO;
            case "default" -> {
                if (Objects.equals(eventStatus, "cancelled"))
                    yield MeetingType.CANCELLED;
                if (Objects.equals(eventStatus, "confirmed"))
                    yield MeetingType.ACCEPTED;
                if (Objects.equals(eventStatus, "tentative"))
                    yield MeetingType.TENTATIVE;
                yield MeetingType.OTHER;
            }
            default -> MeetingType.OTHER;
        };
    }
}
