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
	protected void append(JBlock block, JVar currentHashCode, JType type,
			Collection<JType> possibleTypes, JVar value) {
		Validate.isInstanceOf(JClass.class, type);
		final JClass _class = (JClass) type;

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
			block.assignPlus(currentHashCode, value.invoke("hashCode"));
		} else {
			appendElements(block, currentHashCode, possibleTypes, value, _class);
		}
	}

	private void appendElements(JBlock block, JVar currentHashCode,
			Collection<JType> possibleTypes, JVar value, final JClass type) {
		final JClass elementType = getElementType(type);
		final JVar iterator = block.decl(JMod.FINAL,
				getCodeModel().ref(Iterator.class).narrow(elementType),
				value.name() + "Iterator", value.invoke("iterator"));
		final JBlock subBlock = block._while(iterator.invoke("hasNext")).body();
		final JVar elementValue = subBlock.decl(JMod.FINAL, elementType,
				value.name() + "Element", iterator.invoke("next"));
		subBlock.assign(currentHashCode, currentHashCode.mul(JExpr
				.lit(getCodeGeneratorFactory().getMultiplier())));
		appendElement(subBlock, currentHashCode, elementType, possibleTypes,
				elementValue);
	}

	private void appendElement(final JBlock subBlock, JVar currentHashCode,
			final JClass type, Collection<JType> possibleTypes, final JVar value) {
		final boolean isAlwaysSet = type.isPrimitive();
		final JExpression hasSetValue = isAlwaysSet ? JExpr.TRUE : value
				.ne(JExpr._null());
		getCodeGeneratorFactory().getCodeGenerator(type).append(subBlock,
				type, possibleTypes, isAlwaysSet, currentHashCode, value,
				hasSetValue);
	}

	private JClass getElementType(final JClass _class) {
		final JClass elementType;
		if (_class.getTypeParameters().size() == 1) {
			elementType = _class.getTypeParameters().get(0);
		} else {
			elementType = getCodeModel().ref(Object.class);
		}
		return elementType;
	}
}
