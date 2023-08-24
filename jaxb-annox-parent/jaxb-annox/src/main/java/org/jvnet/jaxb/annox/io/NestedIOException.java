package org.jvnet.jaxb.annox.io;

import java.io.IOException;

public class NestedIOException extends IOException {

	private static final long serialVersionUID = 1L;

	public NestedIOException(Throwable cause) {
		super();
		initCause(cause);
	}

	public NestedIOException(String msg, Throwable cause) {
		super(msg);
		initCause(cause);
	}

	public String getMessage() {
		return buildMessage(super.getMessage(), getCause());
	}

	public static String buildMessage(String message, Throwable cause) {
		if (cause != null) {
			StringBuffer buf = new StringBuffer();
			if (message != null) {
				buf.append(message).append("; ");
			}
			buf.append("nested exception is ").append(cause);
			return buf.toString();
		} else {
			return message;
		}
	}
}
