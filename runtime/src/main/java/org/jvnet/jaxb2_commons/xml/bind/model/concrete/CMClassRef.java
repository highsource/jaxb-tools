package org.jvnet.jaxb2_commons.xml.bind.model.concrete;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassRef;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassTypeInfoVisitor;
import org.jvnet.jaxb2_commons.xml.bind.model.MContainer;
import org.jvnet.jaxb2_commons.xml.bind.model.MCustomizations;
import org.jvnet.jaxb2_commons.xml.bind.model.MPackageInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfoVisitor;
import org.jvnet.jaxb2_commons.xml.bind.model.util.XmlTypeUtils;

public class CMClassRef<T, C extends T> implements MClassRef<T, C> {

	private CMCustomizations customizations = new CMCustomizations();
	private final C targetType;
	private final Class<?> targetClass;
	private final MPackageInfo _package;
	private final String name;
	private final String localName;
	private final MContainer container;
	private final QName typeName;

	public CMClassRef(/* MClassInfoOrigin origin, */C targetType,
			Class<?> targetClass, MPackageInfo _package, MContainer container,
			String localName) {
		super();
		// Validate.notNull(origin);
		Validate.notNull(targetType);
		Validate.notNull(_package);
		Validate.notNull(localName);
		// this.origin = origin;
		this.targetType = targetType;
		this.name = _package.getPackagedName(localName);
		this.localName = localName;
		this._package = _package;
		this.container = container;
		this.targetClass = targetClass;
		this.typeName = targetClass == null ? null : XmlTypeUtils
				.getTypeName(targetClass);
	}

	public String getName() {
		return name;
	}

	public String getLocalName() {
		return localName;
	}

	public C getTargetType() {
		return targetType;
	}

	@Override
	public QName getTypeName() {
		return typeName;
	}

	@Override
	public boolean isSimpleType() {
		return false;
	}

	public <V> V acceptTypeInfoVisitor(MTypeInfoVisitor<T, C, V> visitor) {
		return visitor.visitClassRef(this);
	}

	public MCustomizations getCustomizations() {
		return customizations;
	}

	public MPackageInfo getPackageInfo() {
		return _package;
	}

	public MContainer getContainer() {
		return container;
	}

	public String getContainerLocalName(String delimiter) {
		final String localName = getLocalName();
		if (localName == null) {
			return null;
		} else {
			final MContainer container = getContainer();
			if (container == null) {
				return localName;
			} else {
				final String containerLocalName = container
						.getContainerLocalName(delimiter);
				return containerLocalName == null ? localName
						: containerLocalName + delimiter + localName;
			}
		}
	}

	public C getTargetClass() {
		return targetType;
	}

	@Override
	public <V> V acceptClassTypeInfoVisitor(
			MClassTypeInfoVisitor<T, C, V> visitor) {
		return visitor.visitClassRef(this);
	}

}
