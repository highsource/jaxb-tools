package org.jvnet.jaxb2_commons.plugin.inheritance;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.plugin.AbstractParameterizablePlugin;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;
import org.xml.sax.ErrorHandler;

import com.sun.codemodel.JClass;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CPluginCustomization;
import com.sun.tools.xjc.outline.ClassOutline;
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
		return true;
	}

	protected void processClassOutline(ClassOutline classOutline) {

		generateExtends(classOutline);
		generateImplements(classOutline);

	}

	private void generateExtends(ClassOutline classOutline) {
		final CPluginCustomization extendsClassCustomization = CustomizationUtils
				.findCustomization(classOutline,
						Customizations.EXTENDS_ELEMENT_NAME);
		if (extendsClassCustomization != null) {

			final ExtendsClass extendsClass = (ExtendsClass) CustomizationUtils
					.unmarshall(Customizations.getContext(),
							extendsClassCustomization);

			final String name = extendsClass.getClassName();
			final JClass targetClass = classOutline.implClass.owner().ref(name);
			classOutline.implClass._extends(targetClass);
		}
	}

	private void generateImplements(ClassOutline classOutline) {
		final List<CPluginCustomization> implementsInterfaceCustomizations = CustomizationUtils
				.findCustomizations(classOutline,
						Customizations.IMPLEMENTS_ELEMENT_NAME);
		for (final CPluginCustomization implementsInterfaceCustomization : implementsInterfaceCustomizations) {
			if (implementsInterfaceCustomization != null) {

				final ImplementsInterface implementsInterface = (ImplementsInterface) org.jvnet.jaxb2_commons.util.CustomizationUtils
						.unmarshall(Customizations.getContext(),
								implementsInterfaceCustomization);
				if (implementsInterface.getInterfaceName() != null) {
					final JClass targetClass = classOutline.implClass.owner()
							.ref(implementsInterface.getInterfaceName());
					classOutline.implClass._implements(targetClass);
				}
			}
		}
	}
}
