package com.dfernandezaller.model;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record TimeAnalysisResults(double busyHours, double tentativeHours, double oooHours, double totalWorkTime) {
}