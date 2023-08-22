package org.jvnet.hyperjaxb3.xjc.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CTypeInfo;

public class CTypeInfoUtils {

	private CTypeInfoUtils() {

	}

	public static CTypeInfo getSuperType(CTypeInfo type) {
		if (type instanceof CClassInfo) {
			final CClassInfo classInfo = (CClassInfo) type;
			if (classInfo.getBaseClass() != null) {
				return classInfo.getBaseClass();
			} else if (classInfo.getRefBaseClass() != null) {
				return classInfo.getRefBaseClass();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public static boolean isAssignableFrom(CTypeInfo left, CTypeInfo right) {
		if (left == right) {
			return true;
		} else {
			final CTypeInfo rightSuper = getSuperType(right);
			if (rightSuper == null) {
				return false;
			} else {
				return isAssignableFrom(left, rightSuper);
			}

		}
	}

	public static Set<CTypeInfo> getAssignableTypeItems(final CTypeInfo t) {
		final Set<CTypeInfo> s = new HashSet<CTypeInfo>();
		addAssignableTypeItems(t, s);
		return s;
	}

	private static void addAssignableTypeItems(CTypeInfo t, Set<CTypeInfo> s) {
		if (!s.add(t)) {
			return;
		}

		final CTypeInfo st = getSuperType(t);
		if (st != null) {
			addAssignableTypeItems(st, s);
		}
	}

	public static CTypeInfo getCommonBaseTypeInfo(Collection<? extends CTypeInfo> types) {
		final Set<CTypeInfo> uniqueTypes = new HashSet<CTypeInfo>();
		uniqueTypes.addAll(types);

		if (uniqueTypes.isEmpty()) {
			return null;
		}

		final CTypeInfo firstType = (CTypeInfo) uniqueTypes.iterator().next();

		if (uniqueTypes.size() == 1) {
			return firstType;
		}

		final Set<CTypeInfo> s = getAssignableTypeItems(firstType);
		for (Iterator<CTypeInfo> itr = uniqueTypes.iterator(); itr.hasNext();) {
			final CTypeInfo type = (CTypeInfo) itr.next();
			s.retainAll(getAssignableTypeItems(type));
		}

		if (s.isEmpty()) {
			return null;
		}

		// refine 's' by removing "lower" types.
		// for example, if we have both java.lang.Object and
		// java.io.InputStream, then we don't want to use java.lang.Object.

		final CTypeInfo[] raw = (CTypeInfo[]) s
				.toArray(new CTypeInfo[s.size()]);

		s.clear();

		for (int i = 0; i < raw.length; i++) { // for each raw[i]
			int j;
			for (j = 0; j < raw.length; j++) { // see if raw[j] "includes"
				// raw[i]
				if (i == j)
					continue;

				if (isAssignableFrom(raw[i], raw[j]))
					break; // raw[j] is derived from raw[i], hence j includes
				// i.
			}

			if (j == raw.length)
				// no other type includes raw[i]. remember this value.
				s.add(raw[i]);
		}

		if (s.size() == 1) {
			return (CTypeInfo) s.iterator().next();
		} else {
			return null;
		}
	}

}
