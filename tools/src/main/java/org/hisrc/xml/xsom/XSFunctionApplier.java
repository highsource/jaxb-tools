package org.hisrc.xml.xsom;

import org.apache.commons.lang3.Validate;

import com.sun.xml.xsom.XSComponent;
import com.sun.xml.xsom.visitor.XSFunction;

public class XSFunctionApplier<T> {

	private XSFunction<T> f;

	public XSFunctionApplier(XSFunction<T> f) {
		this.f = Validate.notNull(f);
	}

	public T apply(Object target) {
		Validate.notNull(target);
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
