package org.jvnet.jaxb2_commons.xjc.outline.concrete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.jvnet.jaxb2_commons.xjc.outline.MEnumConstantOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MEnumOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MModelOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MPackageOutline;
import org.jvnet.jaxb2_commons.xml.bind.model.MEnumLeafInfo;

import com.sun.codemodel.JDefinedClass;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;

public class CMEnumOutline implements MEnumOutline {

	private final MModelOutline parent;
	private final MPackageOutline packageOutline;
	private final MEnumLeafInfo<NType, NClass> target;
	private final JDefinedClass code;

	private final List<MEnumConstantOutline> enumConstantOutlines = new ArrayList<MEnumConstantOutline>();
	private final List<MEnumConstantOutline> _enumConstantOutlines = Collections
			.unmodifiableList(enumConstantOutlines);

	public CMEnumOutline(MModelOutline parent, MPackageOutline packageOutline,
			MEnumLeafInfo<NType, NClass> target, JDefinedClass code) {
		Objects.requireNonNull(parent, "Model outline parent must not be null.");
		Objects.requireNonNull(packageOutline, "Package outline must not be null.");
		Objects.requireNonNull(target, "Enum leaf info target must not be null.");
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

	public MEnumLeafInfo<NType, NClass> getTarget() {
		return target;
	}

	public JDefinedClass getCode() {
		return code;
	}

	public List<MEnumConstantOutline> getEnumConstantOutlines() {
		return _enumConstantOutlines;
	}

	public void addEnumConstantOutline(MEnumConstantOutline enumConstantOutline) {
		Objects.requireNonNull(enumConstantOutline, "Enum constant outline must not be null.");
		if (enumConstantOutline.getEnumOutline() != this) {
			throw new IllegalArgumentException("Enum constant outline must belong to this enum outline.");
		}
		this.enumConstantOutlines.add(enumConstantOutline);
	}

}
