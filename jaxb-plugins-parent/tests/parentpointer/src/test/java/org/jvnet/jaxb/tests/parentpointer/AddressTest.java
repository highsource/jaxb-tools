package org.jvnet.jaxb.tests.parentpointer;

import generated.Person;
import org.junit.Assert;
import org.junit.Test;

import generated.Address;

public class AddressTest {

    @Test
    public void testAddress() {
        Address a = new Address();
        a.setParent(new Person());
        Assert.assertNotNull(a.getParent());
    }
}
