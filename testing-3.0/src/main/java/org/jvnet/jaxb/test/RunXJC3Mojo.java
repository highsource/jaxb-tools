package org.jvnet.jaxb.test;

import java.io.File;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

import org.apache.maven.plugin.Mojo;
import org.apache.maven.project.MavenProject;
import org.jvnet.jaxb.AbstractXJC3Mojo;
import org.jvnet.mjiip.v_3_0.XJC30Mojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract test for plugins.
 * 
 * @author Aleksei Valikov
 */

public class RunXJC3Mojo extends TestCase {
	/**
	 * Logger.
	 */
	protected Logger log = LoggerFactory.getLogger(RunXJC3Mojo.class);

	public void testExecute() throws Exception {
		final Mojo mojo = initMojo();
		mojo.execute();
	}

	public void check() throws Exception {
	}

	protected File getBaseDir() {
		try {
			return (new File(getClass().getProtectionDomain().getCodeSource()
					.getLocation().toURI())).getParentFile().getParentFile()
					.getAbsoluteFile();
		} catch (Exception ex) {
			throw new AssertionError(ex);
		}
	}

	public File getSchemaDirectory() {
		return new File(getBaseDir(), "src/main/resources");
	}

	protected File getGeneratedDirectory() {
		return new File(getBaseDir(), "target/generated-sources/xjc");
	}

	public List<String> getArgs() {
		return Collections.emptyList();
	}

	public String getGeneratePackage() {
		return null;
	}

	public boolean isWriteCode() {
		return true;
	}

	public AbstractXJC3Mojo initMojo() {
		final AbstractXJC3Mojo mojo = createMojo();
		configureMojo(mojo);
		return mojo;
	}

	protected AbstractXJC3Mojo createMojo() {
		return new XJC30Mojo();
	}

	protected void configureMojo(final AbstractXJC3Mojo mojo) {
		mojo.setProject(new MavenProject());
		mojo.setSchemaDirectory(getSchemaDirectory());
		mojo.setGenerateDirectory(getGeneratedDirectory());
		mojo.setGeneratePackage(getGeneratePackage());
		mojo.setArgs(getArgs());
		mojo.setVerbose(true);
		mojo.setDebug(true);
		mojo.setWriteCode(isWriteCode());
	}
}