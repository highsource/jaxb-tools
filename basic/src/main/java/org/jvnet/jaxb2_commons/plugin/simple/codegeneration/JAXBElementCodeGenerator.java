package org.jvnet.jaxb2_commons.plugin.simple.codegeneration;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.Validate;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JType;

public class JAXBElementCodeGenerator<A extends Arguments> extends
		AbstractCodeGenerator<A> {

	public JAXBElementCodeGenerator(CodeGenerator<A> codeGenerator,
			CodeGenerationImplementor<A> implementor) {
		super(codeGenerator, implementor);
	}

	@Override
	public void append(JBlock block, JType type,
			Collection<JType> possibleTypes, boolean isAlwaysSet, A arguments) {
		Validate.isInstanceOf(JClass.class, type);

		final JClass _class = (JClass) type;

		// Get the T from JAXBElement<T>
		final JClass valueType = getValueType(_class);

		// Gather possible values types
		final Set<JType> possibleValueTypes = getPossibleValueTypes(possibleTypes);

		getImplementor().onJAXBElement(getCodeGeneratorFactory(), block,
				valueType, possibleValueTypes, isAlwaysSet, arguments);
	}

	private JClass getValueType(final JClass _class) {
		final JClass valueType;
		final List<JClass> typeParameters = _class.getTypeParameters();
		if (typeParameters.size() == 1) {
			valueType = typeParameters.get(0);
		} else {
			valueType = getCodeModel().ref(Object.class).wildcard();
		}
		return valueType;
	}

	private Set<JType> getPossibleValueTypes(Collection<JType> possibleTypes) {
		final Set<JType> possibleValueTypes = new HashSet<JType>();

		for (JType possibleType : possibleTypes) {
			Validate.isInstanceOf(JClass.class, possibleType);
			final JClass possibleClass = (JClass) possibleType;
			if (possibleClass.getTypeParameters().size() == 1) {
				possibleValueTypes
						.add(possibleClass.getTypeParameters().get(0));
			} else {
				possibleValueTypes.add(getCodeModel().ref(Object.class));
			}
		}
		return possibleValueTypes;
	}

}
