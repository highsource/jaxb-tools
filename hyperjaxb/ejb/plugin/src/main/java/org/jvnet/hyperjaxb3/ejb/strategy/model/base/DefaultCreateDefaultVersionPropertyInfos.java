package org.jvnet.hyperjaxb3.ejb.strategy.model.base;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.jvnet.jaxb.annox.util.ClassUtils;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.GeneratedVersion;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Version;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ClassInfoProcessor;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;
import org.jvnet.hyperjaxb3.xjc.generator.bean.field.TransientSingleField;
import org.jvnet.hyperjaxb3.xjc.model.CExternalLeafInfo;

import com.sun.tools.xjc.generator.bean.field.GenericFieldRenderer;
import com.sun.tools.xjc.model.CAttributePropertyInfo;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CCustomizations;
import com.sun.tools.xjc.model.CNonElement;
import com.sun.tools.xjc.model.CPluginCustomization;
import com.sun.tools.xjc.model.CPropertyInfo;

public class DefaultCreateDefaultVersionPropertyInfos implements
		ClassInfoProcessor<Collection<CPropertyInfo>, ProcessModel> {

	public Collection<CPropertyInfo> process(ProcessModel context,
			CClassInfo classInfo) {

		final GeneratedVersion cversion = context.getCustomizing()
				.getGeneratedVersion(classInfo);

		if (cversion == null) {
			return Collections.emptyList();
		} else {

			final CPropertyInfo propertyInfo = createPropertyInfo(context,
					classInfo, cversion);
			return Collections.singletonList(propertyInfo);
		}

	}

	protected CPropertyInfo createPropertyInfo(ProcessModel context,
			CClassInfo classInfo, GeneratedVersion cversion) {
		final String propertyName = getPropertyName(context, cversion);
		final QName attributeName = getAttributeName(context, cversion);
		final CNonElement propertyTypeInfo = getPropertyTypeInfo(context,
				cversion);
		final CCustomizations customizations = new CCustomizations();
		final CPluginCustomization version = createVersionCustomization(context, cversion);
		customizations.add(version);
		//
		// CPluginCustomization generated = CustomizationUtils
		// .createCustomization(org.jvnet.jaxb2_commons.plugin.Customizations.GENERATED_ELEMENT_NAME);
		// generated.markAsAcknowledged();
		// customizations.add(generated);

		final CPropertyInfo propertyInfo = new CAttributePropertyInfo(
				propertyName, null, customizations, null, attributeName,
				propertyTypeInfo, propertyTypeInfo.getTypeName(), false);

		if (cversion.isTransient() != null && cversion.isTransient()) {
			propertyInfo.realization = new GenericFieldRenderer(
					TransientSingleField.class);
		}

		Customizations.markGenerated(propertyInfo);

		return propertyInfo;
	}

	public String getPropertyName(ProcessModel context,
			GeneratedVersion cversion) {
		final String name = cversion.getName();
        Objects.requireNonNull(name, "The hj:version/@name attribute must not be null." );
        if (name.isEmpty()) {
            throw new IllegalArgumentException("The hj:version/@name attribute must not be empty.");
        }
		return name;
	}

	public QName getAttributeName(ProcessModel context, GeneratedVersion version) {
		final QName attributeName = version.getAttributeName();
		return attributeName != null ? attributeName : new QName(
				getPropertyName(context, version));
	}

	public CNonElement getPropertyTypeInfo(ProcessModel context,
			GeneratedVersion cversion) {
		final String javaType = cversion.getJavaType();
        Objects.requireNonNull(javaType, "The hj:version/@javaType attribute must not be null." );
        if (javaType.isEmpty()) {
            throw new IllegalArgumentException("The hj:version/@javaType attribute must not be empty.");
        }
		final QName schemaType = cversion.getSchemaType();
		Objects.requireNonNull(schemaType,
				"The hj:version/@schemaType attribute must not be null.");
		try {
			final Class<?> theClass = ClassUtils.forName(javaType);
			return new CExternalLeafInfo(theClass, schemaType, null);
		} catch (ClassNotFoundException cnfex) {
			throw new IllegalArgumentException(
					"Class name ["
							+ javaType
							+ "] provided in the hj:version/@javaType attribute could not be resolved.",
					cnfex);
		}
	}

	public CPluginCustomization createVersionCustomization(ProcessModel context,
			GeneratedVersion cversion) {
		final Version version = new Version();
		version.mergeFrom(cversion, version);
		final JAXBElement<Version> versionElement = Customizations
				.getCustomizationsObjectFactory().createVersion(version);

		return Customizations.createCustomization(versionElement);

	}

}
