package org.jvnet.jaxb2_commons.tests.v2_0_14;

import org.junit.Assert;
import org.junit.Test;

public class EqualsPo_V2_0_14_Test {

    @Test
    public void testEqualsOld() {
        generated.v2_0_14.Items.Item item = new generated.v2_0_14.Items.Item();
        item.setComment("a");


        generated.v2_0_14.Items.Item anotherItem = new generated.v2_0_14.Items.Item();
        anotherItem.setComment("a");

        Assert.assertEquals("equals doesnt work with 2.0.14 runtime", item, anotherItem);
    }

}
