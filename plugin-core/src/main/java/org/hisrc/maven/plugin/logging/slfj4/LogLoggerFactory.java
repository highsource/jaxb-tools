package org.hisrc.maven.plugin.logging.slfj4;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.Validate;
import org.apache.maven.plugin.logging.Log;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

public class LogLoggerFactory implements ILoggerFactory {

	private final ConcurrentMap<String, Logger> loggerMap;
	private final Log log;
	private final LogLoggerConfiguration configuration;

	public LogLoggerFactory(Log log, Properties properties) {
		this.log = Validate.notNull(log);
		this.loggerMap = new ConcurrentHashMap<String, Logger>();
		this.configuration = new LogLoggerConfiguration(
				Validate.notNull(properties));
	}

	public LogLoggerFactory(Log log) {
		this(log, new Properties());
	}

	public Logger getLogger(String name) {
		Logger logger = loggerMap.get(name);
		if (logger != null) {
			return logger;
		} else {
			int logLevel = this.configuration.getLogLevel(name);
			final Logger newInstance = new LogLogger(log, configuration,
					logLevel, name);
			final Logger oldInstance = loggerMap.putIfAbsent(name, newInstance);
			return oldInstance == null ? newInstance : oldInstance;
		}
	}

	void reset() {
		loggerMap.clear();
	}

}
