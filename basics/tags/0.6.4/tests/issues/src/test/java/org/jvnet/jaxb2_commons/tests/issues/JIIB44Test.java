package org.jvnet.jaxb2_commons.tests.issues;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;

import junit.framework.Assert;
import junit.framework.TestCase;

public class JIIB44Test extends TestCase {

	private JAXBContext context;

	@Override
	protected void setUp() throws Exception {
		context = JAXBContext.newInstance(getClass().getPackage().getName());
	}

	public void testReferencesPropertyAsElementPropertyType() throws Exception {

		IssueJIIB44ReferencesPropertyAsElementPropertyType value = ((JAXBElement<IssueJIIB44ReferencesPropertyAsElementPropertyType>) context
				.createUnmarshaller().unmarshal(
						getClass().getResourceAsStream("issueJIIB44_0.xml")))
				.getValue();

		Assert.assertEquals(3, value.getIssueJIIB44DummyElementInfo().size());
		Assert.assertEquals(3, value.getIssueJIIB44DummyClassInfo().size());
	}

	public void testReferencesPropertyAsReferencePropertyType()
			throws Exception {

		IssueJIIB44ReferencesPropertyAsReferencePropertyType value = ((JAXBElement<IssueJIIB44ReferencesPropertyAsReferencePropertyType>) context
				.createUnmarshaller().unmarshal(
						getClass().getResourceAsStream("issueJIIB44_1.xml")))
				.getValue();

		Assert.assertEquals(3, value.getIssueJIIB44DummyElementInfo().size());
		Assert.assertEquals(3, value.getIssueJIIB44DummyClassInfo().size());
	}

	public void testElementsPropertyAsElementPropertyType() throws Exception {

		IssueJIIB44ElementsPropertyAsElementPropertyType value = ((JAXBElement<IssueJIIB44ElementsPropertyAsElementPropertyType>) context
				.createUnmarshaller().unmarshal(
						getClass().getResourceAsStream("issueJIIB44_2.xml")))
				.getValue();

		Assert.assertEquals(3, value.getString().size());
		Assert.assertEquals(3, value.getInt().size());
	}
}
