package org.jvnet.jaxb2_commons.lang;

import java.util.Collection;

import org.jvnet.jaxb2_commons.locator.ObjectLocator;

public class JAXBMergeStrategy extends DefaultMergeStrategy {

	@Override
	protected Object mergeInternal(ObjectLocator leftLocator,
			ObjectLocator rightLocator, Object left, Object right) {
		if (left instanceof Collection && right instanceof Collection) {
			Collection leftCollection = (Collection) left;
			Collection rightCollection = (Collection) right;
			return mergeInternal(leftLocator, rightLocator, leftCollection,
					rightCollection);
		} else {
			return super.mergeInternal(leftLocator, rightLocator, left, right);
		}
	}

	protected Object mergeInternal(ObjectLocator leftLocator,
			ObjectLocator rightLocator, Collection leftCollection,
			Collection rightCollection) {
		return !leftCollection.isEmpty() ? leftCollection : rightCollection;
	}

	public static final JAXBMergeStrategy INSTANCE2 = new JAXBMergeStrategy();
	@SuppressWarnings("deprecation")
	public static final MergeStrategy INSTANCE = INSTANCE2;

}
