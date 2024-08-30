package org.jvnet.jaxb.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JT194Test {

    @Test
    public void testBindingOK() throws Exception {
        // With bindings we don't have PurchaseOrderType but PurchaseOrder and PurchaseOrderImpl
        Assertions.assertEquals(true, generated.PurchaseOrder.class.isInterface());
        Assertions.assertEquals(false, generated.impl.PurchaseOrderImpl.class.isInterface());
    }
}
