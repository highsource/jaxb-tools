package org.jvnet.jaxb2_commons.lang;

import org.jvnet.jaxb2_commons.locator.ObjectLocator;

public interface CopyTo {

	public Object createNewInstance();

	public Object copyTo(Object target);

	public Object copyTo(ObjectLocator locator, Object target,
			CopyStrategy copyStrategy);

}
