package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import java.util.Collection;

import org.jvnet.hyperjaxb3.xjc.model.CTypeInfoUtils;

import com.sun.java.xml.ns.persistence.orm.OneToMany;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CClass;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.outline.FieldOutline;

public class OneToManyMapping implements FieldOutlineMapping<OneToMany> {

	public OneToMany process(Mapping context, FieldOutline fieldOutline,
			Options options) {

		final OneToMany oneToMany = context.getCustomizing().getOneToMany(
				fieldOutline);
		createOneToMany$Name(context, fieldOutline, oneToMany);
		createOneToMany$OrderColumn(context, fieldOutline, oneToMany);
		createOneToMany$TargetEntity(context, fieldOutline, oneToMany);
		createOneToMany$JoinTableOrJoinColumn(context, fieldOutline, oneToMany);
		return oneToMany;
	}

	public void createOneToMany$Name(Mapping context,
			FieldOutline fieldOutline, final OneToMany oneToMany) {
		oneToMany.setName(context.getNaming().getPropertyName(context,
				fieldOutline));
	}

	public void createOneToMany$OrderColumn(Mapping context,
			FieldOutline fieldOutline, final OneToMany source) {
		if (source.getOrderColumn() != null) {
			context.getAssociationMapping().createOrderColumn(context,
					fieldOutline, source.getOrderColumn());
		}
	}

	public void createOneToMany$TargetEntity(Mapping context,
			FieldOutline fieldOutline, final OneToMany oneToMany) {

		final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();

		final Collection<? extends CTypeInfo> types = context.getGetTypes()
				.process(context, propertyInfo);

		final CTypeInfo type = CTypeInfoUtils.getCommonBaseTypeInfo(types);

		assert type != null;

		assert type instanceof CClass;

		final CClass childClassInfo = (CClass) type;

		oneToMany.setTargetEntity(context.getNaming().getEntityClass(context,
				fieldOutline.parent().parent(), childClassInfo.getType()));

	}

	public void createOneToMany$JoinTableOrJoinColumn(Mapping context,
			FieldOutline fieldOutline, OneToMany oneToMany) {

		if (!oneToMany.getJoinColumn().isEmpty()) {
			final Collection<FieldOutline> idFieldsOutline = context
					.getAssociationMapping().getSourceIdFieldsOutline(context,
							fieldOutline);
			// if (idFieldsOutline.isEmpty()) {
			// oneToMany.getJoinColumn().clear();
			// }
			context.getAssociationMapping().createJoinColumns(context,
					fieldOutline, idFieldsOutline, oneToMany.getJoinColumn());
		} else if (oneToMany.getJoinTable() != null) {
			final Collection<FieldOutline> sourceIdFieldOutlines = context
					.getAssociationMapping().getSourceIdFieldsOutline(context,
							fieldOutline);
			final Collection<FieldOutline> targetIdFieldOutlines = context
					.getAssociationMapping().getTargetIdFieldsOutline(context,
							fieldOutline);

			// if (sourceIdFieldOutlines.isEmpty()) {
			// oneToMany.setJoinTable(null);
			// } else {
			context.getAssociationMapping().createJoinTable(context,
					fieldOutline, sourceIdFieldOutlines, targetIdFieldOutlines,
					oneToMany.getJoinTable());
			// }
		}
	}
}
