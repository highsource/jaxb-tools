package org.jvnet.jaxb2_commons.xjc.model.c;

import org.apache.commons.lang.Validate;
import org.jvnet.jaxb2_commons.xjc.model.MEnumConstant;

public class CMEnumConstant implements MEnumConstant {

	private final String lexicalValue;

	public CMEnumConstant(String lexicalValue) {
		Validate.notNull(lexicalValue);
		this.lexicalValue = lexicalValue;
	}

	public String getLexicalValue() {
		return lexicalValue;
	}
}
