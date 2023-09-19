package org.jvnet.hyperjaxb3.ejb.strategy.model.base;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessPropertyInfos;
import org.jvnet.hyperjaxb3.xjc.model.CClassifier;
import org.jvnet.hyperjaxb3.xjc.model.CClassifyingVisitor;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;

import com.sun.tools.xjc.model.CAttributePropertyInfo;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CClassRef;
import com.sun.tools.xjc.model.CElementPropertyInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CReferencePropertyInfo;
import com.sun.tools.xjc.model.CValuePropertyInfo;

public class DefaultProcessPropertyInfos implements ProcessPropertyInfos {

	protected Log logger = LogFactory.getLog(getClass());

	public Collection<CPropertyInfo> process(ProcessModel context,
			CClassInfo classInfo) {

		logger.debug("Processing property infos for class info ["
				+ classInfo.getName() + "].");

		final Collection<CPropertyInfo> newPropertyInfos = new LinkedList<CPropertyInfo>();
		// In case this is a root entity, create default id properties

		if (CustomizationUtils.containsCustomization(classInfo,
				Customizations.GENERATED_ID_ELEMENT_NAME)) {
			final Collection<CPropertyInfo> idPropertyInfos = context
					.getGetIdPropertyInfos().process(context, classInfo);

			if (!idPropertyInfos.isEmpty()) {
				logger.error(MessageFormat.format(
						"Class info [{0}] is annotated with hj:generated-id customization, "
								+ "but it already contains id properties. "
								+ "The customization will be ignored.",
						classInfo.getName()));
			} else {
				newPropertyInfos.addAll(createDefaultIdPropertyInfos(context,
						classInfo));
			}
		} else if (isRootClass(context, classInfo)) {
			final Collection<CPropertyInfo> idPropertyInfos = context
					.getGetIdPropertyInfos().process(context, classInfo);

			// If no id properties found, create default.
			if (idPropertyInfos.isEmpty()) {
				newPropertyInfos.addAll(createDefaultIdPropertyInfos(context,
						classInfo));
			}
		}

		if (CustomizationUtils.containsCustomization(classInfo,
				Customizations.GENERATED_VERSION_ELEMENT_NAME)) {
			final Collection<CPropertyInfo> versionPropertyInfos = context
					.getGetVersionPropertyInfos().process(context, classInfo);
			if (!versionPropertyInfos.isEmpty()) {
				logger.error(MessageFormat
						.format("Class info [{0}] is annotated with hj:generated-version customization, "
								+ "but it already contains version properties. "
								+ "The customization will be ignored.",
								classInfo.getName()));
			} else {
				newPropertyInfos.addAll(createDefaultVersionPropertyInfos(
						context, classInfo));
			}
		} else if (isRootClass(context, classInfo)) {
			final Collection<CPropertyInfo> versionPropertyInfos = context
					.getGetVersionPropertyInfos().process(context, classInfo);

			// If no version properties found, create default.
			if (versionPropertyInfos.isEmpty()) {
				newPropertyInfos.addAll(createDefaultVersionPropertyInfos(
						context, classInfo));
			}

		}

		final CPropertyInfo[] propertyInfos = classInfo.getProperties()
				.toArray(new CPropertyInfo[classInfo.getProperties().size()]);
		for (final CPropertyInfo propertyInfo : propertyInfos) {
			if (!context.getIgnoring().isPropertyInfoIgnored(context,
					propertyInfo)) {
				newPropertyInfos.addAll(process(context, propertyInfo));
			}
		}

		if (classInfo.declaresAttributeWildcard()) {
			logger.error("Class ["
					+ classInfo.getName()
					+ "] declares an attribute wildcard. This is currently not supported. See issue #46.");
		}

		// Add properties if they're not yet there
		for (final CPropertyInfo newPropertyInfo : newPropertyInfos) {
			if (newPropertyInfo.parent() == null) {
				classInfo.addProperty(newPropertyInfo);
			}
		}

		logger.debug("Finished processing property infos for class info ["
				+ classInfo.getName() + "].");
		return newPropertyInfos;
	}

	public Collection<CPropertyInfo> process(ProcessModel context,
			CPropertyInfo propertyInfo) {
		final CClassifyingVisitor<Collection<CPropertyInfo>> classifyingVisitor = new CClassifyingVisitor<Collection<CPropertyInfo>>(
				context, new PropertyClassifier(context));
		return propertyInfo.accept(classifyingVisitor);
	}

	public boolean isRootClass(ProcessModel context, CClassInfo classInfo) {
		final boolean notMappedSuperclassAndNotEmbeddable = !CustomizationUtils
				.containsCustomization(classInfo,
						Customizations.MAPPED_SUPERCLASS_ELEMENT_NAME)
				&& !CustomizationUtils.containsCustomization(classInfo,
						Customizations.EMBEDDABLE_ELEMENT_NAME);
		if (classInfo.getRefBaseClass() != null) {
			return notMappedSuperclassAndNotEmbeddable
					&& !isSelfOrAncestorRootClass(context,
							classInfo.getRefBaseClass());
		} else if (classInfo.getBaseClass() != null) {
			return notMappedSuperclassAndNotEmbeddable
					&& !isSelfOrAncestorRootClass(context,
							classInfo.getBaseClass());
		} else {
			return notMappedSuperclassAndNotEmbeddable;
		}
	}

	public boolean isSelfOrAncestorRootClass(ProcessModel context,
			CClassInfo classInfo) {
		if (isRootClass(context, classInfo)) {
			return true;
		} else if (classInfo.getRefBaseClass() != null) {
			return isSelfOrAncestorRootClass(context,
					classInfo.getRefBaseClass());
		} else if (classInfo.getBaseClass() != null) {
			return isSelfOrAncestorRootClass(context, classInfo.getBaseClass());
		} else {
			return !CustomizationUtils.containsCustomization(classInfo,
					Customizations.MAPPED_SUPERCLASS_ELEMENT_NAME)
					&& !CustomizationUtils.containsCustomization(classInfo,
							Customizations.EMBEDDABLE_ELEMENT_NAME);
		}

	}

	public boolean isSelfOrAncestorRootClass(ProcessModel context,
			CClassRef classRef) {

		final String className = classRef.fullName();

		try {
			final Class<?> referencedClass = Class.forName(className);
			return isSelfOrAncestorRootClass(referencedClass);

		} catch (ClassNotFoundException cnfex) {
			logger.warn("Referenced class ["
					+ className
					+ "] could not be found, this may lead to incorrect generation of the identifier fields.");
			return true;
		}
	}

	public boolean isRootClass(Class<?> theClass) {
		final boolean notMappedSuperclassAndNotEmbeddable = theClass
				.getAnnotation(MappedSuperclass.class) == null
				&& theClass.getAnnotation(Embeddable.class) == null;
		if (theClass.getSuperclass() != null) {
			return notMappedSuperclassAndNotEmbeddable
					&& !isSelfOrAncestorRootClass(theClass.getSuperclass());
		} else {
			return notMappedSuperclassAndNotEmbeddable;
		}
	}

	public boolean isSelfOrAncestorRootClass(Class<?> theClass) {
		if (isRootClass(theClass)) {
			return true;
		} else if (theClass.getSuperclass() != null) {
			return isSelfOrAncestorRootClass(theClass.getSuperclass());
		} else {
			return theClass.getAnnotation(MappedSuperclass.class) == null
					&& theClass.getAnnotation(Embeddable.class) == null;
		}
	}

	public Collection<CPropertyInfo> createDefaultIdPropertyInfos(
			ProcessModel context, CClassInfo classInfo) {

		return context.getCreateDefaultIdPropertyInfos().process(context,
				classInfo);
	}

	public Collection<CPropertyInfo> createDefaultVersionPropertyInfos(
			ProcessModel context, CClassInfo classInfo) {

		return context.getCreateDefaultVersionPropertyInfos().process(context,
				classInfo);
	}

	private class PropertyClassifier implements
			CClassifier<Collection<CPropertyInfo>> {
		private ProcessModel context;

		public PropertyClassifier(ProcessModel context) {
			this.context = context;
		}

		// / Attribute

		// Single

		public Collection<CPropertyInfo> onSingleBuiltinAttribute(
				CAttributePropertyInfo attributePropertyInfo) {
			return context.getWrapSingleBuiltinAttribute().process(context,
					attributePropertyInfo);
		}

		public Collection<CPropertyInfo> onSingleEnumAttribute(
				CAttributePropertyInfo attributePropertyInfo) {
			return context.getWrapSingleEnumAttribute().process(context,
					attributePropertyInfo);
		}

		public Collection<CPropertyInfo> onSingleOtherAttribute(
				CAttributePropertyInfo attributePropertyInfo) {
			logger.error("[" + attributePropertyInfo.getName(true)
					+ "] is a single other attribute. See issue #56.");
			return Collections.emptyList();
		}

		// Collection

		public Collection<CPropertyInfo> onCollectionBuiltinAttribute(
				CAttributePropertyInfo attributePropertyInfo) {
			return context.getWrapCollectionBuiltinAttribute().process(context,
					attributePropertyInfo);
		}

		public Collection<CPropertyInfo> onCollectionEnumAttribute(
				CAttributePropertyInfo attributePropertyInfo) {
			return context.getWrapCollectionEnumAttribute().process(context,
					attributePropertyInfo);
		}

		public Collection<CPropertyInfo> onCollectionOtherAttribute(
				CAttributePropertyInfo attributePropertyInfo) {
			logger.error("[" + attributePropertyInfo.getName(true)
					+ "] is a collection other attribute. See issue #59.");
			return Collections.emptyList();
		}

		// / Value

		// Single

		public Collection<CPropertyInfo> onSingleBuiltinValue(
				CValuePropertyInfo valuePropertyInfo) {
			return context.getWrapSingleBuiltinValue().process(context,
					valuePropertyInfo);
		}

		public Collection<CPropertyInfo> onSingleEnumValue(
				CValuePropertyInfo valuePropertyInfo) {
			return context.getWrapSingleEnumValue().process(context,
					valuePropertyInfo);
		}

		public Collection<CPropertyInfo> onSingleOtherValue(
				CValuePropertyInfo valuePropertyInfo) {
			logger.error("[" + valuePropertyInfo.getName(true)
					+ "] is a single other value. See issue #60.");
			return Collections.emptyList();
		}

		// Collection

		public Collection<CPropertyInfo> onCollectionBuiltinValue(
				CValuePropertyInfo valuePropertyInfo) {
			return context.getWrapCollectionBuiltinValue().process(context,
					valuePropertyInfo);
		}

		public Collection<CPropertyInfo> onCollectionEnumValue(
				CValuePropertyInfo valuePropertyInfo) {
			return context.getWrapCollectionEnumValue().process(context,
					valuePropertyInfo);
		}

		public Collection<CPropertyInfo> onCollectionOtherValue(
				CValuePropertyInfo valuePropertyInfo) {
			logger.error("[" + valuePropertyInfo.getName(true)
					+ "] is a collection other value. See issue #63.");
			return Collections.emptyList();
		}

		// Element

		// Single

		public Collection<CPropertyInfo> onSingleBuiltinElement(
				CElementPropertyInfo elementPropertyInfo) {
			return context.getWrapSingleBuiltinElement().process(context,
					elementPropertyInfo);
		}

		public Collection<CPropertyInfo> onSingleEnumElement(
				CElementPropertyInfo elementPropertyInfo) {
			return context.getWrapSingleEnumElement().process(context,
					elementPropertyInfo);
		}

		public Collection<CPropertyInfo> onSingleArrayElement(
				CElementPropertyInfo elementPropertyInfo) {
			throw new UnsupportedOperationException("Arrays are not supported.");
		}

		public Collection<CPropertyInfo> onSingleClassElement(
				CElementPropertyInfo elementPropertyInfo) {
			return Collections.emptyList();
		}

		public Collection<CPropertyInfo> onSingleHeteroElement(
				CElementPropertyInfo elementPropertyInfo) {
			return context.getWrapSingleHeteroElement().process(context,
					elementPropertyInfo);
		}

		// Collection

		public Collection<CPropertyInfo> onCollectionBuiltinElement(
				CElementPropertyInfo elementPropertyInfo) {
			return context.getWrapCollectionBuiltinElement().process(context,
					elementPropertyInfo);
		}

		public Collection<CPropertyInfo> onCollectionEnumElement(
				CElementPropertyInfo elementPropertyInfo) {
			return context.getWrapCollectionEnumElement().process(context,
					elementPropertyInfo);
		}

		public Collection<CPropertyInfo> onCollectionArrayElement(
				CElementPropertyInfo elementPropertyInfo) {
			throw new UnsupportedOperationException("Arrays are not supported.");
		}

		public Collection<CPropertyInfo> onCollectionClassElement(
				CElementPropertyInfo elementPropertyInfo) {
			return Collections.emptyList();
		}

		public Collection<CPropertyInfo> onCollectionHeteroElement(
				CElementPropertyInfo elementPropertyInfo) {
			return context.getWrapCollectionHeteroElement().process(context,
					elementPropertyInfo);
		}

		// / Reference

		// Single

		public Collection<CPropertyInfo> onSingleBuiltinElementReference(
				CReferencePropertyInfo referencePropertyInfo) {
			return context.getWrapSingleBuiltinElementReference().process(
					context, referencePropertyInfo);
		}

		public Collection<CPropertyInfo> onSingleEnumElementReference(
				CReferencePropertyInfo referencePropertyInfo) {
			return context.getWrapSingleEnumElementReference().process(context,
					referencePropertyInfo);
		}

		public Collection<CPropertyInfo> onSingleArrayElementReference(
				CReferencePropertyInfo referencePropertyInfo) {
			throw new UnsupportedOperationException("Arrays are not supported.");
		}

		// public Collection<CPropertyInfo> onSingleElementReference(
		// CReferencePropertyInfo referencePropertyInfo) {
		// logger.error("[" + referencePropertyInfo.getName(true)
		// + "] is a single element reference. See issue #65.");
		// return Collections.emptyList();
		// }

		public Collection<CPropertyInfo> onSingleClassReference(
				CReferencePropertyInfo referencePropertyInfo) {
			// logger.error("[" + referencePropertyInfo.getName(true)
			// + "] is a single class reference. See issue #66.");
			// return Collections.emptyList();
			return context.getWrapSingleClassReference().process(context,
					referencePropertyInfo);
		}

		public Collection<CPropertyInfo> onSingleWildcardReference(
				CReferencePropertyInfo referencePropertyInfo) {
			return context.getWrapSingleWildcardReference().process(context,
					referencePropertyInfo);
		}

		public Collection<CPropertyInfo> onSingleHeteroReference(
				CReferencePropertyInfo referencePropertyInfo) {
			return context.getWrapSingleHeteroReference().process(context,
					referencePropertyInfo);
		}

		public Collection<CPropertyInfo> onSingleClassElementReference(
				CReferencePropertyInfo referencePropertyInfo) {
			return context.getWrapSingleClassElementReference().process(
					context, referencePropertyInfo);
		}

		public Collection<CPropertyInfo> onSingleSubstitutedElementReference(
				CReferencePropertyInfo referencePropertyInfo) {
			return context.getWrapSingleSubstitutedElementReference().process(
					context, referencePropertyInfo);
		}

		// Collection

		public Collection<CPropertyInfo> onCollectionBuiltinElementReference(
				CReferencePropertyInfo referencePropertyInfo) {

			logger.error("["
					+ referencePropertyInfo.getName(true)
					+ "] is a collection builtin element reference. See issue #67 (http://java.net/jira/browse/HYPERJAXB3-67).");
			return Collections.emptyList();
		}

		public Collection<CPropertyInfo> onCollectionEnumElementReference(
				CReferencePropertyInfo referencePropertyInfo) {
			logger.error("["
					+ referencePropertyInfo.getName(true)
					+ "] is a collection enum element reference. See issue #68 (http://java.net/jira/browse/HYPERJAXB3-68).");
			return Collections.emptyList();
		}

		public Collection<CPropertyInfo> onCollectionArrayElementReference(
				CReferencePropertyInfo referencePropertyInfo) {
			throw new UnsupportedOperationException("Arrays are not supported.");
		}

//		public Collection<CPropertyInfo> onCollectionElementReference(
//				CReferencePropertyInfo referencePropertyInfo) {
//			logger.error("[" + referencePropertyInfo.getName(true)
//					+ "] is a collection element reference. See issue #69.");
//			return Collections.emptyList();
//		}

		public Collection<CPropertyInfo> onCollectionClassReference(
				CReferencePropertyInfo referencePropertyInfo) {
			logger.error("[" + referencePropertyInfo.getName(true)
					+ "] is a collection class reference. See issue #70.");
			return Collections.emptyList();
		}

		public Collection<CPropertyInfo> onCollectionWildcardReference(
				CReferencePropertyInfo referencePropertyInfo) {
			return context.getWrapCollectionWildcardReference().process(
					context, referencePropertyInfo);
		}

		public Collection<CPropertyInfo> onCollectionClassElementReference(
				CReferencePropertyInfo referencePropertyInfo) {
			logger.error("["
					+ referencePropertyInfo.getName(true)
					+ "] is a collection class element reference. See issue #71.");
			return Collections.emptyList();
		}

		public Collection<CPropertyInfo> onCollectionSubstitutedElementReference(
				CReferencePropertyInfo referencePropertyInfo) {
			return context.getWrapCollectionHeteroReference().process(context,
					referencePropertyInfo);
		}

		public Collection<CPropertyInfo> onCollectionHeteroReference(
				CReferencePropertyInfo referencePropertyInfo) {
			return context.getWrapCollectionHeteroReference().process(context,
					referencePropertyInfo);
		}
	};
}
