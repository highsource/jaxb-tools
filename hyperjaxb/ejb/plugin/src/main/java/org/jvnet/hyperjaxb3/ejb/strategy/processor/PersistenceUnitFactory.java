package org.jvnet.hyperjaxb3.ejb.strategy.processor;

import java.util.Collection;

import jakarta.xml.ns.persistence.Persistence.PersistenceUnit;
import com.sun.tools.xjc.outline.ClassOutline;

public interface PersistenceUnitFactory {

	public PersistenceUnit createPersistenceUnit(Collection<ClassOutline> includedClasses);

}
