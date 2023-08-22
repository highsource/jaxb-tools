package org.jvnet.hyperjaxb3.xml.bind;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.ElementAsString;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XmlAdapterUtils;
import org.w3c.dom.Element;

public class JAXBContextUtils {

	private JAXBContextUtils() {
	}

	public static boolean isElement(String contextPath, Object object) {
		try {
			return object != null
					&& getJAXBContext(contextPath).createJAXBIntrospector()
							.isElement(object);
		} catch (JAXBException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static boolean isMarshallable(String contextPath, Object object) {
		try {
			return object != null
					&& (object instanceof Element || getJAXBContext(contextPath)
							.createJAXBIntrospector().isElement(object));
		} catch (JAXBException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static boolean isMarshallableElement(Object object) {
		return object != null && (object instanceof Element);
	}

	public static String marshalElement(Object object) {
		return object != null && (object instanceof Element) ? XmlAdapterUtils
				.unmarshall(ElementAsString.class, (Element) object) : null;
	}

	public static Object unmarshalElement(String object) {
		return object == null ? null : XmlAdapterUtils.marshall(
				ElementAsString.class, object);
	}

	public static boolean isMarshallableObject(String contextPath, Object object) {
		try {
			return object != null
					&& (getJAXBContext(contextPath).createJAXBIntrospector()
							.isElement(object));
		} catch (JAXBException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static String marshalObject(String contextPath, Object object) {
		if (object != null) {
			try {
				final Marshaller marshaller = getJAXBContext(contextPath)
						.createMarshaller();

				final StringWriter sw = new StringWriter();
				marshaller.marshal(object, sw);
				return sw.toString();
			} catch (JAXBException ex) {
				throw new RuntimeException(ex);
			}
		} else {
			return null;
		}
	}

	public static Object unmarshalObject(String contextPath, String object) {
		if (object == null) {
			return null;
		} else {
			final Element element = XmlAdapterUtils.marshall(
					ElementAsString.class, object);
			try {
				final Unmarshaller unmarshaller = getJAXBContext(contextPath)
						.createUnmarshaller();
				return unmarshaller.unmarshal(element);
			} catch (JAXBException ex) {
				return element;
			}
		}
	}

	public static String marshal(String contextPath, Object object) {
		try {
			return object == null ? null : marshal(getJAXBContext(contextPath),
					object);
		} catch (JAXBException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static String marshalJAXBElement(String contextPath,
			JAXBElement<Object> object) {

		if (object == null) {
			return null;
		} else {
			return marshal(contextPath, object.getValue());
		}
	}

	public static String marshal(JAXBContext context, Object object) {

		if (object == null) {
			return null;
		} else if (object instanceof Element) {
			return XmlAdapterUtils.unmarshall(ElementAsString.class,
					(Element) object);
		} else {
			try {
				Marshaller marshaller = context.createMarshaller();

				final StringWriter sw = new StringWriter();
				marshaller.marshal(object, sw);
				return sw.toString();
			} catch (JAXBException ex) {
				throw new RuntimeException(ex);
			}
		}

	}

	public static Object unmarshal(String contextPath, String string) {
		try {
			return string == null ? null : unmarshal(
					getJAXBContext(contextPath), string);
		} catch (JAXBException ex) {
			throw new RuntimeException(ex);
		}
	}

	private static Map<String, JAXBContext> contextCache = new HashMap<String, JAXBContext>();

	public static JAXBContext getJAXBContext(String contextPath)
			throws JAXBException {
		if (contextCache.containsKey(contextPath)) {
			return contextCache.get(contextPath);
		} else {
			final JAXBContext context = JAXBContext.newInstance(contextPath);
			contextCache.put(contextPath, context);
			return context;
		}
	}

	public static JAXBElement<Object> unmarshalJAXBElement(String contextPath,
			QName name, Class<?> scope, String object) {
		if (object == null) {
			return null;
		} else {
			return new JAXBElement<Object>(name, Object.class, scope,
					unmarshal(contextPath, object));
		}
	}

	public static Object unmarshal(JAXBContext context, String object) {

		if (object == null) {
			return null;
		} else {
			final Element element = XmlAdapterUtils.marshall(
					ElementAsString.class, object);

			try {
				Unmarshaller unmarshaller = context.createUnmarshaller();
				final Object result = unmarshaller.unmarshal(element);
				if (result instanceof JAXBElement
						&& Object.class.equals(((JAXBElement<?>) result)
								.getDeclaredType())) {
					return element;
				} else {
					return result;
				}
			} catch (JAXBException ex) {
				return element;
			}
		}
	}

}
