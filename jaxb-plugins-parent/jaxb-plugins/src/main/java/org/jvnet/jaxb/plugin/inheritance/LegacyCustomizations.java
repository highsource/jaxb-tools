package org.jvnet.jaxb.plugin.inheritance;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import org.glassfish.jaxb.runtime.v2.ContextFactory;
import org.jvnet.jaxb.util.CustomizationUtils;

import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CPluginCustomization;

/**
 * Customizations previous namespace handling
 *
 * @deprecated since 3.0, for removal
 */
@Deprecated
public class LegacyCustomizations {

	public static String LEGACY_NAMESPACE_URI = "http://jaxb2-commons.dev.java.net/basic/inheritance";

	public static QName LEGACY_OBJECT_FACTORY_ELEMENT_NAME = new QName(LEGACY_NAMESPACE_URI, "objectFactory");

	public static QName LEGACY_EXTENDS_ELEMENT_NAME = new QName(LEGACY_NAMESPACE_URI, "extends");

	public static QName LEGACY_IMPLEMENTS_ELEMENT_NAME = new QName(LEGACY_NAMESPACE_URI, "implements");

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
		final LegacyExtendsClass extendsClass = new LegacyExtendsClass();
		extendsClass.setClassName(className);
		final CPluginCustomization customization = CustomizationUtils.marshal(getContext(), LegacyCustomizations.LEGACY_EXTENDS_ELEMENT_NAME, extendsClass);
		classInfo.getCustomizations().add(customization);
		customization.markAsAcknowledged();

	}

	public static void _implements(CClassInfo classInfo, String... interfaceNames) {
		for (String interfaceName : interfaceNames) {
			final LegacyImplementsInterface implementsInterface = new LegacyImplementsInterface();
			implementsInterface.setInterfaceName(interfaceName);
			final CPluginCustomization customization = CustomizationUtils.marshal(getContext(), LegacyCustomizations.LEGACY_IMPLEMENTS_ELEMENT_NAME, implementsInterface);
			customization.markAsAcknowledged();
			classInfo.getCustomizations().add(customization);
		}

	}

}
