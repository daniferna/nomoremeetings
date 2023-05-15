package com.dfernandezaller.conversion;

import com.dfernandezaller.controller.dto.CalendarDTO;
import com.google.api.services.calendar.model.CalendarListEntry;
import io.micronaut.core.convert.ConversionContext;
import io.micronaut.core.convert.TypeConverter;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class GoogleCalendarListEntryToCalendarDTO implements TypeConverter<CalendarListEntry, CalendarDTO> {
    @Override
    public Optional<CalendarDTO> convert(CalendarListEntry calendar, Class<CalendarDTO> targetType, ConversionContext context) {
        return Optional.of(
                CalendarDTO.builder()
                        .name(calendar.getSummary())
                        .id(calendar.getId())
                        .backgroundColor(calendar.getBackgroundColor())
                        .description(calendar.getDescription())
                        .location(calendar.getLocation())
                        .isPrimary(calendar.getPrimary() != null && calendar.getPrimary())
                        .build()
        );
    }
}
