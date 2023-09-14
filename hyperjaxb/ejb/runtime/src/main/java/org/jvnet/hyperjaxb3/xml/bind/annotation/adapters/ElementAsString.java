package org.jvnet.hyperjaxb3.xml.bind.annotation.adapters;

import java.io.StringReader;
import java.io.StringWriter;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class ElementAsString extends XmlAdapter<Element, String> {

	private final DocumentBuilderFactory documentBuilderFactory;
	{
		documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setNamespaceAware(true);
	}

	private final TransformerFactory transformerFactory;
	{
		transformerFactory = TransformerFactory.newInstance();
	}

	@Override
	public Element marshal(String element) throws Exception {
		if (element == null) {
			return null;
		} else {
			final DocumentBuilder documentBuilder = documentBuilderFactory
					.newDocumentBuilder();

			final Document document = documentBuilder.parse(new InputSource(
					new StringReader(element)));
			return document.getDocumentElement();
		}
	}

	@Override
	public String unmarshal(Element element) throws Exception {
		if (element == null) {
			return null;
		} else {
			final Transformer transformer = transformerFactory.newTransformer();
			final StringWriter target = new StringWriter();
			transformer.transform(new DOMSource(element), new StreamResult(
					target));
			return target.toString();
		}
	}

}
