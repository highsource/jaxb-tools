package org.jvnet.jaxb.lang;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jvnet.jaxb.locator.ObjectLocator;

public class JAXBMergeStrategy extends DefaultMergeStrategy {

	@Override
	protected Object mergeInternal(ObjectLocator leftLocator,
			ObjectLocator rightLocator, Object left, Object right) {
		if (left instanceof Collection && right instanceof Collection) {
			@SuppressWarnings("rawtypes")
			Collection leftCollection = (Collection) left;
			@SuppressWarnings("rawtypes")
			Collection rightCollection = (Collection) right;
			return mergeInternal(leftLocator, rightLocator, leftCollection,
					rightCollection);
        } else if (left instanceof Map && right instanceof Map) {
            @SuppressWarnings("rawtypes")
            Map leftMap = (Map) left;
            @SuppressWarnings("rawtypes")
            Map rightMap = (Map) right;
            return mergeInternal(leftLocator, rightLocator, leftMap, rightMap);
        } else {
			return super.mergeInternal(leftLocator, rightLocator, left, right);
		}
	}

    protected Object mergeInternal(ObjectLocator leftLocator,
			ObjectLocator rightLocator, @SuppressWarnings("rawtypes") Collection leftCollection,
                                   @SuppressWarnings("rawtypes") Collection rightCollection) {
		return !leftCollection.isEmpty() ? leftCollection : rightCollection;
	}

    protected Object mergeInternal(ObjectLocator leftLocator,
                                   ObjectLocator rightLocator,
                                   @SuppressWarnings("rawtypes") Map leftMap,
                                   @SuppressWarnings("rawtypes") Map rightMap) {
        return !leftMap.isEmpty() ? leftMap : rightMap;
    }

	public static final JAXBMergeStrategy INSTANCE = new JAXBMergeStrategy();

	public static JAXBMergeStrategy getInstance() {
		return INSTANCE;
	}
}
