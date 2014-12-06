package org.jvnet.jaxb2_commons.plugin.simple.codegeneration;

import java.util.Collection;

import org.jvnet.jaxb2_commons.codemodel.JCMTypeFactory;
import org.jvnet.jaxb2_commons.codemodel.JConditionable;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JType;

public interface CodeGenerationImplementor<A extends Arguments> {

	public JCodeModel getCodeModel();

	public JCMTypeFactory getTypeFactory();

	public void onArray(JBlock block, boolean isAlwaysSet, A arguments);

	public void onBoolean(A arguments, JBlock block, boolean isAlwaysSet);

	public void onByte(A arguments, JBlock block, boolean isAlwaysSet);

	public void onChar(A arguments, JBlock block, boolean isAlwaysSet);

	public void onDouble(A arguments, JBlock block, boolean isAlwaysSet);

	public void onFloat(A arguments, JBlock block, boolean isAlwaysSet);

	public void onInt(A arguments, JBlock block, boolean isAlwaysSet);

	public void onLong(A arguments, JBlock block, boolean isAlwaysSet);

	public void onShort(A arguments, JBlock block, boolean isAlwaysSet);

	public void onObject(A arguments, JBlock block, boolean isAlwaysSet);

	// (JBlock block, JType type, Collection<JType> possibleTypes,
	// boolean isAlwaysSet, A arguments)
	public void onJAXBElement(CodeGenerator<A> codeGenerator, JBlock block,
			JType valueType, Collection<JType> possibleValueTypes,
			boolean isAlwaysSet, A arguments);

	public void onList(CodeGenerator<A> codeGenerator, JBlock block,
			JType elementType, Collection<JType> possibleElementTypes,
			boolean isAlwaysSet, A arguments);

	public void onIfArray(CodeGenerator<A> codeGeneratorFactory,
			JConditionable _if, JType arrayType, A arguments);

	public void onIfJAXBElement(CodeGenerator<A> codeGenerator,
			JConditionable _if, JType jaxbElementType,
			Collection<JType> possibleJAXBElementTypes, A arguments);

}
