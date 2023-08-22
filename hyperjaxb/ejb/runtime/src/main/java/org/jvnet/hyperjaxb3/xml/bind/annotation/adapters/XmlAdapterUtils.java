package org.jvnet.hyperjaxb3.xml.bind.annotation.adapters;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.namespace.QName;

import org.jvnet.hyperjaxb3.item.Converter;

public class XmlAdapterUtils {

	public static <I, O> Converter<I, O> getConverter(
			Class<? extends XmlAdapter<O, I>> xmlAdapterClass) {
		return asConverter(getXmlAdapter(xmlAdapterClass));
	}

	public static <I, O> Converter<I, O> asConverter(XmlAdapter<O, I> adapter) {
		return new XmlAdapterConverter<I, O>(adapter);
	}

	public static <ValueType, BoundType> ValueType marshall(
			Class<? extends XmlAdapter<ValueType, BoundType>> xmlAdapterClass,
			BoundType v) {
		try {
			final XmlAdapter<ValueType, BoundType> xmlAdapter = getXmlAdapter(xmlAdapterClass);
			return xmlAdapter.marshal(v);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static <ValueType, BoundType> BoundType unmarshall(
			Class<? extends XmlAdapter<ValueType, BoundType>> xmlAdapterClass,
			ValueType v) {
		try {
			final XmlAdapter<ValueType, BoundType> xmlAdapter = getXmlAdapter(xmlAdapterClass);
			return xmlAdapter.unmarshal(v);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static <ValueType, BoundType> XmlAdapter<ValueType, BoundType> getXmlAdapter(
			Class<? extends XmlAdapter<ValueType, BoundType>> xmlAdapterClass) {
		try {
			final XmlAdapter<ValueType, BoundType> xmlAdapter = xmlAdapterClass
					.newInstance();
			return xmlAdapter;
		} catch (IllegalAccessException iaex) {
			throw new RuntimeException(iaex);
		} catch (InstantiationException iex) {
			throw new RuntimeException(iex);
		}
	}

	public static <ValueType, BoundType> ValueType unmarshallJAXBElement(
			Class<? extends XmlAdapter<BoundType, ValueType>> xmlAdapterClass,
			JAXBElement<? extends BoundType> v) {
		try {
			if (v == null) {
				return null;
			} else {
				final XmlAdapter<BoundType, ValueType> xmlAdapter = getXmlAdapter(xmlAdapterClass);
				return xmlAdapter.unmarshal(v.getValue());
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static <BoundType> BoundType unmarshallJAXBElement(
			JAXBElement<? extends BoundType> v) {
		if (v == null) {
			return null;
		} else {
			return v.getValue();
		}
	}

	public static <BoundType> boolean isJAXBElement(
			Class<BoundType> declaredType, QName name, Class<?> scope, Object value) {
		if (value == null) {
			return false;
		} else if (value instanceof JAXBElement) {
			final JAXBElement<?> element = (JAXBElement<?>) value;

			return element.getName().equals(name)
					&& declaredType.isAssignableFrom(element.getDeclaredType());
		} else {
			return false;
		}
	}

	public static <ValueType, BoundType> JAXBElement<BoundType> marshallJAXBElement(
			Class<? extends XmlAdapter<BoundType, ValueType>> xmlAdapterClass,
			Class<BoundType> declaredType, QName name, Class<?> scope, ValueType v) {
		try {
			if (v == null) {
				return null;
			} else {
				final XmlAdapter<BoundType, ValueType> xmlAdapter = getXmlAdapter(xmlAdapterClass);
				return new JAXBElement<BoundType>(name, declaredType, scope,
						xmlAdapter.marshal(v));
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static <BoundType> JAXBElement<BoundType> marshallJAXBElement(
			Class<BoundType> declaredType, QName name, Class<?> scope, BoundType v) {
		if (v == null) {
			return null;
		} else {
			return new JAXBElement<BoundType>(name, declaredType, scope, v);
		}
	}
}
