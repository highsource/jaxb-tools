package org.jvnet.jaxb2_commons.xjc.outline.artificial;

import java.util.HashMap;
import java.util.Map;

import jakarta.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.xjc.outline.MClassOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MPropertyAccessor;
import org.jvnet.jaxb2_commons.xml.bind.model.MAnyAttributePropertyInfo;

import com.sun.codemodel.JAnnotatable;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.outline.Outline;

public class AnyAttributePropertyOutline extends AbstractPropertyOutline {

	protected final JType type;

	protected final JFieldVar field;

	protected final JMethod getter;

	protected final JMethod setter;

	public AnyAttributePropertyOutline(Outline outline,
			MClassOutline classOutline,
			MAnyAttributePropertyInfo<NType, NClass> target) {
		super(outline, classOutline, target);

		this.type = generateType();

		this.field = generateField();

		this.getter = generateGetter();

		this.setter = generateSetter();

		annotate(this.field);

	}

	protected JType generateType() {
		return codeModel.ref(Map.class).narrow(QName.class)
				.narrow(Object.class);
	}

	protected JFieldVar generateField() {
		final JFieldVar field = referenceClass.field(
				JMod.PROTECTED,
				type,
				propertyInfo.getPrivateName(),

				JExpr._new(codeModel.ref(HashMap.class).narrow(QName.class)
						.narrow(Object.class)));
		return field;
	}

	protected void annotate(JAnnotatable annotatable) {
		annotatable.annotate(XmlAnyAttribute.class);
	}

	protected JMethod generateGetter() {
		final JMethod getter = referenceClass.method(JMod.PUBLIC, type, "get"
				+ propertyInfo.getPublicName());

		getter.body()._return(JExpr._this().ref(this.field));
		return getter;
	}

	protected JMethod generateSetter() {
		JMethod setter = referenceClass.method(JMod.PUBLIC, codeModel.VOID,
				"set" + propertyInfo.getPublicName());

		JVar value = setter.param(type, "value");

		setter.body().invoke(JExpr._this().ref(this.field), "clear");
		setter.body().invoke(JExpr._this().ref(this.field), "putAll")
				.arg(value);
		return setter;
	}

	public MPropertyAccessor createPropertyAccessor(JExpression target) {
		return new PropertyAccessor(target);
	}

	public class PropertyAccessor extends
			AbstractPropertyOutline.PropertyAccessor {

		public PropertyAccessor(JExpression target) {
			super(target);
		}

		public void get(JBlock block, JVar variable) {
			target.invoke(AnyAttributePropertyOutline.this.getter);

		}

		public void set(JBlock block, String uniqueName, JExpression value) {
			target.invoke(AnyAttributePropertyOutline.this.setter).arg(value);
		}

		public void unset(JBlock body) {
			target.invoke(AnyAttributePropertyOutline.this.getter).invoke(
					"clear");
		}

		public JExpression isSet() {
			return target.invoke(AnyAttributePropertyOutline.this.getter)
					.invoke("isEmpty").not();
		}

		@Override
		public JType getType() {
			return AnyAttributePropertyOutline.this.type;
		}

		@Override
		public boolean isVirtual() {
			return false;
		}

	}

}
