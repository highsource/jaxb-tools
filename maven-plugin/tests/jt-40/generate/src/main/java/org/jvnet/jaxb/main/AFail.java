package org.jvnet.jaxb.main;

import b.B2Type;

/**
 * Class that will fail outside unit test since PurchaseOrderType in only in test-sources scope.
 */
public class AFail {

    public AFail() throws ClassNotFoundException {
        this(Class.forName("generated.PurchaseOrderType").getName());
    }

    public AFail(String value) {
        b = new B2Type();
        b.setB2(value);
    }

    private B2Type b;

    public B2Type getB() {
        return b;
    }

    public void setB(B2Type b) {
        this.b = b;
    }
}
