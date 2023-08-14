package org.jvnet.jaxb.maven.resolver.tools;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Optional;

import org.apache.maven.plugin.logging.Log;
import org.jvnet.jaxb.maven.plugin.logging.NullLog;
import org.jvnet.jaxb.maven.util.StringUtils;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ReResolvingEntityResolverWrapper implements EntityResolver {

	private final EntityResolver entityResolver;
	private final Log log;

	public ReResolvingEntityResolverWrapper(EntityResolver entityResolver, Log log) {
		if (entityResolver == null) {
			throw new IllegalArgumentException("Provided entity resolver must not be null.");
		}
		this.entityResolver = entityResolver;
		this.log = Optional.ofNullable(log).orElse(NullLog.INSTANCE);
	}

	@Override
	public InputSource resolveEntity(String publicId, String systemId)
			throws SAXException, IOException {
		log.debug(MessageFormat.format("ReResolvingEntityResolverWrapper : Resolving publicId [{0}], systemId [{1}].", publicId, systemId));
		final InputSource resolvedInputSource = this.entityResolver.resolveEntity(publicId, systemId);
		if (resolvedInputSource == null) {
			log.debug("ReResolvingEntityResolverWrapper : Resolution result is null.");
			return null;
		} else {
			log.debug(MessageFormat.format("ReResolvingEntityResolverWrapper : Resolved to publicId [{0}], systemId [{1}].", resolvedInputSource.getPublicId(), resolvedInputSource.getSystemId()));
			final String pId = !StringUtils.isEmpty(publicId) ? publicId : resolvedInputSource.getPublicId();
			final String sId = computeSystemId(systemId, resolvedInputSource.getSystemId(), log);
			return new ReResolvingInputSourceWrapper(this.entityResolver, resolvedInputSource, pId, sId, resolvedInputSource.getPublicId(), resolvedInputSource.getSystemId());
		}
	}

	private static String computeSystemId(String systemId, String resolvedSystemId, Log log) {
		if (systemId == null) {
			return resolvedSystemId;
		}
		if (resolvedSystemId == null) {
			return systemId;
		}
		boolean fileExistsSystemId = checkFileExists(systemId, log);
		boolean fileExistsResolvedSystemId = checkFileExists(resolvedSystemId, log);
		return !StringUtils.isEmpty(systemId) && fileExistsSystemId ? systemId : fileExistsResolvedSystemId ? resolvedSystemId : systemId;
	}

	private static boolean checkFileExists(String sId, Log log) {
		try {
			URI uriSystemId = new URI(sId);
			if ("file".equals(uriSystemId.getScheme())) {
				if (!Files.exists(Paths.get(uriSystemId))) {
					// resolved file does not exist, warn and let's continue with original systemId
					log.warn(MessageFormat.format("ReResolvingEntityResolverWrapper : File {0} does not exists.", sId));
					return false;
				}
			}
		} catch (URISyntaxException ex) {
			// ignore, let it be handled by parser as is
		}
		return true;
	}
}
