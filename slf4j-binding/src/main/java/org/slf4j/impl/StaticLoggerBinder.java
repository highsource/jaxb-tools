package org.slf4j.impl;

import org.hisrc.slf4j.impl.StaticLoggerFactoryBinder;
import org.slf4j.ILoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

public class StaticLoggerBinder implements LoggerFactoryBinder {

	private static final StaticLoggerBinder INSTANCE = new StaticLoggerBinder();

	private StaticLoggerBinder() {
	}

	public static StaticLoggerBinder getSingleton() {
		return INSTANCE;
	}

	@Override
	public ILoggerFactory getLoggerFactory() {
		return StaticLoggerFactoryBinder.INSTANCE.getLoggerFactory();
	}

	@Override
	public String getLoggerFactoryClassStr() {
		return StaticLoggerFactoryBinder.INSTANCE.getLoggerFactoryClassStr();
	}
}
