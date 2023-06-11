package com.dfernandezaller.service.imp;

import com.dfernandezaller.controller.dto.UpdateUserDTO;
import com.dfernandezaller.repository.UserRepository;
import com.dfernandezaller.service.UserService;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest(environments = "test")
class UserServiceImpTest {

    @Inject
    UserRepository userRepository;

    @Inject
    UserService userService;

    @Test
    void shouldGetUser() {
        assertTrue(userService.getUser("test1@gmail.com").isPresent());
    }

    @Test
    void shouldNotGetUser() {
        assertTrue(userService.getUser("nonExistingUser@gmail.com").isEmpty());
    }

    @Test
    void shouldUpdateUser() {
        var updatedUserDTO = UpdateUserDTO.builder()
                .startWorkingTime(LocalTime.parse("02:00"))
                .endWorkingTime(LocalTime.parse("18:00"))
                .startLunchTime(LocalTime.parse("16:00"))
                .endLunchTime(LocalTime.parse("17:00"))
                .timeBetweenMeetings(Optional.of(5))
                .calendarId(Optional.of("modifiedCalendarId"))
                .daysToAnalyze(Optional.of(2))
                .build();

        var updatedUser = userService.updateUser("test1@gmail.com", updatedUserDTO);

        assertTrue(updatedUser.isPresent());
        assertEquals(updatedUserDTO.timeBetweenMeetings().orElseThrow(), updatedUser.get().timeBetweenMeetings());
        assertEquals(updatedUserDTO.calendarId().orElseThrow(), updatedUser.get().calendarId());
        assertEquals(updatedUserDTO.daysToAnalyze().orElseThrow(), updatedUser.get().daysToAnalyze());
        assertThat(updatedUserDTO)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .ignoringFieldsOfTypes(Optional.class)
                .isEqualTo(updatedUser.get());
    }

    @Test
    void getUserCalendars() {
        // TODO Complex test because need to mock Google Calendar API
    }
}