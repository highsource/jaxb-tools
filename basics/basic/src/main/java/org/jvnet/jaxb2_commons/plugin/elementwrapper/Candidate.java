package org.jvnet.jaxb2_commons.plugin.elementwrapper;

import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JFieldVar;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;

/**
 * @author bjh
 */
class Candidate {
    protected CClassInfo classInfo;
    protected CPropertyInfo propertyInfo;
    protected CTypeInfo propertyTypeInfo;

    protected JDefinedClass implClass;
    protected JFieldVar field;
    protected String wrappedSchemaTypeName = null;

    public Candidate(CClassInfo classInfo) {
        this.classInfo = classInfo;
        this.propertyInfo = classInfo.getProperties().get(0);
        this.propertyTypeInfo = propertyInfo.ref().iterator().next();
        this.wrappedSchemaTypeName = XmlElementWrapperPlugin.elementName(propertyInfo);
    }

    public CClassInfo getClassInfo() {
        return classInfo;
    }

    public CPropertyInfo getPropertyInfo() {
        return propertyInfo;
    }

    public CTypeInfo getPropertyTypeInfo() {
        return propertyTypeInfo;
    }

    public String getClassName() {
        return classInfo.fullName();
    }

    public String getFieldName() {
        return getPropertyInfo().getName(false);
    }

    public String getFieldTypeName() {
        return propertyTypeInfo.getType().fullName();
    }

    public String getWrappedSchemaTypeName() {
        return wrappedSchemaTypeName;
    }
}
