package org.jvnet.jaxb2_commons.lang;

import org.jvnet.jaxb2_commons.locator.ObjectLocator;

/**
 * Original submission by Kevin Senechal (kevin.senechal@dooapp.com) on 30/07/15.
 */
public class EnumToStringStrategy extends JAXBToStringStrategy {

    public StringBuilder appendStart(ObjectLocator parentLocator, Object parent, StringBuilder stringBuilder) {
        if (parent instanceof Enum) {
            return stringBuilder;
        } else {
            return super.appendStart(parentLocator, parent, stringBuilder);
        }
    }

    public StringBuilder appendEnd(ObjectLocator parentLocator, Object parent, StringBuilder stringBuilder) {
        if (parent instanceof Enum) {
            return appendEnum((Enum) parent, stringBuilder);
        } else {
            return super.appendEnd(parentLocator, parent, stringBuilder);
        }
    }

    protected StringBuilder appendEnum(Enum e, StringBuilder stringBuilder) {
        stringBuilder.append(e.name());
        return stringBuilder;
    }

}
