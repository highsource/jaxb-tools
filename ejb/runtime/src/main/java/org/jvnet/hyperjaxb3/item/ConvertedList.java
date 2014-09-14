package org.jvnet.hyperjaxb3.item;

import java.util.AbstractList;
import java.util.List;

public class ConvertedList<O, I> extends AbstractList<O> {

	private final List<I> inner;
	private final Converter<I, O> converter;

	public ConvertedList(List<I> inner, Converter<I, O> converter) {
		super();
		this.inner = inner;
		this.converter = converter;
	}

	@Override
	public O get(int index) {
		return converter.inverse(inner.get(index));
	}

	public O set(int index, O element) {
		return converter.inverse(inner.set(index, converter.direct(element)));
	}

	public void add(int index, O element) {
		inner.add(index, converter.direct(element));
	}

	@Override
	public O remove(int index) {
		return converter.inverse(inner.remove(index));
	}

	@Override
	public int size() {
		return inner.size();
	}

}
