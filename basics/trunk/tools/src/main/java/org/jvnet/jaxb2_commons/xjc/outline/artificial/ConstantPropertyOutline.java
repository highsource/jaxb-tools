package org.jvnet.jaxb2_commons.xjc.outline.artificial;

import org.apache.commons.lang.Validate;
import org.jvnet.jaxb2_commons.xjc.outline.MClassOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MPropertyAccessor;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfo;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

public abstract class ConstantPropertyOutline extends AbstractPropertyOutline {

	protected final JExpression value;

	protected final JFieldVar field;

	public ConstantPropertyOutline(MClassOutline classOutline,
			MPropertyInfo target, final JExpression value) {
		super(classOutline, target);
		Validate.notNull(value);
		this.value = value;
		this.field = generateField();
	}

	protected JExpression createValue() {
		return value;
	}

	protected JFieldVar generateField() {
		// generate the constant
		JExpression value = createValue();

		JFieldVar field = referenceClass.field(JMod.PUBLIC | JMod.STATIC
				| JMod.FINAL, implementationType,
		// TODO public name
				propertyInfo.getPrivateName(), value);

		annotate(field);

		return field;
	}

	@Override
	public MPropertyAccessor createPropertyAccessor(JExpression target) {
		return new MPropertyAccessor() {

			public void unset(JBlock body) {
			}

			public void set(JBlock block, String uniqueName, JExpression value) {
			}

			public boolean isVirtual() {
				return false;
			}

			public JExpression isSet() {
				return JExpr.TRUE;
			}

			public boolean isConstant() {
				return true;
			}

			@Override
			public JType getType() {
				return ConstantPropertyOutline.this.referenceType;
			}

			@Override
			public void get(JBlock block, JVar variable) {
				block.assign(variable,
						ConstantPropertyOutline.this.referenceClass
								.staticRef(ConstantPropertyOutline.this.field));
			}
		};
	}
}
