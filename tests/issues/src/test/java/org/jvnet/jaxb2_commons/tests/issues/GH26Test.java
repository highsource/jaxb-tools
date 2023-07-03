package org.jvnet.jaxb2_commons.tests.issues;

import java.io.File;
import java.net.URL;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.jvnet.jaxb2_commons.xjc.model.concrete.XJCCMInfoFactory;
import org.jvnet.jaxb2_commons.xml.bind.model.MAttributePropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementRefPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementRefsPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementsPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MModelInfo;

import com.sun.codemodel.JCodeModel;
import com.sun.tools.xjc.ConsoleErrorReporter;
import com.sun.tools.xjc.ModelLoader;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.Model;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;

public class GH26Test {

	@Before
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
				.getClassInfo("org.jvnet.jaxb2_commons.tests.issues.IssueGH26Type");
		Assert.assertNotNull(classInfo);

		final MElementPropertyInfo<NType, NClass> a = (MElementPropertyInfo<NType, NClass>) classInfo
				.getProperty("a");
		Assert.assertEquals("a", a.getDefaultValue());
		Assert.assertNotNull(a.getDefaultValueNamespaceContext());
		final MElementsPropertyInfo<NType, NClass> bOrC = (MElementsPropertyInfo<NType, NClass>) classInfo
				.getProperty("bOrC");
		
//		Assert.assertEquals("b", bOrC.getElementTypeInfos().get(0).getDefaultValue());
		Assert.assertNotNull(bOrC.getElementTypeInfos().get(0).getDefaultValueNamespaceContext());
		
//		Assert.assertEquals("3", bOrC.getElementTypeInfos().get(1).getDefaultValue());
		Assert.assertNotNull(bOrC.getElementTypeInfos().get(1).getDefaultValueNamespaceContext());

		final MElementRefsPropertyInfo<NType, NClass> dOrE = (MElementRefsPropertyInfo<NType, NClass>) classInfo
				.getProperty("dOrE");
//		Assert.assertEquals("e", dOrE.getElementTypeInfos().get(0).getDefaultValue());
		Assert.assertNotNull(dOrE.getElementTypeInfos().get(0).getDefaultValueNamespaceContext());
		
//		Assert.assertEquals("d", dOrE.getElementTypeInfos().get(1).getDefaultValue());
		Assert.assertNotNull(dOrE.getElementTypeInfos().get(1).getDefaultValueNamespaceContext());

		final MAttributePropertyInfo<NType, NClass> z = (MAttributePropertyInfo<NType, NClass>) classInfo
				.getProperty("z");
		Assert.assertEquals("z", z.getDefaultValue());
		Assert.assertNotNull(z.getDefaultValueNamespaceContext());
		// model.generateCode(options, receiver);
		// com.sun.codemodel.CodeWriter cw = options.createCodeWriter();
		// model.codeModel.build(cw);
	}
}
