package org.jvnet.jaxb.annox.parser.exception;

import org.w3c.dom.Element;

public class AnnotationElementParseException extends Exception {

	private static final long serialVersionUID = 1L;

	private Element element;

	public AnnotationElementParseException(Element element, Throwable cause) {
		super("Could not parse the annotation element.", cause);
		this.element = element;
	}

	public Element getElement() {
		return element;
	}

}
