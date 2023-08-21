package org.jvnet.jaxb.annox.parser.exception;

public class AnnotationStringParseException extends Exception {

	private static final long serialVersionUID = 1L;

	private String text;

	public AnnotationStringParseException(String message, String text,
			Throwable cause) {
		super(message, cause);
		this.text = text;
	}

	public AnnotationStringParseException(String message, String text) {
		super(message);
		this.text = text;
	}

	public String getText() {
		return text;
	}

}
