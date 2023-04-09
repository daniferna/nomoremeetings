package com.dfernandezaller.controller.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record GoogleSignupData(String codeToken, String idToken) {
}
