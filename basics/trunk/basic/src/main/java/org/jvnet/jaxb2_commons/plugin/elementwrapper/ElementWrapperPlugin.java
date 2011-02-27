package org.jvnet.jaxb2_commons.plugin.elementwrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.plugin.AbstractParameterizablePlugin;
import org.jvnet.jaxb2_commons.plugin.CustomizedIgnoring;
import org.jvnet.jaxb2_commons.plugin.Ignoring;
import org.jvnet.jaxb2_commons.xjc.generator.artificial.WrapperPropertyOutlineGenerator;
import org.jvnet.jaxb2_commons.xjc.model.concrete.XJCCMInfoFactory;
import org.jvnet.jaxb2_commons.xjc.model.concrete.origin.DefaultPropertyInfoOrigin;
import org.jvnet.jaxb2_commons.xml.bind.model.MClassInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MElementPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MModelInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MTypeInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.concrete.CMElementPropertyInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.util.DefaultPropertyInfoVisitor;
import org.jvnet.jaxb2_commons.xml.bind.model.util.DefaultTypeInfoVisitor;
import org.xml.sax.ErrorHandler;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.Model;
import com.sun.tools.xjc.outline.Outline;
import com.sun.tools.xjc.reader.Ring;

public class ElementWrapperPlugin extends AbstractParameterizablePlugin {

	@Override
	public String getOptionName() {
		return "XelementWrapper";
	}

	@Override
	public String getUsage() {
		return "Generates @XmlElementWrapper fields.";
	}

	private MModelInfo retrieveModel(Model model) {
		try {
			return Ring.get(MModelInfo.class);
		} catch (Throwable t) {
			final MModelInfo mmodel = new XJCCMInfoFactory(model).createModel();
			Ring.add(MModelInfo.class, mmodel);
			return mmodel;
		}
	}

	@Override
	public void postProcessModel(Model model, ErrorHandler errorHandler) {

		final MModelInfo mmodel = retrieveModel(model);

		final Collection<MClassInfo> wrapperClassInfos = new HashSet<MClassInfo>();
		final Collection<MPropertyInfo> wrapperPropertyInfos = new HashSet<MPropertyInfo>();

		final Collection<MClassInfo> classInfos = new ArrayList<MClassInfo>(
				mmodel.getClassInfos());
		for (final MClassInfo classInfo : classInfos) {
			if (/*
				 * TODO !getIgnoring().isIgnored(classInfo) &&
				 */true) {
				List<MPropertyInfo> properties = new ArrayList<MPropertyInfo>(
						classInfo.getProperties());
				for (MPropertyInfo propertyInfo : properties) {

					propertyInfo
							.acceptPropertyInfoVisitor(new DefaultPropertyInfoVisitor<Void>() {

								@Override
								public Void visitElementPropertyInfo(
										final MElementPropertyInfo wrapperPropertyInfo) {

									wrapperPropertyInfo
											.getTypeInfo()
											.acceptTypeInfoVisitor(
													new DefaultTypeInfoVisitor<Void>() {
														@Override
														public Void visitClassInfo(
																final MClassInfo wrapperClassInfo) {
															if (/*
																 * TODO
																 * !getIgnoring
																 * ( ).isIgnored
																 * ( classInfo )
																 * &&
																 */wrapperClassInfo
																	.getProperties()
																	.size() == 1) {
																final MPropertyInfo propertyInfo = wrapperClassInfo
																		.getProperties()
																		.get(0);

																if (propertyInfo
																		.isCollection()) {
																	process(mmodel,
																			classInfo,
																			wrapperPropertyInfo,
																			wrapperClassInfo,
																			propertyInfo);
																}
															}
															return null;
														}
													});
									return null;

								}
							});
				}
			}
		}

		// for (CClassInfo classInfo : model.beans().values()) {
		// final List<CPropertyInfo> wrapperProperties = new
		// ArrayList<CPropertyInfo>();
		// for (CPropertyInfo propertyInfo : classInfo.getProperties()) {
		// if (propertyInfo instanceof CElementPropertyInfo) {
		// CElementPropertyInfo elementPropertyInfo = (CElementPropertyInfo)
		// propertyInfo;
		// if (elementPropertyInfo.getTypes().size() == 1) {
		// final CTypeRef typeRef = elementPropertyInfo.getTypes()
		// .get(0);
		//
		// CNonElement target = typeRef.getTarget();
		// if (wrapperClasses.contains(target)) {
		// System.out.println("Wrapped property "
		// + classInfo.fullName() + "."
		// + propertyInfo.getName(false));
		// wrapperProperties.add(elementPropertyInfo);
		// }
		// }
		// } else if (propertyInfo instanceof CReferencePropertyInfo) {
		// // TODO
		// }
		// }
		// for (CPropertyInfo propertyInfo : wrapperProperties) {
		// System.out.println("Removing wrapper property "
		// + classInfo.fullName() + "."
		// + propertyInfo.getName(false));
		// classInfo.getProperties().remove(propertyInfo);
		// }
		// }
		//
		// for (Entry<NClass, CClassInfo> entry : toBeRemoved.entrySet()) {
		// final NClass theClass = entry.getKey();
		// CClassInfo classInfo = entry.getValue();
		// System.out
		// .println("Removing wrapper class " + classInfo.fullName());
		// model.beans().remove(theClass);
		// }
	}

	public boolean run(Outline outline, Options opt, ErrorHandler errorHandler) {
		// for (final ClassOutline classOutline : outline.getClasses())
		// if (!getIgnoring().isIgnored(classOutline)) {
		// processClassOutline(classOutline);
		// }
		return true;
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

	protected void process(final MModelInfo mmodel, final MClassInfo classInfo,
			final MElementPropertyInfo wrapperPropertyInfo,
			final MClassInfo wrapperClassInfo,
			final MPropertyInfo wrappedPropertyInfo) {
		wrappedPropertyInfo
				.acceptPropertyInfoVisitor(new DefaultPropertyInfoVisitor<Void>() {

					@Override
					public Void visitElementPropertyInfo(
							final MElementPropertyInfo wrappedPropertyInfo) {
						final MTypeInfo wrappedTypeInfo = wrappedPropertyInfo
								.getTypeInfo();
						System.out.println("Class info:" + classInfo.getName());
						System.out.println("Wrapper property info:"
								+ wrapperPropertyInfo.getPrivateName());
						System.out.println("Wrapper class info :"
								+ wrapperClassInfo.getName());
						System.out.println("Wrapped property info:"
								+ wrappedPropertyInfo.getPrivateName());
						System.out.println("Wrapped type info:"
								+ wrappedTypeInfo);

						final MPropertyInfo propertyInfo = new CMElementPropertyInfo(
								new DefaultPropertyInfoOrigin(
										new WrapperPropertyOutlineGenerator()),
								wrapperClassInfo, wrapperPropertyInfo
										.getPrivateName(), wrappedPropertyInfo
										.isCollection(), wrappedTypeInfo,
								wrappedPropertyInfo.getElementName(),
								wrapperPropertyInfo.getElementName());

						classInfo.addProperty(propertyInfo);

						// TODO
						classInfo.removeProperty(wrapperPropertyInfo);
						mmodel.removeClassInfo(wrapperClassInfo);
						return null;
					}
				});
	}

}
