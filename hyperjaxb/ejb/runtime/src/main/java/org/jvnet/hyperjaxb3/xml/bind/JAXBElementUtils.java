package org.jvnet.hyperjaxb3.xml.bind;

import jakarta.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

public class JAXBElementUtils {

	private JAXBElementUtils() {

	}

	public static <T> boolean shouldBeWrapped(JAXBElement<? extends T> element,
			String name, T value) {

		if (element != null) {
			return (element.getValue() != value || element.getName().toString()
					.equals(name));
		} else {
			return value != null;
		}
	}

	public static <T> JAXBElement<T> wrap(JAXBElement element, T value) {
		if (value == null) {
			return null;
		} else {
			if (element == null) {
				return new JAXBElement(new QName("temp"), value.getClass(),
						value);
			} else {
				element.setValue(value);
				return element;
			}
		}
	}

	public static <T> JAXBElement<T> wrap(JAXBElement element,
			String name, Class<T> declaredType) {
		QName qName = QName.valueOf(name);
		if (name == null) {
			return null;
		} else {
			if (element == null) {
				return new JAXBElement(qName, declaredType, null);
			} else if (element.getName().equals(qName)) {
				return element;
			} else {
				return new JAXBElement(qName, element.getDeclaredType(),
						element.getValue());
			}
		}
	}

	public static <T> JAXBElement<T> wrap(JAXBElement element,
			String name, T value) {

		if (name == null || value == null) {
			return null;
		} else {
			if (element != null) {
				if (element.getName().equals(QName.valueOf(name))
						&& element.getDeclaredType() == value.getClass()) {
					element.setValue(value);
					return element;
				} else {
					return new JAXBElement(QName.valueOf(name), value
							.getClass(), value);
				}
			} else {
				return new JAXBElement(QName.valueOf(name), value.getClass(),
						value);
			}
		}
	}

	public static <T> String getName(JAXBElement<? extends T> item) {
		if (item == null) {
			return null;
		} else {
			return item.getName().toString();
		}
	}

	public static <T> T getValue(JAXBElement<? extends T> item) {
		if (item == null) {
			return null;
		} else {
			return item.getValue();
		}
	}
}
