package internal.v0_13_1;

import org.jvnet.jaxb2_commons.lang.JAXBToStringStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;

public class EnumToStringStrategy extends JAXBToStringStrategy {

    public boolean isUseIdentityHashCode() {
        return false;
    }

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
