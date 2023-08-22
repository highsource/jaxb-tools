package org.jvnet.hyperjaxb3.ejb.strategy.model;

import java.util.Collection;
import java.util.Set;

import com.sun.tools.xjc.model.CAttributePropertyInfo;
import com.sun.tools.xjc.model.CElement;
import com.sun.tools.xjc.model.CElementPropertyInfo;
import com.sun.tools.xjc.model.CNonElement;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CReferencePropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.model.CTypeRef;
import com.sun.tools.xjc.model.CValuePropertyInfo;
import com.sun.tools.xjc.model.TypeUse;

public interface GetTypes<C> extends
		PropertyInfoProcessor<Collection<? extends CTypeInfo>, C> {

	public Collection<? extends CTypeInfo> ref(C context,
			CPropertyInfo propertyInfo);

	public Collection<? extends CTypeRef> getTypes(C context,
			CElementPropertyInfo propertyInfo);

	public CNonElement getTarget(C context, CAttributePropertyInfo propertyInfo);

	public CNonElement getTarget(C context, CValuePropertyInfo propertyInfo);

	public TypeUse getTypeUse(C context, CPropertyInfo propertyInfo);

	public Set<CElement> getElements(C context,
			CReferencePropertyInfo referencePropertyInfo);

}
