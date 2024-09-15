package org.jvnet.jaxb2_commons.tests.v1_11_1;

import org.junit.Assert;
import org.junit.Test;

public class ToStringPo_V1_11_1_Test {

    @Test
    public void testToString() {
        generated.v1_11_1.Items.Item item = new generated.v1_11_1.Items.Item();
        item.setComment("a");

        Assert.assertEquals("toString doesnt work with 1.11.1 runtime",
            "generated.v1_11_1.Items$Item[productName=<null>(default), quantity=0, usPrice=<null>(default), comment=a, shipDate=<null>(default), partNum=<null>(default)]",
            item.toString());
    }

}
