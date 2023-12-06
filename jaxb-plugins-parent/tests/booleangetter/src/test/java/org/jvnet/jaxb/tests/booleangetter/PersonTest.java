package org.jvnet.jaxb.tests.booleangetter;

import org.junit.Assert;
import org.junit.Test;

import generated.Person;

public class PersonTest {

    @Test
    public void testPerson() {
        Person p = new Person();
        Assert.assertEquals(false, p.getMailingAddressIdentical());
    }
}
