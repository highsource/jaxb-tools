package org.jvnet.jaxb2_commons.tests.JAXB_1058;

import java.io.File;

import org.junit.Test;

import com.sun.codemodel.JCodeModel;
import com.sun.tools.xjc.ConsoleErrorReporter;
import com.sun.tools.xjc.ModelLoader;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.Model;

public class ExecuteJAXB1058 {

	@Test
	public void compilesContext_V_1_1_0() throws Exception {

		new File("target/generated-sources/xjc").mkdirs();

		final String[] arguments = new String[] { "-xmlschema",
				new File("src/main/resources/schema.xsd").toURI().toString(),
				"-d",
				"target/generated-sources/xjc", 
				"-XfixJAXB1058",
				"-extension", "-XtoString",
				"-Xequals", "-XhashCode", "-Xcopyable", "-Xmergeable"};

		Options options = new Options();
		options.parseArguments(arguments);
		ConsoleErrorReporter receiver = new ConsoleErrorReporter();
		Model model = ModelLoader.load(options, new JCodeModel(), receiver);
		model.generateCode(options, receiver);
		com.sun.codemodel.CodeWriter cw = options.createCodeWriter();
		model.codeModel.build(cw);
	}

}
