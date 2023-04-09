package com.dfernandezaller.authentication;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface TokenVerifier {

    VerificationResult verify(String token) throws GeneralSecurityException, IOException;

}
