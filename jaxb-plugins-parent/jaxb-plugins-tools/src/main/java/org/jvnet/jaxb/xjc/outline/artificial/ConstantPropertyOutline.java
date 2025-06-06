package org.jvnet.jaxb.xjc.outline.artificial;

import java.util.Objects;
import org.jvnet.jaxb.xjc.outline.MClassOutline;
import org.jvnet.jaxb.xjc.outline.MPropertyAccessor;
import org.jvnet.jaxb.xml.bind.model.MPropertyInfo;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JVar;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.outline.Outline;

public abstract class ConstantPropertyOutline extends AbstractPropertyOutline {

	protected final JExpression value;

	protected final JFieldVar field;

	public ConstantPropertyOutline(Outline outline, MClassOutline classOutline,
			MPropertyInfo<NType, NClass> target, final JExpression value) {
		super(outline, classOutline, target);
		Objects.requireNonNull(value, "Value expression must not be null.");
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
				| JMod.FINAL, type, propertyInfo.getPublicName(), value);

		annotate(field);

		return field;
	}

	public MPropertyAccessor createPropertyAccessor(JExpression target) {
		return new PropertyAccessor(target);
	}

	public class PropertyAccessor extends
			AbstractPropertyOutline.PropertyAccessor {

		public PropertyAccessor(JExpression target) {
			super(target);
		}

		@Override
		public boolean isConstant() {
			return true;
		}

		public void unset(JBlock body) {
		}

		public void set(JBlock block, String uniqueName, JExpression value) {
		}

		@Override
		public boolean isVirtual() {
			return false;
		}

		public JExpression isSet() {
			return JExpr.TRUE;
		}

		public void get(JBlock block, JVar variable) {
			block.assign(variable, ConstantPropertyOutline.this.referenceClass
					.staticRef(ConstantPropertyOutline.this.field));
		}

	}
}
