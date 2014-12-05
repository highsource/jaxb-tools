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
	protected void append(JBlock block, JVar currentHashCode, JType type,
			Collection<JType> possibleTypes, JVar value) {
		Validate.isInstanceOf(JClass.class, type);

		final JClass _class = (JClass) type;

		// Get the T from JAXBElement<T>
		final JClass valueType = getValueType(_class);

		// Gather possible values types
		final Set<JType> possibleValueTypes = getPossibleValueTypes(possibleTypes);

		appendName(block, currentHashCode, value);
		appendValue(block, currentHashCode, value, valueType,
				possibleValueTypes);
		appendDeclaredType(block, currentHashCode, value, valueType);
		appendScope(block, currentHashCode, value);
		appendNil(block, currentHashCode, value);
	}

	private void appendName(JBlock block, JVar currentHashCode, JVar value) {
		append(block, currentHashCode, value, "Name", "getName", QName.class);
	}

	private void appendValue(JBlock block, JVar currentHashCode, JVar value,
			final JClass valueType, final Set<JType> possibleValueTypes) {
		append(block, currentHashCode, value, "Value", "getValue", valueType,
				possibleValueTypes);
	}

	private void appendDeclaredType(JBlock block, JVar currentHashCode,
			JVar value, final JClass valueType) {
		final JClass valueTypeClass = getCodeModel().ref(Class.class).narrow(
				valueType);
		append(block, currentHashCode, value, "DeclaredType",
				"getDeclaredType", valueTypeClass,
				Collections.<JType> singleton(valueTypeClass));
	}

	private void appendScope(JBlock block, JVar currentHashCode, JVar value) {
		final JClass classWildcard = getCodeModel().ref(Class.class).narrow(
				getCodeModel().ref(Object.class).wildcard());
		append(block, currentHashCode, value, "Scope", "getScope",
				classWildcard, Collections.<JType> singleton(classWildcard));
	}

	private void appendNil(JBlock block, JVar currentHashCode, JVar value) {
		append(block, currentHashCode, value, "Nil", "isNil",
				getCodeModel().BOOLEAN,
				Collections.<JType> singleton(getCodeModel().BOOLEAN));
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

	private void append(JBlock block, JVar currentHashCode, JVar value,
			String propertyName, String method, Class<?> valueClass) {
		final JClass valueType = getCodeModel().ref(valueClass);
		append(block, currentHashCode, value, propertyName, method, valueType,
				Collections.<JType> singleton(valueType));
	}

	private void append(JBlock block, JVar currentHashCode, JVar value,
			String propertyName, String method, JType valueType,
			Collection<JType> possibleValueTypes) {
		final HashCodeCodeGenerator codeGenerator = getCodeGeneratorFactory()
				.getCodeGenerator(valueType);

		final JType declarablePropertyType = getTypeFactory().create(valueType)
				.getDeclarableType();

		final JVar subValue = block.decl(JMod.FINAL, declarablePropertyType,
				value.name() + propertyName, value.invoke(method));
		// We assume that primitive properties are always set
		boolean isAlwaysSet = valueType.isPrimitive();
		final JExpression hasSetValue = isAlwaysSet ? JExpr.TRUE : subValue
				.ne(JExpr._null());
		block.assign(currentHashCode, currentHashCode.mul(JExpr
				.lit(getCodeGeneratorFactory().getMultiplier())));
		codeGenerator.append(block, valueType, possibleValueTypes,
				isAlwaysSet, currentHashCode, subValue, hasSetValue);
	}
}
