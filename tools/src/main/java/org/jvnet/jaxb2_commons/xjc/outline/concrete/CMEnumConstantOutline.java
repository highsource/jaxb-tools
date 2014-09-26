package org.jvnet.jaxb2_commons.xjc.outline.concrete;

import org.apache.commons.lang3.Validate;
import org.jvnet.jaxb2_commons.xjc.outline.MEnumConstantOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MEnumOutline;
import org.jvnet.jaxb2_commons.xml.bind.model.MEnumConstantInfo;

import com.sun.codemodel.JEnumConstant;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;

public class CMEnumConstantOutline implements MEnumConstantOutline {

	private final MEnumOutline enumOutline;

	private final MEnumConstantInfo<NType, NClass> target;

	private final JEnumConstant code;

	public CMEnumConstantOutline(MEnumOutline enumOutline,
			MEnumConstantInfo<NType, NClass> target, JEnumConstant code) {
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

	public MEnumConstantInfo<NType, NClass> getTarget() {
		return target;
	}

	public JEnumConstant getCode() {
		return code;
	}

}
