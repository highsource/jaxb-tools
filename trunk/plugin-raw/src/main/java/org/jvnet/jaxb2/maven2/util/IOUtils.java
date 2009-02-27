package org.jvnet.jaxb2.maven2.util;

import java.io.File;
import java.net.MalformedURLException;

import org.xml.sax.InputSource;

public class IOUtils {

	/**
	 * Creates an input source for the given file.
	 * 
	 * @param file
	 *            file to create input source for.
	 * 
	 * @return Created input source object.
	 */
	public static InputSource getInputSource(File file) {
		try {
			return new InputSource(StringUtils.escapeSpace(file.toURI().toURL()
					.toExternalForm()));
		} catch (MalformedURLException e) {
			return new InputSource(file.getPath());
		}
	}

}
