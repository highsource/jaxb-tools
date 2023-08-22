package org.jvnet.hyperjaxb3.xjc.generator.bean.field;

import javax.xml.bind.JAXBElement.GlobalScope;

import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JConditional;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JFieldRef;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;
import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.generator.bean.MethodWriter;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import  com.sun.tools.xjc.model.Aspect;
import com.sun.tools.xjc.outline.FieldAccessor;

public abstract class AbstractWrappingField extends AbstractField {

	protected final CPropertyInfo core;
	protected JMethod getter;
	protected JMethod setter;
	protected final JFieldRef coreField;

	public AbstractWrappingField(ClassOutlineImpl context, CPropertyInfo prop,
			CPropertyInfo core) {
		super(context, prop);
		this.core = core;
		if (!Customizations.isGenerated(prop)) {
			this.coreField = JExpr.refthis(core.getName(false));
		} else {
			this.coreField = null;

		}

		assert !exposedType.isPrimitive() && !implType.isPrimitive();
	}

	public JExpression getCore() {
		if (coreField != null) {
			return coreField;
		} else {
			return JExpr._this().invoke("get" + core.getName(true));
		}
	}

	public void setCore(JBlock block, JExpression value) {
		if (coreField != null) {
			block.assign(coreField, value);
		} else {
			block.invoke("set" + core.getName(true)).arg(value);
		}
	}

	public void generateAccessors() {
		getter = createGetter();
		setter = createSetter();
	}

	protected JMethod createSetter() {
		final JMethod setter;

		final MethodWriter writer = outline.createMethodWriter();
		setter = writer.declareMethod(codeModel.VOID, getSetterName());
		final JVar target = writer.addParameter(exposedType, "target");

		final JExpression wrapCondition = wrapCondifiton(target);
		if (wrapCondition == null) {
			setCore(setter.body(), wrap(target));
		} else {
			final JConditional _if = setter.body()._if(wrapCondition);
			setCore(_if._then(), wrap(target));
		}
		return setter;
	}

	protected String getSetterName() {
		return "set" + prop.getName(true);
	}

	protected String getGetterName() {
		return (getFieldType().boxify().getPrimitiveType() == codeModel.BOOLEAN ? "is"
				: "get")
				+ prop.getName(true);
	}

	protected JMethod createGetter() {
		final MethodWriter writer = outline.createMethodWriter();
		final JMethod getter = writer.declareMethod(exposedType,
				getGetterName());
		JExpression source = getCore();
		final JExpression unwrapCondition = unwrapCondifiton(source);
		if (unwrapCondition == null) {
			getter.body()._return(unwrap(source));
		} else {
			final JConditional _if = getter.body()._if(unwrapCondition);
			_if._then()._return(unwrap(source));
			_if._else()._return(JExpr._null());
		}
		return getter;
	}

	protected abstract JExpression unwrap(final JExpression source);

	public JExpression unwrapCondifiton(final JExpression source) {
		return null;
	}

	protected abstract JExpression wrap(final JExpression target);

	public JExpression wrapCondifiton(final JExpression source) {
		return null;
	}

	public final JType getFieldType() {
		return implType;
	}

	public FieldAccessor create(JExpression targetObject) {
		return new Accessor(targetObject);
	}

	public final JType getRawType() {
		return exposedType;
	}

	public JClass getScope(CClassInfo scope) {
		if (scope == null) {
			return codeModel.ref(GlobalScope.class);
		} else {
			return scope.toType(outline.parent(), Aspect.EXPOSED);
		}
	}

	class Accessor extends AbstractField.Accessor {

		// private final FieldAccessor core;

		private final JFieldRef coreField;

		Accessor(JExpression $target) {
			super($target);
			this.coreField = $target.ref(core.getName(false));
		}

		public void unsetValues(JBlock body) {
			body.assign(coreField, JExpr._null());
		}

		public JExpression hasSetValue() {
			return coreField.ne(JExpr._null());
		}

		public final void toRawValue(JBlock block, JVar $var) {
			block.assign($var, $target.invoke(getter));
		}

		public final void fromRawValue(JBlock block, String uniqueName,
				JExpression $var) {
			block.invoke($target, setter).arg($var);
		}
	}

}