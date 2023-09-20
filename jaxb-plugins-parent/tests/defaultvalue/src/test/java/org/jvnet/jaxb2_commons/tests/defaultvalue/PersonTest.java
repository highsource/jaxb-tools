package org.jvnet.jaxb2_commons.tests.defaultvalue;

import org.junit.Assert;
import org.junit.Test;

import generated.Person;

public class PersonTest {

    @Test
    public void testPerson() {
        Person p = new Person();
        Assert.assertEquals(true, p.isMailingAddressIdentical());
    }
}
