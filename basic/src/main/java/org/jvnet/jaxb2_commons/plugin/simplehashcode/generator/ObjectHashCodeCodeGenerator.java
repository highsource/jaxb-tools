package org.jvnet.jaxb2_commons.plugin.simplehashcode.generator;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.JAXBElement;

import org.apache.commons.lang3.Validate;
import org.jvnet.jaxb2_commons.codemodel.JCMTypeFactory;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JConditional;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;
import com.sun.tools.xjc.reader.TypeUtil;

public class ObjectHashCodeCodeGenerator extends BlockHashCodeCodeGenerator {

	private JCMTypeFactory typeFactory;

	public ObjectHashCodeCodeGenerator(
			TypedHashCodeCodeGeneratorFactory factory, JCodeModel codeModel,
			JCMTypeFactory typeFactory) {
		super(factory, codeModel);
		this.typeFactory = Validate.notNull(typeFactory);
	}

	public JCMTypeFactory getTypeFactory() {
		return typeFactory;
	}

	@Override
	protected void generate(JBlock block, JVar currentHashCode,
			JType exposedType, Collection<JType> possibleTypes, JVar value) {
		if (possibleTypes.size() <= 1) {
			block.assign(
					currentHashCode,
					currentHashCode
							.mul(JExpr.lit(getFactory().getMultiplier())).plus(
									value.invoke("hashCode")));

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

			JConditional conditional = null;

			if (!jaxbElements.isEmpty()) {
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
				final JType valueType = TypeUtil.getCommonBaseType(
						getCodeModel(), valueTypes);
				final JClass jaxbElement = jaxbElementClass.narrow(valueType);
				final HashCodeCodeGenerator codeGenerator = getFactory()
						.getCodeGenerator(jaxbElement);
				conditional = block._if(value._instanceof(jaxbElementClass));
				final JBlock _then = conditional._then();
				final JVar valueJAXBElement = _then.decl(JMod.FINAL,
						jaxbElement, value.name() + "JAXBElement",
						JExpr.cast(jaxbElement, value));
				codeGenerator.generate(_then, currentHashCode, jaxbElement,
						new HashSet<JType>(jaxbElements), valueJAXBElement,
						JExpr.TRUE, true);
			}

			if (!arrays.isEmpty()) {
				for (JType array : arrays) {
					final JExpression condition = value._instanceof(array);
					conditional = conditional == null ? block._if(condition)
							: conditional._elseif(condition);
					final JBlock _then = conditional._then();

					final JVar valueArray = _then.decl(JMod.FINAL, array,
							value.name() + "Array", JExpr.cast(array, value));
					final HashCodeCodeGenerator codeGenerator = getFactory()
							.getCodeGenerator(array);
					codeGenerator.generate(_then, currentHashCode, array,
							Collections.singleton(array), valueArray,
							JExpr.TRUE, true);
				}
			}

			if (!otherTypes.isEmpty()) {
				JBlock _else = conditional == null ? block : conditional
						._else();
				_else.assign(
						currentHashCode,
						currentHashCode.mul(
								JExpr.lit(getFactory().getMultiplier())).plus(
								value.invoke("hashCode")));
			}
		}
	}
}