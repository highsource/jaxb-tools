package org.jvnet.jaxb2_commons.xjc.outline.concrete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.jvnet.jaxb2_commons.xjc.outline.MClassOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MModelOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MPackageOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MPropertyOutline;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JDefinedClass;

public class CMClassOutline implements MClassOutline {

	private final MModelOutline parent;
	private final MPackageOutline packageOutline;
	private final MClassInfo target;
	private final MClassOutline superClassOutline;

	private final JDefinedClass referenceCode;
	private final JDefinedClass implementationCode;
	private final JClass implementationReferenceCode;

	private final List<MPropertyOutline> declaredPropertyOutlines = new ArrayList<MPropertyOutline>();
	private final List<MPropertyOutline> _delcaredPropertyOutlines = Collections
			.unmodifiableList(declaredPropertyOutlines);

	public CMClassOutline(MModelOutline parent, MPackageOutline packageOutline,
			MClassInfo target, MClassOutline superClassOutline,
			JDefinedClass referenceCode, JDefinedClass implementationCode,
			JClass implementationReferenceCode) {
		Validate.notNull(parent);
		Validate.notNull(packageOutline);
		Validate.notNull(target);
		Validate.notNull(referenceCode);
		Validate.notNull(implementationCode);
		Validate.notNull(implementationReferenceCode);
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

	public MClassInfo getTarget() {
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

	@Override
	public List<MPropertyOutline> getPropertyOutlines() {
		if (getSuperClassOutline() == null) {
			return getDeclaredPropertyOutlines();
		} else {
			final List<MPropertyOutline> propertyOutlines = new ArrayList<MPropertyOutline>();
			propertyOutlines.addAll(getSuperClassOutline().getPropertyOutlines());
			propertyOutlines.addAll(getDeclaredPropertyOutlines());
			return Collections.unmodifiableList(propertyOutlines);
		}
	}

	@Override
	public List<MPropertyOutline> getDeclaredPropertyOutlines() {
		return _delcaredPropertyOutlines;
	}

	public void addDeclaredPropertyOutline(MPropertyOutline propertyOutline) {
		this.declaredPropertyOutlines.add(propertyOutline);
	}

}
