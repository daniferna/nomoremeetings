package com.dfernandezaller.repository.entity;

import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalTime;

// TODO: 23/04/2023 Parametrize the default working hours and lunch time

@Data
@Entity
@Serdeable
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Builder(toBuilder = true)
public class User {

    @Id
    String email;
    String name;
    String urlPhoto;
    @Builder.Default
    LocalTime startWorkingTime = LocalTime.of(8, 0);
    @Builder.Default
    LocalTime endWorkingTime = LocalTime.of(17, 0);
    @Builder.Default
    LocalTime lunchTime = LocalTime.of(12, 0);

}


