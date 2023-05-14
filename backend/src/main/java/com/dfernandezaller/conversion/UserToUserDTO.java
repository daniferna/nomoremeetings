package com.dfernandezaller.conversion;

import com.dfernandezaller.controller.dto.UserDTO;
import com.dfernandezaller.repository.entity.User;
import io.micronaut.core.convert.ConversionContext;
import io.micronaut.core.convert.TypeConverter;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class UserToUserDTO implements TypeConverter<User, UserDTO> {

    @Override
    public Optional<UserDTO> convert(User user, Class<UserDTO> targetType, ConversionContext context) {
        return Optional.of(UserDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .urlPhoto(user.getUrlPhoto())
                .startWorkingTime(user.getStartWorkingTime())
                .endWorkingTime(user.getEndWorkingTime())
                .startLunchTime(user.getStartLunchTime())
                .endLunchTime(user.getEndLunchTime())
                .timeBetweenMeetings(user.getTimeBetweenMeetings())
                .calendarName(user.getCalendarName())
                .build()
        );
    }

}
