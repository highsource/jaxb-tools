package org.jvnet.jaxb2.maven2.net;

import java.io.File;
import java.net.URI;
import java.text.MessageFormat;

import org.apache.maven.plugin.logging.Log;

public class FileURILastModifiedResolver extends
		AbstractSchemeAwareURILastModifiedResolver {

	public static final String SCHEME = "file";

	public FileURILastModifiedResolver(Log logger) {
		super(SCHEME, logger);
	}

	@Override
	protected Long getLastModifiedForScheme(final URI uri) {
		try {
			final File file = new File(uri);
			if (file.exists()) {
				long lastModified = file.lastModified();
				if (lastModified != 0) {
					getLogger()
							.debug(MessageFormat
									.format("Last modification timestamp of the file URI [{0}] is [{1,date,yyyy-MM-dd HH:mm:ss.SSS}].",
											uri, lastModified));
					return lastModified;
				} else {
					getLogger()
							.error(MessageFormat
									.format("Could not retrieve the last modification of the file [{0}] .",
											file.getAbsolutePath()));
				}
			} else {
				getLogger().error(
						MessageFormat.format("File [{0}] does not exist.",
								file.getAbsolutePath()));
			}
		} catch (Exception ex) {
			getLogger()
					.error(MessageFormat.format(
							"Could not retrieve the last modification of the URI [{0}] .",
							uri), ex);
		}
		getLogger()
				.warn(MessageFormat
						.format("Last modification of the URI [{0}] is not known.",
								uri));
		return null;
	}
}
