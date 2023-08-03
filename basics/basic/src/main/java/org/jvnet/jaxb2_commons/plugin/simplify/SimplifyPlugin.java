package org.jvnet.jaxb2_commons.plugin.simplify;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.plugin.AbstractParameterizablePlugin;
import org.jvnet.jaxb2_commons.plugin.Customizations;
import org.jvnet.jaxb2_commons.plugin.CustomizedIgnoring;
import org.jvnet.jaxb2_commons.plugin.Ignoring;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;
import org.xml.sax.ErrorHandler;

import com.sun.codemodel.JJavaName;
import com.sun.tools.xjc.model.CAdapter;
import com.sun.tools.xjc.model.CAttributePropertyInfo;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CClassRef;
import com.sun.tools.xjc.model.CElement;
import com.sun.tools.xjc.model.CElementInfo;
import com.sun.tools.xjc.model.CElementPropertyInfo;
import com.sun.tools.xjc.model.CElementPropertyInfo.CollectionMode;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CPropertyVisitor;
import com.sun.tools.xjc.model.CReferencePropertyInfo;
import com.sun.tools.xjc.model.CTypeRef;
import com.sun.tools.xjc.model.CValuePropertyInfo;
import com.sun.tools.xjc.model.Model;
import com.sun.xml.bind.v2.model.core.ID;

public class SimplifyPlugin extends AbstractParameterizablePlugin {

	private boolean usePluralForm = false;

	public boolean isUsePluralForm() {
		return usePluralForm;
	}

	public void setUsePluralForm(boolean usePluralForm) {
		this.usePluralForm = usePluralForm;
	}

	@Override
	public String getOptionName() {
		return "Xsimplify";
	}

	@Override
	public String getUsage() {
		return "This plugin allows simplifying \"complex\" properties"
				+ " (ex. aOrBOrC generated from repeatable choices)"
				+ " into several \"simple\" properties (ex. a, b, c).\n"
				+ " Please visit http://confluence.highsource.org/display/J2B/Simplify+Plugin"
				+ " for plugin documentation.";
	}

	private Ignoring ignoring = new CustomizedIgnoring(
			org.jvnet.jaxb2_commons.plugin.simplify.Customizations.IGNORED_ELEMENT_NAME,
			Customizations.IGNORED_ELEMENT_NAME,
			Customizations.GENERATED_ELEMENT_NAME);

	public Ignoring getIgnoring() {
		return ignoring;
	}

	public void setIgnoring(Ignoring ignoring) {
		this.ignoring = ignoring;
	}

	@Override
	public Collection<QName> getCustomizationElementNames() {
		return Arrays
				.asList(org.jvnet.jaxb2_commons.plugin.simplify.Customizations.PROPERTY_ELEMENT_NAME,
						org.jvnet.jaxb2_commons.plugin.simplify.Customizations.AS_ELEMENT_PROPERTY_ELEMENT_NAME,
						org.jvnet.jaxb2_commons.plugin.simplify.Customizations.AS_REFERENCE_PROPERTY_ELEMENT_NAME,
						org.jvnet.jaxb2_commons.plugin.simplify.Customizations.IGNORED_ELEMENT_NAME,
						Customizations.IGNORED_ELEMENT_NAME,
						Customizations.GENERATED_ELEMENT_NAME);
	}

	@Override
	public void postProcessModel(final Model model, ErrorHandler errorHandler) {

		for (final CClassInfo classInfo : model.beans().values()) {
			postProcessClassInfo(model, classInfo);
		}
	}

	private void postProcessClassInfo(final Model model,
			final CClassInfo classInfo) {
		final List<CPropertyInfo> properties = new ArrayList<CPropertyInfo>(
				classInfo.getProperties());
		for (CPropertyInfo property : properties) {
			property.accept(new CPropertyVisitor<Void>() {

				public Void onElement(CElementPropertyInfo elementProperty) {
					postProcessElementPropertyInfo(model, classInfo,
							elementProperty);
					return null;
				}

				public Void onAttribute(CAttributePropertyInfo attributeProperty) {
					// TODO Auto-generated method stub
					return null;
				}

				public Void onValue(CValuePropertyInfo valueProperty) {
					// TODO Auto-generated method stub
					return null;
				}

				public Void onReference(CReferencePropertyInfo p) {
					postProcessReferencePropertyInfo(model, classInfo, p);
					return null;
				}

			});
		}
	}

	private void postProcessElementPropertyInfo(final Model model,
			final CClassInfo classInfo, CElementPropertyInfo property) {
		if (CustomizationUtils
				.containsPropertyCustomizationInPropertyOrClass(
						property,
						org.jvnet.jaxb2_commons.plugin.simplify.Customizations.PROPERTY_ELEMENT_NAME,
						org.jvnet.jaxb2_commons.plugin.simplify.Customizations.AS_ELEMENT_PROPERTY_ELEMENT_NAME)) {
			simplifyElementPropertyInfoAsElementPropertyInfo(model, classInfo,
					property);
		}
	}

	private void postProcessReferencePropertyInfo(final Model model,
			final CClassInfo classInfo, CReferencePropertyInfo property) {
		if (CustomizationUtils
				.containsPropertyCustomizationInPropertyOrClass(
						property,
						org.jvnet.jaxb2_commons.plugin.simplify.Customizations.PROPERTY_ELEMENT_NAME,
						org.jvnet.jaxb2_commons.plugin.simplify.Customizations.AS_ELEMENT_PROPERTY_ELEMENT_NAME)) {
			simplifyReferencePropertyInfoAsElementPropertyInfo(model,
					classInfo, property);
		} else if (CustomizationUtils
				.containsPropertyCustomizationInPropertyOrClass(
						property,
						org.jvnet.jaxb2_commons.plugin.simplify.Customizations.PROPERTY_ELEMENT_NAME,
						org.jvnet.jaxb2_commons.plugin.simplify.Customizations.AS_REFERENCE_PROPERTY_ELEMENT_NAME)) {
			simplifyReferencePropertyInfoAsReferencePropertyInfo(model,
					classInfo, property);
		}
	}

	private void simplifyElementPropertyInfoAsElementPropertyInfo(
			final Model model, final CClassInfo classInfo,
			CElementPropertyInfo property) {
		if (property.getTypes().size() > 1) {
			logger.debug(MessageFormat
					.format("Element property [{0}] has several types and will be simplified.",
							property.getName(false)));
			int index = classInfo.getProperties().indexOf(property);
			for (CTypeRef typeRef : property.getTypes()) {
				final CElementPropertyInfo elementPropertyInfo = createElementPropertyInfo(
						model, property, typeRef);
				classInfo.getProperties().add(index++, elementPropertyInfo);
			}
			classInfo.getProperties().remove(property);
		} else {
			logger.warn(MessageFormat
					.format("Element property [{0}] will not be simplified as it does not contain multiple types.",
							property.getName(false)));
		}
	}

	private void simplifyReferencePropertyInfoAsReferencePropertyInfo(
			final Model model, final CClassInfo classInfo,
			CReferencePropertyInfo property) {
		if (property.getElements().size() <= 1 && !property.isMixed()) {
			logger.warn(MessageFormat
					.format("Element reference property [{0}] will not be simplified as it does not contain multiple elements and is not mixed.",
							property.getName(false)));
		} else {
			logger.debug(MessageFormat
					.format("Element reference property [{0}] contains multiple elements or is mixed and will be simplified.",
							property.getName(false)));
			int index = classInfo.getProperties().indexOf(property);
			for (CElement element : property.getElements()) {
				final CReferencePropertyInfo referencePropertyInfo = createReferencePropertyInfo(
						model, property, element);
				classInfo.getProperties().add(index++, referencePropertyInfo);
			}
			if (property.isMixed()) {
				classInfo.getProperties().add(index++,
						createContentReferencePropertyInfo(model, property));
			}
			classInfo.getProperties().remove(property);
		}
	}

	private void simplifyReferencePropertyInfoAsElementPropertyInfo(
			final Model model, final CClassInfo classInfo,
			CReferencePropertyInfo property) {

		if (property.getElements().size() <= 1 && !property.isMixed()) {
			logger.warn(MessageFormat
					.format("Element reference property [{0}] will not be simplified as it does not contain multiple elements and is not mixed.",
							property.getName(false)));
		} else {
			logger.debug(MessageFormat
					.format("Element reference property [{0}] contains multiple elements or is mixed and will be simplified.",
							property.getName(false)));
			int index = classInfo.getProperties().indexOf(property);
			for (CElement element : property.getElements()) {
				final CElementPropertyInfo elementPropertyInfo;
				if (element instanceof CElementInfo) {
					elementPropertyInfo = createElementPropertyInfo(model,
							property, element, (CElementInfo) element);
				} else if (element instanceof CClassInfo) {
					elementPropertyInfo = createElementPropertyInfo(model,
							property, element, (CClassInfo) element);

				} else if (element instanceof CClassRef) {
					logger.error(MessageFormat
							.format("Element reference property [{0}] contains a class reference type [{1}] and therefore cannot be fully simplified as element property.",
									property.getName(false),
									((CClassRef) element).fullName()));
					elementPropertyInfo = null;
					// createElementPropertyInfo(model,
					// property, element, (CClassRef) element);
				} else {
					// TODO WARN
					elementPropertyInfo = null;
					logger.error(MessageFormat.format(
							"Unsupported CElement type [{0}].", element));
				}
				if (elementPropertyInfo != null) {
					classInfo.getProperties().add(index++, elementPropertyInfo);
				}
			}
			if (property.isMixed()) {
				classInfo.getProperties().add(index++,
						createContentReferencePropertyInfo(model, property));
			}
			classInfo.getProperties().remove(property);
		}
	}

	private CElementPropertyInfo createElementPropertyInfo(final Model model,
			CReferencePropertyInfo property, CElement element,
			final CElementInfo elementInfo) {
		final CElementPropertyInfo elementPropertyInfo;
		final String propertyName = createPropertyName(model, property, element);
		final CElementPropertyInfo originalPropertyInfo = elementInfo
				.getProperty();
		elementPropertyInfo = new CElementPropertyInfo(propertyName,
				property.isCollection() ? CollectionMode.REPEATED_ELEMENT
						: CollectionMode.NOT_REPEATED, ID.NONE, null,
				element.getSchemaComponent(), element.getCustomizations(),
				element.getLocator(), false);

		final CAdapter adapter = originalPropertyInfo.getAdapter();
		if (adapter != null) {
			elementPropertyInfo.setAdapter(adapter);
		}

		elementPropertyInfo.getTypes().add(
				new CTypeRef(elementInfo.getContentType(), element
						.getElementName(), elementInfo.getContentType()
						.getTypeName(), false, null));
		return elementPropertyInfo;
	}

	private CElementPropertyInfo createElementPropertyInfo(final Model model,
			CReferencePropertyInfo property, CElement element,
			final CClassInfo classInfo) {
		final CElementPropertyInfo elementPropertyInfo;
		final String propertyName = createPropertyName(model, property, element);
		elementPropertyInfo = new CElementPropertyInfo(propertyName,
				property.isCollection() ? CollectionMode.REPEATED_ELEMENT
						: CollectionMode.NOT_REPEATED, ID.NONE, null,
				element.getSchemaComponent(), element.getCustomizations(),
				element.getLocator(), false);
		elementPropertyInfo.getTypes().add(
				new CTypeRef(classInfo, element.getElementName(), classInfo
						.getTypeName(), false, null));
		return elementPropertyInfo;
	}

	// private CElementPropertyInfo createElementPropertyInfo(final Model model,
	// CReferencePropertyInfo property, CElement element,
	// final CClassRef classInfo) {
	// final CElementPropertyInfo elementPropertyInfo;
	// final String propertyName = createPropertyName(model, element);
	// elementPropertyInfo = new CElementPropertyInfo(propertyName,
	// property.isCollection() ? CollectionMode.REPEATED_ELEMENT
	// : CollectionMode.NOT_REPEATED, ID.NONE, null,
	// element.getSchemaComponent(), element.getCustomizations(),
	// element.getLocator(), false);
	// elementPropertyInfo.getTypes().add(
	// new CTypeRef(classInfo, element.getElementName(), classInfo
	// .getTypeName(), false, null));
	// return elementPropertyInfo;
	// }
	//
	private CReferencePropertyInfo createReferencePropertyInfo(
			final Model model, CReferencePropertyInfo property, CElement element) {
		final String propertyName = createPropertyName(model, property, element);
		final CReferencePropertyInfo referencePropertyInfo = new CReferencePropertyInfo(
				propertyName, property.isCollection(), /* required */false,/* mixed */
				false, element.getSchemaComponent(),
				element.getCustomizations(), element.getLocator(),
				property.isDummy(), property.isContent(),
				property.isMixedExtendedCust());
		referencePropertyInfo.getElements().add(element);
		return referencePropertyInfo;
	}

	private CReferencePropertyInfo createContentReferencePropertyInfo(
			final Model model, CReferencePropertyInfo property) {
		final String propertyName = "Mixed" + property.getName(true);
		final CReferencePropertyInfo referencePropertyInfo = new CReferencePropertyInfo(
				propertyName, /* collection */true, /* required */false, /* mixed */
				true, property.getSchemaComponent(),
				property.getCustomizations(), property.getLocator(), false,
				true, property.isMixedExtendedCust());
		return referencePropertyInfo;
	}

	private CElementPropertyInfo createElementPropertyInfo(final Model model,
			CElementPropertyInfo property, CTypeRef typeRef) {
		final String propertyName = createPropertyName(model, property, typeRef);
		boolean required = false;
		final CElementPropertyInfo elementPropertyInfo = new CElementPropertyInfo(
				propertyName,
				property.isCollection() ? CollectionMode.REPEATED_ELEMENT
						: CollectionMode.NOT_REPEATED, typeRef.getTarget()
						.idUse(), typeRef.getTarget().getExpectedMimeType(),
				property.getSchemaComponent(), property.getCustomizations(),
				property.getLocator(), required);
		final CAdapter adapter = property.getAdapter();
		if (adapter != null) {
			elementPropertyInfo.setAdapter(adapter);
		}
		elementPropertyInfo.getTypes().add(typeRef);
		return elementPropertyInfo;
	}

	private String createPropertyName(final Model model,
			CPropertyInfo propertyInfo, CElement element) {
		final String localPart;
		if (element instanceof CClassRef) {
			final CClassRef classRef = (CClassRef) element;
			final String fullName = classRef.fullName();
			localPart = fullName.substring(fullName.lastIndexOf('.') + 1);
		} else {
			localPart = element.getElementName().getLocalPart();
		}
		final String propertyName = model.getNameConverter().toPropertyName(
				pluralizeIfNecessary(propertyInfo, localPart));
		return propertyName;
	}

	private String createPropertyName(final Model model,
			CPropertyInfo propertyInfo, CTypeRef element) {
		final String propertyName = model.getNameConverter().toPropertyName(
				pluralizeIfNecessary(propertyInfo, element.getTagName().getLocalPart()));
		return propertyName;
	}

	private String pluralizeIfNecessary(CPropertyInfo propertyInfo,
			final String propertyName) {
		return (propertyInfo.isCollection() && isUsePluralForm())? JJavaName
				.getPluralForm(propertyName) : propertyName;
	}

}
