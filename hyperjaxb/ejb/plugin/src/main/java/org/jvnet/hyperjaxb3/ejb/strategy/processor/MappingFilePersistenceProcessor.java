package org.jvnet.hyperjaxb3.ejb.strategy.processor;

import java.io.File;
import java.util.Collection;
import java.util.Collections;

import jakarta.xml.bind.JAXBException;

import org.jvnet.hyperjaxb3.ejb.plugin.EjbPlugin;
import org.jvnet.hyperjaxb3.ejb.strategy.naming.Naming;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.OutlineProcessor;
import org.jvnet.hyperjaxb3.persistence.util.PersistenceUtils;
import org.jvnet.jaxb2_commons.lang.JAXBMergeCollectionsStrategy;
import org.springframework.beans.factory.annotation.Required;

import com.sun.java.xml.ns.persistence.Persistence;
import com.sun.java.xml.ns.persistence.Persistence.PersistenceUnit;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.Outline;

public class MappingFilePersistenceProcessor implements
		OutlineProcessor<EjbPlugin> {

	private PersistenceFactory persistenceFactory;

	public PersistenceFactory getPersistenceFactory() {
		return persistenceFactory;
	}

	@Required
	public void setPersistenceFactory(PersistenceFactory persistenceFactory) {
		this.persistenceFactory = persistenceFactory;
	}

	private PersistenceMarshaller persistenceMarshaller;

	public PersistenceMarshaller getPersistenceMarshaller() {
		return persistenceMarshaller;
	}

	@Required
	public void setPersistenceMarshaller(
			PersistenceMarshaller persistenceMarshaller) {
		this.persistenceMarshaller = persistenceMarshaller;
	}

	private Naming naming;

	public Naming getNaming() {
		return naming;
	}

	public void setNaming(Naming naming) {
		this.naming = naming;
	}

	public Collection<ClassOutline> process(EjbPlugin plugin, Outline outline,
			Options options) throws Exception {

		Collection<ClassOutline> includedClasses = getOutlineProcessor()
				.process(plugin, outline, options);

		final String pun = plugin.getPersistenceUnitName();
		final String persistenceUnitName = pun != null ? pun : getNaming()
				.getPersistenceUnitName(plugin.getMapping(), outline);

		final PersistenceUnit persistenceUnit = getPersistenceUnitFactory()
				.createPersistenceUnit(includedClasses);

		final Persistence persistence = createPersistence(plugin,
				persistenceUnit, persistenceUnitName);

		getPersistenceMarshaller().marshallPersistence(outline.getCodeModel(),
				persistence);

		// TODO Auto-generated method stub
		return includedClasses;
	}

	private OutlineProcessor<EjbPlugin> outlineProcessor;

	public OutlineProcessor<EjbPlugin> getOutlineProcessor() {
		return outlineProcessor;
	}

	@Required
	public void setOutlineProcessor(OutlineProcessor<EjbPlugin> outlineProcessor) {
		this.outlineProcessor = outlineProcessor;
	}

	protected Persistence createPersistence(EjbPlugin plugin,
			final PersistenceUnit persistenceUnit, String persistenceUnitName)
			throws JAXBException {

		final Persistence persistence;
		final PersistenceUnit targetPersistenceUnit;

		final File persistenceXml = plugin.getPersistenceXml();

		if (persistenceXml != null) {
			try {

				persistence = (Persistence) PersistenceUtils.CONTEXT
						.createUnmarshaller().unmarshal(persistenceXml);

				PersistenceUnit foundPersistenceUnit = null;

				for (final PersistenceUnit unit : persistence
						.getPersistenceUnit()) {
					if (persistenceUnitName != null
							&& persistenceUnitName.equals(unit.getName())) {
						foundPersistenceUnit = unit;
					} else if ("##generated".equals(unit.getName())) {
						foundPersistenceUnit = unit;
						// foundPersistenceUnit.setName(persistenceUnitName);
					}
				}
				if (foundPersistenceUnit != null) {
					targetPersistenceUnit = foundPersistenceUnit;
				} else {
					targetPersistenceUnit = new PersistenceUnit();
					persistence.getPersistenceUnit().add(targetPersistenceUnit);
					// targetPersistenceUnit.setName(persistenceUnitName);
				}

			} catch (Exception ex) {
				throw new JAXBException("Persistence XML file ["
						+ persistenceXml + "] could not be parsed.", ex);
			}

		} else {
			persistence = getPersistenceFactory().createPersistence();
			targetPersistenceUnit = new PersistenceUnit();
			persistence.getPersistenceUnit().add(targetPersistenceUnit);
		}

		targetPersistenceUnit.mergeFrom(null, null, persistenceUnit,
				targetPersistenceUnit, JAXBMergeCollectionsStrategy.INSTANCE2);
		targetPersistenceUnit.setName(persistenceUnitName);
		Collections.sort(targetPersistenceUnit.getMappingFile());
		Collections.sort(targetPersistenceUnit.getClazz());
		return persistence;
	}

	private PersistenceUnitFactory persistenceUnitFactory = new MappingFilePersistenceUnitFactory();

	public PersistenceUnitFactory getPersistenceUnitFactory() {
		return persistenceUnitFactory;
	}

	public void setPersistenceUnitFactory(
			PersistenceUnitFactory persistenceUnitFactory) {
		this.persistenceUnitFactory = persistenceUnitFactory;
	}

}
