package org.jvnet.jaxb.annox.model;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Defines an annotated member.
 * 
 * @param <T>
 *            type of the target member.
 * @author Aleksei Valikov
 */
public abstract class XMember<T extends Member & AnnotatedElement> extends
		XAnnotatedElement<T> {

	/**
	 * Returns the target member.
	 * 
	 * @return Target member.
	 */
	public T getMember() {
		return getAnnotatedElement();
	}

	/**
	 * Returns name of the target member.
	 * 
	 * @return Name of the target memeber.
	 */
	public String getName() {
		return getMember().getName();
	}

	/**
	 * Constructs an annotated member.
	 * 
	 * @param member
	 *            target member.
	 * @param xannotations
	 *            member annotations.
	 */
	public XMember(final T member, final XAnnotation<?>[] xannotations) {
		super(member, xannotations);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getMember())
				.append(getXAnnotations()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!getClass().isAssignableFrom(obj.getClass())) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		final XMember<?> rhs = (XMember<?>) obj;
		return new EqualsBuilder().append(getMember(), rhs.getMember())
				.append(getXAnnotations(), rhs.getXAnnotations()).isEquals();
	}
}
