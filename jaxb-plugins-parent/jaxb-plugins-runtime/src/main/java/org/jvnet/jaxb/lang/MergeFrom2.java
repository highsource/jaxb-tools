package org.jvnet.jaxb.lang;

import org.jvnet.jaxb.locator.ObjectLocator;

public interface MergeFrom2 {

	public Object createNewInstance();

	public void mergeFrom(Object left, Object right);

	public void mergeFrom(ObjectLocator leftLocator,
			ObjectLocator rightLocator, Object left, Object right,
			MergeStrategy2 mergeStrategy);

}
