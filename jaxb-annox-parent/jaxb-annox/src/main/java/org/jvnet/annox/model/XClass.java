package org.jvnet.annox.model;

import org.apache.commons.lang3.Validate;

/**
 * Defines an annotated class.
 * 
 * @author Aleksei Valikov
 */
public class XClass extends XAnnotatedElement<Class<?>> {

	/**
	 * Empty array of classes.
	 */
	public static final XClass[] EMPTY_ARRAY = new XClass[0];

	/**
	 * XClass for Void.
	 */
	public static final XClass VOID = new XClass(Void.class,
			XAnnotation.EMPTY_ARRAY, XField.EMPTY_ARRAY,
			XConstructor.EMPTY_ARRAY, XMethod.EMPTY_ARRAY);

	/**
	 * Target class.
	 */
	private final Class<?> targetClass;

	/**
	 * Annotated class members (constructors, fields, methods).
	 */
	private final XMember<?>[] members;

	/**
	 * Annotated class constructors.
	 */
	private final XConstructor[] constructors;

	/**
	 * Annotated class fields.
	 */
	private final XField[] fields;

	/**
	 * Annotated class methods.
	 */
	private final XMethod[] methods;

	/**
	 * Constructs an annotated class.
	 * 
	 * @param targetClass
	 *            target class.
	 * @param xannotations
	 *            class annotations.
	 * @param xfields
	 *            annotated fields.
	 * @param xconstructors
	 *            annotated constructors.
	 * @param xmethods
	 *            annotated methods.
	 */
	public XClass(final Class<?> targetClass,
			final XAnnotation<?>[] xannotations, final XField[] xfields,
			final XConstructor[] xconstructors, final XMethod[] xmethods) {
		super(targetClass, xannotations);
		Validate.noNullElements(xconstructors,
				"Constructors must not contain null elements.");
		Validate.noNullElements(xfields,
				"Fields must not contain null elements.");
		Validate.noNullElements(xmethods,
				"Methods must not contain null elements.");
		this.targetClass = targetClass;
		this.constructors = xconstructors;
		this.fields = xfields;
		this.methods = xmethods;

		final XMember<?>[] members = new XMember<?>[xconstructors.length
				+ xfields.length + xmethods.length];

		System.arraycopy(xconstructors, 0, members, 0, xconstructors.length);
		System.arraycopy(xfields, 0, members, xconstructors.length,
				xfields.length);
		System.arraycopy(xmethods, 0, members, xconstructors.length
				+ xfields.length, xmethods.length);

		checkMembers(members);
		this.members = members;
	}

	/**
	 * Returns the target class.
	 * 
	 * @return Target class.
	 */
	public Class<?> getTargetClass() {
		return getAnnotatedElement();
	}

	/**
	 * Returns annotated members of the class.
	 * 
	 * @return Annotated members of the class.
	 */
	public XMember<?>[] getMembers() {
		return members;
	}

	/**
	 * Returns annotated constructors of the class.
	 * 
	 * @return Annotated constructors of the class.
	 */
	public XConstructor[] getConstructors() {
		return constructors;
	}

	/**
	 * Returns annotated fields of the class.
	 * 
	 * @return Annotated fields of the class.
	 */
	public XField[] getFields() {
		return fields;
	}

	/**
	 * Returns annotated methods of the class.
	 * 
	 * @return Annotated methods of the class.
	 */
	public XMethod[] getMethods() {
		return methods;
	}

	/**
	 * Checks if all the passed members belong to the given target class.
	 */
	private void checkMembers(XMember<?>[] members) {
		for (int index = 0; index < members.length; index++) {
			final XMember<?> member = members[index];
			if (!member.getMember().getDeclaringClass()
					.isAssignableFrom(getTargetClass())) {
				throw new IllegalArgumentException("Member [" + member
						+ "] does not belong to the target class ["
						+ targetClass + "].");
			}
		}
	}
}
