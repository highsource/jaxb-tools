package org.jvnet.jaxb2_commons.plugin.simplehashcode.codegeneration;

import org.apache.commons.lang3.Validate;
import org.jvnet.jaxb2_commons.plugin.simple.codegeneration.Arguments;

import com.sun.codemodel.JExpression;
import com.sun.codemodel.JVar;

public class HashCodeArguments implements Arguments {

	private final JVar currentHashCode;
	private final JVar value;
	private final JExpression hasSetValue;

	public HashCodeArguments(JVar currentHashCode,
			JVar value, JExpression hasSetValue) {
		this.currentHashCode = Validate.notNull(currentHashCode);
		this.value = Validate.notNull(value);
		this.hasSetValue = Validate.notNull(hasSetValue);
	}

	public JVar currentHashCode() {
		return currentHashCode;
	}

	public JVar value() {
		return value;
	}

	public JExpression hasSetValue() {
		return hasSetValue;
	}

	public HashCodeArguments spawn(JVar value, JExpression hasSetValue) {
		return new HashCodeArguments(currentHashCode(), value, hasSetValue);
	}

}
