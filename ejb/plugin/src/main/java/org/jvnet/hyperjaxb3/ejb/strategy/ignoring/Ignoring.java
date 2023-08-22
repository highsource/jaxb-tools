package org.jvnet.hyperjaxb3.ejb.strategy.ignoring;

import org.jvnet.hyperjaxb3.ejb.strategy.mapping.Mapping;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;

import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CClassInfoParent;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.Model;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.Outline;
import com.sun.tools.xjc.outline.PackageOutline;

public interface Ignoring {

	public boolean isPackageOutlineIgnored(Mapping context, Outline outline,
			PackageOutline packageOutline);

	public boolean isClassOutlineIgnored(Mapping context,
			ClassOutline classOutline);

	public boolean isFieldOutlineIgnored(Mapping context,
			FieldOutline fieldOutline);

	public boolean isPackageInfoIgnored(ProcessModel context, Model model,
			CClassInfoParent.Package packageInfo);

	public boolean isClassInfoIgnored(ProcessModel context, CClassInfo classInfo);

	public boolean isPropertyInfoIgnored(ProcessModel context,
			CPropertyInfo propertyInfo);

}
