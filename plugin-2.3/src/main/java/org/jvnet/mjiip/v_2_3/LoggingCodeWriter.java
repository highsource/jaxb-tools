/**
 * 
 */
package org.jvnet.mjiip.v_2_3;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import org.apache.maven.plugin.logging.Log;

import com.sun.codemodel.CodeWriter;
import com.sun.codemodel.JPackage;
import com.sun.codemodel.writer.FilterCodeWriter;

public class LoggingCodeWriter extends FilterCodeWriter {

	private final boolean verbose;
	private final Log log;

	public LoggingCodeWriter(CodeWriter output, Log log, boolean verbose) {
		super(output);
		this.log = log;
		this.verbose = verbose;
	}

	public Writer openSource(JPackage pkg, String fileName) throws IOException {
		if (verbose) {
			if (pkg.isUnnamed())
				log.info("XJC writing: " + fileName);
			else
				log.info("XJC writing: "
						+ pkg.name().replace('.', File.separatorChar)
						+ File.separatorChar + fileName);
		}

		return core.openSource(pkg, fileName);
	}

	public OutputStream openBinary(JPackage pkg, String fileName)
			throws IOException {
		if (verbose) {
			if (pkg.isUnnamed())
				log.info("XJC writing: " + fileName);
			else
				log.info("XJC writing: "
						+ pkg.name().replace('.', File.separatorChar)
						+ File.separatorChar + fileName);
		}

		return core.openBinary(pkg, fileName);
	}

	@Override
	public void close() throws IOException {
		core.close();
	}

}