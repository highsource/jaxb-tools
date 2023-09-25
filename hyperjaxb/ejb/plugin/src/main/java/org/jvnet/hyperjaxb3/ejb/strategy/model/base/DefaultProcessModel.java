package org.jvnet.hyperjaxb3.ejb.strategy.model.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.hyperjaxb3.ejb.plugin.EjbPlugin;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.strategy.customizing.Customizing;
import org.jvnet.hyperjaxb3.ejb.strategy.ignoring.Ignoring;
import org.jvnet.hyperjaxb3.ejb.strategy.model.AdaptTypeUse;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ClassInfoProcessor;
import org.jvnet.hyperjaxb3.ejb.strategy.model.CreateDefaultIdPropertyInfos;
import org.jvnet.hyperjaxb3.ejb.strategy.model.CreatePropertyInfos;
import org.jvnet.hyperjaxb3.ejb.strategy.model.GetTypes;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessClassInfo;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessPropertyInfos;
import org.jvnet.jaxb.util.CustomizationUtils;
import org.springframework.beans.factory.annotation.Required;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.Model;

public class DefaultProcessModel implements ProcessModel {

	protected Log logger = LogFactory.getLog(getClass());

	public Collection<CClassInfo> process(EjbPlugin context, Model model,
			Options options) throws Exception {

		CustomizationUtils.findCustomization(model,
				Customizations.PERSISTENCE_ELEMENT_NAME);

		logger.debug("Processing model [...].");

		final Collection<CClassInfo> unorderedClassInfos = model.beans().values();
		final CClassInfo[] classInfos = orderClassInfos(unorderedClassInfos)
				.toArray(new CClassInfo[0]);
		final Collection<CClassInfo> includedClasses = new HashSet<CClassInfo>();

		for (final CClassInfo classInfo : classInfos) {
			if (!getIgnoring().isClassInfoIgnored(this, classInfo)) {
				final Collection<CClassInfo> targetClassInfos = getProcessClassInfo()
						.process(this, classInfo);
				if (targetClassInfos != null) {
					for (final CClassInfo targetClassInfo : targetClassInfos) {
						includedClasses.add(targetClassInfo);
						// model.beans().put(targetClassInfo.getClazz(),
						// targetClassInfo);
						context.getCreatedClasses().add(targetClassInfo);
					}
				}
			}
		}
		return includedClasses;
	}

	private List<CClassInfo> orderClassInfos(Collection<CClassInfo> classInfos) {
		final List<CClassInfo> orderedClassInfos = new ArrayList<CClassInfo>(
				classInfos.size());
		final Set<CClassInfo> addedClassInfos = new HashSet<CClassInfo>();

		for (CClassInfo classInfo : classInfos) {
			orderClassInfo(classInfo, orderedClassInfos, addedClassInfos);
		}
		return Collections.unmodifiableList(orderedClassInfos);
	}

	private void orderClassInfo(CClassInfo classInfo,
			List<CClassInfo> orderedClassInfos, Set<CClassInfo> addedClassInfos) {
		if (!addedClassInfos.contains(classInfo)) {
			if (classInfo.getBaseClass() != null) {
				orderClassInfo(classInfo.getBaseClass(), orderedClassInfos,
						addedClassInfos);
			}
			orderedClassInfos.add(classInfo);
			addedClassInfos.add(classInfo);
		}
	}

	private ProcessClassInfo processClassInfo;

	public ProcessClassInfo getProcessClassInfo() {
		return processClassInfo;
	}

	@Required
	public void setProcessClassInfo(ProcessClassInfo processClassInfo) {
		this.processClassInfo = processClassInfo;
	}

	private ProcessPropertyInfos processPropertyInfos;

	public ProcessPropertyInfos getProcessPropertyInfos() {
		return processPropertyInfos;
	}

	@Required
	public void setProcessPropertyInfos(
			ProcessPropertyInfos processPropertyInfos) {
		this.processPropertyInfos = processPropertyInfos;
	}

	private ProcessClassInfo createIdClass;

	public ProcessClassInfo getCreateIdClass() {
		return createIdClass;
	}

	public void setCreateIdClass(ProcessClassInfo createIdClass) {
		this.createIdClass = createIdClass;
	}

	private CreateDefaultIdPropertyInfos createDefaultIdPropertyInfos;

	public CreateDefaultIdPropertyInfos getCreateDefaultIdPropertyInfos() {
		return createDefaultIdPropertyInfos;
	}

	@Required
	public void setCreateDefaultIdPropertyInfos(
			CreateDefaultIdPropertyInfos createDefaultIdPropertyInfos) {
		this.createDefaultIdPropertyInfos = createDefaultIdPropertyInfos;
	}

	private ClassInfoProcessor<Collection<CPropertyInfo>, ProcessModel> createDefaultVersionPropertyInfos;

	public ClassInfoProcessor<Collection<CPropertyInfo>, ProcessModel> getCreateDefaultVersionPropertyInfos() {
		return createDefaultVersionPropertyInfos;
	}

	@Required
	public void setCreateDefaultVersionPropertyInfos(
			ClassInfoProcessor<Collection<CPropertyInfo>, ProcessModel> createDefaultVersionPropertyInfos) {
		this.createDefaultVersionPropertyInfos = createDefaultVersionPropertyInfos;
	}

	private ClassInfoProcessor<Collection<CPropertyInfo>, ProcessModel> getIdPropertyInfos;

	public ClassInfoProcessor<Collection<CPropertyInfo>, ProcessModel> getGetIdPropertyInfos() {
		return getIdPropertyInfos;
	}

	public void setGetIdPropertyInfos(
			ClassInfoProcessor<Collection<CPropertyInfo>, ProcessModel> getIdPropertyInfos) {
		this.getIdPropertyInfos = getIdPropertyInfos;
	}

	private ClassInfoProcessor<Collection<CPropertyInfo>, ProcessModel> getVersionPropertyInfos;

	public ClassInfoProcessor<Collection<CPropertyInfo>, ProcessModel> getGetVersionPropertyInfos() {
		return getVersionPropertyInfos;
	}

	public void setGetVersionPropertyInfos(
			ClassInfoProcessor<Collection<CPropertyInfo>, ProcessModel> getVersionPropertyInfos) {
		this.getVersionPropertyInfos = getVersionPropertyInfos;
	}

	private CreatePropertyInfos wrapComplexHeteroCollection;

	public CreatePropertyInfos getWrapComplexHeteroCollection() {
		return this.wrapComplexHeteroCollection;
	}

	public void setWrapComplexHeteroCollection(
			CreatePropertyInfos wrapHeteroCollection) {
		this.wrapComplexHeteroCollection = wrapHeteroCollection;
	}

	private GetTypes<ProcessModel> getTypes;

	public GetTypes<ProcessModel> getGetTypes() {
		return getTypes;
	}

	@Required
	public void setGetTypes(GetTypes<ProcessModel> getTypes) {
		this.getTypes = getTypes;
	}

	private CreatePropertyInfos wrapSingleBuiltinAttribute;

	public CreatePropertyInfos getWrapSingleBuiltinAttribute() {
		return wrapSingleBuiltinAttribute;
	}

	@Required
	public void setWrapSingleBuiltinAttribute(
			CreatePropertyInfos wrapSingleBuiltinAttribute) {
		this.wrapSingleBuiltinAttribute = wrapSingleBuiltinAttribute;
	}

	private CreatePropertyInfos wrapSingleEnumAttribute;

	public CreatePropertyInfos getWrapSingleEnumAttribute() {
		return wrapSingleEnumAttribute;
	}

	@Required
	public void setWrapSingleEnumAttribute(
			CreatePropertyInfos wrapSingleEnumAttribute) {
		this.wrapSingleEnumAttribute = wrapSingleEnumAttribute;
	}

	private CreatePropertyInfos wrapCollectionBuiltinAttribute;

	public CreatePropertyInfos getWrapCollectionBuiltinAttribute() {
		return wrapCollectionBuiltinAttribute;
	}

	@Required
	public void setWrapCollectionBuiltinAttribute(
			CreatePropertyInfos wrapCollectionBuiltinAttribute) {
		this.wrapCollectionBuiltinAttribute = wrapCollectionBuiltinAttribute;
	}

	private CreatePropertyInfos wrapCollectionEnumAttribute;

	public CreatePropertyInfos getWrapCollectionEnumAttribute() {
		return wrapCollectionEnumAttribute;
	}

	@Required
	public void setWrapCollectionEnumAttribute(
			CreatePropertyInfos wrapCollectionEnumAttribute) {
		this.wrapCollectionEnumAttribute = wrapCollectionEnumAttribute;
	}

	private CreatePropertyInfos wrapSingleBuiltinValue;

	public CreatePropertyInfos getWrapSingleBuiltinValue() {
		return wrapSingleBuiltinValue;
	}

	@Required
	public void setWrapSingleBuiltinValue(
			CreatePropertyInfos wrapSingleBuiltinValue) {
		this.wrapSingleBuiltinValue = wrapSingleBuiltinValue;
	}

	private CreatePropertyInfos wrapSingleEnumValue;

	public CreatePropertyInfos getWrapSingleEnumValue() {
		return wrapSingleEnumValue;
	}

	@Required
	public void setWrapSingleEnumValue(CreatePropertyInfos wrapSingleEnumValue) {
		this.wrapSingleEnumValue = wrapSingleEnumValue;
	}

	private CreatePropertyInfos wrapCollectionBuiltinValue;

	public CreatePropertyInfos getWrapCollectionBuiltinValue() {
		return wrapCollectionBuiltinValue;
	}

	@Required
	public void setWrapCollectionBuiltinValue(
			CreatePropertyInfos wrapCollectionBuiltinValue) {
		this.wrapCollectionBuiltinValue = wrapCollectionBuiltinValue;
	}

	private CreatePropertyInfos wrapCollectionEnumValue;

	public CreatePropertyInfos getWrapCollectionEnumValue() {
		return wrapCollectionEnumValue;
	}

	@Required
	public void setWrapCollectionEnumValue(
			CreatePropertyInfos wrapCollectionEnumValue) {
		this.wrapCollectionEnumValue = wrapCollectionEnumValue;
	}

	private CreatePropertyInfos wrapSingleBuiltinElement;

	public CreatePropertyInfos getWrapSingleBuiltinElement() {
		return wrapSingleBuiltinElement;
	}

	@Required
	public void setWrapSingleBuiltinElement(
			CreatePropertyInfos wrapSingleBuiltinElement) {
		this.wrapSingleBuiltinElement = wrapSingleBuiltinElement;
	}

	private CreatePropertyInfos wrapSingleEnumElement;

	public CreatePropertyInfos getWrapSingleEnumElement() {
		return wrapSingleEnumElement;
	}

	@Required
	public void setWrapSingleEnumElement(
			CreatePropertyInfos wrapSingleEnumElement) {
		this.wrapSingleEnumElement = wrapSingleEnumElement;
	}

	private CreatePropertyInfos wrapSingleHeteroElement;

	public CreatePropertyInfos getWrapSingleHeteroElement() {
		return wrapSingleHeteroElement;
	}

	@Required
	public void setWrapSingleHeteroElement(
			CreatePropertyInfos wrapSingleHeteroElement) {
		this.wrapSingleHeteroElement = wrapSingleHeteroElement;
	}

	private CreatePropertyInfos wrapCollectionBuiltinElement;

	public CreatePropertyInfos getWrapCollectionBuiltinElement() {
		return wrapCollectionBuiltinElement;
	}

	@Required
	public void setWrapCollectionBuiltinElement(
			CreatePropertyInfos wrapCollectionBuiltinElement) {
		this.wrapCollectionBuiltinElement = wrapCollectionBuiltinElement;
	}

	private CreatePropertyInfos wrapCollectionEnumElement;

	public CreatePropertyInfos getWrapCollectionEnumElement() {
		return wrapCollectionEnumElement;
	}

	@Required
	public void setWrapCollectionEnumElement(
			CreatePropertyInfos wrapCollectionEnumElement) {
		this.wrapCollectionEnumElement = wrapCollectionEnumElement;
	}

	private CreatePropertyInfos wrapCollectionHeteroElement;

	public CreatePropertyInfos getWrapCollectionHeteroElement() {
		return wrapCollectionHeteroElement;
	}

	@Required
	public void setWrapCollectionHeteroElement(
			CreatePropertyInfos wrapCollectionHeteroElement) {
		this.wrapCollectionHeteroElement = wrapCollectionHeteroElement;
	}

	// Reference

	private CreatePropertyInfos wrapSingleBuiltinElementReference;

	public CreatePropertyInfos getWrapSingleBuiltinElementReference() {
		return wrapSingleBuiltinElementReference;
	}

	@Required
	public void setWrapSingleBuiltinElementReference(
			CreatePropertyInfos wrapSingleBuiltinElementReference) {
		this.wrapSingleBuiltinElementReference = wrapSingleBuiltinElementReference;
	}

	private CreatePropertyInfos wrapSingleEnumElementReference;

	public CreatePropertyInfos getWrapSingleEnumElementReference() {
		return wrapSingleEnumElementReference;
	}

	@Required
	public void setWrapSingleEnumElementReference(
			CreatePropertyInfos wrapSingleEnumElementReference) {
		this.wrapSingleEnumElementReference = wrapSingleEnumElementReference;
	}

	private CreatePropertyInfos wrapSingleClassElementReference;

	public CreatePropertyInfos getWrapSingleClassElementReference() {
		return wrapSingleClassElementReference;
	}

	@Required
	public void setWrapSingleClassElementReference(
			CreatePropertyInfos wrapSingleClassElementReference) {
		this.wrapSingleClassElementReference = wrapSingleClassElementReference;
	}

	private CreatePropertyInfos wrapSingleSubstitutedElementReference;

	public CreatePropertyInfos getWrapSingleSubstitutedElementReference() {
		return wrapSingleSubstitutedElementReference;
	}

	@Required
	public void setWrapSingleSubstitutedElementReference(
			CreatePropertyInfos wrapSingleSubstitutedElementReference) {
		this.wrapSingleSubstitutedElementReference = wrapSingleSubstitutedElementReference;
	}

	private CreatePropertyInfos wrapSingleHeteroReference;

	public CreatePropertyInfos getWrapSingleHeteroReference() {
		return wrapSingleHeteroReference;
	}

	@Required
	public void setWrapSingleHeteroReference(
			CreatePropertyInfos wrapSingleHeteroReference) {
		this.wrapSingleHeteroReference = wrapSingleHeteroReference;
	}

	private CreatePropertyInfos wrapSingleClassReference;

	public CreatePropertyInfos getWrapSingleClassReference() {
		return wrapSingleClassReference;
	}

	@Required
	public void setWrapSingleClassReference(
			CreatePropertyInfos wrapSingleClassReference) {
		this.wrapSingleClassReference = wrapSingleClassReference;
	}

	private CreatePropertyInfos wrapSingleWildcardReference;

	public CreatePropertyInfos getWrapSingleWildcardReference() {
		return wrapSingleWildcardReference;
	}

	@Required
	public void setWrapSingleWildcardReference(
			CreatePropertyInfos wrapSingleWildcardReference) {
		this.wrapSingleWildcardReference = wrapSingleWildcardReference;
	}

	private CreatePropertyInfos wrapCollectionHeteroReference;

	public CreatePropertyInfos getWrapCollectionHeteroReference() {
		return wrapCollectionHeteroReference;
	}

	public void setWrapCollectionHeteroReference(
			CreatePropertyInfos wrapCollectionHeteroReference) {
		this.wrapCollectionHeteroReference = wrapCollectionHeteroReference;
	}

	private CreatePropertyInfos wrapCollectionWildcardReference;

	public CreatePropertyInfos getWrapCollectionWildcardReference() {
		return wrapCollectionWildcardReference;
	}

	public void setWrapCollectionWildcardReference(
			CreatePropertyInfos wrapCollectionWildcardReference) {
		this.wrapCollectionWildcardReference = wrapCollectionWildcardReference;
	}

	private AdaptTypeUse adaptBuiltinTypeUse;

	public AdaptTypeUse getAdaptBuiltinTypeUse() {
		return adaptBuiltinTypeUse;
	}

	@Required
	public void setAdaptBuiltinTypeUse(AdaptTypeUse adaptBuiltinTypeUse) {
		this.adaptBuiltinTypeUse = adaptBuiltinTypeUse;
	}

	private Ignoring ignoring;// = new DefaultIgnoring();

	public Ignoring getIgnoring() {
		return ignoring;
	}

	public void setIgnoring(Ignoring ignoring) {
		this.ignoring = ignoring;
	}

	private Customizing customizing;

	public Customizing getCustomizing() {
		return customizing;
	}

	@Required
	public void setCustomizing(Customizing customizations) {
		this.customizing = customizations;
	}
}
