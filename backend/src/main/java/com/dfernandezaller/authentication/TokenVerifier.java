package com.dfernandezaller.authentication;

public interface TokenVerifier {

    public VerificationResult verify(String token);

}
