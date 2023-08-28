package org.jvnet.jaxb.annox.parser;

import japa.parser.ast.expr.Expression;

import java.lang.annotation.Annotation;

import org.jvnet.jaxb.annox.annotation.NoSuchAnnotationFieldException;
import org.jvnet.jaxb.annox.model.annotation.field.XAnnotationField;
import org.jvnet.jaxb.annox.parser.exception.AnnotationElementParseException;
import org.jvnet.jaxb.annox.parser.exception.AnnotationExpressionParseException;
import org.jvnet.jaxb.annox.parser.value.XBooleanAnnotationValueParser;
import org.jvnet.jaxb.annox.parser.value.XByteAnnotationValueParser;
import org.jvnet.jaxb.annox.parser.value.XCharAnnotationValueParser;
import org.jvnet.jaxb.annox.parser.value.XClassAnnotationValueParser;
import org.jvnet.jaxb.annox.parser.value.XDoubleAnnotationValueParser;
import org.jvnet.jaxb.annox.parser.value.XEnumAnnotationValueParser;
import org.jvnet.jaxb.annox.parser.value.XFloatAnnotationValueParser;
import org.jvnet.jaxb.annox.parser.value.XIntAnnotationValueParser;
import org.jvnet.jaxb.annox.parser.value.XLongAnnotationValueParser;
import org.jvnet.jaxb.annox.parser.value.XShortAnnotationValueParser;
import org.jvnet.jaxb.annox.parser.value.XStringAnnotationValueParser;
import org.w3c.dom.Element;

public class XGenericFieldParser<T, V> extends XAnnotationFieldParser<T, V> {

	public static final XAnnotationFieldParser<Boolean, Boolean> BOOLEAN = new XSingleAnnotationFieldParser<Boolean, Boolean>(
			new XBooleanAnnotationValueParser());
	public static final XAnnotationFieldParser<Byte, Byte> BYTE = new XSingleAnnotationFieldParser<Byte, Byte>(
			new XByteAnnotationValueParser());
	public static final XAnnotationFieldParser<Integer, Integer> INT = new XSingleAnnotationFieldParser<Integer, Integer>(
			new XIntAnnotationValueParser());
	public static final XAnnotationFieldParser<Long, Long> LONG = new XSingleAnnotationFieldParser<Long, Long>(
			new XLongAnnotationValueParser());
	public static final XAnnotationFieldParser<Short, Short> SHORT = new XSingleAnnotationFieldParser<Short, Short>(
			new XShortAnnotationValueParser());
	public static final XAnnotationFieldParser<Character, Character> CHAR = new XSingleAnnotationFieldParser<Character, Character>(
			new XCharAnnotationValueParser());
	public static final XAnnotationFieldParser<Class<?>, Class<?>> CLASS = new XSingleAnnotationFieldParser<Class<?>, Class<?>>(
			new XClassAnnotationValueParser());
	public static final XAnnotationFieldParser<Double, Double> DOUBLE = new XSingleAnnotationFieldParser<Double, Double>(
			new XDoubleAnnotationValueParser());
	public static final XAnnotationFieldParser<Float, Float> FLOAT = new XSingleAnnotationFieldParser<Float, Float>(
			new XFloatAnnotationValueParser());
	public static final XAnnotationFieldParser<Enum<?>, Enum<?>> ENUM = new XSingleAnnotationFieldParser<Enum<?>, Enum<?>>(
			new XEnumAnnotationValueParser());
	public static final XAnnotationFieldParser<String, String> STRING = new XSingleAnnotationFieldParser<String, String>(
			new XStringAnnotationValueParser());

	@SuppressWarnings("rawtypes")
	public static final XAnnotationFieldParser<?, ?> ANNOTATION = new XAnnotationSingleAnnotationFieldParser();
//	@SuppressWarnings("rawtypes")
//	public static final XAnnotationFieldParser<?, ?> XANNOTATION = new XXAnnotationSingleAnnotationFieldParser();

	public static final XAnnotationFieldParser<Boolean[], Boolean[]> BOOLEAN_ARRAY = new XArrayAnnotationFieldParser<Boolean, Boolean>(
			new XBooleanAnnotationValueParser());
	public static final XAnnotationFieldParser<Byte[], Byte[]> BYTE_ARRAY = new XArrayAnnotationFieldParser<Byte, Byte>(
			new XByteAnnotationValueParser());
	public static final XAnnotationFieldParser<Integer[], Integer[]> INT_ARRAY = new XArrayAnnotationFieldParser<Integer, Integer>(
			new XIntAnnotationValueParser());
	public static final XAnnotationFieldParser<Long[], Long[]> LONG_ARRAY = new XArrayAnnotationFieldParser<Long, Long>(
			new XLongAnnotationValueParser());
	public static final XAnnotationFieldParser<Short[], Short[]> SHORT_ARRAY = new XArrayAnnotationFieldParser<Short, Short>(
			new XShortAnnotationValueParser());
	public static final XAnnotationFieldParser<Character[], Character[]> CHAR_ARRAY = new XArrayAnnotationFieldParser<Character, Character>(
			new XCharAnnotationValueParser());
	public static final XAnnotationFieldParser<Class<?>[], Class<?>[]> CLASS_ARRAY = new XArrayAnnotationFieldParser<Class<?>, Class<?>>(
			new XClassAnnotationValueParser());
	public static final XAnnotationFieldParser<Double[], Double[]> DOUBLE_ARRAY = new XArrayAnnotationFieldParser<Double, Double>(
			new XDoubleAnnotationValueParser());
	public static final XAnnotationFieldParser<Float[], Float[]> FLOAT_ARRAY = new XArrayAnnotationFieldParser<Float, Float>(
			new XFloatAnnotationValueParser());
	public static final XAnnotationFieldParser<Enum<?>[], Enum<?>[]> ENUM_ARRAY = new XArrayAnnotationFieldParser<Enum<?>, Enum<?>>(
			new XEnumAnnotationValueParser());
	public static final XAnnotationFieldParser<String[], String[]> STRING_ARRAY = new XArrayAnnotationFieldParser<String, String>(
			new XStringAnnotationValueParser());

	// public static final XBooleanArrayAnnotationFieldParser BOOLEAN_ARRAY =
	// new XBooleanArrayAnnotationFieldParser();
	// public static final XByteArrayAnnotationFieldParser BYTE_ARRAY = new
	// XByteArrayAnnotationFieldParser();
	// public static final XIntArrayAnnotationFieldParser INT_ARRAY = new
	// XIntArrayAnnotationFieldParser();
	// public static final XLongArrayAnnotationFieldParser LONG_ARRAY = new
	// XLongArrayAnnotationFieldParser();
	// public static final XShortArrayAnnotationFieldParser SHORT_ARRAY = new
	// XShortArrayAnnotationFieldParser();
	// public static final XCharArrayAnnotationFieldParser CHAR_ARRAY = new
	// XCharArrayAnnotationFieldParser();
	// public static final XClassArrayAnnotationFieldParser CLASS_ARRAY = new
	// XClassArrayAnnotationFieldParser();
	// public static final XDoubleArrayAnnotationFieldParser DOUBLE_ARRAY = new
	// XDoubleArrayAnnotationFieldParser();
	// public static final XFloatArrayAnnotationFieldParser FLOAT_ARRAY = new
	// XFloatArrayAnnotationFieldParser();
	// public static final XEnumArrayAnnotationFieldParser ENUM_ARRAY = new
	// XEnumArrayAnnotationFieldParser();
	// public static final XStringArrayAnnotationFieldParser STRING_ARRAY = new
	// XStringArrayAnnotationFieldParser();
	@SuppressWarnings("rawtypes")
	public static final XAnnotationArrayAnnotationFieldParser ANNOTATION_ARRAY = new XAnnotationArrayAnnotationFieldParser();
//	@SuppressWarnings("rawtypes")
//	public static final XXAnnotationArrayAnnotationFieldParser XANNOTATION_ARRAY = new XXAnnotationArrayAnnotationFieldParser();

	@SuppressWarnings("rawtypes")
	public static final XGenericFieldParser GENERIC = new XGenericFieldParser();

	@Override
	public XAnnotationField<T> parse(Element element, String name, Class<?> type)
			throws AnnotationElementParseException {

		@SuppressWarnings("unchecked")
		final XAnnotationFieldParser<T, V> parser = (XAnnotationFieldParser<T, V>) XGenericFieldParser
				.detectType(type);
		return parser.parse(element, name, type);
	}

	@Override
	public XAnnotationField<T> parse(Annotation annotation, String name,
			Class<?> type) throws NoSuchAnnotationFieldException {
		@SuppressWarnings("unchecked")
		final XAnnotationFieldParser<T, V> parser = (XAnnotationFieldParser<T, V>) XGenericFieldParser
				.detectType(type);
		return parser.parse(annotation, name, type);
	}

	@Override
	public XAnnotationField<T> construct(String name, V value, Class<?> type) {
		@SuppressWarnings("unchecked")
		final XAnnotationFieldParser<T, V> parser = (XAnnotationFieldParser<T, V>) XGenericFieldParser
				.detectType(type);
		return parser.construct(name, value, type);
	}

	@Override
	public XAnnotationField<T> parse(Expression expression, String name,
			Class<?> type) throws AnnotationExpressionParseException {
		@SuppressWarnings("unchecked")
		final XAnnotationFieldParser<T, V> parser = (XAnnotationFieldParser<T, V>) XGenericFieldParser
				.detectType(type);
		return parser.parse(expression, name, type);
	}

	public static <T, V> XAnnotationFieldParser<T, V> generic() {
		@SuppressWarnings("unchecked")
		final XAnnotationFieldParser<T, V> parser = XGenericFieldParser.GENERIC;
		return parser;
	}

	public static XAnnotationFieldParser<?, ?> detectType(Class<?> theClass) {
		if (theClass == null)
			throw new IllegalArgumentException("Class must not be null.");

		if (theClass.isArray()) {
			final XAnnotationFieldParser<?, ?> componentType = detectType(theClass
					.getComponentType());
			if (componentType == XGenericFieldParser.BOOLEAN)
				return XGenericFieldParser.BOOLEAN_ARRAY;
			else if (componentType == XGenericFieldParser.BYTE)
				return XGenericFieldParser.BYTE_ARRAY;
			else if (componentType == XGenericFieldParser.INT)
				return XGenericFieldParser.INT_ARRAY;
			else if (componentType == XGenericFieldParser.LONG)
				return XGenericFieldParser.LONG_ARRAY;
			else if (componentType == XGenericFieldParser.SHORT)
				return XGenericFieldParser.SHORT_ARRAY;
			else if (componentType == XGenericFieldParser.CHAR)
				return XGenericFieldParser.CHAR_ARRAY;
			else if (componentType == XGenericFieldParser.CLASS)
				return XGenericFieldParser.CLASS_ARRAY;
			else if (componentType == XGenericFieldParser.DOUBLE)
				return XGenericFieldParser.DOUBLE_ARRAY;
			else if (componentType == XGenericFieldParser.FLOAT)
				return XGenericFieldParser.FLOAT_ARRAY;
			else if (componentType == XGenericFieldParser.ENUM)
				return XGenericFieldParser.ENUM_ARRAY;
			else if (componentType == XGenericFieldParser.STRING)
				return XGenericFieldParser.STRING_ARRAY;
			else if (componentType == XGenericFieldParser.ANNOTATION)
				return XGenericFieldParser.ANNOTATION_ARRAY;
//			else if (componentType == XGenericFieldParser.XANNOTATION)
//				return XGenericFieldParser.XANNOTATION_ARRAY;
			else
				throw new IllegalArgumentException(
						"Unknown annotation field type.");

		} else {
			if (Boolean.class.equals(theClass) || Boolean.TYPE.equals(theClass)) {
				return XGenericFieldParser.BOOLEAN;
			} else if (Byte.class.equals(theClass)
					|| Byte.TYPE.equals(theClass)) {
				return XGenericFieldParser.BYTE;
			} else if (Integer.class.equals(theClass)
					|| Integer.TYPE.equals(theClass)) {
				return XGenericFieldParser.INT;
			} else if (Long.class.equals(theClass)
					|| Long.TYPE.equals(theClass)) {
				return XGenericFieldParser.LONG;
			} else if (Short.class.equals(theClass)
					|| Short.TYPE.equals(theClass)) {
				return XGenericFieldParser.SHORT;
			} else if (Character.class.equals(theClass)
					|| Character.TYPE.equals(theClass)) {
				return XGenericFieldParser.CHAR;
			} else if (Double.class.equals(theClass)
					|| Double.TYPE.equals(theClass)) {
				return XGenericFieldParser.DOUBLE;
			} else if (Float.class.equals(theClass)
					|| Float.TYPE.equals(theClass)) {
				return XGenericFieldParser.FLOAT;
			} else if (Class.class.equals(theClass)) {
				return XGenericFieldParser.CLASS;
			} else if (String.class.equals(theClass)) {
				return XGenericFieldParser.STRING;
			} else if (Enum.class.isAssignableFrom(theClass)) {
				return XGenericFieldParser.ENUM;
			} else if (Annotation.class.isAssignableFrom(theClass)) {
				return XGenericFieldParser.ANNOTATION;
//			} else if (XAnnotation.class.isAssignableFrom(theClass)) {
//				return XGenericFieldParser.XANNOTATION;
			} else {
				throw new IllegalArgumentException(
						"Unknown annotation field type [" + theClass.getName()
								+ "].");
			}
		}
	}

}
