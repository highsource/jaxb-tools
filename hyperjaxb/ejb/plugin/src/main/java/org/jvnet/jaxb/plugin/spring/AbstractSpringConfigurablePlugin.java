package org.jvnet.jaxb.plugin.spring;

import com.sun.tools.xjc.BadCommandLineException;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.Outline;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.jaxb.plugin.AbstractParameterizablePlugin;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public abstract class AbstractSpringConfigurablePlugin extends
		AbstractParameterizablePlugin {

	protected Log logger = LogFactory.getLog(getClass());

	private AbstractXmlApplicationContext applicationContext;

	public AbstractXmlApplicationContext getApplicationContext() {
		return applicationContext;
	}

	protected String[] getDefaultConfigLocations() {
		return null;
	}

	private String[] configLocations = getDefaultConfigLocations();

	public String[] getConfigLocations() {
		return configLocations;
	}

	public void setConfigLocations(String[] configLocations) {
		this.configLocations = configLocations;
	}

	protected int getAutowireMode() {
		return AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE;
	}

	protected boolean isDependencyCheck() {
		return false;
	}

	public void init(Options options) throws Exception {

	}

	@Override
	protected void beforeRun(Outline outline, Options options) throws Exception {
		super.beforeRun(outline, options);
		final String[] configLocations = getConfigLocations();
		if (!ArrayUtils.isEmpty(configLocations)) {
			final String configLocationsString = ArrayUtils
					.toString(configLocations);
			logger.debug("Loading application context from ["
					+ configLocationsString + "].");
			try {
				applicationContext = new FileSystemXmlApplicationContext(
						configLocations, false);
				applicationContext.setClassLoader(Thread.currentThread()
						.getContextClassLoader());
				applicationContext.refresh();
				if (getAutowireMode() != AutowireCapableBeanFactory.AUTOWIRE_NO) {
					applicationContext.getBeanFactory().autowireBeanProperties(
							this, getAutowireMode(), isDependencyCheck());
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				ex.getCause().printStackTrace();
				logger.error("Error loading applicaion context from ["
						+ configLocationsString + "].", ex);
				throw new BadCommandLineException(
						"Error loading  applicaion context from ["
								+ configLocationsString + "].", ex);
			}
		}
	}

}
