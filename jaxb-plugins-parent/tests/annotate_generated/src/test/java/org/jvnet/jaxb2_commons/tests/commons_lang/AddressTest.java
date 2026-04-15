package org.jvnet.jaxb2_commons.tests.commons_lang;

import generated.Address;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvnet.jaxb.xml.bind.Generated;


public class AddressTest {

    @Test
    public void testAddress() {

        Assertions.assertNotNull(Address.class.getAnnotation(Generated.class), "Class should have @Generated annotation");

    }
}
