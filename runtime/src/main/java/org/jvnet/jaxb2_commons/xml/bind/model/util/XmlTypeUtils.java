package org.jvnet.jaxb2_commons.xml.bind.model.util;

import java.util.HashMap;
import java.util.Map;

import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlSchema;
import jakarta.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

import org.glassfish.jaxb.core.api.impl.NameConverter;
import org.jvnet.jaxb2_commons.lang.Validate;

public class XmlTypeUtils {

	private XmlTypeUtils() {
	}

	public static QName getTypeName(Class<?> targetClass) {
		Validate.notNull(targetClass);
		final Package targetPackage = targetClass.getPackage();
		final XmlType xmlTypeAnnotation = targetClass
				.getAnnotation(XmlType.class);

		final String localPart;
		final String namespaceURI;
		final String prefix;

		if (xmlTypeAnnotation == null) {
			localPart = NameConverter.standard.toVariableName(targetClass
					.getSimpleName());
			namespaceURI = getNamespace(targetPackage);
		} else {
			final String name = xmlTypeAnnotation.name();
			if (name == null || "".equals(name)) {
				localPart = null;
			} else {
				if ("##default".equals(name)) {
					localPart = NameConverter.standard
							.toVariableName(targetClass.getSimpleName());
				} else {
					localPart = name;
				}
			}

			final String namespace = xmlTypeAnnotation.namespace();

			if (namespace == null || "".equals(namespace)) {
				namespaceURI = "";
			} else {
				if ("##default".equals(namespace)) {
					namespaceURI = getNamespace(targetPackage);
				} else {
					namespaceURI = namespace;
				}
			}
		}

		if (localPart == null) {
			return null;
		} else {
			prefix = getPrefix(targetPackage, namespaceURI);
		}

		return prefix == null ? new QName(namespaceURI, localPart) : new QName(
				namespaceURI, localPart, prefix);
	}

	private static String getPrefix(final Package targetPackage,
			String namespaceURI) {
		String prefix;
		final Map<String, String> namespacePrefixes = new HashMap<String, String>();
		if (targetPackage != null) {
			final XmlSchema xmlSchemaAnnotation = targetPackage
					.getAnnotation(XmlSchema.class);
			if (xmlSchemaAnnotation != null) {
				for (XmlNs xmlns : xmlSchemaAnnotation.xmlns()) {
					namespacePrefixes.put(xmlns.namespaceURI(), xmlns.prefix());
				}
			}
		}

		prefix = namespacePrefixes.get(namespaceURI);
		return prefix;
	}

	private static String getNamespace(final Package targetPackage) {
		String namespaceURI;
		if (targetPackage == null) {
			namespaceURI = "";
		} else {
			final XmlSchema xmlSchemaAnnotation = targetPackage
					.getAnnotation(XmlSchema.class);
			if (xmlSchemaAnnotation == null) {
				namespaceURI = "";
			} else {
				final String packageNamespace = xmlSchemaAnnotation.namespace();
				if (packageNamespace == null || "".equals(packageNamespace)) {
					namespaceURI = "";
				} else {
					namespaceURI = packageNamespace;
				}
			}
		}
		return namespaceURI;
	}
}
