package org.jvnet.jaxb2_commons.plugin.inheritance;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(namespace = "http://jaxb2-commons.dev.java.net/basic/inheritance", name = "objectFactory")
@XmlType(propOrder = { "extendsClass", "implementsInterface" })
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class ObjectFactoryCustomization {

	private String packageName;

	@XmlAttribute
	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	private ExtendsClass extendsClass;

	@XmlElement(namespace = "http://jaxb2-commons.dev.java.net/basic/inheritance", name = "extends")
	public ExtendsClass getExtendsClass() {
		return extendsClass;
	}

	public void setExtendsClass(ExtendsClass extendsClass) {
		this.extendsClass = extendsClass;
	}

	private List<ImplementsInterface> implementsInterface = new ArrayList<ImplementsInterface>();

	@XmlElement(namespace = "http://jaxb2-commons.dev.java.net/basic/inheritance", name = "implements")
	public List<ImplementsInterface> getImplementsInterface() {
		return implementsInterface;
	}

	public void setImplementsInterface(
			List<ImplementsInterface> implementsInterface) {
		this.implementsInterface = implementsInterface;
	}

}
