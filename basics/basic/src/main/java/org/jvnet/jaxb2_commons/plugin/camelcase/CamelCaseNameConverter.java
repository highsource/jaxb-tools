package org.jvnet.jaxb2_commons.plugin.camelcase;

import com.sun.xml.bind.api.impl.NameConverter;

/**
 * Name converter that unconditionally converts to camel case.
 */
class CamelCaseNameConverter extends NameConverter.Standard {

    /**
     * Changes the first character of the specified string to uppercase,
     * and the rest of characters to lowercase.
     *
     * @param
     *      s string to capitalize
     *
     * @return
     *      capitalized string
     */
    @Override
    public String capitalize(String s) {
        StringBuilder sb = new StringBuilder(s.length());
        sb.append(Character.toUpperCase(s.charAt(0)));
        sb.append(s.substring(1).toLowerCase());
        return sb.toString();
    }
}
