package org.jvnet.jaxb2_commons.xjc.outline.concrete;

import org.apache.commons.lang.Validate;
import org.jvnet.jaxb2_commons.xjc.outline.MEnumConstantOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MEnumOutline;
import org.jvnet.jaxb2_commons.xml.bind.model.MEnumConstantInfo;

import com.sun.codemodel.JEnumConstant;

public class CMEnumConstantOutline implements MEnumConstantOutline {

	private final MEnumOutline enumOutline;

	private final MEnumConstantInfo target;

	private final JEnumConstant code;

	public CMEnumConstantOutline(MEnumOutline enumOutline,
			MEnumConstantInfo target, JEnumConstant code) {
		Validate.notNull(enumOutline);
		Validate.notNull(target);
		Validate.notNull(code);
		this.enumOutline = enumOutline;
		this.target = target;
		this.code = code;
	}

	public MEnumOutline getEnumOutline() {
		return enumOutline;
	}
	
	public MEnumConstantInfo getTarget() {
		return target;
	}
	
	public JEnumConstant getCode() {
		return code;
	}

}
