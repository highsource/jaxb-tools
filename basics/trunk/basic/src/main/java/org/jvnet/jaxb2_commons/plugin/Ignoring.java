package org.jvnet.jaxb2_commons.plugin;

import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;

public interface Ignoring {

	public boolean isIgnored(ClassOutline classOutline);

	public boolean isIgnored(FieldOutline fieldOutline);

}
