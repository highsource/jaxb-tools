package org.jvnet.jaxb.test.plugin.foo;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ErrorHandler;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.Plugin;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CPluginCustomization;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.Outline;

public class FooPlugin extends Plugin {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public String getOptionName() {
		return "Xfoo";
	}

	@Override
	public String getUsage() {
		return "  -Xfoo              :  does nothing";
	}

	@Override
	public boolean run(Outline outline, Options opt, ErrorHandler errorHandler) {
		for (final ClassOutline classOutline : outline.getClasses()) {
			for (final CPluginCustomization pluginCustomization : classOutline.target
					.getCustomizations()) {
				pluginCustomization.markAsAcknowledged();
			}
			final CClassInfo classInfo = classOutline.target;
			logger.debug("Class:" + classInfo.getName());

			for (final FieldOutline fieldOutline : classOutline
					.getDeclaredFields()) {

				final CPropertyInfo propertyInfo = fieldOutline
						.getPropertyInfo();
				logger.debug("Property:" + propertyInfo.getName(true));

				for (final CPluginCustomization pluginCustomization : fieldOutline
						.getPropertyInfo().getCustomizations()) {
					pluginCustomization.markAsAcknowledged();
				}
			}
		}
		return true;
	}

	@Override
	public List<String> getCustomizationURIs() {
		return Collections.emptyList();
	}

	@Override
	public boolean isCustomizationTagName(String nsUri, String localName) {
		return true;
	}
}
