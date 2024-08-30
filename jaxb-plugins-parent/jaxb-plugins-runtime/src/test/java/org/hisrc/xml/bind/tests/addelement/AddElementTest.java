package org.hisrc.xml.bind.tests.addelement;

import java.util.LinkedList;
import java.util.List;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddElementTest {

	@Test
	public void addsElement() throws JAXBException {

		JAXBContext context = JAXBContext.newInstance(Element.class);
		final Element element = (Element) context.createUnmarshaller()
				.unmarshal(getClass().getResource("element.xml"));
		Assertions.assertEquals("beta", element.getChildren().get(1).getValue());
	}

	@XmlRootElement(name = "element")
	public static class Element {
		private List<Element> children = new LinkedList<Element>();

		@XmlTransient
		public List<Element> getChildren() {
			return children;
		}

		@XmlElement(name = "element")
		public void setChild(Element child) {
			this.children.add(child);
		}

		private String value;

		@XmlAttribute
		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
}
