package org.jvnet.hyperjaxb3.xjc.model;

import com.sun.tools.xjc.model.CAttributePropertyInfo;
import com.sun.tools.xjc.model.CElementPropertyInfo;
import com.sun.tools.xjc.model.CReferencePropertyInfo;
import com.sun.tools.xjc.model.CValuePropertyInfo;

public interface CClassifier<V> {

	public V onSingleBuiltinAttribute(
			CAttributePropertyInfo attributePropertyInfo);

	public V onSingleEnumAttribute(CAttributePropertyInfo attributePropertyInfo);

	public V onSingleOtherAttribute(CAttributePropertyInfo attributePropertyInfo);

	public V onCollectionBuiltinAttribute(
			CAttributePropertyInfo attributePropertyInfo);

	public V onCollectionEnumAttribute(
			CAttributePropertyInfo attributePropertyInfo);

	public V onCollectionOtherAttribute(
			CAttributePropertyInfo attributePropertyInfo);

	public V onSingleBuiltinValue(CValuePropertyInfo valuePropertyInfo);

	public V onSingleEnumValue(CValuePropertyInfo valuePropertyInfo);

	public V onSingleOtherValue(CValuePropertyInfo valuePropertyInfo);

	public V onCollectionBuiltinValue(CValuePropertyInfo valuePropertyInfo);

	public V onCollectionEnumValue(CValuePropertyInfo valuePropertyInfo);

	public V onCollectionOtherValue(CValuePropertyInfo valuePropertyInfo);

	public V onSingleBuiltinElement(CElementPropertyInfo elementPropertyInfo);

	public V onSingleEnumElement(CElementPropertyInfo elementPropertyInfo);

	public V onSingleArrayElement(CElementPropertyInfo elementPropertyInfo);

	public V onSingleClassElement(CElementPropertyInfo elementPropertyInfo);

	public V onSingleHeteroElement(CElementPropertyInfo elementPropertyInfo);

	public V onCollectionBuiltinElement(CElementPropertyInfo elementPropertyInfo);

	public V onCollectionEnumElement(CElementPropertyInfo elementPropertyInfo);

	public V onCollectionArrayElement(CElementPropertyInfo elementPropertyInfo);

	public V onCollectionClassElement(CElementPropertyInfo elementPropertyInfo);

	public V onCollectionHeteroElement(CElementPropertyInfo elementPropertyInfo);

	public V onSingleBuiltinElementReference(
			CReferencePropertyInfo referencePropertyInfo);

	public V onSingleEnumElementReference(
			CReferencePropertyInfo referencePropertyInfo);

	public V onSingleArrayElementReference(
			CReferencePropertyInfo referencePropertyInfo);

	public V onSingleClassElementReference(
			CReferencePropertyInfo referencePropertyInfo);

	public V onSingleSubstitutedElementReference(
			CReferencePropertyInfo referencePropertyInfo);

	public V onSingleClassReference(CReferencePropertyInfo referencePropertyInfo);

	public V onSingleWildcardReference(
			CReferencePropertyInfo referencePropertyInfo);

	public V onSingleHeteroReference(
			CReferencePropertyInfo referencePropertyInfo);

	public V onCollectionBuiltinElementReference(
			CReferencePropertyInfo referencePropertyInfo);

	public V onCollectionEnumElementReference(
			CReferencePropertyInfo referencePropertyInfo);

	public V onCollectionArrayElementReference(
			CReferencePropertyInfo referencePropertyInfo);

	public V onCollectionClassElementReference(
			CReferencePropertyInfo referencePropertyInfo);

	public V onCollectionClassReference(
			CReferencePropertyInfo referencePropertyInfo);

	public V onCollectionSubstitutedElementReference(
			CReferencePropertyInfo referencePropertyInfo);

	public V onCollectionWildcardReference(
			CReferencePropertyInfo referencePropertyInfo);

	public V onCollectionHeteroReference(
			CReferencePropertyInfo referencePropertyInfo);

}
