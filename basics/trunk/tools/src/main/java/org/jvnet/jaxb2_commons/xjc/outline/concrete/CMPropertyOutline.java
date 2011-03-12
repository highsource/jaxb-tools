package org.jvnet.jaxb2_commons.xjc.outline.concrete;

import org.apache.commons.lang.Validate;
import org.jvnet.jaxb2_commons.xjc.outline.MClassOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MPropertyAccessor;
import org.jvnet.jaxb2_commons.xjc.outline.MPropertyAccessorFactory;
import org.jvnet.jaxb2_commons.xjc.outline.MPropertyOutline;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfo;

import com.sun.codemodel.JExpression;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;

public class CMPropertyOutline implements MPropertyOutline {

	private final MClassOutline classOutline;

	private final MPropertyInfo<NType, NClass> target;

	private final MPropertyAccessorFactory propertyAccessorFactory;

	public CMPropertyOutline(MClassOutline classOutline,
			MPropertyInfo<NType, NClass> target,
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

	public MPropertyInfo<NType, NClass> getTarget() {
		return target;
	}

	public MPropertyAccessor createPropertyAccessor(JExpression target) {
		return this.propertyAccessorFactory.createPropertyAccessor(target);
	}

}
