package org.jvnet.jaxb2_commons.plugin.simplehashcode.generator;

import java.util.Collection;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JForEach;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

public class ArrayHashCodeGenerator extends BlockHashCodeCodeGenerator {

	public ArrayHashCodeGenerator(
			TypedHashCodeCodeGeneratorFactory factory, JCodeModel codeModel) {
		super(factory, codeModel);
	}

	@Override
	protected void generate(JBlock block, JVar currentHashCode,
			JType possibleType, Collection<JType> possibleTypes, JVar value) {

		final JType elementType = possibleType.elementType();
		final JForEach forEachElement = block.forEach(elementType, value.name()
				+ "Element", value);
		getFactory().getCodeGenerator(elementType).generate(
				forEachElement.body(), currentHashCode, elementType,
				possibleTypes, forEachElement.var(), JExpr.TRUE, true);
	}

}
