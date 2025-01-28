package com.example.customerservice.service.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ServiceTest {

    @Test
    public void successfullyCreatedServiceClasses() throws ClassNotFoundException {
        Assertions.assertNotNull(Class.forName("com.example.customerservice.service.DeleteCustomerById"));
        Assertions.assertNotNull(Class.forName("com.example.customerservice.service.GetCustomerById"));
        Assertions.assertNotNull(Class.forName("com.example.customerservice.service.GetCustomerByIdResponse"));
        Assertions.assertNotNull(Class.forName("com.example.customerservice.service.NoSuchCustomer"));
        Assertions.assertNotNull(Class.forName("com.example.customerservice.service.UpdateCustomer"));
        Assertions.assertNotNull(Class.forName("com.example.customerservice.service.UpdateCustomerResponse"));
    }

}
