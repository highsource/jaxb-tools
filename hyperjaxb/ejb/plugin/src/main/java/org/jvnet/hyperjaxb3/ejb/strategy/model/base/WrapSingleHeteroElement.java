package org.jvnet.hyperjaxb3.ejb.strategy.model.base;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.strategy.model.CreatePropertyInfos;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;
import org.jvnet.hyperjaxb3.xjc.generator.bean.field.SingleWrappingElementField;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;

import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.generator.bean.field.FieldRenderer;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CCustomizations;
import com.sun.tools.xjc.model.CElementPropertyInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeRef;
import com.sun.tools.xjc.model.CElementPropertyInfo.CollectionMode;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.xml.bind.v2.model.core.ID;

public class WrapSingleHeteroElement implements CreatePropertyInfos {

	protected Log logger = LogFactory.getLog(getClass());

	public Collection<CPropertyInfo> process(ProcessModel context,
			CPropertyInfo propertyInfo) {

		assert propertyInfo instanceof CElementPropertyInfo;
		final CElementPropertyInfo elementPropertyInfo = (CElementPropertyInfo) propertyInfo;

		final Collection<CPropertyInfo> newPropertyInfos = new ArrayList<CPropertyInfo>(
				context.getGetTypes().getTypes(context, elementPropertyInfo).size());

		final Collection<CPropertyInfo> properties = createTypeProperties(context, elementPropertyInfo);

		if (properties != null) {

			newPropertyInfos.addAll(properties);
		}

		Customizations.markIgnored(propertyInfo);

		return newPropertyInfos;
	}

	protected Collection<CPropertyInfo> createTypeProperties(
			final ProcessModel context, final CElementPropertyInfo propertyInfo) {

		final Collection<? extends CTypeRef> types = context.getGetTypes().getTypes(context, propertyInfo);
		// Set<CElement> elements = propertyInfo.getElements();

		final Collection<CPropertyInfo> properties = new ArrayList<CPropertyInfo>(
				types.size());

		for (final CTypeRef type : types) {
			final CElementPropertyInfo itemPropertyInfo = new CElementPropertyInfo(
					propertyInfo.getName(true)
							+ ((CClassInfo) propertyInfo.parent()).model
									.getNameConverter().toPropertyName(
											type.getTagName().getLocalPart()),
					CollectionMode.NOT_REPEATED, ID.NONE, propertyInfo
							.getExpectedMimeType(), propertyInfo
							.getSchemaComponent(),
					new CCustomizations(CustomizationUtils
							.getCustomizations(propertyInfo)), propertyInfo
							.getLocator(), false);

			itemPropertyInfo.getTypes().add(type);

			itemPropertyInfo.realization = new FieldRenderer() {
				public FieldOutline generate(ClassOutlineImpl classOutline,
						CPropertyInfo p) {
					final SingleWrappingElementField field = new SingleWrappingElementField(
							classOutline, p, propertyInfo, type);
					field.generateAccessors();
					return field;
				}
			};

			Customizations.markGenerated(itemPropertyInfo);
			properties.add(itemPropertyInfo);

			Collection<CPropertyInfo> newProperties = context
					.getProcessPropertyInfos().process(context,
							itemPropertyInfo);
			if (newProperties != null) {
				properties.addAll(newProperties);
			}

		}

		return properties;
	}

}
