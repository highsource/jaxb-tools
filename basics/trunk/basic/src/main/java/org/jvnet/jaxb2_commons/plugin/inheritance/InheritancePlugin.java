package org.jvnet.jaxb2_commons.plugin.inheritance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.plugin.AbstractParameterizablePlugin;
import org.jvnet.jaxb2_commons.plugin.inheritance.util.JavaTypeParser;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;
import org.xml.sax.ErrorHandler;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CClassRef;
import com.sun.tools.xjc.model.CCustomizations;
import com.sun.tools.xjc.model.CElementInfo;
import com.sun.tools.xjc.model.CPluginCustomization;
import com.sun.tools.xjc.model.Model;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.ElementOutline;
import com.sun.tools.xjc.outline.EnumOutline;
import com.sun.tools.xjc.outline.Outline;
import com.sun.tools.xjc.outline.PackageOutline;
import com.sun.tools.xjc.reader.xmlschema.bindinfo.BIEnum;

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
				Customizations.IMPLEMENTS_ELEMENT_NAME,
				Customizations.OBJECT_FACTORY_ELEMENT_NAME);
	}

	@Override
	public boolean run(Outline outline, Options opt, ErrorHandler errorHandler) {
		final Map<String, JClass> knownClasses = new HashMap<String, JClass>();
		final Map<JClass, CClassInfo> knownClassInfos = new IdentityHashMap<JClass, CClassInfo>();

		for (final ClassOutline classOutline : outline.getClasses()) {
			knownClasses.put(classOutline.implClass.fullName(),
					classOutline.implClass);
			knownClassInfos.put(classOutline.implClass, classOutline.target);
		}
		for (final ClassOutline classOutline : outline.getClasses()) {
			processClassOutline(classOutline, knownClasses, knownClassInfos);
		}
		for (final EnumOutline enumOutline : outline.getEnums()) {
			processEnumOutline(enumOutline, knownClasses);
		}
		for (final CElementInfo elementInfo : outline.getModel()
				.getAllElements()) {
			final ElementOutline elementOutline = outline
					.getElement(elementInfo);
			if (elementOutline != null) {
				processElementOutline(elementOutline, knownClasses);
			}
		}
		processPackageOutlines(outline, knownClasses);
		return true;
	}

	private void processClassOutline(ClassOutline classOutline,
			Map<String, JClass> knownClasses,
			Map<JClass, CClassInfo> knownClassInfos) {

		generateExtends(classOutline, knownClasses, knownClassInfos);
		generateImplements(classOutline, knownClasses);

	}

	private void processEnumOutline(EnumOutline enumOutline,
			Map<String, JClass> knownClasses) {

		generateExtends(enumOutline, knownClasses);
		generateImplements(enumOutline, knownClasses);

	}

	private void processElementOutline(ElementOutline elementOutline,
			Map<String, JClass> knownClasses) {

		generateExtends(elementOutline, knownClasses);
		generateImplements(elementOutline, knownClasses);

	}

	private void processPackageOutlines(Outline outline,
			Map<String, JClass> knownClasses) {
		List<CPluginCustomization> customizations = CustomizationUtils
				.findCustomizations(outline,
						Customizations.OBJECT_FACTORY_ELEMENT_NAME);

		for (CPluginCustomization customization : customizations) {
			final ObjectFactoryCustomization objectFactoryCustomization = (ObjectFactoryCustomization) CustomizationUtils
					.unmarshall(Customizations.getContext(), customization);

			final String packageName = objectFactoryCustomization
					.getPackageName();

			if (packageName != null) {
				for (PackageOutline packageOutline : outline
						.getAllPackageContexts()) {
					final JDefinedClass theClass = packageOutline
							.objectFactory();
					if (packageName.equals(packageOutline._package().name())) {
						ExtendsClass extendsClass = objectFactoryCustomization
								.getExtendsClass();
						if (extendsClass != null) {
							generateExtends(theClass, extendsClass,
									knownClasses);
						}
						List<ImplementsInterface> implementsInterfaces = objectFactoryCustomization
								.getImplementsInterface();
						if (implementsInterfaces != null) {
							for (ImplementsInterface implementsInterface : implementsInterfaces) {
								if (implementsInterface != null) {
									generateImplements(theClass,
											implementsInterface, knownClasses);
								}
							}
						}
					}
				}
			}
		}
	}

	private JClass generateExtends(ClassOutline classOutline,
			Map<String, JClass> knownClasses,
			Map<JClass, CClassInfo> knownClassInfos) {
		final JDefinedClass theClass = classOutline.implClass;
		final CPluginCustomization extendsClassCustomization = CustomizationUtils
				.findCustomization(classOutline,
						Customizations.EXTENDS_ELEMENT_NAME);
		JClass targetClass = generateExtends(theClass,
				extendsClassCustomization, knownClasses);

		final CClassInfo classInfo = classOutline.target;
		if (targetClass != null && classInfo.getBaseClass() == null
				&& classInfo.getRefBaseClass() == null) {
			final CClassInfo targetClassInfo = knownClassInfos.get(targetClass);
			if (targetClassInfo == null && classInfo.getRefBaseClass() == null) {
				final Model model = classInfo.model;
				// BIEnum as BIClass is protected too much
				final BIEnum decl = new BIEnum();
				decl.ref = targetClass.fullName();
				final CClassRef baseClass = new CClassRef(model,
						classInfo.getSchemaComponent(), decl,
						new CCustomizations());
				classInfo.setBaseClass(baseClass);
			} else if (targetClassInfo != null
					&& classInfo.getBaseClass() == null) {
				classInfo.setBaseClass(targetClassInfo);
			}
		}
		return targetClass;
	}

	private JClass generateExtends(EnumOutline enumOutline,
			Map<String, JClass> knownClasses) {
		final JDefinedClass theClass = enumOutline.clazz;
		final CPluginCustomization extendsClassCustomization = CustomizationUtils
				.findCustomization(enumOutline,
						Customizations.EXTENDS_ELEMENT_NAME);
		return generateExtends(theClass, extendsClassCustomization,
				knownClasses);
	}

	private JClass generateExtends(ElementOutline elementOutline,
			Map<String, JClass> knownClasses) {
		final JDefinedClass theClass = elementOutline.implClass;
		final CPluginCustomization extendsClassCustomization = CustomizationUtils
				.findCustomization(elementOutline,
						Customizations.EXTENDS_ELEMENT_NAME);
		return generateExtends(theClass, extendsClassCustomization,
				knownClasses);
	}

	private JClass generateExtends(final JDefinedClass theClass,
			final CPluginCustomization extendsClassCustomization,
			Map<String, JClass> knownClasses) throws AssertionError {
		if (extendsClassCustomization != null) {

			final ExtendsClass extendsClass = (ExtendsClass) CustomizationUtils
					.unmarshall(Customizations.getContext(),
							extendsClassCustomization);

			return generateExtends(theClass, extendsClass, knownClasses);
		} else {
			return null;
		}
	}

	private JClass generateExtends(final JDefinedClass theClass,
			final ExtendsClass extendsClass, Map<String, JClass> knownClasses) {
		if (extendsClass.getClassName() != null) {
			final String _class = extendsClass.getClassName();
			final JClass targetClass = parseClass(_class, theClass.owner(),
					knownClasses);
			theClass._extends(targetClass);
			return targetClass;
		} else {
			return null;
		}
	}

	private List<JClass> generateImplements(ClassOutline classOutline,
			Map<String, JClass> knownClasses) {
		final JDefinedClass theClass = classOutline.implClass;
		final List<CPluginCustomization> implementsInterfaceCustomizations = CustomizationUtils
				.findCustomizations(classOutline,
						Customizations.IMPLEMENTS_ELEMENT_NAME);
		return generateImplements(theClass, implementsInterfaceCustomizations,
				knownClasses);
	}

	private List<JClass> generateImplements(EnumOutline enumOutline,
			Map<String, JClass> knownClasses) {
		final JDefinedClass theClass = enumOutline.clazz;
		final List<CPluginCustomization> implementsInterfaceCustomizations = CustomizationUtils
				.findCustomizations(enumOutline,
						Customizations.IMPLEMENTS_ELEMENT_NAME);
		return generateImplements(theClass, implementsInterfaceCustomizations,
				knownClasses);
	}

	private List<JClass> generateImplements(ElementOutline elementOutline,
			Map<String, JClass> knownClasses) {
		final JDefinedClass theClass = elementOutline.implClass;
		final List<CPluginCustomization> implementsInterfaceCustomizations = CustomizationUtils
				.findCustomizations(elementOutline,
						Customizations.IMPLEMENTS_ELEMENT_NAME);
		return generateImplements(theClass, implementsInterfaceCustomizations,
				knownClasses);
	}

	private List<JClass> generateImplements(final JDefinedClass theClass,
			final List<CPluginCustomization> implementsInterfaceCustomizations,
			Map<String, JClass> knownClasses) throws AssertionError {
		final List<JClass> implementedInterfaces = new ArrayList<JClass>(
				implementsInterfaceCustomizations.size());
		for (final CPluginCustomization implementsInterfaceCustomization : implementsInterfaceCustomizations) {
			if (implementsInterfaceCustomization != null) {

				final ImplementsInterface implementsInterface = (ImplementsInterface) org.jvnet.jaxb2_commons.util.CustomizationUtils
						.unmarshall(Customizations.getContext(),
								implementsInterfaceCustomization);

				final JClass implementedInterface = generateImplements(
						theClass, implementsInterface, knownClasses);
				if (implementedInterface != null) {
					implementedInterfaces.add(implementedInterface);
				}
			}
		}
		return implementedInterfaces;
	}

	private JClass generateImplements(final JDefinedClass theClass,
			final ImplementsInterface implementsInterface,
			Map<String, JClass> knownClasses) {

		String _interface = implementsInterface.getInterfaceName();
		if (_interface != null) {
			final JClass targetClass = parseClass(_interface, theClass.owner(),
					knownClasses);
			theClass._implements(targetClass);
			return targetClass;
		} else {
			return null;
		}
	}

	private JClass parseClass(String _class, JCodeModel codeModel,
			Map<String, JClass> knownClasses) {
		return new JavaTypeParser(knownClasses).parseClass(_class, codeModel);
	}

}
