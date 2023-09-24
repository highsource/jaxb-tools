package org.jvnet.jaxb.xjc.generator.concrete;

import org.apache.commons.lang3.Validate;
import org.jvnet.jaxb.xjc.generator.MEnumConstantOutlineGenerator;
import org.jvnet.jaxb.xjc.outline.MEnumConstantOutline;
import org.jvnet.jaxb.xjc.outline.MEnumOutline;
import org.jvnet.jaxb.xjc.outline.concrete.CMEnumConstantOutline;
import org.jvnet.jaxb.xml.bind.model.MEnumConstantInfo;
import org.jvnet.jaxb.xml.bind.model.MModelInfo;

import com.sun.tools.xjc.model.CEnumConstant;
import com.sun.tools.xjc.model.CEnumLeafInfo;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.outline.EnumConstantOutline;
import com.sun.tools.xjc.outline.EnumOutline;
import com.sun.tools.xjc.outline.Outline;

public class CMEnumConstantOutlineGenerator implements
		MEnumConstantOutlineGenerator {

	private final Outline outline;
	private final CEnumConstant enumConstant;

	public CMEnumConstantOutlineGenerator(Outline outline,
			CEnumConstant enumConstant) {
		Validate.notNull(outline);
		Validate.notNull(enumConstant);
		this.outline = outline;
		this.enumConstant = enumConstant;
	}

	public MEnumConstantOutline generate(MEnumOutline parent,
			MModelInfo<NType, NClass> modelInfo,
			MEnumConstantInfo<NType, NClass> enumConstantInfo) {

		final CEnumLeafInfo eli = enumConstant.getEnclosingClass();

		final EnumOutline enumOutline = outline.getEnum(eli);

		for (EnumConstantOutline enumConstantOutline : enumOutline.constants) {
			if (enumConstantOutline.target == enumConstant) {
				return new CMEnumConstantOutline(parent, enumConstantInfo,
						enumConstantOutline.constRef);
			}
		}
		return null;
	}

}
