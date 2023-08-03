package org.hisrc.xml.bind.tests.addelement;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.junit.Assert;
import org.junit.Test;

public class AddElementTest {

	@Test
	public void addsElement() throws JAXBException {

		JAXBContext context = JAXBContext.newInstance(Element.class);
		final Element element = (Element) context.createUnmarshaller()
				.unmarshal(getClass().getResource("element.xml"));
		Assert.assertEquals("beta", element.getChildren().get(1).getValue());
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
