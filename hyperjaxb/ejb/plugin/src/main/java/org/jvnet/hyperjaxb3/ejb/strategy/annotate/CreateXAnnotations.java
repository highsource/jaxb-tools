package org.jvnet.hyperjaxb3.ejb.strategy.annotate;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import jakarta.persistence.EnumType;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.PrimaryKeyJoinColumns;
import jakarta.persistence.SecondaryTables;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.TemporalType;

import org.jvnet.jaxb.annox.model.XAnnotation;
import org.jvnet.jaxb.annox.model.annotation.field.XArrayAnnotationField;
import org.jvnet.jaxb.annox.model.annotation.field.XSingleAnnotationField;
import org.jvnet.jaxb.annox.model.annotation.value.XClassByNameAnnotationValue;
import org.jvnet.jaxb.annox.model.annotation.value.XEnumAnnotationValue;
import org.jvnet.hyperjaxb3.annotation.util.AnnotationUtils;

import jakarta.xml.ns.persistence.orm.AssociationOverride;
import jakarta.xml.ns.persistence.orm.AttributeOverride;
import jakarta.xml.ns.persistence.orm.Basic;
import jakarta.xml.ns.persistence.orm.CascadeType;
import jakarta.xml.ns.persistence.orm.Column;
import jakarta.xml.ns.persistence.orm.ColumnResult;
import jakarta.xml.ns.persistence.orm.DiscriminatorColumn;
import jakarta.xml.ns.persistence.orm.Embeddable;
import jakarta.xml.ns.persistence.orm.Embedded;
import jakarta.xml.ns.persistence.orm.EmbeddedId;
import jakarta.xml.ns.persistence.orm.EmptyType;
import jakarta.xml.ns.persistence.orm.Entity;
import jakarta.xml.ns.persistence.orm.EntityListener;
import jakarta.xml.ns.persistence.orm.EntityListeners;
import jakarta.xml.ns.persistence.orm.EntityResult;
import jakarta.xml.ns.persistence.orm.FieldResult;
import jakarta.xml.ns.persistence.orm.GeneratedValue;
import jakarta.xml.ns.persistence.orm.Id;
import jakarta.xml.ns.persistence.orm.IdClass;
import jakarta.xml.ns.persistence.orm.Inheritance;
import jakarta.xml.ns.persistence.orm.JoinColumn;
import jakarta.xml.ns.persistence.orm.JoinTable;
import jakarta.xml.ns.persistence.orm.Lob;
import jakarta.xml.ns.persistence.orm.ManyToMany;
import jakarta.xml.ns.persistence.orm.ManyToOne;
import jakarta.xml.ns.persistence.orm.MapKey;
import jakarta.xml.ns.persistence.orm.MappedSuperclass;
import jakarta.xml.ns.persistence.orm.NamedNativeQuery;
import jakarta.xml.ns.persistence.orm.NamedQuery;
import jakarta.xml.ns.persistence.orm.OneToMany;
import jakarta.xml.ns.persistence.orm.OneToOne;
import jakarta.xml.ns.persistence.orm.PrimaryKeyJoinColumn;
import jakarta.xml.ns.persistence.orm.QueryHint;
import jakarta.xml.ns.persistence.orm.SecondaryTable;
import jakarta.xml.ns.persistence.orm.SequenceGenerator;
import jakarta.xml.ns.persistence.orm.SqlResultSetMapping;
import jakarta.xml.ns.persistence.orm.Table;
import jakarta.xml.ns.persistence.orm.TableGenerator;
import jakarta.xml.ns.persistence.orm.Transient;
import jakarta.xml.ns.persistence.orm.UniqueConstraint;
import jakarta.xml.ns.persistence.orm.Version;

public class CreateXAnnotations {
	// ==================================================================
	// 8.1
	// ==================================================================

	// 8.1
	public XAnnotation<jakarta.persistence.Entity> createEntity(Entity cEntity) {
		return cEntity == null ? null :
		//
				new XAnnotation<jakarta.persistence.Entity>(
						jakarta.persistence.Entity.class,
						//
						AnnotationUtils.create("name", cEntity.getName())
				//
				);
	}

	// 8.2
	public XAnnotation<jakarta.persistence.EntityListeners> createEntityListeners(
			EntityListeners cEntityListeners) {
		if (cEntityListeners == null
				|| cEntityListeners.getEntityListener().isEmpty()) {
			return null;
		} else {
			final List<String> classNames = new ArrayList<String>();
			for (EntityListener entityListener : cEntityListeners
					.getEntityListener()) {
				if (entityListener.getClazz() != null) {
					classNames.add(entityListener.getClazz());
				}
			}
			final String[] classNamesArray = classNames
					.toArray(new String[classNames.size()]);
			@SuppressWarnings("unchecked")
			final XClassByNameAnnotationValue<Object>[] values = new XClassByNameAnnotationValue[classNamesArray.length];
			for (int index = 0; index < classNamesArray.length; index++) {
				values[index] = new XClassByNameAnnotationValue<Object>(
						classNamesArray[index]);
			}
			return new XAnnotation<jakarta.persistence.EntityListeners>(
					jakarta.persistence.EntityListeners.class,
					//
					new XArrayAnnotationField<Class<Object>>("value",
							Class[].class, values)
			//
			);
		}
	}

	public XAnnotation<jakarta.persistence.ExcludeSuperclassListeners> createExcludeSuperclassListeners(
			EmptyType cExcludeSuperclassListeners) {
		return cExcludeSuperclassListeners == null ? null :
		//
				new XAnnotation<jakarta.persistence.ExcludeSuperclassListeners>(
						jakarta.persistence.ExcludeSuperclassListeners.class);
	}

	public XAnnotation<jakarta.persistence.ExcludeDefaultListeners> createExcludeDefaultListeners(
			EmptyType cExcludeDefaultListeners) {
		return cExcludeDefaultListeners == null ? null :
		//
				new XAnnotation<jakarta.persistence.ExcludeDefaultListeners>(
						jakarta.persistence.ExcludeDefaultListeners.class);
	}

	// public XAnnotation createEntityListeners(EntityListeners
	// cEntityListeners)

	// 8.3.1
	public XAnnotation<jakarta.persistence.NamedQuery> createNamedQuery(
			NamedQuery cNamedQuery) {
		return cNamedQuery == null ? null
				:
				//
				new XAnnotation<jakarta.persistence.NamedQuery>(
						jakarta.persistence.NamedQuery.class,
						//
						AnnotationUtils.create("query", cNamedQuery.getQuery()),
						//
						AnnotationUtils.create("hints",
								createQueryHint(cNamedQuery.getHint()),
								jakarta.persistence.QueryHint.class),
						//
						AnnotationUtils.create("name", cNamedQuery.getName())
				//
				);

	}

	public XAnnotation<?> createNamedQueries(
			Collection<NamedQuery> cNamedQueries) {
		return transform(
				NamedQueries.class,
				jakarta.persistence.NamedQuery.class,
				cNamedQueries,
				new Transformer<NamedQuery, XAnnotation<jakarta.persistence.NamedQuery>>() {
					public XAnnotation<jakarta.persistence.NamedQuery> transform(
							NamedQuery input) {
						return createNamedQuery(input);
					}
				});
	}

	public XAnnotation<jakarta.persistence.QueryHint> createQueryHint(
			QueryHint cQueryHint) {
		return cQueryHint == null ? null :
		//
				new XAnnotation<jakarta.persistence.QueryHint>(
						jakarta.persistence.QueryHint.class,
						//
						AnnotationUtils.create("name", cQueryHint.getName()),
						//
						AnnotationUtils.create("value", cQueryHint.getValue())
				//
				);
	}

	public XAnnotation<?>[] createQueryHint(Collection<QueryHint> cQueryHints) {
		return transform(
				cQueryHints,
				new Transformer<QueryHint, XAnnotation<jakarta.persistence.QueryHint>>() {
					public XAnnotation<jakarta.persistence.QueryHint> transform(
							QueryHint input) {
						return createQueryHint(input);
					}
				});
	}

	// 8.3.2
	public XAnnotation<jakarta.persistence.NamedNativeQuery> createNamedNativeQuery(
			NamedNativeQuery cNamedNativeQuery) {
		return cNamedNativeQuery == null ? null
				:
				//
				new XAnnotation<jakarta.persistence.NamedNativeQuery>(
						jakarta.persistence.NamedNativeQuery.class,
						//
						AnnotationUtils.create("name",
								cNamedNativeQuery.getName()),
						//
						AnnotationUtils.create("query",
								cNamedNativeQuery.getQuery()),
						//
						AnnotationUtils.create("hints",
								createQueryHint(cNamedNativeQuery.getHint()),
								jakarta.persistence.QueryHint.class),
						//
						cNamedNativeQuery.getResultClass() == null ? null
								: new XSingleAnnotationField<Class<Object>>(
										"resultClass",
										Class.class,
										new XClassByNameAnnotationValue<Object>(
												cNamedNativeQuery
														.getResultClass())),
						//
						AnnotationUtils.create("resultSetMapping",
								cNamedNativeQuery.getResultSetMapping())
				//
				);
	}

	public XAnnotation<?> createNamedNativeQuery(
			Collection<NamedNativeQuery> cNamedNativeQueries) {
		return transform(
				NamedNativeQueries.class,
				jakarta.persistence.NamedNativeQuery.class,
				cNamedNativeQueries,
				new Transformer<NamedNativeQuery, XAnnotation<jakarta.persistence.NamedNativeQuery>>() {
					public XAnnotation<jakarta.persistence.NamedNativeQuery> transform(
							NamedNativeQuery input) {
						return createNamedNativeQuery(input);
					}
				});
	}

	public XAnnotation<jakarta.persistence.SqlResultSetMapping> createSqlResultSetMapping(
			SqlResultSetMapping cSqlResultSetMapping) {
		return cSqlResultSetMapping == null ? null :
		//
				new XAnnotation<jakarta.persistence.SqlResultSetMapping>(
						jakarta.persistence.SqlResultSetMapping.class,
						//
						AnnotationUtils.create("name",
								cSqlResultSetMapping.getName()),
						//
						AnnotationUtils.create("entityResult",
								createEntityResult(cSqlResultSetMapping
										.getEntityResult()),
								jakarta.persistence.EntityResult.class),
						//
						AnnotationUtils.create("columnResult",
								createColumnResult(cSqlResultSetMapping
										.getColumnResult()),
								jakarta.persistence.ColumnResult.class)
				//
				);
	}

	public XAnnotation<?> createSqlResultSetMapping(
			Collection<SqlResultSetMapping> cSqlResultSetMappings) {
		return transform(
				SqlResultSetMappings.class,
				jakarta.persistence.SqlResultSetMapping.class,
				cSqlResultSetMappings,
				new Transformer<SqlResultSetMapping, XAnnotation<jakarta.persistence.SqlResultSetMapping>>() {
					public XAnnotation<jakarta.persistence.SqlResultSetMapping> transform(
							SqlResultSetMapping input) {
						return createSqlResultSetMapping(input);
					}
				});
	}

	public XAnnotation<jakarta.persistence.EntityResult> createEntityResult(
			EntityResult cEntityResult) {
		return cEntityResult == null ? null :
		//
				new XAnnotation<jakarta.persistence.EntityResult>(
						jakarta.persistence.EntityResult.class,
						//
						new XSingleAnnotationField<Class<Object>>(
								"entityClass", Class.class,
								new XClassByNameAnnotationValue<Object>(
										cEntityResult.getEntityClass())),

						//
						AnnotationUtils.create("fields",
								createFieldResult(cEntityResult
										.getFieldResult()),
								jakarta.persistence.FieldResult.class),
						//
						AnnotationUtils.create("discriminatorColumn",
								cEntityResult.getDiscriminatorColumn())
				//
				);
	}

	public XAnnotation<?>[] createEntityResult(List<EntityResult> cEntityResults) {
		return transform(
				cEntityResults,
				new Transformer<EntityResult, XAnnotation<jakarta.persistence.EntityResult>>() {
					public XAnnotation<jakarta.persistence.EntityResult> transform(
							EntityResult cEntityResult) {
						return createEntityResult(cEntityResult);
					}
				});
	}

	public XAnnotation<jakarta.persistence.FieldResult> createFieldResult(
			FieldResult cFieldResult) {
		return cFieldResult == null ? null :
		//
				new XAnnotation<jakarta.persistence.FieldResult>(
						jakarta.persistence.FieldResult.class,
						//
						AnnotationUtils.create("name", cFieldResult.getName()),
						//
						AnnotationUtils.create("column",
								cFieldResult.getColumn())
				//
				);
	}

	public XAnnotation<?>[] createFieldResult(List<FieldResult> cFieldResults) {
		return transform(
				cFieldResults,
				new Transformer<FieldResult, XAnnotation<jakarta.persistence.FieldResult>>() {
					public XAnnotation<jakarta.persistence.FieldResult> transform(
							FieldResult cFieldResult) {
						return createFieldResult(cFieldResult);
					}
				});
	}

	public XAnnotation<jakarta.persistence.ColumnResult> createColumnResult(
			ColumnResult cColumnResult) {
		return cColumnResult == null ? null :
		//
				new XAnnotation<jakarta.persistence.ColumnResult>(
						jakarta.persistence.ColumnResult.class,
						//
						AnnotationUtils.create("name", cColumnResult.getName())
				//
				);
	}

	public XAnnotation<?>[] createColumnResult(
			Collection<ColumnResult> cColumnResults) {
		return transform(
				cColumnResults,
				new Transformer<ColumnResult, XAnnotation<jakarta.persistence.ColumnResult>>() {
					public XAnnotation<jakarta.persistence.ColumnResult> transform(
							ColumnResult cColumnResult) {
						return createColumnResult(cColumnResult);
					}
				});
	}

	// ==================================================================
	// 9.1
	// ==================================================================

	// 9.1.1
	public XAnnotation<jakarta.persistence.Table> createTable(Table cTable) {
		return cTable == null ? null :
		//
				new XAnnotation<jakarta.persistence.Table>(
						jakarta.persistence.Table.class,
						//
						AnnotationUtils.create("name", cTable.getName()),
						//
						AnnotationUtils.create("catalog", cTable.getCatalog()),
						//
						AnnotationUtils.create("schema", cTable.getSchema()),
						//
						AnnotationUtils.create("uniqueConstraints",
								createUniqueConstraint(cTable
										.getUniqueConstraint()),
								jakarta.persistence.UniqueConstraint.class)
				//
				);

	}

	// 9.1.2
	public XAnnotation<jakarta.persistence.SecondaryTable> createSecondaryTable(
			SecondaryTable cSecondaryTable) {
		return cSecondaryTable == null ? null :
		//
				new XAnnotation<jakarta.persistence.SecondaryTable>(
						jakarta.persistence.SecondaryTable.class,
						//
						AnnotationUtils.create("name",
								cSecondaryTable.getName()),
						//
						AnnotationUtils.create("catalog",
								cSecondaryTable.getCatalog()),
						//
						AnnotationUtils.create("schema",
								cSecondaryTable.getSchema()),
						//
						AnnotationUtils.create("pkJoinColumns",
								createPrimaryKeyJoinColumn(cSecondaryTable
										.getPrimaryKeyJoinColumn()),
								jakarta.persistence.PrimaryKeyJoinColumn.class),
						//
						AnnotationUtils.create("uniqueConstraints",
								createUniqueConstraint(cSecondaryTable
										.getUniqueConstraint()),
								jakarta.persistence.UniqueConstraint.class)
				//
				);
	}

	// 9.1.3
	public XAnnotation<?> createSecondaryTables(
			List<SecondaryTable> cSecondaryTables) {
		return transform(
				SecondaryTables.class,
				jakarta.persistence.SecondaryTable.class,
				cSecondaryTables,
				new Transformer<SecondaryTable, XAnnotation<jakarta.persistence.SecondaryTable>>() {
					public XAnnotation<jakarta.persistence.SecondaryTable> transform(
							SecondaryTable input) {
						return createSecondaryTable(input);
					}
				});
	}

	// 9.1.4
	public XAnnotation<jakarta.persistence.UniqueConstraint> createUniqueConstraint(
			UniqueConstraint cUniqueConstraint) {
		return cUniqueConstraint == null ? null :
		//
				new XAnnotation<jakarta.persistence.UniqueConstraint>(
						jakarta.persistence.UniqueConstraint.class,
						//
						AnnotationUtils.create(
								"columnNames",
								cUniqueConstraint.getColumnName().toArray(
										new String[cUniqueConstraint
												.getColumnName().size()]))
				//
				);
	}

	public XAnnotation<?>[] createUniqueConstraint(
			List<UniqueConstraint> cUniqueConstraints) {
		return transform(
				cUniqueConstraints,
				new Transformer<UniqueConstraint, XAnnotation<jakarta.persistence.UniqueConstraint>>() {
					public XAnnotation<jakarta.persistence.UniqueConstraint> transform(
							UniqueConstraint input) {
						return createUniqueConstraint(input);
					}
				});
	}

	// 9.1.5
	public XAnnotation<jakarta.persistence.Column> createColumn(Column cColumn) {

		return cColumn == null ? null
				:
				//
				new XAnnotation<jakarta.persistence.Column>(
						jakarta.persistence.Column.class,
						//
						AnnotationUtils.create("name", cColumn.getName()),
						//
						AnnotationUtils.create("unique", cColumn.isUnique()),
						//
						AnnotationUtils.create("nullable", cColumn.isNullable()),
						//
						AnnotationUtils.create("insertable",
								cColumn.isInsertable()),
						//
						AnnotationUtils.create("updatable",
								cColumn.isUpdatable()),
						//
						AnnotationUtils.create("columnDefinition",
								cColumn.getColumnDefinition()),
						//
						AnnotationUtils.create("table", cColumn.getTable()),
						//
						AnnotationUtils.create("length", cColumn.getLength()),
						//
						AnnotationUtils.create("precision",
								cColumn.getPrecision()),
						//
						AnnotationUtils.create("scale", cColumn.getScale()));
	}

	// 9.1.6
	public XAnnotation<jakarta.persistence.JoinColumn> createJoinColumn(
			JoinColumn cJoinColumn) {
		return cJoinColumn == null ? null
				:
				//
				new XAnnotation<jakarta.persistence.JoinColumn>(
						jakarta.persistence.JoinColumn.class,
						//
						AnnotationUtils.create("name", cJoinColumn.getName()),
						//
						AnnotationUtils.create("referencedColumnName",
								cJoinColumn.getReferencedColumnName()),
						//
						AnnotationUtils.create("unique", cJoinColumn.isUnique()),
						//
						AnnotationUtils.create("nullable",
								cJoinColumn.isNullable()),
						//
						AnnotationUtils.create("insertable",
								cJoinColumn.isInsertable()),
						//
						AnnotationUtils.create("updatable",
								cJoinColumn.isUpdatable()),
						//
						AnnotationUtils.create("columnDefinition",
								cJoinColumn.getColumnDefinition()),
						//
						AnnotationUtils.create("table", cJoinColumn.getTable())
				//
				);
	}

	public XAnnotation<?>[] createJoinColumn(List<JoinColumn> cJoinColumns) {
		return transform(
				cJoinColumns,
				new Transformer<JoinColumn, XAnnotation<jakarta.persistence.JoinColumn>>() {
					public XAnnotation<jakarta.persistence.JoinColumn> transform(
							JoinColumn input) {
						return createJoinColumn(input);
					}
				});
	}

	// 9.1.7
	public XAnnotation<?> createJoinColumns(List<JoinColumn> cJoinColumns) {
		return transform(
				JoinColumns.class,
				jakarta.persistence.JoinColumn.class,
				cJoinColumns,
				new Transformer<JoinColumn, XAnnotation<jakarta.persistence.JoinColumn>>() {
					public XAnnotation<jakarta.persistence.JoinColumn> transform(
							JoinColumn input) {
						return createJoinColumn(input);
					}
				});
	}

	public Collection<XAnnotation<?>> createAttributeAnnotations(
			Object attribute) {

		if (attribute == null) {
			return null;
		} else if (attribute instanceof Id) {
			return createIdAnnotations((Id) attribute);
		} else if (attribute instanceof EmbeddedId) {
			return createEmbeddedIdAnnotations((EmbeddedId) attribute);
		} else if (attribute instanceof Basic) {
			return createBasicAnnotations((Basic) attribute);

		} else if (attribute instanceof Version) {
			return createVersionAnnotations((Version) attribute);

		} else if (attribute instanceof ManyToOne) {
			return createManyToOneAnnotations((ManyToOne) attribute);

		} else if (attribute instanceof OneToMany) {
			return createOneToManyAnnotations((OneToMany) attribute);
		} else if (attribute instanceof OneToOne) {
			return createOneToOneAnnotations((OneToOne) attribute);
		} else if (attribute instanceof ManyToMany) {
			return createManyToManyAnnotations((ManyToMany) attribute);
		} else if (attribute instanceof Embedded) {
			return createEmbeddedAnnotations((Embedded) attribute);
		} else if (attribute instanceof Transient) {
			return createTransientAnnotations((Transient) attribute);
		} else {
			return null;
		}
	}

	// 9.1.8
	public XAnnotation<jakarta.persistence.Id> createId(Id cId) {
		return cId == null ? null :
		//
				new XAnnotation<jakarta.persistence.Id>(
						jakarta.persistence.Id.class);
	}

	// 9.1.9
	public XAnnotation<jakarta.persistence.GeneratedValue> createGeneratedValue(
			GeneratedValue cGeneratedValue) {

		return cGeneratedValue == null ? null :
		//
				new XAnnotation<jakarta.persistence.GeneratedValue>(
						jakarta.persistence.GeneratedValue.class,
						//
						AnnotationUtils.create("generator",
								cGeneratedValue.getGenerator()),
						//
						createGeneratedValue$Strategy(cGeneratedValue
								.getStrategy())
				//
				);
	}

	public XSingleAnnotationField<GenerationType> createGeneratedValue$Strategy(
			String strategy) {
		return strategy == null ? null :
		//
				new XSingleAnnotationField<GenerationType>("strategy",
						GenerationType.class,
						new XEnumAnnotationValue<GenerationType>(
								GenerationType.valueOf(strategy)));
	}

	// 9.1.10
	public XAnnotation<jakarta.persistence.AttributeOverride> createAttributeOverride(
			AttributeOverride cAttributeOverride) {
		return cAttributeOverride == null ? null :
		//
				new XAnnotation<jakarta.persistence.AttributeOverride>(
						jakarta.persistence.AttributeOverride.class,
						//
						AnnotationUtils.create("name",
								cAttributeOverride.getName()),
						//
						AnnotationUtils.create("column",
								createColumn(cAttributeOverride.getColumn()))
				//
				);
	}

	public XAnnotation<jakarta.persistence.AttributeOverride>[] createAttributeOverride(
			List<AttributeOverride> cAttributeOverrides) {
		return transform(
				cAttributeOverrides,
				new Transformer<AttributeOverride, XAnnotation<jakarta.persistence.AttributeOverride>>() {
					public XAnnotation<jakarta.persistence.AttributeOverride> transform(
							AttributeOverride input) {
						return createAttributeOverride(input);
					}
				});
	}

	// 9.1.11
	public XAnnotation<?> createAttributeOverrides(
			List<AttributeOverride> cAttributeOverrides) {
		return transform(
				jakarta.persistence.AttributeOverrides.class,
				jakarta.persistence.AttributeOverride.class,
				cAttributeOverrides,
				new Transformer<AttributeOverride, XAnnotation<jakarta.persistence.AttributeOverride>>() {
					public XAnnotation<jakarta.persistence.AttributeOverride> transform(
							AttributeOverride input) {
						return createAttributeOverride(input);
					}
				});
	}

	// 9.1.12
	public XAnnotation<jakarta.persistence.AssociationOverride> createAssociationOverride(
			AssociationOverride cAssociationOverride) {
		return cAssociationOverride == null ? null :
		//
				new XAnnotation<jakarta.persistence.AssociationOverride>(
						jakarta.persistence.AssociationOverride.class,
						//
						AnnotationUtils.create("name",
								cAssociationOverride.getName()),
						//
						AnnotationUtils.create("joinColumns",
								createJoinColumn(cAssociationOverride
										.getJoinColumn()),
								jakarta.persistence.JoinColumn.class)
				//
				);
	}

	// 9.1.13
	public XAnnotation<?> createAssociationOverrides(
			List<AssociationOverride> cAssociationOverrides) {
		return transform(
				jakarta.persistence.AssociationOverrides.class,
				jakarta.persistence.AssociationOverride.class,
				cAssociationOverrides,
				new Transformer<AssociationOverride, XAnnotation<jakarta.persistence.AssociationOverride>>() {
					public XAnnotation<jakarta.persistence.AssociationOverride> transform(
							AssociationOverride input) {
						return createAssociationOverride(input);
					}
				});
	}

	// 9.1.14
	public XAnnotation<jakarta.persistence.EmbeddedId> createEmbeddedId(
			EmbeddedId cEmbeddedId) {
		return cEmbeddedId == null ? null :
		//
				new XAnnotation<jakarta.persistence.EmbeddedId>(
						jakarta.persistence.EmbeddedId.class);
	}

	// 9.1.15
	public XAnnotation<jakarta.persistence.IdClass> createIdClass(IdClass cIdClass) {
		return cIdClass == null ? null
				:
				//
				new XAnnotation<jakarta.persistence.IdClass>(
						jakarta.persistence.IdClass.class,
						//
						cIdClass.getClazz() == null ? null
								: new XSingleAnnotationField<Class<Object>>(
										"value",
										Class.class,
										new XClassByNameAnnotationValue<Object>(
												cIdClass.getClazz()))
				//
				);
	}

	// 9.1.16
	public XAnnotation<jakarta.persistence.Transient> createTransient(
			Transient cTransient) {
		return cTransient == null ? null :
		//
				new XAnnotation<jakarta.persistence.Transient>(
						jakarta.persistence.Transient.class);
	}

	// 9.1.17
	public XAnnotation<jakarta.persistence.Version> createVersion(Version cVersion) {
		return cVersion == null ? null :
		//
				new XAnnotation<jakarta.persistence.Version>(
						jakarta.persistence.Version.class);
	}

	// 9.1.18
	public XAnnotation<jakarta.persistence.Basic> createBasic(Basic cBasic) {
		return cBasic == null ? null :
		//
				new XAnnotation<jakarta.persistence.Basic>(
						jakarta.persistence.Basic.class,
						//
						AnnotationUtils.create("fetch",
								getFetchType(cBasic.getFetch())),
						//
						AnnotationUtils.create("optional", cBasic.isOptional())
				//
				);
	}

	// 9.1.19
	public XAnnotation<jakarta.persistence.Lob> createLob(Lob cLob) {
		return cLob == null ? null :
		//
				new XAnnotation<jakarta.persistence.Lob>(
						jakarta.persistence.Lob.class);
	}

	// 9.1.20
	public XAnnotation<jakarta.persistence.Temporal> createTemporal(
			String cTemporal) {
		return cTemporal == null ? null :
		//
				new XAnnotation<jakarta.persistence.Temporal>(
						jakarta.persistence.Temporal.class,
						//
						new XSingleAnnotationField<TemporalType>("value",
								TemporalType.class,
								new XEnumAnnotationValue<TemporalType>(
										TemporalType.valueOf(cTemporal))));
	}

	// 9.1.21
	public XAnnotation<jakarta.persistence.Enumerated> createEnumerated(
			String cEnumerated) {
		return cEnumerated == null ? null :
		//
				new XAnnotation<jakarta.persistence.Enumerated>(
						jakarta.persistence.Enumerated.class,
						//
						new XSingleAnnotationField<EnumType>("value",
								EnumType.class,
								new XEnumAnnotationValue<EnumType>(
										EnumType.valueOf(cEnumerated))));
	}

	// 9.1.22
	public XAnnotation<jakarta.persistence.ManyToOne> createManyToOne(
			ManyToOne cManyToOne) {
		return cManyToOne == null ? null :
		//
				new XAnnotation<jakarta.persistence.ManyToOne>(
						jakarta.persistence.ManyToOne.class,
						//
						cManyToOne.getTargetEntity() == null ? null :

						new XSingleAnnotationField<Class<Object>>(
								"targetEntity", Class.class,
								new XClassByNameAnnotationValue<Object>(
										cManyToOne.getTargetEntity())),

						//
						AnnotationUtils.create("cascade",
								getCascadeType(cManyToOne.getCascade())),
						//
						AnnotationUtils.create("fetch",
								getFetchType(cManyToOne.getFetch())),
						//
						AnnotationUtils.create("optional",
								cManyToOne.isOptional())

				//
				);
	}

	// 9.1.23
	public XAnnotation<jakarta.persistence.OneToOne> createOneToOne(
			OneToOne cOneToOne) {
		return cOneToOne == null ? null
				:
				//
				new XAnnotation<jakarta.persistence.OneToOne>(
						jakarta.persistence.OneToOne.class,
						//
						cOneToOne.getTargetEntity() == null ? null
								: new XSingleAnnotationField<Class<Object>>(
										"targetEntity",
										Class.class,
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
								cOneToOne.getMappedBy())
				//
				);
	}

	// 9.1.24
	public XAnnotation<jakarta.persistence.OneToMany> createOneToMany(
			OneToMany cOneToMany) {
		return cOneToMany == null ? null :
		//
				new XAnnotation<jakarta.persistence.OneToMany>(
						jakarta.persistence.OneToMany.class,
						//
						cOneToMany.getTargetEntity() == null ? null :

						new XSingleAnnotationField<Class<Object>>(
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
								cOneToMany.getMappedBy())
				//
				);
	}

	// 9.1.25
	public XAnnotation<jakarta.persistence.JoinTable> createJoinTable(
			JoinTable cJoinTable) {
		return cJoinTable == null ? null
				:
				//
				new XAnnotation<jakarta.persistence.JoinTable>(
						jakarta.persistence.JoinTable.class,

						//
						AnnotationUtils.create("name", cJoinTable.getName()),
						//
						AnnotationUtils.create("catalog",
								cJoinTable.getCatalog()),
						//
						AnnotationUtils.create("schema", cJoinTable.getSchema()),

						//
						AnnotationUtils.create("joinColumns",
								createJoinColumn(cJoinTable.getJoinColumn()),
								jakarta.persistence.JoinColumn.class),
						//
						AnnotationUtils.create("inverseJoinColumns",
								createJoinColumn(cJoinTable
										.getInverseJoinColumn()),
								jakarta.persistence.JoinColumn.class),
						//
						AnnotationUtils.create("uniqueConstraints",
								createUniqueConstraint(cJoinTable
										.getUniqueConstraint()),
								jakarta.persistence.UniqueConstraint.class)
				//
				);
	}

	// 9.1.26
	public XAnnotation<jakarta.persistence.ManyToMany> createManyToMany(
			ManyToMany cManyToMany) {
		return cManyToMany == null ? null
				:
				//
				new XAnnotation<jakarta.persistence.ManyToMany>(
						jakarta.persistence.ManyToMany.class,
						//
						cManyToMany.getTargetEntity() == null ? null
								: new XSingleAnnotationField<Class<Object>>(
										"targetEntity",
										Class.class,
										new XClassByNameAnnotationValue<Object>(
												cManyToMany.getTargetEntity())),
						//
						AnnotationUtils.create("cascade",
								getCascadeType(cManyToMany.getCascade())),
						//
						AnnotationUtils.create("fetch",
								getFetchType(cManyToMany.getFetch())),
						//
						AnnotationUtils.create("mappedBy",
								cManyToMany.getMappedBy())
				//
				);
	}

	// 9.1.27
	public XAnnotation<jakarta.persistence.MapKey> createMapKey(MapKey cMapKey) {
		return cMapKey == null ? null :
		//
				new XAnnotation<jakarta.persistence.MapKey>(
						jakarta.persistence.MapKey.class,
						//
						AnnotationUtils.create("name", cMapKey.getName())
				//
				);
	}

	// 9.1.28
	public XAnnotation<jakarta.persistence.OrderBy> createOrderBy(String orderBy) {
		return orderBy == null ? null :
		//
				new XAnnotation<jakarta.persistence.OrderBy>(
						jakarta.persistence.OrderBy.class,
						AnnotationUtils.create("value", orderBy));
	}

	// 9.1.29
	public XAnnotation<jakarta.persistence.Inheritance> createInheritance(
			Inheritance cInheritance) {
		return cInheritance == null ? null :
		//
				new XAnnotation<jakarta.persistence.Inheritance>(
						jakarta.persistence.Inheritance.class,
						//
						AnnotationUtils.create("strategy",
								getInheritanceType(cInheritance.getStrategy()))
				//
				);
	}

	// 9.1.30
	public XAnnotation<jakarta.persistence.DiscriminatorColumn> createDiscriminatorColumn(
			DiscriminatorColumn cDiscriminatorColumn) {
		return cDiscriminatorColumn == null ? null :
		//
				new XAnnotation<jakarta.persistence.DiscriminatorColumn>(
						jakarta.persistence.DiscriminatorColumn.class,
						//
						AnnotationUtils.create("name",
								cDiscriminatorColumn.getName()),
						//
						AnnotationUtils.create("discriminatorType",
								getDiscriminatorType(cDiscriminatorColumn
										.getDiscriminatorType())),
						//
						AnnotationUtils.create("columnDefinition",
								cDiscriminatorColumn.getColumnDefinition()),
						//
						AnnotationUtils.create("length",
								cDiscriminatorColumn.getLength())
				//
				);
	}

	// 9.1.31
	public XAnnotation<jakarta.persistence.DiscriminatorValue> createDiscriminatorValue(
			String cDiscriminatorValue) {
		return cDiscriminatorValue == null ? null :
		//
				new XAnnotation<jakarta.persistence.DiscriminatorValue>(
						jakarta.persistence.DiscriminatorValue.class,
						AnnotationUtils.create("value", cDiscriminatorValue));
	}

	// 9.1.32
	public XAnnotation<jakarta.persistence.PrimaryKeyJoinColumn> createPrimaryKeyJoinColumn(
			PrimaryKeyJoinColumn cPrimaryKeyJoinColumn) {
		return cPrimaryKeyJoinColumn == null ? null :
		//
				new XAnnotation<jakarta.persistence.PrimaryKeyJoinColumn>(
						jakarta.persistence.PrimaryKeyJoinColumn.class,
						//
						AnnotationUtils.create("name",
								cPrimaryKeyJoinColumn.getName()),
						//
						AnnotationUtils
								.create("referencedColumnName",
										cPrimaryKeyJoinColumn
												.getReferencedColumnName()),
						//
						AnnotationUtils.create("columnDefinition",
								cPrimaryKeyJoinColumn.getColumnDefinition())
				//
				);
	}

	public XAnnotation<jakarta.persistence.PrimaryKeyJoinColumn>[] createPrimaryKeyJoinColumn(
			List<PrimaryKeyJoinColumn> cPrimaryKeyJoinColumn) {
		return transform(
				cPrimaryKeyJoinColumn,
				new Transformer<PrimaryKeyJoinColumn, XAnnotation<jakarta.persistence.PrimaryKeyJoinColumn>>() {
					public XAnnotation<jakarta.persistence.PrimaryKeyJoinColumn> transform(
							PrimaryKeyJoinColumn input) {
						return createPrimaryKeyJoinColumn(input);
					}

				});
	}

	// 9.1.33
	public XAnnotation<?> createPrimaryKeyJoinColumns(
			List<PrimaryKeyJoinColumn> cPrimaryKeyJoinColumn) {
		return transform(
				PrimaryKeyJoinColumns.class,
				jakarta.persistence.PrimaryKeyJoinColumn.class,
				cPrimaryKeyJoinColumn,
				new Transformer<PrimaryKeyJoinColumn, XAnnotation<jakarta.persistence.PrimaryKeyJoinColumn>>() {
					public XAnnotation<jakarta.persistence.PrimaryKeyJoinColumn> transform(
							PrimaryKeyJoinColumn input) {
						return createPrimaryKeyJoinColumn(input);
					}

				});
	}

	// 9.1.34
	public XAnnotation<jakarta.persistence.Embeddable> createEmbeddable(
			Embeddable cEmbeddable) {
		return cEmbeddable == null ? null :
		//
				new XAnnotation<jakarta.persistence.Embeddable>(
						jakarta.persistence.Embeddable.class);
	}

	// 9.1.35
	public XAnnotation<jakarta.persistence.Embedded> createEmbedded(
			Embedded cEmbedded) {
		return cEmbedded == null ? null :
		//
				new XAnnotation<jakarta.persistence.Embedded>(
						jakarta.persistence.Embedded.class);
	}

	// 9.1.36
	public XAnnotation<jakarta.persistence.MappedSuperclass> createMappedSuperclass(
			MappedSuperclass cMappedSuperclass) {
		return cMappedSuperclass == null ? null :
		//
				new XAnnotation<jakarta.persistence.MappedSuperclass>(
						jakarta.persistence.MappedSuperclass.class);
	}

	// 9.1.37
	public XAnnotation<jakarta.persistence.SequenceGenerator> createSequenceGenerator(
			SequenceGenerator cSequenceGenerator) {

		return cSequenceGenerator == null ? null :
		//
				new XAnnotation<jakarta.persistence.SequenceGenerator>(
						jakarta.persistence.SequenceGenerator.class,
						//
						AnnotationUtils.create("name",
								cSequenceGenerator.getName()),
						//
						AnnotationUtils.create("sequenceName",
								cSequenceGenerator.getSequenceName()),
						//
						AnnotationUtils.create("initialValue",
								cSequenceGenerator.getInitialValue()),
						//
						AnnotationUtils.create("allocationSize",
								cSequenceGenerator.getAllocationSize()));
	}

	// 9.1.38
	public XAnnotation<jakarta.persistence.TableGenerator> createTableGenerator(
			TableGenerator cTableGenerator) {

		return cTableGenerator == null ? null :
		//
				new XAnnotation<jakarta.persistence.TableGenerator>(
						jakarta.persistence.TableGenerator.class,
						//
						AnnotationUtils.create("name",
								cTableGenerator.getName()),
						//
						AnnotationUtils.create("table",
								cTableGenerator.getTable()),
						//
						AnnotationUtils.create("catalog",
								cTableGenerator.getCatalog()),
						//
						AnnotationUtils.create("schema",
								cTableGenerator.getSchema()),
						//
						AnnotationUtils.create("pkColumnName",
								cTableGenerator.getPkColumnName()),
						//
						AnnotationUtils.create("valueColumnName",
								cTableGenerator.getValueColumnName()),
						//
						AnnotationUtils.create("pkColumnValue",
								cTableGenerator.getPkColumnValue()),
						//
						AnnotationUtils.create("initialValue",
								cTableGenerator.getInitialValue()),
						//
						AnnotationUtils.create("allocationSize",
								cTableGenerator.getAllocationSize()),
						//
						AnnotationUtils.create("uniqueConstraints",
								createUniqueConstraint(cTableGenerator
										.getUniqueConstraint()),
								jakarta.persistence.UniqueConstraint.class)
				//
				);
	}

	// ==================================================================
	// 10.1
	// ==================================================================

	// 10.1.3
	public Collection<XAnnotation<?>> createEntityAnnotations(Entity cEntity) {
		return cEntity == null ? Collections.<XAnnotation<?>> emptyList()
				:
				//
				annotations(
				//
						createEntity(cEntity),
						//
						createTable(cEntity.getTable()),
						//
						createSecondaryTables(cEntity.getSecondaryTable()),
						//
						createPrimaryKeyJoinColumns(cEntity
								.getPrimaryKeyJoinColumn()),
						//
						createIdClass(cEntity.getIdClass()),
						//
						createInheritance(cEntity.getInheritance()),
						//
						createDiscriminatorValue(cEntity
								.getDiscriminatorValue()),
						//
						createDiscriminatorColumn(cEntity
								.getDiscriminatorColumn()),
						//
						createSequenceGenerator(cEntity.getSequenceGenerator()),
						//
						createTableGenerator(cEntity.getTableGenerator()),
						//
						createNamedQueries(cEntity.getNamedQuery()),
						//
						createNamedNativeQuery(cEntity.getNamedNativeQuery()),
						//
						createSqlResultSetMapping(cEntity
								.getSqlResultSetMapping()),
						//
						createExcludeDefaultListeners(cEntity
								.getExcludeDefaultListeners()),
						//
						createExcludeSuperclassListeners(cEntity
								.getExcludeSuperclassListeners()),
						//
						createEntityListeners(cEntity.getEntityListeners()),
						//
						// "prePersist",
						//
						// "postPersist",
						//
						// "preRemove",
						//
						// "postRemove",
						//
						// "preUpdate",
						//
						// "postUpdate",
						//
						// "postLoad",
						//
						createAttributeOverrides(cEntity.getAttributeOverride()),
						//
						createAssociationOverrides(cEntity
								.getAssociationOverride())
				// "attributes"

				//
				);
	}

	// 10.1.3.22
	public Collection<XAnnotation<?>> createIdAnnotations(Id cId) {
		return cId == null ? Collections.<XAnnotation<?>> emptyList() :
		//
				annotations(
				//
						createId(cId),
						//
						createColumn(cId.getColumn()),
						//
						createGeneratedValue(cId.getGeneratedValue()),
						//
						createTemporal(cId.getTemporal()),
						//
						createTableGenerator(cId.getTableGenerator()),
						//
						createSequenceGenerator(cId.getSequenceGenerator())

				//
				);
	}

	// 10.1.3.23
	public Collection<XAnnotation<?>> createEmbeddedIdAnnotations(
			EmbeddedId cEmbeddedId) {
		return cEmbeddedId == null ? Collections.<XAnnotation<?>> emptyList() :
		//
				annotations(
						//
						createEmbeddedId(cEmbeddedId),
						//
						createAttributeOverrides(cEmbeddedId
								.getAttributeOverride())
				//
				);
	}

	// 10.1.3.24
	public Collection<XAnnotation<?>> createBasicAnnotations(Basic cBasic) {
		return cBasic == null ? Collections.<XAnnotation<?>> emptyList() :
		//
				annotations(
				//
						createBasic(cBasic),
						//
						createColumn(cBasic.getColumn()),
						//
						createLob(cBasic.getLob()),
						//
						createTemporal(cBasic.getTemporal()),
						//
						createEnumerated(cBasic.getEnumerated())
				//
				);
	}

	// 10.1.3.25
	public Collection<XAnnotation<?>> createVersionAnnotations(Version cVersion) {
		return cVersion == null ? Collections.<XAnnotation<?>> emptyList() :
		//
				annotations(
				//
						createVersion(cVersion),
						//
						createColumn(cVersion.getColumn()),
						//
						createTemporal(cVersion.getTemporal())
				//
				);
	}

	// 10.1.3.26
	public Collection<XAnnotation<?>> createManyToOneAnnotations(
			ManyToOne cManyToOne) {
		return cManyToOne == null ? Collections.<XAnnotation<?>> emptyList() :
		//
				annotations(
				//
						createManyToOne(cManyToOne),
						//
						createJoinColumns(cManyToOne.getJoinColumn()),
						//
						createJoinTable(cManyToOne.getJoinTable())
				//
				);
	}

	// 10.1.3.27
	public Collection<XAnnotation<?>> createOneToManyAnnotations(
			OneToMany cOneToMany) {
		return cOneToMany == null ? Collections.<XAnnotation<?>> emptyList() :
		//
				annotations(
				//
						createOneToMany(cOneToMany),
						//
						createOrderBy(cOneToMany.getOrderBy()),
						//
						createMapKey(cOneToMany.getMapKey()),
						//
						createJoinColumns(cOneToMany.getJoinColumn()),
						//
						createJoinTable(cOneToMany.getJoinTable())
				//
				);
	}

	// 10.1.3.28
	public Collection<XAnnotation<?>> createOneToOneAnnotations(
			OneToOne cOneToOne) {
		return cOneToOne == null ? Collections.<XAnnotation<?>> emptyList() :
		//
				annotations(
						//
						createOneToOne(cOneToOne),
						//
						createPrimaryKeyJoinColumns(cOneToOne
								.getPrimaryKeyJoinColumn()),
						//
						createJoinColumns(cOneToOne.getJoinColumn()),
						//
						createJoinTable(cOneToOne.getJoinTable())
				//
				);
	}

	// 10.1.3.29
	public Collection<XAnnotation<?>> createManyToManyAnnotations(
			ManyToMany cManyToMany) {
		return cManyToMany == null ? Collections.<XAnnotation<?>> emptyList() :
		//
				annotations(
				//
						createManyToMany(cManyToMany),
						//
						createOrderBy(cManyToMany.getOrderBy()),
						//
						createMapKey(cManyToMany.getMapKey()),
						//
						createJoinTable(cManyToMany.getJoinTable())
				//
				);
	}

	// 10.1.3.30
	public Collection<XAnnotation<?>> createEmbeddedAnnotations(
			Embedded cEmbedded) {
		return cEmbedded == null ? Collections.<XAnnotation<?>> emptyList() :
		//
				annotations(
						//
						createEmbedded(cEmbedded),
						//
						createAttributeOverrides(cEmbedded
								.getAttributeOverride())
				//
				);
	}

	// 10.1.3.31
	public Collection<XAnnotation<?>> createTransientAnnotations(
			Transient cTransient) {
		return cTransient == null ? Collections.<XAnnotation<?>> emptyList() :
		//
				annotations(
				//
				createTransient(cTransient)
				//
				);

	}

	// 10.1.4
	public Collection<XAnnotation<?>> createMappedSuperclassAnnotations(
			MappedSuperclass cMappedSuperclass) {
		return cMappedSuperclass == null ? Collections
				.<XAnnotation<?>> emptyList() :
		//
				annotations(
						//
						createMappedSuperclass(cMappedSuperclass),
						//
						createIdClass(cMappedSuperclass.getIdClass()),
						//
						createExcludeDefaultListeners(cMappedSuperclass
								.getExcludeDefaultListeners()),
						//
						createExcludeSuperclassListeners(cMappedSuperclass
								.getExcludeSuperclassListeners()),
						//
						createEntityListeners(cMappedSuperclass
								.getEntityListeners())
				//
				// "prePersist",
				//
				// "postPersist",
				//
				// "preRemove",
				//
				// "postRemove",
				//
				// "preUpdate",
				//
				// "postUpdate",
				//
				// "postLoad",
				//

				);

	}

	// 10.1.4
	public Collection<XAnnotation<?>> createEmbeddableAnnotations(
			Embeddable cEmbeddable) {
		return cEmbeddable == null ? Collections.<XAnnotation<?>> emptyList() :
		//
				annotations(
				//
				createEmbeddable(cEmbeddable)
				//
				);
	}

	public interface Transformer<I, O> {
		public O transform(I input);
	}

	public static <T, A1 extends Annotation, A2 extends Annotation> XAnnotation<?> transform(
			Class<A1> collectionClass, Class<A2> singleClass,

			Collection<T> collection,
			Transformer<T, XAnnotation<A2>> transformer) {
		if (collection == null || collection.isEmpty()) {
			return null;
		} else if (collection.size() == 1) {
			return transformer.transform(collection.iterator().next());
		} else {
			return new XAnnotation<A1>(collectionClass, AnnotationUtils.create(
					"value", transform(collection, transformer), singleClass));
		}
	}

	public static <T, A extends Annotation> XAnnotation<A>[] transform(
			Collection<T> collection, Transformer<T, XAnnotation<A>> transformer) {
		if (collection == null || collection.isEmpty()) {
			return null;
		} else {
			final Collection<XAnnotation<A>> annotations = new ArrayList<XAnnotation<A>>(
					collection.size());
			for (T item : collection) {
				annotations.add(transformer.transform(item));
			}
			@SuppressWarnings("unchecked")
			final XAnnotation<A>[] xannotations = annotations
					.toArray(new XAnnotation[annotations.size()]);
			return xannotations;
		}
	}

	public static Collection<XAnnotation<?>> annotations(
			XAnnotation<?>... annotations) {
		if (annotations == null) {
			return null;
		} else if (annotations.length == 0) {
			return Collections.emptyList();
		} else {
			final List<XAnnotation<?>> xannotations = new ArrayList<XAnnotation<?>>(
					annotations.length);
			for (XAnnotation<?> annotation : annotations) {
				if (annotation != null) {
					xannotations.add(annotation);
				}
			}
			return xannotations;
		}
	}

	public static Collection<XAnnotation<?>> annotations(
			Collection<XAnnotation<?>> annotations,

			XAnnotation<?>... additionalAnnotations) {
		if (annotations == null) {
			return annotations(additionalAnnotations);
		} else if (additionalAnnotations == null) {
			return annotations;
		} else {
			final Collection<XAnnotation<?>> result = new ArrayList<XAnnotation<?>>(
					annotations.size() + additionalAnnotations.length);
			result.addAll(annotations);
			result.addAll(annotations(additionalAnnotations));
			return result;
		}
	}

	@SuppressWarnings("unchecked")
	public static Collection<XAnnotation<?>> annotations(Object... annotations) {
		if (annotations == null) {
			return null;
		} else if (annotations.length == 0) {
			return Collections.emptyList();
		} else {
			final List<XAnnotation<?>> xannotations = new ArrayList<XAnnotation<?>>(
					annotations.length);
			for (Object annotation : annotations) {
				if (annotation != null) {
					if (annotation instanceof XAnnotation) {
						final XAnnotation<?> xannotation = (XAnnotation<?>) annotation;
						xannotations.add(xannotation);
					} else if (annotation instanceof Collection) {
						final Collection<XAnnotation<?>> xannotation = (Collection<XAnnotation<?>>) annotation;
						xannotations.addAll(xannotation);
					} else {
						throw new IllegalArgumentException(
								"Expecting either annotations or collections of annotations.");
					}
				}
			}
			return xannotations;
		}
	}

	public jakarta.persistence.FetchType getFetchType(String fetch) {
		return fetch == null ? null : jakarta.persistence.FetchType
				.valueOf(fetch);
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
			return cascades.toArray(new jakarta.persistence.CascadeType[cascades
					.size()]);
		}
	}

	public jakarta.persistence.DiscriminatorType getDiscriminatorType(
			String discriminatorType) {
		return discriminatorType == null ? null
				: jakarta.persistence.DiscriminatorType
						.valueOf(discriminatorType);
	}

	public jakarta.persistence.InheritanceType getInheritanceType(String strategy) {
		return strategy == null ? null : jakarta.persistence.InheritanceType
				.valueOf(strategy);
	}

}
