package org.jvnet.jaxb2_commons.tests.equals;

import org.junit.Assert;
import org.junit.Test;

public class EqualsPoTest {

    @Test
    public void testEquals() {
        generated.Items.Item item = new generated.Items.Item();
        item.setComment("a");


        generated.Items.Item anotherItem = new generated.Items.Item();
        anotherItem.setComment("a");

        Assert.assertEquals("equals doesnt work with latest runtime", item, anotherItem);
    }

}
