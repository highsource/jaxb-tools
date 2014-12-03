package org.jvnet.jaxb2_commons.plugin.simplehashcode.generator;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.xml.bind.JAXBElement;

import org.apache.commons.lang3.Validate;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

public class ListHashCodeCodeGenerator extends BlockHashCodeCodeGenerator {

	public ListHashCodeCodeGenerator(TypedHashCodeCodeGeneratorFactory factory,
			JCodeModel codeModel) {
		super(factory, codeModel);
	}

	@Override
	protected void generate(JBlock block, JVar currentHashCode,
			JType exposedType, Collection<JType> possibleTypes, JVar value) {
		Validate.isInstanceOf(JClass.class, exposedType);

		final JClass jaxbElementClass = getCodeModel().ref(JAXBElement.class);
		final Set<JType> arrays = new HashSet<JType>();
		final Collection<JClass> jaxbElements = new HashSet<JClass>();
		final Set<JType> otherTypes = new HashSet<JType>();
		for (final JType possibleType : possibleTypes) {
			if (possibleType.isArray()) {
				arrays.add(possibleType);
			} else if (possibleType instanceof JClass
					&& jaxbElementClass
							.isAssignableFrom(((JClass) possibleType).erasure())) {
				jaxbElements.add((JClass) possibleType);
			} else {
				otherTypes.add(possibleType);
			}
		}
		// If list items are not arrays or JAXBElements, just delegate to the
		// hashCode of the list
		if (arrays.isEmpty() && jaxbElements.isEmpty()) {
			block.assign(
					currentHashCode,
					currentHashCode
							.mul(JExpr.lit(getFactory().getMultiplier())).plus(
									value.invoke("hashCode")));

		} else {

			final JClass listType = (JClass) exposedType;

			final JClass elementType;
			if (listType.getTypeParameters().size() == 1) {
				elementType = listType.getTypeParameters().get(0);
			} else {
				elementType = getCodeModel().ref(Object.class);
			}

			final JVar iterator = block.decl(JMod.FINAL,
					getCodeModel().ref(Iterator.class).narrow(elementType),
					value.name() + "Iterator", value.invoke("iterator"));

			final JVar valueHashCode = block.decl(JMod.NONE, getCodeModel().INT,
					value.name() + "HashCode", JExpr.lit(1));

			final JBlock elementBlock = block
					._while(iterator.invoke("hasNext")).body();
			final JVar elementValue = elementBlock.decl(JMod.FINAL,
					elementType, value.name() + "Element",
					iterator.invoke("next"));

			final boolean isAlwaysSet = elementType.isPrimitive();
			final JExpression hasSetValue = isAlwaysSet ? JExpr.TRUE
					: elementValue.ne(JExpr._null());
			getFactory().getCodeGenerator(elementType).generate(elementBlock,
					valueHashCode, elementType, possibleTypes, elementValue,
					hasSetValue, isAlwaysSet);
			block.assign(
					currentHashCode,
					currentHashCode
							.mul(JExpr.lit(getFactory().getMultiplier())).plus(
									valueHashCode));
		}
	}
}
