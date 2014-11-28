package org.jvnet.jaxb2_commons.plugin.simpleequals.generator;

import javax.xml.namespace.QName;

import org.apache.commons.lang3.Validate;
import org.jvnet.jaxb2_commons.codemodel.generator.TypedCodeGeneratorFactory;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JConditional;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JOp;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

public class JAXBElementEqualsCodeGenerator implements EqualsCodeGenerator {

	private final JCodeModel codeModel;
	private final TypedCodeGeneratorFactory<EqualsCodeGenerator> codeGeneratorFactory;

	public JAXBElementEqualsCodeGenerator(JCodeModel codeModel,
			TypedCodeGeneratorFactory<EqualsCodeGenerator> codeGeneratorFactory) {
		this.codeModel = Validate.notNull(codeModel);
		this.codeGeneratorFactory = Validate.notNull(codeGeneratorFactory);
	}

	@Override
	public void generate(JBlock block, JType type, JVar left, JVar right) {

		JBlock leftNeRight = block._if(left.ne(right))._then();

		JConditional ifLeftOrRightIsNull = leftNeRight._if(JOp.cor(
				left.eq(JExpr._null()), right.eq(JExpr._null())));

		final JBlock leftOrRightAreNull = ifLeftOrRightIsNull._then();
		final JBlock leftAndRightAreNotNull = ifLeftOrRightIsNull._else();

		leftOrRightAreNull._return(JExpr.FALSE);

		generateNonNull(leftAndRightAreNotNull, type, left, right);

	}

	public void generateNonNull(JBlock block, JType type, JVar left, JVar right) {

		// TODO extract type wildcard
		generate(block, type, left, right, "Name", "getName", QName.class);
		generate(block, type, left, right, "Value", "getValue", Object.class);
		final JClass classWildcard = codeModel.ref(Class.class).narrow(
				codeModel.ref(Object.class).wildcard());
		generate(block, type, left, right, "DeclaredType", "getDeclaredType",
				classWildcard);
		generate(block, type, left, right, "Scope", "getScope", classWildcard);
		generate(block, type, left, right, "Nil", "isNil", codeModel.BOOLEAN);

	}

	private void generate(JBlock block, JType type, JVar left, JVar right,
			String propertyName, String method, Class<?> propertyType) {
		generate(block, type, left, right, propertyName, method,
				codeModel.ref(propertyType));
	}

	private void generate(JBlock block, JType type, JVar left, JVar right,
			String propertyName, String method, JType propertyType) {

		final JBlock propertyBlock = block.block();

		JVar leftPropertyValue = propertyBlock.decl(JMod.FINAL, propertyType,
				left.name() + propertyName, left.invoke(method));
		JVar rightPropertyValue = propertyBlock.decl(JMod.FINAL, propertyType,
				right.name() + propertyName, right.invoke(method));

		final EqualsCodeGenerator codeGenerator = codeGeneratorFactory
				.getCodeGenerator(propertyType);
		codeGenerator.generate(propertyBlock, propertyType, leftPropertyValue,
				rightPropertyValue);
	}

}
