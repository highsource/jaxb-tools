package org.jvnet.annox.model.annotation.value;


public abstract class XAnnotationValue<T> {

	public abstract T getValue();

	protected abstract Object getInternalValue();

	public final Object getResult() {
		return getValue();
	}

	public abstract <P> P accept(XAnnotationValueVisitor<P> visitor);

	@Override
	public final String toString() {
		return getInternalValue().toString();
	}

	@Override
	public final int hashCode() {
		int hash = 0;
		final Object value = getInternalValue();
		hash = hash * 37 + value.hashCode();
		return hash;
	}

	@Override
	public final boolean equals(Object obj) {
		if (!(obj instanceof XAnnotationValue)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		final XAnnotationValue<?> other = (XAnnotationValue<?>) obj;
		return getInternalValue().equals(other.getInternalValue());
	}
}
