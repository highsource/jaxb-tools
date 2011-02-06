package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MEnumConstant;

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
