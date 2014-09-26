package org.jvnet.jaxb2_commons.xml.bind.model.util;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.lang.StringUtils;
import org.jvnet.jaxb2_commons.lang.Validate;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MModelInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPackageInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfo;

public class PackageInfoQNameAnalyzer<T, C> {

	private final MModelInfo<T, C> modelInfo;

	public PackageInfoQNameAnalyzer(MModelInfo<T, C> modelInfo) {
		Validate.notNull(modelInfo);
		this.modelInfo = modelInfo;
	}

	public String getMostUsedElementNamespaceURI(MPackageInfo packageInfo) {
		final NamespaceURICounter counter = new NamespaceURICounter();
		final QNameCollector collector = new QNameCollector() {

			public void element(QName name) {
				counter.add(name.getNamespaceURI());
			}

			public void attribute(QName name) {

			}
		};

		collectNamespaceURIs(packageInfo, collector);
		return counter.getMostUsedNamespaceURI();
	}

	public String getMostUsedAttributeNamespaceURI(MPackageInfo packageInfo) {
		final NamespaceURICounter counter = new NamespaceURICounter();
		final QNameCollector collector = new QNameCollector() {

			public void element(QName name) {
			}

			public void attribute(QName name) {
				counter.add(name.getNamespaceURI());
			}
		};

		collectNamespaceURIs(packageInfo, collector);
		return counter.getMostUsedNamespaceURI();
	}

	private void collectNamespaceURIs(MPackageInfo packageInfo,
			final QNameCollector collector) {
		for (MElementInfo<T, C> elementInfo : modelInfo.getElementInfos()) {
			if (elementInfo.getPackageInfo() == packageInfo) {
				collector.element(elementInfo.getElementName());
			}
		}

		final QNameCollectingPropertyInfoVisitor<T, C> visitor = new QNameCollectingPropertyInfoVisitor<T, C>(
				collector);
		for (MClassInfo<T, C> classInfo : modelInfo.getClassInfos()) {
			if (classInfo.getPackageInfo() == packageInfo) {
				for (MPropertyInfo<T, C> propertyInfo : classInfo
						.getProperties()) {
					propertyInfo.acceptPropertyInfoVisitor(visitor);
				}
			}
		}
	}

	private static class NamespaceURICounter {
		private Map<String, Integer> map = new HashMap<String, Integer>();

		public void add(String namespaceURI) {
			final Integer count = map.get(namespaceURI);
			if (count == null) {
				map.put(namespaceURI, Integer.valueOf(1));
			} else {
				map.put(namespaceURI, Integer.valueOf(count.intValue() + 1));
			}
		}

		public String getMostUsedNamespaceURI() {
			String namespaceURI = null;
			int count = 0;

			for (Map.Entry<String, Integer> e : map.entrySet()) {
				final String currentNamespaceURI = e.getKey();
				final int currentCount = e.getValue();
				if (namespaceURI == null) {
					namespaceURI = currentNamespaceURI;
					count = currentCount;
				} else {
					if (currentCount > count
							|| (currentCount == count && namespaceURI == null)) {
						namespaceURI = currentNamespaceURI;
						count = currentCount;
					}
				}
			}

			return StringUtils.isEmpty(namespaceURI) ? null : namespaceURI;

		}

	}

}
