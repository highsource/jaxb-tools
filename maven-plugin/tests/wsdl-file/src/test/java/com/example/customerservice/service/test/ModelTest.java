package com.example.customerservice.service.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ModelTest {

    @Test
    public void successfullyCreatedModelClasses() throws ClassNotFoundException {
        Assertions.assertNotNull(Class.forName("com.example.customerservice.model.Customer"));
        Assertions.assertNotNull(Class.forName("com.example.customerservice.model.CustomerType"));
    }

}
