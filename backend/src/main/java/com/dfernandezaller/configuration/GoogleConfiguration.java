package com.dfernandezaller.configuration;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;

@Data
@Serdeable
@ConfigurationProperties("google")
public class GoogleConfiguration {
    private String clientId;
    private String clientSecret;

}
