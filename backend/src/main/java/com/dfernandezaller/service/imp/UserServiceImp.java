package com.dfernandezaller.service.imp;

import com.dfernandezaller.controller.dto.UserDTO;
import com.dfernandezaller.repository.UserRepository;
import com.dfernandezaller.service.UserService;
import io.micronaut.core.convert.ConversionService;
import jakarta.inject.Singleton;

import javax.transaction.Transactional;
import java.util.Optional;

@Singleton
@Transactional
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final ConversionService<?> conversionService;

    public UserServiceImp(UserRepository userRepository, ConversionService<?> conversionService) {
        this.userRepository = userRepository;
        this.conversionService = conversionService;
    }

    @Override
    public Optional<UserDTO> getUser(String email) {
        return conversionService.convert(userRepository.findById(email).orElse(null), UserDTO.class);
    }

}
