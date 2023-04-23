package com.dfernandezaller.conversion;

import com.dfernandezaller.controller.dto.UserDTO;
import com.dfernandezaller.repository.entity.User;
import io.micronaut.core.convert.ConversionContext;
import io.micronaut.core.convert.TypeConverter;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class UserDTOToUser implements TypeConverter<UserDTO, User> {
    @Override
    public Optional<User> convert(UserDTO userDTO, Class<User> targetType, ConversionContext context) {
        return Optional.of(User.builder()
                .email(userDTO.email())
                .name(userDTO.name())
                .urlPhoto(userDTO.urlPhoto())
                .startWorkingTime(userDTO.startWorkingTime())
                .endWorkingTime(userDTO.endWorkingTime())
                .lunchTime(userDTO.lunchTime())
                .build()
        );
    }
}
