package org.jvnet.jaxb.plugin.simplehashcode;

import java.util.Arrays;

import org.jvnet.jaxb.plugin.codegenerator.AbstractCodeGenerationImplementor;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JOp;
import com.sun.codemodel.JVar;

public class HashCodeCodeGenerationImplementor extends
		AbstractCodeGenerationImplementor<HashCodeArguments> {


	public HashCodeCodeGenerationImplementor(JCodeModel codeModel) {
		super(codeModel);
	}

	private void ifHasSetValueAssignPlusValueHashCode(
			HashCodeArguments arguments, JBlock block,
			JExpression valueHashCode, boolean isAlwaysSet,
			boolean checkForNullRequired) {
		arguments.ifHasSetValue(block, isAlwaysSet, checkForNullRequired)
				.assignPlus(arguments.currentHashCode(), valueHashCode);
	}

	private void ifHasSetValueAssignPlusValueCastedToInt(
			HashCodeArguments arguments, JBlock block, boolean isAlwaysSet) {
		ifHasSetValueAssignPlusValueHashCode(arguments, block,
				JExpr.cast(getCodeModel().INT, arguments.value()), isAlwaysSet,
				true);
	}

	@Override
	public void onArray(JBlock block, boolean isAlwaysSet,
			HashCodeArguments arguments) {
		ifHasSetValueAssignPlusValueHashCode(
				arguments,
				block,
				getCodeModel().ref(Arrays.class).staticInvoke("hashCode")
						.arg(arguments.value()), isAlwaysSet, false);

	}

	@Override
	public void onBoolean(HashCodeArguments arguments, JBlock block,
			boolean isAlwaysSet) {
		ifHasSetValueAssignPlusValueHashCode(arguments, block,
				JOp.cond(arguments.value(), JExpr.lit(1231), JExpr.lit(1237)),
				isAlwaysSet, true);
	}

	@Override
	public void onByte(HashCodeArguments arguments, JBlock block,
			boolean isAlwaysSet) {
		ifHasSetValueAssignPlusValueCastedToInt(arguments, block, isAlwaysSet);
	}

	@Override
	public void onChar(HashCodeArguments arguments, JBlock block,
			boolean isAlwaysSet) {
		ifHasSetValueAssignPlusValueCastedToInt(arguments, block, isAlwaysSet);
	}

	@Override
	public void onDouble(HashCodeArguments arguments, JBlock block,
			boolean isAlwaysSet) {
		// long bits = doubleToLongBits(value);
		final JVar bits = block.decl(JMod.FINAL, getCodeModel().LONG, arguments
				.value().name() + "Bits", getCodeModel().ref(Double.class)
				.staticInvoke("doubleToLongBits").arg(arguments.value()));
		// return (int)(bits ^ (bits >>> 32));
		final JExpression valueHashCode = JExpr.cast(getCodeModel().INT,
				JOp.xor(bits, JOp.shrz(bits, JExpr.lit(32))));
		ifHasSetValueAssignPlusValueHashCode(arguments, block, valueHashCode,
				isAlwaysSet, true);
	}

	@Override
	public void onFloat(HashCodeArguments arguments, JBlock block,
			boolean isAlwaysSet) {
		ifHasSetValueAssignPlusValueHashCode(arguments, block,
				getCodeModel().ref(Float.class).staticInvoke("floatToIntBits")
						.arg(arguments.value()), isAlwaysSet, true);
	}

	@Override
	public void onInt(HashCodeArguments arguments, JBlock block,
			boolean isAlwaysSet) {
		ifHasSetValueAssignPlusValueHashCode(arguments, block,
				arguments.value(), isAlwaysSet, true);
	}

	@Override
	public void onLong(HashCodeArguments arguments, JBlock block,
			boolean isAlwaysSet) {
		ifHasSetValueAssignPlusValueHashCode(
				arguments,
				block,
				JExpr.cast(
						getCodeModel().INT,
						arguments.value().xor(
								arguments.value().shrz(JExpr.lit(32)))),
				isAlwaysSet, true);
	}

	@Override
	public void onShort(HashCodeArguments arguments, JBlock block,
			boolean isAlwaysSet) {
		ifHasSetValueAssignPlusValueCastedToInt(arguments, block, isAlwaysSet);
	}

	@Override
	public void onObject(HashCodeArguments arguments, JBlock block,
			boolean isAlwaysSet) {
		ifHasSetValueAssignPlusValueHashCode(arguments, block, arguments
				.value().invoke("hashCode"), isAlwaysSet, true);
	}
}
