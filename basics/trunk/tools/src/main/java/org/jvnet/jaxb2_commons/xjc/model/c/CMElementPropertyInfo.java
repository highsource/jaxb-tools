package org.jvnet.jaxb2_commons.xjc.model.c;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.xjc.model.MElementPropertyInfo;
import org.jvnet.jaxb2_commons.xjc.model.MTypeInfo;

public class CMElementPropertyInfo extends CMPropertyInfo implements MElementPropertyInfo{
	
	private final MTypeInfo typeInfo;
	private final QName elementName;
	private final QName wrapperElementName;
	private final boolean listed;
	
	

	public CMElementPropertyInfo(String privateName, boolean collection,
			MTypeInfo typeInfo, QName elementName, QName wrapperElementName,
			boolean listed) {
		super(privateName, collection);
		this.typeInfo = typeInfo;
		this.elementName = elementName;
		this.wrapperElementName = wrapperElementName;
		this.listed = listed;
	}

	public MTypeInfo getTypeInfo() {
		return typeInfo;
	}
	
	public QName getElementName() {
		return elementName;
	}
	
	public QName getWrapperElementName() {
		return wrapperElementName;
	}
	
	public boolean isListed() {
		return listed;
	}
	

}
