package com.dfernandezaller.service;

import com.dfernandezaller.controller.dto.UpdateWorkingHoursRequestDTO;
import com.dfernandezaller.controller.dto.UserDTO;

import java.util.Optional;

public interface UserService {

    Optional<UserDTO> getUser(String email);

    boolean updateUser(String email, UpdateWorkingHoursRequestDTO requestDTO);
}
