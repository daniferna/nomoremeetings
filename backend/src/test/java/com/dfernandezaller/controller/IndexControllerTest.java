package com.dfernandezaller.controller;

import io.micronaut.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IndexControllerTest {

    private final IndexController indexController = new IndexController();

    @Test
    void index() {
        var result = indexController.index();

        assertEquals(HttpStatus.OK, result.getStatus());
    }

}