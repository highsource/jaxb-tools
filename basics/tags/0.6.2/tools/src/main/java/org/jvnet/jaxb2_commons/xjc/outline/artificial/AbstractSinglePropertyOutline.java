package org.jvnet.jaxb2_commons.xjc.outline.artificial;

import org.jvnet.jaxb2_commons.xjc.outline.MClassOutline;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfo;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JVar;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.outline.Outline;

public abstract class AbstractSinglePropertyOutline extends
		AbstractPropertyOutline {

	protected final JFieldVar field;

	protected final JMethod getter;

	protected final JMethod setter;

	public AbstractSinglePropertyOutline(Outline outline,
			MClassOutline classOutline, MPropertyInfo<NType, NClass> target) {
		super(outline, classOutline, target);
		this.field = generateField();
		this.getter = generateGetter();
		this.setter = generateSetter();
	}

	protected JFieldVar generateField() {
		final JFieldVar field = referenceClass.field(JMod.PROTECTED, type,
				propertyInfo.getPrivateName());
		annotate(field);
		return field;

	}

	protected abstract JMethod generateGetter();

	protected abstract JMethod generateSetter();

	protected String getGetterMethodName() {
		return (type.boxify().getPrimitiveType() == codeModel.BOOLEAN ? "is"
				: "get") + propertyInfo.getPublicName();
	}

	protected String getSetterMethodName() {
		return "set" + propertyInfo.getPublicName();
	}

	protected abstract class PropertyAccessor extends
			AbstractPropertyOutline.PropertyAccessor {

		public PropertyAccessor(JExpression target) {
			super(target);
		}

		public void get(JBlock block, JVar variable) {
			block.assign(variable,
					target.invoke(AbstractSinglePropertyOutline.this.getter));
		}

		public void set(JBlock block, String uniqueName, JExpression value) {
			block.invoke(target, AbstractSinglePropertyOutline.this.setter)
					.arg(value);
		}
	}

}
