package org.jvnet.jaxb2_commons.xjc.model.concrete.tests.alpha;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.xml.namespace.QName;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.jvnet.jaxb2_commons.xjc.model.concrete.XJCCMInfoFactory;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MModelInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xml.namespace.util.QNameUtils;
import org.jvnet.jaxb2_commons.xmlschema.XmlSchemaConstants;

import com.sun.codemodel.JCodeModel;
import com.sun.tools.xjc.BadCommandLineException;
import com.sun.tools.xjc.ConsoleErrorReporter;
import com.sun.tools.xjc.ModelLoader;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.Model;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;

public class AlphaMInfoFactoryTest {

	private static MModelInfo<NType, NClass> MODEL_INFO;

	@BeforeClass
	public static void createModel() throws BadCommandLineException,
			IOException {
		final String generateDirectory = "target/generated-sources/"
				+ AlphaMInfoFactoryTest.class.getPackage().getName();
		new File(generateDirectory).mkdirs();
		final URL schema = AlphaMInfoFactoryTest.class
				.getResource("schema.xsd");
		final URL binding = AlphaMInfoFactoryTest.class
				.getResource("binding.xjb");
		final String[] arguments = new String[] { "-xmlschema",
				schema.toExternalForm(), "-b", binding.toExternalForm(), "-d",
				generateDirectory, "-extension" };

		Options options = new Options();
		options.parseArguments(arguments);
		ConsoleErrorReporter receiver = new ConsoleErrorReporter();
		Model model = ModelLoader.load(options, new JCodeModel(), receiver);
		Assert.assertNotNull(model);

		final XJCCMInfoFactory factory = new XJCCMInfoFactory(model);

		AlphaMInfoFactoryTest.MODEL_INFO = factory.createModel();

		model.generateCode(options, receiver);
		com.sun.codemodel.CodeWriter cw = options.createCodeWriter();
		model.codeModel.build(cw);

	}

	@Test
	public void successfullyLoadsModel() {
		Assert.assertNotNull(MODEL_INFO);
	}

	@Test
	public void createsCorrectTypeNameForNamedComplexType() {
		final MClassInfo<NType, NClass> classInfo = MODEL_INFO
				.getClassInfo(getClass().getPackage().getName() + "."
						+ "NamedComplexType");
		Assert.assertEquals(new QName("urn:test", "NamedComplexType"),
				classInfo.getTypeName());
	}

	@Test
	public void createsCorrectTypeNameForUnnamedComplexType() {
		final MElementInfo<NType, NClass> elementInfo = MODEL_INFO
				.getGlobalElementInfo(new QName("urn:test",
						"UnnamedComplexTypeElement"));
		Assert.assertNotNull(elementInfo);
		Assert.assertNull(elementInfo.getTypeInfo().getTypeName());
	}

	@Test
	public void createsCorrectTypeNameForNamedSimpleType() {
		final MElementInfo<NType, NClass> elementInfo = MODEL_INFO
				.getGlobalElementInfo(new QName("urn:test",
						"NamedSimpleTypeElement"));
		Assert.assertNotNull(elementInfo);
		Assert.assertEquals(new QName("urn:test", "NamedSimpleType"),
				elementInfo.getTypeInfo().getTypeName());
	}

	@Test
	public void createsCorrectTypeNameForUnnamedSimpleType() {
		final MElementInfo<NType, NClass> elementInfo = MODEL_INFO
				.getGlobalElementInfo(new QName("urn:test",
						"UnnamedSimpleTypeElement"));
		Assert.assertNotNull(elementInfo);
		Assert.assertNull(elementInfo.getTypeInfo().getTypeName());
	}

	@Test
	public void createsCorrectTypeNameForStringElementType() {
		final MElementInfo<NType, NClass> elementInfo = MODEL_INFO
				.getGlobalElementInfo(new QName("urn:test", "StringElement"));
		Assert.assertNotNull(elementInfo);
		Assert.assertEquals(XmlSchemaConstants.STRING, elementInfo
				.getTypeInfo().getTypeName());
	}

	@Test
	public void createsCorrectTypeNameForKnownReferencedType() {
		final QName typeName = new QName("urn:test", "KnownReferencedType",
				"test");
		final MElementInfo<NType, NClass> elementInfo = MODEL_INFO
				.getGlobalElementInfo(new QName("urn:test",
						"KnownReferencedTypeElement"));
		final MTypeInfo<NType, NClass> typeInfo = elementInfo.getTypeInfo();
		Assert.assertNotNull(typeInfo);
		Assert.assertEquals(QNameUtils.getKey(typeName),
				QNameUtils.getKey(typeInfo.getTypeName()));
	}

	@Test
	public void createsCorrectTypeNameForUnnownReferencedType() {
		final MElementInfo<NType, NClass> elementInfo = MODEL_INFO
				.getGlobalElementInfo(new QName("urn:test",
						"UnknownReferencedTypeElement"));
		final MTypeInfo<NType, NClass> typeInfo = elementInfo.getTypeInfo();
		Assert.assertNotNull(typeInfo);
		Assert.assertNull(typeInfo.getTypeName());
	}
}
