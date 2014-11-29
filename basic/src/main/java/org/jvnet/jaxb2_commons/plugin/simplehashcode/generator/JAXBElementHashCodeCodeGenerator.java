package org.jvnet.jaxb2_commons.plugin.simplehashcode.generator;

import javax.xml.namespace.QName;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

public class JAXBElementHashCodeCodeGenerator extends
		BlockHashCodeCodeGenerator {

	public JAXBElementHashCodeCodeGenerator(
			TypedHashCodeCodeGeneratorFactory factory, JCodeModel codeModel) {
		super(factory, codeModel);
	}

	@Override
	protected void generate(JBlock block, JVar currentHashCode, JType type,
			JVar value) {
		valueHashCode(block, currentHashCode, type, value, "Name", "getName",
				QName.class);
		valueHashCode(block, currentHashCode, type, value, "Value", "getValue",
				Object.class);
		final JClass classWildcard = getCodeModel().ref(Class.class).narrow(
				getCodeModel().ref(Object.class).wildcard());
		valueHashCode(block, currentHashCode, type, value, "DeclaredType",
				"getDeclaredType", classWildcard);
		valueHashCode(block, currentHashCode, type, value, "Scope", "getScope",
				classWildcard);
		valueHashCode(block, currentHashCode, type, value, "Nil", "isNil",
				getCodeModel().BOOLEAN);
	}

	private void valueHashCode(JBlock block, JVar currentHashCode, JType type,
			JVar value, String propertyName, String method,
			Class<?> propertyType) {
		valueHashCode(block, currentHashCode, type, value, propertyName,
				method, getCodeModel().ref(propertyType));
	}

	private void valueHashCode(JBlock block, JVar currentHashCode, JType type,
			JVar value, String propertyName, String method, JType propertyType) {
		final HashCodeCodeGenerator codeGenerator = getFactory()
				.getCodeGenerator(propertyType);
		final JVar propertyValue = block.decl(JMod.FINAL, propertyType,
				value.name() + propertyName, value.invoke(method));
		// We assume that primitive properties are always set
		boolean isAlwaysSet = propertyType.isPrimitive();
		final JExpression hasSetValue = isAlwaysSet ? JExpr.TRUE
				: propertyValue.ne(JExpr._null());
		codeGenerator.generate(block, currentHashCode, propertyType,
				propertyValue, hasSetValue, isAlwaysSet);
	}

}
