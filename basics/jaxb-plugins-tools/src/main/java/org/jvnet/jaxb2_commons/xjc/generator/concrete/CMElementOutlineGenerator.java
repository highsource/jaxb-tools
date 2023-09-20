package org.jvnet.jaxb2_commons.xjc.generator.concrete;

import org.apache.commons.lang3.Validate;
import org.jvnet.jaxb2_commons.xjc.generator.MElementOutlineGenerator;
import org.jvnet.jaxb2_commons.xjc.outline.MElementOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MPackageOutline;
import org.jvnet.jaxb2_commons.xjc.outline.concrete.CMElementOutline;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MModelInfo;

import com.sun.tools.xjc.model.CElementInfo;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.outline.ElementOutline;
import com.sun.tools.xjc.outline.Outline;

public class CMElementOutlineGenerator implements MElementOutlineGenerator {

	private final Outline outline;
	private final CElementInfo elementInfo;

	public CMElementOutlineGenerator(Outline outline, CElementInfo elementInfo) {
		Validate.notNull(outline);
		Validate.notNull(elementInfo);
		this.outline = outline;
		this.elementInfo = elementInfo;
	}

	public MElementOutline generate(MPackageOutline parent,
			MModelInfo<NType, NClass> modelInfo,
			MElementInfo<NType, NClass> elementInfo) {
		final ElementOutline elementOutline = outline
				.getElement(this.elementInfo);
		if (elementOutline != null) {
			return new CMElementOutline(parent.getParent(), parent,
					elementInfo, elementOutline.implClass);
		} else {
			return null;
		}
	}

}
