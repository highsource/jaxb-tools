//package org.jvnet.jaxb2.maven2.resolver.tools;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//public abstract class DelegatingInputStreamWrapper extends InputStream {
//
//	protected abstract InputStream getInputStream() throws IOException;
//
//	@Override
//	public void close() throws IOException {
//		getInputStream().close();
//	}
//
//	@Override
//	public int read() throws IOException {
//		return getInputStream().read();
//	}
//
//	@Override
//	public int read(byte[] b, int off, int len) throws IOException {
//		return getInputStream().read(b, off, len);
//	}
//
//	@Override
//	public int available() throws IOException {
//		return getInputStream().available();
//	}
//
//	@Override
//	public long skip(long n) throws IOException {
//		return getInputStream().skip(n);
//	}
//
//	@Override
//	public boolean markSupported() {
//		try {
//			return getInputStream().markSupported();
//		} catch (IOException ioex) {
//			return false;
//		}
//	}
//
//	@Override
//	public synchronized void mark(int readlimit) {
//		try {
//			getInputStream().mark(readlimit);
//		} catch (IOException ioex) {
//			throw new IllegalStateException(
//					"Could not open the underlying input stream.", ioex);
//		}
//	}
//
//	@Override
//	public synchronized void reset() throws IOException {
//		getInputStream().reset();
//	}
//
//	@Override
//	public int read(byte[] b) throws IOException {
//		return getInputStream().read(b);
//	}
//}
