package org.jvnet.jaxb.plugin.codegenerator;

import java.util.Arrays;
import java.util.Collection;

import javax.xml.namespace.QName;

import org.jvnet.jaxb.plugin.AbstractParameterizablePlugin;
import org.jvnet.jaxb.plugin.Customizations;
import org.jvnet.jaxb.plugin.CustomizedIgnoring;
import org.jvnet.jaxb.plugin.Ignoring;
import org.jvnet.jaxb.util.FieldAccessorFactory;
import org.jvnet.jaxb.util.PropertyFieldAccessorFactory;
import org.xml.sax.ErrorHandler;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.Outline;

public abstract class AbstractCodeGeneratorPlugin<A extends Arguments<A>> extends
		AbstractParameterizablePlugin {

	private FieldAccessorFactory fieldAccessorFactory = PropertyFieldAccessorFactory.INSTANCE;

	public FieldAccessorFactory getFieldAccessorFactory() {
		return fieldAccessorFactory;
	}

	public void setFieldAccessorFactory(
			FieldAccessorFactory fieldAccessorFactory) {
		this.fieldAccessorFactory = fieldAccessorFactory;
	}

	private Ignoring ignoring = new CustomizedIgnoring(
			getSpecialIgnoredElementName(),
			Customizations.IGNORED_ELEMENT_NAME,
			Customizations.GENERATED_ELEMENT_NAME);

	protected abstract QName getSpecialIgnoredElementName();

	public Ignoring getIgnoring() {
		return ignoring;
	}

	public void setIgnoring(Ignoring ignoring) {
		this.ignoring = ignoring;
	}

	@Override
	public Collection<QName> getCustomizationElementNames() {
		return Arrays.asList(getSpecialIgnoredElementName(),
				Customizations.IGNORED_ELEMENT_NAME,
				Customizations.GENERATED_ELEMENT_NAME);
	}

	private CodeGenerator<A> codeGenerator;

	protected CodeGenerator<A> getCodeGenerator() {
		if (codeGenerator == null) {
			throw new IllegalStateException("Code generator was not set yet.");
		}
		return codeGenerator;
	}

	protected abstract CodeGenerator<A> createCodeGenerator(JCodeModel codeModel);

	@Override
	public boolean run(Outline outline, Options opt, ErrorHandler errorHandler) {
		this.codeGenerator = createCodeGenerator(outline.getCodeModel());
		for (final ClassOutline classOutline : outline.getClasses()) {
			if (!getIgnoring().isIgnored(classOutline)) {
				processClassOutline(classOutline);
			}
		}
		return true;
	}

	protected void processClassOutline(ClassOutline classOutline) {
		final JDefinedClass theClass = classOutline.implClass;
		generate(classOutline, theClass);
	}

	protected abstract void generate(final ClassOutline classOutline,
			final JDefinedClass theClass);
}
