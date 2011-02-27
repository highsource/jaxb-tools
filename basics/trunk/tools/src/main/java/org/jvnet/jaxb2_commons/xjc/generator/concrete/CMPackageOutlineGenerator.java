package org.jvnet.jaxb2_commons.xjc.generator.concrete;

import org.apache.commons.lang.Validate;
import org.jvnet.jaxb2_commons.xjc.generator.MPackageOutlineGenerator;
import org.jvnet.jaxb2_commons.xjc.outline.MModelOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MPackageOutline;
import org.jvnet.jaxb2_commons.xjc.outline.concrete.CMPackageOutline;
import org.jvnet.jaxb2_commons.xml.bind.model.MModelInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPackageInfo;

import com.sun.codemodel.JPackage;
import com.sun.tools.xjc.outline.Outline;
import com.sun.tools.xjc.outline.PackageOutline;

public class CMPackageOutlineGenerator implements MPackageOutlineGenerator {

	private final Outline outline;

	private final JPackage packageInfo;

	public CMPackageOutlineGenerator(Outline outline, JPackage packageInfo) {
		Validate.notNull(outline);
		Validate.notNull(packageInfo);
		this.outline = outline;
		this.packageInfo = packageInfo;
	}

	@Override
	public MPackageOutline generate(MModelOutline parent, MModelInfo modelInfo,
			MPackageInfo packageInfo) {
		final PackageOutline packageOutline = outline
				.getPackageContext(this.packageInfo);
		Validate.notNull(packageOutline);
		return new CMPackageOutline(parent, packageInfo, packageOutline);
	}

}
