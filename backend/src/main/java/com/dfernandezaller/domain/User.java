package com.dfernandezaller.domain;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    private UUID id;
    private String refreshToken;
    private ZonedDateTime refreshTokenExpirationDate;
    private ZonedDateTime startWorkTime;
    private ZonedDateTime endWorkTime;

}
