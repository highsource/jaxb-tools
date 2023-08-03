package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MEnumConstantInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MEnumLeafInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MEnumConstantInfoOrigin;

public class CMEnumConstantInfo<T, C extends T> implements
		MEnumConstantInfo<T, C> {

	private final MEnumConstantInfoOrigin origin;
	private final MEnumLeafInfo<T, C> enumLeafInfo;
	private final String lexicalValue;

	public CMEnumConstantInfo(MEnumConstantInfoOrigin origin,
			MEnumLeafInfo<T, C> enumLeafInfo, String lexicalValue) {
		Validate.notNull(origin);
		Validate.notNull(enumLeafInfo);
		Validate.notNull(lexicalValue);
		this.origin = origin;
		this.enumLeafInfo = enumLeafInfo;
		this.lexicalValue = lexicalValue;
	}

	public MEnumConstantInfoOrigin getOrigin() {
		return origin;
	}

	public MEnumLeafInfo<T, C> getEnumLeafInfo() {
		return enumLeafInfo;
	}

	public String getLexicalValue() {
		return lexicalValue;
	}
}
