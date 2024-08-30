package org.jvnet.jaxb2_commons.tests.defaultvalue;

import generated.Address;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddressTest {

    @Test
    public void testAddress() {
        Address a = new Address();
        Assertions.assertEquals(42, a.getNumber());
        Assertions.assertEquals("none", a.getCareOf());
        // no default value for street
        Assertions.assertNull(a.getStreet());
    }
}
