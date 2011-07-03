package org.jvnet.jaxb2_commons.plugin.jaxbindex;

import org.jvnet.jaxb2_commons.util.CodeModelUtils;
import org.xml.sax.ErrorHandler;

import com.sun.codemodel.fmt.JTextFile;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.Outline;
import com.sun.tools.xjc.outline.PackageOutline;

public class JaxbIndexPlugin extends com.sun.tools.xjc.Plugin {

	public String getOptionName() {
		return "Xjaxbindex";
	}

	public String getUsage() {
		return "-Xjaxbindex:	generate per-package jaxb.index file";
	}

	@Override
	public boolean run(Outline outline, Options opt, ErrorHandler errorHandler) {

		for (final PackageOutline packageOutline : outline
				.getAllPackageContexts()) {
			final StringBuilder sb = new StringBuilder();
			for (final ClassOutline classOutline : packageOutline.getClasses()) {
				sb.append(CodeModelUtils.getLocalClassName(classOutline.ref));
				sb.append("\n");
			}

			final JTextFile indexFile = new JTextFile("jaxb.index");
			indexFile.setContents(sb.toString());
			packageOutline._package().addResourceFile(indexFile);
		}
		return true;
	}
}
