package org.jvnet.jaxb2_commons.plugin.simplehashcode.generator;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang3.Validate;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

public class ListHashCodeCodeGenerator extends BlockHashCodeCodeGenerator {

	public ListHashCodeCodeGenerator(TypedHashCodeCodeGeneratorFactory factory,
			JCodeModel codeModel) {
		super(factory, codeModel);
	}

	@Override
	protected void generate(JBlock block, JVar currentHashCode, JType exposedType,
			Collection<JType> possibleTypes,
			JVar value) {
		Validate.isInstanceOf(JClass.class, exposedType);
		final JClass listType = (JClass) exposedType;

		final JClass elementType;
		if (listType.getTypeParameters().size() == 1) {
			elementType = listType.getTypeParameters().get(0);
		} else {
			elementType = getCodeModel().ref(Object.class);
		}

		final JVar iterator = block.decl(JMod.FINAL,
				getCodeModel().ref(Iterator.class).narrow(elementType), value.name() + "Iterator",
				value.invoke("iterator"));

		final JBlock elementBlock = block._while(iterator.invoke("hasNext"))
				.body();
		final JVar elementValue = elementBlock.decl(JMod.FINAL, elementType,
				value.name() + "Element", iterator.invoke("next"));

		final boolean isAlwaysSet = elementType.isPrimitive();
		final JExpression hasSetValue = isAlwaysSet ? JExpr.TRUE : elementValue
				.ne(JExpr._null());
		getFactory().getCodeGenerator(elementType).generate(elementBlock,
				currentHashCode, elementType, possibleTypes, elementValue,
				hasSetValue, isAlwaysSet);

	}
}
