package com.dfernandezaller.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record Credential(@JsonProperty("credential") String credential) {
    @Override
    public String toString() {
        return "Credential{" +
                "credential='" + credential + '\'' +
                '}';
    }
}