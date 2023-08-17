package org.jvnet.annox.reader.resourced;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;

import javax.xml.bind.JAXBContext;

import org.apache.commons.lang3.Validate;
import org.jvnet.annox.model.XClass;
import org.jvnet.annox.model.XConstructor;
import org.jvnet.annox.model.XField;
import org.jvnet.annox.model.XMethod;
import org.jvnet.annox.model.XPackage;
import org.jvnet.annox.parser.XAnnotationParser;
import org.jvnet.annox.parser.exception.AnnotationElementParseException;
import org.jvnet.annox.reader.XReader;
import org.jvnet.annox.reflect.AnnotatedElementException;

public class ResourcedXReader implements XReader {

	private final NParser nparser;

	private final NXConverter nxconverter;

	private final ClassLoader classLoader;

	private Map<Package, XPackage> packages = new WeakHashMap<Package, XPackage>();

	private Map<Class<?>, XClass> classes = new WeakHashMap<Class<?>, XClass>();

	private Map<Class<?>, XClass> packageClasses = new WeakHashMap<Class<?>, XClass>();

	@SuppressWarnings("unused")
	private Map<Field, XField> fields = new WeakHashMap<Field, XField>();

	@SuppressWarnings("unused")
	private Map<Constructor<?>, XConstructor> constructors = new WeakHashMap<Constructor<?>, XConstructor>();

	@SuppressWarnings("unused")
	private Map<Method, XMethod> methods = new WeakHashMap<Method, XMethod>();

	public ResourcedXReader() {
		this.nparser = new NParser();
		final ClassLoader contextClassLoader = Thread.currentThread()
				.getContextClassLoader();
		if (contextClassLoader != null) {
			this.classLoader = contextClassLoader;
		} else {
			this.classLoader = NXConverter.class.getClassLoader();
		}
		this.nxconverter = new NXConverter(XAnnotationParser.INSTANCE,
				this.classLoader);
	}

	public ResourcedXReader(final ClassLoader classLoader) {
		Validate.notNull(classLoader);
		this.nparser = new NParser();
		this.classLoader = classLoader;
		this.nxconverter = new NXConverter(new XAnnotationParser(
				this.classLoader), this.classLoader);
	}

	public ResourcedXReader(final ClassLoader classLoader,
			final JAXBContext context, final XAnnotationParser xannotationParser) {
		Validate.notNull(classLoader);
		Validate.notNull(context);
		Validate.notNull(xannotationParser);
		this.classLoader = classLoader;
		this.nparser = new NParser(context);
		this.nxconverter = new NXConverter(xannotationParser, classLoader);
	}

	public ResourcedXReader(final ClassLoader classLoader,
			final NParser nparser, NXConverter nxconverter) {
		Validate.notNull(classLoader);
		Validate.notNull(nparser);
		Validate.notNull(nxconverter);
		this.classLoader = classLoader;
		this.nparser = nparser;
		this.nxconverter = nxconverter;
	}

	protected ClassLoader getClassLoader() {
		return classLoader;
	}

	protected NParser getNParser() {
		return nparser;
	}

	protected NXConverter getNXConverter() {
		return nxconverter;
	}

	public XPackage getXPackage(Package thePackage)
			throws AnnotatedElementException {
		// Check cache
		final XPackage one = packages.get(thePackage);
		// If something is found in the cache
		if (one != null) {
			// VOID signals that package could not be found
			if (one == XPackage.VOID) {
				return null;
			} else {
				return one;
			}
		} else {
			final String resourceName = getResourceName(thePackage);
			final InputStream is = getClassLoader().getResourceAsStream(
					resourceName);
			if (is == null) {
				// If package could not be found, put a VOID marker
				packages.put(thePackage, XPackage.VOID);
				return null;
			} else {
				try {
					return loadXPackage(thePackage, is);
				} catch (IOException ex) {
					throw new AnnotatedElementException(thePackage, ex);
				} catch (ClassNotFoundException ex) {
					throw new AnnotatedElementException(thePackage, ex);
				} catch (NoSuchFieldException ex) {
					throw new AnnotatedElementException(thePackage, ex);
				} catch (NoSuchMethodException ex) {
					throw new AnnotatedElementException(thePackage, ex);
				} catch (AnnotationElementParseException ex) {
					throw new AnnotatedElementException(thePackage, ex);
				}
			}
		}
	}

	public XClass getXClass(Class<?> theClass) throws AnnotatedElementException {
		Validate.notNull(theClass);
		// Check direct class cache
		final XClass one = classes.get(theClass);
		if (one != null && one != XClass.VOID) {
			return one;
		} else {
			// One is null or VOID
			// Check package class cache
			final XClass two = packageClasses.get(theClass);
			if (two != null) {
				// VOID signals that class was not found
				if (two == XClass.VOID) {
					return null;
				} else {
					return two;
				}
			} else {
				final String resourceName = getResourceName(theClass);

				final InputStream is = getClassLoader().getResourceAsStream(
						resourceName);
				if (is == null) {
					// Put a VOID as a sign that class resource is not found
					classes.put(theClass, XClass.VOID);
					// Check package
					final Package thePackage = theClass.getPackage();
					final XPackage xpackage = getXPackage(thePackage);
					// If the class could not be found in the package, put VOID
					// marker
					if (xpackage == null) {
						packageClasses.put(theClass, XClass.VOID);
						return null;
					} else {
						for (XClass xclass : xpackage.getClasses()) {
							if (xclass.getTargetClass().equals(theClass)) {
								return xclass;
							}
						}
						// The class could not have been found in the package
						packageClasses.put(theClass, XClass.VOID);
						return null;
					}
				} else {
					try {
						return loadXClass(theClass, is);
					} catch (IOException ex) {
						throw new AnnotatedElementException(theClass, ex);
					} catch (ClassNotFoundException ex) {
						throw new AnnotatedElementException(theClass, ex);
					} catch (NoSuchFieldException ex) {
						throw new AnnotatedElementException(theClass, ex);
					} catch (NoSuchMethodException ex) {
						throw new AnnotatedElementException(theClass, ex);
					} catch (AnnotationElementParseException ex) {
						throw new AnnotatedElementException(theClass, ex);
					}
				}
			}
		}
	}

	public XField getXField(Field theField) throws AnnotatedElementException {
		Validate.notNull(theField);
		return getXField(theField.getDeclaringClass(), theField);
	}

	public XField getXField(Class<?> theClass, Field theField)
			throws AnnotatedElementException {
		Validate.notNull(theClass);
		Validate.notNull(theField);
		final XClass xclass = getXClass(theClass);
		if (xclass == null) {
			return null;
		} else {
			for (XField field : xclass.getFields()) {
				if (theField.equals(field.getField())) {
					return field;
				}
			}
			return null;
		}
	}

	public XConstructor getXConstructor(Constructor<?> theConstructor)
			throws AnnotatedElementException {
		Validate.notNull(theConstructor);
		return getXConstructor(theConstructor.getDeclaringClass(),
				theConstructor);
	}

	public XConstructor getXConstructor(Class<?> theClass,
			Constructor<?> theConstructor) throws AnnotatedElementException {
		Validate.notNull(theClass);
		Validate.notNull(theConstructor);
		final XClass xclass = getXClass(theClass);
		if (xclass == null) {
			return null;
		} else {
			for (XConstructor constructor : xclass.getConstructors()) {
				if (theConstructor.equals(constructor.getConstructor())) {
					return constructor;
				}
			}
			return null;
		}
	}

	public XMethod getXMethod(Method theMethod)
			throws AnnotatedElementException {
		Validate.notNull(theMethod);
		return getXMethod(theMethod.getDeclaringClass(), theMethod);

	}

	public XMethod getXMethod(Class<?> theClass, Method theMethod)
			throws AnnotatedElementException {
		Validate.notNull(theClass);
		Validate.notNull(theMethod);
		final XClass xclass = getXClass(theClass);
		if (xclass == null) {
			return null;
		} else {
			for (XMethod method : xclass.getMethods()) {
				if (theMethod.equals(method.getMethod())) {
					return method;
				}
			}
			return null;
		}
	}

	protected XPackage loadXPackage(Package thePackage, final InputStream is)
			throws IOException, ClassNotFoundException, NoSuchFieldException,
			NoSuchMethodException, AnnotationElementParseException {
		try {
			final XPackage xpackage = parseXPackage(thePackage, is);
			packages.put(thePackage, xpackage);
			for (XClass xclass : xpackage.getClasses()) {
				packageClasses.put(xclass.getTargetClass(), xclass);
			}
			return xpackage;
		} finally {
			try {
				is.close();
			} catch (IOException ignored) {
			}
		}
	}

	protected XClass loadXClass(Class<?> theClass, final InputStream is)
			throws IOException, ClassNotFoundException, NoSuchFieldException,
			NoSuchMethodException, AnnotationElementParseException {
		try {
			final XClass xclass = parseXClass(theClass, is);
			classes.put(theClass, xclass);
			return xclass;
		} finally {
			try {
				is.close();
			} catch (IOException ignored) {

			}
		}
	}

	protected XPackage parseXPackage(Package thePackage, final InputStream is)
			throws IOException, ClassNotFoundException, NoSuchFieldException,
			NoSuchMethodException, AnnotationElementParseException {
		final NPackage npackage = getNParser().parseNPackage(is);
		final XPackage xpackage = getNXConverter().convertNPackage(thePackage,
				npackage);
		return xpackage;
	}

	protected XClass parseXClass(Class<?> theClass, final InputStream is)
			throws IOException, ClassNotFoundException, NoSuchFieldException,
			NoSuchMethodException, AnnotationElementParseException {
		final NClass nclass = getNParser().parseNClass(is);
		final XClass xclass = getNXConverter().convertNClass(theClass, nclass);
		return xclass;
	}

	protected String getResourceName(Package thePackage) {
		final String resourceName = (thePackage == null ? "" : thePackage
				.getName().replace('.', '/') + '/') + "package-info.ann.xml";
		return resourceName;
	}

	protected String getResourceName(Class<?> theClass) {
		final String resourceName = theClass.getName().replace('.', '/')
				+ ".ann.xml";
		return resourceName;
	}

}
