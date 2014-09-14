package org.jvnet.hyperjaxb3.ejb.strategy.model.base;

import java.util.Collection;
import java.util.Set;

import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.strategy.model.CreatePropertyInfos;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;

import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CElement;
import com.sun.tools.xjc.model.CElementInfo;
import com.sun.tools.xjc.model.CNonElement;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CReferencePropertyInfo;

public class WrapSingleClassElementReference implements CreatePropertyInfos {

	public Collection<CPropertyInfo> process(ProcessModel context,
			CPropertyInfo propertyInfo) {

		assert propertyInfo instanceof CReferencePropertyInfo;
		final CReferencePropertyInfo referencePropertyInfo = (CReferencePropertyInfo) propertyInfo;

		assert referencePropertyInfo.getWildcard() == null;
		assert !referencePropertyInfo.isMixed();
		final Set<CElement> elements = context.getGetTypes().getElements(
				context, referencePropertyInfo);
		assert elements.size() == 1;

		final CElement element = elements.iterator().next();

		assert element instanceof CElementInfo;

		final CElementInfo elementInfo = (CElementInfo) element;

		final CNonElement contentType = elementInfo.getContentType();

		assert contentType instanceof CClassInfo;

		final CClassInfo classInfo = (CClassInfo) contentType;
		final CreatePropertyInfos createPropertyInfos = new AdaptSingleBuiltinReference(
				classInfo);

		final Collection<CPropertyInfo> newPropertyInfos = createPropertyInfos
				.process(context, propertyInfo);
		Customizations.markIgnored(propertyInfo);
		return newPropertyInfos;
	}
}
