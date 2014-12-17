package org.hisrc.maven.plugin.logging.slfj4;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.Validate;
import org.slf4j.helpers.Util;
import org.slf4j.impl.SimpleLoggerFactory;
import org.slf4j.spi.LocationAwareLogger;

public class LogLoggerConfiguration {

	private final long startTime = System.currentTimeMillis();

	private final Properties properties;

	private int defaultLogLevel = LocationAwareLogger.INFO_INT;
	private boolean showDateTime = false;
	private String dateTimeFormatString = null;
	private DateFormat dateFormatter = null;
	private boolean showThreadName = true;
	private boolean showLogName = true;
	private boolean showShortLogName = false;

	/**
	 * All system properties used by <code>SimpleLogger</code> start with this
	 * prefix
	 */
	public static final String SYSTEM_PREFIX = "org.hisrc.maven.plugin.logging.slfj4.logLogger.";

	public static final String DEFAULT_LOG_LEVEL_KEY = SYSTEM_PREFIX
			+ "defaultLogLevel";
	public static final String SHOW_DATE_TIME_KEY = SYSTEM_PREFIX
			+ "showDateTime";
	public static final String DATE_TIME_FORMAT_KEY = SYSTEM_PREFIX
			+ "dateTimeFormat";
	public static final String SHOW_THREAD_NAME_KEY = SYSTEM_PREFIX
			+ "showThreadName";
	public static final String SHOW_LOG_NAME_KEY = SYSTEM_PREFIX
			+ "showLogName";
	public static final String SHOW_SHORT_LOG_NAME_KEY = SYSTEM_PREFIX
			+ "showShortLogName";
	public static final String LOG_FILE_KEY = SYSTEM_PREFIX + "logFile";
	public static final String LEVEL_IN_BRACKETS_KEY = SYSTEM_PREFIX
			+ "levelInBrackets";
	public static final String WARN_LEVEL_STRING_KEY = SYSTEM_PREFIX
			+ "warnLevelString";

	public static final String LOG_KEY_PREFIX = SYSTEM_PREFIX + "log.";

	private String getStringProperty(String name) {
		String prop = null;
		try {
			prop = System.getProperty(name);
		} catch (SecurityException e) {
			; // Ignore
		}
		return (prop == null) ? properties.getProperty(name) : prop;
	}

	private String getStringProperty(String name, String defaultValue) {
		String prop = getStringProperty(name);
		return (prop == null) ? defaultValue : prop;
	}

	private boolean getBooleanProperty(String name, boolean defaultValue) {
		String prop = getStringProperty(name);
		return (prop == null) ? defaultValue : "true".equalsIgnoreCase(prop);
	}

	// Initialize class attributes.
	// Load properties file, if found.
	// Override with system properties.
	void init() {

		String defaultLogLevelString = getStringProperty(DEFAULT_LOG_LEVEL_KEY,
				null);
		if (defaultLogLevelString != null)
			defaultLogLevel = stringToLevel(defaultLogLevelString);

		showLogName = getBooleanProperty(SHOW_LOG_NAME_KEY, showLogName);
		showShortLogName = getBooleanProperty(SHOW_SHORT_LOG_NAME_KEY,
				showShortLogName);
		showDateTime = getBooleanProperty(SHOW_DATE_TIME_KEY, showDateTime);
		showThreadName = getBooleanProperty(SHOW_THREAD_NAME_KEY,
				showThreadName);
		dateTimeFormatString = getStringProperty(DATE_TIME_FORMAT_KEY,
				dateTimeFormatString);

		if (dateTimeFormatString != null) {
			try {
				dateFormatter = new SimpleDateFormat(dateTimeFormatString);
			} catch (IllegalArgumentException e) {
				Util.report(MessageFormat.format(
						"Bad date format [{0}] will output relative time",
						dateTimeFormatString), e);
			}
		}
	}

	/**
	 * Package access allows only {@link SimpleLoggerFactory} to instantiate
	 * SimpleLogger instances.
	 */
	LogLoggerConfiguration(Properties properties) {
		this.properties = Validate.notNull(properties);
		init();
	}

	private String recursivelyComputeLevelString(String name) {
		String tempName = name;
		String levelString = null;
		int indexOfLastDot = tempName.length();
		while ((levelString == null) && (indexOfLastDot > -1)) {
			tempName = tempName.substring(0, indexOfLastDot);
			levelString = getStringProperty(LOG_KEY_PREFIX + tempName, null);
			indexOfLastDot = String.valueOf(tempName).lastIndexOf(".");
		}
		return levelString;
	}

	private static int stringToLevel(String levelStr) {
		if ("trace".equalsIgnoreCase(levelStr)) {
			return LocationAwareLogger.TRACE_INT;
		} else if ("debug".equalsIgnoreCase(levelStr)) {
			return LocationAwareLogger.DEBUG_INT;
		} else if ("info".equalsIgnoreCase(levelStr)) {
			return LocationAwareLogger.INFO_INT;
		} else if ("warn".equalsIgnoreCase(levelStr)) {
			return LocationAwareLogger.WARN_INT;
		} else if ("error".equalsIgnoreCase(levelStr)) {
			return LocationAwareLogger.ERROR_INT;
		}
		// assume INFO by default
		return LocationAwareLogger.INFO_INT;
	}

	private String getFormattedDate(Date now) {
		String dateText;
		synchronized (dateFormatter) {
			dateText = dateFormatter.format(now);
		}
		return dateText;
	}

	public int getLogLevel(String name) {
		String levelString = recursivelyComputeLevelString(name);
		if (levelString != null) {
			return stringToLevel(levelString);
		} else {
			return defaultLogLevel;
		}
	}

	public String getFormattedDateTime() {
		if (dateFormatter != null) {
			return getFormattedDate(new Date());
		} else {
			return Long.toString(System.currentTimeMillis() - startTime);
		}

	}

	public boolean isShowDateTime() {
		return showDateTime;
	}

	public boolean isShowThreadName() {
		return showThreadName;
	}

	public boolean isShowShortLogName() {
		return showShortLogName;
	}

	public boolean isShowLogName() {
		return showLogName;
	}
}
