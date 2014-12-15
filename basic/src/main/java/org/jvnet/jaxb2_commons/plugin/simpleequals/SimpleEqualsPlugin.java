package org.jvnet.jaxb2_commons.plugin.simpleequals;

import java.util.Collection;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.plugin.codegenerator.AbstractCodeGeneratorPlugin;
import org.jvnet.jaxb2_commons.plugin.codegenerator.CodeGenerator;
import org.jvnet.jaxb2_commons.plugin.util.FieldOutlineUtils;
import org.jvnet.jaxb2_commons.plugin.util.StrategyClassUtils;
import org.jvnet.jaxb2_commons.util.FieldUtils;
import org.jvnet.jaxb2_commons.xjc.outline.FieldAccessorEx;

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
import com.sun.tools.xjc.model.Aspect;
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
		return "  -XsimpleEquals :  Generate reflection-free equals(Object that) methods.";
	}

	@Override
	protected QName getSpecialIgnoredElementName() {
		return org.jvnet.jaxb2_commons.plugin.equals.Customizations.IGNORED_ELEMENT_NAME;
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
		final JVar object = objectEquals.param(Object.class, "object");
		final JBlock body = objectEquals.body();
		final JConditional ifNotInstanceof = body._if(JOp.not(object
				._instanceof(theClass)));
		ifNotInstanceof._then()._return(JExpr.FALSE);
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
				leftFieldAccessor.getValue(block, leftValue, false);

				final JVar rightValue = block.decl(
						rightFieldAccessor.getType(), "right" + name);
				rightFieldAccessor.getValue(block, rightValue, false);

				final JType exposedType = leftFieldAccessor.getType();

				final Collection<JType> possibleTypes = FieldUtils
						.getPossibleTypes(fieldOutline, Aspect.EXPOSED);
				final boolean isAlwaysSet = leftFieldAccessor.isAlwaysSet();
				final JExpression leftHasSetValue = exposedType.isPrimitive() ? JExpr.TRUE
						: leftValue.ne(JExpr._null());
				final JExpression rightHasSetValue = exposedType.isPrimitive() ? JExpr.TRUE
						: rightValue.ne(JExpr._null());

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
