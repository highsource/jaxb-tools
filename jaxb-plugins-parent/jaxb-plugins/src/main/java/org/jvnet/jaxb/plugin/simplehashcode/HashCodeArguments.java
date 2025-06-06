package org.jvnet.jaxb.plugin.simplehashcode;

import java.util.Collection;
import java.util.ListIterator;
import java.util.Objects;

import org.jvnet.jaxb.plugin.codegenerator.Arguments;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

public class HashCodeArguments implements Arguments<HashCodeArguments> {

	private final JCodeModel codeModel;
	private final JVar currentHashCode;
	private final int multiplier;
	private final JVar value;
	private final JExpression hasSetValue;

	public HashCodeArguments(JCodeModel codeModel, JVar currentHashCode,
			int multiplier, JVar value, JExpression hasSetValue) {
		this.codeModel = Objects.requireNonNull(codeModel, "codeModel must not be null");
		this.currentHashCode = Objects.requireNonNull(currentHashCode, "currentHashCode must not be null");
		this.multiplier = multiplier;
		this.value = Objects.requireNonNull(value, "value must not be null");
		this.hasSetValue = Objects.requireNonNull(hasSetValue, "hasSetValue must not be null");
	}

	private JCodeModel getCodeModel() {
		return codeModel;
	}

	public JVar currentHashCode() {
		return currentHashCode;
	}

	public int multiplier() {
		return multiplier;
	}

	public JVar value() {
		return value;
	}

	public JExpression hasSetValue() {
		return hasSetValue;
	}

	private HashCodeArguments spawn(JVar value, JExpression hasSetValue) {
		return new HashCodeArguments(getCodeModel(), currentHashCode(),
				multiplier(), value, hasSetValue);
	}

	public HashCodeArguments property(JBlock block, String propertyName,
			String propertyMethod, JType declarablePropertyType,
			JType propertyType, Collection<JType> possiblePropertyTypes) {
		block.assign(currentHashCode(),
				currentHashCode().mul(JExpr.lit(multiplier())));
		final JVar propertyValue = block.decl(JMod.FINAL,
				declarablePropertyType, value().name() + propertyName, value()
						.invoke(propertyMethod));
		// We assume that primitive properties are always set
		boolean isAlwaysSet = propertyType.isPrimitive();
		final JExpression propertyHasSetValue = isAlwaysSet ? JExpr.TRUE
				: propertyValue.ne(JExpr._null());
		return spawn(propertyValue, propertyHasSetValue);
	}

	public HashCodeArguments iterator(JBlock block, JType elementType) {
		final JVar listIterator = block
				.decl(JMod.FINAL, getCodeModel().ref(ListIterator.class)
						.narrow(elementType), value().name() + "ListIterator",
						value().invoke("listIterator"));

		return spawn(listIterator, JExpr.TRUE);
	}

	public HashCodeArguments element(JBlock subBlock, JType elementType) {
		final JVar elementValue = subBlock.decl(JMod.FINAL, elementType,
				value().name() + "Element", value().invoke("next"));
		final boolean isElementAlwaysSet = elementType.isPrimitive();
		final JExpression elementHasSetValue = isElementAlwaysSet ? JExpr.TRUE
				: elementValue.ne(JExpr._null());
		return spawn(elementValue, elementHasSetValue);

	}

	public JExpression _instanceof(JType type) {
		return value()._instanceof(type);
	}

	public HashCodeArguments cast(String suffix, JBlock block,
			JType jaxbElementType, boolean suppressWarnings) {
		final JVar castedValue = block.decl(JMod.FINAL, jaxbElementType,
				value().name() + suffix, JExpr.cast(jaxbElementType, value()));
		if (suppressWarnings) {
			castedValue.annotate(SuppressWarnings.class).param("value",
					"unchecked");
		}
		return spawn(castedValue, JExpr.TRUE);
	}

	public JBlock ifHasSetValue(JBlock block, boolean isAlwaysSet,
			boolean checkForNullRequired) {

		if (isAlwaysSet || !checkForNullRequired) {
			return block;
		} else {
			return block._if(hasSetValue())._then();
		}
	}

	public JBlock _while(JBlock block) {
		final JBlock subBlock = block._while(value().invoke("hasNext")).body();
		subBlock.assign(currentHashCode(),
				currentHashCode().mul(JExpr.lit(multiplier())));
		return subBlock;
	}

}
