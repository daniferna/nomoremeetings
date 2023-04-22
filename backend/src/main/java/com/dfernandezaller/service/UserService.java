package com.dfernandezaller.service;

import com.dfernandezaller.service.dto.UserDTO;

import java.util.Optional;

public interface UserService {

    Optional<UserDTO> getUser(String email);

}
