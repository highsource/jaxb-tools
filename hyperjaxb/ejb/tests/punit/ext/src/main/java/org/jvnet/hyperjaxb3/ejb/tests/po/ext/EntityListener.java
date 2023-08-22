package org.jvnet.hyperjaxb3.ejb.tests.po.ext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EntityListener {

	protected static Log logger = LogFactory.getLog(EntityListener.class);

	public void prePersist(Object object) {
		logger.debug("Extended pre-persisting [" + object + "].");

	}
}
