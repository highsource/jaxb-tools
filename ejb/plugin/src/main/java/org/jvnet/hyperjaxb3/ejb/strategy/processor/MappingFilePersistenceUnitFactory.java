package org.jvnet.hyperjaxb3.ejb.strategy.processor;

import java.util.Collection;

import org.jvnet.jaxb2_commons.util.OutlineUtils;

import com.sun.java.xml.ns.persistence.Persistence.PersistenceUnit;
import com.sun.tools.xjc.outline.ClassOutline;

public class MappingFilePersistenceUnitFactory implements
		PersistenceUnitFactory {

	public PersistenceUnit createPersistenceUnit(
			final Collection<ClassOutline> includedClasses) {
		final PersistenceUnit persistenceUnit = new PersistenceUnit();
		for (final ClassOutline classOutline : includedClasses) {
			final String className = OutlineUtils.getClassName(classOutline);
			persistenceUnit.getMappingFile().add(
					className.replace('.', '/') + ".orm.xml");
		}
		return persistenceUnit;

	}
}
