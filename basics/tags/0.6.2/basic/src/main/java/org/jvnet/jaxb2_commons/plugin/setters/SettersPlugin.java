package org.jvnet.jaxb2_commons.plugin.setters;

import java.util.Arrays;
import java.util.Collection;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.plugin.AbstractParameterizablePlugin;
import org.jvnet.jaxb2_commons.plugin.CustomizedIgnoring;
import org.jvnet.jaxb2_commons.plugin.Ignoring;
import org.jvnet.jaxb2_commons.plugin.util.FieldOutlineUtils;
import org.xml.sax.ErrorHandler;

import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldAccessor;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.Outline;

public class SettersPlugin extends AbstractParameterizablePlugin {

	private static final JType[] ABSENT = new JType[0];

	@Override
	public String getOptionName() {
		return "Xsetters";
	}

	@Override
	public String getUsage() {
		return "Generates setters for collections.";
	}

	public boolean run(Outline outline, Options opt, ErrorHandler errorHandler) {
		for (final ClassOutline classOutline : outline.getClasses())
			if (!getIgnoring().isIgnored(classOutline)) {

				processClassOutline(classOutline);
			}
		return true;
	}

	protected void processClassOutline(ClassOutline classOutline) {
		final JDefinedClass theClass = classOutline.implClass;

		generateSetters(classOutline, theClass);

	}

	private void generateSetters(ClassOutline classOutline,
			JDefinedClass theClass) {

		final FieldOutline[] declaredFields = FieldOutlineUtils.filter(
				classOutline.getDeclaredFields(), getIgnoring());

		for (final FieldOutline fieldOutline : declaredFields) {

			final String publicName = fieldOutline.getPropertyInfo().getName(
					true);

			final String getterName = "get" + publicName;

			final JMethod getter = theClass.getMethod(getterName, ABSENT);

			if (getter != null) {
				final JType type = getter.type();
				final JType rawType = fieldOutline.getRawType();
				final String setterName = "set" + publicName;
				final JMethod boxifiedSetter = theClass.getMethod(setterName,
						new JType[] { rawType.boxify() });
				final JMethod unboxifiedSetter = theClass.getMethod(setterName,
						new JType[] { rawType.unboxify() });
				final JMethod setter = boxifiedSetter != null ? boxifiedSetter
						: unboxifiedSetter;

				if (setter == null) {
					final JMethod generatedSetter = theClass.method(
							JMod.PUBLIC, theClass.owner().VOID, setterName);
					final JVar value = generatedSetter.param(type, "value");
					final FieldAccessor accessor = fieldOutline.create(JExpr
							._this());
					accessor.unsetValues(generatedSetter.body());
					accessor.fromRawValue(generatedSetter.body(), "draft",
							value);
				}
			}
		}
	}

	private Ignoring ignoring = new CustomizedIgnoring(
			org.jvnet.jaxb2_commons.plugin.setters.Customizations.IGNORED_ELEMENT_NAME);

	public Ignoring getIgnoring() {
		return ignoring;
	}

	public void setIgnoring(Ignoring ignoring) {
		this.ignoring = ignoring;
	}

	@Override
	public Collection<QName> getCustomizationElementNames() {
		return Arrays
				.asList(org.jvnet.jaxb2_commons.plugin.setters.Customizations.IGNORED_ELEMENT_NAME);
	}

}
