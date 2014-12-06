package org.jvnet.jaxb2_commons.plugin.simple.codegeneration;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.JAXBElement;

import org.jvnet.jaxb2_commons.codemodel.JConditionable;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JConditional;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JType;
import com.sun.tools.xjc.reader.TypeUtil;

public class ObjectCodeGenerator<A extends Arguments> extends
		AbstractCodeGenerator<A> {

	public ObjectCodeGenerator(CodeGenerator<A> codeGenerator,
			CodeGenerationImplementor<A> implementor) {
		super(codeGenerator, implementor);
	}

	@Override
	public void append(final JBlock block, JType type,
			Collection<JType> possibleTypes, boolean isAlwaysSet, A arguments) {
		if (possibleTypes.size() <= 1) {
			getImplementor().onObject(arguments, block, isAlwaysSet);
		} else {
			final JClass jaxbElementClass = getCodeModel().ref(
					JAXBElement.class);
			final Set<JType> arrays = new HashSet<JType>();
			final Collection<JClass> jaxbElements = new HashSet<JClass>();
			final Set<JType> otherTypes = new HashSet<JType>();
			for (final JType possibleType : possibleTypes) {
				if (possibleType.isArray()) {
					arrays.add(possibleType);
				} else if (possibleType instanceof JClass
						&& jaxbElementClass
								.isAssignableFrom(((JClass) possibleType)
										.erasure())) {
					jaxbElements.add((JClass) possibleType);
				} else {
					otherTypes.add(possibleType);
				}
			}

			final JConditionable _if = new JConditionable() {

				private JConditional conditional = null;

				@Override
				public JBlock _ifThen(JExpression condition) {
					if (conditional == null) {
						conditional = block._if(condition);
					} else {
						conditional = conditional._elseif(condition);
					}
					return conditional._then();
				}

				@Override
				public JBlock _else() {
					if (conditional == null) {
						return block;
					} else {
						return conditional._else();
					}
				}
			};

			if (!jaxbElements.isEmpty()) {
				final Set<JType> valueTypes = getValueTypes(jaxbElements);
				final JType valueType = getValueType(valueTypes);
				final JClass jaxbElement = jaxbElementClass.narrow(valueType);

				getImplementor().onIfJAXBElement(getCodeGeneratorFactory(),
						_if, jaxbElement, new HashSet<JType>(jaxbElements),
						arguments);
			}

			if (!arrays.isEmpty()) {
				for (JType arrayType : arrays) {
					getImplementor().onIfArray(getCodeGeneratorFactory(), _if,
							arrayType, arguments);
				}
			}

			if (!otherTypes.isEmpty()) {
				getImplementor().onObject(arguments, _if._else(), false);
			}
		}
	}

	private JType getValueType(final Set<JType> valueTypes) {
		final JType valueType = TypeUtil.getCommonBaseType(getCodeModel(),
				valueTypes);
		return valueType;
	}

	private Set<JType> getValueTypes(final Collection<JClass> jaxbElements) {
		final Set<JType> valueTypes = new HashSet<JType>();
		for (JClass jaxbElement : jaxbElements) {
			final JType valueType;
			if (jaxbElement.getTypeParameters().size() == 1) {
				valueType = jaxbElement.getTypeParameters().get(0);
			} else {
				valueType = getCodeModel().ref(Object.class);
			}
			valueTypes.add(valueType);
		}
		return valueTypes;
	}

}