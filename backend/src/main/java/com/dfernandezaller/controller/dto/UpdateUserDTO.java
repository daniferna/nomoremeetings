package com.dfernandezaller.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.micronaut.serde.annotation.Serdeable;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.Optional;

@Serdeable
public record UpdateUserDTO(@NotNull @JsonFormat(pattern = "HH:mm") LocalTime startWorkingTime,
                            @NotNull @JsonFormat(pattern = "HH:mm") LocalTime endWorkingTime,
                            @NotNull @JsonFormat(pattern = "HH:mm") LocalTime startLunchTime,
                            @NotNull @JsonFormat(pattern = "HH:mm") LocalTime endLunchTime,
                            Optional<Integer> timeBetweenMeetings,
                            Optional<String> calendarId) {
}
