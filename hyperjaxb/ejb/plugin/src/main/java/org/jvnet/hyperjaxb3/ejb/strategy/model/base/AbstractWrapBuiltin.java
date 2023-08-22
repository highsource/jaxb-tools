package org.jvnet.hyperjaxb3.ejb.strategy.model.base;

import java.util.Collection;
import java.util.Collections;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.hyperjaxb3.ejb.strategy.model.CreatePropertyInfos;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;

import com.sun.tools.xjc.model.CBuiltinLeafInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.model.TypeUse;

public abstract class AbstractWrapBuiltin implements CreatePropertyInfos {

	protected Log logger = LogFactory.getLog(getClass());

	public Collection<CPropertyInfo> process(ProcessModel context,
			CPropertyInfo propertyInfo) {
//		 Single
//		assert !propertyInfo.isCollection();
		// Builtin
		Collection<? extends CTypeInfo> types = context.getGetTypes().process(
				context, propertyInfo);
		assert types.size() == 1;
		assert types.iterator().next() instanceof CBuiltinLeafInfo;

		final CBuiltinLeafInfo originalTypeUse = getTypeUse(context,
				propertyInfo);
		if (propertyInfo.getAdapter() != null) {
			logger.debug("Adapter property info is not wrapped");
			return Collections.emptyList();
		} else if (originalTypeUse == CBuiltinLeafInfo.DATA_HANDLER) {
			// TODO #42
			logger
					.error("Data handler is currently not supported. See issue #88 (http://java.net/jira/browse/HYPERJAXB3-88).");
			return Collections.emptyList();
		} else if (originalTypeUse == CBuiltinLeafInfo.ANYTYPE) {
			return wrapAnyType(context, propertyInfo);
		} else {

			final TypeUse adaptedTypeUse = context.getAdaptBuiltinTypeUse()
					.process(context, propertyInfo);

			if (adaptedTypeUse == null) {
				logger.error("Unsupported builtin type ["
						+ originalTypeUse.getTypeName() + "] in property ["
						+ propertyInfo.getName(true) + "].");
				return Collections.emptyList();
			} else {

				final CreatePropertyInfos createPropertyInfos = getCreatePropertyInfos(
						context, propertyInfo);

				final Collection<CPropertyInfo> newPropertyInfos = createPropertyInfos
						.process(context, propertyInfo);
				// Customizations.markIgnored(propertyInfo);
				return newPropertyInfos;
			}
		}
	}

	protected abstract Collection<CPropertyInfo> wrapAnyType(
			ProcessModel context, CPropertyInfo propertyInfo);

	public abstract CBuiltinLeafInfo getTypeUse(ProcessModel context,
			CPropertyInfo propertyInfo);

	public abstract CreatePropertyInfos getCreatePropertyInfos(
			ProcessModel context, CPropertyInfo propertyInfo);
}
