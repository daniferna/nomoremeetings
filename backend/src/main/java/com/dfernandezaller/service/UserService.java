package com.dfernandezaller.service;

import com.dfernandezaller.controller.dto.CalendarDTO;
import com.dfernandezaller.controller.dto.UpdateUserDTO;
import com.dfernandezaller.controller.dto.UserDTO;
import com.google.api.services.calendar.model.Calendar;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserDTO> getUser(String email);

    Optional<UserDTO> updateUser(String email, UpdateUserDTO requestDTO);

    Calendar getCalendar(String email) throws GeneralSecurityException, IOException;

    List<CalendarDTO> getUserCalendars(String name);
}
