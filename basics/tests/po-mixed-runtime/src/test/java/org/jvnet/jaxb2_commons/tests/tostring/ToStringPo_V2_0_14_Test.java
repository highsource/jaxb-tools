package org.jvnet.jaxb2_commons.tests.tostring;

import org.junit.Assert;
import org.junit.Test;

public class ToStringPo_V2_0_14_Test {

    @Test
    public void testToString() {
        generated.v2_0_14.Items.Item item = new generated.v2_0_14.Items.Item();
        item.setComment("a");

        Assert.assertEquals("toString doesnt work with 2.0.14 runtime",
            "generated.v2_0_14.Items$Item[productName=<null>(default), quantity=0, usPrice=<null>(default), comment=a, shipDate=<null>(default), partNum=<null>(default)]",
            item.toString());
    }

}
