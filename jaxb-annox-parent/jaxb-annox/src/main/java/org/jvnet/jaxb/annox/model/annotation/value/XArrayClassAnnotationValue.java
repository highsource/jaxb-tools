package org.jvnet.jaxb.annox.model.annotation.value;

import org.jvnet.jaxb.annox.util.Validate;

import java.lang.reflect.Array;
import java.util.Arrays;

public class XArrayClassAnnotationValue<A, I> extends
		XDynamicAnnotationValue<Class<A>> {

	private final XClassByNameAnnotationValue<I> itemClassByNameAnnotationValue;
	private final int dimension;
	private final String arrayClassName;

	public XArrayClassAnnotationValue(
			XClassByNameAnnotationValue<I> itemClassByNameAnnotationValue,
			int dimension) {
		this.itemClassByNameAnnotationValue = Validate.notNull(itemClassByNameAnnotationValue);
		Validate.isTrue(dimension > 0);
		this.dimension = dimension;
		String arrayClassName = itemClassByNameAnnotationValue.getClassName();
		for (int index = 0; index < dimension; index++) {
			arrayClassName = arrayClassName + "[]";
		}
		this.arrayClassName = arrayClassName;
	}

	public XClassByNameAnnotationValue<I> getItemClassByNameAnnotationValue() {
		return itemClassByNameAnnotationValue;
	}

	public String getItemClassName() {
		return itemClassByNameAnnotationValue.getClassName();
	}

	public int getDimension() {
		return dimension;
	}

	@Override
	protected Object getInternalValue() {
		return this.arrayClassName;
	}

	@Override
	public <P> P accept(XAnnotationValueVisitor<P> visitor) {
		return visitor.visit(this);
	}

	@Override
	public Class<A> getValue() {
		final Class<I> componentType = this.itemClassByNameAnnotationValue
				.getValue();

		final int[] dimensions = new int[this.dimension];
		Arrays.fill(dimensions, 0);
		final Object array = Array.newInstance(componentType, dimensions);
		@SuppressWarnings("unchecked")
		final Class<A> arrayClass = (Class<A>) array.getClass();
		return arrayClass;
	}
}
