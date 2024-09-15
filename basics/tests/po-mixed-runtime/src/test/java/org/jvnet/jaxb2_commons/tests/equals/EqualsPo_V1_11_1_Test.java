package org.jvnet.jaxb2_commons.tests.equals;

import org.junit.Assert;
import org.junit.Test;

public class EqualsPo_V1_11_1_Test {

    @Test
    public void testEquals() {
        generated.v1_11_1.Items.Item item = new generated.v1_11_1.Items.Item();
        item.setComment("a");


        generated.v1_11_1.Items.Item anotherItem = new generated.v1_11_1.Items.Item();
        anotherItem.setComment("a");

        Assert.assertEquals("equals doesnt work with 1.11.1 runtime", item, anotherItem);
    }

}
