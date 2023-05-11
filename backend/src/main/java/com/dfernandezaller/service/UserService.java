package com.dfernandezaller.service;

import com.dfernandezaller.controller.dto.UpdateUserTimesDTO;
import com.dfernandezaller.controller.dto.UserDTO;

import java.util.Optional;

public interface UserService {

    Optional<UserDTO> getUser(String email);

    Optional<UserDTO> updateUser(String email, UpdateUserTimesDTO requestDTO);
}
