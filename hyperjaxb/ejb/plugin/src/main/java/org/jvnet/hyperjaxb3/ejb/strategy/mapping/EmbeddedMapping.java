package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import com.sun.java.xml.ns.persistence.orm.Embedded;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.FieldOutline;

public class EmbeddedMapping implements FieldOutlineMapping<Embedded> {

	public Embedded process(Mapping context, FieldOutline fieldOutline,
			Options options) {

		final Embedded embedded = context.getCustomizing().getEmbedded(
				fieldOutline);

		createEmbedded$Name(context, fieldOutline, embedded);

		context.getAttributeMapping().createAttributeOverride(context,
				fieldOutline, embedded.getAttributeOverride());

		context.getAssociationMapping().createAssociationOverride(context,
				fieldOutline, embedded.getAssociationOverride());

		return embedded;
	}

	public void createEmbedded$Name(Mapping context, FieldOutline fieldOutline,
			final Embedded embedded) {
		embedded.setName(context.getNaming().getPropertyName(context,
				fieldOutline));
	}

}
