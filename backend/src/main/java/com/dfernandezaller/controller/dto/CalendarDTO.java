package com.dfernandezaller.controller.dto;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;

@Serdeable
@Builder(toBuilder = true)
public record CalendarDTO(String name, String id, String backgroundColor, String description, String location,
                          boolean isPrimary) {
}
