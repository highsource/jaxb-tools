package org.jvnet.jaxb.test;

import org.junit.Assert;
import org.junit.Test;

public class JT194Test {

    @Test
    public void testBindingOK() throws Exception {
        // With bindings we don't have PurchaseOrderType but PurchaseOrder and PurchaseOrderImpl
        Assert.assertEquals(true, generated.PurchaseOrder.class.isInterface());
        Assert.assertEquals(false, generated.impl.PurchaseOrderImpl.class.isInterface());
    }
}
