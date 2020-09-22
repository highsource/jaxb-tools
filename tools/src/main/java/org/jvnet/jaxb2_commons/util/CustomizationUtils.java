package org.jvnet.jaxb2_commons.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.JAXBIntrospector;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.tools.xjc.model.CAttributePropertyInfo;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CCustomizable;
import com.sun.tools.xjc.model.CCustomizations;
import com.sun.tools.xjc.model.CElement;
import com.sun.tools.xjc.model.CElementInfo;
import com.sun.tools.xjc.model.CElementPropertyInfo;
import com.sun.tools.xjc.model.CEnumConstant;
import com.sun.tools.xjc.model.CEnumLeafInfo;
import com.sun.tools.xjc.model.CPluginCustomization;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CPropertyVisitor;
import com.sun.tools.xjc.model.CReferencePropertyInfo;
import com.sun.tools.xjc.model.CValuePropertyInfo;
import com.sun.tools.xjc.model.Model;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.ElementOutline;
import com.sun.tools.xjc.outline.EnumConstantOutline;
import com.sun.tools.xjc.outline.EnumOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.Outline;

public class CustomizationUtils {

	private static final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY;

	static {
		DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();
		// try {
		// DOCUMENT_BUILDER = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder();
		// } catch (ParserConfigurationException pce) {
		// throw new ExceptionInInitializerError(pce);
		// }
	}

	public static DocumentBuilder getDocumentBuilder() {
		try {
			return DOCUMENT_BUILDER_FACTORY.newDocumentBuilder();
		} catch (ParserConfigurationException pce) {
			throw new AssertionError(pce);
		}
	}

	public static CPluginCustomization createCustomization(QName name) {
		final Document document = getDocumentBuilder().newDocument();
		final Element element = document.createElementNS(name.getNamespaceURI(), name.getLocalPart());
		return createCustomization(element);
	}

	public static CPluginCustomization createCustomization(final Element element) {
		final CPluginCustomization customization = new CPluginCustomization(element, null);
		return customization;
	}

	public static boolean containsCustomization(Outline outline, QName name) {
		final CPluginCustomization customization = findCustomization(outline, name);
		if (customization != null) {
			customization.markAsAcknowledged();
		}
		return customization != null;
	}

	public static boolean containsCustomization(Model model, QName name) {
		final CPluginCustomization customization = findCustomization(model, name);
		if (customization != null) {
			customization.markAsAcknowledged();
		}
		return customization != null;
	}

	public static boolean containsCustomization(ClassOutline classOutline, QName name) {
		final CPluginCustomization customization = findCustomization(classOutline, name);
		if (customization != null) {
			customization.markAsAcknowledged();
		}
		return customization != null;
	}

	public static boolean containsCustomization(EnumOutline enumOutline, QName name) {
		final CPluginCustomization customization = findCustomization(enumOutline, name);
		if (customization != null) {
			customization.markAsAcknowledged();
		}
		return customization != null;
	}

	public static boolean containsCustomization(CClassInfo classInfo, QName name) {
		final CPluginCustomization customization = findCustomization(classInfo, name);
		if (customization != null) {
			customization.markAsAcknowledged();
		}
		return customization != null;
	}

	public static CPluginCustomization findCustomization(ClassOutline classOutline, QName name) {
		final CCustomizations customizations = CustomizationUtils.getCustomizations(classOutline);
		final CPluginCustomization customization = customizations.find(name.getNamespaceURI(), name.getLocalPart());
		if (customization != null) {
			customization.markAsAcknowledged();
		}
		return customization;
	}

	public static CPluginCustomization findCustomization(EnumOutline classOutline, QName name) {
		final CCustomizations customizations = CustomizationUtils.getCustomizations(classOutline);
		final CPluginCustomization customization = customizations.find(name.getNamespaceURI(), name.getLocalPart());
		if (customization != null) {
			customization.markAsAcknowledged();
		}
		return customization;
	}

	public static CPluginCustomization findCustomization(ElementOutline elementOutline, QName name) {
		final CCustomizations customizations = CustomizationUtils.getCustomizations(elementOutline);
		final CPluginCustomization customization = customizations.find(name.getNamespaceURI(), name.getLocalPart());
		if (customization != null) {
			customization.markAsAcknowledged();
		}
		return customization;
	}

	public static List<CPluginCustomization> findCustomizations(ClassOutline classOutline, QName name) {
		return findCustomizations(classOutline.target, name);
	}

	public static List<CPluginCustomization> findCustomizations(EnumOutline enumOutline, QName name) {
		return findCustomizations(enumOutline.target, name);
	}

	public static List<CPluginCustomization> findCustomizations(ElementOutline elementOutline, QName name) {
		return findCustomizations(elementOutline.target, name);
	}

	public static List<CPluginCustomization> findCustomizations(CClassInfo classInfo, QName name) {
		final CCustomizations customizations = CustomizationUtils.getCustomizations(classInfo);

		final List<CPluginCustomization> pluginCustomizations = new LinkedList<CPluginCustomization>();

		for (CPluginCustomization pluginCustomization : customizations) {
			if (fixNull(pluginCustomization.element.getNamespaceURI()).equals(name.getNamespaceURI())
					&& fixNull(pluginCustomization.element.getLocalName()).equals(name.getLocalPart())) {
				pluginCustomization.markAsAcknowledged();
				pluginCustomizations.add(pluginCustomization);
			}
		}

		return pluginCustomizations;
	}

	public static List<CPluginCustomization> findPropertyCustomizationsInPropertyAndClass(CPropertyInfo propertyInfo,
			QName propertyCustomizationName, QName customizationName) {

		final List<CPluginCustomization> foundPropertyCustomizations = new LinkedList<CPluginCustomization>();
		foundPropertyCustomizations.addAll(findPropertyCustomizationsInProperty(propertyInfo, customizationName));
		if (propertyInfo.parent() instanceof CClassInfo) {
			foundPropertyCustomizations.addAll(findPropertyCustomizationsInClass((CClassInfo) propertyInfo.parent(),
					propertyInfo, propertyCustomizationName, customizationName));
		}
		return foundPropertyCustomizations;
	}

	public static List<CPluginCustomization> findPropertyCustomizationsInProperty(CPropertyInfo propertyInfo,
			QName name) {

		final List<CPluginCustomization> foundPropertyCustomizations = new LinkedList<CPluginCustomization>();

		final List<CPluginCustomization> propertyCustomizations = CustomizationUtils.getCustomizations(propertyInfo);

		for (CPluginCustomization propertyCustomization : propertyCustomizations) {
			if (fixNull(propertyCustomization.element.getNamespaceURI()).equals(name.getNamespaceURI())
					&& fixNull(propertyCustomization.element.getLocalName()).equals(name.getLocalPart())) {
				propertyCustomization.markAsAcknowledged();
				foundPropertyCustomizations.add(propertyCustomization);
			}
		}
		return foundPropertyCustomizations;
	}

	public static List<CPluginCustomization> getPropertyCustomizationsInClass(CClassInfo classInfo,
			CPropertyInfo propertyInfo, QName propertyCustomizationName) {
		final List<CPluginCustomization> foundPropertyCustomizations = new LinkedList<CPluginCustomization>();

		final List<CPluginCustomization> classCustomizations = CustomizationUtils.getCustomizations(classInfo);

		for (CPluginCustomization classCustomization : classCustomizations) {
			if (fixNull(classCustomization.element.getNamespaceURI())
					.equals(propertyCustomizationName.getNamespaceURI())
					&& fixNull(classCustomization.element.getLocalName())
							.equals(propertyCustomizationName.getLocalPart())
					&& propertyInfo.getName(false).equals(classCustomization.element.getAttribute("name"))) {

				final Element classCustomizationElement = classCustomization.element;

				final NodeList nodes = classCustomizationElement.getChildNodes();
				final int length = nodes.getLength();
				for (int index = 0; index < length; index++) {
					final Node node = nodes.item(index);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						final Element element = (Element) node;

						classCustomization.markAsAcknowledged();
						final CPluginCustomization propertyCustomization = new CPluginCustomization(element,
								classCustomization.locator);
						propertyCustomization.markAsAcknowledged();
						foundPropertyCustomizations.add(propertyCustomization);
					}
				}
			}
		}
		return foundPropertyCustomizations;
	}

	public static List<CPluginCustomization> findPropertyCustomizationsInClass(CClassInfo classInfo,
			CPropertyInfo propertyInfo, QName propertyCustomizationName, QName customizationName) {

		final List<CPluginCustomization> foundPropertyCustomizations = new LinkedList<CPluginCustomization>();

		final List<CPluginCustomization> classCustomizations = CustomizationUtils.getCustomizations(classInfo);

		for (CPluginCustomization classCustomization : classCustomizations) {
			if (fixNull(classCustomization.element.getNamespaceURI())
					.equals(propertyCustomizationName.getNamespaceURI())
					&& fixNull(classCustomization.element.getLocalName())
							.equals(propertyCustomizationName.getLocalPart())
					&& propertyInfo.getName(false).equals(classCustomization.element.getAttribute("name"))) {

				final Element classCustomizationElement = classCustomization.element;

				final NodeList nodes = classCustomizationElement.getChildNodes();
				final int length = nodes.getLength();
				for (int index = 0; index < length; index++) {
					final Node node = nodes.item(index);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						final Element element = (Element) node;
						if (fixNull(element.getNamespaceURI()).equals(customizationName.getNamespaceURI())
								&& fixNull(element.getLocalName()).equals(customizationName.getLocalPart())) {

							classCustomization.markAsAcknowledged();
							final CPluginCustomization propertyCustomization = new CPluginCustomization(element,
									classCustomization.locator);
							propertyCustomization.markAsAcknowledged();
							foundPropertyCustomizations.add(propertyCustomization);
						}
					}
				}
			}
		}
		return foundPropertyCustomizations;
	}

	public static List<CPluginCustomization> findCustomizations(CEnumLeafInfo enumInfo, QName name) {
		final CCustomizations customizations = CustomizationUtils.getCustomizations(enumInfo);

		final List<CPluginCustomization> pluginCustomizations = new LinkedList<CPluginCustomization>();

		for (CPluginCustomization pluginCustomization : customizations) {
			if (fixNull(pluginCustomization.element.getNamespaceURI()).equals(name.getNamespaceURI())
					&& fixNull(pluginCustomization.element.getLocalName()).equals(name.getLocalPart())) {
				pluginCustomization.markAsAcknowledged();
				pluginCustomizations.add(pluginCustomization);
			}
		}

		return pluginCustomizations;
	}

	public static List<CPluginCustomization> findCustomizations(CElementInfo elementInfo, QName name) {
		final CCustomizations customizations = CustomizationUtils.getCustomizations(elementInfo);

		final List<CPluginCustomization> pluginCustomizations = new LinkedList<CPluginCustomization>();

		for (CPluginCustomization pluginCustomization : customizations) {
			if (fixNull(pluginCustomization.element.getNamespaceURI()).equals(name.getNamespaceURI())
					&& fixNull(pluginCustomization.element.getLocalName()).equals(name.getLocalPart())) {
				pluginCustomization.markAsAcknowledged();
				pluginCustomizations.add(pluginCustomization);
			}
		}

		return pluginCustomizations;
	}

	public static List<CPluginCustomization> findCustomizations(Outline outline, QName name) {
		return findCustomizations(outline.getModel(), name);
	}

	public static List<CPluginCustomization> findCustomizations(Model model, QName name) {
		final CCustomizations customizations = CustomizationUtils.getCustomizations(model);

		final List<CPluginCustomization> pluginCustomizations = new LinkedList<CPluginCustomization>();

		for (CPluginCustomization pluginCustomization : customizations) {
			if (fixNull(pluginCustomization.element.getNamespaceURI()).equals(name.getNamespaceURI())
					&& fixNull(pluginCustomization.element.getLocalName()).equals(name.getLocalPart())) {
				pluginCustomization.markAsAcknowledged();
				pluginCustomizations.add(pluginCustomization);
			}
		}

		return pluginCustomizations;
	}

	public static CPluginCustomization findCustomization(CClassInfo classInfo, QName name) {
		final CCustomizations customizations = CustomizationUtils.getCustomizations(classInfo);
		final CPluginCustomization customization = customizations.find(name.getNamespaceURI(), name.getLocalPart());
		if (customization != null) {
			customization.markAsAcknowledged();
		}
		return customization;
	}

	public static CPluginCustomization findCustomization(CEnumLeafInfo enumLeafInfo, QName name) {
		final CCustomizations customizations = CustomizationUtils.getCustomizations(enumLeafInfo);
		final CPluginCustomization customization = customizations.find(name.getNamespaceURI(), name.getLocalPart());
		if (customization != null) {
			customization.markAsAcknowledged();
		}
		return customization;
	}

	public static CPluginCustomization findCustomization(CElementInfo elementInfo, QName name) {
		final CCustomizations customizations = CustomizationUtils.getCustomizations(elementInfo);
		final CPluginCustomization customization = customizations.find(name.getNamespaceURI(), name.getLocalPart());
		if (customization != null) {
			customization.markAsAcknowledged();
		}
		return customization;
	}

	public static boolean containsCustomization(FieldOutline fieldOutline, QName name) {
		final CPluginCustomization customization = findCustomization(fieldOutline, name);
		if (customization != null) {
			customization.markAsAcknowledged();
		}
		return customization != null;
	}

	public static boolean containsCustomization(CEnumLeafInfo enumLeafInfo, QName name) {
		final CPluginCustomization customization = findCustomization(enumLeafInfo, name);
		if (customization != null) {
			customization.markAsAcknowledged();
		}
		return customization != null;
	}

	public static boolean containsCustomization(CPropertyInfo propertyInfo, QName name) {
		final CPluginCustomization customization = findCustomization(propertyInfo, name);
		if (customization != null) {
			customization.markAsAcknowledged();
		}
		return customization != null;
	}

	public static boolean containsPropertyCustomizationInPropertyOrClass(CPropertyInfo propertyInfo,
			QName propertyCustomizationName, QName customizationName) {
		final List<CPluginCustomization> foundPropertyCustomizations = findPropertyCustomizationsInPropertyAndClass(
				propertyInfo, propertyCustomizationName, customizationName);
		return !foundPropertyCustomizations.isEmpty();
	}

	public static CPluginCustomization findCustomization(FieldOutline fieldOutline, QName name) {
		final CCustomizations customizations = CustomizationUtils.getCustomizations(fieldOutline);
		final CPluginCustomization customization = customizations.find(name.getNamespaceURI(), name.getLocalPart());
		if (customization != null) {
			customization.markAsAcknowledged();
		}
		return customization;
	}

	public static CPluginCustomization findCustomization(CPropertyInfo propertyInfo, QName name) {
		final CCustomizations customizations = CustomizationUtils.getCustomizations(propertyInfo);
		final CPluginCustomization customization = customizations.find(name.getNamespaceURI(), name.getLocalPart());
		if (customization != null) {
			customization.markAsAcknowledged();
		}
		return customization;
	}

	public static CPluginCustomization findCustomization(Outline outline, QName name) {
		final CCustomizations customizations = CustomizationUtils.getCustomizations(outline);
		final CPluginCustomization customization = customizations.find(name.getNamespaceURI(), name.getLocalPart());
		if (customization != null) {
			customization.markAsAcknowledged();
		}
		return customization;
	}

	public static CPluginCustomization findCustomization(Model model, QName name) {
		final CCustomizations customizations = CustomizationUtils.getCustomizations(model);
		final CPluginCustomization customization = customizations.find(name.getNamespaceURI(), name.getLocalPart());
		if (customization != null) {
			customization.markAsAcknowledged();
		}
		return customization;
	}

	// public static BIXPluginCustomization getCustomization(JavaItem item,
	// QName name) {
	// final BIXPluginCustomization[] customizations = getCustomizations(item,
	// name);
	// if (customizations == null || customizations.length < 1) {
	// return null;
	// }
	// else {
	// return customizations[0];
	// }
	// }

	public static FieldOutline findFieldWithCustomization(ClassOutline classOutline, final QName name) {

		for (final FieldOutline fieldOutline : classOutline.getDeclaredFields()) {
			final CCustomizations customizations = getCustomizations(fieldOutline);
			final CPluginCustomization customization = customizations.find(name.getNamespaceURI(), name.getLocalPart());
			if (customization != null)
				return fieldOutline;
		}
		return null;

	}

	public static CPropertyInfo findPropertyWithCustomization(CClassInfo classInfo, final QName name) {

		for (final CPropertyInfo propertyInfo : classInfo.getProperties()) {
			final CCustomizations customizations = getCustomizations(propertyInfo);
			final CPluginCustomization customization = customizations.find(name.getNamespaceURI(), name.getLocalPart());
			if (customization != null)
				return propertyInfo;
		}
		return null;
	}

	public static FieldOutline findInheritedFieldWithCustomization(ClassOutline classOutline, final QName name) {
		for (final FieldOutline fieldOutline : classOutline.getDeclaredFields()) {
			if (containsCustomization(fieldOutline, name)) {
				return fieldOutline;
			}
		}
		final ClassOutline superClassOutline = classOutline.getSuperClass();
		if (superClassOutline != null) {
			return findInheritedFieldWithCustomization(superClassOutline, name);
		} else {
			return null;
		}
	}

	public static CPropertyInfo findInheritedPropertyWithCustomization(CClassInfo classInfo, final QName name) {
		for (final CPropertyInfo propertyInfo : classInfo.getProperties()) {
			if (containsCustomization(propertyInfo, name)) {
				return propertyInfo;
			}
		}
		final CClassInfo superClassInfo = classInfo.getBaseClass();
		if (superClassInfo != null) {
			return findInheritedPropertyWithCustomization(superClassInfo, name);
		} else {
			return null;
		}
	}

	public static CCustomizations getCustomizations(ElementOutline elementOutline) {
		return getCustomizations(elementOutline.target);
	}

	public static CCustomizations getCustomizations(final CElementInfo elementInfo) {
		return elementInfo.getCustomizations();
	}

	public static CCustomizations getCustomizations(EnumOutline enumOutline) {
		return getCustomizations(enumOutline.target);
	}

	public static CCustomizations getCustomizations(EnumConstantOutline enumConstantOutline) {
		return getCustomizations(enumConstantOutline.target);
	}

	public static CCustomizations getCustomizations(FieldOutline fieldOutline) {
		return getCustomizations(fieldOutline.getPropertyInfo());
	}

	public static CCustomizations getCustomizations(final Outline outline) {
		return getCustomizations(outline.getModel());
	}

	public static CCustomizations getCustomizations(final Model model) {
		final CCustomizations main = new CCustomizations(model.getCustomizations());
		return main;
	}

	public static CCustomizations getCustomizations(final CPropertyInfo propertyInfo) {

		final CCustomizations main = new CCustomizations(propertyInfo.getCustomizations());

		final Collection<CCustomizations> elementCustomizations = propertyInfo
				.accept(new CPropertyVisitor<Collection<CCustomizations>>() {
					public Collection<CCustomizations> onAttribute(CAttributePropertyInfo info) {
						return Collections.emptyList();
					}

					public Collection<CCustomizations> onElement(CElementPropertyInfo arg0) {
						return Collections.emptyList();
					}

					public Collection<CCustomizations> onReference(CReferencePropertyInfo info) {

						final List<CCustomizations> elementCustomizations = new ArrayList<CCustomizations>(
								info.getElements().size());

						for (CElement element : info.getElements()) {
							if (!(element instanceof CElementInfo && ((CElementInfo) element).hasClass())) {
								elementCustomizations.add(element.getCustomizations());
							}
						}

						return elementCustomizations;
					}

					public Collection<CCustomizations> onValue(CValuePropertyInfo arg0) {
						return Collections.emptyList();
					};

				});

		CCustomizations customizations = main;

		for (CCustomizations e : elementCustomizations) {
			main.addAll(e);
		}

		return customizations;
	}

	public static CCustomizations getCustomizations(ClassOutline classOutline) {
		return getCustomizations(classOutline.target);
	}

	public static CCustomizations getCustomizations(final CClassInfo classInfo) {
		return classInfo.getCustomizations();
	}

	public static CCustomizations getCustomizations(final CEnumLeafInfo enumLeafInfo) {
		return enumLeafInfo.getCustomizations();
	}

	public static CCustomizations getCustomizations(final CEnumConstant enumConstant) {
		final Object _enumConstant = enumConstant;
		if (_enumConstant instanceof CCustomizable) {
			return ((CCustomizable) _enumConstant).getCustomizations();
		} else {
			return CCustomizations.EMPTY;
		}
	}

	private static String fixNull(String s) {
		if (s == null) {
			return "";
		} else {
			return s;
		}
	}

	public static Object unmarshall(final JAXBContext context, final CPluginCustomization customization)
			throws AssertionError {
		if (customization == null) {
			return null;
		} else

		{
			final Unmarshaller unmarshaller;
			try {
				unmarshaller = context.createUnmarshaller();
			} catch (JAXBException ex) {
				final AssertionError error = new AssertionError("Unmarshaller could not be created.");
				error.initCause(ex);
				throw error;
			}

			try {
				final Object result = unmarshaller.unmarshal(new DOMSource(customization.element));
				final JAXBIntrospector introspector = context.createJAXBIntrospector();
				if (introspector.isElement(result)) {
					return JAXBIntrospector.getValue(result);
				} else {
					return result;
				}
			} catch (JAXBException ex) {
				throw new IllegalArgumentException("Could not unmarshal the customization.", ex);
			}

		}
	}

	public static CPluginCustomization marshal(final JAXBContext context, final QName name, final Object object) {

		try {

			final JAXBIntrospector introspector = context.createJAXBIntrospector();

			final Object value;
			{
				if (introspector.isElement(object)) {
					value = object;
				} else {
					@SuppressWarnings({ "unchecked", "rawtypes" })
					final JAXBElement jaxbElement = new JAXBElement(name, object.getClass(), object);
					value = jaxbElement;
				}

			}

			final Marshaller marshaller = context.createMarshaller();

			final DOMResult result = new DOMResult();

			marshaller.marshal(value, result);

			final Node node = result.getNode();

			final Element element;
			if (node instanceof Element)

			{
				element = (Element) node;
			} else if (node instanceof Document) {
				element = ((Document) node).getDocumentElement();
			} else {
				element = null;
				throw new IllegalArgumentException("Could not marhsall object into an element.");
			}
			return new CPluginCustomization(element, null);
		} catch (JAXBException jaxbex) {
			throw new IllegalArgumentException("Could not marhsall object into an element.", jaxbex);

		}
	}

	public static CPluginCustomization addCustomization(CCustomizable customizable, JAXBContext context, QName name,
			Object object) {
		final CPluginCustomization customization = marshal(context, name, object);
		customizable.getCustomizations().add(customization);
		return customization;
	}
}
