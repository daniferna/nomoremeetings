package com.dfernandezaller.conversion;

import com.dfernandezaller.controller.dto.CalendarDTO;
import com.google.api.services.calendar.model.CalendarListEntry;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
public class GoogleCalendarListEntryToCalendarDTOTest {

    @Inject
    GoogleCalendarListEntryToCalendarDTO googleCalendarListEntryToCalendarDTO;

    @Test
    public void testConvert() {
        CalendarListEntry calendarListEntry = new CalendarListEntry();
        calendarListEntry.setSummary("Test Calendar");
        calendarListEntry.setId("testId");
        calendarListEntry.setBackgroundColor("#ffffff");
        calendarListEntry.setDescription("Test Description");
        calendarListEntry.setLocation("Test Location");
        calendarListEntry.setPrimary(true);

        CalendarDTO calendarDTO = googleCalendarListEntryToCalendarDTO.convert(calendarListEntry, CalendarDTO.class).orElse(null);

        assertThat(calendarDTO)
                .isEqualTo(
                        CalendarDTO.builder()
                                .name("Test Calendar")
                                .id("testId")
                                .backgroundColor("#ffffff")
                                .description("Test Description")
                                .location("Test Location")
                                .isPrimary(true)
                                .build()
                );
    }
}
