package org.jvnet.jaxb2_commons.tests.v0_13_1;

import org.junit.Assert;
import org.junit.Test;

public class ToStringPo_V0_13_1_Test {

    @Test
    public void testToString() {
        generated.v0_13_1.Items.Item item = new generated.v0_13_1.Items.Item();
        item.setComment("a");

        Assert.assertEquals("toString doesnt work with 0.13.1 runtime",
            "generated.v0_13_1.Items$Item[productName=<null>(default), quantity=0, usPrice=<null>(default), comment=a, shipDate=<null>(default), partNum=<null>(default)]",
            item.toString());
    }

}
