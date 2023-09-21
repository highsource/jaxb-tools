package org.jvnet.jaxb.plugin.codegenerator;

import java.util.Collection;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JType;

public interface Arguments<A extends Arguments<A>> {

	public JBlock ifHasSetValue(JBlock block, boolean isAlwaysSet,
			boolean checkForNullRequired);

	public JExpression _instanceof(JType type);

	public A cast(String suffix, JBlock block, JType type, boolean suppressWarnings);

	public A element(JBlock subBlock, JType elementType);

	public A iterator(JBlock block, JType elementType);

	public JBlock _while(JBlock block);

	public A property(JBlock block, String propertyName, String propertyMethod,
			JType declarablePropertyType, JType propertyType,
			Collection<JType> possiblePropertyTypes);

}
