package org.jvnet.hyperjaxb3.ejb.dynamic.tests;

import java.io.File;

import jakarta.persistence.Basic;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.hyperjaxb3.ejb.dynamic.DynamicCompiler;
import org.jvnet.hyperjaxb3.ejb.test.RoundtripTest;

public class TestDynamicCompiler extends TestCase {

	protected Log logger = LogFactory.getLog(getClass());

	private File temporaryDirectory;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		if (temporaryDirectory == null) {
			temporaryDirectory = File.createTempFile("hyperjaxb3-", "");
			temporaryDirectory.delete();
			temporaryDirectory.mkdirs();
		} else {
			FileUtils.cleanDirectory(temporaryDirectory);
		}
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		if (temporaryDirectory != null) {
			// FileUtils.deleteDirectory(temporaryDirectory);
		}
	}

	public void testCompile() throws Exception {

		final File schema = new File(getClass().getResource("schema.xsd")
				.toURI());
		final File bindings = new File(getClass().getResource("bindings.xjb")
				.toURI());

		final DynamicCompiler compiler = new DynamicCompiler(
				new File[] { schema }, new File[] { bindings }, null,
				temporaryDirectory);

		compiler.execute();

		final ClassLoader oldClassLoader = Thread.currentThread()
				.getContextClassLoader();
		try {
			final ClassLoader newClassLoader = compiler
					.createClassLoader(oldClassLoader);
			Thread.currentThread().setContextClassLoader(newClassLoader);

			final RoundtripTest test = new RoundtripTest() {
				@Override
				public String getContextPath() {
					return compiler.getContextPath();
				}

				@Override
				protected File getSamplesDirectory() {
					return schema.getParentFile();
				}

				@Override
				protected ClassLoader getContextClassLoader() {
					return Thread.currentThread().getContextClassLoader();
				}
			};

			test.setUp();

			test.testSamples();

			test.tearDown();
		} finally {
			Thread.currentThread().setContextClassLoader(oldClassLoader);
		}
	}

	public void testUnmarshall() throws Exception {

		final File schema = new File(getClass().getResource("schema.xsd")
				.toURI());
		final File bindings = new File(getClass().getResource("bindings.xjb")
				.toURI());
		final File sample = new File(getClass().getResource("po.xml").toURI());

		final DynamicCompiler compiler = new DynamicCompiler(
				new File[] { schema }, new File[] { bindings }, null,
				temporaryDirectory);

		compiler.execute();

		final JAXBContext context = JAXBContext.newInstance(compiler
				.getContextPath(), compiler.getClassLoader());

		final JAXBElement element = (JAXBElement) context.createUnmarshaller()
				.unmarshal(sample);

		final String elementValueClassName = element.getValue().getClass()
				.getName();
		logger.debug("We have just loaded an instance of ["
				+ elementValueClassName + "]");
		Assert
				.assertEquals(
						"org.jvnet.hyperjaxb3.ejb.tests.pocustomized.PurchaseOrderType",
						elementValueClassName);
	}
}
