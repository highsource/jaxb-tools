package org.jvnet.jaxb2.maven2.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class CollectionUtils {

	public static class PositiveComparator<V extends Object & Comparable<? super V>>
			implements Comparator<V> {
		public int compare(V o1, V o2) {
			if (o1 == null && o2 == null)
				return 0;
			else if (o1 == null)
				return 1;
			else if (o2 == null)
				return -1;
			else
				return o1.compareTo(o2);
		}
	}

	public static class NegativeComparator<V extends Object & Comparable<? super V>>
			implements Comparator<V> {
		public int compare(V o1, V o2) {
			if (o1 == null && o2 == null)
				return 0;
			else if (o1 == null)
				return -1;
			else if (o2 == null)
				return 1;
			else
				return -o1.compareTo(o2);
		}
	}

	public interface Function<T, V> {
		public V eval(T argument);
	}

	public static <T, V> List<V> apply(Collection<T> collection,
			Function<T, V> function) {
		final List<V> list = new ArrayList<V>(collection.size());
		for (T t : collection) {
			list.add(function.eval(t));
		}
		return list;
	}

	public static <T, V> V bestValue(Collection<T> collection,
			CollectionUtils.Function<T, V> function, Comparator<V> comparator) {

		if (collection == null || collection.isEmpty())
			return null;

		final Iterator<T> i = collection.iterator();
		V candidateValue = function.eval(i.next());

		while (i.hasNext()) {
			final V nextValue = function.eval(i.next());
			if (comparator.compare(candidateValue, nextValue) < 0) {
				candidateValue = nextValue;
			}
		}
		return candidateValue;
	}

	@SuppressWarnings("rawtypes")
	private static Comparator<?> LT = new NegativeComparator();

	@SuppressWarnings("rawtypes")
	private static Comparator<?> GT = new PositiveComparator();

	public static <V extends Object & Comparable<? super V>> Comparator<V> lt() {
		@SuppressWarnings("unchecked")
		final Comparator<V> comparator = (Comparator<V>) LT;
		return comparator;
	}

	public static <V extends Object & Comparable<? super V>> Comparator<V> gt() {
		@SuppressWarnings("unchecked")
		final Comparator<V> comparator = (Comparator<V>) GT;
		return comparator;
	}

}
