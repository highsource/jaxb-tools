package org.jvnet.jaxb2_commons.plugin.simplehashcode;

import java.util.Arrays;
import java.util.Collection;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.plugin.AbstractParameterizablePlugin;
import org.jvnet.jaxb2_commons.plugin.Customizations;
import org.jvnet.jaxb2_commons.plugin.CustomizedIgnoring;
import org.jvnet.jaxb2_commons.plugin.Ignoring;
import org.jvnet.jaxb2_commons.plugin.simplehashcode.generator.HashCodeCodeGenerator;
import org.jvnet.jaxb2_commons.plugin.simplehashcode.generator.HashCodeCodeGeneratorFactory;
import org.jvnet.jaxb2_commons.plugin.simplehashcode.generator.TypedHashCodeCodeGeneratorFactory;
import org.jvnet.jaxb2_commons.plugin.util.FieldOutlineUtils;
import org.jvnet.jaxb2_commons.plugin.util.StrategyClassUtils;
import org.jvnet.jaxb2_commons.util.FieldAccessorFactory;
import org.jvnet.jaxb2_commons.util.FieldUtils;
import org.jvnet.jaxb2_commons.util.PropertyFieldAccessorFactory;
import org.jvnet.jaxb2_commons.xjc.outline.FieldAccessorEx;
import org.xml.sax.ErrorHandler;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.Aspect;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.Outline;

public class SimpleHashCodePlugin extends AbstractParameterizablePlugin {

	@Override
	public String getOptionName() {
		return "XsimpleHashCode";
	}

	@Override
	public String getUsage() {
		// TODO
		return "TBD";
	}

	private FieldAccessorFactory fieldAccessorFactory = PropertyFieldAccessorFactory.INSTANCE;

	public FieldAccessorFactory getFieldAccessorFactory() {
		return fieldAccessorFactory;
	}

	public void setFieldAccessorFactory(
			FieldAccessorFactory fieldAccessorFactory) {
		this.fieldAccessorFactory = fieldAccessorFactory;
	}

	/*
	 * private String hashCodeStrategyClass =
	 * JAXBHashCodeStrategy.class.getName();
	 * 
	 * public void setHashCodeStrategyClass(String hashCodeStrategy) {
	 * this.hashCodeStrategyClass = hashCodeStrategy; }
	 * 
	 * public String getHashCodeStrategyClass() { return hashCodeStrategyClass;
	 * }
	 * 
	 * public JExpression createHashCodeStrategy(JCodeModel codeModel) { return
	 * StrategyClassUtils.createStrategyInstanceExpression(codeModel,
	 * HashCodeStrategy.class, getHashCodeStrategyClass()); }
	 */

	private Ignoring ignoring = new CustomizedIgnoring(
			org.jvnet.jaxb2_commons.plugin.hashcode.Customizations.IGNORED_ELEMENT_NAME,
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
				.asList(org.jvnet.jaxb2_commons.plugin.hashcode.Customizations.IGNORED_ELEMENT_NAME,
						Customizations.IGNORED_ELEMENT_NAME,
						Customizations.GENERATED_ELEMENT_NAME);
	}

	private TypedHashCodeCodeGeneratorFactory codeGeneratorFactory;

	private TypedHashCodeCodeGeneratorFactory getCodeGeneratorFactory() {
		if (codeGeneratorFactory == null) {
			throw new IllegalStateException(
					"Code generator factory was not set yet.");
		}
		return codeGeneratorFactory;
	}

	@Override
	public boolean run(Outline outline, Options opt, ErrorHandler errorHandler) {
		this.codeGeneratorFactory = new HashCodeCodeGeneratorFactory(
				outline.getCodeModel());

		for (final ClassOutline classOutline : outline.getClasses()) {
			if (!getIgnoring().isIgnored(classOutline)) {
				processClassOutline(classOutline);
			}
		}
		return true;
	}

	protected void processClassOutline(ClassOutline classOutline) {
		final JDefinedClass theClass = classOutline.implClass;
		@SuppressWarnings("unused")
		final JMethod object$hashCode = generateObject$hashCode(classOutline,
				theClass);
	}

	protected JMethod generateObject$hashCode(final ClassOutline classOutline,
			final JDefinedClass theClass) {

		final JCodeModel codeModel = theClass.owner();
		final JMethod object$hashCode = theClass.method(JMod.PUBLIC,
				codeModel.INT, "hashCode");
		{
			final JBlock body = object$hashCode.body();

			final JExpression currentHashCodeExpression = JExpr
					.lit(getCodeGeneratorFactory().getInitial());

			final JVar currentHashCode = body.decl(codeModel.INT,
					"currentHashCode", currentHashCodeExpression);

			final Boolean superClassImplementsHashCode = StrategyClassUtils
					.superClassNotIgnored(classOutline, ignoring);

			if (superClassImplementsHashCode != null) {
				body.assign(
						currentHashCode,
						currentHashCode.mul(
								JExpr.lit(getCodeGeneratorFactory()
										.getMultiplier())).plus(
								JExpr._super().invoke("hashCode")));
			}

			final FieldOutline[] declaredFields = FieldOutlineUtils.filter(
					classOutline.getDeclaredFields(), getIgnoring());

			if (declaredFields.length > 0) {

				for (final FieldOutline fieldOutline : declaredFields) {
					final FieldAccessorEx fieldAccessor = getFieldAccessorFactory()
							.createFieldAccessor(fieldOutline, JExpr._this());
					if (fieldAccessor.isConstant()) {
						continue;
					}
					final JBlock block = body.block();

					String propertyName = fieldOutline.getPropertyInfo()
							.getName(true);
					final JVar value = block.decl(fieldAccessor.getType(),
							"the" + propertyName);

					fieldAccessor.toRawValue(block, value);
					final JType exposedType = fieldAccessor.getType();

					final Collection<JType> possibleTypes = FieldUtils
							.getPossibleTypes(fieldOutline, Aspect.EXPOSED);
					final boolean isAlwaysSet = fieldAccessor.isAlwaysSet();
					final JExpression hasSetValue = fieldAccessor.hasSetValue();
					final HashCodeCodeGenerator codeGenerator = getCodeGeneratorFactory()
							.getCodeGenerator(exposedType);
					block.assign(
							currentHashCode,
							currentHashCode
							.mul(JExpr.lit(getCodeGeneratorFactory().getMultiplier())));
					codeGenerator.append(block, currentHashCode, exposedType,
							possibleTypes, value, hasSetValue, isAlwaysSet);
				}
			}
			body._return(currentHashCode);
		}
		return object$hashCode;
	}
}
