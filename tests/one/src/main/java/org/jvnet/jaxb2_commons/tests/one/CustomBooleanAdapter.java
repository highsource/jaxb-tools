package org.jvnet.jaxb2_commons.tests.one;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class CustomBooleanAdapter extends XmlAdapter<String, Boolean> {
    @Override
    public Boolean unmarshal(String s) {
        if("true".equals(s) || "false".equals(s)){
            return Boolean.valueOf(s);
        }
        return null;

    }

    @Override
    public String marshal(Boolean c) {
        return c == null ? null : c.toString();
    }
}