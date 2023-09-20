package org.jvnet.jaxb2_commons.plugin;

import java.io.IOException;

import org.apache.commons.beanutils.BeanUtils;

import com.sun.tools.xjc.BadCommandLineException;
import com.sun.tools.xjc.Options;

/**
 * Abstract base class for parameterizable JAXB plugins.
 * <p>
 * This plugin looks for the arguments of the form
 * <code>-<em>myPlugin</em>-<em>name</em>=<em>value</em></code>
 * (<code><em>myPlugin</em></code> is the plugin option name) and
 * then invokes <code>set<em>Name</em>(<em>value</em>)</code> on itself.</p>
 * <p>For instance, the argument <code>-Xfoo-bar=test</code> triggers
 * <code>setBar("test")</code> invocation.</p>
 * <p>Values are injected using Commons BeanUtils as bean properties, so
 * types will be converted correspondingly</p>
 *
 * @author valikov
 */
public abstract class AbstractParameterizablePlugin extends AbstractPlugin {

	/**
	 * Parses the arguments and injects values into the beans via properties.
	 */
	public int parseArgument(Options opt, String[] args, int start)
			throws BadCommandLineException, IOException {

		int consumed = 0;
		final String optionPrefix = "-" + getOptionName() + "-";
		final int optionPrefixLength = optionPrefix.length();

		final String arg = args[start];
		final int equalsPosition = arg.indexOf('=');

		if (arg.startsWith(optionPrefix) && equalsPosition > optionPrefixLength) {
			final String propertyName = arg.substring(optionPrefixLength,
					equalsPosition);

			final String value = arg.substring(equalsPosition + 1);
			consumed++;
			try {
				BeanUtils.setProperty(this, propertyName, value);
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new BadCommandLineException("Error setting property ["
						+ propertyName + "], value [" + value + "].");
			}
		}
		return consumed;
	}
}
