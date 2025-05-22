package org.jvnet.jaxb.tests.propertylistenerinjector;

import org.junit.Assert;
import org.junit.Test;

import generated.Address;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

public class AddressTest {

    @Test
    public void testAddress() {
        List<PropertyChangeEvent> events = new ArrayList<>();

        Address a = new Address();
        a.setStreet("Dollar Lane");
        a.setCity("Los Angeles");

        a.addPropertyChangeListener("street", events::add);
        a.setStreet("Penny Lane");
        a.setCity("New York");
        Assert.assertEquals(1, events.size());
        PropertyChangeEvent e = events.get(0);
        Assert.assertEquals("Dollar Lane", e.getOldValue());
        Assert.assertEquals("Penny Lane", e.getNewValue());
        Assert.assertEquals("street", e.getPropertyName());
    }
}
