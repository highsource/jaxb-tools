package org.jvnet.jaxb.test;

import java.io.File;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

import org.apache.maven.plugin.Mojo;
import org.apache.maven.project.MavenProject;
import org.jvnet.jaxb.AbstractXJC2Mojo;
import org.jvnet.mjiip.v_2_3.XJC23Mojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract test for plugins.
 * 
 * @author Aleksei Valikov
 */

public class RunXJC2Mojo extends TestCase {
	/**
	 * Logger.
	 */
	protected Logger log = LoggerFactory.getLogger(RunXJC2Mojo.class);

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

	public AbstractXJC2Mojo initMojo() {
		final AbstractXJC2Mojo mojo = createMojo();
		configureMojo(mojo);
		return mojo;
	}

	protected AbstractXJC2Mojo createMojo() {
		return new XJC23Mojo();
	}

	protected void configureMojo(final AbstractXJC2Mojo mojo) {
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