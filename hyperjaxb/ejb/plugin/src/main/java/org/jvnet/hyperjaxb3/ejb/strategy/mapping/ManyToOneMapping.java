package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import java.util.Collection;

import jakarta.xml.ns.persistence.orm.ManyToOne;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CClass;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.outline.FieldOutline;

public class ManyToOneMapping implements FieldOutlineMapping<ManyToOne> {

	public ManyToOne process(Mapping context, FieldOutline fieldOutline,
			Options options) {

		final ManyToOne manyToOne = context.getCustomizing().getManyToOne(
				fieldOutline);

		createManyToOne$Name(context, fieldOutline, manyToOne);
		createManyToOne$TargetEntity(context, fieldOutline, manyToOne);
		createManyToOne$JoinTableOrJoinColumn(context, fieldOutline, manyToOne);
		return manyToOne;
	}

	public void createManyToOne$Name(Mapping context,
			FieldOutline fieldOutline, final ManyToOne manyToOne) {
		manyToOne.setName(context.getNaming().getPropertyName(context,
				fieldOutline));
	}

	public void createManyToOne$TargetEntity(Mapping context,
			FieldOutline fieldOutline, final ManyToOne manyToOne) {

		final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();

		final Collection<? extends CTypeInfo> types = context.getGetTypes()
				.process(context, propertyInfo);

		assert types.size() == 1;

		final CTypeInfo type = types.iterator().next();

		assert type instanceof NType;

		assert type instanceof CClass;

		final CClass childClassInfo = (CClass) type;

		manyToOne.setTargetEntity(context.getNaming().getEntityClass(context,
				fieldOutline.parent().parent(), childClassInfo.getType()));

	}

	public void createManyToOne$JoinTableOrJoinColumn(Mapping context,
			FieldOutline fieldOutline, ManyToOne manyToOne) {

		if (manyToOne.getJoinColumn() != null
				&& !manyToOne.getJoinColumn().isEmpty()) {
			final Collection<FieldOutline> idFieldsOutline = context
					.getAssociationMapping().getTargetIdFieldsOutline(context,
							fieldOutline);
			// if (idFieldsOutline.isEmpty()) {
			// manyToOne.getJoinColumn().clear();
			// }
			context.getAssociationMapping().createJoinColumns(context,
					fieldOutline, idFieldsOutline, manyToOne.getJoinColumn());
		} else if (manyToOne.getJoinTable() != null) {
			final Collection<FieldOutline> sourceIdFieldOutlines = context
					.getAssociationMapping().getSourceIdFieldsOutline(context,
							fieldOutline);
			final Collection<FieldOutline> targetIdFieldOutlines = context
					.getAssociationMapping().getTargetIdFieldsOutline(context,
							fieldOutline);
			// if (sourceIdFieldOutlines.isEmpty()) {
			// manyToOne.setJoinTable(null);
			// } else {
			context.getAssociationMapping().createJoinTable(context,
					fieldOutline, sourceIdFieldOutlines, targetIdFieldOutlines,
					manyToOne.getJoinTable());
			// }
		}
	}
}
