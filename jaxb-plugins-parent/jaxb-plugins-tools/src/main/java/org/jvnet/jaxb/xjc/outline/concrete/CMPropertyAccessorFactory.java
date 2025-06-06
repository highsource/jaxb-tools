package org.jvnet.jaxb.xjc.outline.concrete;

import java.util.Objects;
import org.jvnet.jaxb.util.FieldAccessorFactory;
import org.jvnet.jaxb.xjc.outline.FieldAccessorEx;
import org.jvnet.jaxb.xjc.outline.MPropertyAccessor;
import org.jvnet.jaxb.xjc.outline.MPropertyAccessorFactory;

import com.sun.codemodel.JExpression;
import com.sun.tools.xjc.outline.FieldOutline;

public class CMPropertyAccessorFactory implements MPropertyAccessorFactory {

	private final FieldAccessorFactory fieldAccessorFactory;
	private final FieldOutline fieldOutline;

	public CMPropertyAccessorFactory(FieldAccessorFactory fieldAccessorFactory,
			FieldOutline fieldOutline) {
		Objects.requireNonNull(fieldAccessorFactory, "Field accessor factory must not be null.");
		Objects.requireNonNull(fieldOutline, "Field outline must not be null.");
		this.fieldAccessorFactory = fieldAccessorFactory;
		this.fieldOutline = fieldOutline;
	}

	public MPropertyAccessor createPropertyAccessor(JExpression target) {
		FieldAccessorEx fieldAccessor = fieldAccessorFactory
				.createFieldAccessor(fieldOutline, target);
		return new CMPropertyAccessor(fieldAccessor);
	}

}
