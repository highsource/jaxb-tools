package org.jvnet.jaxb.plugin;

import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CEnumLeafInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.EnumOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import org.apache.commons.logging.Log;
import org.jvnet.jaxb.plugin.customizations.Customizations;
import org.jvnet.jaxb.plugin.customizations.LegacyCustomizations;
import org.jvnet.jaxb.util.CustomizationUtils;

import javax.xml.namespace.QName;

public class ComposedIgnoring implements Ignoring {

    private final Log logger;
    private final Ignoring ignoring;
    private final Ignoring legacyIgnoring;

    public ComposedIgnoring(Log logger, Ignoring ignoring) {
        this(logger, ignoring, null);
    }

    public ComposedIgnoring(Log logger, Ignoring ignoring, Ignoring legacyIgnoring) {
        this.logger = logger;
        this.ignoring = ignoring;
        this.legacyIgnoring = legacyIgnoring;
    }

    @Override
    public QName[] getIgnoredCustomizationElementNames() {
        return ignoring.getIgnoredCustomizationElementNames();
    }

    public boolean isIgnored(ClassOutline classOutline) {
        if (ignoring.isIgnored(classOutline)) {
            return true;
        }
        if (legacyIgnoring != null && legacyIgnoring.isIgnored(classOutline)) {
            logger.warn("Please migrate your namespace in xsd / xjb from " + legacyIgnoring.getIgnoredCustomizationElementNames() + " to " + ignoring.getIgnoredCustomizationElementNames());
            return true;
        }
        return false;
    }

    public boolean isIgnored(EnumOutline enumOutline) {
        if (ignoring.isIgnored(enumOutline)) {
            return true;
        }
        if (legacyIgnoring != null && legacyIgnoring.isIgnored(enumOutline)) {
            logger.warn("Please migrate your namespace in xsd / xjb from " + legacyIgnoring.getIgnoredCustomizationElementNames() + " to " + ignoring.getIgnoredCustomizationElementNames());
            return true;
        }
        return false;
    }

    public boolean isIgnored(FieldOutline fieldOutline) {
        if (ignoring.isIgnored(fieldOutline)) {
            return true;
        }
        if (legacyIgnoring != null && legacyIgnoring.isIgnored(fieldOutline)) {
            logger.warn("Please migrate your namespace in xsd / xjb from " + legacyIgnoring.getIgnoredCustomizationElementNames() + " to " + ignoring.getIgnoredCustomizationElementNames());
            return true;
        }
        return false;
    }

    public boolean isIgnored(CClassInfo classInfo) {
        if (ignoring.isIgnored(classInfo)) {
            return true;
        }
        if (legacyIgnoring != null && legacyIgnoring.isIgnored(classInfo)) {
            logger.warn("Please migrate your namespace in xsd / xjb from " + legacyIgnoring.getIgnoredCustomizationElementNames() + " to " + ignoring.getIgnoredCustomizationElementNames());
            return true;
        }
        return false;
    }

    public boolean isIgnored(CEnumLeafInfo enumLeafInfo) {
        if (ignoring.isIgnored(enumLeafInfo)) {
            return true;
        }
        if (legacyIgnoring != null && legacyIgnoring.isIgnored(enumLeafInfo)) {
            logger.warn("Please migrate your namespace in xsd / xjb from " + legacyIgnoring.getIgnoredCustomizationElementNames() + " to " + ignoring.getIgnoredCustomizationElementNames());
            return true;
        }
        return false;
    }

    public boolean isIgnored(CPropertyInfo propertyInfo) {
        if (ignoring.isIgnored(propertyInfo)) {
            return true;
        }
        if (legacyIgnoring != null && legacyIgnoring.isIgnored(propertyInfo)) {
            logger.warn("Please migrate your namespace in xsd / xjb from " + legacyIgnoring.getIgnoredCustomizationElementNames() + " to " + ignoring.getIgnoredCustomizationElementNames());
            return true;
        }
        return false;
    }

}
