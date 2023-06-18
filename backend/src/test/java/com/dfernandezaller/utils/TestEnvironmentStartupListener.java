package com.dfernandezaller.utils;

import io.micronaut.context.env.ActiveEnvironment;
import io.micronaut.context.env.PropertySource;
import io.micronaut.context.env.PropertySourceLoader;
import io.micronaut.core.io.ResourceLoader;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

public class TestEnvironmentStartupListener implements TestExecutionListener, PropertySourceLoader {
    private static volatile TestEnvironment testEnvironment;

    @Override
    public void testPlanExecutionStarted(TestPlan testPlan) {
        testEnvironment = new TestEnvironment();
        testEnvironment.startServices();
    }

    @Override
    public void testPlanExecutionFinished(TestPlan testPlan) {
        testEnvironment.stopServices();
    }

    @Override
    public Optional<PropertySource> load(String resourceName, ResourceLoader resourceLoader) {
        if (resourceName.equals("application")) {
            return Optional.of(
                    PropertySource.of(
                            Map.of(
                                    "datasources.default.url", testEnvironment.getPostgreSQLDbUrl(),
                                    "datasources.default.username", testEnvironment.getPostgreSQLDbUsername(),
                                    "datasources.default.password", testEnvironment.getPostgreSQLDbPassword()
                            )
                    )
            );
        }
        return Optional.empty();
    }

    @Override
    public Optional<PropertySource> loadEnv(String resourceName, ResourceLoader resourceLoader,
                                            ActiveEnvironment activeEnvironment) {
        return Optional.empty();
    }

    @Override
    public Map<String, Object> read(String name, InputStream input) {
        return null;
    }

}