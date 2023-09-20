package org.jvnet.jaxb2_commons.lang.tests.pojo;

public class CloneableNoClone implements Cloneable {
    private final String attribute;

    public CloneableNoClone(String attribute) {
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }
}
