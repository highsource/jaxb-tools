package org.jvnet.hyperjaxb3.xjc.generator.bean.field;

import java.util.ArrayList;
import java.util.List;

import org.jvnet.hyperjaxb3.item.ItemUtils;
import org.jvnet.hyperjaxb3.item.MixedItemUtils;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JFieldRef;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;
import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.generator.bean.MethodWriter;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CReferencePropertyInfo;
import com.sun.tools.xjc.outline.Aspect;
import com.sun.tools.xjc.outline.FieldAccessor;

public abstract class AbstractWrapCollectionField extends AbstractField {

	protected final JFieldRef propertyField;

	protected final JFieldRef wrappedPropertyField;

	protected final JFieldRef wrappingPropertyField;

	protected final JMethod getter;

	protected final JMethod setter;

	protected final CPropertyInfo property;

	protected final JClass propertyListType;

	protected final JType propertyExposedType;

	protected final CPropertyInfo wrappedProperty;

	protected final CPropertyInfo wrappingProperty;

	protected final JType wrappedPropertyExposedType;

	protected final JType wrappingPropertyExposedType;

	public AbstractWrapCollectionField(ClassOutlineImpl context,
			CPropertyInfo property, CPropertyInfo wrappedProperty,
			CPropertyInfo wrappingProperty) {
		super(context, property);
		this.property = property;
		this.wrappedProperty = wrappedProperty;
		this.wrappingProperty = wrappingProperty;

		propertyExposedType = getType(property, Aspect.EXPOSED);

		propertyListType = codeModel.ref(List.class).narrow(
				propertyExposedType.boxify());

		wrappedPropertyExposedType = getType(wrappedProperty, Aspect.EXPOSED);

		wrappingPropertyExposedType = getType(wrappingProperty, Aspect.EXPOSED);

		propertyField = createField();
		wrappedPropertyField = JExpr._this()
				.ref(wrappedProperty.getName(false));
		wrappingPropertyField = JExpr._this().ref(
				wrappingProperty.getName(false));

		getter = createGetter();
		setter = createSetter();

	}

	protected abstract JFieldRef createField();

	protected JMethod createSetter() {
		final JMethod setter;
		final MethodWriter writer = outline.createMethodWriter();
		setter = writer.declareMethod(codeModel.VOID, getSetterName());
		final JVar value = writer.addParameter(propertyListType, "value");
		final JBlock body = setter.body();
		body.assign(wrappedPropertyField, JExpr._null());
		body.assign(wrappingPropertyField, JExpr._null());
		body.assign(propertyField, value);
		fix(body);
		return setter;
	}

	protected String getSetterName() {
		return "set" + prop.getName(true);
	}

	protected JMethod createGetter() {
		final MethodWriter writer = outline.createMethodWriter();
		final JMethod getter = writer.declareMethod(propertyListType,
				getGetterName());
		final JBlock body = getter.body();
		fix(body);
		body._return(propertyField);
		return getter;
	}

	protected void fix(final JBlock body) {

		// final JFieldRef wrappedPropertyField = field;
		//
		// final JFieldRef wrappingPropertyField = itemsField;

		body._if(wrappingPropertyField.eq(JExpr._null()))._then().assign(
				wrappingPropertyField,
				JExpr._new(codeModel.ref(ArrayList.class).narrow(
						wrappingPropertyExposedType.boxify())));

		final JClass utilsClass =

		(wrappedProperty instanceof CReferencePropertyInfo && ((CReferencePropertyInfo) wrappedProperty)
				.isMixed()) ? codeModel.ref(MixedItemUtils.class) : codeModel
				.ref(ItemUtils.class);
		body._if(
				utilsClass.staticInvoke("shouldBeWrapped").arg(
						wrappedPropertyField))._then().assign(
				wrappedPropertyField,

				utilsClass.staticInvoke("wrap").arg(wrappedPropertyField).arg(
						wrappingPropertyField).arg(
						wrappingPropertyExposedType.boxify().dotclass()));
	}

	protected String getGetterName() {
		return (getFieldType().boxify().getPrimitiveType() == codeModel.BOOLEAN ? "is"
				: "get")
				+ prop.getName(true);
	}

	public final JType getFieldType() {
		return implType;
	}

	public FieldAccessor create(JExpression targetObject) {
		return new Accessor(targetObject);
	}

	public final JType getRawType() {
		return propertyExposedType;
	}

	protected class Accessor extends AbstractField.Accessor {

		Accessor(JExpression $target) {
			super($target);
		}

		public void unsetValues(JBlock body) {
			body.assign(propertyField, JExpr._null());
		}

		public JExpression hasSetValue() {
			return propertyField.ne(JExpr._null()).cand(
					propertyField.invoke("isEmpty").not());
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
