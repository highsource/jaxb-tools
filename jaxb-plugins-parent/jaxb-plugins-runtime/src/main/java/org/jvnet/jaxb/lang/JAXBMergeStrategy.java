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
			return mergeInternal(leftLocator, rightLocator, leftCollection, rightCollection);
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
                                   ObjectLocator rightLocator,
                                   @SuppressWarnings("rawtypes") Map leftMap,
                                   @SuppressWarnings("rawtypes") Map rightMap) {
        if (leftMap == null && rightMap == null) {
            return Collections.emptyMap();
        }
        @SuppressWarnings("rawtypes") Set<Map.Entry> leftMapEntrySet = leftMap == null ? ((Map) Collections.emptyMap()).entrySet() : leftMap.entrySet();
        @SuppressWarnings("rawtypes") Set<Map.Entry> rightMapEntrySet = rightMap == null ? ((Map) Collections.emptyMap()).entrySet() : rightMap.entrySet();

        return Stream.concat(leftMapEntrySet.stream(), rightMapEntrySet.stream()).collect(Collectors.toMap(
                (v) -> v.getKey(),
                (v) -> v.getValue(),
                (value1, value2) -> value2 // En cas de doublon de clé, on garde la valeur de la map2
            ));
    }

	protected Object mergeInternal(ObjectLocator leftLocator,
                                   ObjectLocator rightLocator,
                                   @SuppressWarnings("rawtypes") Collection leftCollection,
                                   @SuppressWarnings("rawtypes") Collection rightCollection) {
        if (leftCollection == null && rightCollection == null) {
            return Collections.emptyList();
        }
        return Stream.concat(
                leftCollection == null ? Stream.empty() : leftCollection.stream(),
                rightCollection == null ? Stream.empty() : rightCollection.stream())
            .collect(Collectors.toList());
	}

	public static final JAXBMergeStrategy INSTANCE = new JAXBMergeStrategy();

	public static JAXBMergeStrategy getInstance() {
		return INSTANCE;
	}
}
