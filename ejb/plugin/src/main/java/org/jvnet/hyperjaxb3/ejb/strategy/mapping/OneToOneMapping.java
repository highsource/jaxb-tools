package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import java.util.Collection;

import com.sun.java.xml.ns.persistence.orm.OneToOne;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CClass;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.outline.FieldOutline;

public class OneToOneMapping implements FieldOutlineMapping<OneToOne> {

	public OneToOne process(Mapping context, FieldOutline fieldOutline,
			Options options) {

		final OneToOne oneToOne = context.getCustomizing().getOneToOne(
				fieldOutline);
		createOneToOne$Name(context, fieldOutline, oneToOne);
		createOneToOne$TargetEntity(context, fieldOutline, oneToOne);
		createOneToOne$JoinTableOrJoinColumnOrPrimaryKeyJoinColumn(context,
				fieldOutline, oneToOne);
		return oneToOne;
	}

	public void createOneToOne$Name(Mapping context, FieldOutline fieldOutline,
			final OneToOne oneToOne) {
		oneToOne.setName(context.getNaming().getPropertyName(context,
				fieldOutline));
	}

	public void createOneToOne$TargetEntity(Mapping context,
			FieldOutline fieldOutline, final OneToOne oneToOne) {

		final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();

		final Collection<? extends CTypeInfo> types = context.getGetTypes()
				.process(context, propertyInfo);

		assert types.size() == 1;

		final CTypeInfo type = types.iterator().next();

		assert type instanceof CClass;

		final CClass childClassInfo = (CClass) type;

		oneToOne.setTargetEntity(context.getNaming().getEntityClass(context,
				fieldOutline.parent().parent(), childClassInfo.getType()));

	}

	public void createOneToOne$JoinTableOrJoinColumnOrPrimaryKeyJoinColumn(
			Mapping context, FieldOutline fieldOutline, OneToOne oneToOne) {
		if (!oneToOne.getPrimaryKeyJoinColumn().isEmpty()) {

			final Collection<FieldOutline> idFieldOutlines = context
					.getAssociationMapping().getSourceIdFieldsOutline(context,
							fieldOutline);
			// if (idFieldOutlines.isEmpty()) {
			// oneToOne.getPrimaryKeyJoinColumn().clear();
			// } else {
			context.getAssociationMapping().createPrimaryKeyJoinColumns(
					context, fieldOutline, idFieldOutlines,
					oneToOne.getPrimaryKeyJoinColumn());
			// }
		} else if (!oneToOne.getJoinColumn().isEmpty()) {
			final Collection<FieldOutline> idFieldsOutline = context
					.getAssociationMapping().getSourceIdFieldsOutline(context,
							fieldOutline);
			// if (idFieldsOutline.isEmpty()) {
			// oneToOne.getJoinColumn().clear();
			// }

			context.getAssociationMapping().createJoinColumns(context,
					fieldOutline, idFieldsOutline, oneToOne.getJoinColumn());
		} else if (oneToOne.getJoinTable() != null) {
			final Collection<FieldOutline> sourceIdFieldOutlines = context
					.getAssociationMapping().getSourceIdFieldsOutline(context,
							fieldOutline);
			final Collection<FieldOutline> targetIdFieldOutlines = context
					.getAssociationMapping().getTargetIdFieldsOutline(context,
							fieldOutline);

			// if (sourceIdFieldOutlines.isEmpty()) {
			// oneToOne.setJoinTable(null);
			// } else {
			context.getAssociationMapping().createJoinTable(context,
					fieldOutline, sourceIdFieldOutlines, targetIdFieldOutlines,
					oneToOne.getJoinTable());
			// }
		}
	}

}
