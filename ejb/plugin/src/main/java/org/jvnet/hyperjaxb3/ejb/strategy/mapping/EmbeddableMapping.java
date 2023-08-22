package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import org.jvnet.jaxb2_commons.util.OutlineUtils;

import com.sun.java.xml.ns.persistence.orm.Embeddable;
import com.sun.java.xml.ns.persistence.orm.EmbeddableAttributes;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.ClassOutline;

public class EmbeddableMapping implements ClassOutlineMapping<Embeddable> {

	// private static Log logger = LogFactory.getLog(EntityMapping.class);

	public Embeddable process(Mapping context, ClassOutline classOutline,
			Options options) {
		final Embeddable entity = context.getCustomizing().getEmbeddable(
				classOutline);
		createEmbeddable(context, classOutline, entity);
		return entity;
	}

	public void createEmbeddable(Mapping context, ClassOutline classOutline,
			final Embeddable entity) {
		createEmbeddable$Class(context, classOutline, entity);

		createEmbeddable$Attributes(context, classOutline, entity);
	}

	public void createEmbeddable$Class(Mapping context,
			ClassOutline classOutline, final Embeddable entity) {
		if (entity.getClazz() == null || "##default".equals(entity.getClazz())) {
			entity.setClazz(OutlineUtils.getClassName(classOutline));
		}
	}

	public void createEmbeddable$Attributes(Mapping context,
			ClassOutline classOutline, final Embeddable entity) {
		final EmbeddableAttributes attributes = context
				.getEmbeddableAttributesMapping().process(context,
						classOutline, null);
		entity.setAttributes(attributes);
	}

}
