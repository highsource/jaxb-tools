package org.jvnet.jaxb2_commons.plugin.customizations;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jvnet.jaxb2_commons.plugin.AbstractParameterizablePlugin;
import org.jvnet.jaxb2_commons.util.ClassUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CEnumConstant;
import com.sun.tools.xjc.model.CEnumLeafInfo;
import com.sun.tools.xjc.model.CPluginCustomization;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.Model;

public class CustomizationsPlugin extends AbstractParameterizablePlugin {

	private final DocumentBuilderFactory documentBuilderFactory;
	{
		documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setNamespaceAware(true);
	}

	private File directory;

	public File getDirectory() {
		return directory;
	}

	public void setDirectory(File customizationsDirectory) {
		this.directory = customizationsDirectory;
	}

	private boolean verbose;

	public boolean isVerbose() {
		return verbose;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	@Override
	public String getOptionName() {
		return "Xcustomizations";
	}

	@Override
	public String getUsage() {
		return "  -Xcustomizations   :  reads and adds customizations from files";
	}

	@Override
	public void postProcessModel(Model model, ErrorHandler errorHandler) {
		if (getDirectory() == null) {
			logger.warn(
					"Customizations directory is not provided, please use the -Xcustomizations-directory=<directory> command line argument to provide it.");
		} else if (!getDirectory().exists()) {
			logger.warn(MessageFormat.format("Customizations directory [{0}] does not exist.",
					getDirectory().getAbsolutePath()));
		} else if (!getDirectory().isDirectory()) {
			logger.warn(MessageFormat.format("Customizations directory [{0}] is not a directory.",
					getDirectory().getAbsolutePath()));
		} else {
			postProcessModel(model);
		}
	}

	private void postProcessModel(Model model) {
		for (final CClassInfo classInfo : model.beans().values()) {
			postProcessClassInfo(model, classInfo);
		}
		for (final CEnumLeafInfo enumLeafInfo : model.enums().values()) {
			postProcessEnumLeafInfo(model, enumLeafInfo);
		}
	}

	private void postProcessClassInfo(Model model, CClassInfo classInfo) {
		final String packagedClassName = ClassUtils.getPackagedClassName(classInfo);
		final String customizationsFileName = packagedClassName.replace(".", "/") + ".xml";
		
		final List<CPluginCustomization> customizations = readCustomizations(customizationsFileName);
		
		classInfo.getCustomizations().addAll(customizations);
		
		for (CPropertyInfo propertyInfo: classInfo.getProperties())
		{
			postProcessPropertyInfo(model, classInfo, propertyInfo);
		}
	}

	private void postProcessPropertyInfo(Model model, CClassInfo classInfo, CPropertyInfo propertyInfo) {
		final String packagedClassName = ClassUtils.getPackagedClassName(classInfo);
		final String customizationsFileName = packagedClassName.replace(".", "/") + propertyInfo.getName(false) + ".xml";
		final List<CPluginCustomization> customizations = readCustomizations(customizationsFileName);
		propertyInfo.getCustomizations().addAll(customizations);
	}

	private void postProcessEnumLeafInfo(Model model, CEnumLeafInfo enumLeafInfo) {
		final String packagedClassName = ClassUtils.getPackagedClassName(enumLeafInfo);
		final String customizationsFileName = packagedClassName.replace(".", "/") + ".xml";
		
		final List<CPluginCustomization> customizations = readCustomizations(customizationsFileName);
		
		enumLeafInfo.getCustomizations().addAll(customizations);
		for (CEnumConstant enumConstant : enumLeafInfo.getConstants()) {
			postProcessEnumConstant(model, enumLeafInfo, enumConstant);
		}
	}

	private void postProcessEnumConstant(Model model, CEnumLeafInfo enumLeafInfo, CEnumConstant enumConstant) {
		final String packagedClassName = ClassUtils.getPackagedClassName(enumLeafInfo);
		final String customizationsFileName = packagedClassName.replace(".", "/") + enumConstant.getName() + ".xml";
		final List<CPluginCustomization> customizations = readCustomizations(customizationsFileName);
		enumConstant.getCustomizations().addAll(customizations);
	}

	private List<CPluginCustomization> readCustomizations(String fileName) {
		final List<CPluginCustomization> customizations = new LinkedList<CPluginCustomization>();
		DocumentBuilder documentBuilder = null;

		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException pcex) {
			throw new UnsupportedOperationException("Could not created the DOM parser.", pcex);
		}

		final File file = new File(getDirectory(), fileName);
		if (!file.exists()) {
			if (isVerbose()) {
				logger.debug(MessageFormat.format("File [{0}] does not exist.", file.getAbsolutePath()));
			}
		} else if (!file.isFile()) {
			logger.warn(MessageFormat.format("File [{0}] is not a file.", file.getAbsolutePath()));
		} else {
			InputStream is = null;
			try {
				is = new FileInputStream(file);
				final Document document = documentBuilder.parse(is);
				final Element documentElement = document.getDocumentElement();
				logger.debug(MessageFormat.format("Loaded customizations from [{0}].", file.getAbsolutePath()));
				final QName documentElementName = new QName(documentElement.getNamespaceURI(),
						documentElement.getLocalName());
				if (Customizations.CUSTOMIZATIONS_ELEMENT_NAME.equals(documentElementName)) {
					final NodeList childNodes = documentElement.getChildNodes();
					for (int index = 0; index < childNodes.getLength(); index++) {
						final Node childNode = childNodes.item(index);
						if (childNode.getNodeType() == Node.ELEMENT_NODE) {
							final Element childElement = (Element) childNode;
							customizations.add(new CPluginCustomization(childElement, null));
						}
					}
				} else {
					customizations.add(new CPluginCustomization(documentElement, null));
				}

			} catch (IOException ioex) {
				logger.warn(MessageFormat.format("Could not parse [{0}].", file.getAbsolutePath()), ioex);
			} catch (SAXException saxex) {
				logger.warn(MessageFormat.format("Could not parse [{0}].", file.getAbsolutePath()), saxex);
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException ignored) {
					}
				}
			}
		}
		return customizations;
	}
}
