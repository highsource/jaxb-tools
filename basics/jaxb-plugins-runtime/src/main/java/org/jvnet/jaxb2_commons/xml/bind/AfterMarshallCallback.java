package org.jvnet.jaxb2_commons.xml.bind;

import jakarta.xml.bind.Marshaller;

public interface AfterMarshallCallback {

	public void afterMarshal(Marshaller marshaller);
}
