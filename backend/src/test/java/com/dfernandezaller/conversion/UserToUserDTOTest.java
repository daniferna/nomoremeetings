package com.dfernandezaller.conversion;

import com.dfernandezaller.controller.dto.UserDTO;
import com.dfernandezaller.repository.entity.User;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
public class UserToUserDTOTest {

    @Inject
    UserToUserDTO userToUserDTO;

    @Test
    public void testConvert() {
        User user = User.builder()
                .email("test@test.com")
                .name("Test")
                .urlPhoto("url")
                .startWorkingTime(LocalTime.of(6,0))
                .endWorkingTime(LocalTime.of(15,0))
                .startLunchTime(LocalTime.of(13,0))
                .endLunchTime(LocalTime.of(14,0))
                .timeBetweenMeetings(10)
                .calendarId("testId")
                .daysToAnalyze(22)
                .build();

        UserDTO userDTO = userToUserDTO.convert(user, UserDTO.class).orElse(null);

        assertThat(userDTO).usingRecursiveComparison().isEqualTo(user);
    }
}
