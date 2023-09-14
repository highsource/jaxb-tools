package org.jvnet.hyperjaxb3.xml.bind.annotation.adapters;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.namespace.QName;

public class QNameAsString extends XmlAdapter<QName, String> {

	@Override
	public QName marshal(String name) throws Exception {
		if (name == null) {
			return null;
		} else {
			return QName.valueOf(name);
		}
	}

	@Override
	public String unmarshal(QName name) throws Exception {
		if (name == null) {
			return null;
		} else {
			return name.toString();
		}
	}
}
