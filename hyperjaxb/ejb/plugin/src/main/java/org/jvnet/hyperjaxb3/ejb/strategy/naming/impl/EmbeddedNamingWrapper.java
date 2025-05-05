package org.jvnet.hyperjaxb3.ejb.strategy.naming.impl;

import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Embedded;
import org.jvnet.hyperjaxb3.ejb.strategy.mapping.Mapping;
import org.jvnet.hyperjaxb3.ejb.strategy.naming.Naming;

import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.Outline;

public class EmbeddedNamingWrapper implements Naming {

	private final Naming naming;
	private final FieldOutline parentFieldOutline;

	public EmbeddedNamingWrapper(Naming naming, FieldOutline fieldOutline) {
		super();
		this.naming = naming;
		this.parentFieldOutline = fieldOutline;
	}

	public String getPropertyName(Mapping context, FieldOutline fieldOutline) {
		return naming.getPropertyName(context, fieldOutline);
	}

	public String getEntityClass(Mapping context, Outline outline, NType type) {
		return naming.getEntityClass(context, outline, type);
	}

	public String getEntityName(Mapping context, Outline outline, NType type) {
		return naming.getEntityName(context, outline, type);
	}

	public String getPersistenceUnitName(Mapping context, Outline outline) {
		return naming.getPersistenceUnitName(context, outline);
	}

	public String getEntityTable$Name(Mapping context, ClassOutline classOutline) {
		return naming.getEntityTable$Name(context, classOutline);
	}

	public String getEntityTable$Name(Mapping context, FieldOutline fieldOutline) {
		return naming.getEntityTable$Name(context, fieldOutline);
	}

	public String getColumn$Name$Prefix(Mapping context) {
		final String prefix;

		final Embedded embedded = context.getCustomizing().getEmbedded(
				parentFieldOutline);
		if (embedded != null && embedded.getColumnNamePrefix() != null) {
			prefix = embedded.getColumnNamePrefix();
		} else {
			prefix = naming.getColumn$Name$Prefix(context)
					+ parentFieldOutline.getPropertyInfo().getName(true) + "_";
		}
		return prefix;
	}

	public String getColumn$Name(Mapping context, FieldOutline fieldOutline) {
		return naming.getColumn$Name(context, fieldOutline);
	}

	public String getJoinTable$Name(Mapping context, FieldOutline fieldOutline) {
		return naming.getJoinTable$Name(context, fieldOutline);
	}

	public String getJoinColumn$Name(Mapping context,
			FieldOutline fieldOutline, FieldOutline idFieldOutline) {
		return naming.getJoinColumn$Name(context, fieldOutline, idFieldOutline);
	}

	public String getJoinTable$JoinColumn$Name(Mapping context,
			FieldOutline fieldOutline, FieldOutline idFieldOutline) {
		return naming.getJoinTable$JoinColumn$Name(context, fieldOutline,
				idFieldOutline);
	}

	public String getJoinTable$InverseJoinColumn$Name(Mapping context,
			FieldOutline fieldOutline, FieldOutline idFieldOutline) {
		return naming.getJoinTable$InverseJoinColumn$Name(context,
				fieldOutline, idFieldOutline);
	}

	public String getOrderColumn$Name(Mapping context, FieldOutline fieldOutline) {
		return naming.getOrderColumn$Name(context, fieldOutline);
	}

	public String getName(Mapping context, String draftName) {
		return naming.getName(context, draftName);
	}

	public Naming createEmbeddedNaming(Mapping context,
			FieldOutline fieldOutline) {
		return new EmbeddedNamingWrapper(this, fieldOutline);
	}

	public String getElementCollection$CollectionTable$Name(Mapping context,
			FieldOutline fieldOutline) {
		return naming.getElementCollection$CollectionTable$Name(context,
				fieldOutline);
	}

	public String getElementCollection$CollectionTable$JoinColumn$Name(
			Mapping context, FieldOutline fieldOutline,
			FieldOutline idFieldOutline) {
		return naming.getElementCollection$CollectionTable$JoinColumn$Name(
				context, fieldOutline, idFieldOutline);
	}

	public String getElementCollection$Column$Name(Mapping context,
			FieldOutline fieldOutline) {
		return naming.getElementCollection$Column$Name(context, fieldOutline);
	}

	public String getElementCollection$OrderColumn$Name(Mapping context,
			FieldOutline fieldOutline) {
		return naming.getElementCollection$OrderColumn$Name(context,
				fieldOutline);
	}

    @Override
    public int getMaxIdentifierLength() {
        return naming.getMaxIdentifierLength();
    }

    @Override
    public void setMaxIdentifierLength(int maxIdentifierLength) {
        naming.setMaxIdentifierLength(maxIdentifierLength);
    }
}
