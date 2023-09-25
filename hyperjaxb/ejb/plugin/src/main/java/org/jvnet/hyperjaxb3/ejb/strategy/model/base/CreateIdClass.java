package org.jvnet.hyperjaxb3.ejb.strategy.model.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessClassInfo;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;
import org.jvnet.jaxb.util.ClassUtils;
import org.xml.sax.Locator;

import jakarta.xml.ns.persistence.orm.Entity;
import jakarta.xml.ns.persistence.orm.IdClass;
import jakarta.xml.ns.persistence.orm.MappedSuperclass;
import com.sun.tools.xjc.model.CAttributePropertyInfo;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CClassInfoParent;
import com.sun.tools.xjc.model.CCustomizations;
import com.sun.tools.xjc.model.CElementPropertyInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CPropertyVisitor;
import com.sun.tools.xjc.model.CReferencePropertyInfo;
import com.sun.tools.xjc.model.CValuePropertyInfo;
import com.sun.tools.xjc.model.CElementPropertyInfo.CollectionMode;
import com.sun.tools.xjc.reader.Ring;
import com.sun.tools.xjc.reader.xmlschema.BGMBuilder;
import com.sun.tools.xjc.reader.xmlschema.bindinfo.LocalScoping;
import com.sun.xml.xsom.XSComponent;

public class CreateIdClass implements ProcessClassInfo {

	public Collection<CClassInfo> process(final ProcessModel context,
			CClassInfo classInfo) {

		final XSComponent component = classInfo.getSchemaComponent();

		final Collection<CPropertyInfo> propertyInfos = context
				.getGetIdPropertyInfos().process(context, classInfo);

		if (propertyInfos.size() > 1) {

			context.getGetIdPropertyInfos().process(context, classInfo);

			final CClassInfoParent parent = Ring.get(BGMBuilder.class)
					.getGlobalBinding().getFlattenClasses() == LocalScoping.NESTED ? classInfo
					: classInfo.parent();

			final CClassInfo idClassInfo = new CClassInfo(classInfo.model,
					parent, classInfo.shortName + "Id", null, null, null,

					component, new CCustomizations());

			org.jvnet.jaxb.plugin.inheritance.Customizations
					._implements(idClassInfo, Serializable.class.getName());

			Customizations.markIgnored(idClassInfo);
			// Customizations.markGenerated(idClassInfo);

			for (CPropertyInfo propertyInfo : propertyInfos) {
				idClassInfo.addProperty(propertyInfo
						.accept(new CPropertyVisitor<CPropertyInfo>() {

							public CPropertyInfo onAttribute(
									CAttributePropertyInfo propertyInfo) {
								return new CAttributePropertyInfo(propertyInfo
										.getName(true), propertyInfo
										.getSchemaComponent(),
										new CCustomizations(), null,
										propertyInfo.getXmlName(), context
												.getGetTypes().getTarget(
														context, propertyInfo),
										propertyInfo.getSchemaType(), false);
							}

							public CPropertyInfo onElement(
									CElementPropertyInfo propertyInfo) {

								final CElementPropertyInfo elementPropertyInfo = new CElementPropertyInfo(

										propertyInfo.getName(true),
										propertyInfo.isCollection() ? CollectionMode.REPEATED_ELEMENT
												: CollectionMode.NOT_REPEATED,
										propertyInfo.id(), propertyInfo
												.getExpectedMimeType(),
										propertyInfo.getSchemaComponent(),
										new CCustomizations(), (Locator) null,
										false);
								elementPropertyInfo.getTypes().addAll(
										context.getGetTypes().getTypes(context,
												propertyInfo));
								return elementPropertyInfo;
							}

							public CPropertyInfo onReference(
									CReferencePropertyInfo propertyInfo) {
								final CReferencePropertyInfo referencePropertyInfo = new CReferencePropertyInfo(
										propertyInfo.getName(true),
										propertyInfo.isCollection(), false,
										propertyInfo.isMixed(), propertyInfo
												.getSchemaComponent(),
										new CCustomizations(), null,
										propertyInfo.isDummy(), propertyInfo
												.isContent(), propertyInfo
												.isMixedExtendedCust());
								referencePropertyInfo.getElements()
										.addAll(context.getGetTypes()
												.getElements(context,
														referencePropertyInfo));
								return referencePropertyInfo;
							}

							public CPropertyInfo onValue(
									CValuePropertyInfo propertyInfo) {
								return new CValuePropertyInfo(propertyInfo
										.getName(true), propertyInfo
										.getSchemaComponent(),
										new CCustomizations(), null, context
												.getGetTypes().getTarget(
														context, propertyInfo),
										propertyInfo.getSchemaType());
							}
						}));
			}

			final IdClass idClass = new IdClass();

			idClass.setClazz(ClassUtils.getPackagedClassName(idClassInfo));

			final Object customization = context.getCustomizing()
					.getEntityOrMappedSuperclassOrEmbeddable(classInfo);

			if (customization instanceof Entity) {
				((Entity) customization).setIdClass(idClass);
			} else if (customization instanceof MappedSuperclass) {
				((MappedSuperclass) customization).setIdClass(idClass);
			}

			return Collections.singleton(idClassInfo);
		} else {
			return Collections.emptyList();
		}
	}

}
