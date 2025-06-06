package org.jvnet.jaxb.xjc.generator.concrete;

import java.util.Objects;
import org.jvnet.jaxb.xjc.generator.MClassOutlineGenerator;
import org.jvnet.jaxb.xjc.generator.MPropertyOutlineGenerator;
import org.jvnet.jaxb.xjc.outline.MClassOutline;
import org.jvnet.jaxb.xjc.outline.MPackageOutline;
import org.jvnet.jaxb.xjc.outline.MPropertyOutline;
import org.jvnet.jaxb.xjc.outline.concrete.CMClassOutline;
import org.jvnet.jaxb.xml.bind.model.MClassInfo;
import org.jvnet.jaxb.xml.bind.model.MClassRef;
import org.jvnet.jaxb.xml.bind.model.MClassTypeInfoVisitor;
import org.jvnet.jaxb.xml.bind.model.MModelInfo;
import org.jvnet.jaxb.xml.bind.model.MPropertyInfo;

import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.Outline;

public class CMClassOutlineGenerator implements MClassOutlineGenerator {

	private final Outline outline;

	private final CClassInfo classInfo;

	public CMClassOutlineGenerator(Outline outline, CClassInfo classInfo) {
		Objects.requireNonNull(outline, "Outline must not be null.");
		Objects.requireNonNull(classInfo, "Class info must not be null.");
		this.outline = outline;
		this.classInfo = classInfo;
	}

	public MClassOutline generate(final MPackageOutline parent,
			MModelInfo<NType, NClass> modelInfo,
			MClassInfo<NType, NClass> classInfo) {

		ClassOutline co = outline.getClazz(this.classInfo);

		final MClassOutline superClassOutline;
		if (classInfo.getBaseTypeInfo() != null) {
			superClassOutline = classInfo
					.getBaseTypeInfo()
					.acceptClassTypeInfoVisitor(
							new MClassTypeInfoVisitor<NType, NClass, MClassOutline>() {
								@Override
								public MClassOutline visitClassInfo(
										MClassInfo<NType, NClass> info) {
									return parent.getParent().getClassOutline(
											info);
								}

								@Override
								public MClassOutline visitClassRef(
										MClassRef<NType, NClass> info) {
									return null;
								}
							});
		} else {
			superClassOutline = null;
		}

		final CMClassOutline classOutline = new CMClassOutline(
				parent.getParent(), parent, classInfo, superClassOutline,
				co.ref, co.implClass, co.implRef);

		for (MPropertyInfo<NType, NClass> propertyInfo : classInfo
				.getProperties()) {
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
