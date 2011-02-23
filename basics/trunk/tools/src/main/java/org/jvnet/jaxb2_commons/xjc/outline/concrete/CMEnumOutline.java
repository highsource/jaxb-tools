package org.jvnet.jaxb2_commons.xjc.outline.concrete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.jvnet.jaxb2_commons.xjc.outline.MEnumConstantOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MEnumOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MModelOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MPackageOutline;
import org.jvnet.jaxb2_commons.xml.bind.model.MEnumLeafInfo;

import com.sun.codemodel.JDefinedClass;

public class CMEnumOutline implements MEnumOutline {

	private final MModelOutline parent;
	private final MPackageOutline packageOutline;
	private final MEnumLeafInfo target;
	private final JDefinedClass code;

	private final List<MEnumConstantOutline> enumConstantOutlines = new ArrayList<MEnumConstantOutline>();
	private final List<MEnumConstantOutline> _enumConstantOutlines = Collections
			.unmodifiableList(enumConstantOutlines);

	public CMEnumOutline(MModelOutline parent, MPackageOutline packageOutline,
			MEnumLeafInfo target, JDefinedClass code) {
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

	public MEnumLeafInfo getTarget() {
		return target;
	}

	public JDefinedClass getCode() {
		return code;
	}

	public List<MEnumConstantOutline> getEnumConstantOutlines() {
		return _enumConstantOutlines;
	}

	public void addEnumConstantOutline(MEnumConstantOutline enumConstantOutline) {
		Validate.notNull(enumConstantOutline);
		Validate.isTrue(enumConstantOutline.getEnumOutline() == this);
		this.enumConstantOutlines.add(enumConstantOutline);
	}

}
