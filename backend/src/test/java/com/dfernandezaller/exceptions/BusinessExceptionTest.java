package com.dfernandezaller.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BusinessExceptionTest {

    @Test
    public void testBusinessExceptionMessage() {
        Exception exception = assertThrows(BusinessException.class, () -> {
            throw new BusinessException("Error de negocio");
        });

        assertEquals("Error de negocio", exception.getMessage());
    }

    @Test
    public void testBusinessExceptionCause() {
        Exception cause = new RuntimeException("causa");
        Exception exception = assertThrows(BusinessException.class, () -> {
            throw new BusinessException("Error de negocio", cause);
        });

        assertEquals("Error de negocio", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
