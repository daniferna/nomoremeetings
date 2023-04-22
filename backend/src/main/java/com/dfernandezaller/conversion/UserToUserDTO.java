package com.dfernandezaller.conversion;

import com.dfernandezaller.repository.entity.User;
import com.dfernandezaller.service.dto.UserDTO;
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
                .build()
        );
    }

}
