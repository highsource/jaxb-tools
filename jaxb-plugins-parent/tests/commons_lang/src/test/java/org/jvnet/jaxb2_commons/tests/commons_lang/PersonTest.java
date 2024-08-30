package org.jvnet.jaxb2_commons.tests.commons_lang;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import generated.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PersonTest {

    @Test
    public void testPerson() {
        Person p = new Person();
        // No plugin default-value present, checking everything is null or default java value
        Assertions.assertEquals(false, p.isMailingAddressIdentical());

        Assertions.assertEquals(ToStringBuilder.reflectionToString(p, ToStringStyle.MULTI_LINE_STYLE), p.toString());
    }
}
