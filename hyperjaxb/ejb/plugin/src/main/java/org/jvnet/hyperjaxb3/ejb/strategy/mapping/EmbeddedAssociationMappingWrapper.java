package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.jaxb.util.CustomizationUtils;

import jakarta.xml.ns.persistence.orm.AssociationOverride;
import jakarta.xml.ns.persistence.orm.JoinColumn;
import jakarta.xml.ns.persistence.orm.JoinTable;
import jakarta.xml.ns.persistence.orm.OrderColumn;
import jakarta.xml.ns.persistence.orm.PrimaryKeyJoinColumn;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;

public class EmbeddedAssociationMappingWrapper implements AssociationMapping {

	private final AssociationMapping associationMapping;
	private final FieldOutline parentFieldOutline;

	public EmbeddedAssociationMappingWrapper(
			AssociationMapping associationMapping,
			FieldOutline parentFieldOutline) {
		super();
		this.associationMapping = associationMapping;
		this.parentFieldOutline = parentFieldOutline;
	}

	public Collection<FieldOutline> getSourceIdFieldsOutline(Mapping context,
			FieldOutline fieldOutline) {
		final ClassOutline classOutline = parentFieldOutline.parent();

		return getIdFieldsOutline(classOutline);
	}

	public Collection<FieldOutline> getTargetIdFieldsOutline(Mapping context,
			FieldOutline fieldOutline) {
		return associationMapping.getTargetIdFieldsOutline(context,
				fieldOutline);
	}

	public void createPrimaryKeyJoinColumns(Mapping context,
			FieldOutline fieldOutline,
			Collection<FieldOutline> idFieldOutlines,
			List<PrimaryKeyJoinColumn> primaryKeyJoinColumns) {
		associationMapping.createPrimaryKeyJoinColumns(context, fieldOutline,
				idFieldOutlines, primaryKeyJoinColumns);
	}

	public void createJoinColumns(Mapping context, FieldOutline fieldOutline,
			Collection<FieldOutline> idFieldOutlines,
			List<JoinColumn> joinColumns) {
		associationMapping.createJoinColumns(context, fieldOutline,
				idFieldOutlines, joinColumns);
	}

	public void createJoinTable(Mapping context, FieldOutline fieldOutline,
			Collection<FieldOutline> sourceIdFieldOutlines,
			Collection<FieldOutline> targetIdFieldOutlines, JoinTable joinTable) {
		associationMapping.createJoinTable(context, fieldOutline,
				sourceIdFieldOutlines, targetIdFieldOutlines, joinTable);
	}

	public void createOrderColumn(Mapping context, FieldOutline fieldOutline,
			OrderColumn orderColumn) {
		associationMapping
				.createOrderColumn(context, fieldOutline, orderColumn);
	}

	private Collection<FieldOutline> getIdFieldsOutline(
			final ClassOutline classOutline) {
		final Collection<FieldOutline> idFieldOutlines = new ArrayList<FieldOutline>();
		ClassOutline current = classOutline;

		while (current != null) {
			for (FieldOutline idFieldOutline : current.getDeclaredFields()) {
				final CPropertyInfo propertyInfo = idFieldOutline
						.getPropertyInfo();
				if ((CustomizationUtils.containsCustomization(propertyInfo,
						Customizations.ID_ELEMENT_NAME) || CustomizationUtils
						.containsCustomization(propertyInfo,
								Customizations.EMBEDDED_ID_ELEMENT_NAME))
						&& !CustomizationUtils.containsCustomization(
								propertyInfo,
								Customizations.IGNORED_ELEMENT_NAME)) {
					idFieldOutlines.add(idFieldOutline);
				}
			}
			current = current.getSuperClass();
		}

		return idFieldOutlines;
	}

	public void createElementCollection$CollectionTable$JoinColumns(
			Mapping context, FieldOutline fieldOutline,
			Collection<FieldOutline> idFieldOutlines,
			List<JoinColumn> joinColumns) {
		associationMapping.createElementCollection$CollectionTable$JoinColumns(
				context, fieldOutline, idFieldOutlines, joinColumns);
	}

	public void createElementCollection$OrderColumn(Mapping context,
			FieldOutline fieldOutline, OrderColumn orderColumn) {
		associationMapping.createElementCollection$OrderColumn(context,
				fieldOutline, orderColumn);

	}

	public void createAssociationOverride(Mapping context,
			FieldOutline fieldOutline,
			List<AssociationOverride> associationOverrides) {
		associationMapping.createAssociationOverride(context, fieldOutline,
				associationOverrides);
	}

	public AssociationMapping createEmbeddedAssociationMapping(
			FieldOutline fieldOutline) {
		return new EmbeddedAssociationMappingWrapper(this, parentFieldOutline);
	}

}
