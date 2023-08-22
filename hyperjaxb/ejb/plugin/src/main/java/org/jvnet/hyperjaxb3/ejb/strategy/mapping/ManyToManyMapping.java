package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import java.util.Collection;

import org.jvnet.hyperjaxb3.xjc.model.CTypeInfoUtils;

import com.sun.java.xml.ns.persistence.orm.ManyToMany;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CClass;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.outline.FieldOutline;

public class ManyToManyMapping implements FieldOutlineMapping<ManyToMany> {

	public ManyToMany process(Mapping context, FieldOutline fieldOutline,
			Options options) {

		final ManyToMany manyToMany = context.getCustomizing().getManyToMany(
				fieldOutline);

		createManyToMany$Name(context, fieldOutline, manyToMany);
		createManyToMany$OrderColumn(context, fieldOutline, manyToMany);
		createManyToMany$TargetEntity(context, fieldOutline, manyToMany);
		createManyToMany$JoinTable(context, fieldOutline, manyToMany);
		return manyToMany;
	}

	public void createManyToMany$Name(Mapping context,
			FieldOutline fieldOutline, final ManyToMany manyToMany) {
		manyToMany.setName(context.getNaming().getPropertyName(context,
				fieldOutline));
	}

	public void createManyToMany$OrderColumn(Mapping context,
			FieldOutline fieldOutline, final ManyToMany manyToMany) {
		if (manyToMany.getOrderColumn() != null) {
			context.getAssociationMapping().createOrderColumn(context,
					fieldOutline, manyToMany.getOrderColumn());
		}
	}

	public void createManyToMany$TargetEntity(Mapping context,
			FieldOutline fieldOutline, final ManyToMany manyToMany) {

		final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();

		final Collection<? extends CTypeInfo> types = context.getGetTypes()
				.process(context, propertyInfo);

		final CTypeInfo type = CTypeInfoUtils.getCommonBaseTypeInfo(types);

		assert type != null;

		assert type instanceof CClass;

		final CClass childClassInfo = (CClass) type;

		manyToMany.setTargetEntity(context.getNaming().getEntityClass(context,
				fieldOutline.parent().parent(), childClassInfo.getType()));

	}

	public void createManyToMany$JoinTable(Mapping context,
			FieldOutline fieldOutline, ManyToMany manyToMany) {
		final Collection<FieldOutline> sourceIdFieldOutlines = context
				.getAssociationMapping().getSourceIdFieldsOutline(context,
						fieldOutline);
		final Collection<FieldOutline> targetIdFieldOutlines = context
				.getAssociationMapping().getTargetIdFieldsOutline(context,
						fieldOutline);
		// if (sourceIdFieldOutlines.isEmpty()) {
		// manyToMany.setJoinTable(null);
		// } else
		if (manyToMany.getJoinTable() != null) {
			context.getAssociationMapping().createJoinTable(context,
					fieldOutline, sourceIdFieldOutlines, targetIdFieldOutlines,
					manyToMany.getJoinTable());
		}
		// else {
		// // ***
		// final JoinTable joinTable = new JoinTable();
		// manyToMany.setJoinTable(joinTable);
		// context.getAssociationMapping().createJoinTable(context,
		// fieldOutline, sourceIdFieldOutlines, targetIdFieldOutlines,
		// manyToMany.getJoinTable());
		// }

	}
}
