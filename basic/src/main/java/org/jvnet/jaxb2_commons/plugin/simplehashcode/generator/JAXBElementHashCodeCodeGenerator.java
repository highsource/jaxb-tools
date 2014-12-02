package org.jvnet.jaxb2_commons.plugin.simplehashcode.generator;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;

import org.apache.commons.lang3.Validate;
import org.jvnet.jaxb2_commons.codemodel.JCMTypeFactory;

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

	private JCMTypeFactory typeFactory;

	public JAXBElementHashCodeCodeGenerator(
			TypedHashCodeCodeGeneratorFactory factory, JCodeModel codeModel,
			JCMTypeFactory typeFactory) {
		super(factory, codeModel);
		this.typeFactory = Validate.notNull(typeFactory);
	}
	
	private JCMTypeFactory getTypeFactory() {
		return typeFactory;
	}

	@Override
	protected void generate(JBlock block, JVar currentHashCode,
			JType exposedType, Collection<JType> possibleTypes, JVar value) {
		Validate.isInstanceOf(JClass.class, exposedType);

		final JClass exposedClass = (JClass) exposedType;

		// Get the T from JAXBElement<T>
		final JClass exposedTypeParameter;
		final List<JClass> typeParameters = exposedClass.getTypeParameters();
		if (typeParameters.size() == 1) {
			exposedTypeParameter = typeParameters.get(0);
		} else {
			exposedTypeParameter = getCodeModel().ref(Object.class).wildcard();
		}

		// Gather possible values types
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

		valueHashCode(block, currentHashCode, exposedType, value, "Name",
				"getName", QName.class);
		valueHashCode(block, currentHashCode, exposedType, value, "Value",
				"getValue", exposedTypeParameter, possibleValueTypes);
		final JClass classWildcard = getCodeModel().ref(Class.class).narrow(
				getCodeModel().ref(Object.class).wildcard());
		final JClass exposedClassWildcard = getCodeModel().ref(Class.class)
				.narrow(exposedTypeParameter);
		valueHashCode(block, currentHashCode, exposedType, value,
				"DeclaredType", "getDeclaredType", exposedClassWildcard,
				Collections.<JType> singleton(classWildcard));
		valueHashCode(block, currentHashCode, exposedType, value, "Scope",
				"getScope", classWildcard,
				Collections.<JType> singleton(classWildcard));
		valueHashCode(block, currentHashCode, exposedType, value, "Nil",
				"isNil", getCodeModel().BOOLEAN,
				Collections.<JType> singleton(getCodeModel().BOOLEAN));
	}

	private void valueHashCode(JBlock block, JVar currentHashCode, JType type,
			JVar value, String propertyName, String method,
			Class<?> _propertyType) {
		final JClass propertyType = getCodeModel().ref(_propertyType);
		valueHashCode(block, currentHashCode, type, value, propertyName,
				method, propertyType,
				Collections.<JType> singleton(propertyType));
	}

	private void valueHashCode(JBlock block, JVar currentHashCode, JType type,
			JVar value, String propertyName, String method, JType propertyType,
			Collection<JType> possiblePropertyTypes) {
		final HashCodeCodeGenerator codeGenerator = getFactory()
				.getCodeGenerator(propertyType);

		final JType declarablePropertyType = getTypeFactory().create(
				propertyType).getDeclarableType();

		final JVar propertyValue = block.decl(JMod.FINAL,
				declarablePropertyType, value.name() + propertyName,
				value.invoke(method));
		// We assume that primitive properties are always set
		boolean isAlwaysSet = propertyType.isPrimitive();
		final JExpression hasSetValue = isAlwaysSet ? JExpr.TRUE
				: propertyValue.ne(JExpr._null());
		codeGenerator.generate(block, currentHashCode, propertyType,
				possiblePropertyTypes, propertyValue, hasSetValue, isAlwaysSet);
	}
}
