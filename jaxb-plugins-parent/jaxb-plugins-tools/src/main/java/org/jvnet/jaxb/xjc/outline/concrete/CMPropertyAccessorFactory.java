package org.jvnet.jaxb.xjc.outline.concrete;

import org.apache.commons.lang3.Validate;
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
		Validate.notNull(fieldAccessorFactory);
		Validate.notNull(fieldOutline);
		this.fieldAccessorFactory = fieldAccessorFactory;
		this.fieldOutline = fieldOutline;
	}

	public MPropertyAccessor createPropertyAccessor(JExpression target) {
		FieldAccessorEx fieldAccessor = fieldAccessorFactory
				.createFieldAccessor(fieldOutline, target);
		return new CMPropertyAccessor(fieldAccessor);
	}

}
