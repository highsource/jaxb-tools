package org.jvnet.jaxb.xml.bind;

import jakarta.xml.bind.Unmarshaller;

public interface BeforeUnmarshallCallback {

	public void beforeUnmarshal(Unmarshaller unmarshaller, Object parent);
}
