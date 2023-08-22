package org.jvnet.hyperjaxb3.xjc.generator.bean.field;

import org.jvnet.hyperjaxb3.xml.bind.JAXBElementUtils;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JFieldRef;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JOp;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;
import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.generator.bean.MethodWriter;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.outline.FieldAccessor;

public class SingleElementField extends AbstractField {

	protected final JFieldRef field;

	protected final JMethod getter;

	protected final JMethod setter;

	protected final CPropertyInfo nameProperty;

	protected final JFieldRef nameField;

	protected final CPropertyInfo valueProperty;

	protected final JFieldRef valueField;

	public SingleElementField(ClassOutlineImpl outline,
			CPropertyInfo property, CPropertyInfo nameProperty,
			CPropertyInfo valueProperty) {
		super(outline, property);

		this.nameProperty = nameProperty;

		this.nameField = JExpr.refthis(nameProperty.getName(false));

		this.valueProperty = valueProperty;

		this.valueField = JExpr.refthis(valueProperty.getName(false));

		final JFieldVar field = outline.implClass.field(JMod.PROTECTED,

		exposedType, property.getName(false));

		annotate(field);

		this.field = JExpr._this().ref(field);

		this.setter = createSetter();
		this.getter = createGetter();

	}

	protected JMethod createSetter() {
		final MethodWriter writer = outline.createMethodWriter();

		final JMethod setter = writer.declareMethod(codeModel.VOID,
				getSetterName());
		final JVar var = writer.addParameter(exposedType, prop.getName(false));
		final JBlock block = setter.body();
		block.assign(field, var);
		block.assign(nameField, codeModel.ref(JAXBElementUtils.class)
				.staticInvoke("getName").arg(var));
		block.assign(valueField, codeModel.ref(JAXBElementUtils.class)
				.staticInvoke("getValue").arg(var));
		return setter;
	}
	
	protected JMethod createGetter() {
		
		
		final MethodWriter writer = outline.createMethodWriter();

		final JMethod getter = writer.declareMethod(exposedType,
				getGetterName());
		final JBlock block = getter.body();
		
		block._if(codeModel.ref(JAXBElementUtils.class).staticInvoke("shouldBeWrapped").arg(field).arg(nameField).arg(valueField)).
		_then().assign(field, codeModel.ref(JAXBElementUtils.class).staticInvoke("wrap").arg(field).arg(nameField).arg(valueField));
		block._return(field);
		return setter;
	}
	

	protected String getSetterName() {
		return "set" + prop.getName(true);
	}

	protected String getGetterName() {
		return (exposedType.boxify().getPrimitiveType() == codeModel.BOOLEAN ? "is"
				: "get")
				+ prop.getName(true);
	}
	
	private class Accessor extends AbstractField.Accessor {

		Accessor(JExpression $target) {
			super($target);
		}

		public void unsetValues(JBlock body) {
			body.assign(field, JExpr._null());
		}

		public JExpression hasSetValue() {
			return JOp.ne(field, JExpr._null());
		}

		public final void toRawValue(JBlock block, JVar $var) {
			block.assign($var, $target.invoke(getter));
		}

		public final void fromRawValue(JBlock block, String uniqueName,
				JExpression $var) {
			block.invoke($target, setter).arg($var);
		}
	}


	public FieldAccessor create(JExpression targetObject) {
		return new Accessor(targetObject);
	}
	
	

	public JType getRawType() {
		return exposedType;
	}

}
