package org.jvnet.jaxb2.maven2.resolver.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ReResolvingInputSourceWrapper extends InputSource {

	private final EntityResolver entityResolver;
	private final InputSource inputSource;

	public ReResolvingInputSourceWrapper(EntityResolver entityResolver,
			InputSource inputSource, String publicId, String systemId) {
		this.entityResolver = entityResolver;
		this.inputSource = inputSource;
		this.setPublicId(publicId);
		this.setSystemId(systemId);
	}

	@Override
	public Reader getCharacterStream() {
		final Reader originalReader = inputSource.getCharacterStream();
		if (originalReader == null) {
			return null;
		} else {
			try {
				InputSource resolvedEntity = this.entityResolver.resolveEntity(
						getPublicId(), getSystemId());
				if (resolvedEntity != null) {
					return resolvedEntity.getCharacterStream();
				} else {
					return originalReader;
				}
			} catch (IOException ioex) {
				return originalReader;
			} catch (SAXException saxex) {
				return originalReader;
			}
		}
	}

	@Override
	public void setCharacterStream(Reader characterStream) {
	}

	@Override
	public InputStream getByteStream() {
		final InputStream originalInputStream = inputSource.getByteStream();
		if (originalInputStream == null) {
			return null;
		} else {
			try {
				InputSource resolvedEntity = this.entityResolver.resolveEntity(
						getPublicId(), getSystemId());
				if (resolvedEntity != null) {
					return resolvedEntity.getByteStream();
				} else {
					return originalInputStream;
				}
			} catch (IOException ioex) {
				return originalInputStream;
			} catch (SAXException saxex) {
				return originalInputStream;
			}
		}
	}

	@Override
	public void setByteStream(InputStream byteStream) {
	}
}
