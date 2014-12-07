package org.jvnet.jaxb2_commons.plugin.codegenerator;

import org.jvnet.jaxb2_commons.codemodel.JCMTypeFactory;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JCodeModel;

public interface CodeGenerationImplementor<A extends Arguments<A>> {

	public JCodeModel getCodeModel();

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

}
