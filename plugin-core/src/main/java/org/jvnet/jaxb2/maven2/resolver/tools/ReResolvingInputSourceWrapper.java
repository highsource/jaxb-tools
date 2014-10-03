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
			// return new DelegatingReaderWrapper() {
			// private Reader reader = null;
			//
			// private Reader openReader() throws IOException {
			// Reader r = null;
			// try {
			// final String publicId = getPublicId();
			// final String systemId = getSystemId();
			// final InputSource resolvedInputSource =
			// ReResolvingInputSourceWrapper.this.entityResolver
			// .resolveEntity(publicId, systemId);
			// final Reader resolvedReader = resolvedInputSource
			// .getCharacterStream();
			// if (resolvedReader != null) {
			// r = resolvedReader;
			// } else {
			// // Fallback to the original reader;
			// r = originalReader;
			// }
			// } catch (SAXException saxex) {
			// throw new IOException(saxex);
			// }
			// return r;
			// }
			//
			// @Override
			// protected Reader getReader() throws IOException {
			// if (reader == null) {
			// reader = openReader();
			// }
			// return reader;
			// }
			// };
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
			// return new DelegatingInputStreamWrapper() {
			// private InputStream inputStream = null;
			//
			// private InputStream openInputStream() throws IOException {
			// InputStream is = null;
			// try {
			// final String publicId = getPublicId();
			// final String systemId = getSystemId();
			// final InputSource resolvedInputSource =
			// ReResolvingInputSourceWrapper.this.entityResolver
			// .resolveEntity(publicId, systemId);
			// final InputStream resolvedInputStream = resolvedInputSource
			// .getByteStream();
			// if (resolvedInputStream != null) {
			// is = resolvedInputStream;
			// } else {
			// // Fallback to the original InputStream;
			// is = originalInputStream;
			// }
			// } catch (SAXException saxex) {
			// throw new IOException(saxex);
			// }
			// return is;
			// }
			//
			// @Override
			// protected InputStream getInputStream() throws IOException {
			// if (inputStream == null) {
			// inputStream = openInputStream();
			// }
			// return inputStream;
			// }
			// };
		}
	}

	@Override
	public void setByteStream(InputStream byteStream) {
	}
}
