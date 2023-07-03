package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import org.jvnet.jaxb2_commons.xml.bind.model.MCustomization;
import org.w3c.dom.Element;

public class CMCustomization implements MCustomization {

	private final Element element;

	public CMCustomization(Element element) {
		this.element = element;
	}

	public Element getElement() {
		return element;
	}
}
