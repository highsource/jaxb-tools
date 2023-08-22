package org.jvnet.hyperjaxb3.ejb.strategy.model.base;

import java.util.Collection;
import java.util.Set;

import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.strategy.model.CreatePropertyInfos;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;

import com.sun.tools.xjc.model.CElement;
import com.sun.tools.xjc.model.CElementInfo;
import com.sun.tools.xjc.model.CEnumLeafInfo;
import com.sun.tools.xjc.model.CNonElement;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CReferencePropertyInfo;

public class WrapSingleEnumElementReference implements CreatePropertyInfos {

	public Collection<CPropertyInfo> process(ProcessModel context,
			CPropertyInfo propertyInfo) {

		assert propertyInfo instanceof CReferencePropertyInfo;
		final CReferencePropertyInfo referencePropertyInfo = (CReferencePropertyInfo) propertyInfo;

		assert referencePropertyInfo.getWildcard() == null;
		assert !referencePropertyInfo.isMixed();
		Set<CElement> elements = context.getGetTypes().getElements(context,
				referencePropertyInfo);
		assert elements.size() == 1;

		final CElement element = elements.iterator().next();

		assert element instanceof CElementInfo;

		final CElementInfo elementInfo = (CElementInfo) element;

		final CNonElement contentType = elementInfo.getContentType();

		assert contentType instanceof CEnumLeafInfo;

		final CEnumLeafInfo enumLeafInfo = (CEnumLeafInfo) contentType;
		final CreatePropertyInfos createPropertyInfos = new AdaptSingleBuiltinReference(
				enumLeafInfo);

		final Collection<CPropertyInfo> newPropertyInfos = createPropertyInfos
				.process(context, propertyInfo);
		Customizations.markIgnored(propertyInfo);
		return newPropertyInfos;
	}
}
