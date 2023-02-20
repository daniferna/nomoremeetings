package com.dfernandezaller.authentication.google;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GoogleUser(String name, String email, String picture) {
}
