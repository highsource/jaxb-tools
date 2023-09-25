package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.hyperjaxb3.ejb.plugin.EjbPlugin;
import org.jvnet.hyperjaxb3.ejb.strategy.ignoring.Ignoring;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.OutlineProcessor;
import org.jvnet.hyperjaxb3.persistence.jpa3.JPA3Utils;
import org.jvnet.jaxb.util.CodeModelUtils;
import org.jvnet.jaxb.util.OutlineUtils;

import com.sun.codemodel.fmt.JTextFile;
import jakarta.xml.ns.persistence.orm.Embeddable;
import jakarta.xml.ns.persistence.orm.Entity;
import jakarta.xml.ns.persistence.orm.EntityMappings;
import jakarta.xml.ns.persistence.orm.MappedSuperclass;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.Outline;

public class MarshalMappings implements OutlineProcessor<EjbPlugin> {

	protected Marshaller getMarshaller() throws JAXBException {
		return JPA3Utils.createMarshaller();
	}

	protected Log logger = LogFactory.getLog(getClass());

	public Collection<ClassOutline> process(EjbPlugin context, Outline outline,
			Options options) throws Exception {
		logger.debug("Processing outline with context path ["
				+ OutlineUtils.getContextPath(outline) + "].");

		final Collection<? extends ClassOutline> classes = outline.getClasses();
		final Collection<ClassOutline> processedClassOutlines = new ArrayList<ClassOutline>(
				classes.size());

		for (final ClassOutline classOutline : classes) {
			if (!getIgnoring()
					.isClassOutlineIgnored(getMapping(), classOutline)) {
				final ClassOutline processedClassOutline = process(this,
						classOutline, options);
				if (processedClassOutline != null) {
					processedClassOutlines.add(processedClassOutline);
				}
			}
		}
		return processedClassOutlines;
	}

	public ClassOutline process(MarshalMappings context,
			ClassOutline classOutline, Options options) throws Exception {
		logger.debug("Processing class outline ["
				+ OutlineUtils.getClassName(classOutline) + "].");

		final String className = CodeModelUtils
				.getLocalClassName(classOutline.ref);

		final JTextFile classOrmXmlFile = new JTextFile(className + ".orm.xml");

		classOutline._package()._package().addResourceFile(classOrmXmlFile);

		final EntityMappings entityMappings = createEntityMappings();

		final Object draftEntityOrMappedSuperclassOrEmbeddable = context
				.getMapping().getEntityOrMappedSuperclassOrEmbeddableMapping()
				.process(context.getMapping(), classOutline, options);
		if (draftEntityOrMappedSuperclassOrEmbeddable instanceof Entity) {
			final Entity draftEntity = (Entity) draftEntityOrMappedSuperclassOrEmbeddable;

			final Entity entity = new Entity();
			entity.mergeFrom(draftEntity, entity);
			entityMappings.getEntity().add(entity);
		} else if (draftEntityOrMappedSuperclassOrEmbeddable instanceof MappedSuperclass) {
			final MappedSuperclass draftMappedSuperclass = (MappedSuperclass) draftEntityOrMappedSuperclassOrEmbeddable;

			final MappedSuperclass entity = new MappedSuperclass();
			entity.mergeFrom(draftMappedSuperclass, entity);
			entityMappings.getMappedSuperclass().add(entity);
		} else if (draftEntityOrMappedSuperclassOrEmbeddable instanceof Embeddable) {
			final Embeddable draftEmbeddable = (Embeddable) draftEntityOrMappedSuperclassOrEmbeddable;

			final Embeddable entity = new Embeddable();
			entity.mergeFrom(draftEmbeddable, entity);
			entityMappings.getEmbeddable().add(entity);
		} else {
			throw new AssertionError(
					"Either one-to-many or many-to-many mappings are expected.");
		}

		final Writer writer = new StringWriter();
		getMarshaller().marshal(entityMappings, writer);
		classOrmXmlFile.setContents(writer.toString());
		return classOutline;
	}

	protected EntityMappings createEntityMappings() {
		final EntityMappings entityMappings = new EntityMappings();
		entityMappings.setVersion("1.0");
		return entityMappings;
	}

	private Ignoring ignoring;

	public Ignoring getIgnoring() {
		return ignoring;
	}

	public void setIgnoring(Ignoring ignoring) {
		this.ignoring = ignoring;
	}

	private Mapping mapping;

	public Mapping getMapping() {
		return mapping;
	}

	public void setMapping(Mapping mapping) {
		this.mapping = mapping;
	}

}
