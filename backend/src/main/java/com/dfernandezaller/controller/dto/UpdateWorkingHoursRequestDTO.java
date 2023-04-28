package com.dfernandezaller.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.micronaut.serde.annotation.Serdeable;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Serdeable
public record UpdateWorkingHoursRequestDTO(@NotNull @JsonFormat(pattern = "HH:mm") LocalTime startWorkingTime,
                                           @NotNull @JsonFormat(pattern = "HH:mm") LocalTime endWorkingTime,
                                           @NotNull @JsonFormat(pattern = "HH:mm") LocalTime lunchTime) {
}
