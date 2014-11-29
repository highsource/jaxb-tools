package org.jvnet.jaxb2_commons.plugin.simplehashcode.generator;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JOp;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

public class DoubleHashCodeCodeGenerator extends
		PrimitiveBlockHashCodeCodeGenerator {

	public DoubleHashCodeCodeGenerator(JCodeModel codeModel) {
		super(codeModel);
	}

	@Override
	protected JExpression hashCodeValue(JBlock block, JType type, JVar value) {
		
		// long bits = doubleToLongBits(value);
		final JVar bits = block.decl(
				JMod.FINAL,
				type,
				value.name() + "Bits",
				getCodeModel().ref(Double.class)
						.staticInvoke("doubleToLongBits").arg(value));
		// return (int)(bits ^ (bits >>> 32));

		final JExpression valueHashCode = JExpr.cast(getCodeModel().INT,
				JOp.xor(bits, JOp.shrz(bits, JExpr.lit(32))));
		return valueHashCode;
	}
}
