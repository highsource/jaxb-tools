package com.example.customerservice.service.test;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

import com.example.customerservice.service.DeleteCustomerById;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JAXBContextTest {

    public static final String CONTEXT_PATH = DeleteCustomerById.class.getPackage().getName();

    @Test
    public void successfullyCreatesMarshallerAndUnmarshaller() throws JAXBException {
        final JAXBContext context = JAXBContext.newInstance(CONTEXT_PATH);
        Assertions.assertNotNull(context.createMarshaller());
        Assertions.assertNotNull(context.createUnmarshaller());
    }

}
