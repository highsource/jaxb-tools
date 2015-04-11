package org.jvnet.jaxb2_commons.plugin.elementwrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.plugin.CustomizedIgnoring;
import org.jvnet.jaxb2_commons.plugin.Ignoring;
import org.jvnet.jaxb2_commons.plugin.model.AbstractModelPlugin;
import org.jvnet.jaxb2_commons.xjc.model.concrete.origin.DummyPropertyInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementRefPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementRefsPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementsPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MModelInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.CMElementPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.CMElementRefPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.CMElementRefsPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.CMElementsPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.util.DefaultPropertyInfoVisitor;
import org.jvnet.jaxb2_commons.xml.bind.model.util.DefaultTypeInfoVisitor;
import org.xml.sax.ErrorHandler;

import com.sun.tools.xjc.model.Model;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;

public class ElementWrapperPlugin extends AbstractModelPlugin {

	@Override
	public String getOptionName() {
		return "XelementWrapper";
	}

	@Override
	public String getUsage() {
		return "Generates @XmlElementWrapper annotations.";
	}

	@Override
	protected void postProcessModel(Model model,
			final MModelInfo<NType, NClass> mmodel, ErrorHandler errorHandler) {

		final Collection<MClassInfo<NType, NClass>> classInfos = new ArrayList<MClassInfo<NType, NClass>>(
				mmodel.getClassInfos());
		for (final MClassInfo<NType, NClass> rootClassInfo : classInfos) {
			if (/*
				 * TODO !getIgnoring().isIgnored(classInfo) &&
				 */true) {
				List<MPropertyInfo<NType, NClass>> properties = new ArrayList<MPropertyInfo<NType, NClass>>(
						rootClassInfo.getProperties());
				for (MPropertyInfo<NType, NClass> propertyInfo0 : properties) {

					propertyInfo0
							.acceptPropertyInfoVisitor(new DefaultPropertyInfoVisitor<NType, NClass, Void>() {

								@Override
								public Void visitElementPropertyInfo(
										final MElementPropertyInfo<NType, NClass> wrapperPropertyInfo) {

									processWrapperElementPropertyInfo(mmodel,
											rootClassInfo, wrapperPropertyInfo);
									return null;

								}
							});
				}
			}
		}

	}

	private Ignoring ignoring = new CustomizedIgnoring(
			org.jvnet.jaxb2_commons.plugin.elementwrapper.Customizations.IGNORED_ELEMENT_NAME);

	public Ignoring getIgnoring() {
		return ignoring;
	}

	public void setIgnoring(Ignoring ignoring) {
		this.ignoring = ignoring;
	}

	@Override
	public Collection<QName> getCustomizationElementNames() {
		return Arrays
				.asList(org.jvnet.jaxb2_commons.plugin.elementwrapper.Customizations.IGNORED_ELEMENT_NAME);
	}

	protected void processWrapperElementPropertyInfo(
			final MModelInfo<NType, NClass> mmodel,
			final MClassInfo<NType, NClass> rootClassInfo,
			final MElementPropertyInfo<NType, NClass> wrapperPropertyInfo) {
		wrapperPropertyInfo.getTypeInfo().acceptTypeInfoVisitor(
				new DefaultTypeInfoVisitor<NType, NClass, Void>() {
					@Override
					public Void visitClassInfo(
							final MClassInfo<NType, NClass> wrapperClassInfo) {
						if (/*
							 * TODO !getIgnoring ( ).isIgnored ( classInfo ) &&
							 */wrapperClassInfo.getProperties().size() == 1) {
							final MPropertyInfo<NType, NClass> wrappedPropertyInfo = wrapperClassInfo
									.getProperties().get(0);

							if (wrappedPropertyInfo.isCollection()) {
								processWrappedPropertyInfo(mmodel,
										rootClassInfo, wrapperPropertyInfo,
										wrapperClassInfo, wrappedPropertyInfo);
							}
						}
						return null;
					}
				});
	}

	protected void processWrappedPropertyInfo(
			final MModelInfo<NType, NClass> mmodel,
			final MClassInfo<NType, NClass> rootClassInfo,
			final MElementPropertyInfo<NType, NClass> wrapperPropertyInfo,
			final MClassInfo<NType, NClass> wrapperClassInfo,
			final MPropertyInfo<NType, NClass> wrappedPropertyInfo) {
		wrappedPropertyInfo
				.acceptPropertyInfoVisitor(new DefaultPropertyInfoVisitor<NType, NClass, Void>() {

					@Override
					public Void visitElementPropertyInfo(
							final MElementPropertyInfo<NType, NClass> wrappedPropertyInfo) {
						processWrappedElementPropertyInfo(mmodel,
								rootClassInfo, wrapperPropertyInfo,
								wrapperClassInfo, wrappedPropertyInfo);
						return null;
					}

					@Override
					public Void visitElementsPropertyInfo(
							MElementsPropertyInfo<NType, NClass> wrappedPropertyInfo) {
						processWrappedElementsPropertyInfo(mmodel,
								rootClassInfo, wrapperPropertyInfo,
								wrapperClassInfo, wrappedPropertyInfo);
						return null;
					}

					@Override
					public Void visitElementRefPropertyInfo(
							final MElementRefPropertyInfo<NType, NClass> wrappedPropertyInfo) {
						processWrappedElementRefPropertyInfo(mmodel,
								rootClassInfo, wrapperPropertyInfo,
								wrapperClassInfo, wrappedPropertyInfo);
						return null;
					}

					@Override
					public Void visitElementRefsPropertyInfo(
							MElementRefsPropertyInfo<NType, NClass> wrappedPropertyInfo) {
						processWrappedElementRefsPropertyInfo(mmodel,
								rootClassInfo, wrapperPropertyInfo,
								wrapperClassInfo, wrappedPropertyInfo);
						return null;
					}
				});
	}

	protected void processWrappedElementPropertyInfo(
			final MModelInfo<NType, NClass> mmodel,
			final MClassInfo<NType, NClass> rootClassInfo,
			final MElementPropertyInfo<NType, NClass> wrapperPropertyInfo,
			final MClassInfo<NType, NClass> wrapperClassInfo,
			final MElementPropertyInfo<NType, NClass> wrappedPropertyInfo) {
		System.out.println("Class info:" + rootClassInfo.getName());
		System.out.println("Wrapper property info:"
				+ wrapperPropertyInfo.getPrivateName());
		System.out.println("Wrapper class info :" + wrapperClassInfo.getName());
		System.out.println("Wrapped property info:"
				+ wrappedPropertyInfo.getPrivateName());

		final MPropertyInfo<NType, NClass> propertyInfo = new CMElementPropertyInfo<NType, NClass>(
				new DummyPropertyInfoOrigin(), wrapperClassInfo,
				wrapperPropertyInfo.getPrivateName(),
				wrappedPropertyInfo.isCollection(),
				wrappedPropertyInfo.getTypeInfo(),
				wrappedPropertyInfo.getElementName(),
				wrapperPropertyInfo.getWrapperElementName(),
				wrappedPropertyInfo.isNillable(),
				wrappedPropertyInfo.getDefaultValue());

		rootClassInfo.addProperty(propertyInfo);

		// TODO
		rootClassInfo.removeProperty(wrapperPropertyInfo);
		mmodel.removeClassInfo(wrapperClassInfo);
	}

	protected void processWrappedElementsPropertyInfo(
			final MModelInfo<NType, NClass> mmodel,
			final MClassInfo<NType, NClass> rootClassInfo,
			final MElementPropertyInfo<NType, NClass> wrapperPropertyInfo,
			final MClassInfo<NType, NClass> wrapperClassInfo,
			final MElementsPropertyInfo<NType, NClass> wrappedPropertyInfo) {
		System.out.println("Class info:" + rootClassInfo.getName());
		System.out.println("Wrapper property info:"
				+ wrapperPropertyInfo.getPrivateName());
		System.out.println("Wrapper class info :" + wrapperClassInfo.getName());
		System.out.println("Wrapped property info:"
				+ wrappedPropertyInfo.getPrivateName());

		final MPropertyInfo<NType, NClass> propertyInfo = new CMElementsPropertyInfo<NType, NClass>(
				new DummyPropertyInfoOrigin(), wrapperClassInfo,
				wrapperPropertyInfo.getPrivateName(),
				wrappedPropertyInfo.isCollection(),

				wrappedPropertyInfo.getElementTypeInfos(),
				wrapperPropertyInfo.getElementName());

		rootClassInfo.addProperty(propertyInfo);

		// TODO
		rootClassInfo.removeProperty(wrapperPropertyInfo);
		mmodel.removeClassInfo(wrapperClassInfo);
	}

	protected void processWrappedElementRefPropertyInfo(
			final MModelInfo<NType, NClass> mmodel,
			final MClassInfo<NType, NClass> rootClassInfo,
			final MElementPropertyInfo<NType, NClass> wrapperPropertyInfo,
			final MClassInfo<NType, NClass> wrapperClassInfo,
			final MElementRefPropertyInfo<NType, NClass> wrappedPropertyInfo) {
		System.out.println("Class info:" + rootClassInfo.getName());
		System.out.println("Wrapper property info:"
				+ wrapperPropertyInfo.getPrivateName());
		System.out.println("Wrapper class info :" + wrapperClassInfo.getName());
		System.out.println("Wrapped property info:"
				+ wrappedPropertyInfo.getPrivateName());

		final MPropertyInfo<NType, NClass> propertyInfo = new CMElementRefPropertyInfo<NType, NClass>(
				new DummyPropertyInfoOrigin(), wrapperClassInfo,
				wrapperPropertyInfo.getPrivateName(),
				wrappedPropertyInfo.isCollection(),
				wrappedPropertyInfo.getTypeInfo(),
				wrappedPropertyInfo.getElementName(),
				wrapperPropertyInfo.getElementName(),
				wrappedPropertyInfo.isMixed(),
				wrappedPropertyInfo.isDomAllowed(),
				wrappedPropertyInfo.isTypedObjectAllowed(),
				wrappedPropertyInfo.getDefaultValue());

		rootClassInfo.addProperty(propertyInfo);

		// TODO
		rootClassInfo.removeProperty(wrapperPropertyInfo);
		mmodel.removeClassInfo(wrapperClassInfo);
	}

	protected void processWrappedElementRefsPropertyInfo(
			final MModelInfo<NType, NClass> mmodel,
			final MClassInfo<NType, NClass> rootClassInfo,
			final MElementPropertyInfo<NType, NClass> wrapperPropertyInfo,
			final MClassInfo<NType, NClass> wrapperClassInfo,
			final MElementRefsPropertyInfo<NType, NClass> wrappedPropertyInfo) {
		System.out.println("Class info:" + rootClassInfo.getName());
		System.out.println("Wrapper property info:"
				+ wrapperPropertyInfo.getPrivateName());
		System.out.println("Wrapper class info :" + wrapperClassInfo.getName());
		System.out.println("Wrapped property info:"
				+ wrappedPropertyInfo.getPrivateName());

		final MPropertyInfo<NType, NClass> propertyInfo = new CMElementRefsPropertyInfo<NType, NClass>(
				new DummyPropertyInfoOrigin(), wrapperClassInfo,
				wrapperPropertyInfo.getPrivateName(),
				wrappedPropertyInfo.isCollection(),

				wrappedPropertyInfo.getElementTypeInfos(),
				wrapperPropertyInfo.getElementName(),
				wrappedPropertyInfo.isMixed(),
				wrappedPropertyInfo.isDomAllowed(),
				wrappedPropertyInfo.isTypedObjectAllowed());

		rootClassInfo.addProperty(propertyInfo);

		// TODO
		rootClassInfo.removeProperty(wrapperPropertyInfo);
		mmodel.removeClassInfo(wrapperClassInfo);
	}

}
