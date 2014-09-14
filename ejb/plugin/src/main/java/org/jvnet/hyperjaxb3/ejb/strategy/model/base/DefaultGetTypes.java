package org.jvnet.hyperjaxb3.ejb.strategy.model.base;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.jvnet.hyperjaxb3.ejb.strategy.model.GetTypes;

import com.sun.codemodel.JType;
import com.sun.tools.xjc.model.CAttributePropertyInfo;
import com.sun.tools.xjc.model.CBuiltinLeafInfo;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CElementInfo;
import com.sun.tools.xjc.model.CElementPropertyInfo;
import com.sun.tools.xjc.model.CNonElement;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CReferencePropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.model.CTypeRef;
import com.sun.tools.xjc.model.CValuePropertyInfo;
import com.sun.tools.xjc.model.TypeUse;
import com.sun.tools.xjc.model.TypeUseFactory;
import com.sun.xml.bind.v2.model.core.ID;

public class DefaultGetTypes<C> implements GetTypes<C> {

	public Collection<? extends CTypeInfo> process(C context,
			CPropertyInfo propertyInfo) {
		return ref(context, propertyInfo);
	}

	public Collection<? extends CTypeInfo> ref(C context,
			CPropertyInfo propertyInfo) {
		final Collection<? extends CTypeInfo> types = propertyInfo.ref();
		final JType baseType = propertyInfo.baseType;
		final ID id = propertyInfo.id();

		final CTypeInfo parent = propertyInfo.parent();
		if (ID.IDREF.equals(id)) {
			if (parent instanceof CClassInfo) {
				final CClassInfo parentClassInfo = (CClassInfo) parent;
				final String fullName = baseType.fullName();
				for (CClassInfo possibleClassInfo : parentClassInfo.model
						.beans().values()) {
					final String possibleFullName = possibleClassInfo
							.fullName();
					if (fullName != null && fullName.equals(possibleFullName)) {
						return Collections.singleton(possibleClassInfo);
					}
				}
			}
		}
		return types;
	}

	public Collection<? extends CTypeRef> getTypes(C context,
			CElementPropertyInfo propertyInfo) {
		return propertyInfo.getTypes();
	}

	public CNonElement getTarget(C context, CAttributePropertyInfo propertyInfo) {
		return propertyInfo.getTarget();
	}

	public CNonElement getTarget(C context, CValuePropertyInfo propertyInfo) {
		return propertyInfo.getTarget();
	}

	public Set<com.sun.tools.xjc.model.CElement> getElements(C context,
			CReferencePropertyInfo referencePropertyInfo) {

		return referencePropertyInfo.getElements();
	}

	public TypeUse getTypeUse(C context, CPropertyInfo propertyInfo) {
		if (propertyInfo instanceof CValuePropertyInfo) {
			return ((CValuePropertyInfo) propertyInfo).getTarget();
		} else if (propertyInfo instanceof CAttributePropertyInfo) {
			return ((CAttributePropertyInfo) propertyInfo).getTarget();
		} else {
			final CTypeInfo type = propertyInfo.ref().iterator().next();
			if (type instanceof CBuiltinLeafInfo) {
				if (propertyInfo.getAdapter() != null) {
					return TypeUseFactory.adapt((CBuiltinLeafInfo) type,
							propertyInfo.getAdapter());
				} else {
					return (CBuiltinLeafInfo) type;
				}
			} else if (type instanceof CElementInfo) {
				final CElementInfo elementInfo = (CElementInfo) type;
				return getTypeUse(context, elementInfo.getProperty());
			} else {
				throw new AssertionError("Unexpected type.");
			}
		}

	}
}
