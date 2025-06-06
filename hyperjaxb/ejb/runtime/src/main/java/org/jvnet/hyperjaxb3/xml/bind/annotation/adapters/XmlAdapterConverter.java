package org.jvnet.hyperjaxb3.xml.bind.annotation.adapters;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import org.jvnet.hyperjaxb3.item.Converter;

import java.util.Objects;

public class XmlAdapterConverter<I, O> implements Converter<I, O> {

	private final XmlAdapter<O, I> adapter;

	public XmlAdapterConverter(XmlAdapter<O, I> adapter) {
		Objects.requireNonNull(adapter, "The adapter must not be null.");
		this.adapter = adapter;
	}

	public I direct(O outer) {
		if (outer == null) {
			return null;
		} else {
			try {
				return adapter.unmarshal(outer);
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
	}

	public O inverse(I inner) {
		if (inner == null) {
			return null;
		} else {
			try {
				return adapter.marshal(inner);
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
	}

}
