package org.jvnet.jaxb.test;

import org.junit.Assert;
import org.junit.Test;
import org.jvnet.jaxb.main.AFail;
import org.jvnet.jaxb.main.ASuccess;

public class JT40Test {

    @Test
    public void testGeneratedTestSourcesNoClassRefTestInMain() throws Exception {
        ASuccess a = new ASuccess();
        Assert.assertEquals("OK", a.getB().getB2());

        ASuccess aOther = new ASuccess("OK Too");
        Assert.assertEquals("OK Too", aOther.getB().getB2());
    }

    @Test
    public void testGeneratedTestSourcesClassRefTestInMain() throws Exception {
        try {
            AFail a = new AFail();
            Assert.assertEquals(generated.PurchaseOrderType.class.getName(), a.getB().getB2());
        } catch (ClassNotFoundException e) {
            Assert.fail("Exception thrown (but should not)");
        }

        AFail aOther = new AFail("OK");
        Assert.assertEquals("OK", aOther.getB().getB2());
    }
}
