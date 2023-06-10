package com.dfernandezaller.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;

import java.time.LocalDateTime;

@Serdeable
@Builder(toBuilder = true)
public record Meeting(MeetingType type, @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
                      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime, boolean isAllDay) {
}
