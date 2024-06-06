package org.jvnet.jaxb.annox.model;

import org.jvnet.jaxb.annox.util.Validate;

/**
 * Defines an annotated package.
 *
 * @author Aleksei Valikov
 */
public class XPackage extends XAnnotatedElement<Package> {

	/**
	 * VOID package.
	 */
	public static final XPackage VOID = new XPackage(
			Package.getPackage("java.lang"), XAnnotation.EMPTY_ARRAY,
			XClass.EMPTY_ARRAY);

	/**
	 * Annotated classes.
	 */
	private final XClass[] classes;

	/**
	 * Constructs an annotated package.
	 *
	 * @param targetPackage
	 *            target package.
	 * @param xannotations
	 *            package annotations, may be <code>null</code>.
	 * @param xclasses
	 *            annotated classes
	 */
	public XPackage(Package targetPackage, XAnnotation<?>[] xannotations,
			XClass[] xclasses) {
		super(targetPackage, xannotations);
		Validate.noNullElements(xclasses);
		this.classes = xclasses;
	}

	/**
	 * Returns the target package.
	 *
	 * @return Target package.
	 */
	public Package getPackage() {
		return getAnnotatedElement();
	}

	/**
	 * Returns annotated classes of the package.
	 *
	 * @return Annotated classes of the package.
	 */
	public XClass[] getClasses() {
		return classes;
	}

}
