package com.dfernandezaller.service.dto;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;

@Serdeable
@Builder(toBuilder = true)
public record UserDTO(String email, String name, String urlPhoto) {
}