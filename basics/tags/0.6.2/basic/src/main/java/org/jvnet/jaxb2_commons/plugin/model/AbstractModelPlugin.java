package org.jvnet.jaxb2_commons.plugin.model;

import org.jvnet.jaxb2_commons.plugin.AbstractParameterizablePlugin;
import org.jvnet.jaxb2_commons.xjc.generator.MModelOutlineGenerator;
import org.jvnet.jaxb2_commons.xjc.generator.concrete.ModelOutlineGeneratorFactory;
import org.jvnet.jaxb2_commons.xjc.model.concrete.XJCCMInfoFactory;
import org.jvnet.jaxb2_commons.xjc.outline.MModelOutline;
import org.jvnet.jaxb2_commons.xml.bind.model.MModelInfo;
import org.xml.sax.ErrorHandler;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.Model;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.outline.Outline;
import com.sun.tools.xjc.reader.Ring;

public abstract class AbstractModelPlugin extends AbstractParameterizablePlugin {

	protected MModelInfo<NType, NClass> getModel(Model model) {
		try {
			@SuppressWarnings("unchecked")
			final MModelInfo<NType, NClass> modelInfo = (MModelInfo<NType, NClass>) Ring
					.get(MModelInfo.class);
			return modelInfo;
		} catch (Throwable t) {
			final MModelInfo<NType, NClass> mmodel = new XJCCMInfoFactory(model)
					.createModel();
			Ring.add(MModelInfo.class, mmodel);
			return mmodel;
		}
	}

	protected MModelOutline getModelOutline(
			MModelInfo<NType, NClass> modelInfo, Outline outline,
			Options options) {
		try {
			final MModelOutline modelOutline = (MModelOutline) Ring
					.get(MModelOutline.class);
			return modelOutline;
		} catch (Throwable t) {
			if (modelInfo.getOrigin() instanceof ModelOutlineGeneratorFactory) {
				MModelOutlineGenerator generator = ((ModelOutlineGeneratorFactory) modelInfo
						.getOrigin()).createGenerator(outline);
				MModelOutline modelOutline = generator.generate(modelInfo);
				Ring.add(MModelOutline.class, modelOutline);
				return modelOutline;
			} else {
				throw new AssertionError("Model is expected to be generateable");
			}
		}
	}

	private MModelInfo<NType, NClass> modelInfo;

	@Override
	public void postProcessModel(Model model, ErrorHandler errorHandler) {
		this.modelInfo = getModel(model);
		postProcessModel(model, modelInfo, errorHandler);
	}

	protected void postProcessModel(Model model,
			MModelInfo<NType, NClass> modelInfo, ErrorHandler errorHandler) {
		// Template method to be overridden by classes
	}

	@Override
	protected boolean run(Outline outline, Options options) throws Exception {
		if (modelInfo.getOrigin() instanceof ModelOutlineGeneratorFactory) {
			MModelOutlineGenerator generator = ((ModelOutlineGeneratorFactory) modelInfo
					.getOrigin()).createGenerator(outline);
			MModelOutline modelOutline = generator.generate(modelInfo);
			Ring.add(MModelOutline.class, modelOutline);
		} else {
			throw new AssertionError("Model is expected to be generateable");
		}
		return true;
	}

}
