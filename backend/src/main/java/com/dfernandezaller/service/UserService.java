package com.dfernandezaller.service;

import com.dfernandezaller.controller.dto.UpdateUserTimesDTO;
import com.dfernandezaller.controller.dto.UserDTO;
import com.google.api.services.calendar.model.Calendar;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

public interface UserService {

    Optional<UserDTO> getUser(String email);

    Optional<UserDTO> updateUser(String email, UpdateUserTimesDTO requestDTO);

    Calendar getCalendar(String email) throws GeneralSecurityException, IOException;
}
