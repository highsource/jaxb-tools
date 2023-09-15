package org.jvnet.jaxb.maven.test;

import java.io.File;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

import org.apache.maven.plugin.Mojo;
import org.apache.maven.project.MavenProject;
import org.jvnet.jaxb.maven.AbstractXJCMojo;
import org.jvnet.jaxb.maven.XJCMojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.tools.xjc.Options;

/**
 * Abstract test for plugins.
 *
 * @author Aleksei Valikov
 */

public class RunXJCMojo extends TestCase {
	/**
	 * Logger.
	 */
	protected Logger log = LoggerFactory.getLogger(RunXJCMojo.class);

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

	public AbstractXJCMojo<Options> initMojo() {
		final AbstractXJCMojo<Options> mojo = createMojo();
		configureMojo(mojo);
		return mojo;
	}

	protected AbstractXJCMojo<Options> createMojo() {
		return new XJCMojo();
	}

	protected void configureMojo(final AbstractXJCMojo<Options> mojo) {
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
