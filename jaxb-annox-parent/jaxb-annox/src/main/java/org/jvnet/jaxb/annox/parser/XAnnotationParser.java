package org.jvnet.jaxb.annox.parser;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.jvnet.jaxb.annox.Constants;
import org.jvnet.jaxb.annox.annotation.AnnotationClassNotFoundException;
import org.jvnet.jaxb.annox.annotation.NoSuchAnnotationFieldException;
import org.jvnet.jaxb.annox.javaparser.AnnotationExprParser;
import org.jvnet.jaxb.annox.javaparser.ast.visitor.AbstractGenericExpressionVisitor;
import org.jvnet.jaxb.annox.model.XAnnotation;
import org.jvnet.jaxb.annox.model.annotation.field.XAnnotationField;
import org.jvnet.jaxb.annox.parser.exception.AnnotationElementParseException;
import org.jvnet.jaxb.annox.parser.exception.AnnotationExpressionParseException;
import org.jvnet.jaxb.annox.parser.exception.AnnotationStringParseException;
import org.w3c.dom.Element;

public class XAnnotationParser {

	public static final XAnnotationParser INSTANCE = new XAnnotationParser();

	private final ClassLoader classLoader;
    private final Logger logger = Logger.getLogger(getClass().getName());

	/**
	 * Default constructor.
	 */
	public XAnnotationParser() {
		classLoader = this.getClass().getClassLoader();
	}

	/**
	 * Constructor with a specified class loader.
	 *
	 * @param classLoader
	 *            the class loader which should be used to get classes (like
	 *            annotations)
	 */
	public XAnnotationParser(ClassLoader classLoader) {
		super();
		this.classLoader = classLoader;
	}

	public XAnnotation<?> parse(final Annotation annotation) {
		Validate.notNull(annotation, "Annotation must not be null.");

		final Class<? extends Annotation> annotationClass = annotation
				.annotationType();

		final XAnnotationField<?>[] fields = parseFields(annotation,
				annotationClass);

		@SuppressWarnings({ "rawtypes", "unchecked" })
		final XAnnotation<?> xannotation = new XAnnotation(annotationClass,
				fields);
		return xannotation;

	}

	public XAnnotation<?>[] parse(final Annotation[] annotations) {
		Validate.noNullElements(annotations,
				"Annotations must not contain nulls.");
		final XAnnotation<?>[] xannotations = new XAnnotation[annotations.length];
		for (int index = 0; index < annotations.length; index++) {
			final Annotation annotation = annotations[index];
			xannotations[index] = parse(annotation);
		}
		return xannotations;
	}

	@SuppressWarnings("unchecked")
	public XAnnotation<?> parse(final Element annotationElement)
			throws AnnotationElementParseException {
		Validate.notNull(annotationElement,
				"Annotation element must not be null.");

		final String name = annotationElement.getLocalName();

		final String classAttribute = annotationElement.getAttributeNS(
				Constants.NAMESPACE_URI, "class");
        final String legacyClassAttribute = annotationElement.getAttributeNS(
            Constants.LEGACY_NAMESPACE_URI, "class");

		final String className;
		if (!StringUtils.isEmpty(classAttribute)) {
			className = classAttribute;
		} else if (!StringUtils.isEmpty(legacyClassAttribute)) {
            logger.log(Level.WARNING, "Please migrate your namespace in xsd / xjb from " + Constants.LEGACY_NAMESPACE_URI + " to " + Constants.NAMESPACE_URI);
            className = legacyClassAttribute;
        } else {
			final String namespaceURI = annotationElement.getNamespaceURI();

			if (namespaceURI != null
					&& namespaceURI.startsWith(Constants.NAMESPACE_URI_PREFIX)) {
				final String containerPrefix = namespaceURI
						.substring(Constants.NAMESPACE_URI_PREFIX.length());
				className = containerPrefix + "." + name.replace('.', '$');
			} else if (namespaceURI != null
                && namespaceURI.startsWith(Constants.LEGACY_NAMESPACE_URI_PREFIX)) {
                logger.log(Level.WARNING, "Please migrate your namespace in xsd / xjb from " + Constants.LEGACY_NAMESPACE_URI_PREFIX + " to " + Constants.NAMESPACE_URI_PREFIX);
                final String containerPrefix = namespaceURI
                    .substring(Constants.LEGACY_NAMESPACE_URI_PREFIX.length());
                className = containerPrefix + "." + name.replace('.', '$');
            } else {
				className = name;
			}
		}

		try {
			final Class<?> draftClass = classLoader.loadClass(className);

			if (!Annotation.class.isAssignableFrom(draftClass))
				throw new AnnotationElementParseException(annotationElement,
						new IllegalArgumentException(MessageFormat.format(
								"The class [{0}] is not an annotation class.",
								draftClass.getName())));

			final Class<? extends Annotation> annotationClass = (Class<? extends Annotation>) draftClass;

			final XAnnotationField<?>[] fields = parseFields(annotationElement,
					annotationClass);
			@SuppressWarnings("rawtypes")
			final XAnnotation<?> xannotation = new XAnnotation(annotationClass,
					fields);
			return xannotation;
		} catch (ClassNotFoundException cnfex) {
			throw new AnnotationElementParseException(annotationElement,
					new AnnotationClassNotFoundException(className, cnfex));
		} catch (AnnotationElementParseException cnfex) {
			throw new AnnotationElementParseException(annotationElement,
					new AnnotationClassNotFoundException(className, cnfex));
		}

	}

	public XAnnotation<?>[] parse(final Element[] annotationElements)
			throws AnnotationElementParseException {
		Validate.noNullElements(annotationElements,
				"Annotation elements must not contain null.");
		final XAnnotation<?>[] xannotations = new XAnnotation[annotationElements.length];
		for (int index = 0; index < annotationElements.length; index++) {
			final Element annotationElement = annotationElements[index];
			try {
				xannotations[index] = parse(annotationElement);
			} catch (AnnotationElementParseException aepex) {
				throw new AnnotationElementParseException(annotationElement,
						aepex);
			}
		}
		return xannotations;
	}

	public XAnnotation<?> parse(final String annotationString)
			throws AnnotationStringParseException,
			AnnotationExpressionParseException {
		final AnnotationExpr expression = parseAnnotationExpr(annotationString);
		return parse(expression);
	}

	public XAnnotation<?>[] parse(final String[] annotationStrings)
			throws AnnotationStringParseException,
			AnnotationExpressionParseException {
		Validate.noNullElements(annotationStrings);
		final List<AnnotationExpr> allAnnotationExprs = new ArrayList<AnnotationExpr>(
				annotationStrings.length);
		for (String annotationString : annotationStrings) {
			allAnnotationExprs.addAll(parseAnnotationExprs(annotationString));
		}
		final AnnotationExpr[] annotationExprs = new AnnotationExpr[allAnnotationExprs
				.size()];
		allAnnotationExprs.toArray(annotationExprs);
		return parse(annotationExprs);
	}

	private AnnotationExpr parseAnnotationExpr(final String annotationString)
			throws AnnotationStringParseException {
		List<AnnotationExpr> annotationExprs = parseAnnotationExprs(annotationString);

		if (annotationExprs.isEmpty()) {
			throw new AnnotationStringParseException(
					MessageFormat.format(
							"Could not parse the annotation [{0}], this expression apparently contains no annotations.",
							annotationString), annotationString);
		} else if (annotationExprs.size() > 1) {
			throw new AnnotationStringParseException(
					MessageFormat.format(
							"Could not parse the annotation [{0}], this expression apparently more than one annotation.",
							annotationString), annotationString);
		} else {
			return annotationExprs.get(0);
		}
	}

	private List<AnnotationExpr> parseAnnotationExprs(
			final String annotationString)
			throws AnnotationStringParseException {
		final AnnotationExprParser parser = new AnnotationExprParser();

		try {

			final List<AnnotationExpr> annotations = parser
					.parse(annotationString);

			return annotations;
		} catch (ParseException pex) {
			throw new AnnotationStringParseException(MessageFormat.format(
					"Could not parse the annotation [{0}].", annotationString),
					annotationString, pex);
		}
	}

	@SuppressWarnings("unchecked")
	public XAnnotation<?> parse(final AnnotationExpr annotationElement)
			throws AnnotationExpressionParseException {
		Validate.notNull(annotationElement,
				"Annotation expression must not be null.");
		final String className = annotationElement.getName().toString();

		try {

			final Class<?> draftClass = ClassUtils.getClass(classLoader,
					className);

			if (!Annotation.class.isAssignableFrom(draftClass))
				throw new AnnotationExpressionParseException(annotationElement,
						new IllegalArgumentException(MessageFormat.format(
								"The class [{0}] is not an annotation class.",
								draftClass.getName())));

			final Class<? extends Annotation> annotationClass = (Class<? extends Annotation>) draftClass;

			final XAnnotationField<?>[] fields = parseFields(annotationElement,
					annotationClass);
			@SuppressWarnings("rawtypes")
			final XAnnotation<?> xannotation = new XAnnotation(annotationClass,
					fields);
			return xannotation;
		} catch (ClassNotFoundException cnfex) {
			throw new AnnotationExpressionParseException(annotationElement,
					new AnnotationClassNotFoundException(className, cnfex));
		}
	}

	public XAnnotation<?>[] parse(final AnnotationExpr[] annotationElements)
			throws AnnotationExpressionParseException {
		Validate.noNullElements(annotationElements,
				"Annotation elements must not contain null.");
		final XAnnotation<?>[] xannotations = new XAnnotation[annotationElements.length];
		for (int index = 0; index < annotationElements.length; index++) {
			final AnnotationExpr annotationElement = annotationElements[index];
			try {
				xannotations[index] = parse(annotationElement);
			} catch (AnnotationExpressionParseException aepex) {
				throw new AnnotationExpressionParseException(annotationElement,
						aepex);
			}
		}
		return xannotations;
	}

	public XAnnotationField<?>[] parseFields(Element annotationElement,
			Class<? extends Annotation> annotationClass)
			throws AnnotationElementParseException {

		final Method[] methods = annotationClass.getMethods();

		final Map<String, Class<?>> fieldsMap = new HashMap<String, Class<?>>();

		for (final Method method : methods) {
			if (!Annotation.class.equals(method.getDeclaringClass())) {
				final String name = method.getName();
				final Class<?> type = method.getReturnType();
				fieldsMap.put(name, type);
			}
		}

		final List<XAnnotationField<?>> fields = new ArrayList<XAnnotationField<?>>(
				fieldsMap.size());

		for (Entry<String, Class<?>> entry : fieldsMap.entrySet()) {
			final String name = entry.getKey();
			final Class<?> type = entry.getValue();
			try {
				final XAnnotationField<?> field = parseField(annotationElement,
						name, type);
				if (field != null) {
					fields.add(field);
				}
			} catch (AnnotationElementParseException aepex) {
				throw new AnnotationElementParseException(annotationElement,
						aepex);

			}

		}
		return fields.toArray(new XAnnotationField<?>[fields.size()]);
	}

	public XAnnotationField<?>[] parseFields(Annotation annotation,
			Class<? extends Annotation> annotationClass) {

		final Method[] methods = annotationClass.getMethods();

		final List<XAnnotationField<?>> fields = new ArrayList<XAnnotationField<?>>(
				methods.length);

		for (final Method method : methods) {
			if (!Annotation.class.equals(method.getDeclaringClass())) {
				final String name = method.getName();
				final Class<?> type = method.getReturnType();
				try {
					fields.add(parseField(annotation, name, type));
				} catch (NoSuchAnnotationFieldException nsafex) {
					// We're sure that method exists, exception can not happen
					throw new AssertionError(nsafex);
				}
			}
		}
		return fields.toArray(new XAnnotationField<?>[fields.size()]);
	}

	public XAnnotationField<?>[] parseFields(AnnotationExpr annotationExpr,
			Class<? extends Annotation> annotationClass)
			throws AnnotationExpressionParseException {
		final List<MemberValuePair> pairs = annotationExpr
				.accept(new AbstractGenericExpressionVisitor<List<MemberValuePair>, Void>() {
					@Override
					public List<MemberValuePair> visitDefault(Node n, Void arg) {
						throw new IllegalArgumentException();
					}

					@Override
					public List<MemberValuePair> visit(NormalAnnotationExpr n,
							Void arg) {
						return n.getPairs();
					}

					@Override
					public List<MemberValuePair> visit(MarkerAnnotationExpr n,
							Void arg) {
						return Collections.emptyList();
					}

					@Override
					public List<MemberValuePair> visit(
							SingleMemberAnnotationExpr n, Void arg) {
						return Collections.singletonList(new MemberValuePair(
								"value", n.getMemberValue()));
					}
				}, null);

		final Method[] methods = annotationClass.getMethods();

		final Map<String, Class<?>> fieldsMap = new HashMap<String, Class<?>>();

		for (final Method method : methods) {
			if (!Annotation.class.equals(method.getDeclaringClass())) {
				final String name = method.getName();
				final Class<?> type = method.getReturnType();
				fieldsMap.put(name, type);
			}
		}

		final List<XAnnotationField<?>> fields = new ArrayList<XAnnotationField<?>>(
				fieldsMap.size());

		for (MemberValuePair memberValuePair : pairs) {
			final String name = memberValuePair.getName();
			final Expression value = memberValuePair.getValue();
			final Class<?> type = fieldsMap.get(name);
			if (type != null) {
				fields.add(parseField(value, name, type));
			} else {
				// TODO
			}
		}
		return fields.toArray(new XAnnotationField<?>[fields.size()]);
	}

	@SuppressWarnings("unchecked")
	public XAnnotationField<?> parseField(Expression annotationExpression,
			String name, Class<?> type)
			throws AnnotationExpressionParseException {

		final XAnnotationField<?> field = XGenericFieldParser.GENERIC.parse(
				annotationExpression, name, type);
		return field;
	}

	@SuppressWarnings("unchecked")
	public XAnnotationField<?> parseField(Element annotationElement,
			String name, Class<?> type) throws AnnotationElementParseException {

		final XAnnotationField<?> field = XGenericFieldParser.GENERIC.parse(
				annotationElement, name, type);
		return field;
	}

	@SuppressWarnings("unchecked")
	public XAnnotationField<?> parseField(Annotation annotation, String name,
			Class<?> type) throws NoSuchAnnotationFieldException {

		final XAnnotationField<?> field = XGenericFieldParser.GENERIC.parse(
				annotation, name, type);
		return field;
	}

}
