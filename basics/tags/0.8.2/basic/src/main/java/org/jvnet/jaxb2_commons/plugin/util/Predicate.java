package org.jvnet.jaxb2_commons.plugin.util;

public interface Predicate<T> {

	public boolean evaluate(T object);

}