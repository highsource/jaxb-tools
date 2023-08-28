package org.jvnet.jaxb.annox.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.jvnet.jaxb.annox.model.annotation.field.XAnnotationField;
import org.jvnet.jaxb.annox.parser.XGenericFieldParser;

/**
 * Defines an xannotation.
 *
 * @author Aleksei Valikov
 *
 */
public class XAnnotation<A extends Annotation> {

	/**
	 * Empty array of annotations.
	 */
	public static XAnnotation<?>[] EMPTY_ARRAY = new XAnnotation[0];

	/**
	 * Class of the annotation.
	 */
	final Class<A> annotationClass;

	/**
	 * Map of the xannotation fields.
	 */
	private final Map<String, XAnnotationField<?>> fieldsMap;

	/**
	 * Set of the xannotation fields.
	 */
	private final Set<XAnnotationField<?>> fieldsSet;

	/**
	 * List of the xannotation fields.
	 */
	private final List<XAnnotationField<?>> fieldsList;

	/**
	 * Constructs an xannotation for the given annotation class.
	 *
	 * @param annotationClass
	 *            annotation class, must not be <code>null</code>
	 * @param fields
	 *            fields of the xannotation.
	 * @throws IllegalArgumentException
	 *             If annotation class is <code>null</code>, some required
	 *             annotation fields are missing or some annotation fields have
	 *             mismatching types.
	 */
	public XAnnotation(final Class<A> annotationClass,
			final XAnnotationField<?>... fields)
			throws IllegalArgumentException {
		Validate.notNull(annotationClass, "Annotation class must not be null.");
		this.annotationClass = annotationClass;
		final List<XAnnotationField<?>> fieldsList = Collections
				.unmodifiableList(toFieldsList(fields));

		final Map<String, XAnnotationField<?>> fieldsMap = Collections
				.unmodifiableMap(toFieldsMap(annotationClass.getMethods(),
						fieldsList));
		checkFieldsMap(fieldsMap);

		this.fieldsList = fieldsList;
		this.fieldsMap = fieldsMap;
		this.fieldsSet = Collections
				.unmodifiableSet(new HashSet<XAnnotationField<?>>(fieldsMap
						.values()));
	}

	private static List<XAnnotationField<?>> toFieldsList(
			final XAnnotationField<?>[] fields) {
		if (fields == null) {
			return Collections.emptyList();
		} else {
			final List<XAnnotationField<?>> fieldsList = new ArrayList<XAnnotationField<?>>(
					fields.length);

			for (XAnnotationField<?> field : fields) {
				if (field != null) {
					fieldsList.add(field);
				}
			}
			return fieldsList;
		}
	}

	@SuppressWarnings("unchecked")
	private static Map<String, XAnnotationField<?>> toFieldsMap(
			final Method[] methods, final List<XAnnotationField<?>> fieldsList) {
		final Map<String, XAnnotationField<?>> definedFieldsMap = new HashMap<String, XAnnotationField<?>>();
		if (fieldsList != null) {
			for (XAnnotationField<?> definedField : fieldsList) {
				if (definedField == null)
					throw new IllegalArgumentException(
							"Fields array must not contain null entries.");
				definedFieldsMap.put(definedField.getName(), definedField);
			}
		}

		final Map<String, XAnnotationField<?>> fieldsMap = new HashMap<String, XAnnotationField<?>>();

		for (final Method method : methods) {
			if (Annotation.class.equals(method.getDeclaringClass())) {
				// Annotation class methods are ignored
			} else {
				final String name = method.getName();
				final Class<?> fieldType = method.getReturnType();
				final XAnnotationField<?> field;

				final XAnnotationField<?> definedField = definedFieldsMap
						.get(name);
				if (definedField == null) {
					final Object defaultValue = method.getDefaultValue();
					if (defaultValue == null) {
						throw new IllegalArgumentException(
								"Field ["
										+ name
										+ "] is not defined and it has no default value.");
					} else {
						try {
							field = XGenericFieldParser.GENERIC.construct(
									name, defaultValue, fieldType);
						} catch (Exception ex) {
							throw new IllegalArgumentException("Field [" + name
									+ "] could not be parsed.", ex);
						}
					}
				} else {
					final Class<?> definedFieldType = definedField.getType();
					if (!fieldType.equals(definedFieldType)) {
						throw new IllegalArgumentException("Field [" + name
								+ "] has the wrong type [" + definedFieldType
								+ "] instead of expected [" + fieldType + "].");
					} else {
						field = definedField;
					}
				}

				fieldsMap.put(field.getName(), field);
			}
		}
		return fieldsMap;
	}

	private void checkFieldsMap(Map<String, XAnnotationField<?>> fieldsMap) {

		final Method[] methods = annotationClass.getMethods();

		for (final Method method : methods) {
			if (!Annotation.class.equals(method.getDeclaringClass())) {
				final String name = method.getName();
				final Class<?> type = method.getReturnType();
				final XAnnotationField<?> field = fieldsMap.get(name);
				if (field == null) {
					throw new IllegalArgumentException("Field [" + name
							+ "] is not defined.");
				}
				final Class<?> fieldType = field.getType();
				if (!type.equals(fieldType)) {
					throw new IllegalArgumentException("Field [" + name
							+ "] has the wrong type [" + field.getType()
							+ "] instead of expected [" + type + "].");
				}
			}
		}

	}

	/**
	 * Returns the annotation class.
	 *
	 * @return annotation class.
	 */
	public Class<? extends Annotation> getAnnotationClass() {
		return annotationClass;
	}

	public String getAnnotationClassName() {
		return annotationClass.getName();
	}

	/**
	 * Returns the list of the fields.
	 *
	 * @return list of the fields.
	 */
	public List<XAnnotationField<?>> getFieldsList() {
		return fieldsList;
	}

	/**
	 * Returns the map of the fields.
	 *
	 * @return map of the fields.
	 */
	public Map<String, XAnnotationField<?>> getFieldsMap() {
		return fieldsMap;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash = hash * 37 + annotationClass.hashCode();
		hash = hash * 37 + fieldsSet.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof XAnnotation)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		final XAnnotation<?> other = (XAnnotation<?>) obj;
		return annotationClass.equals(other.annotationClass)
				&& fieldsSet.equals(other.fieldsSet);
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();
		sb.append("@");
		sb.append(getAnnotationClass().getName());

		sb.append("(");
		for (int index = 0; index < fieldsList.size(); index++) {
			final XAnnotationField<?> field = fieldsList.get(index);
			if (index > 0) {
				sb.append(", ");
			}
			sb.append(field);
		}
		sb.append(")");
		return sb.toString();
	}

	/**
	 * Returns the instance of the annotation for this xannotation.
	 *
	 * @return Instance of the annotation for this xannotation.
	 */
	public A getResult() {
		final InvocationHandler handler = new XAnnotationInvocationHandler(this);

		@SuppressWarnings("unchecked")
		final A annotation = (A) Proxy.newProxyInstance(
				annotationClass.getClassLoader(),
				new Class[] { annotationClass }, handler);

		return annotation;
	}

}
