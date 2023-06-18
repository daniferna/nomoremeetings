package com.dfernandezaller.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class TestEnvironment {
    private static final Logger LOG = LoggerFactory.getLogger(TestEnvironment.class);
    private final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:15-alpine3.17"));

    public String getPostgreSQLDbUrl() {
        return postgreSQLContainer.getJdbcUrl();
    }

    public String getPostgreSQLDbUsername() {
        return postgreSQLContainer.getUsername();
    }

    public String getPostgreSQLDbPassword() {
        return postgreSQLContainer.getPassword();
    }

    public void startServices() {
        postgreSQLContainer.start();
        LOG.info("Started PostgreSQL container on port {}", postgreSQLContainer.getFirstMappedPort());
    }

    public void stopServices() {
        LOG.info("Stopping all services...");
        postgreSQLContainer.stop();
        LOG.info("Stopped all services");
    }
}