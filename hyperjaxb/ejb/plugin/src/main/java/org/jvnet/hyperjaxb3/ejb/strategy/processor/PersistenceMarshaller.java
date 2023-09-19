package org.jvnet.hyperjaxb3.ejb.strategy.processor;

import java.io.StringWriter;
import java.io.Writer;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import org.jvnet.hyperjaxb3.persistence.jpa3.JPA3Utils;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JPackage;
import com.sun.codemodel.fmt.JTextFile;
import jakarta.xml.ns.persistence.Persistence;

public class PersistenceMarshaller {

	protected Marshaller getMarshaller() throws JAXBException {
		return JPA3Utils.createMarshaller();
	}

	public void marshallPersistence(JCodeModel codeModel,
			Persistence persistence) throws Exception {

		// final JPackage defaultPackage = codeModel._package("");
		final JPackage metaInfPackage = codeModel._package("META-INF");

		final JTextFile persistenceXmlFile = new JTextFile("persistence.xml");

		metaInfPackage.addResourceFile(persistenceXmlFile);

		final Writer writer = new StringWriter();
		getMarshaller().marshal(persistence, writer);
		persistenceXmlFile.setContents(writer.toString());

	}
}
