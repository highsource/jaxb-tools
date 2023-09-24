package org.jvnet.jaxb.plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.sun.tools.xjc.BadCommandLineException;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.Plugin;
import com.sun.tools.xjc.outline.Outline;

public abstract class AbstractPlugin extends Plugin {

	/**
	 * Plugin logger.
	 */
	protected Log logger = LogFactory.getLog(getClass());

	@Override
	public void onActivated(Options options) throws BadCommandLineException {
		super.onActivated(options);
		try {
			init(options);
		} catch (Exception ex) {
			throw new BadCommandLineException(
					"Could not initialize the plugin [" + getOptionName()
							+ "].", ex);
		}
	}

	@Override
	public boolean run(Outline outline, Options options,
			ErrorHandler errorHandler) throws SAXException {
		/*
		 * try { init(options); } catch (Exception ex) { SAXParseException saxex
		 * = new SAXParseException( "Could not initialize Spring context.",
		 * null, ex); errorHandler.fatalError(saxex); throw saxex; }
		 */
		try {
			beforeRun(outline, options);
			return run(outline, options);
		} catch (Exception ex) {
			final SAXParseException saxex = new SAXParseException(
					"Error during plugin execution.", null, ex);
			errorHandler.error(saxex);
			throw saxex;
		} finally {
			try {
				afterRun(outline, options);
			} catch (Exception ex) {
				final SAXParseException saxex = new SAXParseException(
						"Error during plugin execution.", null, ex);
				errorHandler.error(saxex);
				throw saxex;

			}
		}
	}

	protected void beforeRun(Outline outline, Options options) throws Exception {

	}

	protected boolean run(Outline outline, Options options) throws Exception {
		return true;
	}

	protected void afterRun(Outline outline, Options options) throws Exception {

	}

	protected void init(Options options) throws Exception {
	}

	public Collection<QName> getCustomizationElementNames() {
		return Collections.<QName> emptyList();
	}

	private List<String> customizationURIs;

	private Set<QName> customizationElementNames;

	@Override
	public List<String> getCustomizationURIs() {
		if (this.customizationURIs == null) {
			final Collection<QName> customizationElementNames = getCustomizationElementNames();
			this.customizationURIs = new ArrayList<String>(
					customizationElementNames.size());
			for (QName customizationElementName : customizationElementNames) {
				final String namespaceURI = customizationElementName
						.getNamespaceURI();
				if (!(namespaceURI== null || namespaceURI.length() == 0)) {
					this.customizationURIs.add(namespaceURI);
				}
			}
		}
		return this.customizationURIs;
	}

	@Override
	public boolean isCustomizationTagName(String namespaceURI, String localName) {
		if (this.customizationElementNames == null) {
			this.customizationElementNames = new HashSet<QName>(
					getCustomizationElementNames());
		}
		return this.customizationElementNames.contains(new QName(namespaceURI,
				localName));
	}

}
