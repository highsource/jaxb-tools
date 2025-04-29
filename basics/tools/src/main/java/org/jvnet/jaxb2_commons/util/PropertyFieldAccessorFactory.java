package org.jvnet.jaxb2_commons.util;

import com.sun.tools.xjc.model.CAttributePropertyInfo;
import org.jvnet.jaxb2_commons.xjc.outline.FieldAccessorEx;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JConditional;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JOp;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;
import com.sun.tools.xjc.model.CDefaultValue;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.outline.FieldAccessor;
import com.sun.tools.xjc.outline.FieldOutline;

public class PropertyFieldAccessorFactory implements FieldAccessorFactory {

	public static FieldAccessorFactory INSTANCE = new PropertyFieldAccessorFactory();

	public PropertyFieldAccessorFactory() {

	}

	public FieldAccessorEx createFieldAccessor(FieldOutline fieldOutline,
			JExpression targetObject) {
		return new PropertyFieldAccessor(fieldOutline, targetObject);
	}

	private static class PropertyFieldAccessor implements FieldAccessorEx {
		private static final JType[] ABSENT = new JType[0];
		private final FieldOutline fieldOutline;
		private final JExpression targetObject;
		private final JDefinedClass theClass;
		private final JMethod isSetter;
		private final JMethod unSetter;
		private final JMethod getter;
		private final JMethod setter;
		private final JFieldVar constantField;
		private final JFieldVar field;
		private FieldAccessor fieldAccessor;
		private final JType type;
		@SuppressWarnings("unused")
		private final JType fieldType;

		public PropertyFieldAccessor(final FieldOutline fieldOutline,
				JExpression targetObject) {
			super();
			this.fieldOutline = fieldOutline;
			this.targetObject = targetObject;
			this.fieldAccessor = fieldOutline.create(targetObject);
			final String publicName = fieldOutline.getPropertyInfo().getName(
					true);
			final String privateName = fieldOutline.getPropertyInfo().getName(
					false);
			this.theClass = fieldOutline.parent().implClass;
			final String setterName = "set" + publicName;
			final JMethod getGetter = theClass.getMethod("get" + publicName,
					ABSENT);
			final JMethod isGetter = theClass.getMethod("is" + publicName,
					ABSENT);
			final JFieldVar field = theClass.fields().get(privateName);
			this.field = field != null
					&& ((field.mods().getValue() & JMod.PROTECTED) != 0)
					&& ((field.mods().getValue() & JMod.STATIC) == 0)
					&& ((field.mods().getValue() & JMod.FINAL) == 0) ? field
					: null;
			this.getter = getGetter != null ? getGetter
					: (isGetter != null ? isGetter : null);
			this.type = this.getter != null ? this.getter.type() : fieldOutline
					.getRawType();
			this.fieldType = this.field != null ? this.field.type() : this.type;

			final JFieldVar constantField = theClass.fields().get(publicName);
			this.constantField = constantField != null
					&& ((constantField.mods().getValue() & JMod.PUBLIC) != 0)
					&& ((constantField.mods().getValue() & JMod.STATIC) != 0)
					&& ((constantField.mods().getValue() & JMod.FINAL) != 0) ? constantField
					: null;
			// fieldOutline.getRawType();
			final JType rawType = fieldOutline.getRawType();
			final JMethod boxifiedSetter = theClass.getMethod(setterName,
					new JType[] { rawType.boxify() });
			final JMethod unboxifiedSetter = theClass.getMethod(setterName,
					new JType[] { rawType.unboxify() });
			this.setter = boxifiedSetter != null ? boxifiedSetter
					: unboxifiedSetter;
			this.isSetter = theClass.getMethod("isSet" + publicName, ABSENT);
			this.unSetter = theClass.getMethod("unset" + publicName, ABSENT);
		}

		public JType getType() {
			return type;
		}

		public boolean isVirtual() {
			return constantField != null;
		}

		public boolean isConstant() {
			return constantField != null;
		}

        public boolean isRequiredCollectionAttribute() {
            return field != null
                && fieldOutline.getPropertyInfo().isCollection()
                && fieldOutline.getPropertyInfo() instanceof CAttributePropertyInfo
                && ((CAttributePropertyInfo) fieldOutline.getPropertyInfo()).isRequired();
        }

		public FieldOutline owner() {
			return fieldOutline;
		}

		public CPropertyInfo getPropertyInfo() {
			return fieldOutline.getPropertyInfo();
		}

		public boolean isAlwaysSet() {
			if (constantField != null) {
				return true;
			} else {
				return JExpr.TRUE == fieldAccessor.hasSetValue();
			}
		}

		public JExpression hasSetValue() {
			if (constantField != null) {
				return JExpr.TRUE;
			} else if (isRequiredCollectionAttribute()) {
                return field.ne(JExpr._null());
            } else if (isSetter != null) {
				return targetObject.invoke(isSetter);
			} else {
				return fieldAccessor.hasSetValue();
			}
		}

		public void unsetValues(JBlock body) {
			if (constantField != null) {

			} else if (unSetter != null) {
				body.invoke(targetObject, unSetter);
			} else {

				fieldAccessor.unsetValues(body);
			}
		}

		public void fromRawValue(JBlock block, String uniqueName,
				JExpression $var) {
			if (constantField != null) {

			} else if (setter != null) {
				block.invoke(targetObject, setter).arg($var);
			} else {
				unsetValues(block);
				if (fieldOutline.getPropertyInfo().isCollection()) {
					fieldAccessor.fromRawValue(block
							._if($var.ne(JExpr._null()))._then(), uniqueName,
							$var);
				} else {
					fieldAccessor.fromRawValue(block, uniqueName, $var);
				}
			}
		}

		public void toRawValue(JBlock block, JVar $var) {
			if (constantField != null) {
				block.assign($var, theClass.staticRef(this.constantField));
			} else if (type.isPrimitive()
					|| fieldOutline.getPropertyInfo().isCollection()) {
				final JExpression defaultExpression;
				if (type.isPrimitive()) {
					final CDefaultValue defaultValue = fieldOutline
							.getPropertyInfo().defaultValue;
					if (defaultValue != null) {
						defaultExpression = defaultValue.compute(fieldOutline
								.parent().parent());
					} else if (type.fullName().equals(
							type.owner().BOOLEAN.fullName())) {
						defaultExpression = JExpr.FALSE;
					} else if (type.fullName().equals(
							type.owner().BYTE.fullName())) {
						final byte value = 0;
						defaultExpression = JExpr.lit(value);
					} else if (type.fullName().equals(
							type.owner().CHAR.fullName())) {
						final char value = 0;
						defaultExpression = JExpr.lit(value);
					} else if (type.fullName().equals(
							type.owner().DOUBLE.fullName())) {
						final double value = 0;
						defaultExpression = JExpr.lit(value);
					} else if (type.fullName().equals(
							type.owner().FLOAT.fullName())) {
						final float value = 0;
						defaultExpression = JExpr.lit(value);
					} else if (type.fullName().equals(
							type.owner().INT.fullName())) {
						final int value = 0;
						defaultExpression = JExpr.lit(value);
					} else if (type.fullName().equals(
							type.owner().LONG.fullName())) {
						final long value = 0;
						defaultExpression = JExpr.lit(value);
					} else if (type.fullName().equals(
							type.owner().SHORT.fullName())) {
						final short value = 0;
						defaultExpression = JExpr.lit(value);
					} else {
						throw new UnsupportedOperationException();
					}

				} else if (fieldOutline.getPropertyInfo().isCollection()) {
					defaultExpression = JExpr._null();
				} else {
					throw new UnsupportedOperationException();
				}
				if (getter != null) {
					if (isAlwaysSet()) {
						block.assign($var, targetObject.invoke(getter));
					} else {
						block.assign($var, JOp.cond(hasSetValue(),
								targetObject.invoke(getter), defaultExpression));
					}
				} else {
					final JConditional _if = block._if(hasSetValue());
					fieldAccessor.toRawValue(_if._then(), $var);
					_if._else().assign($var, defaultExpression);
				}
			} else {
				if (getter != null) {
					block.assign($var, targetObject.invoke(getter));
				} else {
					fieldAccessor.toRawValue(block, $var);
				}
			}
		}
	}
}
