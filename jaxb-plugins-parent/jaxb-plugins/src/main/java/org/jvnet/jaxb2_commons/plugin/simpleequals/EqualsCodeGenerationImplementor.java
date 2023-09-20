package org.jvnet.jaxb2_commons.plugin.simpleequals;

import java.util.Arrays;

import org.jvnet.jaxb2_commons.plugin.codegenerator.AbstractCodeGenerationImplementor;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JOp;

public class EqualsCodeGenerationImplementor extends
		AbstractCodeGenerationImplementor<EqualsArguments> {

	public EqualsCodeGenerationImplementor(JCodeModel codeModel) {
		super(codeModel);
	}

	private void returnFalseIfNotEqualsCondition(EqualsArguments arguments,
			JBlock block, boolean isAlwaysSet,
			final JExpression notEqualsCondition) {
		arguments.ifHasSetValue(block, isAlwaysSet, true)
				._if(notEqualsCondition)._then()._return(JExpr.FALSE);
	}

	private void returnFalseIfNe(EqualsArguments arguments, JBlock block,
			boolean isAlwaysSet) {
		returnFalseIfNotEqualsCondition(arguments, block, isAlwaysSet,
				JOp.ne(arguments.leftValue(), arguments.rightValue()));
	}

	@Override
	public void onArray(JBlock block, boolean isAlwaysSet,
			EqualsArguments arguments) {
		returnFalseIfNotEqualsCondition(
				arguments,
				block,
				isAlwaysSet,
				getCodeModel().ref(Arrays.class).staticInvoke("equals")
						.arg(arguments.leftValue()).arg(arguments.rightValue())
						.not());
	}

	@Override
	public void onBoolean(EqualsArguments arguments, JBlock block,
			boolean isAlwaysSet) {
		returnFalseIfNe(arguments, block, isAlwaysSet);
	}

	@Override
	public void onByte(EqualsArguments arguments, JBlock block,
			boolean isAlwaysSet) {
		returnFalseIfNe(arguments, block, isAlwaysSet);
	}

	@Override
	public void onChar(EqualsArguments arguments, JBlock block,
			boolean isAlwaysSet) {
		returnFalseIfNe(arguments, block, isAlwaysSet);
	}

	@Override
	public void onDouble(EqualsArguments arguments, JBlock block,
			boolean isAlwaysSet) {
		final JClass Double$class = getCodeModel().ref(Double.class);
		final JExpression leftValueLongBits = Double$class.staticInvoke(
				"doubleToLongBits").arg(arguments.leftValue());
		final JExpression rightValueLongBits = Double$class.staticInvoke(
				"doubleToLongBits").arg(arguments.rightValue());
		returnFalseIfNotEqualsCondition(arguments, block, isAlwaysSet,
				JOp.ne(leftValueLongBits, rightValueLongBits));
	}

	@Override
	public void onFloat(EqualsArguments arguments, JBlock block,
			boolean isAlwaysSet) {
		final JClass Float$class = getCodeModel().ref(Float.class);
		final JExpression leftValueLongBits = Float$class.staticInvoke(
				"floatToIntBits").arg(arguments.leftValue());
		final JExpression rightValueLongBits = Float$class.staticInvoke(
				"floatToIntBits").arg(arguments.rightValue());
		returnFalseIfNotEqualsCondition(arguments, block, isAlwaysSet,
				JOp.ne(leftValueLongBits, rightValueLongBits));
	}

	@Override
	public void onInt(EqualsArguments arguments, JBlock block,
			boolean isAlwaysSet) {
		returnFalseIfNe(arguments, block, isAlwaysSet);
	}

	@Override
	public void onLong(EqualsArguments arguments, JBlock block,
			boolean isAlwaysSet) {
		returnFalseIfNe(arguments, block, isAlwaysSet);
	}

	@Override
	public void onShort(EqualsArguments arguments, JBlock block,
			boolean isAlwaysSet) {
		returnFalseIfNe(arguments, block, isAlwaysSet);
	}

	@Override
	public void onObject(EqualsArguments arguments, JBlock block,
			boolean isAlwaysSet) {
		returnFalseIfNotEqualsCondition(
				arguments,
				block,
				isAlwaysSet,
				arguments.leftValue().invoke("equals")
						.arg(arguments.rightValue()).not());
	}
}
