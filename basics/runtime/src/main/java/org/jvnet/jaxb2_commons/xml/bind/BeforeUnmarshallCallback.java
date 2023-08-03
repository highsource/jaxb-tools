package org.jvnet.jaxb2_commons.xml.bind;

import javax.xml.bind.Unmarshaller;

public interface BeforeUnmarshallCallback {

	public void beforeUnmarshal(Unmarshaller unmarshaller, Object parent);
}
