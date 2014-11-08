package org.jvnet.hyperjaxb3.ejb.strategy.model.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.GeneratedClass;
import org.jvnet.hyperjaxb3.ejb.strategy.model.CreatePropertyInfos;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;
import org.jvnet.hyperjaxb3.item.Item;
import org.jvnet.hyperjaxb3.xjc.generator.bean.field.WrappedCollectionField;
import org.jvnet.hyperjaxb3.xjc.generator.bean.field.WrappingCollectionField;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;
import org.jvnet.jaxb2_commons.util.FieldAccessorUtils;
import org.w3c.dom.Element;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;
import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.generator.bean.field.FieldRenderer;
import com.sun.tools.xjc.generator.bean.field.SingleField;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CClassInfoParent;
import com.sun.tools.xjc.model.CCustomizations;
import com.sun.tools.xjc.model.CElementPropertyInfo;
import com.sun.tools.xjc.model.CElementPropertyInfo.CollectionMode;
import com.sun.tools.xjc.model.CPluginCustomization;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeRef;
import  com.sun.tools.xjc.model.Aspect;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.reader.Ring;
import com.sun.tools.xjc.reader.xmlschema.BGMBuilder;
import com.sun.tools.xjc.reader.xmlschema.bindinfo.LocalScoping;
import com.sun.xml.bind.v2.model.core.ID;

public class WrapCollectionElement implements CreatePropertyInfos {

	protected Log logger = LogFactory.getLog(getClass());

	public Collection<CPropertyInfo> process(ProcessModel context,
			final CPropertyInfo propertyInfo) {

		assert propertyInfo instanceof CElementPropertyInfo;

		final CElementPropertyInfo wrappedPropertyInfo = (CElementPropertyInfo) propertyInfo;

		final CClassInfo classInfo = (CClassInfo) wrappedPropertyInfo.parent();

		final String propertyName = wrappedPropertyInfo.getName(true);

		logger.debug("Property [" + propertyName
				+ "] is a simple homogeneous collection property.");

		final CClassInfoParent parent = Ring.get(BGMBuilder.class)
				.getGlobalBinding().getFlattenClasses() == LocalScoping.NESTED ? classInfo
				: classInfo.parent();

		final GeneratedClass generatedClass = context.getCustomizing()
				.getGeneratedClass(propertyInfo);

		final String className = (generatedClass == null || generatedClass
				.getClassName() == null) ? (classInfo.shortName + propertyName + "Item")
				: generatedClass.getClassName();

		final CCustomizations customizations;
		if (generatedClass != null && !generatedClass.getAny().isEmpty()) {
			final Collection<CPluginCustomization> cPluginCustomizations = new ArrayList<CPluginCustomization>(
					generatedClass.getAny().size());
			for (Element element : generatedClass.getAny()) {
				cPluginCustomizations.add(CustomizationUtils
						.createCustomization(element));
			}
			customizations = new CCustomizations(cPluginCustomizations);

		} else {
			customizations = new CCustomizations();
		}

		final CClassInfo itemClassInfo = new CClassInfo(classInfo.model,
				parent, className, null, new QName(propertyName), null,
				propertyInfo.getSchemaComponent(), customizations);

		Customizations.markGenerated(itemClassInfo);

		final CElementPropertyInfo itemPropertyInfo = new CElementPropertyInfo(
				"Item", CollectionMode.NOT_REPEATED, ID.NONE,
				wrappedPropertyInfo.getExpectedMimeType(), wrappedPropertyInfo
						.getSchemaComponent(), new CCustomizations(
						CustomizationUtils
								.getCustomizations(wrappedPropertyInfo)),
				wrappedPropertyInfo.getLocator(), false);

		itemPropertyInfo.getTypes().addAll(
				context.getGetTypes().getTypes(context, wrappedPropertyInfo)				
				);
		if (wrappedPropertyInfo.getAdapter() != null) {
			itemPropertyInfo.setAdapter(wrappedPropertyInfo.getAdapter());
		}

		final FieldRenderer fieldRenderer = new FieldRenderer() {
			public FieldOutline generate(ClassOutlineImpl classOutline,
					CPropertyInfo propertyInfo) {
				final FieldOutline fieldOutline = new SingleField(classOutline,
						propertyInfo) {
					@Override
					protected String getGetterMethod() {
						return "get" + prop.getName(true);
					}

					protected JType getType(Aspect aspect) {
						return super.getType(aspect).boxify();
					}
				};

				final JClass itemClass = classOutline.implClass.owner().ref(
						Item.class).narrow(fieldOutline.getRawType().boxify());
				classOutline.implClass._implements(itemClass);
				if (classOutline.parent().getModel().serializable) {
					classOutline.implClass._implements(Serializable.class);
				}

				final JMethod isGetter = FieldAccessorUtils
						.getter(fieldOutline);

				if (isGetter.name().startsWith("is")) {
					final JMethod getter = classOutline.implClass.method(
							JMod.PUBLIC, isGetter.type(),

							"get" + isGetter.name().substring(2));

					getter.body()._return(JExpr._this().invoke(isGetter));
				}

				return fieldOutline;
			}
		};

		itemPropertyInfo.realization = fieldRenderer;
		itemClassInfo.addProperty(itemPropertyInfo);

		context.getProcessClassInfo().process(context, itemClassInfo);

		final CElementPropertyInfo wrappingPropertyInfo =

		new CElementPropertyInfo(propertyName + "Items",
				CollectionMode.REPEATED_ELEMENT, ID.NONE, wrappedPropertyInfo
						.getExpectedMimeType(), null, new CCustomizations(),
				null, false);

		for (final CTypeRef typeRef : context.getGetTypes().getTypes(context, wrappedPropertyInfo)) {

			wrappingPropertyInfo.getTypes().add(
					new CTypeRef(itemClassInfo, new QName(typeRef.getTagName()
							.getNamespaceURI(), typeRef.getTagName()
							.getLocalPart()
							+ "Items"), null, false, null));
		}

		wrappingPropertyInfo.realization = new FieldRenderer() {
			public FieldOutline generate(ClassOutlineImpl outline,
					CPropertyInfo wrappingPropertyInfo) {
				return new WrappingCollectionField(outline,
						wrappedPropertyInfo, wrappingPropertyInfo);
			}
		};

		wrappedPropertyInfo.realization = new FieldRenderer() {
			public FieldOutline generate(ClassOutlineImpl outline,
					CPropertyInfo wrappedPropertyInfo) {
				return new WrappedCollectionField(outline, wrappedPropertyInfo,
						wrappingPropertyInfo);
			}
		};

		Customizations.markGenerated(wrappingPropertyInfo);
		Customizations.markIgnored(wrappedPropertyInfo);

		final List<CPropertyInfo> a = new ArrayList<CPropertyInfo>(1);
		a.add(wrappingPropertyInfo);
		a.add(itemPropertyInfo);
		return a;
	}
}
