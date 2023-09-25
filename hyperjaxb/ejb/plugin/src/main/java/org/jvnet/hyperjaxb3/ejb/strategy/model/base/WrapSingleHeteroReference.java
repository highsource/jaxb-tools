package org.jvnet.hyperjaxb3.ejb.strategy.model.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Basic;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.JaxbContext;
import org.jvnet.hyperjaxb3.ejb.strategy.model.CreatePropertyInfos;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;
import org.jvnet.hyperjaxb3.xjc.generator.bean.field.ElementField;
import org.jvnet.hyperjaxb3.xjc.generator.bean.field.SingleWrappingClassInfoField;
import org.jvnet.hyperjaxb3.xjc.generator.bean.field.SingleWrappingReferenceElementInfoField;
import org.jvnet.hyperjaxb3.xjc.generator.bean.field.SingleWrappingReferenceObjectField;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.ElementAsString;
import org.jvnet.jaxb.util.CustomizationUtils;
import org.jvnet.jaxb.util.OutlineUtils;

import jakarta.xml.ns.persistence.orm.Lob;
import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.generator.bean.field.FieldRenderer;
import com.sun.tools.xjc.model.CAttributePropertyInfo;
import com.sun.tools.xjc.model.CBuiltinLeafInfo;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CClassRef;
import com.sun.tools.xjc.model.CCustomizations;
import com.sun.tools.xjc.model.CElement;
import com.sun.tools.xjc.model.CElementInfo;
import com.sun.tools.xjc.model.CElementPropertyInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CReferencePropertyInfo;
import com.sun.tools.xjc.model.CTypeRef;
import com.sun.tools.xjc.model.TypeUseFactory;
import com.sun.tools.xjc.model.CElementPropertyInfo.CollectionMode;
import com.sun.tools.xjc.outline.FieldOutline;
import org.glassfish.jaxb.core.v2.model.core.ID;

public class WrapSingleHeteroReference implements CreatePropertyInfos {

	protected Log logger = LogFactory.getLog(getClass());

	public Collection<CPropertyInfo> process(ProcessModel context,
			CPropertyInfo propertyInfo) {

		assert propertyInfo instanceof CReferencePropertyInfo;
		final CReferencePropertyInfo referencePropertyInfo = (CReferencePropertyInfo) propertyInfo;
		assert !referencePropertyInfo.isMixed();

		// if (referencePropertyInfo.getElements().isEmpty()) {
		final CPropertyInfo elementProperty = createElementProperty(referencePropertyInfo);

		final CPropertyInfo objectProperty = createObjectProperty(context,
				referencePropertyInfo);

		final Collection<CPropertyInfo> newPropertyInfos = new ArrayList<CPropertyInfo>(
				context.getGetTypes()
						.getElements(context, referencePropertyInfo).size() + 3);

		if (elementProperty != null) {
			newPropertyInfos.add(elementProperty);
		}
		if (objectProperty != null) {
			newPropertyInfos.add(objectProperty);
		}

		final Collection<CPropertyInfo> properties = createElementProperties(
				context, (CReferencePropertyInfo) propertyInfo);

		if (properties != null) {

			newPropertyInfos.addAll(properties);
		}

		Customizations.markIgnored(propertyInfo);

		return newPropertyInfos;
	}

	protected CPropertyInfo createObjectProperty(final ProcessModel context,
			final CReferencePropertyInfo referencePropertyInfo) {
		final CPropertyInfo objectProperty;
		if (referencePropertyInfo.getWildcard() != null
				&& referencePropertyInfo.getWildcard().allowTypedObject) {

			objectProperty = new CAttributePropertyInfo(
					referencePropertyInfo.getName(true) + "Object",
					referencePropertyInfo.getSchemaComponent(),
					new CCustomizations(), referencePropertyInfo.getLocator(),
					new QName(referencePropertyInfo.getName(true) + "Object"),
					CBuiltinLeafInfo.STRING,
					CBuiltinLeafInfo.STRING.getTypeName(), false);

			objectProperty.realization = new FieldRenderer() {
				public FieldOutline generate(ClassOutlineImpl classOutline,
						CPropertyInfo prop) {
					final JaxbContext jaxbContext = context.getCustomizing()
							.getJaxbContext(prop);

					final String contextPath = (jaxbContext == null || jaxbContext
							.getContextPath() == null) ? OutlineUtils
							.getContextPath(classOutline.parent())
							: jaxbContext.getContextPath();

					final boolean _final = (jaxbContext == null
							|| jaxbContext.getField() == null || jaxbContext
							.getField().isFinal() == null) ? true : jaxbContext
							.getField().isFinal();

					final SingleWrappingReferenceObjectField fieldOutline = new SingleWrappingReferenceObjectField(
							classOutline, prop, referencePropertyInfo,
							contextPath, _final);
					fieldOutline.generateAccessors();
					return fieldOutline;
				}
			};
			final Basic basic = new Basic();
			basic.setLob(new Lob());

			CustomizationUtils.addCustomization(objectProperty,
					Customizations.getContext(),
					Customizations.BASIC_ELEMENT_NAME, basic);

			Customizations.markGenerated(objectProperty);

		} else {
			objectProperty = null;
		}
		return objectProperty;
	}

	protected CPropertyInfo createElementProperty(
			final CReferencePropertyInfo referencePropertyInfo) {
		final CAttributePropertyInfo elementProperty;
		if (referencePropertyInfo.getWildcard() != null
				&& referencePropertyInfo.getWildcard().allowDom) {

			elementProperty = new CAttributePropertyInfo(
					referencePropertyInfo.getName(true) + "Element",
					referencePropertyInfo.getSchemaComponent(),
					new CCustomizations(), referencePropertyInfo.getLocator(),
					new QName(referencePropertyInfo.getName(true) + "Element"),

					TypeUseFactory.adapt(CBuiltinLeafInfo.STRING,
							ElementAsString.class, false),
					CBuiltinLeafInfo.STRING.getTypeName(), false);

			elementProperty.realization = new FieldRenderer() {
				public FieldOutline generate(ClassOutlineImpl context,
						CPropertyInfo prop) {
					ElementField fieldOutline = new ElementField(context, prop,
							referencePropertyInfo);
					fieldOutline.generateAccessors();
					return fieldOutline;
				}

			};

			final Basic basic = new Basic();
			basic.setLob(new Lob());

			CustomizationUtils.addCustomization(elementProperty,
					Customizations.getContext(),
					Customizations.BASIC_ELEMENT_NAME, basic);

			Customizations.markGenerated(elementProperty);
		} else {
			elementProperty = null;
		}
		return elementProperty;
	}

	protected Collection<CPropertyInfo> createElementProperties(
			ProcessModel context, final CReferencePropertyInfo propertyInfo) {

		Set<CElement> elements = context.getGetTypes().getElements(context,
				propertyInfo);

		final Collection<CPropertyInfo> properties = new ArrayList<CPropertyInfo>(
				elements.size());

		for (CElement element : elements) {

			final CElementPropertyInfo itemPropertyInfo = new CElementPropertyInfo(
					propertyInfo.getName(true)
							+ ((CClassInfo) propertyInfo.parent()).model
									.getNameConverter().toPropertyName(
											element.getElementName()
													.getLocalPart()),
					CollectionMode.NOT_REPEATED, ID.NONE,
					propertyInfo.getExpectedMimeType(),
					propertyInfo.getSchemaComponent(),
					new CCustomizations(CustomizationUtils
							.getCustomizations(propertyInfo)),
					propertyInfo.getLocator(), false);

			if (element instanceof CElementInfo) {
				final CElementInfo elementInfo = (CElementInfo) element;
				if (!elementInfo.getSubstitutionMembers().isEmpty()) {
					logger.error("["
							+ ((CClassInfo) propertyInfo.parent()).getName()
							+ "."
							+ propertyInfo.getName(true)
							+ "] is a single hetero reference containing element ["
							+ elementInfo.getSqueezedName()
							+ "] which is a substitution group head. See issue #95.");
				} else {
					itemPropertyInfo.getTypes().addAll(
							context.getGetTypes().getTypes(context,
									((CElementInfo) element).getProperty()));

					itemPropertyInfo.realization = new FieldRenderer() {
						public FieldOutline generate(
								ClassOutlineImpl classOutline, CPropertyInfo p) {
							SingleWrappingReferenceElementInfoField field = new SingleWrappingReferenceElementInfoField(
									classOutline, p, propertyInfo, elementInfo);
							field.generateAccessors();
							return field;
						}
					};
					Customizations.markGenerated(itemPropertyInfo);

					properties.add(itemPropertyInfo);
				}
			} else if (element instanceof CClassInfo) {

				final CClassInfo classInfo = (CClassInfo) element;

				final QName elementName = classInfo.getElementName();
				final QName typeName = classInfo.getTypeName();
				final CTypeRef typeRef = new CTypeRef(classInfo, elementName,
						typeName, false, null);

				itemPropertyInfo.realization = new FieldRenderer() {
					public FieldOutline generate(ClassOutlineImpl classOutline,
							CPropertyInfo p) {
						SingleWrappingClassInfoField field = new SingleWrappingClassInfoField(
								classOutline, p, propertyInfo, classInfo);
						field.generateAccessors();
						return field;
					}
				};

				itemPropertyInfo.getTypes().add(typeRef);

				Customizations.markGenerated(itemPropertyInfo);
				properties.add(itemPropertyInfo);

			} else if (element instanceof CClassRef) {
				final CClassRef classRef = (CClassRef) element;
				logger.error("CClassRef elements are not supported yet.");

				logger.error("["
						+ ((CClassInfo) propertyInfo.parent()).getName()
						+ "."
						+ propertyInfo.getName(true)
						+ "] is a single hetero reference containing unsupported CClassRef element ["
						+ classRef.fullName() + "]. See issue #94.");

			}
		}
		return properties;
	}
}
