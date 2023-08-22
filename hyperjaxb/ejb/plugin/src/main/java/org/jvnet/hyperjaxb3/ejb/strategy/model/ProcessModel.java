package org.jvnet.hyperjaxb3.ejb.strategy.model;

import java.util.Collection;

import org.jvnet.hyperjaxb3.ejb.plugin.EjbPlugin;
import org.jvnet.hyperjaxb3.ejb.strategy.customizing.Customizing;
import org.jvnet.hyperjaxb3.ejb.strategy.ignoring.Ignoring;

import com.sun.tools.xjc.model.CPropertyInfo;

public interface ProcessModel extends ModelProcessor<EjbPlugin> {

	public ProcessClassInfo getProcessClassInfo();

	public ProcessPropertyInfos getProcessPropertyInfos();

	public CreateDefaultIdPropertyInfos getCreateDefaultIdPropertyInfos();

	public ClassInfoProcessor<Collection<CPropertyInfo>, ProcessModel> getCreateDefaultVersionPropertyInfos();

	public ClassInfoProcessor<Collection<CPropertyInfo>, ProcessModel> getGetIdPropertyInfos();

	public ClassInfoProcessor<Collection<CPropertyInfo>, ProcessModel> getGetVersionPropertyInfos();

	public ProcessClassInfo getCreateIdClass();

	public GetTypes<ProcessModel> getGetTypes();

	// Value

	public CreatePropertyInfos getWrapSingleBuiltinValue();

	public CreatePropertyInfos getWrapSingleEnumValue();

	public CreatePropertyInfos getWrapCollectionBuiltinValue();

	public CreatePropertyInfos getWrapCollectionEnumValue();

	// Attribute

	public CreatePropertyInfos getWrapSingleBuiltinAttribute();

	public CreatePropertyInfos getWrapSingleEnumAttribute();

	public CreatePropertyInfos getWrapCollectionBuiltinAttribute();

	public CreatePropertyInfos getWrapCollectionEnumAttribute();

	// Element

	public CreatePropertyInfos getWrapSingleBuiltinElement();

	public CreatePropertyInfos getWrapSingleEnumElement();

	public CreatePropertyInfos getWrapSingleHeteroElement();

	public CreatePropertyInfos getWrapCollectionBuiltinElement();

	public CreatePropertyInfos getWrapCollectionEnumElement();

	public CreatePropertyInfos getWrapCollectionHeteroElement();

	// Reference

	public CreatePropertyInfos getWrapSingleBuiltinElementReference();

	public CreatePropertyInfos getWrapSingleEnumElementReference();

	public CreatePropertyInfos getWrapSingleClassElementReference();

	public CreatePropertyInfos getWrapSingleSubstitutedElementReference();

	public CreatePropertyInfos getWrapSingleClassReference();

	public CreatePropertyInfos getWrapSingleWildcardReference();

	public CreatePropertyInfos getWrapSingleHeteroReference();

	public CreatePropertyInfos getWrapCollectionHeteroReference();

	public CreatePropertyInfos getWrapCollectionWildcardReference();

	public AdaptTypeUse getAdaptBuiltinTypeUse();

	public Ignoring getIgnoring();

	public Customizing getCustomizing();
}
