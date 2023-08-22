package org.jvnet.hyperjaxb3.ejb.strategy.processor;

import java.util.Collection;

import com.sun.java.xml.ns.persistence.Persistence.PersistenceUnit;
import com.sun.tools.xjc.outline.ClassOutline;

public interface PersistenceUnitFactory {
	
	public PersistenceUnit createPersistenceUnit(Collection<ClassOutline> includedClasses);

}
