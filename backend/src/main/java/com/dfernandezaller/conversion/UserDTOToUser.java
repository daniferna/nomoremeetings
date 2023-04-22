package com.dfernandezaller.conversion;

import com.dfernandezaller.repository.entity.User;
import com.dfernandezaller.service.dto.UserDTO;
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
                .build()
        );
    }
}
