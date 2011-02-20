package org.jvnet.jaxb2_commons.plugin.model;

import org.jvnet.jaxb2_commons.plugin.AbstractPlugin;
import org.jvnet.jaxb2_commons.xjc.model.concrete.XJCCMInfoFactory;
import org.jvnet.jaxb2_commons.xml.bind.model.MModel;
import org.xml.sax.ErrorHandler;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.Model;
import com.sun.tools.xjc.outline.Outline;
import com.sun.tools.xjc.reader.Ring;

public class ModelPlugin extends AbstractPlugin {
	
	public ModelPlugin() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getOptionName() {
		return "Xmodel";
	}

	@Override
	public String getUsage() {
		return "TBD";
	}

	private MModel retrieveModel(Model model) {
		try {
			return Ring.get(MModel.class);
		} catch (Throwable t) {
			final MModel mmodel = new XJCCMInfoFactory(model).createModel();
			Ring.add(MModel.class, mmodel);
			return mmodel;
		}
	}

	private MModel mmodel;

	@Override
	public void postProcessModel(Model model, ErrorHandler errorHandler) {
		mmodel = retrieveModel(model);
	}

	@Override
	protected boolean run(Outline outline, Options options) throws Exception {
		Ring.add(MModel.class, mmodel);
//		logger.info("Generating the model...");
		return true;
	}

}
