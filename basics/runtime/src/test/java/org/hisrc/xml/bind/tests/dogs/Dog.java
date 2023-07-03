package org.hisrc.xml.bind.tests.dogs;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

public class Dog extends JAXBElement<DogType> {

	public static final QName NAME = new QName("dog");

	private static final long serialVersionUID = 1L;

	public Dog(DogType value) {
		super(NAME, DogType.class, value);
	}

	public Dog(String dogName, DogType value) {
		super(NAME, DogType.class, value);
//		if (value != null) {
//			value.setName(dogName);
//		}
	}
	
	@Override
	public QName getName() {
		final DogType value = getValue();
		if (value != null && value.getName() != null) {
			return new QName(value.getName());
		} else {
			return super.getName();
		}
	}
}