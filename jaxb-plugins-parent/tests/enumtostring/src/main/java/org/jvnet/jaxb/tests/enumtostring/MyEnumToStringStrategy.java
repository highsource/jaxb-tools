package org.jvnet.jaxb.tests.enumtostring;

import generated.Model;
import org.jvnet.jaxb.lang.EnumToStringStrategy;

public class MyEnumToStringStrategy extends EnumToStringStrategy {
    @Override
    protected StringBuilder appendEnum(Enum e, StringBuilder stringBuilder) {
        if (e instanceof Model) {
            stringBuilder.append(((Model) e).value());
            return stringBuilder;
        }
        return super.appendEnum(e, stringBuilder);
    }
}
