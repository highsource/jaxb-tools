package org.jvnet.jaxb.tests.propertylistenerinjector;

import org.junit.Test;

import generated.Address;

public class AddressTest {

    @Test
    public void testAddress() {
        Address a = new Address();
        // just checking new methods have been added
        a.addPropertyChangeListener(null);
        a.removePropertyChangeListener(null);
    }
}
