package com.dfernandezaller.service;

import com.dfernandezaller.controller.dto.CalendarDTO;
import com.dfernandezaller.controller.dto.UpdateUserDTO;
import com.dfernandezaller.controller.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserDTO> getUser(String email);

    Optional<UserDTO> updateUser(String email, UpdateUserDTO requestDTO);
    
    List<CalendarDTO> getUserCalendars(String name);
}
