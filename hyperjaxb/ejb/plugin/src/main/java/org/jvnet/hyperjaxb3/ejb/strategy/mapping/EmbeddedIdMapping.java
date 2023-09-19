package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import jakarta.xml.ns.persistence.orm.EmbeddedId;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.FieldOutline;

public class EmbeddedIdMapping implements FieldOutlineMapping<EmbeddedId> {

	public EmbeddedId process(Mapping context, FieldOutline fieldOutline,
			Options options) {

		final EmbeddedId embeddedId = context.getCustomizing().getEmbeddedId(
				fieldOutline);

		createEmbeddedId$Name(context, fieldOutline, embeddedId);
		context.getAttributeMapping().createAttributeOverride(context,
				fieldOutline, embeddedId.getAttributeOverride());

		return embeddedId;
	}

	public void createEmbeddedId$Name(Mapping context,
			FieldOutline fieldOutline, final EmbeddedId embeddedId) {
		embeddedId.setName(context.getNaming().getPropertyName(context,
				fieldOutline));
	}
}
