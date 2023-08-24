package org.jvnet.jaxb.annox.model;

import java.lang.annotation.Annotation;

/**
 * Defines an item with an array of the associated annotations.
 * 
 * @author Aleksei Valikov
 */
public abstract class XAnnotated {

	/**
	 * Annotations.
	 */
	private final XAnnotation<?>[] xannotations;

	/**
	 * Constructs an annotated item.
	 * 
	 * @param xannotations
	 *            item annotations, may be <code>null</code>
	 */
	public XAnnotated(XAnnotation<?>[] xannotations) {
		this.xannotations = xannotations != null ? xannotations
				: new XAnnotation[0];
	}

	/**
	 * Returns the annotations associated with the item.
	 * 
	 * @return Item annotations.
	 */
	public XAnnotation<?>[] getXAnnotations() {
		return xannotations;
	}

	/**
	 * Returns annotations for this annotated item.
	 * 
	 * @return Array of annotations.
	 */
	public Annotation[] getAnnotations() {
		final XAnnotation<?>[] xannotations = getXAnnotations();
		final Annotation[] annotations = new Annotation[xannotations.length];
		for (int index = 0; index < xannotations.length; index++) {
			annotations[index] = xannotations[index].getResult();
		}
		return annotations;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		for (int i = 0; i < xannotations.length; i++) {
			hash = hash * 37 + xannotations.hashCode();
		}
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof XAnnotated)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		final XAnnotated other = (XAnnotated) obj;
		final XAnnotation<?>[] lhs = xannotations;
		final XAnnotation<?>[] rhs = other.xannotations;
		if (lhs == rhs) {
			return true;
		}
		if (lhs == null || rhs == null) {
			return false;
		}
		if (lhs.length != rhs.length) {
			return false;
		}
		for (int i = 0; i < lhs.length; ++i) {
			if (!(lhs[i].equals(rhs[i])))
				return false;
		}
		return true;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();
		for (int index = 0; index < xannotations.length; index++) {
			final XAnnotation<?> field = xannotations[index];
			if (index > 0) {
				sb.append("\n");
			}
			sb.append(field);
		}
		return sb.toString();
	}
}