package org.jvnet.mjiip.v_2_0;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import org.apache.maven.plugin.MojoExecutionException;
import org.jfrog.maven.annomojo.annotations.MojoGoal;
import org.jfrog.maven.annomojo.annotations.MojoPhase;
import org.jvnet.jaxb2.maven2.RawXJC2Mojo;

import com.sun.codemodel.CodeWriter;
import com.sun.codemodel.JCodeModel;
import com.sun.tools.xjc.ModelLoader;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.Model;
import com.sun.tools.xjc.outline.Outline;

/**
 * JAXB 2.x Mojo.
 * 
 * @author Aleksei Valikov (valikov@gmx.net)
 */
@MojoGoal("generate")
@MojoPhase("generate-sources")
public class XJC20Mojo extends RawXJC2Mojo<Options> {

	private final org.jvnet.jaxb2.maven2.OptionsFactory<Options> optionsFactory = new OptionsFactory();

	@Override
	protected org.jvnet.jaxb2.maven2.OptionsFactory<Options> getOptionsFactory() {
		return optionsFactory;
	}

	@Override
	public void doExecute(Options options) throws MojoExecutionException {
		final Model model = loadModel(options);
		final Outline outline = generateCode(model);
		writeCode(outline);

	}

	protected Model loadModel(Options options) throws MojoExecutionException {
		if (getVerbose()) {
			getLog().info("Parsing input schema(s)...");
		}
		final Model model = ModelLoader.load(options, new JCodeModel(),
				new LoggingErrorReceiver("Error while parsing schema(s).",
						getLog(), getVerbose()));

		if (model == null)
			throw new MojoExecutionException(
					"Unable to parse input schema(s). Error messages should have been provided.");
		return model;
	}

	protected Outline generateCode(final Model model)
			throws MojoExecutionException {
		if (getVerbose()) {
			getLog().info("Compiling input schema(s)...");
		}

		final Outline outline = model.generateCode(model.options,
				new LoggingErrorReceiver("Error while generating code.",
						getLog(), getVerbose()));
		if (outline == null) {
			throw new MojoExecutionException(
					"Failed to compile input schema(s)!  Error messages should have been provided.");
		} else {
			return outline;
		}
	}

	protected void writeCode(Outline outline) throws MojoExecutionException {

		if (getWriteCode()) {
			if (getVerbose())
				getLog().info(
						"Writing output to ["
								+ outline.getModel().options.targetDir
										.getAbsolutePath() + "].");

			try {
				final CodeWriter codeWriter = new LoggingCodeWriter(
						outline.getModel().options.createCodeWriter(),
						getLog(), getVerbose());
				outline.getModel().codeModel.build(codeWriter);
			} catch (IOException e) {
				throw new MojoExecutionException("Unable to write files: "
						+ e.getMessage(), e);
			}
		} else {
			getLog().info("Code will not be written.");
		}
	}
	
	@Override
	public Collection<File> getEpisodeFiles() {
		return Collections.emptyList();
	}
	
	@Override
	public boolean getEpisode() {
		return false;
	}

}
