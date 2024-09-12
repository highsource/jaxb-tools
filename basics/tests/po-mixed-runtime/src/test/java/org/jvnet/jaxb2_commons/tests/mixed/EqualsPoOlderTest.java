package org.jvnet.jaxb2_commons.tests.mixed;

import org.junit.Assert;
import org.junit.Test;

public class EqualsPoOlderTest {

    @Test
    public void testEqualsOld() {
        generatedolder.Items.Item item = new generatedolder.Items.Item();
        item.setComment("a");


        generatedolder.Items.Item anotherItem = new generatedolder.Items.Item();
        anotherItem.setComment("a");

        Assert.assertEquals("equals doesnt work with 0.11.1 runtime in mixed env", item, anotherItem);
    }

}
