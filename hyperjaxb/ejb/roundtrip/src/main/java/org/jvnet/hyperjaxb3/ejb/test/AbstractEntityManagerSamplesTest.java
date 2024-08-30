package org.jvnet.hyperjaxb3.ejb.test;

import java.io.File;
import java.util.Collection;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang3.SystemUtils;
import org.junit.jupiter.api.Test;
import org.jvnet.jaxb.test.AbstractSamplesTest;
import org.jvnet.jaxb.xml.bind.ContextPathAware;

public abstract class AbstractEntityManagerSamplesTest extends
		AbstractEntityManagerTest implements ContextPathAware {

	private final static IOFileFilter JAVA_1_5_SAMPLES = FileFilterUtils
			.andFileFilter(FileFilterUtils.suffixFileFilter(".xml"),
					FileFilterUtils.notFileFilter(

					FileFilterUtils.suffixFileFilter("1.6.xml")));

	private final static IOFileFilter JAVA_1_6_SAMPLES = FileFilterUtils
			.andFileFilter(FileFilterUtils.suffixFileFilter(".xml"),
					FileFilterUtils.notFileFilter(

					FileFilterUtils.suffixFileFilter("1.5.xml")));

	private final static IOFileFilter SAMPLES = SystemUtils.IS_JAVA_1_5 ? JAVA_1_5_SAMPLES
			: JAVA_1_6_SAMPLES;

	private AbstractSamplesTest samplesTest;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		samplesTest = createSamplesTest();
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	protected AbstractSamplesTest createSamplesTest() {
		return new AbstractSamplesTest() {

			@Override
			protected void checkSample(File sample) throws Exception {
				AbstractEntityManagerSamplesTest.this.checkSample(sample);
			}

			@Override
			protected String getContextPath() {
				return AbstractEntityManagerSamplesTest.this.getContextPath();
			}

			@Override
			protected Class<? extends Object> getTestClass() {
				return AbstractEntityManagerSamplesTest.this.getClass();
			}

			@Override
			@SuppressWarnings("unchecked")
			protected File[] getSampleFiles() {
				return AbstractEntityManagerSamplesTest.this.getSampleFiles();
			}

			@Override
			protected File getSamplesDirectory() {
				return AbstractEntityManagerSamplesTest.this
						.getSamplesDirectory();
			}

			@Override
			protected ClassLoader getContextClassLoader() {
				return AbstractEntityManagerSamplesTest.this
						.getContextClassLoader();
			}

		};
	}

    @Test
	public void testSamples() throws Exception {
        if (samplesTest != null) {
            samplesTest.testSamples();
        }
	}

	protected JAXBContext createContext() throws JAXBException {
		return samplesTest.createContext();
	}

	@Override
	public String getPersistenceUnitName() {
		return getContextPath();
	}

	protected abstract void checkSample(File sample) throws Exception;

	public String getContextPath() {
		return getClass().getPackage().getName();
	}

	protected File getBaseDir() {
		try {
			return (new File(getClass().getProtectionDomain().getCodeSource()
					.getLocation().getFile())).getParentFile().getParentFile()
					.getAbsoluteFile();
		} catch (Exception ex) {
			throw new AssertionError(ex);
		}
	}

	protected File getSamplesDirectory() {
		return new File(getBaseDir(), getSamplesDirectoryName());
	}

	protected File[] getSampleFiles() {
		File samplesDirectory = getSamplesDirectory();
		logger.debug("Sample directory [" + samplesDirectory.getAbsolutePath()
				+ "].");
		if (samplesDirectory == null || !samplesDirectory.isDirectory()) {
			return new File[] {};
		} else {

			final Collection<File> files = FileUtils.listFiles(
					samplesDirectory, SAMPLES, TrueFileFilter.INSTANCE);
			return files.toArray(new File[files.size()]);
		}
	}

	public static final String DEFAULT_SAMPLES_DIRECTORY_NAME = "src/test/samples";

	protected String getSamplesDirectoryName() {
		return DEFAULT_SAMPLES_DIRECTORY_NAME;
	}

	protected ClassLoader getContextClassLoader() {
		return getClass().getClassLoader();
	}
}
