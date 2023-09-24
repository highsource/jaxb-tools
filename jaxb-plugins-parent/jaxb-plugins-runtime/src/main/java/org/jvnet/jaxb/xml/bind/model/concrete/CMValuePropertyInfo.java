package org.jvnet.jaxb.xml.bind.model.concrete;

import javax.xml.namespace.NamespaceContext;

import org.jvnet.jaxb.xml.bind.model.MClassInfo;
import org.jvnet.jaxb.xml.bind.model.MPropertyInfoVisitor;
import org.jvnet.jaxb.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb.xml.bind.model.MValuePropertyInfo;
import org.jvnet.jaxb.xml.bind.model.origin.MPropertyInfoOrigin;

public class CMValuePropertyInfo<T, C extends T> extends
		CMSingleTypePropertyInfo<T, C> implements MValuePropertyInfo<T, C> {

	public CMValuePropertyInfo(MPropertyInfoOrigin origin,
			MClassInfo<T, C> classInfo, String privateName,
			MTypeInfo<T, C> typeInfo, String defaultValue,
			NamespaceContext defaultValueNamespaceContext) {
		super(origin, classInfo, privateName, false, typeInfo, /* required */
				true, defaultValue, defaultValueNamespaceContext);
	}

	public <V> V acceptPropertyInfoVisitor(MPropertyInfoVisitor<T, C, V> visitor) {
		return visitor.visitValuePropertyInfo(this);
	}

}
