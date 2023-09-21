package org.jvnet.jaxb.xml.bind.model.concrete;

import javax.xml.namespace.NamespaceContext;

import org.jvnet.jaxb.lang.Validate;
import org.jvnet.jaxb.xml.bind.model.MClassInfo;
import org.jvnet.jaxb.xml.bind.model.MSingleTypePropertyInfo;
import org.jvnet.jaxb.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb.xml.bind.model.origin.MPropertyInfoOrigin;

public abstract class CMSingleTypePropertyInfo<T, C extends T> extends
		CMPropertyInfo<T, C> implements MSingleTypePropertyInfo<T, C> {

	private final MTypeInfo<T, C> typeInfo;
	private final String defaultValue;
	private final NamespaceContext defaultValueNamespaceContext;

	public CMSingleTypePropertyInfo(MPropertyInfoOrigin origin,
			MClassInfo<T, C> classInfo, String privateName, boolean collection,
			MTypeInfo<T, C> typeInfo, boolean required, String defaultValue,
			NamespaceContext defaultValueNamespaceContext) {
		super(origin, classInfo, privateName, collection, required);
		Validate.notNull(typeInfo);
		this.typeInfo = typeInfo;
		this.defaultValue = defaultValue;
		this.defaultValueNamespaceContext = defaultValueNamespaceContext;
	}

	@Override
	public String getDefaultValue() {
		return this.defaultValue;
	}

	@Override
	public NamespaceContext getDefaultValueNamespaceContext() {
		return this.defaultValueNamespaceContext;
	}

	public MTypeInfo<T, C> getTypeInfo() {
		return typeInfo;
	}

}
