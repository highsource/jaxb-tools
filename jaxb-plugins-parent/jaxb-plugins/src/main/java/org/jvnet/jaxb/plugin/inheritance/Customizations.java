package org.jvnet.jaxb.plugin.inheritance;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBContextFactory;
import jakarta.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import org.glassfish.jaxb.runtime.v2.ContextFactory;
import org.jvnet.jaxb.util.CustomizationUtils;

import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CPluginCustomization;

public class Customizations {

	public static String NAMESPACE_URI = "urn:jaxb.jvnet.org:plugin:inheritance";

	public static QName OBJECT_FACTORY_ELEMENT_NAME = new QName(NAMESPACE_URI, "objectFactory");

	public static QName EXTENDS_ELEMENT_NAME = new QName(NAMESPACE_URI, "extends");

	public static QName IMPLEMENTS_ELEMENT_NAME = new QName(NAMESPACE_URI, "implements");

	private static final JAXBContext context;
	static {
		try {
            context = ContextFactory.createContext(
                ObjectFactory.class.getPackage().getName(),
                ObjectFactory.class.getClassLoader(),
                null);
		} catch (JAXBException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public static JAXBContext getContext() {
		return context;
	}

	public static void _extends(CClassInfo classInfo, String className) {
		final ExtendsClass extendsClass = new ExtendsClass();
		extendsClass.setClassName(className);
		final CPluginCustomization customization = CustomizationUtils.marshal(getContext(), Customizations.EXTENDS_ELEMENT_NAME, extendsClass);
		classInfo.getCustomizations().add(customization);
		customization.markAsAcknowledged();

	}

	public static void _implements(CClassInfo classInfo, String... interfaceNames) {
		for (String interfaceName : interfaceNames) {
			final ImplementsInterface implementsInterface = new ImplementsInterface();
			implementsInterface.setInterfaceName(interfaceName);
			final CPluginCustomization customization = CustomizationUtils.marshal(getContext(), Customizations.IMPLEMENTS_ELEMENT_NAME, implementsInterface);
			customization.markAsAcknowledged();
			classInfo.getCustomizations().add(customization);
		}

	}

}
