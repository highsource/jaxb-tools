package org.jvnet.jaxb.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    protected Object mergeInternal(ObjectLocator leftLocator,
                                   ObjectLocator rightLocator,
                                   @SuppressWarnings("rawtypes") Map leftMap,
                                   @SuppressWarnings("rawtypes") Map rightMap) {
        @SuppressWarnings("rawtypes") Set<Map.Entry> leftMapEntrySet = leftMap.entrySet();
        @SuppressWarnings("rawtypes") Set<Map.Entry> rightMapEntrySet = rightMap.entrySet();

        return Stream.concat(leftMapEntrySet.stream(), rightMapEntrySet.stream()).collect(Collectors.toMap(
            (v) -> v.getKey(),
            (v) -> v.getValue(),
            (value1, value2) -> merge(leftLocator, rightLocator, value1, value2)
        ));
    }

	public static final JAXBMergeCollectionsStrategy INSTANCE = new JAXBMergeCollectionsStrategy();

	public static JAXBMergeCollectionsStrategy getInstance() {
		return INSTANCE;
	}
}
