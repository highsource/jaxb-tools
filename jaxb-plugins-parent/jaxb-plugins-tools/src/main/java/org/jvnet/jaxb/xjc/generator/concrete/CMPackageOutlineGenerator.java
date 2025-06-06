package org.jvnet.jaxb.xjc.generator.concrete;

import java.util.Objects;
import org.jvnet.jaxb.xjc.generator.MPackageOutlineGenerator;
import org.jvnet.jaxb.xjc.outline.MModelOutline;
import org.jvnet.jaxb.xjc.outline.MPackageOutline;
import org.jvnet.jaxb.xjc.outline.concrete.CMPackageOutline;
import org.jvnet.jaxb.xml.bind.model.MModelInfo;
import org.jvnet.jaxb.xml.bind.model.MPackageInfo;

import com.sun.codemodel.JPackage;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.outline.Outline;
import com.sun.tools.xjc.outline.PackageOutline;

public class CMPackageOutlineGenerator implements MPackageOutlineGenerator {

	private final Outline outline;

	private final JPackage packageInfo;

	public CMPackageOutlineGenerator(Outline outline, JPackage packageInfo) {
		Objects.requireNonNull(outline, "Outline must not be null.");
		Objects.requireNonNull(packageInfo, "Package info must not be null.");
		this.outline = outline;
		this.packageInfo = packageInfo;
	}

	public MPackageOutline generate(MModelOutline parent,
			MModelInfo<NType, NClass> modelInfo, MPackageInfo packageInfo) {
		final PackageOutline packageOutline = outline
				.getPackageContext(this.packageInfo);
		Objects.requireNonNull(packageOutline, "Package outline must not be null.");
		return new CMPackageOutline(parent, packageInfo, packageOutline);
	}

}
