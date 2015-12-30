package org.jvnet.jaxb2_commons.tests.issues.tests;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.sun.codemodel.JCodeModel;
import com.sun.tools.xjc.ConsoleErrorReporter;
import com.sun.tools.xjc.ModelLoader;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.Model;

public class RunPlugins {

	@Before
	public void setUp() {
		System.setProperty("javax.xml.accessExternalSchema", "all");
	}

	@Test
	public void compilesSchema() throws Exception {

		new File("target/generated-sources/xjc").mkdirs();

		String schemaDirectory = new File(getClass().getResource("/schema.xsd")
				.toURI()).getParentFile().getAbsolutePath();
		String bindingDirectory = new File(getClass().getResource(
				"/binding.xjb").toURI()).getParentFile().getAbsolutePath();
		final String[] arguments = new String[] { "-xmlschema",
				schemaDirectory, "-b", bindingDirectory, "-d",
				"target/generated-sources/xjc", "-extension", "-Xannotate"

		};

		Options options = new Options();
		options.parseArguments(arguments);
		ConsoleErrorReporter receiver = new ConsoleErrorReporter();
		Model model = ModelLoader.load(options, new JCodeModel(), receiver);
		model.generateCode(options, receiver);
		com.sun.codemodel.CodeWriter cw = options.createCodeWriter();
		model.codeModel.build(cw);
	}
}
