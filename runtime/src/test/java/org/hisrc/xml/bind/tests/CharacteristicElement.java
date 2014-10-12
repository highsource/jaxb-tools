package org.hisrc.xml.bind.tests;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

public class CharacteristicElement extends JAXBElement<Characteristic> {

	private static final long serialVersionUID = 6867156576690396968L;

	public static final QName NAME = new QName("characteristic");

	public CharacteristicElement(Characteristic value) {
		super(new QName("foo"), Characteristic.class, value);
	}

	@Override
	public QName getName() {
		final Characteristic value = this.getValue();
		if (value != null) {
			final String characteristic = value.getCharacteristic();
			if (characteristic != null) {
				return new QName(characteristic);
			}
		}
		return NAME;
	}
}
