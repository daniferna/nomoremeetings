package com.dfernandezaller;

import io.micronaut.context.ApplicationContextBuilder;
import io.micronaut.context.ApplicationContextConfigurer;
import io.micronaut.context.annotation.ContextConfigurer;
import io.micronaut.core.annotation.NonNull;
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

    @ContextConfigurer
    public static class Configurer implements ApplicationContextConfigurer {
        @Override
        public void configure(@NonNull ApplicationContextBuilder builder) {
            builder.defaultEnvironments("dev");
        }
    }
    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}