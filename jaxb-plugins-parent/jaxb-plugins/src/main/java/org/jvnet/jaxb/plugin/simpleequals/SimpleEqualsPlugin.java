package org.jvnet.jaxb.plugin.simpleequals;

import java.util.Collection;

import javax.xml.namespace.QName;

import org.jvnet.jaxb.plugin.codegenerator.AbstractCodeGeneratorPlugin;
import org.jvnet.jaxb.plugin.codegenerator.CodeGenerator;
import org.jvnet.jaxb.plugin.util.FieldOutlineUtils;
import org.jvnet.jaxb.plugin.util.StrategyClassUtils;
import org.jvnet.jaxb.util.FieldUtils;
import org.jvnet.jaxb.xjc.outline.FieldAccessorEx;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JOp;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;
import com.sun.tools.xjc.outline.Aspect;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;

public class SimpleEqualsPlugin extends
		AbstractCodeGeneratorPlugin<EqualsArguments> {

	@Override
	public String getOptionName() {
		return "XsimpleEquals";
	}

	@Override
	public String getUsage() {
		return "  -XsimpleEquals :  Generate reflection-free runtime-free equals(Object that) methods.\n" +
	           "                    See https://github.com/highsource/jaxb-tools/wiki/JAXB2-SimpleEquals-Plugin";
	}

	@Override
	protected QName getSpecialIgnoredElementName() {
		return org.jvnet.jaxb.plugin.equals.Customizations.IGNORED_ELEMENT_NAME;
	}

	@Override
	protected CodeGenerator<EqualsArguments> createCodeGenerator(
			JCodeModel codeModel) {
		return new EqualsCodeGenerator(codeModel);
	}

	@Override
	protected void generate(ClassOutline classOutline, JDefinedClass theClass) {
		final JCodeModel codeModel = theClass.owner();
		final JMethod objectEquals = theClass.method(JMod.PUBLIC,
				codeModel.BOOLEAN, "equals");
		objectEquals.annotate(Override.class);
		final JVar object = objectEquals.param(Object.class, "object");
		final JBlock body = objectEquals.body();

		JExpression objectIsNull = object.eq(JExpr._null());
		JExpression notTheSameType = JExpr._this().invoke("getClass").ne(object.invoke("getClass"));
		body._if(JOp.cor(objectIsNull, notTheSameType))
				._then()._return(JExpr.FALSE);

		body._if(JExpr._this().eq(object))._then()._return(JExpr.TRUE);
		final Boolean superClassNotIgnored = StrategyClassUtils
				.superClassNotIgnored(classOutline, getIgnoring());

		if (superClassNotIgnored != null) {
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

				final String name = fieldOutline.getPropertyInfo()
						.getName(true);

				final JType type = leftFieldAccessor.getType();
				final JVar leftValue = block.decl(type, "left" + name);
				leftFieldAccessor.toRawValue(block, leftValue);

				final JVar rightValue = block.decl(
						rightFieldAccessor.getType(), "right" + name);
				rightFieldAccessor.toRawValue(block, rightValue);

				final JType exposedType = leftFieldAccessor.getType();

				final Collection<JType> possibleTypes = FieldUtils
						.getPossibleTypes(fieldOutline, Aspect.EXPOSED);
				final boolean isAlwaysSet = leftFieldAccessor.isAlwaysSet();
//				final JExpression leftHasSetValue = exposedType.isPrimitive() ? JExpr.TRUE
//						: leftValue.ne(JExpr._null());
				final JExpression leftHasSetValue = (leftFieldAccessor.isAlwaysSet() || leftFieldAccessor
						.hasSetValue() == null) ? JExpr.TRUE
						: leftFieldAccessor.hasSetValue();

//				final JExpression rightHasSetValue = exposedType.isPrimitive() ? JExpr.TRUE
//						: rightValue.ne(JExpr._null());

				final JExpression rightHasSetValue = (rightFieldAccessor.isAlwaysSet() || rightFieldAccessor
						.hasSetValue() == null) ? JExpr.TRUE
						: rightFieldAccessor.hasSetValue();

				getCodeGenerator().generate(
						block,
						exposedType,
						possibleTypes,
						isAlwaysSet,
						new EqualsArguments(codeModel, leftValue,
								leftHasSetValue, rightValue, rightHasSetValue));
			}
		}
		body._return(JExpr.TRUE);

	}
}
