package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MEnumConstantInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MEnumLeafInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPackageInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfoVisitor;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.EnumConstantOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.origin.EnumLeafInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.origin.MEnumLeafInfoOrigin;

import com.sun.xml.bind.v2.model.core.EnumConstant;
import com.sun.xml.bind.v2.model.core.EnumLeafInfo;

public class CMEnumLeafInfo implements MEnumLeafInfo {

	private final MEnumLeafInfoOrigin origin;
	private final MPackageInfo _package;
	private final String name;
	private final String localName;
	private final MTypeInfo baseTypeInfo;
	private final List<MEnumConstantInfo> constants = new ArrayList<MEnumConstantInfo>();
	private final List<MEnumConstantInfo> _constants = Collections
			.unmodifiableList(constants);
	private final QName elementName;

	public CMEnumLeafInfo(MEnumLeafInfoOrigin origin, MPackageInfo _package,
			String localName, MTypeInfo baseTypeInfo, QName elementName) {

		Validate.notNull(origin);
		Validate.notNull(_package);
		Validate.notNull(localName);
		Validate.notNull(baseTypeInfo);
		this.origin = origin;
		this._package = _package;
		this.localName = localName;
		this.name = _package.getPackagedName(localName);
		this.baseTypeInfo = baseTypeInfo;
		// May be null
		this.elementName = elementName;
	}

	public MEnumLeafInfoOrigin getOrigin() {
		return origin;
	}

	@Override
	public MElementInfo createElementInfo(MTypeInfo scope,
			QName substitutionHead) {
		return new CMElementInfo(getOrigin().createElementInfoOrigin(),
				getPackageInfo(), getElementName(), scope, this, substitutionHead);
	}

	public String getName() {
		return name;
	}

	@Override
	public String getLocalName() {
		return localName;
	}

	@Override
	public MPackageInfo getPackageInfo() {
		return _package;
	}

	@Override
	public MTypeInfo getBaseTypeInfo() {
		return baseTypeInfo;
	}

	@Override
	public List<MEnumConstantInfo> getConstants() {
		return _constants;
	}

	public void addEnumConstantInfo(MEnumConstantInfo enumConstantInfo) {
		Validate.notNull(enumConstantInfo);
		this.constants.add(enumConstantInfo);
	}

	public void removeEnumConstantInfo(MEnumConstantInfo enumConstantInfo) {
		Validate.notNull(enumConstantInfo);

		if (getOrigin() instanceof EnumLeafInfoOrigin
				&& enumConstantInfo.getOrigin() instanceof EnumConstantOrigin) {
			// TODO
			EnumLeafInfo eli = (EnumLeafInfo) ((EnumLeafInfoOrigin) getOrigin())
					.getSource();
			EnumConstant ec = (EnumConstant) ((EnumConstantOrigin) enumConstantInfo
					.getOrigin()).getSource();

			Iterator iterator = eli.getConstants().iterator();

			while (iterator.hasNext()) {
				if (iterator.next() == ec) {
					iterator.remove();
				}
			}
		}
		// TODO Auto-generated method stub

	}

	@Override
	public QName getElementName() {
		return elementName;
	}

	@Override
	public String toString() {
		return MessageFormat.format("EnumInfo [{0}]", getBaseTypeInfo());
	}

	@Override
	public <V> V acceptTypeInfoVisitor(MTypeInfoVisitor<V> visitor) {
		return visitor.visitEnumLeafInfo(this);
	}

}
