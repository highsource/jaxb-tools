package org.jvnet.jaxb2_commons.xjc.outline.concrete;

import org.jvnet.jaxb2_commons.xjc.outline.MEnumConstantOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MEnumOutline;
import org.jvnet.jaxb2_commons.xml.bind.model.MEnumConstantInfo;

import com.sun.codemodel.JEnumConstant;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;

import java.util.Objects;

public class CMEnumConstantOutline implements MEnumConstantOutline {

	private final MEnumOutline enumOutline;

	private final MEnumConstantInfo<NType, NClass> target;

	private final JEnumConstant code;

	public CMEnumConstantOutline(MEnumOutline enumOutline,
			MEnumConstantInfo<NType, NClass> target, JEnumConstant code) {
		Objects.requireNonNull(enumOutline, "Enum outline must not be null.");
        Objects.requireNonNull(target, "Enum constant target must not be null.");
        Objects.requireNonNull(code, "Enum constant code must not be null.");
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
