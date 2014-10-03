package org.jvnet.jaxb2.maven2.resolver.tools;

import java.io.IOException;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ReResolvingEntityResolverWrapper implements EntityResolver {

	private final EntityResolver entityResolver;

	public ReResolvingEntityResolverWrapper(EntityResolver entityResolver) {
		if (entityResolver == null) {
			throw new IllegalArgumentException(
					"Provided entity resolver must not be null.");
		}
		this.entityResolver = entityResolver;
	}

	@Override
	public InputSource resolveEntity(String publicId, String systemId)
			throws SAXException, IOException {
		final InputSource resolvedInputSource = this.entityResolver
				.resolveEntity(publicId, systemId);
		if (resolvedInputSource == null) {
			return null;
		} else {
			final String pId = publicId != null ? publicId : resolvedInputSource.getPublicId();
			final String sId = systemId != null ? systemId : resolvedInputSource.getSystemId();
			return new ReResolvingInputSourceWrapper(this.entityResolver,
					resolvedInputSource, pId, sId);
		}
	}
}
