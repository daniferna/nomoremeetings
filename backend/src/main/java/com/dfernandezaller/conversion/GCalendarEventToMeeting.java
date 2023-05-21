package com.dfernandezaller.conversion;

import com.dfernandezaller.model.Meeting;
import com.dfernandezaller.model.MeetingType;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import io.micronaut.core.convert.ConversionContext;
import io.micronaut.core.convert.TypeConverter;
import jakarta.inject.Singleton;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Optional;

@Singleton
public class GCalendarEventToMeeting implements TypeConverter<Event, Meeting> {
    @Override
    public Optional<Meeting> convert(Event object, Class<Meeting> targetType, ConversionContext context) {
        return Optional.of(
                Meeting.builder()
                        .type(
                                getEventType(object.getEventType(), object.getStatus())
                        )
                        .startTime(
                                Instant.ofEpochMilli(getStartDateTime(object).getValue())
                                        .atZone(ZoneId.of(object.getStart().getTimeZone()))
                                        .toLocalDateTime()
                        )
                        .endTime(
                                Instant.ofEpochMilli(getEndDateTime(object).getValue())
                                        .atZone(ZoneId.of(object.getEnd().getTimeZone()))
                                        .toLocalDateTime()
                        )
                        .build()
        );
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
        return switch (eventType) {
            case "outOfOffice" -> MeetingType.OOO;
            case null, "default" -> {
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
