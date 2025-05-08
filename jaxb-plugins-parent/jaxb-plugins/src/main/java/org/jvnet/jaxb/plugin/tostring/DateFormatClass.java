package org.jvnet.jaxb.plugin.tostring;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(namespace = Customizations.NAMESPACE_URI, name = "date-format")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class DateFormatClass {

    private String format;

    private String formatRef;

    @XmlAttribute
    @XmlJavaTypeAdapter(value = CollapsedStringAdapter.class)
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @XmlAttribute
    @XmlJavaTypeAdapter(value = CollapsedStringAdapter.class)
    public String getFormatRef() {
        return formatRef;
    }

    public void setFormatRef(String formatRef) {
        this.formatRef = formatRef;
    }
}
