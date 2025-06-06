package org.jvnet.jaxb.xjc.outline.concrete;

import java.util.Objects;
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
		Objects.requireNonNull(parent, "Model outline parent must not be null.");
		Objects.requireNonNull(packageOutline, "Package outline must not be null.");
		Objects.requireNonNull(target, "Element info target must not be null.");
		Objects.requireNonNull(code, "Code must not be null.");
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
