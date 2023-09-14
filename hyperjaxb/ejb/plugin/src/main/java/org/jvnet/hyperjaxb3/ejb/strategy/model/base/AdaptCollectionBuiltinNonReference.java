package org.jvnet.hyperjaxb3.ejb.strategy.model.base;

import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;
import org.jvnet.hyperjaxb3.xjc.generator.bean.field.AdaptingWrappingCollectionField;

import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.model.CAttributePropertyInfo;
import com.sun.tools.xjc.model.CElementPropertyInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CPropertyVisitor;
import com.sun.tools.xjc.model.CReferencePropertyInfo;
import com.sun.tools.xjc.model.CValuePropertyInfo;
import com.sun.tools.xjc.model.TypeUse;
import com.sun.tools.xjc.model.CElementPropertyInfo.CollectionMode;
import com.sun.tools.xjc.outline.FieldOutline;
import org.glassfish.jaxb.core.v2.model.core.PropertyKind;

public class AdaptCollectionBuiltinNonReference extends
		AbstractAdaptBuiltinPropertyInfo {

	public AdaptCollectionBuiltinNonReference(TypeUse propertyType) {
		super(propertyType);
	}

	@Override
	public CollectionMode getDefaultGeneratedPropertyCollectionMode(
			ProcessModel context, CPropertyInfo propertyInfo) {
		return propertyInfo.accept(new CPropertyVisitor<CollectionMode>() {
			public CollectionMode onNonElement(CPropertyInfo p) {
				return p.isCollection() ? CollectionMode.REPEATED_ELEMENT
						: CollectionMode.NOT_REPEATED;
			}

			public CollectionMode onAttribute(CAttributePropertyInfo p) {
				return onNonElement(p);
			}

			public CollectionMode onElement(CElementPropertyInfo p) {
				return p.isCollection() ? (p.isValueList() ? CollectionMode.REPEATED_VALUE
						: CollectionMode.REPEATED_ELEMENT)
						: CollectionMode.NOT_REPEATED;
			}

			public CollectionMode onReference(CReferencePropertyInfo p) {
				return onNonElement(p);
			}

			public CollectionMode onValue(CValuePropertyInfo p) {
				return onNonElement(p);
			}
		});
	}

	@Override
	public PropertyKind getDefaultGeneratedPropertyKind(ProcessModel context,
			CPropertyInfo propertyInfo) {
		return propertyInfo.accept(new CPropertyVisitor<PropertyKind>() {

			public PropertyKind onAttribute(CAttributePropertyInfo p) {
				return PropertyKind.ATTRIBUTE;
			}

			public PropertyKind onElement(CElementPropertyInfo p) {
				return PropertyKind.ELEMENT;
			}

			public PropertyKind onReference(CReferencePropertyInfo p) {
				return PropertyKind.ELEMENT;
			}

			public PropertyKind onValue(CValuePropertyInfo p) {
				return PropertyKind.ATTRIBUTE;
			}
		});
	}

	@Override
	protected FieldOutline generateField(ProcessModel context,
			CPropertyInfo core, ClassOutlineImpl classOutline,
			CPropertyInfo propertyInfo) {
		return new AdaptingWrappingCollectionField(classOutline, core, core,
				propertyInfo);
	}
}
