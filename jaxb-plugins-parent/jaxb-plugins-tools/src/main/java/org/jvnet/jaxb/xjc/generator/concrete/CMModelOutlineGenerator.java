package org.jvnet.jaxb.xjc.generator.concrete;

import java.util.Objects;
import org.jvnet.jaxb.xjc.generator.MClassOutlineGenerator;
import org.jvnet.jaxb.xjc.generator.MElementOutlineGenerator;
import org.jvnet.jaxb.xjc.generator.MEnumOutlineGenerator;
import org.jvnet.jaxb.xjc.generator.MModelOutlineGenerator;
import org.jvnet.jaxb.xjc.generator.MPackageOutlineGenerator;
import org.jvnet.jaxb.xjc.outline.MClassOutline;
import org.jvnet.jaxb.xjc.outline.MElementOutline;
import org.jvnet.jaxb.xjc.outline.MEnumOutline;
import org.jvnet.jaxb.xjc.outline.MModelOutline;
import org.jvnet.jaxb.xjc.outline.MPackageOutline;
import org.jvnet.jaxb.xjc.outline.concrete.CMModelOutline;
import org.jvnet.jaxb.xml.bind.model.MClassInfo;
import org.jvnet.jaxb.xml.bind.model.MClassRef;
import org.jvnet.jaxb.xml.bind.model.MClassTypeInfoVisitor;
import org.jvnet.jaxb.xml.bind.model.MElementInfo;
import org.jvnet.jaxb.xml.bind.model.MEnumLeafInfo;
import org.jvnet.jaxb.xml.bind.model.MModelInfo;
import org.jvnet.jaxb.xml.bind.model.MPackageInfo;

import com.sun.tools.xjc.model.Model;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.outline.Outline;

public class CMModelOutlineGenerator implements MModelOutlineGenerator {

	private final Outline outline;
	private final Model model;

	public CMModelOutlineGenerator(Outline outline, Model model) {
		Objects.requireNonNull(outline, "Outline must not be null.");
		Objects.requireNonNull(model, "Model must not be null.");
		this.outline = outline;
		this.model = model;
	}

	public MModelOutline generate(MModelInfo<NType, NClass> modelInfo) {

		final CMModelOutline modelOutline = new CMModelOutline(modelInfo,
				outline.getCodeModel());

		for (MClassInfo<NType, NClass> classInfo : modelInfo.getClassInfos()) {
			generatePackageOutline(modelOutline, modelInfo,
					classInfo.getPackageInfo());
		}

		for (MElementInfo<NType, NClass> elementInfo : modelInfo
				.getElementInfos()) {
			generatePackageOutline(modelOutline, modelInfo,
					elementInfo.getPackageInfo());
		}

		for (MEnumLeafInfo<NType, NClass> enumLeafInfo : modelInfo
				.getEnumLeafInfos()) {
			generatePackageOutline(modelOutline, modelInfo,
					enumLeafInfo.getPackageInfo());
		}

		for (MClassInfo<NType, NClass> classInfo : modelInfo.getClassInfos()) {
			generateClassOutline(modelOutline, modelInfo, classInfo);
		}

		for (MElementInfo<NType, NClass> elementInfo : modelInfo
				.getElementInfos()) {
			generateElementOutline(modelOutline, modelInfo, elementInfo);
		}

		for (MEnumLeafInfo<NType, NClass> enumLeafInfo : modelInfo
				.getEnumLeafInfos()) {
			generateEnumOutline(modelOutline, modelInfo, enumLeafInfo);
		}

		// TODO Auto-generated method stub
		return modelOutline;
	}

	private void generatePackageOutline(CMModelOutline modelOutline,
			MModelInfo<NType, NClass> modelInfo, MPackageInfo packageInfo) {
		if (modelOutline.getPackageOutline(packageInfo) == null &&

		packageInfo.getOrigin() instanceof PackageOutlineGeneratorFactory) {
			final MPackageOutlineGenerator generator = ((PackageOutlineGeneratorFactory) packageInfo
					.getOrigin()).createGenerator(outline);
			final MPackageOutline packageOutline = generator.generate(
					modelOutline, modelInfo, packageInfo);
			modelOutline.addPackageOutline(packageOutline);

		}
	}

	private void generateClassOutline(final CMModelOutline modelOutline,
			final MModelInfo<NType, NClass> modelInfo,
			MClassInfo<NType, NClass> classInfo) {
		if (classInfo.getBaseTypeInfo() != null) {
			classInfo.getBaseTypeInfo().acceptClassTypeInfoVisitor(
					new MClassTypeInfoVisitor<NType, NClass, Void>() {
						@Override
						public Void visitClassInfo(
								MClassInfo<NType, NClass> info) {
							generateClassOutline(modelOutline, modelInfo, info);
							return null;
						}

						@Override
						public Void visitClassRef(MClassRef<NType, NClass> info) {
							return null;
						}
					});
		}

		if (classInfo.getOrigin() instanceof ClassOutlineGeneratorFactory) {
			final MClassOutlineGenerator generator = ((ClassOutlineGeneratorFactory) classInfo
					.getOrigin()).createGenerator(this.outline);
			final MClassOutline classOutline = generator.generate(
					modelOutline.getPackageOutline(classInfo.getPackageInfo()),
					modelInfo, classInfo);
			if (classOutline != null) {
				modelOutline.addClassOutline(classOutline);
			}
		}
	}

	private void generateElementOutline(CMModelOutline modelOutline,
			MModelInfo<NType, NClass> modelInfo,
			MElementInfo<NType, NClass> elementInfo)

	{
		if (elementInfo.getOrigin() instanceof ElementOutlineGeneratorFactory) {
			final MElementOutlineGenerator generator = ((ElementOutlineGeneratorFactory) elementInfo
					.getOrigin()).createGenerator(outline);
			final MElementOutline elementOutline = generator
					.generate(modelOutline.getPackageOutline(elementInfo
							.getPackageInfo()), modelInfo, elementInfo);
			if (elementOutline != null) {
				modelOutline.addElementOutline(elementOutline);
			}
		}
	}

	private void generateEnumOutline(CMModelOutline modelOutline,
			MModelInfo<NType, NClass> modelInfo,
			MEnumLeafInfo<NType, NClass> enumLeafInfo)

	{
		if (enumLeafInfo.getOrigin() instanceof EnumOutlineGeneratorFactory) {
			final MEnumOutlineGenerator generator = ((EnumOutlineGeneratorFactory) enumLeafInfo
					.getOrigin()).createGenerator(outline);
			final MEnumOutline enumOutline = generator.generate(modelOutline
					.getPackageOutline(enumLeafInfo.getPackageInfo()),
					modelInfo, enumLeafInfo);
			if (enumOutline != null) {
				modelOutline.addEnumOutline(enumOutline);
			}
		}
	}
}
