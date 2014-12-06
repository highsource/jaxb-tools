package org.jvnet.jaxb2_commons.plugin.simplehashcode.codegeneration;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.apache.commons.lang3.Validate;
import org.jvnet.jaxb2_commons.codemodel.JCMTypeFactory;
import org.jvnet.jaxb2_commons.codemodel.JConditionable;
import org.jvnet.jaxb2_commons.plugin.simple.codegeneration.CodeGenerationImplementor;
import org.jvnet.jaxb2_commons.plugin.simple.codegeneration.CodeGenerator;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JConditional;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JOp;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

public class HashCodeCodeGenerationImplementor implements
		CodeGenerationImplementor<HashCodeArguments> {

	private final JCodeModel codeModel;
	private final JCMTypeFactory typeFactory = JCMTypeFactory.INSTANCE;
	private int multiplier;

	public HashCodeCodeGenerationImplementor(JCodeModel codeModel,
			int multiplier) {
		this.codeModel = Validate.notNull(codeModel);
		this.multiplier = multiplier;
	}

	@Override
	public JCodeModel getCodeModel() {
		return codeModel;
	}

	@Override
	public JCMTypeFactory getTypeFactory() {
		return typeFactory;
	}

	public int getMultiplier() {
		return multiplier;
	}

	private void generateBlock(HashCodeArguments arguments, JBlock block,
			JExpression valueHashCode, boolean isAlwaysSet,
			boolean checkForNullRequired) {

		final JExpression conditionalValueHashCode;
		if (isAlwaysSet || !checkForNullRequired) {
			conditionalValueHashCode = valueHashCode;
		} else {
			conditionalValueHashCode = JOp.cond(arguments.hasSetValue(),
					valueHashCode, JExpr.lit((int) 0));
		}
		block.assignPlus(arguments.currentHashCode(), conditionalValueHashCode);
	}

	private void castToInt(HashCodeArguments arguments, JBlock block,
			boolean isAlwaysSet) {
		generateBlock(arguments, block,
				JExpr.cast(getCodeModel().INT, arguments.value()), isAlwaysSet,
				true);
	}

	@Override
	public void onArray(JBlock block, boolean isAlwaysSet,
			HashCodeArguments arguments) {
		generateBlock(arguments, block, getCodeModel().ref(Arrays.class)
				.staticInvoke("hashCode").arg(arguments.value()), isAlwaysSet,
				false);

	}

	@Override
	public void onBoolean(HashCodeArguments arguments, JBlock block,
			boolean isAlwaysSet) {
		generateBlock(arguments, block,
				JOp.cond(arguments.value(), JExpr.lit(1231), JExpr.lit(1237)),
				isAlwaysSet, true);
	}

	@Override
	public void onByte(HashCodeArguments arguments, JBlock block,
			boolean isAlwaysSet) {
		castToInt(arguments, block, isAlwaysSet);
	}

	@Override
	public void onChar(HashCodeArguments arguments, JBlock block,
			boolean isAlwaysSet) {
		castToInt(arguments, block, isAlwaysSet);
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
		generateBlock(arguments, block, valueHashCode, isAlwaysSet, true);
	}

	@Override
	public void onFloat(HashCodeArguments arguments, JBlock block,
			boolean isAlwaysSet) {
		generateBlock(arguments, block, getCodeModel().ref(Float.class)
				.staticInvoke("floatToIntBits").arg(arguments.value()),
				isAlwaysSet, true);
	}

	@Override
	public void onInt(HashCodeArguments arguments, JBlock block,
			boolean isAlwaysSet) {
		generateBlock(arguments, block, arguments.value(), isAlwaysSet, true);
	}

	@Override
	public void onLong(HashCodeArguments arguments, JBlock block,
			boolean isAlwaysSet) {
		generateBlock(
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
		castToInt(arguments, block, isAlwaysSet);
	}

	@Override
	public void onObject(HashCodeArguments arguments, JBlock block,
			boolean isAlwaysSet) {
		block = ifHasValueSet(block, isAlwaysSet, arguments);
		block.assignPlus(arguments.currentHashCode(),
				arguments.value().invoke("hashCode"));
	}

	@Override
	public void onIfArray(CodeGenerator<HashCodeArguments> codeGenerator,
			JConditionable _if, JType arrayType, HashCodeArguments arguments) {
		final JExpression condition = arguments.value()._instanceof(arrayType);
		final JBlock block = _if._ifThen(condition);
		final JVar valueArray = block.decl(JMod.FINAL, arrayType, arguments
				.value().name() + "Array",
				JExpr.cast(arrayType, arguments.value()));
		codeGenerator.append(block, arrayType,
				Collections.singleton(arrayType), true,
				arguments.spawn(valueArray, JExpr.TRUE));

	}

	@Override
	public void onIfJAXBElement(CodeGenerator<HashCodeArguments> codeGenerator,
			JConditionable _if, JType jaxbElementType,
			Collection<JType> possibleJAXBElementTypes,
			HashCodeArguments arguments) {
		final JBlock block = _if._ifThen(arguments.value()._instanceof(
				getCodeModel().ref(JAXBElement.class)));
		final JVar valueJAXBElement = block.decl(JMod.FINAL, jaxbElementType,
				arguments.value().name() + "JAXBElement",
				JExpr.cast(jaxbElementType, arguments.value()));
		valueJAXBElement.annotate(SuppressWarnings.class).param("value",
				"unchecked");
		codeGenerator.append(block, jaxbElementType, possibleJAXBElementTypes,
				true, arguments.spawn(valueJAXBElement, JExpr.TRUE));
	}

	private JBlock ifHasValueSet(JBlock block, boolean isAlwaysSet,
			HashCodeArguments arguments) {
		final JBlock subBlock;
		if (isAlwaysSet) {
			subBlock = block;
		} else {
			final JConditional ifHasSetValue = block._if(arguments
					.hasSetValue());
			subBlock = ifHasSetValue._then();
		}
		return subBlock;
	}

	@Override
	public void onList(CodeGenerator<HashCodeArguments> codeGenerator,
			JBlock block, JType elementType,
			Collection<JType> possibleElementTypes, boolean isAlwaysSet,
			HashCodeArguments arguments) {
		block = ifHasValueSet(block, isAlwaysSet, arguments);
		final JVar iterator = block.decl(JMod.FINAL,
				getCodeModel().ref(Iterator.class).narrow(elementType),
				arguments.value().name() + "Iterator", arguments.value()
						.invoke("iterator"));
		JBlock subBlock = block._while(iterator.invoke("hasNext")).body();
		subBlock.assign(arguments.currentHashCode(), arguments
				.currentHashCode().mul(JExpr.lit(getMultiplier())));
		final JVar elementValue = subBlock.decl(JMod.FINAL, elementType,
				arguments.value().name() + "Element", iterator.invoke("next"));
		final boolean isElementAlwaysSet = elementType.isPrimitive();
		final JExpression elementHasSetValue = isElementAlwaysSet ? JExpr.TRUE
				: elementValue.ne(JExpr._null());
		final HashCodeArguments elementArguments = arguments.spawn(
				elementValue, elementHasSetValue);
		codeGenerator.append(subBlock, elementType, possibleElementTypes,
				isElementAlwaysSet, elementArguments);

	}

	@Override
	public void onJAXBElement(CodeGenerator<HashCodeArguments> codeGenerator,
			JBlock block, JType valueType,
			Collection<JType> possibleValueTypes, boolean isAlwaysSet,
			HashCodeArguments arguments) {
		block = ifHasValueSet(block, isAlwaysSet, arguments);
		append(codeGenerator, block, "Name", "getName", arguments,
				getCodeModel().ref(QName.class));
		append(codeGenerator, block, "Value", "getValue", valueType,
				possibleValueTypes, arguments);
		append(codeGenerator, block, "DeclaredType", "getDeclaredType",
				arguments, getCodeModel().ref(Class.class).narrow(valueType));
		append(codeGenerator,
				block,
				"Scope",
				"getScope",
				arguments,
				getCodeModel().ref(Class.class).narrow(
						getCodeModel().ref(Object.class).wildcard()));
		append(codeGenerator, block, "Nil", "isNil", arguments,
				getCodeModel().BOOLEAN);
	}

	private void append(CodeGenerator<HashCodeArguments> codeGenerator,
			JBlock block, String propertyName, String method,
			HashCodeArguments arguments, final JType type) {
		append(codeGenerator, block, propertyName, method, type,
				Collections.<JType> singleton(type), arguments);
	}

	private void append(CodeGenerator<HashCodeArguments> codeGenerator,
			JBlock block, String propertyName, String propertyMethod,
			JType propertyType, Collection<JType> possiblePropertyTypes,
			HashCodeArguments arguments) {
		block = block.block();
		block.assign(arguments.currentHashCode(), arguments.currentHashCode()
				.mul(JExpr.lit(getMultiplier())));

		final JType declarablePropertyType = getTypeFactory().create(
				propertyType).getDeclarableType();

		final JVar propertyValue = block.decl(JMod.FINAL,
				declarablePropertyType,

				arguments.value().name() + propertyName, arguments.value()
						.invoke(propertyMethod));
		// We assume that primitive properties are always set
		boolean isAlwaysSet = propertyType.isPrimitive();
		final JExpression propertyHasSetValue = isAlwaysSet ? JExpr.TRUE
				: propertyValue.ne(JExpr._null());
		codeGenerator.append(block, propertyType, possiblePropertyTypes,
				isAlwaysSet,
				arguments.spawn(propertyValue, propertyHasSetValue));
	}
}
