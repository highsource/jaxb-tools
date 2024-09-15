package org.jvnet.jaxb2_commons.tests.v0_13_1;

import org.junit.Assert;
import org.junit.Test;

public class EqualsPo_V0_13_1_Test {

    @Test
    public void testEqualsOld() {
        generated.v0_13_1.Items.Item item = new generated.v0_13_1.Items.Item();
        item.setComment("a");


        generated.v0_13_1.Items.Item anotherItem = new generated.v0_13_1.Items.Item();
        anotherItem.setComment("a");

        Assert.assertEquals("equals doesnt work with 0.13.1 runtime", item, anotherItem);
    }

}
