package org.jvnet.jaxb2_commons.xjc.outline.concrete;

import org.jvnet.jaxb2_commons.xjc.outline.FieldAccessorEx;
import org.jvnet.jaxb2_commons.xjc.outline.MPropertyAccessor;
import org.jvnet.jaxb2_commons.xjc.outline.MPropertyOutline;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

public class CMPropertyAccessor implements MPropertyAccessor {

	private final MPropertyOutline propertyOutline;

	private final FieldAccessorEx fieldAccessor;

	public CMPropertyAccessor(MPropertyOutline propertyOutline,
			FieldAccessorEx fieldAccessor) {
		super();
		this.propertyOutline = propertyOutline;
		this.fieldAccessor = fieldAccessor;
	}

	@Override
	public MPropertyOutline getPropertyOutline() {
		return propertyOutline;
	}

	@Override
	public void get(JBlock block, JVar variable) {
		fieldAccessor.toRawValue(block, variable);
	}

	@Override
	public void set(JBlock block, String uniqueName, JExpression value) {
		fieldAccessor.fromRawValue(block, uniqueName, value);
	}

	@Override
	public void unset(JBlock body) {
		fieldAccessor.unsetValues(body);
	}

	@Override
	public JExpression isSet() {
		return fieldAccessor.hasSetValue();
	}

	@Override
	public JType getType() {
		return fieldAccessor.getType();
	}

	@Override
	public boolean isConstant() {
		return fieldAccessor.isConstant();
	}

	@Override
	public boolean isVirtual() {
		return fieldAccessor.isVirtual();
	}

}
