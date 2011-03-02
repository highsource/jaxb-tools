package org.jvnet.jaxb2_commons.xjc.outline.artificial;

import org.jvnet.jaxb2_commons.xjc.outline.MClassOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MPropertyAccessor;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfo;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JMethod;

public class SinglePropertyOutline extends AbstractSinglePropertyOutline {

	public SinglePropertyOutline(MClassOutline classOutline,
			MPropertyInfo target) {
		super(classOutline, target);
	}
	
	protected JMethod generateGetter() {
		return null;
	}
	
	protected JMethod generateSetter() {
		return null;
	}

	@Override
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
