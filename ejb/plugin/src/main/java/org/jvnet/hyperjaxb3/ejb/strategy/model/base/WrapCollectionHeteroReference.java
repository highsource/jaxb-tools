package org.jvnet.hyperjaxb3.ejb.strategy.model.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Basic;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.strategy.model.CreatePropertyInfos;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;
import org.jvnet.hyperjaxb3.item.Item;
import org.jvnet.hyperjaxb3.item.MixedItem;
import org.jvnet.hyperjaxb3.xjc.generator.bean.field.SingleField;
import org.jvnet.hyperjaxb3.xjc.generator.bean.field.WrappedCollectionField;
import org.jvnet.hyperjaxb3.xjc.generator.bean.field.WrappingCollectionField;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;
import org.jvnet.jaxb2_commons.util.FieldAccessorUtils;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;
import com.sun.java.xml.ns.persistence.orm.Lob;
import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.generator.bean.field.FieldRenderer;
import com.sun.tools.xjc.model.CAttributePropertyInfo;
import com.sun.tools.xjc.model.CBuiltinLeafInfo;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CClassInfoParent;
import com.sun.tools.xjc.model.CCustomizations;
import com.sun.tools.xjc.model.CElementPropertyInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CReferencePropertyInfo;
import com.sun.tools.xjc.model.CTypeRef;
import com.sun.tools.xjc.model.CElementPropertyInfo.CollectionMode;
import  com.sun.tools.xjc.model.Aspect;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.reader.Ring;
import com.sun.tools.xjc.reader.xmlschema.BGMBuilder;
import com.sun.tools.xjc.reader.xmlschema.bindinfo.LocalScoping;
import com.sun.xml.bind.v2.model.core.ID;
import com.sun.xml.bind.v2.model.core.WildcardMode;

public class WrapCollectionHeteroReference implements CreatePropertyInfos {

	protected Log logger = LogFactory.getLog(getClass());

	public Collection<CPropertyInfo> process(ProcessModel context,
			final CPropertyInfo propertyInfo) {

		assert propertyInfo instanceof CReferencePropertyInfo;

		final CReferencePropertyInfo wrappedPropertyInfo = (CReferencePropertyInfo) propertyInfo;

		final String propertyName = wrappedPropertyInfo.getName(true);

		// if (wrappedPropertyInfo.isMixed()) {
		// // TODO #43
		// logger
		// .error("["
		// + propertyName
		// + "] is a mixed complex heterogeneous collection property. See issue
		// #43.");
		// return Collections.emptyList();
		// }
		//
		logger.debug("Property [" + propertyName
				+ "] is a complex heterogeneous collection property.");

		final CClassInfo classInfo = (CClassInfo) wrappedPropertyInfo.parent();

		final CClassInfoParent parent = Ring.get(BGMBuilder.class)
				.getGlobalBinding().getFlattenClasses() == LocalScoping.NESTED ? classInfo
				: classInfo.parent();

		final CClassInfo itemClassInfo = new CClassInfo(classInfo.model,
				parent, classInfo.shortName + propertyName + "Item", null,
				new QName(propertyName), null, propertyInfo
						.getSchemaComponent(), new CCustomizations());

		Customizations.markGenerated(itemClassInfo);

		final CReferencePropertyInfo itemPropertyInfo = new CReferencePropertyInfo(
		// String name
				"Item",
				// boolean collection
				false,
				// boolean required
				false,

				// boolean isMixed (2.1.9+)
				false,
				// XSComponent source
				wrappedPropertyInfo.getSchemaComponent(),
				// CCustomizations customizations

				new CCustomizations(CustomizationUtils
						.getCustomizations(wrappedPropertyInfo)),
				// Locator locator
				wrappedPropertyInfo.getLocator(),
				// boolean dummy
				false,
				// boolean content
				false,
				// boolean isMixedExtended
				false);

		// For a mixed/skip use lax to allow strings
		if (wrappedPropertyInfo.isMixed()
				&& WildcardMode.SKIP.equals(wrappedPropertyInfo.getWildcard())) {
			itemPropertyInfo.setWildcard(WildcardMode.LAX);
		} else {
			itemPropertyInfo.setWildcard(wrappedPropertyInfo.getWildcard());
		}

		itemPropertyInfo.getElements()
				.addAll(context.getGetTypes()
						.getElements(context,
								wrappedPropertyInfo));

		itemClassInfo.addProperty(itemPropertyInfo);

		final CPropertyInfo stringProperty;
		if (wrappedPropertyInfo.isMixed()) {

			stringProperty = new CAttributePropertyInfo("Text", propertyInfo
					.getSchemaComponent(), new CCustomizations(), propertyInfo
					.getLocator(), new QName("Text"),

			CBuiltinLeafInfo.STRING, CBuiltinLeafInfo.STRING.getTypeName(),
					false);

			// stringProperty.realization = new FieldRenderer() {
			// public FieldOutline generate(ClassOutlineImpl outline,
			// CPropertyInfo propertyInfo) {
			// StringField fieldOutline = new StringField(outline,
			// propertyInfo, itemPropertyInfo);
			// fieldOutline.generateAccessors();
			// return fieldOutline;
			// }
			// };
			// Customizations.markGenerated(stringProperty);

			final Basic basic = new Basic();
			basic.setLob(new Lob());
			CustomizationUtils.addCustomization(stringProperty, Customizations
					.getContext(), Customizations.BASIC_ELEMENT_NAME, basic);

			Customizations.markGenerated(stringProperty);

			itemClassInfo.addProperty(stringProperty);
		} else {
			stringProperty = null;
		}

		context.getProcessClassInfo().process(context, itemClassInfo);

		itemPropertyInfo.realization = wrappedPropertyInfo.isMixed() ? new MixedItemFieldRederer(
				itemPropertyInfo.realization)
				: new ItemFieldRederer(itemPropertyInfo.realization);

		final CElementPropertyInfo wrappingPropertyInfo =

		new CElementPropertyInfo(

		propertyName + "Items", CollectionMode.REPEATED_ELEMENT, ID.NONE,
				wrappedPropertyInfo.getExpectedMimeType(), null,
				new CCustomizations(), null, false);

		wrappingPropertyInfo.getTypes().add(
				new CTypeRef(itemClassInfo, new QName(propertyName + "Items"),
						null, false, null));

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
		if (stringProperty != null) {
			a.add(stringProperty);
		}
		return a;
	}

	private class MixedItemFieldRederer implements FieldRenderer {
		// private final FieldRenderer core;

		public MixedItemFieldRederer(final FieldRenderer core) {
			super();
			// this.core = core;
		}

		public FieldOutline generate(ClassOutlineImpl classOutline,
				CPropertyInfo propertyInfo) {

			final FieldOutline fieldOutline =

			// core == null ?

			new SingleField(classOutline, propertyInfo) {
				@Override
				protected String getGetterMethod() {
					return "get" + prop.getName(true);
				}

				protected JType getType(Aspect aspect) {
					return super.getType(aspect).boxify();
				}
			};// : core.generate(classOutline, propertyInfo);

			final JClass itemClass = classOutline.implClass.owner().ref(
					MixedItem.class).narrow(fieldOutline.getRawType().boxify());
			classOutline.implClass._implements(itemClass);
			if (classOutline.parent().getModel().serializable) {
				classOutline.implClass._implements(Serializable.class);
			}
			final JMethod isGetter = FieldAccessorUtils.getter(fieldOutline);

			if (isGetter.name().startsWith("is")) {
				final JMethod getter = classOutline.implClass.method(
						JMod.PUBLIC, isGetter.type(),

						"get" + isGetter.name().substring(2));

				getter.body()._return(JExpr._this().invoke(isGetter));
			}

			return fieldOutline;
		}
	}

	private class ItemFieldRederer implements FieldRenderer {
		// private final FieldRenderer core;

		public ItemFieldRederer(final FieldRenderer core) {
			super();
			// this.core = core;
		}

		public FieldOutline generate(ClassOutlineImpl classOutline,
				CPropertyInfo propertyInfo) {

			final FieldOutline fieldOutline =

			// core == null ?

			new SingleField(classOutline, propertyInfo) {
				@Override
				protected String getGetterMethod() {
					return "get" + prop.getName(true);
				}

				protected JType getType(Aspect aspect) {
					return super.getType(aspect).boxify();
				}
			};// : core.generate(classOutline, propertyInfo);

			final JClass itemClass = classOutline.implClass.owner().ref(
					Item.class).narrow(fieldOutline.getRawType().boxify());
			classOutline.implClass._implements(itemClass);
			if (classOutline.parent().getModel().serializable) {
				classOutline.implClass._implements(Serializable.class);
			}
			final JMethod isGetter = FieldAccessorUtils.getter(fieldOutline);

			if (isGetter.name().startsWith("is")) {
				final JMethod getter = classOutline.implClass.method(
						JMod.PUBLIC, isGetter.type(),

						"get" + isGetter.name().substring(2));

				getter.body()._return(JExpr._this().invoke(isGetter));
			}

			return fieldOutline;
		}
	}

	// protected CPropertyInfo createObjectProperty(
	// final CReferencePropertyInfo referencePropertyInfo) {
	// final CPropertyInfo objectProperty;
	// if (referencePropertyInfo.getWildcard() != null
	// && referencePropertyInfo.getWildcard().allowTypedObject) {
	//
	// objectProperty = new CAttributePropertyInfo(referencePropertyInfo
	// .getName(true)
	// + "Object", referencePropertyInfo.getSchemaComponent(),
	// new CCustomizations(), referencePropertyInfo.getLocator(),
	// new QName(referencePropertyInfo.getName(true) + "Object"),
	// CBuiltinLeafInfo.STRING, CBuiltinLeafInfo.STRING
	// .getTypeName(), false);
	//
	// objectProperty.realization = new FieldRenderer() {
	// public FieldOutline generate(ClassOutlineImpl context,
	// CPropertyInfo prop) {
	// final SingleWrappingReferenceObjectField fieldOutline = new
	// SingleWrappingReferenceObjectField(
	// context, prop, referencePropertyInfo);
	// fieldOutline.generateAccessors();
	// return fieldOutline;
	// }
	// };
	// Customizations.addCustomizationElement(objectProperty, null, null);
	// Customizations.markGenerated(objectProperty);
	//
	// } else {
	// objectProperty = null;
	// }
	// return objectProperty;
	// }

	// protected CPropertyInfo createElementProperty(
	// final CReferencePropertyInfo referencePropertyInfo) {
	// final CAttributePropertyInfo elementProperty;
	// if (referencePropertyInfo.getWildcard() != null
	// && referencePropertyInfo.getWildcard().allowDom) {
	//
	// elementProperty = new CAttributePropertyInfo(referencePropertyInfo
	// .getName(true)
	// + "Element", referencePropertyInfo.getSchemaComponent(),
	// new CCustomizations(), referencePropertyInfo.getLocator(),
	// new QName(referencePropertyInfo.getName(true) + "Element"),
	//
	// TypeUseFactory.adapt(CBuiltinLeafInfo.STRING,
	// ElementAsString.class, false),
	// CBuiltinLeafInfo.STRING.getTypeName(), false);
	//
	// elementProperty.realization = new FieldRenderer() {
	// public FieldOutline generate(ClassOutlineImpl context,
	// CPropertyInfo prop) {
	// ElementField fieldOutline = new ElementField(context, prop,
	// referencePropertyInfo);
	// fieldOutline.generateAccessors();
	// return fieldOutline;
	// }
	// };
	// Customizations.addCustomizationElement(elementProperty, null, null);
	// Customizations.markGenerated(elementProperty);
	// } else {
	// elementProperty = null;
	// }
	// return elementProperty;
	// }

}
