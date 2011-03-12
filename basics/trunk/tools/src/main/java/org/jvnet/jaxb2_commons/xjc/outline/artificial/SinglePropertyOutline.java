package org.jvnet.jaxb2_commons.xjc.outline.artificial;

import org.jvnet.jaxb2_commons.xjc.outline.MClassOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MPropertyAccessor;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfo;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JVar;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.outline.Outline;

public class SinglePropertyOutline extends AbstractSinglePropertyOutline {

	public SinglePropertyOutline(Outline outline, MClassOutline classOutline,
			MPropertyInfo<NType, NClass> target) {
		super(outline, classOutline, target);
	}

	protected JMethod generateGetter() {
		final JMethod getter = referenceClass.method(JMod.PUBLIC,
				type, getGetterMethodName());
		getter.body()._return(field);
		return getter;
	}

	protected JMethod generateSetter() {
		final JMethod setter = referenceClass.method(JMod.PUBLIC,
				codeModel.VOID, getSetterMethodName());
		final JVar value = setter.param(type, "value");
		setter.body().assign(JExpr._this().ref(field), value);
		return setter;
	}

	public MPropertyAccessor createPropertyAccessor(JExpression target) {
		return new PropertyAccessor(target);
	}

	protected class PropertyAccessor extends
			AbstractSinglePropertyOutline.PropertyAccessor {
		public PropertyAccessor(JExpression target) {
			super(target);
		}

		public JExpression isSet() {
			return field.ne(JExpr._null());
		}

		public void unset(JBlock body) {
			body.assign(field, JExpr._null());

		}
	}
}
