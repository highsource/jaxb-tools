package org.jvnet.jaxb.annox.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jvnet.jaxb.annox.Constants;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AnnotationElementUtils {

	private AnnotationElementUtils() {
	}

	public static String getFieldValue(final Element annotationElement,
			final String name) {
		String value = null;

		final String attribute = annotationElement.getAttribute(name);
		if (!StringUtils.isEmpty(attribute)) {
			value = attribute;
		}

		final NodeList nodes = annotationElement.getChildNodes();
		for (int index = 0; index < nodes.getLength(); index++) {
			final Node node = nodes.item(index);
			if (isFieldContainerElement(node, name)) {
				value = ((Element) node).getTextContent();
			}
		}
		if ("value".equals(name) && value == null) {
			value = annotationElement.getTextContent();
		}
		return value;
	}

	private static boolean isFieldContainerElement(Node node, String name) {
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			final Element element = (Element) node;
			if (name.equals(element.getLocalName())
					|| name.equals(element.getAttributeNS(
							Constants.NAMESPACE_URI, "field"))) {
				return true;
			}
		}
		return false;
	}

	public static Element getFieldElement(final Element annotationElement,
			final String name) {
		Element value = null;
		final NodeList nodes = annotationElement.getChildNodes();
		for (int index = 0; index < nodes.getLength(); index++) {
			final Node node = nodes.item(index);
			if (isFieldContainerElement(node, name)) {
				final Element element = (Element) node;
				final NodeList subelements = element.getChildNodes();
				for (int jndex = 0; jndex < subelements.getLength(); jndex++) {
					final Node subnode = subelements.item(jndex);
					if (subnode.getNodeType() == Node.ELEMENT_NODE) {
						value = (Element) subnode;
					}
				}
			}
		}
		if ("value".equals(name) && value == null) {
			for (int index = 0; index < nodes.getLength(); index++) {
				final Node node = nodes.item(index);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					value = (Element) node;
				}
			}
		}
		return value;
	}

	public static String[] getFieldValues(final Element annotationElement,
			final String name) {
		final List<String> values = new ArrayList<String>();
		final String attribute = annotationElement.getAttribute(name);
		if (!StringUtils.isEmpty(attribute)) {
			final String[] entries = attribute.trim().split(" ");
			values.addAll(Arrays.asList(entries));
		}

		final NodeList nodes = annotationElement.getChildNodes();
		for (int index = 0; index < nodes.getLength(); index++) {
			final Node node = nodes.item(index);
			if (isFieldContainerElement(node, name)) {
				final Element element = (Element) node;
				final String entry = element.getTextContent();
				values.add(entry);
			}
		}

		if ("value".equals(name) && values.isEmpty()) {
			final String text = annotationElement.getTextContent();
			if (!StringUtils.isEmpty(text)) {
				final String[] entries = text.trim().split(" ");
				values.addAll(Arrays.asList(entries));
			}
		}
		return values.isEmpty() ? new String[0] : values
				.toArray(new String[values.size()]);
	}

	public static Element[] getFieldElements(final Element annotationElement,
			final String name) {
		final List<Element> values = new ArrayList<Element>();
		final NodeList nodes = annotationElement.getChildNodes();
		for (int index = 0; index < nodes.getLength(); index++) {
			final Node node = nodes.item(index);
			if (isFieldContainerElement(node, name)) {
				final Element element = (Element) node;
				final NodeList subelements = element.getChildNodes();
				for (int jndex = 0; jndex < subelements.getLength(); jndex++) {
					final Node subnode = subelements.item(jndex);
					if (subnode.getNodeType() == Node.ELEMENT_NODE) {
						values.add((Element) subnode);
					}
				}
			}
		}
		if ("value".equals(name) && values.isEmpty()) {
			for (int index = 0; index < nodes.getLength(); index++) {
				final Node node = nodes.item(index);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					values.add((Element) node);
				}
			}
		}
		return values.isEmpty() ? new Element[0] : values
				.toArray(new Element[values.size()]);
	}
}
