package com.dfernandezaller.authentication;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Serdeable
@NoArgsConstructor
public abstract class VerificationResult {

    private boolean isValid;
    private String failureReason;

    protected VerificationResult(boolean isValid, String failureReason) {
        this.isValid = isValid;
        this.failureReason = failureReason;
    }

}
