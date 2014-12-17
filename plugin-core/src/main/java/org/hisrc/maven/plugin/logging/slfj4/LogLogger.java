package org.hisrc.maven.plugin.logging.slfj4;

import org.apache.maven.plugin.logging.Log;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.impl.SimpleLoggerFactory;
import org.slf4j.spi.LocationAwareLogger;

public class LogLogger extends MarkerIgnoringBase {

	private static final long serialVersionUID = -1330122643950450284L;

	public static final int LOG_LEVEL_TRACE = LocationAwareLogger.TRACE_INT;
	public static final int LOG_LEVEL_DEBUG = LocationAwareLogger.DEBUG_INT;
	public static final int LOG_LEVEL_INFO = LocationAwareLogger.INFO_INT;
	public static final int LOG_LEVEL_WARN = LocationAwareLogger.WARN_INT;
	public static final int LOG_LEVEL_ERROR = LocationAwareLogger.ERROR_INT;

	/** The current log level */
	private final int currentLogLevel;

	/** The short name of this simple log instance */
	private transient String shortLogName = null;

	private final Log log;
	private final LogLoggerConfiguration configuration;
	private final String name;

	/**
	 * Package access allows only {@link SimpleLoggerFactory} to instantiate
	 * SimpleLogger instances.
	 */
	LogLogger(Log log, LogLoggerConfiguration configuration, int level,
			String name) {
		this.log = log;
		this.configuration = configuration;
		this.name = name;
		this.currentLogLevel = level;
	}

	/**
	 * This is our internal implementation for logging regular
	 * (non-parameterized) log messages.
	 *
	 * @param level
	 *            One of the LOG_LEVEL_XXX constants defining the log level
	 * @param message
	 *            The message itself
	 * @param t
	 *            The exception whose stack trace should be logged
	 */
	private void log(int level, String message, Throwable t) {
		if (!isLevelEnabled(level)) {
			return;
		}

		StringBuffer buf = new StringBuffer(32);

		// Append date-time if so configured
		if (configuration.isShowDateTime()) {
			buf.append(configuration.getFormattedDateTime());
			buf.append(' ');
		}

		// Append current thread name if so configured
		if (configuration.isShowThreadName()) {
			buf.append('[');
			buf.append(Thread.currentThread().getName());
			buf.append("] ");
		}

		// Append the name of the log instance if so configured
		if (configuration.isShowShortLogName()) {
			if (shortLogName == null)
				shortLogName = computeShortName();
			buf.append(String.valueOf(shortLogName)).append(" - ");
		} else if (configuration.isShowLogName()) {
			buf.append(String.valueOf(name)).append(" - ");
		}

		// Append the message
		buf.append(message);

		write(level, buf, t);

	}

	void write(int level, StringBuffer buf, Throwable t) {
		switch (level) {
		case LOG_LEVEL_TRACE:
			if (t == null) {
				log.debug(buf.toString());
			} else {
				log.debug(buf.toString(), t);
			}
			break;
		case LOG_LEVEL_DEBUG:
			if (t == null) {
				log.debug(buf.toString());
			} else {
				log.debug(buf.toString(), t);
			}
			break;
		case LOG_LEVEL_INFO:
			if (t == null) {
				log.info(buf.toString());
			} else {
				log.info(buf.toString(), t);
			}
			break;
		case LOG_LEVEL_WARN:
			if (t == null) {
				log.warn(buf.toString());
			} else {
				log.warn(buf.toString(), t);
			}
			break;
		case LOG_LEVEL_ERROR:
			if (t == null) {
				log.error(buf.toString());
			} else {
				log.error(buf.toString(), t);
			}
			break;
		}
	}

	private String computeShortName() {
		return name.substring(name.lastIndexOf(".") + 1);
	}

	/**
	 * For formatted messages, first substitute arguments and then log.
	 *
	 * @param level
	 * @param format
	 * @param arg1
	 * @param arg2
	 */
	private void formatAndLog(int level, String format, Object arg1, Object arg2) {
		if (!isLevelEnabled(level)) {
			return;
		}
		FormattingTuple tp = MessageFormatter.format(format, arg1, arg2);
		log(level, tp.getMessage(), tp.getThrowable());
	}

	/**
	 * For formatted messages, first substitute arguments and then log.
	 *
	 * @param level
	 * @param format
	 * @param arguments
	 *            a list of 3 ore more arguments
	 */
	private void formatAndLog(int level, String format, Object... arguments) {
		if (!isLevelEnabled(level)) {
			return;
		}
		FormattingTuple tp = MessageFormatter.arrayFormat(format, arguments);
		log(level, tp.getMessage(), tp.getThrowable());
	}

	/**
	 * Is the given log level currently enabled?
	 *
	 * @param logLevel
	 *            is this level enabled?
	 */
	protected boolean isLevelEnabled(int logLevel) {
		// log level are numerically ordered so can use simple numeric
		// comparison
		return (logLevel >= currentLogLevel);
	}

	/** Are {@code trace} messages currently enabled? */
	public boolean isTraceEnabled() {
		return isLevelEnabled(LOG_LEVEL_TRACE);
	}

	/**
	 * A simple implementation which logs messages of level TRACE according to
	 * the format outlined above.
	 */
	public void trace(String msg) {
		log(LOG_LEVEL_TRACE, msg, null);
	}

	/**
	 * Perform single parameter substitution before logging the message of level
	 * TRACE according to the format outlined above.
	 */
	public void trace(String format, Object param1) {
		formatAndLog(LOG_LEVEL_TRACE, format, param1, null);
	}

	/**
	 * Perform double parameter substitution before logging the message of level
	 * TRACE according to the format outlined above.
	 */
	public void trace(String format, Object param1, Object param2) {
		formatAndLog(LOG_LEVEL_TRACE, format, param1, param2);
	}

	/**
	 * Perform double parameter substitution before logging the message of level
	 * TRACE according to the format outlined above.
	 */
	public void trace(String format, Object... argArray) {
		formatAndLog(LOG_LEVEL_TRACE, format, argArray);
	}

	/** Log a message of level TRACE, including an exception. */
	public void trace(String msg, Throwable t) {
		log(LOG_LEVEL_TRACE, msg, t);
	}

	/** Are {@code debug} messages currently enabled? */
	public boolean isDebugEnabled() {
		return isLevelEnabled(LOG_LEVEL_DEBUG);
	}

	/**
	 * A simple implementation which logs messages of level DEBUG according to
	 * the format outlined above.
	 */
	public void debug(String msg) {
		log(LOG_LEVEL_DEBUG, msg, null);
	}

	/**
	 * Perform single parameter substitution before logging the message of level
	 * DEBUG according to the format outlined above.
	 */
	public void debug(String format, Object param1) {
		formatAndLog(LOG_LEVEL_DEBUG, format, param1, null);
	}

	/**
	 * Perform double parameter substitution before logging the message of level
	 * DEBUG according to the format outlined above.
	 */
	public void debug(String format, Object param1, Object param2) {
		formatAndLog(LOG_LEVEL_DEBUG, format, param1, param2);
	}

	/**
	 * Perform double parameter substitution before logging the message of level
	 * DEBUG according to the format outlined above.
	 */
	public void debug(String format, Object... argArray) {
		formatAndLog(LOG_LEVEL_DEBUG, format, argArray);
	}

	/** Log a message of level DEBUG, including an exception. */
	public void debug(String msg, Throwable t) {
		log(LOG_LEVEL_DEBUG, msg, t);
	}

	/** Are {@code info} messages currently enabled? */
	public boolean isInfoEnabled() {
		return isLevelEnabled(LOG_LEVEL_INFO);
	}

	/**
	 * A simple implementation which logs messages of level INFO according to
	 * the format outlined above.
	 */
	public void info(String msg) {
		log(LOG_LEVEL_INFO, msg, null);
	}

	/**
	 * Perform single parameter substitution before logging the message of level
	 * INFO according to the format outlined above.
	 */
	public void info(String format, Object arg) {
		formatAndLog(LOG_LEVEL_INFO, format, arg, null);
	}

	/**
	 * Perform double parameter substitution before logging the message of level
	 * INFO according to the format outlined above.
	 */
	public void info(String format, Object arg1, Object arg2) {
		formatAndLog(LOG_LEVEL_INFO, format, arg1, arg2);
	}

	/**
	 * Perform double parameter substitution before logging the message of level
	 * INFO according to the format outlined above.
	 */
	public void info(String format, Object... argArray) {
		formatAndLog(LOG_LEVEL_INFO, format, argArray);
	}

	/** Log a message of level INFO, including an exception. */
	public void info(String msg, Throwable t) {
		log(LOG_LEVEL_INFO, msg, t);
	}

	/** Are {@code warn} messages currently enabled? */
	public boolean isWarnEnabled() {
		return isLevelEnabled(LOG_LEVEL_WARN);
	}

	/**
	 * A simple implementation which always logs messages of level WARN
	 * according to the format outlined above.
	 */
	public void warn(String msg) {
		log(LOG_LEVEL_WARN, msg, null);
	}

	/**
	 * Perform single parameter substitution before logging the message of level
	 * WARN according to the format outlined above.
	 */
	public void warn(String format, Object arg) {
		formatAndLog(LOG_LEVEL_WARN, format, arg, null);
	}

	/**
	 * Perform double parameter substitution before logging the message of level
	 * WARN according to the format outlined above.
	 */
	public void warn(String format, Object arg1, Object arg2) {
		formatAndLog(LOG_LEVEL_WARN, format, arg1, arg2);
	}

	/**
	 * Perform double parameter substitution before logging the message of level
	 * WARN according to the format outlined above.
	 */
	public void warn(String format, Object... argArray) {
		formatAndLog(LOG_LEVEL_WARN, format, argArray);
	}

	/** Log a message of level WARN, including an exception. */
	public void warn(String msg, Throwable t) {
		log(LOG_LEVEL_WARN, msg, t);
	}

	/** Are {@code error} messages currently enabled? */
	public boolean isErrorEnabled() {
		return isLevelEnabled(LOG_LEVEL_ERROR);
	}

	/**
	 * A simple implementation which always logs messages of level ERROR
	 * according to the format outlined above.
	 */
	public void error(String msg) {
		log(LOG_LEVEL_ERROR, msg, null);
	}

	/**
	 * Perform single parameter substitution before logging the message of level
	 * ERROR according to the format outlined above.
	 */
	public void error(String format, Object arg) {
		formatAndLog(LOG_LEVEL_ERROR, format, arg, null);
	}

	/**
	 * Perform double parameter substitution before logging the message of level
	 * ERROR according to the format outlined above.
	 */
	public void error(String format, Object arg1, Object arg2) {
		formatAndLog(LOG_LEVEL_ERROR, format, arg1, arg2);
	}

	/**
	 * Perform double parameter substitution before logging the message of level
	 * ERROR according to the format outlined above.
	 */
	public void error(String format, Object... argArray) {
		formatAndLog(LOG_LEVEL_ERROR, format, argArray);
	}

	/** Log a message of level ERROR, including an exception. */
	public void error(String msg, Throwable t) {
		log(LOG_LEVEL_ERROR, msg, t);
	}

}
