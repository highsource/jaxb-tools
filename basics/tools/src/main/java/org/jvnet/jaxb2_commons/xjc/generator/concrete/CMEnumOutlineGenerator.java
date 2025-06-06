package org.jvnet.jaxb2_commons.xjc.generator.concrete;

import java.util.Objects;
import org.jvnet.jaxb2_commons.xjc.generator.MEnumConstantOutlineGenerator;
import org.jvnet.jaxb2_commons.xjc.generator.MEnumOutlineGenerator;
import org.jvnet.jaxb2_commons.xjc.outline.MEnumConstantOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MEnumOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MPackageOutline;
import org.jvnet.jaxb2_commons.xjc.outline.concrete.CMEnumOutline;
import org.jvnet.jaxb2_commons.xml.bind.model.MEnumConstantInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MEnumLeafInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MModelInfo;

import com.sun.tools.xjc.model.CEnumLeafInfo;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.outline.EnumOutline;
import com.sun.tools.xjc.outline.Outline;

public class CMEnumOutlineGenerator implements MEnumOutlineGenerator {

	private final Outline outline;
	private final CEnumLeafInfo enumLeafInfo;

	public CMEnumOutlineGenerator(Outline outline, CEnumLeafInfo enumLeafInfo) {
		Objects.requireNonNull(outline, "Outline must not be null.");
		Objects.requireNonNull(enumLeafInfo, "Enum leaf info must not be null.");
		this.outline = outline;
		this.enumLeafInfo = enumLeafInfo;
	}

	public MEnumOutline generate(MPackageOutline parent,
			MModelInfo<NType, NClass> modelInfo,
			MEnumLeafInfo<NType, NClass> enumLeafInfo) {
		final EnumOutline eo = outline.getEnum(this.enumLeafInfo);

		final CMEnumOutline enumOutline = new CMEnumOutline(parent.getParent(),
				parent, enumLeafInfo, eo.clazz);

		for (MEnumConstantInfo<NType, NClass> enumConstantInfo : enumLeafInfo
				.getConstants()) {

			if (enumConstantInfo.getOrigin() instanceof EnumConstantOutlineGeneratorFactory) {
				final MEnumConstantOutlineGenerator generator = ((EnumConstantOutlineGeneratorFactory) enumConstantInfo
						.getOrigin()).createGenerator(outline);
				final MEnumConstantOutline enumConstantOutline = generator
						.generate(enumOutline, modelInfo, enumConstantInfo);
				if (enumConstantOutline != null) {
					enumOutline.addEnumConstantOutline(enumConstantOutline);
				}
			}
		}
		return enumOutline;
	}

}
