package org.jvnet.jaxb.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jvnet.jaxb.locator.ObjectLocator;

public class JAXBMergeCollectionsStrategy extends JAXBMergeStrategy {

	@SuppressWarnings("unchecked")
	@Override
	protected Object mergeInternal(ObjectLocator leftLocator,
			ObjectLocator rightLocator, @SuppressWarnings("rawtypes") Collection leftCollection,
			@SuppressWarnings("rawtypes") Collection rightCollection) {

		if (leftCollection instanceof List && rightCollection instanceof List) {
			final List<Object> list = new ArrayList<Object>(leftCollection
					.size()
					+ rightCollection.size());
			list.addAll(leftCollection);
			list.addAll(rightCollection);
			return list;
		} else if (leftCollection instanceof Set
				&& rightCollection instanceof Set) {
			final Set<Object> set = new HashSet<Object>(leftCollection.size()
					+ rightCollection.size());
			set.addAll(leftCollection);
			set.addAll(rightCollection);
			return set;
		} else {
			return super.mergeInternal(leftLocator, rightLocator,
					leftCollection, rightCollection);
		}
	}

	public static final JAXBMergeCollectionsStrategy INSTANCE = new JAXBMergeCollectionsStrategy();

	public static JAXBMergeCollectionsStrategy getInstance() {
		return INSTANCE;
	}
}
