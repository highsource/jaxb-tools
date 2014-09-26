package org.jvnet.jaxb2_commons.xml.bind;

import javax.xml.bind.Marshaller;

public interface AfterMarshallCallback {

	public void afterMarshal(Marshaller marshaller);
}
