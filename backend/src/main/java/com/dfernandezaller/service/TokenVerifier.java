package com.dfernandezaller.service;

import com.dfernandezaller.authentication.google.dto.GoogleVerificationResult;

public interface TokenVerifier {

    GoogleVerificationResult verify(String token);

}
