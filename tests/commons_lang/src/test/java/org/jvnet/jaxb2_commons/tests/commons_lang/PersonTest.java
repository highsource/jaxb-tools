package org.jvnet.jaxb2_commons.tests.commons_lang;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Assert;
import org.junit.Test;

import generated.Person;

public class PersonTest {

    @Test
    public void testPerson() {
        Person p = new Person();
        // No plugin default-value present, checking everything is null or default java value
        Assert.assertEquals(false, p.isMailingAddressIdentical());
        
        Assert.assertEquals(ToStringBuilder.reflectionToString(p, ToStringStyle.MULTI_LINE_STYLE), p.toString());
    }
}