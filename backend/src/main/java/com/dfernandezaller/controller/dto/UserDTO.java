package com.dfernandezaller.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;

import java.time.LocalTime;

@Serdeable
@Builder(toBuilder = true)
public record UserDTO(String email, String name, String urlPhoto,
                      @JsonFormat(pattern = "HH:mm") LocalTime startWorkingTime,
                      @JsonFormat(pattern = "HH:mm") LocalTime endWorkingTime,
                      @JsonFormat(pattern = "HH:mm") LocalTime lunchTime) {
}