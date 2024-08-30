package org.jvnet.jaxb.tests.parentpointer;

import generated.Address;
import generated.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddressTest {

    @Test
    public void testAddress() {
        Address a = new Address();
        a.setParent(new Person());
        Assertions.assertNotNull(a.getParent());
    }
}
