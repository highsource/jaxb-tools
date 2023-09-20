package org.jvnet.jaxb2_commons.plugin.simpleequals;

import java.util.Collection;
import java.util.ListIterator;

import org.apache.commons.lang3.Validate;
import org.jvnet.jaxb2_commons.plugin.codegenerator.Arguments;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JConditional;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JOp;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

public class EqualsArguments implements Arguments<EqualsArguments> {

	private final JCodeModel codeModel;
	private final JVar leftValue;
	private final JExpression leftHasSetValue;

	private final JVar rightValue;
	private final JExpression rightHasSetValue;

	public EqualsArguments(JCodeModel codeModel, JVar leftValue,
			JExpression leftHasSetValue, JVar rightValue,
			JExpression rightHasSetValue) {
		this.codeModel = Validate.notNull(codeModel);
		this.leftValue = Validate.notNull(leftValue);
		this.leftHasSetValue = Validate.notNull(leftHasSetValue);
		this.rightValue = Validate.notNull(rightValue);
		this.rightHasSetValue = Validate.notNull(rightHasSetValue);
	}

	private JCodeModel getCodeModel() {
		return codeModel;
	}

	public JVar leftValue() {
		return leftValue;
	}

	public JExpression leftHasSetValue() {
		return leftHasSetValue;
	}

	public JVar rightValue() {
		return rightValue;
	}

	public JExpression rightHasSetValue() {
		return rightHasSetValue;
	}

	private EqualsArguments spawn(JVar leftValue, JExpression leftHasSetValue,
			JVar rightValue, JExpression rightHasSetValue) {
		return new EqualsArguments(getCodeModel(), leftValue, leftHasSetValue,
				rightValue, rightHasSetValue);
	}

	public EqualsArguments property(JBlock block, String propertyName,
			String propertyMethod, JType declarablePropertyType,
			JType propertyType, Collection<JType> possiblePropertyTypes) {
		final JVar leftPropertyValue = block.decl(JMod.FINAL,
				declarablePropertyType, leftValue().name() + propertyName,
				leftValue().invoke(propertyMethod));
		final JVar rightPropertyValue = block.decl(JMod.FINAL,
				declarablePropertyType, rightValue().name() + propertyName,
				rightValue().invoke(propertyMethod));
		// We assume that primitive properties are always set
		boolean isAlwaysSet = propertyType.isPrimitive();
		final JExpression leftPropertyHasSetValue = isAlwaysSet ? JExpr.TRUE
				: leftPropertyValue.ne(JExpr._null());
		final JExpression rightPropertyHasSetValue = isAlwaysSet ? JExpr.TRUE
				: rightPropertyValue.ne(JExpr._null());
		return spawn(leftPropertyValue, leftPropertyHasSetValue,
				rightPropertyValue, rightPropertyHasSetValue);
	}

	public EqualsArguments iterator(JBlock block, JType elementType) {
		final JVar leftListIterator = block.decl(JMod.FINAL, getCodeModel()
				.ref(ListIterator.class).narrow(elementType), leftValue()
				.name() + "ListIterator", leftValue().invoke("listIterator"));
		final JVar rightListIterator = block.decl(JMod.FINAL, getCodeModel()
				.ref(ListIterator.class).narrow(elementType), rightValue()
				.name() + "ListIterator", rightValue().invoke("listIterator"));

		return spawn(rightListIterator, JExpr.TRUE, leftListIterator,
				JExpr.TRUE);
	}

	public EqualsArguments element(JBlock subBlock, JType elementType) {
		final JVar leftElementValue = subBlock.decl(JMod.FINAL, elementType,
				leftValue().name() + "Element", leftValue().invoke("next"));
		final JVar rightElementValue = subBlock.decl(JMod.FINAL, elementType,
				rightValue().name() + "Element", rightValue().invoke("next"));
		// if (!(o1==null ? o2==null : o1.equals(o2)))
		// return false;
		final boolean isElementAlwaysSet = elementType.isPrimitive();
		final JExpression leftElementHasSetValue = isElementAlwaysSet ? JExpr.TRUE
				: leftElementValue.ne(JExpr._null());
		final JExpression rightElementHasSetValue = isElementAlwaysSet ? JExpr.TRUE
				: rightElementValue.ne(JExpr._null());
		return spawn(leftElementValue, leftElementHasSetValue,
				rightElementValue, rightElementHasSetValue);

	}

	public JExpression _instanceof(JType type) {
		return JOp.cand(leftValue()._instanceof(type), rightValue()
				._instanceof(type));
	}

	public EqualsArguments cast(String suffix, JBlock block,
			JType jaxbElementType, boolean suppressWarnings) {
		final JVar castedLeftValue = block.decl(JMod.FINAL, jaxbElementType,
				leftValue().name() + suffix,
				JExpr.cast(jaxbElementType, leftValue()));
		if (suppressWarnings) {
			castedLeftValue.annotate(SuppressWarnings.class).param("value",
					"unchecked");
		}
		final JVar castedRightValue = block.decl(JMod.FINAL, jaxbElementType,
				rightValue().name() + suffix,
				JExpr.cast(jaxbElementType, rightValue()));
		if (suppressWarnings) {
			castedRightValue.annotate(SuppressWarnings.class).param("value",
					"unchecked");
		}
		return new EqualsArguments(getCodeModel(), castedLeftValue, JExpr.TRUE,
				castedRightValue, JExpr.TRUE);
	}

	public JBlock ifHasSetValue(JBlock block, boolean isAlwaysSet,
			boolean checkForNullRequired) {
		if (isAlwaysSet || !checkForNullRequired) {
			return block;
		} else {
			final JConditional ifLeftHasSetValue = block._if(leftHasSetValue());
			final JConditional ifLeftHasSetValueAndRightHasSetValue = ifLeftHasSetValue
					._then()._if(rightHasSetValue());
			final JBlock subBlock = ifLeftHasSetValueAndRightHasSetValue
					._then();
			ifLeftHasSetValueAndRightHasSetValue._else()._return(JExpr.FALSE);
			ifLeftHasSetValue._elseif(rightHasSetValue())._then()
					._return(JExpr.FALSE);
			return subBlock;
		}
	}

	public JBlock _while(JBlock block) {
		// while(e1.hasNext() && e2.hasNext()) {
		final JBlock _while = block._while(
				JOp.cand(leftValue().invoke("hasNext"),
						rightValue().invoke("hasNext"))).body();
		block._if(
				JOp.cor(leftValue().invoke("hasNext"),
						rightValue().invoke("hasNext")))._then()
				._return(JExpr.FALSE);
		return _while;

	}
}
