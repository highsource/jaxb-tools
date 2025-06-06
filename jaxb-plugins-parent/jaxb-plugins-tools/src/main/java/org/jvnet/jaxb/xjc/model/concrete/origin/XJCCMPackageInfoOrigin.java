package org.jvnet.jaxb.xjc.model.concrete.origin;

import java.util.Objects;
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
		Objects.requireNonNull(source, "Source package must not be null.");
		this.source = source;
	}

	public JPackage getSource() {
		return source;
	}

	public MPackageOutlineGenerator createGenerator(Outline outline) {
		return new CMPackageOutlineGenerator(outline, getSource());
	}

}
