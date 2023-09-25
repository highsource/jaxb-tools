package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import org.jvnet.jaxb.util.OutlineUtils;

import jakarta.xml.ns.persistence.orm.Attributes;
import jakarta.xml.ns.persistence.orm.MappedSuperclass;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.ClassOutline;

public class MappedSuperclassMapping implements
		ClassOutlineMapping<MappedSuperclass> {

	public MappedSuperclass process(Mapping context, ClassOutline classOutline,
			Options options) {
		final MappedSuperclass entity = context.getCustomizing()
				.getMappedSuperclass(classOutline);
		createMappedSuperclass(context, classOutline, entity);
		return entity;
	}

	public void createMappedSuperclass(Mapping context,
			ClassOutline classOutline, final MappedSuperclass mappedSuperclass) {
		createMappedSuperclass$Class(context, classOutline, mappedSuperclass);
		/*
		 * createEntity$Inheritance(context, classOutline, mappedSuperclass);
		 *
		 * createEntity$Table(context, classOutline, mappedSuperclass);
		 */
		createMappedSuperclass$Attributes(context, classOutline,
				mappedSuperclass);
	}

	public void createMappedSuperclass$Class(Mapping context,
			ClassOutline classOutline, final MappedSuperclass mappedSuperclass) {
		if (mappedSuperclass.getClazz() == null
				|| "##default".equals(mappedSuperclass.getClazz())) {
			mappedSuperclass.setClazz(OutlineUtils.getClassName(classOutline));
		}
	}

	public void createMappedSuperclass$Attributes(Mapping context,
			ClassOutline classOutline, final MappedSuperclass mappedSuperclass) {
		final Attributes attributes = context.getAttributesMapping().process(
				context, classOutline, null);
		mappedSuperclass.setAttributes(attributes);
	}

}
