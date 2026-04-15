package org.jvnet.jaxb2_commons.tests.annoate_generated;

import generated.Address;
import my.MyGeneratedClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class AddressTest {

    @Test
    public void testAddress() {

        Assertions.assertNotNull(Address.class.getAnnotation(MyGeneratedClass.class), "Class should have @Generated annotation");

    }
}
