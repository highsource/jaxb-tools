package org.jvnet.jaxb.main;

import b.B2Type;

/**
 * Class that will always success since B2Type in sources scope.
 */
public class ASuccess {

    public ASuccess() {
        this("OK");
    }

    public ASuccess(String value) {
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
