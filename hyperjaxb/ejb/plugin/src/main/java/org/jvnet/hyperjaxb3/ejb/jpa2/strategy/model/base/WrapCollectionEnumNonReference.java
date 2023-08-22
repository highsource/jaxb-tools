package org.jvnet.hyperjaxb3.ejb.jpa2.strategy.model.base;

import java.util.Collection;

import org.jvnet.hyperjaxb3.ejb.strategy.model.CreatePropertyInfos;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;
import org.jvnet.hyperjaxb3.ejb.strategy.model.base.CreateNoPropertyInfos;

import com.sun.tools.xjc.model.CEnumLeafInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;

public class WrapCollectionEnumNonReference implements CreatePropertyInfos {

	public Collection<CPropertyInfo> process(ProcessModel context,
			CPropertyInfo propertyInfo) {

		// Single
		assert propertyInfo.isCollection();
		
		final Collection<? extends CTypeInfo> types = context.getGetTypes().process(context, propertyInfo);
		// Builtin
		assert types.size() == 1;
		assert types.iterator().next() instanceof CEnumLeafInfo;

		return getCreatePropertyInfos(context, propertyInfo).process(context,
				propertyInfo);
	}

	public CreatePropertyInfos getCreatePropertyInfos(ProcessModel context,
			CPropertyInfo propertyInfo) {

		// See http://jira.highsource.org/browse/HJIII-90
		// final ElementCollection elementCollection =
		// context.getCustomizing().getElementCollection(propertyInfo);
		//
		// if (elementCollection != null &&
		// elementCollection.getEnumeratedValue() != null) {
		// return new AdaptCollectionEnumNonReferenceAsEnumValue();
		// } else {
		return CreateNoPropertyInfos.INSTANCE;
		// }
	}

}
