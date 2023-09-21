package org.jvnet.jaxb.xjc.model.concrete.origin;

import org.apache.commons.lang3.Validate;
import org.jvnet.jaxb.xjc.generator.MPackageOutlineGenerator;
import org.jvnet.jaxb.xjc.generator.concrete.CMPackageOutlineGenerator;
import org.jvnet.jaxb.xjc.generator.concrete.PackageOutlineGeneratorFactory;
import org.jvnet.jaxb.xml.bind.model.concrete.origin.CMPackageInfoOrigin;

import com.sun.codemodel.JPackage;
import com.sun.tools.xjc.outline.Outline;

public class XJCCMPackageInfoOrigin extends CMPackageInfoOrigin implements
		PackageInfoOrigin, PackageOutlineGeneratorFactory {

	private final JPackage source;

	public XJCCMPackageInfoOrigin(JPackage source) {
		Validate.notNull(source);
		this.source = source;
	}

	public JPackage getSource() {
		return source;
	}

	public MPackageOutlineGenerator createGenerator(Outline outline) {
		return new CMPackageOutlineGenerator(outline, getSource());
	}

}
