package org.jvnet.jaxb2_commons.tests.mixed;

import org.junit.Assert;
import org.junit.Test;

public class EqualsPoOldTest {

    @Test
    public void testEqualsOld() {
        generatedold.Items.Item item = new generatedold.Items.Item();
        item.setComment("a");


        generatedold.Items.Item anotherItem = new generatedold.Items.Item();
        anotherItem.setComment("a");

        Assert.assertEquals("equals doesnt work with 0.13.1 runtime in mixed env", item, anotherItem);
    }

}
