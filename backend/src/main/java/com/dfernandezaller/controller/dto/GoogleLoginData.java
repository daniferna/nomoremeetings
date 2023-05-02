package com.dfernandezaller.controller.dto;

import io.micronaut.serde.annotation.Serdeable;

import javax.validation.constraints.NotNull;

@Serdeable
public record GoogleLoginData(@NotNull String idToken) {
}
