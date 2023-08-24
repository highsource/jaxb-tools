package org.jvnet.jaxb.annox.model.annotation.field;

import org.jvnet.jaxb.annox.model.XAnnotationFieldVisitor;
import org.jvnet.jaxb.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.jaxb.annox.util.ClassUtils;

public class XSingleAnnotationField<T> extends XAnnotationField<T> {

	private final Class<?> type;
	private final XAnnotationValue<T> annotationValue;

	public XSingleAnnotationField(String name, Class<?> type,
			XAnnotationValue<T> value) {
		super(name);
		this.type = ClassUtils.wrapperToPrimitive(type);
		this.annotationValue = value;
	}

	@Override
	public Class<?> getType() {
		return type;
	}

	@Override
	public T getValue() {
		return this.annotationValue.getValue();
	}

	public XAnnotationValue<T> getAnnotationValue() {
		return annotationValue;
	}

	@Override
	public String toString() {
		return getName() + "=" + getAnnotationValue().toString();
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash = hash * 37 + getName().hashCode();
		hash = hash * 37 + getAnnotationValue().hashCode();
		return hash;
	}

	@Override
	public final boolean equals(Object obj) {
		if (!(obj instanceof XSingleAnnotationField)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		final XSingleAnnotationField<?> other = (XSingleAnnotationField<?>) obj;
		return getName().equals(other.getName())
				&& getAnnotationValue().equals(other.getAnnotationValue());
	}

	@Override
	public <P> P accept(XAnnotationFieldVisitor<P> visitor) {
		return visitor.visitSingleAnnotationField(this);
	}

}
