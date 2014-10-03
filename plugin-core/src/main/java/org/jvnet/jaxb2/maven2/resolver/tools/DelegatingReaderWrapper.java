//package org.jvnet.jaxb2.maven2.resolver.tools;
//
//import java.io.IOException;
//import java.io.Reader;
//import java.nio.CharBuffer;
//
//public abstract class DelegatingReaderWrapper extends Reader {
//
//	protected abstract Reader getReader() throws IOException;
//
//	@Override
//	public int read(char[] cbuf, int off, int len) throws IOException {
//		return getReader().read(cbuf, off, len);
//	}
//
//	@Override
//	public void close() throws IOException {
//		getReader().close();
//	}
//
//	@Override
//	public int read() throws IOException {
//		return getReader().read();
//	}
//
//	@Override
//	public void reset() throws IOException {
//		getReader().reset();
//	}
//
//	@Override
//	public boolean ready() throws IOException {
//		return getReader().ready();
//	}
//
//	@Override
//	public void mark(int readAheadLimit) throws IOException {
//		getReader().mark(readAheadLimit);
//	}
//
//	@Override
//	public boolean markSupported() {
//		try {
//			return getReader().markSupported();
//		} catch (IOException ioex) {
//			return false;
//		}
//	}
//
//	@Override
//	public long skip(long n) throws IOException {
//		return getReader().skip(n);
//	}
//
//	@Override
//	public int read(char[] cbuf) throws IOException {
//		return getReader().read(cbuf);
//	}
//
//	@Override
//	public int read(CharBuffer target) throws IOException {
//		return getReader().read(target);
//	}
//
//}
