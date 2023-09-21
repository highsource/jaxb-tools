package org.jvnet.jaxb.xjc.outline.concrete;

import org.apache.commons.lang3.Validate;
import org.jvnet.jaxb.xjc.outline.FieldAccessorEx;
import org.jvnet.jaxb.xjc.outline.MPropertyAccessor;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

public class CMPropertyAccessor implements MPropertyAccessor {

	private final FieldAccessorEx fieldAccessor;

	public CMPropertyAccessor(FieldAccessorEx fieldAccessor) {
		Validate.notNull(fieldAccessor);
		this.fieldAccessor = fieldAccessor;
	}

	public void get(JBlock block, JVar variable) {
		fieldAccessor.toRawValue(block, variable);
	}

	public void set(JBlock block, String uniqueName, JExpression value) {
		fieldAccessor.fromRawValue(block, uniqueName, value);
	}

	public void unset(JBlock body) {
		fieldAccessor.unsetValues(body);
	}

	public JExpression isSet() {
		return fieldAccessor.hasSetValue();
	}

	public JType getType() {
		return fieldAccessor.getType();
	}

	public boolean isConstant() {
		return fieldAccessor.isConstant();
	}

	public boolean isVirtual() {
		return fieldAccessor.isVirtual();
	}

}
