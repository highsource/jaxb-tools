package org.jvnet.jaxb.tests.issues;

import java.io.File;
import java.net.URL;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jvnet.jaxb.xjc.model.concrete.XJCCMInfoFactory;
import org.jvnet.jaxb.xml.bind.model.MAttributePropertyInfo;
import org.jvnet.jaxb.xml.bind.model.MClassInfo;
import org.jvnet.jaxb.xml.bind.model.MElementPropertyInfo;
import org.jvnet.jaxb.xml.bind.model.MElementRefsPropertyInfo;
import org.jvnet.jaxb.xml.bind.model.MElementsPropertyInfo;
import org.jvnet.jaxb.xml.bind.model.MModelInfo;

import com.sun.codemodel.JCodeModel;
import com.sun.tools.xjc.ConsoleErrorReporter;
import com.sun.tools.xjc.ModelLoader;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.Model;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;

public class GH26Test {

	@BeforeEach
	public void setUp() {
		System.setProperty("javax.xml.accessExternalSchema", "all");
	}

	@Test
	public void compilesSchema() throws Exception {

		new File("target/generated-sources/xjc").mkdirs();

		URL schema = getClass().getResource("/schema.xsd");
		URL binding = getClass().getResource("/binding.xjb");
		final String[] arguments = new String[] { "-xmlschema",
				schema.toExternalForm(), "-b", binding.toExternalForm(), "-d",
				"target/generated-sources/xjc", "-extension", "-XhashCode",
				"-Xequals", "-XtoString", "-Xcopyable", "-Xmergeable",
				"-Xinheritance", "-Xsetters", "-Xsetters-mode=direct",
				"-Xwildcard", "-XenumValue"
		// "-XsimpleToString"

		};

		Options options = new Options();
		options.parseArguments(arguments);
		ConsoleErrorReporter receiver = new ConsoleErrorReporter();
		Model model = ModelLoader.load(options, new JCodeModel(), receiver);
		final XJCCMInfoFactory factory = new XJCCMInfoFactory(model);
		final MModelInfo<NType, NClass> mmodel = factory.createModel();

		final MClassInfo<NType, NClass> classInfo = mmodel
				.getClassInfo("org.jvnet.jaxb.tests.issues.IssueGH26Type");
		Assertions.assertNotNull(classInfo);

		final MElementPropertyInfo<NType, NClass> a = (MElementPropertyInfo<NType, NClass>) classInfo
				.getProperty("a");
		Assertions.assertEquals("a", a.getDefaultValue());
		Assertions.assertNotNull(a.getDefaultValueNamespaceContext());
		final MElementsPropertyInfo<NType, NClass> bOrC = (MElementsPropertyInfo<NType, NClass>) classInfo
				.getProperty("bOrC");

//		Assertions.assertEquals("b", bOrC.getElementTypeInfos().get(0).getDefaultValue());
		Assertions.assertNotNull(bOrC.getElementTypeInfos().get(0).getDefaultValueNamespaceContext());

//		Assertions.assertEquals("3", bOrC.getElementTypeInfos().get(1).getDefaultValue());
		Assertions.assertNotNull(bOrC.getElementTypeInfos().get(1).getDefaultValueNamespaceContext());

		final MElementRefsPropertyInfo<NType, NClass> dOrE = (MElementRefsPropertyInfo<NType, NClass>) classInfo
				.getProperty("dOrE");
//		Assertions.assertEquals("e", dOrE.getElementTypeInfos().get(0).getDefaultValue());
		Assertions.assertNotNull(dOrE.getElementTypeInfos().get(0).getDefaultValueNamespaceContext());

//		Assertions.assertEquals("d", dOrE.getElementTypeInfos().get(1).getDefaultValue());
		Assertions.assertNotNull(dOrE.getElementTypeInfos().get(1).getDefaultValueNamespaceContext());

		final MAttributePropertyInfo<NType, NClass> z = (MAttributePropertyInfo<NType, NClass>) classInfo
				.getProperty("z");
		Assertions.assertEquals("z", z.getDefaultValue());
		Assertions.assertNotNull(z.getDefaultValueNamespaceContext());
		// model.generateCode(options, receiver);
		// com.sun.codemodel.CodeWriter cw = options.createCodeWriter();
		// model.codeModel.build(cw);
	}
}
