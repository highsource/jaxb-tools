package org.jvnet.jaxb2_commons.tests.fakeolder;

import org.junit.Assert;
import org.junit.Test;

public class EqualsPoFakeOlderTest {

    @Test
    public void testEqualsFakeOlder() {
        generatedfakeolder.Items.Item item = new generatedfakeolder.Items.Item();
        item.setComment("a");


        generatedfakeolder.Items.Item anotherItem = new generatedfakeolder.Items.Item();
        anotherItem.setComment("a");

        Assert.assertEquals("equals doesnt work with 1.11.1 runtime", item, anotherItem);
    }

}
