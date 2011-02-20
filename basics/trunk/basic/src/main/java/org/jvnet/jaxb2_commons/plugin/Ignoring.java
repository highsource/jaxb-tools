package org.jvnet.jaxb2_commons.plugin;

import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;

public interface Ignoring {

	public boolean isIgnored(ClassOutline classOutline);

	public boolean isIgnored(FieldOutline fieldOutline);

	public boolean isIgnored(CClassInfo classInfo);

	public boolean isIgnored(CPropertyInfo propertyInfo);

}
