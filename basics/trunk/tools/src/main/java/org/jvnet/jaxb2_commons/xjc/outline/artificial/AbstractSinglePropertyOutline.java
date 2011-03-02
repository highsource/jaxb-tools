package org.jvnet.jaxb2_commons.xjc.outline.artificial;

import org.jvnet.jaxb2_commons.xjc.outline.MClassOutline;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfo;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

public abstract class AbstractSinglePropertyOutline extends
		AbstractPropertyOutline {

	protected final JFieldVar field;

	protected final JMethod getter;

	protected final JMethod setter;

	public AbstractSinglePropertyOutline(MClassOutline classOutline,
			MPropertyInfo target) {
		super(classOutline, target);
		this.field = generateField();
		this.getter = generateGetter();
		this.setter = generateSetter();
	}

	protected JFieldVar generateField() {
		final JFieldVar field = implementationClass.field(JMod.PROTECTED,
				implementationType, propertyInfo.getPrivateName());
		annotate(field);
		return field;

	}

	protected abstract JMethod generateGetter();

	protected abstract JMethod generateSetter();

	protected String getGetterMethodName() {
		return (implementationType.boxify().getPrimitiveType() == codeModel.BOOLEAN ? "is"
				: "get")
				+
				// TODO public name
				propertyInfo.getPrivateName();
	}

	protected abstract class PropertyAccessor extends
			AbstractPropertyOutline.PropertyAccessor {

		public PropertyAccessor(JExpression target) {
			super(target);
		}

		public JType getType() {
			return AbstractSinglePropertyOutline.this.implementationType;
		}

		public boolean isVirtual() {
			return false;
		}

		@Override
		public void get(JBlock block, JVar variable) {
			block.assign(variable,
					target.invoke(AbstractSinglePropertyOutline.this.getter));
		}

		@Override
		public void set(JBlock block, String uniqueName, JExpression value) {
			block.invoke(target, AbstractSinglePropertyOutline.this.setter)
					.arg(value);
		}
	}

}
