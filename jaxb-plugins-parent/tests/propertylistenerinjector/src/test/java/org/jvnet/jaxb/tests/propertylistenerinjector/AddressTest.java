package org.jvnet.jaxb.tests.propertylistenerinjector;

import generated.Address;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddressTest {

    @Test
    public void testAddress() {
        List<PropertyChangeEvent> events = new ArrayList<>();

        Address a = new Address();
        a.addPropertyChangeListener("street", events::add);
        a.setStreet("Penny Lane");
        Assertions.assertEquals(1, events.size());
        PropertyChangeEvent e = events.get(0);
        Assertions.assertEquals("Penny Lane", e.getNewValue());
        Assertions.assertEquals("street", e.getPropertyName());
    }
}
