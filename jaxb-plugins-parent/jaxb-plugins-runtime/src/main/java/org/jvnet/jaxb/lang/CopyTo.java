package org.jvnet.jaxb.lang;

import org.jvnet.jaxb.locator.ObjectLocator;

@Deprecated
public interface CopyTo {

	public Object createNewInstance();

	public Object copyTo(Object target);

	public Object copyTo(ObjectLocator locator, Object target,
			CopyStrategy copyStrategy);

}
