package org.jvnet.jaxb2_commons.plugin.annotate;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import org.jvnet.annox.model.XAnnotation;
import org.jvnet.annox.model.XAnnotationField;
import org.jvnet.annox.model.XAnnotationVisitor;
import org.jvnet.annox.model.XAnnotationField.XAnnotationArray;
import org.jvnet.annox.model.XAnnotationField.XBoolean;
import org.jvnet.annox.model.XAnnotationField.XBooleanArray;
import org.jvnet.annox.model.XAnnotationField.XByte;
import org.jvnet.annox.model.XAnnotationField.XByteArray;
import org.jvnet.annox.model.XAnnotationField.XChar;
import org.jvnet.annox.model.XAnnotationField.XCharArray;
import org.jvnet.annox.model.XAnnotationField.XClass;
import org.jvnet.annox.model.XAnnotationField.XClassArray;
import org.jvnet.annox.model.XAnnotationField.XDouble;
import org.jvnet.annox.model.XAnnotationField.XDoubleArray;
import org.jvnet.annox.model.XAnnotationField.XEnum;
import org.jvnet.annox.model.XAnnotationField.XEnumArray;
import org.jvnet.annox.model.XAnnotationField.XFloat;
import org.jvnet.annox.model.XAnnotationField.XFloatArray;
import org.jvnet.annox.model.XAnnotationField.XInt;
import org.jvnet.annox.model.XAnnotationField.XIntArray;
import org.jvnet.annox.model.XAnnotationField.XLong;
import org.jvnet.annox.model.XAnnotationField.XLongArray;
import org.jvnet.annox.model.XAnnotationField.XShort;
import org.jvnet.annox.model.XAnnotationField.XShortArray;
import org.jvnet.annox.model.XAnnotationField.XString;
import org.jvnet.annox.model.XAnnotationField.XStringArray;
import org.jvnet.jaxb2_commons.util.CodeModelUtils;

import com.sun.codemodel.JAnnotatable;
import com.sun.codemodel.JAnnotationArrayMember;
import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JAnnotationValue;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JFormatter;
import com.sun.codemodel.JType;

public class Annotator {

	public static final Annotator INSTANCE = new Annotator();

	public void annotate(JCodeModel codeModel, JAnnotatable annotatable,
			Collection<XAnnotation> xannotations) {
		for (final XAnnotation xannotation : xannotations) {
			if (xannotation != null) {
				annotate(codeModel, annotatable, xannotation);
			}
		}
	}

	public void annotate(JCodeModel codeModel, JAnnotatable annotatable,
			XAnnotation xannotation) {
		final JAnnotationUse annotationUse = annotatable.annotate(xannotation
				.getAnnotationClass());
		final XAnnotationVisitor<JAnnotationUse> visitor = createAnnotationFieldVisitor(
				codeModel, annotationUse);
		xannotation.accept(visitor);

	}

	protected XAnnotationVisitor<JAnnotationUse> createAnnotationFieldVisitor(
			JCodeModel codeModel, final JAnnotationUse annotationUse) {
		final XAnnotationVisitor<JAnnotationUse> visitor = new AnnotatingFieldVisitor(
				codeModel, annotationUse);
		return visitor;
	}

	public static class AnnotatingFieldVisitor implements
			XAnnotationVisitor<JAnnotationUse> {

		protected final JCodeModel codeModel;

		protected final JAnnotationUse use;

		public AnnotatingFieldVisitor(final JCodeModel codeModel,
				final JAnnotationUse use) {
			this.codeModel = codeModel;
			this.use = use;
		}

		public JAnnotationUse visitAnnotation(XAnnotation annotation) {
			for (final XAnnotationField<?> field : annotation.getFieldsList()) {
				field.accept(this);
			}
			return use;
		}

		public JAnnotationUse visitBooleanField(XBoolean field) {
			return use.param(field.getName(), field.getValue());
		}

		public JAnnotationUse visitByteField(XByte field) {
			return use.param(field.getName(), field.getValue());
		}

		public JAnnotationUse visitCharField(XChar field) {
			return use.param(field.getName(), field.getValue());
		}

		public JAnnotationUse visitClassField(XClass field) {
			JType type = CodeModelUtils.ref(codeModel, field.getClassName());
			return use.param(field.getName(), type);
		}

		public JAnnotationUse visitDoubleField(XDouble field) {
			// TODO: patch code model
			return use.param(field.getName(), field.getValue().intValue());
		}

		public JAnnotationUse visitEnumField(XEnum field) {
			return use.param(field.getName(), field.getValue());
		}

		public JAnnotationUse visitFloatField(XFloat field) {
			// TODO: patch code model
			return use.param(field.getName(), field.getValue().intValue());
		}

		public JAnnotationUse visitIntField(XInt field) {
			return use.param(field.getName(), field.getValue());
		}

		public JAnnotationUse visitLongField(XLong field) {
			return use.param(field.getName(), field.getValue().intValue());
		}

		public JAnnotationUse visitShortField(XShort field) {
			return use.param(field.getName(), field.getValue());
		}

		public JAnnotationUse visitStringField(XString field) {
			return use.param(field.getName(), field.getValue());
		}

		public JAnnotationUse visitBooleanArrayField(XBooleanArray field) {

			final JAnnotationArrayMember array = use
					.paramArray(field.getName());

			for (final Boolean value : field.getValue()) {
				array.param(value);
			}
			return use;
		}

		public JAnnotationUse visitByteArrayField(XByteArray field) {

			final JAnnotationArrayMember array = use
					.paramArray(field.getName());

			for (final Byte value : field.getValue()) {
				array.param(value);
			}
			return use;
		}

		public JAnnotationUse visitCharArrayField(XCharArray field) {
			final JAnnotationArrayMember array = use
					.paramArray(field.getName());

			for (final Character value : field.getValue()) {
				array.param(value);
			}
			return use;
		}

		public JAnnotationUse visitClassArrayField(XClassArray field) {
			final JAnnotationArrayMember array = use
					.paramArray(field.getName());

			for (final String className : field.getClassNames()) {
				final JType type = CodeModelUtils.ref(codeModel, className);
				array.param(type);
			}
			return use;
		}

		public JAnnotationUse visitDoubleArrayField(XDoubleArray field) {
			final JAnnotationArrayMember array = use
					.paramArray(field.getName());

			for (final Double value : field.getValue()) {
				// TODO
				array.param(value.intValue());
			}
			return use;
		}

		public JAnnotationUse visitEnumArrayField(XEnumArray field) {
			final JAnnotationArrayMember array = use
					.paramArray(field.getName());

			for (final Enum<?> value : field.getValue()) {

				final JAnnotationValue annotationValue = new JAnnotationValue() {
					public void generate(JFormatter f) {
						f.t(codeModel.ref(value.getDeclaringClass())).p('.').p(
								value.name());
					}
				};

				addParam(array, annotationValue);
			}
			return use;
		}

		@SuppressWarnings("unchecked")
		public void addParam(final JAnnotationArrayMember array,
				JAnnotationValue annotationValue) {
			try {
				final Field values = JAnnotationArrayMember.class
						.getDeclaredField("values");
				values.setAccessible(true);
				((List<JAnnotationValue>) values.get(array))
						.add(annotationValue);
			} catch (Exception ex) {
				throw new AssertionError(ex);
			}
		}

		public JAnnotationUse visitFloatArrayField(XFloatArray field) {
			final JAnnotationArrayMember array = use
					.paramArray(field.getName());
			for (final Float value : field.getValue()) {
				// TODO
				array.param(value.intValue());
			}
			return use;
		}

		public JAnnotationUse visitIntArrayField(XIntArray field) {
			final JAnnotationArrayMember array = use
					.paramArray(field.getName());
			for (final Integer value : field.getValue()) {
				array.param(value);
			}
			return use;
		}

		public JAnnotationUse visitShortArrayField(XShortArray field) {
			final JAnnotationArrayMember array = use
					.paramArray(field.getName());
			for (final Short value : field.getValue()) {
				array.param(value);
			}
			return use;
		}

		public JAnnotationUse visitStringArrayField(XStringArray field) {
			final JAnnotationArrayMember array = use
					.paramArray(field.getName());
			for (final String value : field.getValue()) {
				array.param(value);
			}
			return use;
		}

		public JAnnotationUse visitAnnotationArrayField(XAnnotationArray field) {

			final JAnnotationArrayMember array = use
					.paramArray(field.getName());

			final XAnnotation[] annotations = field.getValue();
			for (final XAnnotation annotation : annotations) {

				final JAnnotationUse annotationUse = array.annotate(annotation
						.getAnnotationClass());

				final AnnotatingFieldVisitor visitor = new AnnotatingFieldVisitor(
						codeModel, annotationUse);

				annotation.accept(visitor);

			}
			return use;
		}

		public JAnnotationUse visitLongArrayField(XLongArray field) {
			final JAnnotationArrayMember array = use
					.paramArray(field.getName());
			for (final Long value : field.getValue()) {
				array.param(value);
			}
			return use;
		}

		public JAnnotationUse visitAnnotationField(
				org.jvnet.annox.model.XAnnotationField.XAnnotation field) {
			final JAnnotationUse annotationUse = use.annotationParam(field
					.getName(), field.getAnnotationClass());

			final AnnotatingFieldVisitor visitor = new AnnotatingFieldVisitor(
					codeModel, annotationUse);
			return field.getValue().accept(visitor);
		}

	}

}
