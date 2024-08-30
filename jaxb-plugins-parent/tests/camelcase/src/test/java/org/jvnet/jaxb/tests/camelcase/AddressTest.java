package org.jvnet.jaxb.tests.camelcase;

import generated.Address;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddressTest {

    @Test
    public void testAddress() {
        Address a = new Address();
        // No plugin default-value present, checking everything is null or default java value
        Assertions.assertEquals(0, a.getNumber());
        Assertions.assertNull(a.getCareOf());
        Assertions.assertNull(a.getStreet());
    }
}
