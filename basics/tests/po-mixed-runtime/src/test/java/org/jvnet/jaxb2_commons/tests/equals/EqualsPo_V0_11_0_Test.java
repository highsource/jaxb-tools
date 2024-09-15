package org.jvnet.jaxb2_commons.tests.equals;

import org.junit.Assert;
import org.junit.Test;

public class EqualsPo_V0_11_0_Test {

    @Test
    public void testEquals() {
        generated.v0_11_0.Items.Item item = new generated.v0_11_0.Items.Item();
        item.setComment("a");


        generated.v0_11_0.Items.Item anotherItem = new generated.v0_11_0.Items.Item();
        anotherItem.setComment("a");

        Assert.assertEquals("equals doesnt work with 0.11.0 runtime", item, anotherItem);
    }

}
