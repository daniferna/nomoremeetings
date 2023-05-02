package com.dfernandezaller.authentication.google.dto;

import com.dfernandezaller.authentication.VerificationResult;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Serdeable
@EqualsAndHashCode(callSuper = true)
public class GoogleVerificationResult extends VerificationResult {

    GoogleIdToken.Payload payload;

    public GoogleVerificationResult(boolean isValid, String failureReason, GoogleIdToken.Payload payload) {
        super(isValid, failureReason);
        this.payload = payload;
    }

}
