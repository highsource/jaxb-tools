package org.jvnet.jaxb2_commons.xml.bind;

import jakarta.xml.bind.Unmarshaller;

public interface AfterUnmarshallCallback {

	public void afterUnmarshal(Unmarshaller unmarshaller, Object parent);
}
