package org.jvnet.jaxb2_commons.xml.bind.model.util;

import javax.xml.namespace.QName;

public interface QNameCollector {

	public void element(QName name);

	public void attribute(QName name);
}
