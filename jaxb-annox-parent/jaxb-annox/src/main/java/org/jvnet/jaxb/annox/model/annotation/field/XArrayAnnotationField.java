package org.jvnet.jaxb.annox.model.annotation.field;

import java.lang.reflect.Array;
import java.text.MessageFormat;

import org.apache.commons.lang3.Validate;
import org.jvnet.jaxb.annox.model.XAnnotationFieldVisitor;
import org.jvnet.jaxb.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.jaxb.annox.util.ClassUtils;

public class XArrayAnnotationField<T> extends XAnnotationField<T[]> {

	private final Class<?> type;
	private final Class<?> componentType;
	private final Class<?> wrapperComponentType;
	private final XAnnotationValue<T>[] annotationValues;

	public XArrayAnnotationField(String name, Class<?> type,
			XAnnotationValue<T>... values) {
		super(name);
		if (!type.isArray()) {
			throw new IllegalArgumentException(MessageFormat.format(
					"Type [{0}] is expected to be an array type.", type));
		}
		type = ClassUtils.wrapperArrayToPrimitiveArray(type);
		this.annotationValues = Validate.noNullElements(values);
		this.type = type;
		this.componentType = type.getComponentType();
		this.wrapperComponentType = ClassUtils
				.primitiveToWrapper(this.componentType);
	}

	@Override
	public Class<?> getType() {
		return this.type;
	}

	public XAnnotationValue<T>[] getAnnotationValues() {
		return annotationValues;
	}

	@Override
	public T[] getValue() {
		final Object array = Array.newInstance(wrapperComponentType,
				this.annotationValues.length);
		@SuppressWarnings("unchecked")
		final T[] value = (T[]) array;
		for (int index = 0; index < this.annotationValues.length; index++) {
			final T singleValue = this.annotationValues[index].getValue();
			value[index] = singleValue;
		}
		return value;
	}

	@Override
	public Object getResult() {
		final Object array = Array.newInstance(this.componentType,
				this.annotationValues.length);
		for (int index = 0; index < this.annotationValues.length; index++) {
			Array.set(array, index, this.annotationValues[index].getValue());
		}
		return array;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();
		sb.append(getName());
		sb.append("=[");
		final XAnnotationValue<T>[] values = getAnnotationValues();
		for (int index = 0; index < values.length; index++) {
			final XAnnotationValue<T> value = values[index];
			if (index > 0) {
				sb.append(", ");
			}
			sb.append(value.toString());
		}
		sb.append("]");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		int hash = 0;
		final String name = getName();
		hash = hash * 37 + name.hashCode();
		final XAnnotationValue<T>[] values = getAnnotationValues();
		for (int index = 0; index < values.length; index++) {
			final XAnnotationValue<T> v = values[index];
			hash = hash * 37 + v.hashCode();
		}
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof XArrayAnnotationField<?>)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		final XArrayAnnotationField<?> other = (XArrayAnnotationField<?>) obj;
		if (!getName().equals(other.getName()))
			return false;

		final XAnnotationValue<?>[] lhs = getAnnotationValues();
		final XAnnotationValue<?>[] rhs = other.getAnnotationValues();
		if (lhs == rhs) {
			return true;
		}
		if (lhs == null || rhs == null) {
			return false;
		}
		if (lhs.length != rhs.length) {
			return false;
		}
		for (int i = 0; i < lhs.length; ++i) {
			if (!(lhs[i].equals(rhs[i])))
				return false;
		}
		return true;
	}

	@Override
	public <P> P accept(XAnnotationFieldVisitor<P> visitor) {
		return visitor.visitArrayAnnotationField(this);
	}

}
