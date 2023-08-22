package org.jvnet.hyperjaxb3.xjc.model;

import java.util.Collection;
import java.util.Set;

import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;

import com.sun.tools.xjc.model.CArrayInfo;
import com.sun.tools.xjc.model.CAttributePropertyInfo;
import com.sun.tools.xjc.model.CBuiltinLeafInfo;
import com.sun.tools.xjc.model.CClass;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CElement;
import com.sun.tools.xjc.model.CElementInfo;
import com.sun.tools.xjc.model.CElementPropertyInfo;
import com.sun.tools.xjc.model.CEnumLeafInfo;
import com.sun.tools.xjc.model.CNonElement;
import com.sun.tools.xjc.model.CPropertyVisitor;
import com.sun.tools.xjc.model.CReferencePropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.model.CValuePropertyInfo;
import com.sun.tools.xjc.model.CWildcardTypeInfo;

public class CClassifyingVisitor<U> implements CPropertyVisitor<U> {

	private final ProcessModel context;
	private final CClassifier<U> classifier;

	public CClassifyingVisitor(ProcessModel context, CClassifier<U> classifier) {
		this.context = context;
		this.classifier = classifier;
	}

	public U onAttribute(CAttributePropertyInfo attributePropertyInfo) {

		final CNonElement type = context.getGetTypes().getTarget(context,
				attributePropertyInfo);
		if (type instanceof CBuiltinLeafInfo) {
			return onBuiltinAttribute(attributePropertyInfo);
		} else if (type instanceof CEnumLeafInfo) {
			return onEnumAttribute(attributePropertyInfo);
		} else {
			return onOtherAttribute(attributePropertyInfo);
		}
	}

	public U onValue(CValuePropertyInfo valuePropertyInfo) {
		final CNonElement type = context.getGetTypes().getTarget(context,
				valuePropertyInfo);
		if (type instanceof CBuiltinLeafInfo) {
			return onBuiltinValue(valuePropertyInfo);
		} else if (type instanceof CEnumLeafInfo) {
			return onEnumValue(valuePropertyInfo);
		} else {
			return onOtherValue(valuePropertyInfo);
		}
	}

	public U onElement(CElementPropertyInfo elementPropertyInfo) {
		final Collection<? extends CTypeInfo> types = context.getGetTypes()
				.process(context, elementPropertyInfo);
		if (types.size() == 1) {
			final CTypeInfo type = types.iterator().next();
			if (type instanceof CBuiltinLeafInfo) {
				return onBuiltinElement(elementPropertyInfo);
			} else if (type instanceof CEnumLeafInfo) {
				return onEnumElement(elementPropertyInfo);
			} else if (type instanceof CArrayInfo) {
				return onArrayElement(elementPropertyInfo);
			} else if (type instanceof CClass) {
				return onClassElement(elementPropertyInfo);
			} else {
				throw new UnsupportedOperationException("Unexpected type.");
			}
		} else {
			return onHeteroElement(elementPropertyInfo);
		}
	}

	public U onReference(CReferencePropertyInfo referencePropertyInfo) {

		final Collection<? extends CTypeInfo> types = context.getGetTypes()
				.process(context, referencePropertyInfo);
		final Set<CElement> elements = context.getGetTypes().getElements(
				context, referencePropertyInfo);
		if (types.size() == 1) {
			final CTypeInfo type = types.iterator().next();

			if (type instanceof CWildcardTypeInfo
					|| type.equals(CBuiltinLeafInfo.ANYTYPE)) {
				assert elements.isEmpty();
				assert referencePropertyInfo.getWildcard() != null;
				assert !referencePropertyInfo.isMixed();
				return onWildcardReference(referencePropertyInfo);
			} else {
				assert type instanceof CElement;
				assert !elements.isEmpty();
				assert referencePropertyInfo.getWildcard() == null;
				assert !referencePropertyInfo.isMixed();

				if (type instanceof CClass) {
					return onClassReference(referencePropertyInfo);
				} else if (type instanceof CElementInfo) {
					final CElementInfo elementInfo = (CElementInfo) type;
					final CNonElement contentType = elementInfo
							.getContentType();

					if (contentType instanceof CBuiltinLeafInfo) {
						assert referencePropertyInfo.getWildcard() == null;
						assert referencePropertyInfo.isMixed()
								^ elements.isEmpty();
						return onBuiltinElementReference(referencePropertyInfo);
					} else if (contentType instanceof CEnumLeafInfo) {
						assert !elements.isEmpty();
						assert referencePropertyInfo.getWildcard() == null;
						assert !referencePropertyInfo.isMixed();
						return onEnumElementReference(referencePropertyInfo);
					} else if (contentType instanceof CArrayInfo) {
						assert !elements.isEmpty();
						assert referencePropertyInfo.getWildcard() == null;
						assert !referencePropertyInfo.isMixed();
						return onArrayElementReference(referencePropertyInfo);
					} else if (contentType instanceof CClass) {
						assert !elements.isEmpty();
						assert referencePropertyInfo.getWildcard() == null;
						assert !referencePropertyInfo.isMixed();
						return onClassElementReference(referencePropertyInfo);
					} else {
						throw new UnsupportedOperationException(
								"Unexpected type in property ["
										+ referencePropertyInfo.getName(true)
										+ "] of the class ["
										+ ((CClassInfo) referencePropertyInfo
												.parent()).getSqueezedName()
										+ "].");
					}
				} else {
					throw new UnsupportedOperationException(
							"Unexpected type in property ["
									+ referencePropertyInfo.getName(true)
									+ "] of the class ["
									+ ((CClassInfo) referencePropertyInfo
											.parent()).getSqueezedName() + "].");
				}
			}
			// This covers also mixed non-wildcard
		} else {

			if (referencePropertyInfo.getWildcard() == null
					&& !referencePropertyInfo.isMixed()) {

				if (elements.size() == 1) {
					final CElement element = elements.iterator().next();

					if (element instanceof CElementInfo) {
						return onSubstitutedElementReference(referencePropertyInfo);
					} else {
						throw new UnsupportedOperationException(
								"Unexpected type.");
					}
				} else {
					return onHeteroReference(referencePropertyInfo);
				}

			} else {
				return onHeteroReference(referencePropertyInfo);
			}
		}
	}

	public U onBuiltinAttribute(CAttributePropertyInfo attributePropertyInfo) {
		return !attributePropertyInfo.isCollection() ? classifier
				.onSingleBuiltinAttribute(attributePropertyInfo) : classifier
				.onCollectionBuiltinAttribute(attributePropertyInfo);
	}

	public U onEnumAttribute(CAttributePropertyInfo attributePropertyInfo) {
		return !attributePropertyInfo.isCollection() ? classifier
				.onSingleEnumAttribute(attributePropertyInfo) : classifier
				.onCollectionEnumAttribute(attributePropertyInfo);
	}

	public U onOtherAttribute(CAttributePropertyInfo attributePropertyInfo) {
		return !attributePropertyInfo.isCollection() ? classifier
				.onSingleOtherAttribute(attributePropertyInfo) : classifier
				.onCollectionOtherAttribute(attributePropertyInfo);
	}

	public U onBuiltinValue(CValuePropertyInfo valuePropertyInfo) {
		return !valuePropertyInfo.isCollection() ? classifier
				.onSingleBuiltinValue(valuePropertyInfo) : classifier
				.onCollectionBuiltinValue(valuePropertyInfo);
	}

	public U onEnumValue(CValuePropertyInfo valuePropertyInfo) {
		return !valuePropertyInfo.isCollection() ? classifier
				.onSingleEnumValue(valuePropertyInfo) : classifier
				.onCollectionEnumValue(valuePropertyInfo);
	}

	public U onOtherValue(CValuePropertyInfo valuePropertyInfo) {
		return !valuePropertyInfo.isCollection() ? classifier
				.onSingleOtherValue(valuePropertyInfo) : classifier
				.onCollectionOtherValue(valuePropertyInfo);
	}

	public U onBuiltinElement(CElementPropertyInfo elementPropertyInfo) {
		return !elementPropertyInfo.isCollection() ? classifier
				.onSingleBuiltinElement(elementPropertyInfo) : classifier
				.onCollectionBuiltinElement(elementPropertyInfo);
	}

	public U onEnumElement(CElementPropertyInfo elementPropertyInfo) {
		return !elementPropertyInfo.isCollection() ? classifier
				.onSingleEnumElement(elementPropertyInfo) : classifier
				.onCollectionEnumElement(elementPropertyInfo);
	}

	public U onArrayElement(CElementPropertyInfo elementPropertyInfo) {
		return !elementPropertyInfo.isCollection() ? classifier
				.onSingleArrayElement(elementPropertyInfo) : classifier
				.onCollectionArrayElement(elementPropertyInfo);
	}

	public U onClassElement(CElementPropertyInfo elementPropertyInfo) {
		return !elementPropertyInfo.isCollection() ? classifier
				.onSingleClassElement(elementPropertyInfo) : classifier
				.onCollectionClassElement(elementPropertyInfo);
	}

	public U onHeteroElement(CElementPropertyInfo elementPropertyInfo) {
		return !elementPropertyInfo.isCollection() ? classifier
				.onSingleHeteroElement(elementPropertyInfo) : classifier
				.onCollectionHeteroElement(elementPropertyInfo);
	}

	public U onBuiltinElementReference(
			CReferencePropertyInfo referencePropertyInfo) {
		return !referencePropertyInfo.isCollection() ? classifier
				.onSingleBuiltinElementReference(referencePropertyInfo)
				: classifier
						.onCollectionBuiltinElementReference(referencePropertyInfo);
	}

	public U onEnumElementReference(CReferencePropertyInfo referencePropertyInfo) {
		return !referencePropertyInfo.isCollection() ? classifier
				.onSingleEnumElementReference(referencePropertyInfo)
				: classifier
						.onCollectionEnumElementReference(referencePropertyInfo);
	}

	public U onArrayElementReference(
			CReferencePropertyInfo referencePropertyInfo) {
		return !referencePropertyInfo.isCollection() ? classifier
				.onSingleArrayElementReference(referencePropertyInfo)
				: classifier
						.onCollectionArrayElementReference(referencePropertyInfo);
	}

	public U onClassElementReference(
			CReferencePropertyInfo referencePropertyInfo) {
		return !referencePropertyInfo.isCollection() ? classifier
				.onSingleClassElementReference(referencePropertyInfo)
				: classifier
						.onCollectionClassElementReference(referencePropertyInfo);
	}

	public U onSubstitutedElementReference(
			CReferencePropertyInfo referencePropertyInfo) {
		return !referencePropertyInfo.isCollection() ? classifier
				.onSingleSubstitutedElementReference(referencePropertyInfo)
				: classifier
						.onCollectionSubstitutedElementReference(referencePropertyInfo);
	}

	public U onClassReference(CReferencePropertyInfo referencePropertyInfo) {
		return !referencePropertyInfo.isCollection() ? classifier
				.onSingleClassReference(referencePropertyInfo) : classifier
				.onCollectionClassReference(referencePropertyInfo);
	}

	public U onWildcardReference(CReferencePropertyInfo referencePropertyInfo) {
		return !referencePropertyInfo.isCollection() ? classifier
				.onSingleWildcardReference(referencePropertyInfo) : classifier
				.onCollectionWildcardReference(referencePropertyInfo);
	}

	public U onHeteroReference(CReferencePropertyInfo referencePropertyInfo) {
		return !referencePropertyInfo.isCollection() ? classifier
				.onSingleHeteroReference(referencePropertyInfo) : classifier
				.onCollectionHeteroReference(referencePropertyInfo);
	}

}