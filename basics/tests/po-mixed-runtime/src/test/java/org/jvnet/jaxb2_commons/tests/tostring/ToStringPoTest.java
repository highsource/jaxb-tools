package org.jvnet.jaxb2_commons.tests.tostring;

import org.junit.Assert;
import org.junit.Test;

public class ToStringPoTest {

    @Test
    public void testToString() {
        generated.Items.Item item = new generated.Items.Item();
        item.setComment("a");

        Assert.assertEquals("toString doesnt work with latest runtime",
            "generated.Items$Item[productName=<null>(default), quantity=0, usPrice=<null>(default), comment=a, shipDate=<null>(default), partNum=<null>(default)]",
            item.toString());
    }

}
