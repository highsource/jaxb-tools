package org.jvnet.jaxb2_commons.tests.defaultvalue;

import generated.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PersonTest {

    @Test
    public void testPerson() {
        Person p = new Person();
        Assertions.assertEquals(true, p.isMailingAddressIdentical());
    }
}
