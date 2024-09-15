package org.jvnet.jaxb2_commons.tests.tostring;

import org.junit.Assert;
import org.junit.Test;

public class ToStringPo_V0_11_0_Test {

    @Test
    public void testToString() {
        generated.v0_11_0.Items.Item item = new generated.v0_11_0.Items.Item();
        item.setComment("a");

        Assert.assertEquals("toString doesnt work with 0.11.0 runtime",
            "generated.v0_11_0.Items$Item[productName=<null>(default), quantity=0, usPrice=<null>(default), comment=a, shipDate=<null>(default), partNum=<null>(default)]",
            item.toString());
    }

}
