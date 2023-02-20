package com.dfernandezaller.authentication;

import java.util.Optional;

public record VerificationResult(boolean isValid, Optional<String> name, Optional<String> failureReason) {
}
