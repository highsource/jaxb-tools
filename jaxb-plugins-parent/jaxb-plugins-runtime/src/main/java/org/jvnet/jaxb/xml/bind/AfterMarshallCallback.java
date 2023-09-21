package org.jvnet.jaxb.xml.bind;

import jakarta.xml.bind.Marshaller;

public interface AfterMarshallCallback {

	public void afterMarshal(Marshaller marshaller);
}
