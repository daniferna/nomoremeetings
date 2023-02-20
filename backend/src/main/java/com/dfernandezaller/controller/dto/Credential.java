package com.dfernandezaller.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record Credential(
        @JsonProperty("code") String code,
        @JsonProperty("scope") String scope,
        @JsonProperty("authuser") String authuser,
        @JsonProperty("prompt") String prompt
) {
    @Override
    public String toString() {
        return "Credential{" +
                "code='" + code + '\'' +
                ", scope='" + scope + '\'' +
                ", authuser='" + authuser + '\'' +
                ", prompt='" + prompt + '\'' +
                '}';
    }
}