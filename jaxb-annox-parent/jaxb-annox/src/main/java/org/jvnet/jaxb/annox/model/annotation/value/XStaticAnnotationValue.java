package org.jvnet.jaxb.annox.model.annotation.value;

import org.apache.commons.lang3.Validate;

public abstract class XStaticAnnotationValue<T> extends XAnnotationValue<T> {

	private final T value;

	public XStaticAnnotationValue(T value) {
		Validate.notNull(value);
		this.value = value;
	}

	@Override
	public T getValue() {
		return this.value;
	}

	@Override
	protected Object getInternalValue() {
		return getValue();
	}

}
