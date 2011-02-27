package org.jvnet.jaxb2_commons.xjc.generator.concrete;

import org.apache.commons.lang.Validate;
import org.jvnet.jaxb2_commons.xjc.generator.MClassOutlineGenerator;
import org.jvnet.jaxb2_commons.xjc.generator.MPropertyOutlineGenerator;
import org.jvnet.jaxb2_commons.xjc.outline.MClassOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MPackageOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MPropertyOutline;
import org.jvnet.jaxb2_commons.xjc.outline.concrete.CMClassOutline;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MModelInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfo;

import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.Outline;

public class CMClassOutlineGenerator implements MClassOutlineGenerator {

	private final Outline outline;

	private final CClassInfo classInfo;

	public CMClassOutlineGenerator(Outline outline, CClassInfo classInfo) {
		Validate.notNull(outline);
		Validate.notNull(classInfo);
		this.outline = outline;
		this.classInfo = classInfo;
	}

	@Override
	public MClassOutline generate(MPackageOutline parent, MModelInfo modelInfo,
			MClassInfo classInfo) {

		ClassOutline co = outline.getClazz(this.classInfo);

		final MClassOutline superClassOutline;
		if (classInfo.getBaseTypeInfo() != null) {
			superClassOutline = parent.getParent().getClassOutline(
					classInfo.getBaseTypeInfo());
		} else {
			superClassOutline = null;
		}

		final CMClassOutline classOutline = new CMClassOutline(
				parent.getParent(), parent, classInfo, superClassOutline,
				co.ref, co.implClass, co.implRef);

		for (MPropertyInfo propertyInfo : classInfo.getProperties()) {
			if (propertyInfo.getOrigin() instanceof PropertyOutlineGeneratorFactory) {
				final MPropertyOutlineGenerator generator = ((PropertyOutlineGeneratorFactory) propertyInfo
						.getOrigin()).createGenerator(outline);
				final MPropertyOutline propertyOutline = generator.generate(
						classOutline, modelInfo, propertyInfo);
				if (propertyOutline != null) {
					classOutline.addDeclaredPropertyOutline(propertyOutline);
				}
			}
		}
		return classOutline;
	}

}
