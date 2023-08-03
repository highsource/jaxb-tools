package org.jvnet.jaxb2_commons.xml.bind;

import javax.xml.bind.Marshaller;

public interface BeforeMarshallCallback {

	public void beforeMarshal(Marshaller marshaller);
}
