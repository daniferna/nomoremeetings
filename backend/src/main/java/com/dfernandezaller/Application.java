package com.dfernandezaller;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "nomoremeetings-backend",
                version = "0.0",
                description = "Backend for NoMoreMeetings"
        )
)
// TODO: 20/02/2023 Improve documentation of controllers and endpoints to generate better OpenAPI documentation
public class Application {
    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}