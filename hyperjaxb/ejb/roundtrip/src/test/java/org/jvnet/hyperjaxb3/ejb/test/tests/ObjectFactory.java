package org.jvnet.hyperjaxb3.ejb.test.tests;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.jvnet.hyperjaxb3.ejb.test.tests.A.G;
import org.jvnet.hyperjaxb3.ejb.test.tests.A.G1;
import org.jvnet.hyperjaxb3.ejb.test.tests.A.G2;

@XmlRegistry
public class ObjectFactory {

	private final static QName _A_QNAME = new QName("", "A");

	private final static QName _G_QNAME = new QName("", "g");

	private final static QName _G1_QNAME = new QName("", "g1");

	private final static QName _G2_QNAME = new QName("", "g2");

	private final static QName _K_QNAME = new QName("", "K");

	private final static QName _L_QNAME = new QName("", "L");

	public ObjectFactory() {
	}

	public A createA() {
		return new A();
	}

	public B createB() {
		return new B();
	}

	public K createK() {
		return new K();
	}

	public M createM() {
		return new M();
	}

	@XmlElementDecl(namespace = "", name = "A")
	public JAXBElement<A> createA(A value) {
		return new JAXBElement<A>(_A_QNAME, A.class, null, value);
	}

	@XmlElementDecl(namespace = "", name = "g")
	public JAXBElement<G> createG(G value) {
		return new JAXBElement<G>(_G_QNAME, G.class, null, value);
	}

	@XmlElementDecl(namespace = "", name = "K")
	public JAXBElement<K> createK(K value) {
		return new JAXBElement<K>(_K_QNAME, K.class, null, value);
	}

	@XmlElementDecl(namespace = "", name = "L")
	public JAXBElement<L> createL(L value) {
		return new JAXBElement<L>(_L_QNAME, L.class, null, value);
	}

	public G createG() {
		return new G();
	}

	@XmlElementDecl(namespace = "", name = "g1", substitutionHeadNamespace = "", substitutionHeadName = "g")
	public JAXBElement<G1> createG1(G1 value) {
		return new JAXBElement<G1>(_G1_QNAME, G1.class, null, value);
	}

	public G1 createG1() {
		return new G1();
	}

	@XmlElementDecl(namespace = "", name = "g2", substitutionHeadNamespace = "", substitutionHeadName = "g")
	public JAXBElement<G2> createG2(G2 value) {
		return new JAXBElement<G2>(_G2_QNAME, G2.class, null, value);
	}

	public G2 createG2() {
		return new G2();
	}

	@XmlElementDecl(namespace = "", name = "eNillable")
	public JAXBElement<String> createENillable(String value) {
		return new JAXBElement<String>(new QName("eNillable"), String.class,
				A.class, value);
	}

	@XmlElementDecl(namespace = "", name = "fNillable")
	public JAXBElement<XMLGregorianCalendar> createFNillable(
			XMLGregorianCalendar value) {
		return new JAXBElement<XMLGregorianCalendar>(new QName("fNillable"),
				XMLGregorianCalendar.class, A.class, value);
	}
}
