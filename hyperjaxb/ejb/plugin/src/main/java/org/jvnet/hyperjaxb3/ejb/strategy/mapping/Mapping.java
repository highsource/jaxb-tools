package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import org.jvnet.hyperjaxb3.ejb.strategy.customizing.Customizing;
import org.jvnet.hyperjaxb3.ejb.strategy.ignoring.Ignoring;
import org.jvnet.hyperjaxb3.ejb.strategy.model.GetTypes;
import org.jvnet.hyperjaxb3.ejb.strategy.naming.Naming;

import com.sun.java.xml.ns.persistence.orm.Attributes;
import com.sun.java.xml.ns.persistence.orm.Basic;
import com.sun.java.xml.ns.persistence.orm.ElementCollection;
import com.sun.java.xml.ns.persistence.orm.Embeddable;
import com.sun.java.xml.ns.persistence.orm.EmbeddableAttributes;
import com.sun.java.xml.ns.persistence.orm.Embedded;
import com.sun.java.xml.ns.persistence.orm.EmbeddedId;
import com.sun.java.xml.ns.persistence.orm.Entity;
import com.sun.java.xml.ns.persistence.orm.Id;
import com.sun.java.xml.ns.persistence.orm.ManyToMany;
import com.sun.java.xml.ns.persistence.orm.ManyToOne;
import com.sun.java.xml.ns.persistence.orm.MappedSuperclass;
import com.sun.java.xml.ns.persistence.orm.OneToMany;
import com.sun.java.xml.ns.persistence.orm.OneToOne;
import com.sun.java.xml.ns.persistence.orm.Transient;
import com.sun.java.xml.ns.persistence.orm.Version;
import com.sun.tools.xjc.outline.FieldOutline;

public class Mapping implements Cloneable {

	@Override
	public Mapping clone() {
		try {
			return (Mapping) super.clone();
		} catch (CloneNotSupportedException cnsex) {
			throw new UnsupportedOperationException(cnsex);
		}
	}

	private GetTypes<Mapping> getTypes;

	public GetTypes<Mapping> getGetTypes() {
		return getTypes;
	}

	public void setGetTypes(GetTypes<Mapping> getTypes) {
		this.getTypes = getTypes;
	}

	private ClassOutlineMapping<Object> entityOrMappedSuperclassOrEmbeddableMapping = new EntityOrMappedSuperclassOrEmbeddableMapping();

	public ClassOutlineMapping<Object> getEntityOrMappedSuperclassOrEmbeddableMapping() {
		return entityOrMappedSuperclassOrEmbeddableMapping;
	}

	public void setEntityOrMappedSuperclassOrEmbeddableMapping(
			ClassOutlineMapping<Object> entityOrMappedSuperclassMapping) {
		this.entityOrMappedSuperclassOrEmbeddableMapping = entityOrMappedSuperclassMapping;
	}

	private ClassOutlineMapping<Entity> entityMapping = new EntityMapping();

	public ClassOutlineMapping<Entity> getEntityMapping() {
		return entityMapping;
	}

	public void setEntityMapping(ClassOutlineMapping<Entity> entityMapping) {
		this.entityMapping = entityMapping;
	}

	private ClassOutlineMapping<MappedSuperclass> mappedSuperclassMapping = new MappedSuperclassMapping();

	public ClassOutlineMapping<MappedSuperclass> getMappedSuperclassMapping() {
		return mappedSuperclassMapping;
	}

	public void setMappedSuperclassMapping(
			ClassOutlineMapping<MappedSuperclass> mappedSuperclassMapping) {
		this.mappedSuperclassMapping = mappedSuperclassMapping;
	}

	private ClassOutlineMapping<Embeddable> embeddableMapping = new EmbeddableMapping();

	public ClassOutlineMapping<Embeddable> getEmbeddableMapping() {
		return embeddableMapping;
	}

	public void setEmbeddableMapping(
			ClassOutlineMapping<Embeddable> embeddableMapping) {
		this.embeddableMapping = embeddableMapping;
	}

	private ClassOutlineMapping<Attributes> attributesMapping = new AttributesMapping();

	public ClassOutlineMapping<Attributes> getAttributesMapping() {
		return attributesMapping;
	}

	public void setAttributesMapping(
			ClassOutlineMapping<Attributes> attributesMapping) {
		this.attributesMapping = attributesMapping;
	}

	private ClassOutlineMapping<EmbeddableAttributes> embeddableAttributesMapping;// =
																					// new
																					// EmbeddableAttributesMapping();

	public ClassOutlineMapping<EmbeddableAttributes> getEmbeddableAttributesMapping() {
		return embeddableAttributesMapping;
	}

	public void setEmbeddableAttributesMapping(
			ClassOutlineMapping<EmbeddableAttributes> embeddableAttributesMapping) {
		this.embeddableAttributesMapping = embeddableAttributesMapping;
	}

	private FieldOutlineMapping<Id> idMapping = new IdMapping();

	public FieldOutlineMapping<Id> getIdMapping() {
		return idMapping;
	}

	public void setIdMapping(FieldOutlineMapping<Id> idMapping) {
		this.idMapping = idMapping;
	}

	private FieldOutlineMapping<EmbeddedId> embeddedIdMapping = new EmbeddedIdMapping();

	public FieldOutlineMapping<EmbeddedId> getEmbeddedIdMapping() {
		return embeddedIdMapping;
	}

	public void setEmbeddedIdMapping(
			FieldOutlineMapping<EmbeddedId> embeddedIdMapping) {
		this.embeddedIdMapping = embeddedIdMapping;
	}

	private FieldOutlineMapping<Basic> basicMapping = new BasicMapping();

	public FieldOutlineMapping<Basic> getBasicMapping() {
		return basicMapping;
	}

	public void setBasicMapping(FieldOutlineMapping<Basic> basicMapping) {
		this.basicMapping = basicMapping;
	}

	private FieldOutlineMapping<Version> versionMapping = new VersionMapping();

	public FieldOutlineMapping<Version> getVersionMapping() {
		return versionMapping;
	}

	public void setVersionMapping(FieldOutlineMapping<Version> versionMapping) {
		this.versionMapping = versionMapping;
	}

	/*
	 * private FieldOutlineMapping<EmbeddedId> embeddedIdMaping;// = new //
	 * EmbeddedIdMapping();
	 * 
	 * public FieldOutlineMapping<EmbeddedId> getEmbeddedIdMapping() { throw new
	 * UnsupportedOperationException(); // return embeddedIdMaping; }
	 * 
	 * public void setEmbeddedIdMaping( FieldOutlineMapping<EmbeddedId>
	 * embeddedIdMaping) { this.embeddedIdMaping = embeddedIdMaping; }
	 */

	private FieldOutlineMapping<Embedded> embeddedMapping = new EmbeddedMapping();

	public FieldOutlineMapping<Embedded> getEmbeddedMapping() {
		return embeddedMapping;
	}

	public void setEmbeddedMapping(FieldOutlineMapping<Embedded> embeddedMapping) {
		this.embeddedMapping = embeddedMapping;
	}

	private FieldOutlineMapping<?> toOneMapping = new ToOneMapping();

	public FieldOutlineMapping<?> getToOneMapping() {
		return toOneMapping;
	}

	public void setToOneMapping(FieldOutlineMapping<?> toOneMapping) {
		this.toOneMapping = toOneMapping;
	}

	private FieldOutlineMapping<ElementCollection> elementCollectionMapping = new ElementCollectionMapping();

	public FieldOutlineMapping<ElementCollection> getElementCollectionMapping() {
		return elementCollectionMapping;
	}

	public void setElementCollectionMapping(
			FieldOutlineMapping<ElementCollection> elementCollectionMapping) {
		this.elementCollectionMapping = elementCollectionMapping;
	}

	private FieldOutlineMapping<ManyToOne> manyToOneMapping = new ManyToOneMapping();

	public FieldOutlineMapping<ManyToOne> getManyToOneMapping() {
		return manyToOneMapping;
	}

	public void setManyToOneMapping(
			FieldOutlineMapping<ManyToOne> manyToOneMapping) {
		this.manyToOneMapping = manyToOneMapping;
	}

	private FieldOutlineMapping<OneToOne> oneToOneMapping = new OneToOneMapping();

	public FieldOutlineMapping<OneToOne> getOneToOneMapping() {
		return oneToOneMapping;
	}

	public void setOneToOneMapping(FieldOutlineMapping<OneToOne> oneToOneMapping) {
		this.oneToOneMapping = oneToOneMapping;
	}

	private FieldOutlineMapping<?> toManyMapping = new ToManyMapping();

	public FieldOutlineMapping<?> getToManyMapping() {
		return toManyMapping;
	}

	public void setToManyMapping(FieldOutlineMapping<?> toManyMapping) {
		this.toManyMapping = toManyMapping;
	}

	private FieldOutlineMapping<OneToMany> oneToManyMapping = new OneToManyMapping();

	public FieldOutlineMapping<OneToMany> getOneToManyMapping() {
		return oneToManyMapping;
	}

	public void setOneToManyMapping(
			FieldOutlineMapping<OneToMany> oneToManyMapping) {
		this.oneToManyMapping = oneToManyMapping;
	}

	private FieldOutlineMapping<ManyToMany> manyToManyMapping = new ManyToManyMapping();

	public FieldOutlineMapping<ManyToMany> getManyToManyMapping() {
		return manyToManyMapping;
	}

	public void setManyToManyMapping(
			FieldOutlineMapping<ManyToMany> manyToManyMapping) {
		this.manyToManyMapping = manyToManyMapping;
	}

	private FieldOutlineMapping<Transient> transientMapping = new TransientMapping();

	public FieldOutlineMapping<Transient> getTransientMapping() {
		return transientMapping;
	}

	public void setTransientMapping(
			FieldOutlineMapping<Transient> transientMapping) {
		this.transientMapping = transientMapping;
	}

	private Customizing customizing;

	public Customizing getCustomizing() {
		return customizing;
	}

	public void setCustomizing(Customizing modelCustomizations) {
		this.customizing = modelCustomizations;
	}

	private Naming naming;

	public Naming getNaming() {
		return naming;
	}

	public void setNaming(Naming naming) {
		this.naming = naming;
	}

	private Ignoring ignoring;

	public Ignoring getIgnoring() {
		return ignoring;
	}

	public void setIgnoring(Ignoring ignoring) {
		this.ignoring = ignoring;
	}

	public Mapping createEmbeddedMapping(Mapping context, FieldOutline fieldOutline) {
		// TODO Rework with wrappers
		final Mapping embeddedMapping = clone();
		embeddedMapping.setNaming(getNaming()
				.createEmbeddedNaming(context, fieldOutline));
		embeddedMapping.setAssociationMapping(getAssociationMapping()
				.createEmbeddedAssociationMapping(fieldOutline));
		return embeddedMapping;

	}

	private AssociationMapping associationMapping = new DefaultAssociationMapping();

	public AssociationMapping getAssociationMapping() {
		return associationMapping;
	}

	public void setAssociationMapping(AssociationMapping associationMapping) {
		this.associationMapping = associationMapping;
	}

	private AttributeMapping defaultAttributeMapping = new DefaultAttributeMapping();

	public AttributeMapping getAttributeMapping() {
		return defaultAttributeMapping;
	}

	public void setAttributeMapping(AttributeMapping defaultAttributeMapping) {
		this.defaultAttributeMapping = defaultAttributeMapping;
	}

}
