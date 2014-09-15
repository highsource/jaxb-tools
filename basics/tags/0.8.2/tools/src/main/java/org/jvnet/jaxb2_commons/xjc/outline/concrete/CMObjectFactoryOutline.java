package org.jvnet.jaxb2_commons.xjc.outline.concrete;

import org.apache.commons.lang3.Validate;
import org.jvnet.jaxb2_commons.xjc.outline.MModelOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MObjectFactoryOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MPackageOutline;

import com.sun.codemodel.JDefinedClass;

public class CMObjectFactoryOutline implements MObjectFactoryOutline {

	private final MModelOutline parent;

	private final MPackageOutline packageOutline;

	private final JDefinedClass code;

	public CMObjectFactoryOutline(MModelOutline parent,
			MPackageOutline packageOutline, JDefinedClass code) {
		Validate.notNull(parent);
		Validate.notNull(packageOutline);
		Validate.notNull(code);
		this.parent = parent;
		this.packageOutline = packageOutline;
		this.code = code;
	}

	public MModelOutline getParent() {
		return parent;
	}

	public MPackageOutline getPackageOutline() {
		return packageOutline;
	}

	public JDefinedClass getCode() {
		return code;
	}

}
