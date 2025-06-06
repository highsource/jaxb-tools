package org.hisrc.xml.xsom;

import java.util.Objects;

import com.sun.xml.xsom.XSComponent;
import com.sun.xml.xsom.visitor.XSFunction;

public class XSFunctionApplier<T> {

	private XSFunction<T> f;

	public XSFunctionApplier(XSFunction<T> f) {
		this.f = Objects.requireNonNull(f, "Function must not be null.");
	}

	public T apply(Object target) {
		Objects.requireNonNull(target, "Target must not be null.");
		if (target instanceof XSComponent) {
			return ((XSComponent) target).apply(f);
		} else if (target instanceof SchemaComponentAware) {
			final XSComponent schemaComponent = ((SchemaComponentAware) target)
					.getSchemaComponent();
			if (schemaComponent == null) {
				return null;
			} else {
				return schemaComponent.apply(f);
			}
		} else {
			return null;
		}
	}

}
