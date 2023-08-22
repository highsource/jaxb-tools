package org.jvnet.hyperjaxb3.ejb.schemas.customizations;

public interface Mergeable {
	/**
	 * Whether the object should be merged or not.
	 * 
	 * @return <code>true</code> to merge, <code>false</code> otherwise.
	 */
	public boolean isMerge();
}
