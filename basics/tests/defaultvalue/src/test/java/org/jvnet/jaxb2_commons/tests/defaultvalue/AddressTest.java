package org.jvnet.jaxb2_commons.tests.defaultvalue;

import org.junit.Assert;
import org.junit.Test;

import generated.Address;

public class AddressTest {

    @Test
    public void testAddress() {
        Address a = new Address();
        Assert.assertEquals(42, a.getNumber());
        Assert.assertEquals("none", a.getCareOf());
        // no default value for street
        Assert.assertNull(a.getStreet());
    }
}
