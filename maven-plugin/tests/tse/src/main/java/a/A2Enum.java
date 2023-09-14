package a;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


@XmlType(name = "A2EnumType", namespace = "a")
@XmlEnum
public enum A2Enum {

    ABC,
    DEF,
    GHI;

    public String value() {
        return name();
    }

    public static A2Enum fromValue(String v) {
        return valueOf(v);
    }

}
