package org.jvnet.hyperjaxb3.jdo.test.tests;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

	private final static QName _A_QNAME = new QName("", "A");

	public ObjectFactory() {
	}

	public A createA() {
		return new A();
	}

	public B createB() {
		return new B();
	}

	@XmlElementDecl(namespace = "", name = "A")
	public JAXBElement<A> createA(A value) {
		return new JAXBElement<A>(_A_QNAME, A.class, null, value);
	}

}
