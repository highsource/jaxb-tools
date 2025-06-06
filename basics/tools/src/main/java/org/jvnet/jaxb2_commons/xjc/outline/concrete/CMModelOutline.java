package org.jvnet.jaxb2_commons.xjc.outline.concrete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.jvnet.jaxb2_commons.xjc.outline.MClassOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MElementOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MEnumOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MModelOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MPackageOutline;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MEnumLeafInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MModelInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPackageInfo;

import com.sun.codemodel.JCodeModel;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;

public class CMModelOutline implements MModelOutline {

	private final MModelInfo<NType, NClass> target;
	private final JCodeModel code;

	private final List<MPackageOutline> packageOutlines = new ArrayList<MPackageOutline>();
	private final List<MPackageOutline> _packageOutlines = Collections
			.unmodifiableList(packageOutlines);
	private final Map<MPackageInfo, MPackageOutline> packageOutlinesMap = new IdentityHashMap<MPackageInfo, MPackageOutline>();
	private final List<MElementOutline> elementOutlines = new ArrayList<MElementOutline>();
	private final List<MElementOutline> _elementOutlines = Collections
			.unmodifiableList(elementOutlines);
	private final Map<MElementInfo<NType, NClass>, MElementOutline> elementOutlinesMap = new IdentityHashMap<MElementInfo<NType, NClass>, MElementOutline>();
	private final List<MClassOutline> classOutlines = new ArrayList<MClassOutline>();
	private final List<MClassOutline> _classOutlines = Collections
			.unmodifiableList(classOutlines);
	private final Map<MClassInfo<NType, NClass>, MClassOutline> classOutlinesMap = new IdentityHashMap<MClassInfo<NType, NClass>, MClassOutline>();
	private final List<MEnumOutline> enumOutlines = new ArrayList<MEnumOutline>();
	private final List<MEnumOutline> _enumOutlines = Collections
			.unmodifiableList(enumOutlines);
	private final Map<MEnumLeafInfo<NType, NClass>, MEnumOutline> enumOutlinesMap = new IdentityHashMap<MEnumLeafInfo<NType, NClass>, MEnumOutline>();

	public CMModelOutline(MModelInfo<NType, NClass> target, JCodeModel code) {
		Objects.requireNonNull(target, "Model info target must not be null.");
		Objects.requireNonNull(code, "Code model must not be null.");
		this.target = target;
		this.code = code;
	}

	public MModelInfo<NType, NClass> getTarget() {
		return target;
	}

	public JCodeModel getCode() {
		return code;
	}

	public Collection<MPackageOutline> getPackageOutlines() {
		return _packageOutlines;
	}

	public MPackageOutline getPackageOutline(MPackageInfo target) {
		return packageOutlinesMap.get(target);
	}

	public void addPackageOutline(MPackageOutline packageOutline) {
		Objects.requireNonNull(packageOutline, "Package outline must not be null.");
		this.packageOutlines.add(packageOutline);
		this.packageOutlinesMap.put(packageOutline.getTarget(), packageOutline);
	}

	public Collection<MClassOutline> getClassOutlines() {
		return _classOutlines;
	}

	public MClassOutline getClassOutline(MClassInfo<NType, NClass> target) {
		return classOutlinesMap.get(target);
	}

	public void addClassOutline(MClassOutline classOutline) {
		Objects.requireNonNull(classOutline, "Class outline must not be null.");
		this.classOutlines.add(classOutline);
		this.classOutlinesMap.put(classOutline.getTarget(), classOutline);
	}

	public Collection<MEnumOutline> getEnumOutlines() {
		return _enumOutlines;
	}

	public MEnumOutline getEnumOutline(MEnumLeafInfo<NType, NClass> target) {
		return enumOutlinesMap.get(target);
	}

	public void addEnumOutline(MEnumOutline enumOutline) {
		Objects.requireNonNull(enumOutline, "Enum outline must not be null.");
		this.enumOutlines.add(enumOutline);
		this.enumOutlinesMap.put(enumOutline.getTarget(), enumOutline);
	}

	public Collection<MElementOutline> getElementOutlines() {
		return _elementOutlines;
	}

	public MElementOutline getElementOutline(MElementInfo<NType, NClass> target) {
		return elementOutlinesMap.get(target);
	}

	public void addElementOutline(MElementOutline elementOutline) {
		Objects.requireNonNull(elementOutline, "Element outline must not be null.");
		this.elementOutlines.add(elementOutline);
		this.elementOutlinesMap.put(elementOutline.getTarget(), elementOutline);
	}

}
