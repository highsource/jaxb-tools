package org.jvnet.jaxb.tests.camelcase;

import org.junit.Assert;
import org.junit.Test;

import generated.Address;

public class AddressTest {

    @Test
    public void testAddress() {
        Address a = new Address();
        // No plugin default-value present, checking everything is null or default java value
        Assert.assertEquals(0, a.getNumber());
        Assert.assertNull(a.getCareOf());
        Assert.assertNull(a.getStreet());
    }
}
