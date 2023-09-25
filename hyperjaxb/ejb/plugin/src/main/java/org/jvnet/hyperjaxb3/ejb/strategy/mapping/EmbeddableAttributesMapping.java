package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.hyperjaxb3.codemodel.util.JTypeUtils;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.jaxb.util.CustomizationUtils;
import org.jvnet.jaxb.util.FieldAccessorUtils;

import com.sun.codemodel.JMethod;
import com.sun.codemodel.JType;
import jakarta.xml.ns.persistence.orm.Basic;
import jakarta.xml.ns.persistence.orm.EmbeddableAttributes;
import jakarta.xml.ns.persistence.orm.Transient;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CClass;
import com.sun.tools.xjc.model.CEnumLeafInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;

public class EmbeddableAttributesMapping implements
		ClassOutlineMapping<EmbeddableAttributes> {

	protected Log logger = LogFactory.getLog(getClass());

	public EmbeddableAttributes process(Mapping context,
			ClassOutline classOutline, Options options) {

		final EmbeddableAttributes attributes = new EmbeddableAttributes();

		final FieldOutline[] fieldOutlines = classOutline.getDeclaredFields();
		for (final FieldOutline fieldOutline : fieldOutlines) {

			final Object attributeMapping = getAttributeMapping(context,
					fieldOutline, options).process(context, fieldOutline,
					options);

			if (attributeMapping instanceof Basic) {
				attributes.getBasic().add((Basic) attributeMapping);
			} else if (attributeMapping instanceof Transient) {
				attributes.getTransient().add((Transient) attributeMapping);
			}
		}
		return attributes;
	}

	public FieldOutlineMapping<?> getAttributeMapping(Mapping context,
			FieldOutline fieldOutline, Options options) {
		if (context.getIgnoring().isFieldOutlineIgnored(context, fieldOutline)) {
			return context.getTransientMapping();
		} else if (isFieldOutlineId(context, fieldOutline)) {
			final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();
			logger.warn("Field outline  [" + propertyInfo.getName(true)
					+ "] is marked as [id] field. "
					+ "This is not supported in embeddable classes. "
					+ "This field will be made transient.");

			return context.getTransientMapping();
		} else if (isFieldOutlineEmbeddedId(context, fieldOutline)) {
			final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();
			logger.warn("Field outline  [" + propertyInfo.getName(true)
					+ "] is marked as [embedded-id] field. "
					+ "This is not supported in embeddable classes. "
					+ "This field will be made transient.");

			return context.getTransientMapping();
		} else if (isFieldOutlineVersion(context, fieldOutline)) {
			final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();
			logger.warn("Field outline  [" + propertyInfo.getName(true)
					+ "] is marked as [version] field. "
					+ "This is not supported in embeddable classes. "
					+ "This field will be made transient.");
			return context.getTransientMapping();
		} else {

			final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();
			if (!propertyInfo.isCollection()) {
				logger.trace("Field outline  [" + propertyInfo.getName(true)
						+ "] is a single field.");

				final Collection<? extends CTypeInfo> types = context
						.getGetTypes().process(context, propertyInfo);

				if (types.size() == 1) {
					logger.trace("Field outline  ["
							+ propertyInfo.getName(true)
							+ "] is a homogeneous single field.");

					if (isFieldOutlineBasic(context, fieldOutline)) {
						return context.getBasicMapping();
					} else

					if (isFieldOutlineComplex(context, fieldOutline)) {
						logger.warn("Field outline  ["
								+ propertyInfo.getName(true)
								+ "] is a complex field. "
								+ "This is not supported in embeddable classes. "
								+ "This field will be made transient.");

						return context.getTransientMapping();
					} else {
						logger.warn("Field outline  ["
								+ propertyInfo.getName(true)
								+ "] is not a basic field. "
								+ "This is not supported in embeddable classes. "
								+ "This field will be made transient.");

						return context.getTransientMapping();

					}
				} else {
					logger.warn("Field outline  [" + propertyInfo.getName(true)
							+ "] is a heterogeneous field. "
							+ "This is not supported in embeddable classes. "
							+ "This field will be made transient.");

					return context.getTransientMapping();
				}
			} else {
				logger.warn("Field outline  [" + propertyInfo.getName(true)
						+ "] is a collection field. "
						+ "This is not supported in embeddable classes. "
						+ "This field will be made transient.");
				return context.getTransientMapping();
			}
		}
	}

	public boolean isFieldOutlineId(Mapping context, FieldOutline fieldOutline) {
		return CustomizationUtils.containsCustomization(fieldOutline,
				Customizations.ID_ELEMENT_NAME);
	}

	public boolean isFieldOutlineVersion(Mapping context,
			FieldOutline fieldOutline) {

		return CustomizationUtils.containsCustomization(fieldOutline,
				Customizations.VERSION_ELEMENT_NAME);
	}

	public boolean isFieldOutlineBasic(Mapping context,
			FieldOutline fieldOutline) {

		return isFieldOutlineCore(context, fieldOutline)
				|| isFieldOutlineEnumerated(context, fieldOutline);
	}

	public boolean isFieldOutlineCore(Mapping context, FieldOutline fieldOutline) {
		final JMethod getter = FieldAccessorUtils.getter(fieldOutline);

		final JType type = getter.type();
		return JTypeUtils.isBasicType(type);
	}

	public boolean isFieldOutlineEnumerated(Mapping context,
			FieldOutline fieldOutline) {
		final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();

		final Collection<? extends CTypeInfo> types = context.getGetTypes()
				.process(context, propertyInfo);
		if (types.size() == 1) {

			final CTypeInfo type = types.iterator().next();

			return type instanceof CEnumLeafInfo;
		} else {
			return false;
		}
	}

	public boolean isFieldOutlineComplex(Mapping context,
			FieldOutline fieldOutline) {

		final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();

		final Collection<? extends CTypeInfo> types = context.getGetTypes()
				.process(context, propertyInfo);

		assert types.size() == 1;

		final CTypeInfo type = types.iterator().next();

		return type instanceof CClass;
	}

	public boolean isFieldOutlineEmbeddedId(Mapping context,
			FieldOutline fieldOutline) {
		return CustomizationUtils.containsCustomization(fieldOutline,
				Customizations.EMBEDDED_ID_ELEMENT_NAME);
	}

}
