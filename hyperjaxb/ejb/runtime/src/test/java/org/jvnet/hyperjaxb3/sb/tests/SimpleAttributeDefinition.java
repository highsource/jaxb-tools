package org.jvnet.hyperjaxb3.sb.tests;

import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlValue;
import javax.xml.namespace.QName;

import org.glassfish.jaxb.runtime.v2.runtime.JAXBContextImpl;
import org.glassfish.jaxb.runtime.v2.runtime.JaxBeanInfo;
import org.glassfish.jaxb.runtime.v2.runtime.unmarshaller.UnmarshallerImpl;

@XmlRootElement(name = "SimpleAttribute")
public class SimpleAttributeDefinition<T> {

	private String name;

	@XmlAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String content;

	private QName type;

	@XmlAttribute(name = "type", namespace = "http://www.w3.org/2001/XMLSchema-instance")
	public QName getType() {
		return type;
	}

	public void setType(QName type) {
		this.type = type;
	}

	@XmlValue
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	private T value;

	@XmlTransient
	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
		this.content = value.toString();
	}

	private void afterUnmarshal(Unmarshaller unmarshaller, Object parent)
			throws Exception {
		final String content = getContent();
		final QName type = getType();
		if (type != null) {
			final UnmarshallerImpl realUnmarshaller = (UnmarshallerImpl) unmarshaller;
			final JAXBContextImpl context = realUnmarshaller.coordinator
					.getJAXBContext();
			final JaxBeanInfo globalType = context.getGlobalType(type);
			if (globalType != null) {
				final Object object = globalType.getTransducer().parse(content);
				setValue((T) object);
			}
		}
	}

	// private void beforeMarshal(Marshaller marshaller, Object parent)
	// throws Exception {
	//
	// // final String content = getContent();
	// final QName type = getType();
	// final T value;
	// // Convert content to T considering type
	//
	// UnmarshallerImpl realUnmarshaller = (UnmarshallerImpl) unmarshaller;
	//
	// JAXBContextImpl context = realUnmarshaller.coordinator.getJAXBContext();
	// // final String nearestTypeName = context.getNearestTypeName(type);
	//
	// // RuntimeBuiltinLeafInfoImpl.
	//
	// JaxBeanInfo globalType = context.getGlobalType(type);
	//
	// final Object object = globalType.getTransducer().parse(content);
	// value = (T) object;
	// setValue(value);
	//
	// }
	//

}
