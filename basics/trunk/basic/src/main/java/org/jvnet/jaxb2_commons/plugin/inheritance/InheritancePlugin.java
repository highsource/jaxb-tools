package org.jvnet.jaxb2_commons.plugin.inheritance;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.plugin.AbstractParameterizablePlugin;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;
import org.xml.sax.ErrorHandler;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JDefinedClass;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CElementInfo;
import com.sun.tools.xjc.model.CPluginCustomization;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.ElementOutline;
import com.sun.tools.xjc.outline.EnumOutline;
import com.sun.tools.xjc.outline.Outline;

public class InheritancePlugin extends AbstractParameterizablePlugin {

	@Override
	public String getOptionName() {
		return "Xinheritance";
	}

	@Override
	public String getUsage() {
		return "TBD";
	}

	@Override
	public Collection<QName> getCustomizationElementNames() {
		return Arrays.asList(Customizations.EXTENDS_ELEMENT_NAME,
				Customizations.IMPLEMENTS_ELEMENT_NAME);
	}

	@Override
	public boolean run(Outline outline, Options opt, ErrorHandler errorHandler) {
		for (final ClassOutline classOutline : outline.getClasses()) {
			processClassOutline(classOutline);
		}
		for (final EnumOutline enumOutline : outline.getEnums()) {
			processEnumOutline(enumOutline);
		}
		for (final CElementInfo elementInfo : outline.getModel()
				.getAllElements()) {
			final ElementOutline elementOutline = outline
					.getElement(elementInfo);
			if (elementOutline != null) {
				processElementOutline(elementOutline);
			}

		}
		return true;
	}

	private void processClassOutline(ClassOutline classOutline) {

		generateExtends(classOutline);
		generateImplements(classOutline);

	}

	private void processEnumOutline(EnumOutline enumOutline) {

		generateExtends(enumOutline);
		generateImplements(enumOutline);

	}

	private void processElementOutline(ElementOutline elementOutline) {

		generateExtends(elementOutline);
		generateImplements(elementOutline);

	}

	private void generateExtends(ClassOutline classOutline) {
		final JDefinedClass theClass = classOutline.implClass;
		final CPluginCustomization extendsClassCustomization = CustomizationUtils
				.findCustomization(classOutline,
						Customizations.EXTENDS_ELEMENT_NAME);
		generateExtends(theClass, extendsClassCustomization);
	}

	private void generateExtends(EnumOutline enumOutline) {
		final JDefinedClass theClass = enumOutline.clazz;
		final CPluginCustomization extendsClassCustomization = CustomizationUtils
				.findCustomization(enumOutline,
						Customizations.EXTENDS_ELEMENT_NAME);
		generateExtends(theClass, extendsClassCustomization);
	}

	private void generateExtends(ElementOutline elementOutline) {
		final JDefinedClass theClass = elementOutline.implClass;
		final CPluginCustomization extendsClassCustomization = CustomizationUtils
				.findCustomization(elementOutline,
						Customizations.EXTENDS_ELEMENT_NAME);
		generateExtends(theClass, extendsClassCustomization);
	}

	private void generateExtends(final JDefinedClass theClass,
			final CPluginCustomization extendsClassCustomization)
			throws AssertionError {
		if (extendsClassCustomization != null) {

			final ExtendsClass extendsClass = (ExtendsClass) CustomizationUtils
					.unmarshall(Customizations.getContext(),
							extendsClassCustomization);

			final String name = extendsClass.getClassName();
			final JClass targetClass = theClass.owner().ref(name);
			theClass._extends(targetClass);
		}
	}

	private void generateImplements(ClassOutline classOutline) {
		final JDefinedClass theClass = classOutline.implClass;
		final List<CPluginCustomization> implementsInterfaceCustomizations = CustomizationUtils
				.findCustomizations(classOutline,
						Customizations.IMPLEMENTS_ELEMENT_NAME);
		generateImplements(theClass, implementsInterfaceCustomizations);
	}

	private void generateImplements(EnumOutline enumOutline) {
		final JDefinedClass theClass = enumOutline.clazz;
		final List<CPluginCustomization> implementsInterfaceCustomizations = CustomizationUtils
				.findCustomizations(enumOutline,
						Customizations.IMPLEMENTS_ELEMENT_NAME);
		generateImplements(theClass, implementsInterfaceCustomizations);
	}

	private void generateImplements(ElementOutline elementOutline) {
		final JDefinedClass theClass = elementOutline.implClass;
		final List<CPluginCustomization> implementsInterfaceCustomizations = CustomizationUtils
				.findCustomizations(elementOutline,
						Customizations.IMPLEMENTS_ELEMENT_NAME);
		generateImplements(theClass, implementsInterfaceCustomizations);
	}

	private void generateImplements(final JDefinedClass theClass,
			final List<CPluginCustomization> implementsInterfaceCustomizations)
			throws AssertionError {
		for (final CPluginCustomization implementsInterfaceCustomization : implementsInterfaceCustomizations) {
			if (implementsInterfaceCustomization != null) {

				final ImplementsInterface implementsInterface = (ImplementsInterface) org.jvnet.jaxb2_commons.util.CustomizationUtils
						.unmarshall(Customizations.getContext(),
								implementsInterfaceCustomization);
				if (implementsInterface.getInterfaceName() != null) {
					final JClass targetClass = theClass.owner().ref(
							implementsInterface.getInterfaceName());
					theClass._implements(targetClass);
				}
			}
		}
	}
}
