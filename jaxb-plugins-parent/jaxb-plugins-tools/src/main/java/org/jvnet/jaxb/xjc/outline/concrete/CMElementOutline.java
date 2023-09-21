package org.jvnet.jaxb.xjc.outline.concrete;

import org.apache.commons.lang3.Validate;
import org.jvnet.jaxb.xjc.outline.MElementOutline;
import org.jvnet.jaxb.xjc.outline.MModelOutline;
import org.jvnet.jaxb.xjc.outline.MPackageOutline;
import org.jvnet.jaxb.xml.bind.model.MElementInfo;

import com.sun.codemodel.JDefinedClass;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;

public class CMElementOutline implements MElementOutline {

	private final MModelOutline parent;
	private final MPackageOutline packageOutline;
	private final MElementInfo<NType, NClass> target;
	private final JDefinedClass code;

	public CMElementOutline(MModelOutline parent,
			MPackageOutline packageOutline, MElementInfo<NType, NClass> target,
			JDefinedClass code) {
		Validate.notNull(parent);
		Validate.notNull(packageOutline);
		Validate.notNull(target);
		Validate.notNull(code);
		this.parent = parent;
		this.packageOutline = packageOutline;
		this.target = target;
		this.code = code;
	}

	public MModelOutline getParent() {
		return parent;
	}

	public MPackageOutline getPackageOutline() {
		return packageOutline;
	}

	public MElementInfo<NType, NClass> getTarget() {
		return target;
	}

	public JDefinedClass getCode() {
		return code;
	}
}
