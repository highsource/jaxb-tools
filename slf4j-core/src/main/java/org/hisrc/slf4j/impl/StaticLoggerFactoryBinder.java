package org.hisrc.slf4j.impl;

import org.slf4j.ILoggerFactory;
import org.slf4j.helpers.NOPLoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

public class StaticLoggerFactoryBinder implements LoggerFactoryBinder {

	private final static ILoggerFactory FALLBACK_LOGGER_FACTORY = new NOPLoggerFactory();

	private ILoggerFactory loggerFactory = FALLBACK_LOGGER_FACTORY;

	public static StaticLoggerFactoryBinder INSTANCE = new StaticLoggerFactoryBinder();

	private StaticLoggerFactoryBinder() {
		// Hidden constructor to avoid instantiation
	}

	public ILoggerFactory getLoggerFactory() {
		return loggerFactory;
	}

	@Override
	public String getLoggerFactoryClassStr() {
		return loggerFactory.getClass().getName();
	}

	public void setLoggerFactory(ILoggerFactory loggerFactory) {
		if (loggerFactory == null) {
			throw new IllegalArgumentException(
					"Logger factory may not be null.");
		}
		this.loggerFactory = loggerFactory;
	}

}
