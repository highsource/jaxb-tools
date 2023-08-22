package org.jvnet.hyperjaxb3.ejb.strategy.model.base;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.hyperjaxb3.ejb.strategy.model.CreatePropertyInfos;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;

import com.sun.tools.xjc.model.CBuiltinLeafInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.model.TypeUse;

public class WrapSingleBuiltinNonReference extends AbstractWrapBuiltin {

	protected Log logger = LogFactory.getLog(getClass());

	public CBuiltinLeafInfo getTypeUse(ProcessModel context,
			CPropertyInfo propertyInfo) {
		final Collection<? extends CTypeInfo> types = context.getGetTypes().process(
				context, propertyInfo);
		return (CBuiltinLeafInfo) types.iterator().next();
	}

	@Override
	public CreatePropertyInfos getCreatePropertyInfos(ProcessModel context,
			CPropertyInfo propertyInfo) {

		final CBuiltinLeafInfo originalTypeUse = getTypeUse(context,
				propertyInfo);

		final TypeUse adaptingTypeUse = context.getAdaptBuiltinTypeUse()
				.process(context, propertyInfo);

		if (adaptingTypeUse == originalTypeUse
				|| adaptingTypeUse.getAdapterUse() == null) {
			logger.debug("No adaptation required.");
			return CreateNoPropertyInfos.INSTANCE;

		} else {
			return new AdaptSingleBuiltinNonReference(adaptingTypeUse);
		}
	}

	protected Collection<CPropertyInfo> wrapAnyType(ProcessModel context,
			CPropertyInfo propertyInfo) {
		return new AdaptSingleWildcardNonReference(CBuiltinLeafInfo.STRING).process(
				context, propertyInfo);
	}

}
