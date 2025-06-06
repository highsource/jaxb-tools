package org.jvnet.jaxb2_commons.xjc.outline.concrete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.jvnet.jaxb2_commons.xjc.outline.MClassOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MElementOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MEnumOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MModelOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MObjectFactoryOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MPackageOutline;
import org.jvnet.jaxb2_commons.xml.bind.model.MPackageInfo;

import com.sun.codemodel.JPackage;
import com.sun.tools.xjc.outline.PackageOutline;

public class CMPackageOutline implements MPackageOutline {

	private final MModelOutline parent;

	private final MPackageInfo target;

	private final JPackage code;

	private final MObjectFactoryOutline objectFactoryOutline;

	private final List<MElementOutline> elementOutlines = new ArrayList<MElementOutline>();
	private final List<MClassOutline> classOutlines = new ArrayList<MClassOutline>();
	private final List<MEnumOutline> enumOutlines = new ArrayList<MEnumOutline>();

	private final List<MElementOutline> _elementOutlines = Collections
			.unmodifiableList(elementOutlines);
	private final List<MClassOutline> _classOutlines = Collections
			.unmodifiableList(classOutlines);
	private final List<MEnumOutline> _enumOutlines = Collections
			.unmodifiableList(enumOutlines);

	public CMPackageOutline(MModelOutline parent, MPackageInfo target,
			PackageOutline packageOutline) {
		Objects.requireNonNull(parent, "Model outline parent must not be null.");
		Objects.requireNonNull(target, "Package info target must not be null.");
		this.parent = parent;
		this.target = target;
		this.code = packageOutline._package();
		this.objectFactoryOutline = new CMObjectFactoryOutline(parent, this,
				packageOutline.objectFactory());
	}

	public MModelOutline getParent() {
		return parent;
	}

	public MPackageInfo getTarget() {
		return target;
	}

	public JPackage getCode() {
		return code;
	}

	public MObjectFactoryOutline getObjectFactoryOutline() {
		return objectFactoryOutline;
	}

	public Collection<MClassOutline> getClassOutlines() {
		return _classOutlines;
	}

	public void addClassOutline(MClassOutline classOutline) {
		Objects.requireNonNull(classOutline, "Class outline must not be null.");
		if (classOutline.getPackageOutline() != this) {
			throw new IllegalArgumentException("Class outline must belong to this package outline.");
		}
		this.classOutlines.add(classOutline);
	}

	public Collection<MElementOutline> getElementOutlines() {
		return _elementOutlines;
	}

	public void addElementOutline(MElementOutline elementOutline) {
		Objects.requireNonNull(elementOutline, "Element outline must not be null.");
		if (elementOutline.getPackageOutline() != this) {
			throw new IllegalArgumentException("Element outline must belong to this package outline.");
		}
		this.elementOutlines.add(elementOutline);
	}

	public Collection<MEnumOutline> getEnumOutlines() {
		return _enumOutlines;
	}

	public void addEnumOutline(MEnumOutline enumOutline) {
		Objects.requireNonNull(enumOutline, "Enum outline must not be null.");
		if (enumOutline.getPackageOutline() != this) {
			throw new IllegalArgumentException("Enum outline must belong to this package outline.");
		}
		this.enumOutlines.add(enumOutline);
	}
}
