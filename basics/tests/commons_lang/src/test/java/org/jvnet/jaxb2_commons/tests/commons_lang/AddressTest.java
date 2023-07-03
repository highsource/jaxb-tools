package org.jvnet.jaxb2_commons.tests.commons_lang;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Assert;
import org.junit.Test;

import generated.Address;

public class AddressTest {

    @Test
    public void testAddress() {
        Address a = new Address();
        // No plugin default-value present, checking everything is null or default java value
        Assert.assertEquals(0, a.getNumber());
        Assert.assertNull(a.getCareOf());
        Assert.assertNull(a.getStreet());
        
        Assert.assertEquals(ToStringBuilder.reflectionToString(a, ToStringStyle.MULTI_LINE_STYLE), a.toString());
    }
}