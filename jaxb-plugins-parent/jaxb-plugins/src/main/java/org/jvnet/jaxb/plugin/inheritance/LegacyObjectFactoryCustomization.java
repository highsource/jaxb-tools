package org.jvnet.jaxb.plugin.inheritance;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(namespace = "http://jaxb2-commons.dev.java.net/basic/inheritance", name = "objectFactory")
@XmlType(propOrder = { "extendsClass", "implementsInterface" })
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class LegacyObjectFactoryCustomization {

	private String packageName;

	@XmlAttribute
	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	private LegacyExtendsClass extendsClass;

	@XmlElement(namespace = "http://jaxb2-commons.dev.java.net/basic/inheritance", name = "extends")
	public LegacyExtendsClass getExtendsClass() {
		return extendsClass;
	}

	public void setExtendsClass(LegacyExtendsClass extendsClass) {
		this.extendsClass = extendsClass;
	}

	private List<LegacyImplementsInterface> implementsInterface = new ArrayList<LegacyImplementsInterface>();

	@XmlElement(namespace = "http://jaxb2-commons.dev.java.net/basic/inheritance", name = "implements")
	public List<LegacyImplementsInterface> getImplementsInterface() {
		return implementsInterface;
	}

	public void setImplementsInterface(
			List<LegacyImplementsInterface> implementsInterface) {
		this.implementsInterface = implementsInterface;
	}

}
