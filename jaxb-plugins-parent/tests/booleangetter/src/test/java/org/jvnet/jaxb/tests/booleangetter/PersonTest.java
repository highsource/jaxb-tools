package org.jvnet.jaxb.tests.booleangetter;

import generated.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PersonTest {

    @Test
    public void testPerson() {
        Person p = new Person();
        Assertions.assertEquals(false, p.getMailingAddressIdentical());
    }
}
