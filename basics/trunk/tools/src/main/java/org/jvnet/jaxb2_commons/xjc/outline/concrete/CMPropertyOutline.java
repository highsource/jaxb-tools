package org.jvnet.jaxb2_commons.xjc.outline.concrete;

import org.apache.commons.lang.Validate;
import org.jvnet.jaxb2_commons.xjc.outline.MClassOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MPropertyAccessor;
import org.jvnet.jaxb2_commons.xjc.outline.MPropertyAccessorFactory;
import org.jvnet.jaxb2_commons.xjc.outline.MPropertyOutline;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfo;

import com.sun.codemodel.JExpression;

public class CMPropertyOutline implements MPropertyOutline {

	private final MClassOutline classOutline;

	private final MPropertyInfo target;

	private final MPropertyAccessorFactory propertyAccessorFactory;

	public CMPropertyOutline(MClassOutline classOutline, MPropertyInfo target,
			MPropertyAccessorFactory propertyAccessorFactory) {
		Validate.notNull(classOutline);
		Validate.notNull(target);
		Validate.notNull(propertyAccessorFactory);
		this.classOutline = classOutline;
		this.target = target;
		this.propertyAccessorFactory = propertyAccessorFactory;
	}

	public MClassOutline getClassOutline() {
		return classOutline;
	}

	public MPropertyInfo getTarget() {
		return target;
	}

	@Override
	public MPropertyAccessor createPropertyAccessor(JExpression target) {
		return this.propertyAccessorFactory.createPropertyAccessor(target);
	}

}
