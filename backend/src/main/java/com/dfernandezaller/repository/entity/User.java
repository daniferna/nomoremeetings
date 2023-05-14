package com.dfernandezaller.repository.entity;

import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Data
@Entity
@Serdeable
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Builder(toBuilder = true)
public class User {

    @Id
    @NotNull
    String email;
    @NotNull
    String name;
    String urlPhoto;
    @Builder.Default
    LocalTime startWorkingTime = LocalTime.of(8, 0);
    @Builder.Default
    LocalTime endWorkingTime = LocalTime.of(17, 0);
    @Builder.Default
    LocalTime startLunchTime = LocalTime.of(13, 0);
    @Builder.Default
    LocalTime endLunchTime = LocalTime.of(14, 0);
    @Builder.Default
    int timeBetweenMeetings = 15;
    @Builder.Default
    String calendarName = "primary";

}


