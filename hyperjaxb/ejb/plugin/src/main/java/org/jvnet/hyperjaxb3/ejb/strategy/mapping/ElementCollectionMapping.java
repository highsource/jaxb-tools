package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import java.util.Collection;

import jakarta.xml.ns.persistence.orm.CollectionTable;
import jakarta.xml.ns.persistence.orm.Column;
import jakarta.xml.ns.persistence.orm.ElementCollection;
import jakarta.xml.ns.persistence.orm.OrderColumn;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.FieldOutline;

public class ElementCollectionMapping implements
		FieldOutlineMapping<ElementCollection> {

	public ElementCollection process(Mapping context,
			FieldOutline fieldOutline, Options options) {

		final ElementCollection elementCollection = context.getCustomizing()
				.getElementCollection(fieldOutline);
		createElementCollection$Name(context, fieldOutline, elementCollection);
		createElementCollection$Column(context, fieldOutline, elementCollection);
		createElementCollection$OrderColumn(context, fieldOutline,
				elementCollection);
		createElementCollection$CollectionTable(context, fieldOutline,
				elementCollection);

		if (elementCollection.getLob() == null
				&& elementCollection.getTemporal() == null
				&& elementCollection.getEnumerated() == null) {
			if (context.getAttributeMapping().isTemporal(context, fieldOutline)) {
				elementCollection.setTemporal(context.getAttributeMapping()
						.createTemporalType(context, fieldOutline));
			} else if (context.getAttributeMapping().isEnumerated(context, fieldOutline)) {
				elementCollection.setEnumerated(context.getAttributeMapping()
						.createEnumerated(context, fieldOutline));
			} else if (context.getAttributeMapping().isLob(context, fieldOutline)) {
				elementCollection.setLob(context.getAttributeMapping()
						.createLob(context, fieldOutline));
			}

		}
		return elementCollection;
	}

	private void createElementCollection$Column(Mapping context,
			FieldOutline fieldOutline, final ElementCollection elementCollection) {
		if (elementCollection.getColumn() == null) {
			elementCollection.setColumn(new Column());
		}
		createElementCollection$Column$Name(context, fieldOutline,
				elementCollection.getColumn());
	}

	private void createElementCollection$Column$Name(Mapping context,
			FieldOutline fieldOutline, Column column) {
		if (column.getName() == null || "##default".equals(column.getName())) {
			column.setName(context.getNaming()
					.getElementCollection$Column$Name(context, fieldOutline));
		}
	}

	private void createElementCollection$CollectionTable(Mapping context,
			FieldOutline fieldOutline, ElementCollection elementCollection) {
		if (elementCollection.getCollectionTable() == null) {
			elementCollection.setCollectionTable(new CollectionTable());
		}

		final CollectionTable collectionTable = elementCollection
				.getCollectionTable();
		createElementCollection$CollectionTable$Name(context, fieldOutline,
				collectionTable);
		createElementCollection$CollectionTable$JoinColumn(context,
				fieldOutline, collectionTable);
	}

	private void createElementCollection$CollectionTable$Name(Mapping context,
			FieldOutline fieldOutline, CollectionTable collectionTable) {
		if (collectionTable.getName() == null
				|| "##default".equals(collectionTable.getName())) {
			collectionTable.setName(context.getNaming()
					.getElementCollection$CollectionTable$Name(context,
							fieldOutline));
		}
	}

	private void createElementCollection$Name(Mapping context,
			FieldOutline fieldOutline, final ElementCollection target) {
		target.setName(context.getNaming().getPropertyName(context,
				fieldOutline));
	}

	private void createElementCollection$OrderColumn(Mapping context,
			FieldOutline fieldOutline, final ElementCollection source) {
		if (source.getOrderColumn() == null) {
			source.setOrderColumn(new OrderColumn());
		}
		context.getAssociationMapping().createElementCollection$OrderColumn(
				context, fieldOutline, source.getOrderColumn());

	}

	// public void createOneToMany$OrderColumn(Mapping context,
	// FieldOutline fieldOutline, final OneToMany source) {
	// if (source.getOrderColumn() != null) {
	// context.getAssociationMapping().createOrderColumn(context,
	// fieldOutline, source.getOrderColumn());
	// }
	// }
	//
	// public void createOneToMany$TargetEntity(Mapping context,
	// FieldOutline fieldOutline, final OneToMany oneToMany) {
	//
	// final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();
	//
	// final Collection<? extends CTypeInfo> types = propertyInfo.ref();
	//
	// final CTypeInfo type = CTypeInfoUtils.getCommonBaseTypeInfo(types);
	//
	// assert type != null;
	//
	// assert type instanceof CClass;
	//
	// final CClass childClassInfo = (CClass) type;
	//
	// oneToMany.setTargetEntity(context.getNaming().getEntityClass(
	// fieldOutline.parent().parent(), childClassInfo.getType()));
	//
	// }
	//
	public void createElementCollection$CollectionTable$JoinColumn(
			Mapping context, FieldOutline fieldOutline,
			CollectionTable collectionTable) {

		final Collection<FieldOutline> idFieldsOutline = context
				.getAssociationMapping().getSourceIdFieldsOutline(context,
						fieldOutline);
		// if (idFieldsOutline.isEmpty()) {
		// collectionTable.getJoinColumn().clear();
		// }
		context.getAssociationMapping()
				.createElementCollection$CollectionTable$JoinColumns(context,
						fieldOutline, idFieldsOutline,
						collectionTable.getJoinColumn());
	}
}
