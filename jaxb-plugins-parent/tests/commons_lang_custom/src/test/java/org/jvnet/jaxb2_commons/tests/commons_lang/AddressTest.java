package org.jvnet.jaxb2_commons.tests.commons_lang;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import generated.Address;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddressTest {

    @Test
    public void testAddress() {
        Address a = new Address();
        // No plugin default-value present, checking everything is null or default java value
        Assertions.assertEquals(0, a.getNumber());
        Assertions.assertNull(a.getCareOf());
        Assertions.assertNull(a.getStreet());

        Assertions.assertEquals(ToStringBuilder.reflectionToString(a, ToStringStyle.SIMPLE_STYLE), a.toString());
    }
}
