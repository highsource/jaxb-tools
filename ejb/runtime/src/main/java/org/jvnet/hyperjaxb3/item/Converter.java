package org.jvnet.hyperjaxb3.item;

public interface Converter<I, O> {

	public I direct(O outer);

	public O inverse(I inner);

}
