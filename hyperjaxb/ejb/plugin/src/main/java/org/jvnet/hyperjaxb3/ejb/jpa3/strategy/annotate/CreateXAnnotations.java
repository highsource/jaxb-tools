package org.jvnet.hyperjaxb3.ejb.jpa3.strategy.annotate;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import jakarta.persistence.AccessType;
import jakarta.persistence.EnumType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.LockModeType;
import jakarta.persistence.MapKeyJoinColumns;
import jakarta.persistence.QueryHint;
import jakarta.persistence.TemporalType;

import org.jvnet.jaxb.annox.model.XAnnotation;
import org.jvnet.jaxb.annox.model.annotation.field.XSingleAnnotationField;
import org.jvnet.jaxb.annox.model.annotation.value.XClassByNameAnnotationValue;
import org.jvnet.jaxb.annox.model.annotation.value.XEnumAnnotationValue;
import org.jvnet.hyperjaxb3.annotation.util.AnnotationUtils;

import jakarta.xml.ns.persistence.orm.AssociationOverride;
import jakarta.xml.ns.persistence.orm.Basic;
import jakarta.xml.ns.persistence.orm.CascadeType;
import jakarta.xml.ns.persistence.orm.CollectionTable;
import jakarta.xml.ns.persistence.orm.ElementCollection;
import jakarta.xml.ns.persistence.orm.Embeddable;
import jakarta.xml.ns.persistence.orm.Embedded;
import jakarta.xml.ns.persistence.orm.EmbeddedId;
import jakarta.xml.ns.persistence.orm.Entity;
import jakarta.xml.ns.persistence.orm.Id;
import jakarta.xml.ns.persistence.orm.ManyToMany;
import jakarta.xml.ns.persistence.orm.ManyToOne;
import jakarta.xml.ns.persistence.orm.MapKeyClass;
import jakarta.xml.ns.persistence.orm.MapKeyColumn;
import jakarta.xml.ns.persistence.orm.MapKeyJoinColumn;
import jakarta.xml.ns.persistence.orm.MappedSuperclass;
import jakarta.xml.ns.persistence.orm.NamedQuery;
import jakarta.xml.ns.persistence.orm.OneToMany;
import jakarta.xml.ns.persistence.orm.OneToOne;
import jakarta.xml.ns.persistence.orm.OrderColumn;
import jakarta.xml.ns.persistence.orm.SequenceGenerator;
import jakarta.xml.ns.persistence.orm.UniqueConstraint;
import jakarta.xml.ns.persistence.orm.Version;

public class CreateXAnnotations extends
		org.jvnet.hyperjaxb3.ejb.strategy.annotate.CreateXAnnotations {

	// 9.1.8
	public XAnnotation<jakarta.persistence.Id> createId(Boolean source) {
		return source == null ? null : createId(source.booleanValue());
	}

	public XAnnotation<jakarta.persistence.Id> createId(boolean source) {
		return !source ? null : new XAnnotation<jakarta.persistence.Id>(
				jakarta.persistence.Id.class);
	}

	public XAnnotation<jakarta.persistence.MapsId> createMapsId(String source) {
		return source == null ? null
				: new XAnnotation<jakarta.persistence.MapsId>(
						jakarta.persistence.MapsId.class, AnnotationUtils.create(
								"value", source));

	}

	public XAnnotation<jakarta.persistence.Access> createAccess(String access) {
		return access == null ? null
				: new XAnnotation<jakarta.persistence.Access>(
						jakarta.persistence.Access.class, AnnotationUtils.create(
								"value", AccessType.valueOf(access)));

	}

	public XAnnotation<jakarta.persistence.Cacheable> createCacheable(
			Boolean cacheable) {
		return cacheable == null ? null :
		//
				new XAnnotation<jakarta.persistence.Cacheable>(
						jakarta.persistence.Cacheable.class,
						//
						AnnotationUtils.create("value", cacheable)
				//
				);
	}

	@Override
	public Collection<XAnnotation<?>> createEntityAnnotations(Entity source) {

		final Collection<XAnnotation<?>> annotations = super
				.createEntityAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createCacheable(source.isCacheable()));
	}

	@Override
	public Collection<XAnnotation<?>> createBasicAnnotations(Basic source) {
		final Collection<XAnnotation<?>> annotations = super
				.createBasicAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createAccess(source.getAccess()));
	}

	@Override
	public Collection<XAnnotation<?>> createEmbeddableAnnotations(
			Embeddable source) {
		final Collection<XAnnotation<?>> annotations = super
				.createEmbeddableAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createAccess(source.getAccess()));
	}

	@Override
	public Collection<XAnnotation<?>> createEmbeddedAnnotations(Embedded source) {
		final Collection<XAnnotation<?>> annotations = super
				.createEmbeddedAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createAccess(source.getAccess()),
				createAssociationOverrides(source.getAssociationOverride()));
	}

	@Override
	public Collection<XAnnotation<?>> createEmbeddedIdAnnotations(
			EmbeddedId source) {
		Collection<XAnnotation<?>> annotations = super
				.createEmbeddedIdAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createAccess(source.getAccess()));
	}

	@Override
	public Collection<XAnnotation<?>> createIdAnnotations(Id source) {
		final Collection<XAnnotation<?>> annotations = super
				.createIdAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createAccess(source.getAccess()));
	}

	@Override
	public Collection<XAnnotation<?>> createManyToManyAnnotations(
			ManyToMany source) {
		final Collection<XAnnotation<?>> annotations = super
				.createManyToManyAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createAccess(source.getAccess()),
				createOrderColumn(source.getOrderColumn()),
				createMapKeyClass(source.getMapKeyClass()),
				createMapKeyTemporal(source.getMapKeyTemporal()),
				createMapKeyEnumerated(source.getMapKeyTemporal()),
				// createMapKeyAttributeOverride(source.getMapKeyAttributeOverride()),
				createMapKeyColumn(source.getMapKeyColumn()),
				createMapKeyJoinColumns(source.getMapKeyJoinColumn()));
	}

	@Override
	public Collection<XAnnotation<?>> createManyToOneAnnotations(
			ManyToOne source) {
		final Collection<XAnnotation<?>> annotations = super
				.createManyToOneAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createAccess(source.getAccess()),
				createMapsId(source.getMapsId()), createId(source.isId()));
	}

	@Override
	public Collection<XAnnotation<?>> createMappedSuperclassAnnotations(
			MappedSuperclass source) {
		Collection<XAnnotation<?>> annotations = super
				.createMappedSuperclassAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createAccess(source.getAccess()));
	}

	@Override
	public XAnnotation<jakarta.persistence.OneToMany> createOneToMany(
			OneToMany cOneToMany) {
		return cOneToMany == null ? null :
		//
				new XAnnotation<jakarta.persistence.OneToMany>(
						jakarta.persistence.OneToMany.class,
						//
						cOneToMany.getTargetEntity() == null ? null

						: new XSingleAnnotationField<Class<Object>>(
								"targetEntity", Class.class,
								new XClassByNameAnnotationValue<Object>(
										cOneToMany.getTargetEntity())),
						//
						AnnotationUtils.create("cascade",
								getCascadeType(cOneToMany.getCascade())),
						//
						AnnotationUtils.create("fetch",
								getFetchType(cOneToMany.getFetch())),
						//
						AnnotationUtils.create("mappedBy",
								cOneToMany.getMappedBy()),
						//
						AnnotationUtils.create("orphanRemoval",
								cOneToMany.isOrphanRemoval())
				//
				);
	}

	@Override
	public Collection<XAnnotation<?>> createOneToManyAnnotations(
			OneToMany source) {
		final Collection<XAnnotation<?>> annotations = super
				.createOneToManyAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createAccess(source.getAccess()),
				createOrderColumn(source.getOrderColumn()),
				createMapKeyClass(source.getMapKeyClass()),
				createMapKeyTemporal(source.getMapKeyTemporal()),
				createMapKeyEnumerated(source.getMapKeyTemporal()),
				// createMapKeyAttributeOverride(source.getMapKeyAttributeOverride()),
				createMapKeyColumn(source.getMapKeyColumn()),
				createMapKeyJoinColumns(source.getMapKeyJoinColumn()));
	}

	// 9.1.23
	public XAnnotation<jakarta.persistence.OneToOne> createOneToOne(
			OneToOne cOneToOne) {
		return cOneToOne == null ? null :
		//
				new XAnnotation<jakarta.persistence.OneToOne>(
						jakarta.persistence.OneToOne.class,
						//
						cOneToOne.getTargetEntity() == null ? null :

						new XSingleAnnotationField<Class<Object>>(
								"targetEntity", Class.class,
								new XClassByNameAnnotationValue<Object>(
										cOneToOne.getTargetEntity())),
						//
						AnnotationUtils.create("cascade",
								getCascadeType(cOneToOne.getCascade())),
						//
						AnnotationUtils.create("fetch",
								getFetchType(cOneToOne.getFetch())),
						//
						AnnotationUtils.create("optional",
								cOneToOne.isOptional()),
						//
						AnnotationUtils.create("mappedBy",
								cOneToOne.getMappedBy()),
						//
						AnnotationUtils.create("orphanRemoval",
								cOneToOne.isOrphanRemoval())
				//
				);
	}

	@Override
	public Collection<XAnnotation<?>> createOneToOneAnnotations(OneToOne source) {
		final Collection<XAnnotation<?>> annotations = super
				.createOneToOneAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createAccess(source.getAccess()),
				createMapsId(source.getMapsId()), createId(source.isId()));
	}

	@Override
	public Collection<XAnnotation<?>> createVersionAnnotations(Version source) {
		final Collection<XAnnotation<?>> annotations = super
				.createVersionAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createAccess(source.getAccess()));
	}

	@Override
	public XAnnotation<jakarta.persistence.UniqueConstraint> createUniqueConstraint(
			UniqueConstraint source) {
		return source == null ? null :
		//
				new XAnnotation<jakarta.persistence.UniqueConstraint>(
						jakarta.persistence.UniqueConstraint.class,
						//
						AnnotationUtils.create("name", source.getName()),
						AnnotationUtils.create(
								"columnNames",
								source.getColumnName().toArray(
										new String[source.getColumnName()
												.size()]))
				//
				);
	}

	@Override
	public XAnnotation<jakarta.persistence.SequenceGenerator> createSequenceGenerator(
			SequenceGenerator source) {
		return source == null ? null :
		//
				new XAnnotation<jakarta.persistence.SequenceGenerator>(
						jakarta.persistence.SequenceGenerator.class,
						//
						AnnotationUtils.create("name", source.getName()),
						//
						AnnotationUtils.create("sequenceName",
								source.getSequenceName()),
						//
						AnnotationUtils.create("catalog", source.getCatalog()),
						//
						AnnotationUtils.create("schema", source.getSchema()),
						//
						AnnotationUtils.create("initialValue",
								source.getInitialValue()),
						//
						AnnotationUtils.create("allocationSize",
								source.getAllocationSize()));
	}

	// 8.3.1
	public XAnnotation<jakarta.persistence.NamedQuery> createNamedQuery(
			NamedQuery source) {
		return source == null ? null :
		//
				new XAnnotation<jakarta.persistence.NamedQuery>(
						jakarta.persistence.NamedQuery.class,
						//
						AnnotationUtils.create("query", source.getQuery()),
						//
						AnnotationUtils.create("hints",
								createQueryHint(source.getHint()),
								QueryHint.class),
						//
						AnnotationUtils.create("name", source.getName()),
						AnnotationUtils.create("lockMode",
								createLockMode(source.getLockMode()))

				//
				);

	}

	public LockModeType createLockMode(String lockMode) {
		return lockMode == null ? null : LockModeType.valueOf(lockMode);
	}

	public jakarta.persistence.CascadeType[] getCascadeType(CascadeType cascade) {

		if (cascade == null) {
			return null;
		} else {
			final Collection<jakarta.persistence.CascadeType> cascades = new HashSet<jakarta.persistence.CascadeType>();

			if (cascade.getCascadeAll() != null) {
				cascades.add(jakarta.persistence.CascadeType.ALL);
			}
			if (cascade.getCascadeMerge() != null) {
				cascades.add(jakarta.persistence.CascadeType.MERGE);
			}
			if (cascade.getCascadePersist() != null) {
				cascades.add(jakarta.persistence.CascadeType.PERSIST);
			}
			if (cascade.getCascadeRefresh() != null) {
				cascades.add(jakarta.persistence.CascadeType.REFRESH);
			}
			if (cascade.getCascadeRemove() != null) {
				cascades.add(jakarta.persistence.CascadeType.REMOVE);
			}
			if (cascade.getCascadeDetach() != null) {
				cascades.add(jakarta.persistence.CascadeType.DETACH);
			}
			return cascades.toArray(new jakarta.persistence.CascadeType[cascades
					.size()]);
		}
	}

	public XAnnotation<jakarta.persistence.OrderColumn> createOrderColumn(
			OrderColumn source) {
		return source == null ? null
				:
				//
				new XAnnotation<jakarta.persistence.OrderColumn>(
						jakarta.persistence.OrderColumn.class,
						//
						AnnotationUtils.create("name", source.getName()),
						//
						AnnotationUtils.create("nullable", source.isNullable()),
						//
						AnnotationUtils.create("insertable",
								source.isInsertable()),
						//
						AnnotationUtils.create("updatable",
								source.isUpdatable()),
						//
						AnnotationUtils.create("columnDefinition",
								source.getColumnDefinition())
				//
				);

	}

	public XAnnotation<jakarta.persistence.CollectionTable> createCollectionTable(
			CollectionTable source) {
		return source == null ? null :
		//
				new XAnnotation<jakarta.persistence.CollectionTable>(
						jakarta.persistence.CollectionTable.class,
						//
						AnnotationUtils.create("name", source.getName()),
						//
						AnnotationUtils.create("catalog", source.getCatalog()),
						//
						AnnotationUtils.create("schema", source.getSchema()),
						//
						AnnotationUtils.create("joinColumns",
								createJoinColumn(source.getJoinColumn()),
								JoinColumn.class),
						//
						AnnotationUtils.create("uniqueConstraints",
								createUniqueConstraint(source
										.getUniqueConstraint()),
								jakarta.persistence.UniqueConstraint.class)
				//
				);

	}

	public XAnnotation<jakarta.persistence.MapKeyJoinColumn> createMapKeyJoinColumn(
			MapKeyJoinColumn source) {
		return source == null ? null
				:
				//
				new XAnnotation<jakarta.persistence.MapKeyJoinColumn>(
						jakarta.persistence.MapKeyJoinColumn.class,
						//
						AnnotationUtils.create("name", source.getName()),
						//
						AnnotationUtils.create("referencedColumnName",
								source.getReferencedColumnName()),
						//
						AnnotationUtils.create("unique", source.isUnique()),
						//
						AnnotationUtils.create("nullable", source.isNullable()),
						//
						AnnotationUtils.create("insertable",
								source.isInsertable()),
						//
						AnnotationUtils.create("updatable",
								source.isUpdatable()),
						//
						AnnotationUtils.create("columnDefinition",
								source.getColumnDefinition()),
						//
						AnnotationUtils.create("table", source.getTable())
				//
				);
	}

	public XAnnotation<?> createMapKeyJoinColumns(
			Collection<MapKeyJoinColumn> source) {
		return transform(
				MapKeyJoinColumns.class,
				jakarta.persistence.MapKeyJoinColumn.class,
				source,
				new Transformer<MapKeyJoinColumn, XAnnotation<jakarta.persistence.MapKeyJoinColumn>>() {
					public XAnnotation<jakarta.persistence.MapKeyJoinColumn> transform(
							MapKeyJoinColumn input) {
						return createMapKeyJoinColumn(input);
					}
				});
	}

	public XAnnotation<jakarta.persistence.MapKeyColumn> createMapKeyColumn(
			MapKeyColumn source) {
		return source == null ? null
				:
				//
				new XAnnotation<jakarta.persistence.MapKeyColumn>(
						jakarta.persistence.MapKeyColumn.class,
						//
						AnnotationUtils.create("name", source.getName()),
						//
						AnnotationUtils.create("unique", source.isUnique()),
						//
						AnnotationUtils.create("nullable", source.isNullable()),
						//
						AnnotationUtils.create("insertable",
								source.isInsertable()),
						//
						AnnotationUtils.create("updatable",
								source.isUpdatable()),
						//
						AnnotationUtils.create("columnDefinition",
								source.getColumnDefinition()),
						//
						AnnotationUtils.create("table", source.getTable()),
						//
						AnnotationUtils.create("length", source.getLength()),
						//
						AnnotationUtils.create("precision",
								source.getPrecision()),
						//
						AnnotationUtils.create("scale", source.getScale())

				//
				);
	}

	public XAnnotation<jakarta.persistence.MapKeyClass> createMapKeyClass(
			MapKeyClass source) {
		return source == null ? null :
		//
				new XAnnotation<jakarta.persistence.MapKeyClass>(
						jakarta.persistence.MapKeyClass.class,
						//
						source.getClazz() == null ? null :

						new XSingleAnnotationField<Class<Object>>("value",
								Class.class,
								new XClassByNameAnnotationValue<Object>(
										source.getClazz()))

				//
				);
	}

	public XAnnotation<jakarta.persistence.ElementCollection> createElementCollection(
			ElementCollection source) {
		return source == null ? null
				:
				//
				new XAnnotation<jakarta.persistence.ElementCollection>(
						jakarta.persistence.ElementCollection.class,
						//
						source.getTargetClass() == null ? null
								: new XSingleAnnotationField<Class<Object>>(
										"value",
										Class.class,
										new XClassByNameAnnotationValue<Object>(
												source.getTargetClass())),

						AnnotationUtils.create("fetch",
								getFetchType(source.getFetch()))

				//
				);
	}

	public Collection<XAnnotation<?>> createElementCollectionAnnotations(
			ElementCollection source) {
		return source == null ? Collections.<XAnnotation<?>> emptyList()
				:
				//
				annotations(
				//
						createElementCollection(source),
						//
						createOrderBy(source.getOrderBy()),
						//
						createOrderColumn(source.getOrderColumn()),

						//
						createMapKey(source.getMapKey()),
						//
						createMapKeyClass(source.getMapKeyClass()),
						//
						createMapKeyTemporal(source.getMapKeyTemporal()),
						//
						createMapKeyEnumerated(source.getMapKeyEnumerated()),
						//
						createAttributeOverrides(source
								.getMapKeyAttributeOverride()),
						//
						createMapKeyColumn(source.getMapKeyColumn()),
						//
						createMapKeyJoinColumns(source.getMapKeyJoinColumn()),
						//
						createColumn(source.getColumn()),
						//
						createTemporal(source.getTemporal()),
						//
						createEnumerated(source.getEnumerated()),
						//
						createLob(source.getLob()),
						//
						createAttributeOverrides(source.getAttributeOverride()),
						//
						createAssociationOverrides(source
								.getAssociationOverride()),
						//
						createCollectionTable(source.getCollectionTable()),
						//
						createAccess(source.getAccess())
				//
				);
	}

	public XAnnotation<jakarta.persistence.MapKeyTemporal> createMapKeyTemporal(
			String source) {
		return source == null ? null :
		//
				new XAnnotation<jakarta.persistence.MapKeyTemporal>(
						jakarta.persistence.MapKeyTemporal.class,
						//
						new XSingleAnnotationField<TemporalType>("value",
								TemporalType.class,
								new XEnumAnnotationValue<TemporalType>(
										jakarta.persistence.TemporalType
												.valueOf(source))));
	}

	public XAnnotation<jakarta.persistence.MapKeyEnumerated> createMapKeyEnumerated(
			String source) {
		return source == null ? null :
		//
				new XAnnotation<jakarta.persistence.MapKeyEnumerated>(
						jakarta.persistence.MapKeyEnumerated.class,
						//
						new XSingleAnnotationField<EnumType>("value",
								EnumType.class,
								new XEnumAnnotationValue<EnumType>(
										jakarta.persistence.EnumType
												.valueOf(source))));
	}

	// 9.1.12
	public XAnnotation<jakarta.persistence.AssociationOverride> createAssociationOverride(
			AssociationOverride source) {
		return source == null ? null :
		//
				new XAnnotation<jakarta.persistence.AssociationOverride>(
						jakarta.persistence.AssociationOverride.class,
						//
						AnnotationUtils.create("name", source.getName()),
						//
						AnnotationUtils.create("joinColumns",
								createJoinColumn(source.getJoinColumn()),
								JoinColumn.class),
						//
						AnnotationUtils.create("joinTable",
								createJoinTable(source.getJoinTable()))

				//
				);
	}

	public Collection<XAnnotation<?>> createAttributeAnnotations(
			Object attribute) {
		if (attribute instanceof ElementCollection) {
			return createElementCollectionAnnotations((ElementCollection) attribute);
		} else {
			return super.createAttributeAnnotations(attribute);
		}

	}

}
