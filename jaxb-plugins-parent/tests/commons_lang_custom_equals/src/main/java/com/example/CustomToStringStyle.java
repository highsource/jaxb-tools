package com.example;

import org.apache.commons.lang3.builder.ToStringStyle;

public class CustomToStringStyle extends ToStringStyle {

    public static final ToStringStyle CUSTOM_TO_STRING_STYLE = new CustomToStringStyle();


    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new instance.
     *
     * <p>Use the static constant rather than instantiating.</p>
     */
    public CustomToStringStyle() {
        setUseIdentityHashCode(false);
        setContentStart("[");
        setFieldSeparator(System.lineSeparator() + "  ");
        setFieldSeparatorAtStart(true);
        setContentEnd(System.lineSeparator() + "]");
    }

    /**
     * Ensure <code>Singleton</ode> after serialization.
     * @return the singleton
     */
    private Object readResolve() {
        return CUSTOM_TO_STRING_STYLE;
    }

}
