package org.jvnet.jaxb.annox.parser.value;

import java.lang.reflect.Array;

import org.jvnet.jaxb.annox.model.annotation.value.AbstractBasicXAnnotationValueVisitor;
import org.jvnet.jaxb.annox.model.annotation.value.XAnnotationValue;
import org.jvnet.jaxb.annox.model.annotation.value.XArrayClassAnnotationValue;
import org.jvnet.jaxb.annox.model.annotation.value.XClassAnnotationValue;
import org.jvnet.jaxb.annox.model.annotation.value.XClassByNameAnnotationValue;
import org.jvnet.jaxb.annox.parser.exception.ValueParseException;
import org.jvnet.jaxb.annox.parser.java.visitor.ClassExpressionVisitor;
import org.jvnet.jaxb.annox.parser.java.visitor.ExpressionVisitor;
import org.jvnet.jaxb.annox.util.ClassUtils;
import org.jvnet.jaxb.annox.util.StringUtils;

public class XClassAnnotationValueParser extends
		XAnnotationValueParser<Class<?>, Class<?>> {

	@Override
	public XAnnotationValue<Class<?>> parse(String value, Class<?> type)
			throws ValueParseException {
		try {
			final Class<?> _class = ClassUtils.getClass(value);
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final XAnnotationValue<Class<?>> annotationValue = new XClassAnnotationValue(
					_class);
			return annotationValue;
		} catch (ClassNotFoundException cnfex) {

			final int lastLeftBracketPosition = value.lastIndexOf('[');
			final int lastRightBracketPosition = value.lastIndexOf(']');

			if (lastLeftBracketPosition >= 0
					&& lastRightBracketPosition > lastLeftBracketPosition) {

				final String classNamePart = value.substring(0,
						lastLeftBracketPosition);
				final String indexPart = value.substring(
						lastLeftBracketPosition + 1, lastRightBracketPosition);
				final String suffixPart = value
						.substring(lastRightBracketPosition + 1);

				if (!StringUtils.isBlank(classNamePart)
						&& StringUtils.isBlank(indexPart)
						&& StringUtils.isBlank(suffixPart)) {
					final XAnnotationValue<Class<?>> componentTypeValue = parse(
							classNamePart.trim(), type);

					return componentTypeValue
							.accept(new AbstractBasicXAnnotationValueVisitor<XAnnotationValue<Class<?>>>() {

								@Override
								public XAnnotationValue<Class<?>> visitDefault(
										XAnnotationValue<?> value) {
									throw new IllegalArgumentException();
								}

								@SuppressWarnings("rawtypes")
								@Override
								public XAnnotationValue<Class<?>> visit(
										XClassAnnotationValue<?> value) {
									final Class<?> itemClass = value.getValue();
									final Class<?> arrayClass = Array
											.newInstance(itemClass, 0)
											.getClass();
									@SuppressWarnings("unchecked")
									final XAnnotationValue<Class<?>> classAnnotationValue = new XClassAnnotationValue(
											arrayClass);
									return classAnnotationValue;
								}

								@Override
								public XAnnotationValue<Class<?>> visit(
										XClassByNameAnnotationValue<?> value) {
									@SuppressWarnings({ "unchecked", "rawtypes" })
									final XAnnotationValue<Class<?>> arrayClassAnnotationValue = new XArrayClassAnnotationValue(
											value, 1);
									return arrayClassAnnotationValue;
								}

								@Override
								public XAnnotationValue<Class<?>> visit(
										XArrayClassAnnotationValue<?, ?> value) {
									@SuppressWarnings({ "unchecked", "rawtypes" })
									final XAnnotationValue<Class<?>> arrayClassAnnotationValue = new XArrayClassAnnotationValue(
											value.getItemClassByNameAnnotationValue(),
											value.getDimension() + 1);
									return arrayClassAnnotationValue;
								}
							});
				}
			}
			@SuppressWarnings({ "rawtypes", "unchecked" })
			final XAnnotationValue<Class<?>> classByNameAnnotationValue = new XClassByNameAnnotationValue(
					value);
			return classByNameAnnotationValue;
		}
	}

	@Override
	public XAnnotationValue<Class<?>> construct(Class<?> value, Class<?> type) {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final XAnnotationValue<Class<?>> annotationValue = new XClassAnnotationValue(
				value);
		return annotationValue;
	}

	@Override
	public ExpressionVisitor<XAnnotationValue<Class<?>>> createExpressionVisitor(
			Class<?> type) {
		return new ClassExpressionVisitor(type);
	}

}
