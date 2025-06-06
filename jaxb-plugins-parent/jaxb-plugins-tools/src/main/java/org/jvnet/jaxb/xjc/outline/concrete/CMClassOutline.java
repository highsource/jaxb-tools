package org.jvnet.jaxb.xjc.outline.concrete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.jvnet.jaxb.xjc.outline.MClassOutline;
import org.jvnet.jaxb.xjc.outline.MModelOutline;
import org.jvnet.jaxb.xjc.outline.MPackageOutline;
import org.jvnet.jaxb.xjc.outline.MPropertyOutline;
import org.jvnet.jaxb.xml.bind.model.MClassInfo;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JDefinedClass;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;

public class CMClassOutline implements MClassOutline {

	private final MModelOutline parent;
	private final MPackageOutline packageOutline;
	private final MClassInfo<NType, NClass> target;
	private final MClassOutline superClassOutline;

	private final JDefinedClass referenceCode;
	private final JDefinedClass implementationCode;
	private final JClass implementationReferenceCode;

	private final List<MPropertyOutline> declaredPropertyOutlines = new ArrayList<MPropertyOutline>();
	private final List<MPropertyOutline> _delcaredPropertyOutlines = Collections
			.unmodifiableList(declaredPropertyOutlines);

	public CMClassOutline(MModelOutline parent, MPackageOutline packageOutline,
			MClassInfo<NType, NClass> target, MClassOutline superClassOutline,
			JDefinedClass referenceCode, JDefinedClass implementationCode,
			JClass implementationReferenceCode) {
		Objects.requireNonNull(parent, "Model outline parent must not be null.");
		Objects.requireNonNull(packageOutline, "Package outline must not be null.");
		Objects.requireNonNull(target, "Class info target must not be null.");
		Objects.requireNonNull(referenceCode, "Reference code must not be null.");
		Objects.requireNonNull(implementationCode, "Implementation code must not be null.");
		Objects.requireNonNull(implementationReferenceCode, "Implementation reference code must not be null.");
		this.parent = parent;
		this.packageOutline = packageOutline;
		this.target = target;
		this.superClassOutline = superClassOutline;
		this.referenceCode = referenceCode;
		this.implementationCode = implementationCode;
		this.implementationReferenceCode = implementationReferenceCode;
	}

	public MModelOutline getParent() {
		return parent;
	}

	public MPackageOutline getPackageOutline() {
		return packageOutline;
	}

	public MClassInfo<NType, NClass> getTarget() {
		return target;
	}

	public MClassOutline getSuperClassOutline() {
		return superClassOutline;
	}

	public JDefinedClass getReferenceCode() {
		return referenceCode;
	}

	public JDefinedClass getImplementationCode() {
		return implementationCode;
	}

	public JClass getImplementationReferenceCode() {
		return implementationReferenceCode;
	}

	public List<MPropertyOutline> getPropertyOutlines() {
		if (getSuperClassOutline() == null) {
			return getDeclaredPropertyOutlines();
		} else {
			final List<MPropertyOutline> propertyOutlines = new ArrayList<MPropertyOutline>();
			propertyOutlines.addAll(getSuperClassOutline()
					.getPropertyOutlines());
			propertyOutlines.addAll(getDeclaredPropertyOutlines());
			return Collections.unmodifiableList(propertyOutlines);
		}
	}

	public List<MPropertyOutline> getDeclaredPropertyOutlines() {
		return _delcaredPropertyOutlines;
	}

	public void addDeclaredPropertyOutline(MPropertyOutline propertyOutline) {
		this.declaredPropertyOutlines.add(propertyOutline);
	}

}
