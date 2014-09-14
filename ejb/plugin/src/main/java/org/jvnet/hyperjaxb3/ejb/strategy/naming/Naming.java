package org.jvnet.hyperjaxb3.ejb.strategy.naming;

import org.jvnet.hyperjaxb3.ejb.strategy.mapping.Mapping;

import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.Outline;

public interface Naming {

	public String getColumn$Name$Prefix(Mapping context);

	public String getPropertyName(Mapping context, FieldOutline fieldOutline);

	public String getEntityClass(Mapping context, Outline outline, NType type);

	public String getEntityName(Mapping context, Outline outline, NType type);

	public String getPersistenceUnitName(Mapping context, Outline outline);

	public String getEntityTable$Name(Mapping context, ClassOutline classOutline);

	public String getEntityTable$Name(Mapping context, FieldOutline fieldOutline);

	public String getColumn$Name(Mapping context, FieldOutline fieldOutline);

	public String getJoinTable$Name(Mapping context, FieldOutline fieldOutline);

	public String getJoinColumn$Name(Mapping context,
			FieldOutline fieldOutline, FieldOutline idFieldOutline);

	public String getJoinTable$JoinColumn$Name(Mapping context,
			FieldOutline fieldOutline, FieldOutline idFieldOutline);

	public String getJoinTable$InverseJoinColumn$Name(Mapping context,
			FieldOutline fieldOutline, FieldOutline idFieldOutline);

	public String getOrderColumn$Name(Mapping context, FieldOutline fieldOutline);

	public String getElementCollection$CollectionTable$Name(Mapping context,
			FieldOutline fieldOutline);

	public String getElementCollection$CollectionTable$JoinColumn$Name(
			Mapping context, FieldOutline fieldOutline,
			FieldOutline idFieldOutline);

	public String getElementCollection$OrderColumn$Name(Mapping context,
			FieldOutline fieldOutline);

	public String getElementCollection$Column$Name(Mapping context,
			FieldOutline fieldOutline);

	public String getName(Mapping context, final String draftName);

	public Naming createEmbeddedNaming(Mapping context,
			FieldOutline fieldOutline);
}
