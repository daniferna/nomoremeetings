package com.dfernandezaller.authentication;

public interface TokenVerifier {

    VerificationResult verify(String token);

}
