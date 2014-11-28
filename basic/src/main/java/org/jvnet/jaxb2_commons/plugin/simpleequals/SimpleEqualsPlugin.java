package org.jvnet.jaxb2_commons.plugin.simpleequals;

import java.util.Arrays;
import java.util.Collection;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.codemodel.generator.TypedCodeGeneratorFactory;
import org.jvnet.jaxb2_commons.plugin.AbstractParameterizablePlugin;
import org.jvnet.jaxb2_commons.plugin.Customizations;
import org.jvnet.jaxb2_commons.plugin.CustomizedIgnoring;
import org.jvnet.jaxb2_commons.plugin.Ignoring;
import org.jvnet.jaxb2_commons.plugin.simpleequals.generator.EqualsCodeGenerator;
import org.jvnet.jaxb2_commons.plugin.simpleequals.generator.EqualsCodeGeneratorFactory;
import org.jvnet.jaxb2_commons.plugin.util.FieldOutlineUtils;
import org.jvnet.jaxb2_commons.plugin.util.StrategyClassUtils;
import org.jvnet.jaxb2_commons.util.FieldAccessorFactory;
import org.jvnet.jaxb2_commons.util.PropertyFieldAccessorFactory;
import org.jvnet.jaxb2_commons.xjc.outline.FieldAccessorEx;
import org.xml.sax.ErrorHandler;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JConditional;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JOp;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.Outline;

public class SimpleEqualsPlugin extends AbstractParameterizablePlugin {

	@Override
	public String getOptionName() {
		return "XsimpleEquals";
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

	private Ignoring ignoring = new CustomizedIgnoring(
			org.jvnet.jaxb2_commons.plugin.equals.Customizations.IGNORED_ELEMENT_NAME,
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
				.asList(org.jvnet.jaxb2_commons.plugin.equals.Customizations.IGNORED_ELEMENT_NAME,
						Customizations.IGNORED_ELEMENT_NAME,
						Customizations.GENERATED_ELEMENT_NAME);
	}

	private TypedCodeGeneratorFactory<EqualsCodeGenerator> codeGeneratorFactory;

	private TypedCodeGeneratorFactory<EqualsCodeGenerator> getCodeGeneratorFactory() {
		if (codeGeneratorFactory == null) {
			throw new IllegalStateException(
					"Code generator factory was not set yet.");
		}
		return codeGeneratorFactory;
	}

	@Override
	public boolean run(Outline outline, Options opt, ErrorHandler errorHandler) {
		this.codeGeneratorFactory = new EqualsCodeGeneratorFactory(
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
		final JMethod objectEquals = generateObject$equals(classOutline,
				theClass);
	}

	// protected void processEnumOutline(EnumOutline enumOutline) {
	// final JDefinedClass theClass = enumOutline.clazz;
	// ClassUtils._implements(theClass, theClass.owner().ref(Equals.class));
	//
	// @SuppressWarnings("unused")
	// final JMethod equals$equals = generateEquals$equals(enumOutline,
	// theClass);
	// }

	protected JMethod generateObject$equals(final ClassOutline classOutline,
			final JDefinedClass theClass) {

		final JCodeModel codeModel = theClass.owner();
		final JMethod objectEquals = theClass.method(JMod.PUBLIC,
				codeModel.BOOLEAN, "equals");
		{
			final JVar object = objectEquals.param(Object.class, "object");
			final JBlock body = objectEquals.body();

			final JConditional ifNotInstanceof = body._if(JOp.not(object
					._instanceof(theClass)));
			ifNotInstanceof._then()._return(JExpr.FALSE);

			//
			body._if(JExpr._this().eq(object))._then()._return(JExpr.TRUE);

			final Boolean superClassImplementsEquals = StrategyClassUtils
					.superClassNotIgnored(classOutline, ignoring);

			if (superClassImplementsEquals == null) {
				// No superclass
			} else if (superClassImplementsEquals.booleanValue()) {
				body._if(JOp.not(JExpr._super().invoke("equals").arg(object)))
						._then()._return(JExpr.FALSE);

			} else {
				body._if(JOp.not(JExpr._super().invoke("equals").arg(object)))
						._then()._return(JExpr.FALSE);
			}

			final JExpression _this = JExpr._this();

			final FieldOutline[] fields = FieldOutlineUtils.filter(
					classOutline.getDeclaredFields(), getIgnoring());

			if (fields.length > 0) {

				final JVar _that = body.decl(JMod.FINAL, theClass, "that",
						JExpr.cast(theClass, object));

				for (final FieldOutline fieldOutline : fields) {

					final FieldAccessorEx leftFieldAccessor = getFieldAccessorFactory()
							.createFieldAccessor(fieldOutline, _this);
					final FieldAccessorEx rightFieldAccessor = getFieldAccessorFactory()
							.createFieldAccessor(fieldOutline, _that);

					if (leftFieldAccessor.isConstant()
							|| rightFieldAccessor.isConstant()) {
						continue;
					}

					final JBlock block = body.block();

					final String name = fieldOutline.getPropertyInfo().getName(
							true);

					final JType type = leftFieldAccessor.getType();
					final JVar lhsValue = block.decl(type, "lhs" + name);
					leftFieldAccessor.toRawValue(block, lhsValue);

					final JVar rhsValue = block.decl(
							rightFieldAccessor.getType(), "rhs" + name);
					rightFieldAccessor.toRawValue(block, rhsValue);

					final EqualsCodeGenerator codeGenerator = getCodeGeneratorFactory()
							.getCodeGenerator(type);
					codeGenerator.generate(block, type, lhsValue, rhsValue);
				}
			}
			body._return(JExpr.TRUE);
		}
		return objectEquals;
	}
}
