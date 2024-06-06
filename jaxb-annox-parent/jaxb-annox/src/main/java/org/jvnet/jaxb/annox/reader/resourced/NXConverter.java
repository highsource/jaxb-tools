package org.jvnet.jaxb.annox.reader.resourced;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import org.jvnet.jaxb.annox.model.XAnnotation;
import org.jvnet.jaxb.annox.model.XClass;
import org.jvnet.jaxb.annox.model.XConstructor;
import org.jvnet.jaxb.annox.model.XField;
import org.jvnet.jaxb.annox.model.XMethod;
import org.jvnet.jaxb.annox.model.XPackage;
import org.jvnet.jaxb.annox.model.XParameter;
import org.jvnet.jaxb.annox.parser.XAnnotationParser;
import org.jvnet.jaxb.annox.parser.exception.AnnotationElementParseException;
import org.jvnet.jaxb.annox.util.ClassUtils;
import org.jvnet.jaxb.annox.util.ReflectionUtils;
import org.jvnet.jaxb.annox.util.Validate;
import org.w3c.dom.Element;

public class NXConverter {

	private final XAnnotationParser xannotationParser;

	private final ClassLoader classLoader;

	public NXConverter() {
		this(XAnnotationParser.INSTANCE, Thread.currentThread()
				.getContextClassLoader());
	}

	public NXConverter(XAnnotationParser xannotationParser,
			ClassLoader classLoader) {
		this.xannotationParser = xannotationParser;
		if (classLoader != null) {
			this.classLoader = classLoader;
		} else {
			final ClassLoader contextClassLoader = Thread.currentThread()
					.getContextClassLoader();
			if (contextClassLoader != null) {
				this.classLoader = contextClassLoader;
			} else {
				this.classLoader = NXConverter.class.getClassLoader();
			}
		}
	}

	protected XAnnotationParser getXAnnotationParser() {
		return this.xannotationParser;
	}

	protected ClassLoader getClassLoader() {
		return this.classLoader;
	}

	public XPackage convertNPackage(Package thePackage, NPackage npackage)
			throws ClassNotFoundException, NoSuchFieldException,
			NoSuchMethodException, AnnotationElementParseException {
		final List<XClass> classes = new LinkedList<XClass>();
		List<Element> annotationElements = null;
		if (npackage.content != null) {
			for (Object item : npackage.content) {
				if (item instanceof Element) {
					if (annotationElements == null) {
						annotationElements = new LinkedList<Element>();
					}
					annotationElements.add((Element) item);
				} else if (item instanceof NClass) {
					final NClass nclass = (NClass) item;
					final Class<?> theClass = getClass(thePackage, nclass.name);
					classes.add(convertNClass(theClass, nclass));
				}
			}
		}
		final XAnnotation<?>[] xannotations = parseAnnotations(annotationElements);
		final XClass[] xclasses = classes.toArray(new XClass[classes.size()]);
		return new XPackage(thePackage, xannotations, xclasses);
	}

	public XClass convertNClass(Class<?> theClass, NClass nclass)
			throws ClassNotFoundException, NoSuchFieldException,
			NoSuchMethodException, AnnotationElementParseException {

		List<Element> content = null;
		final List<XField> xfields = new LinkedList<XField>();
		final List<XConstructor> xconstructors = new LinkedList<XConstructor>();
		final List<XMethod> xmethods = new LinkedList<XMethod>();
		if (nclass.content != null) {
			for (Object item : nclass.content) {
				if (item instanceof Element) {
					if (content == null) {
						content = new LinkedList<Element>();
					}
					content.add((Element) item);
				} else if (item instanceof NField) {
					xfields.add(convertNField(theClass, (NField) item));
				} else if (item instanceof NConstructor) {
					xconstructors.add(convertNConstructor(theClass,
							(NConstructor) item));
				} else if (item instanceof NMethod) {
					xmethods.add(convertNMethod(theClass, (NMethod) item));
				}
			}
		}
		final XAnnotation<?>[] annotations = parseAnnotations(content);
		final XField[] fields = xfields.toArray(new XField[xfields.size()]);
		final XConstructor[] constructors = xconstructors
				.toArray(new XConstructor[xconstructors.size()]);
		final XMethod[] methods = xmethods
				.toArray(new XMethod[xmethods.size()]);
		return new XClass(theClass, annotations, fields, constructors, methods);
	}

	public XField convertNField(Class<?> theClass, NField nfield)
			throws NoSuchFieldException, AnnotationElementParseException {
		// TODO check nfield
		final Field field = getField(theClass, nfield.name);
		final XAnnotation<?>[] annotations = parseAnnotations(nfield.content);
		return new XField(field, annotations);
	}

	public XConstructor convertNConstructor(Class<?> theClass,
			NConstructor nconstructor) throws ClassNotFoundException,
			NoSuchMethodException,

			AnnotationElementParseException {
		// TODO check nconstructor
		final Constructor<?> theConstructor = getConstructor(theClass,
				parseArguments(nconstructor.arguments));

		List<Element> elements = null;
		List<NParameter> nparameters = null;
		if (nconstructor.content != null) {
			for (Object item : nconstructor.content) {
				if (item instanceof Element) {
					if (elements == null) {
						elements = new LinkedList<Element>();
					}
					elements.add((Element) item);
				} else if (item instanceof NParameter) {
					if (nparameters == null) {
						nparameters = new LinkedList<NParameter>();
					}
					nparameters.add((NParameter) item);
				} else {
					// TODO
				}
			}
		}

		final XAnnotation<?>[] xannotations = parseAnnotations(elements);
		final XParameter[] xparameters = convertNParameters(theConstructor
				.getParameterTypes(), nparameters);
		return new XConstructor(theConstructor, xannotations, xparameters);
	}

	public XMethod convertNMethod(Class<?> theClass, NMethod nmethod)
			throws ClassNotFoundException, NoSuchMethodException,
			AnnotationElementParseException {
		// Check nmethod
		final Method method = getMethod(theClass, nmethod.name,
				parseArguments(nmethod.arguments));

		List<Element> elements = null;
		List<NParameter> nparameters = null;
		if (nmethod.content != null) {
			for (Object item : nmethod.content) {
				if (item instanceof Element) {
					if (elements == null) {
						elements = new LinkedList<Element>();
					}
					elements.add((Element) item);
				} else if (item instanceof NParameter) {
					if (nparameters == null) {
						nparameters = new LinkedList<NParameter>();
					}
					nparameters.add((NParameter) item);
				} else {
					// TODO
				}
			}
		}

		final XAnnotation<?>[] xannotations = parseAnnotations(elements);
		final XParameter[] xparameters = convertNParameters(method
				.getParameterTypes(), nparameters);
		return new XMethod(method, xannotations, xparameters);
	}

	public XParameter[] convertNParameters(Class<?>[] parameterTypes,
			List<NParameter> nparameters)
			throws AnnotationElementParseException {
		final NParameter[] nparametersArray = new NParameter[parameterTypes.length];
		if (nparameters != null) {
			for (NParameter nparameter : nparameters) {
				// TODO
				nparametersArray[nparameter.index] = nparameter;
			}
		}

		final XParameter[] xparameters = new XParameter[parameterTypes.length];

		for (int index = 0; index < parameterTypes.length; index++) {
			final Class<?> parameterType = parameterTypes[index];
			final NParameter nparameter = nparametersArray[index];
			xparameters[index] = convertNParameter(parameterType, nparameter);
		}
		return xparameters;
	}

	public XParameter convertNParameter(Class<?> parameterType,
			NParameter nparameter) throws AnnotationElementParseException {
		Validate.notNull(parameterType);
		final XAnnotation<?>[] annotations = nparameter == null ? XAnnotation.EMPTY_ARRAY
				: parseAnnotations(nparameter.content);
		return new XParameter(parameterType, annotations);
	}

	protected Class<?> getClass(Package thePackage, String name)
			throws ClassNotFoundException {
		final String className = thePackage.getName() + "." + name;
		return ClassUtils.forName(className, true, getClassLoader());
	}

	protected Field getField(Class<?> theClass, String name)
			throws NoSuchFieldException {
		return ReflectionUtils.getField(theClass, name);

	}

	protected Constructor<?> getConstructor(Class<?> theClass,
			Class<?>[] arguments) throws NoSuchMethodException {
		return ReflectionUtils.getConstructor(theClass, arguments);
	}

	protected Method getMethod(Class<?> theClass, String name,
			Class<?>[] arguments) throws NoSuchMethodException {
		return ReflectionUtils.getMethod(theClass, name, arguments);
	}

	protected XAnnotation<?>[] parseAnnotations(List<Element> elements)
			throws AnnotationElementParseException {
		if (elements == null || elements.isEmpty()) {
			return XAnnotation.EMPTY_ARRAY;
		} else {
			final Element[] annotationElements = elements
					.toArray(new Element[elements.size()]);
			return getXAnnotationParser().parse(annotationElements);

		}
	}

	protected Class<?>[] parseArguments(String arguments)
			throws ClassNotFoundException {
		return ClassUtils.forNames(arguments, true, getClassLoader());
	}

}
