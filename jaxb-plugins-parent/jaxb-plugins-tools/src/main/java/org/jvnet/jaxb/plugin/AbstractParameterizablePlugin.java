package org.jvnet.jaxb.plugin;

import java.io.IOException;

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
			setProperty(propertyName, value);
		}
		return consumed;
	}

	/**
	 * Set the {@code propertyName} filed of this {@link AbstractParameterizablePlugin} to the specified {@code value}.
	 * <p>
	 * This implementation always throws {@link UnsupportedOperationException}.
	 * It is up to the subclasses of {@link #AbstractParameterizablePlugin()} to property implement this method and
	 * possibly delegate to this implementation if an unknown property occurs.
	 *
	 * @param propertyName the field to set
	 * @param value the value to set
	 */
	protected void setProperty(String propertyName, String value) {
		throw new UnsupportedOperationException("Cannot set property " + propertyName + " on " + this.getClass().getName());
	}
}
