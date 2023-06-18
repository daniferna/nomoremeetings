package com.dfernandezaller.conversion;

import com.dfernandezaller.controller.dto.UserDTO;
import com.dfernandezaller.repository.entity.User;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
public class UserDTOToUserTest {

    @Inject
    UserDTOToUser userDTOToUser;

    @Test
    public void testConvert() {
        UserDTO userDTO = new UserDTO(
                "test@test.com",
                "Test",
                "url",
                LocalTime.of(6,0),
                LocalTime.of(15,0),
                LocalTime.of(13,0),
                LocalTime.of(14,0),
                10,
                "testId",
                22
        );

        User user = userDTOToUser.convert(userDTO, User.class).orElse(null);

        assertThat(user).usingRecursiveComparison().isEqualTo(userDTO);
    }
}
