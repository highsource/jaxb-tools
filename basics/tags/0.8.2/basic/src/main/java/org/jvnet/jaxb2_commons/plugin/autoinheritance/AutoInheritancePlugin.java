package org.jvnet.jaxb2_commons.plugin.autoinheritance;

import java.util.LinkedList;
import java.util.List;

import org.jvnet.jaxb2_commons.plugin.AbstractParameterizablePlugin;
import org.xml.sax.ErrorHandler;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JDefinedClass;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CElementInfo;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.ElementOutline;
import com.sun.tools.xjc.outline.Outline;

public class AutoInheritancePlugin extends AbstractParameterizablePlugin {

	private String xmlRootElementsExtend = null;
	private List<String> xmlRootElementsImplement = new LinkedList<String>();

	private String xmlTypesExtend = null;
	private List<String> xmlTypesImplement = new LinkedList<String>();

	private List<String> jaxbElementsImplement = new LinkedList<String>();

	public String getXmlRootElementsExtend() {
		return xmlRootElementsExtend;
	}

	public void setXmlRootElementsExtend(String globalElementsExtend) {
		this.xmlRootElementsExtend = globalElementsExtend;
	}

	public String getXmlRootElementsImplement() {
		return xmlRootElementsImplement.toString();
	}

	public void setXmlRootElementsImplement(String xmlRootElementsImplement) {
		this.xmlRootElementsImplement.add(xmlRootElementsImplement);
	}

	public String getXmlTypesExtend() {
		return xmlTypesExtend;
	}

	public void setXmlTypesExtend(String globalComplexTypesExtend) {
		this.xmlTypesExtend = globalComplexTypesExtend;
	}

	public String getXmlTypesImplement() {
		return xmlTypesImplement.toString();
	}

	public void setXmlTypesImplement(String xmlTypesImplement) {
		this.xmlTypesImplement.add(xmlTypesImplement);
	}

	public String getJaxbElementsImplement() {
		return jaxbElementsImplement.toString();
	}

	public void setJaxbElementsImplement(String jaxbElementsImplement) {
		this.jaxbElementsImplement.add(jaxbElementsImplement);
	}

	@Override
	public String getOptionName() {
		return "XautoInheritance";
	}

	@Override
	public String getUsage() {
		return "TBD";
	}

	@Override
	public boolean run(Outline outline, Options opt, ErrorHandler errorHandler) {
		for (final ClassOutline classOutline : outline.getClasses()) {
			if (classOutline.target.getElementName() != null) {
				processGlobalElement(classOutline);
			} else {
				processGlobalComplexType(classOutline);
			}
		}
		for (final CElementInfo elementInfo : outline.getModel()
				.getAllElements()) {
			final ElementOutline elementOutline = outline
					.getElement(elementInfo);
			if (elementOutline != null) {
				processGlobalJAXBElement(elementOutline);
			}
		}
		return true;
	}

	protected void processGlobalElement(ClassOutline classOutline) {

		generateExtends(classOutline.implClass, xmlRootElementsExtend);
		generateImplements(classOutline.implClass, xmlRootElementsImplement);

	}

	protected void processGlobalJAXBElement(ElementOutline elementOutline) {

		generateImplements(elementOutline.implClass, jaxbElementsImplement);

	}

	protected void processGlobalComplexType(ClassOutline classOutline) {

		generateExtends(classOutline.implClass, xmlTypesExtend);
		generateImplements(classOutline.implClass, xmlTypesImplement);

	}

	private void generateExtends(JDefinedClass theClass, String name) {
		if (name != null) {
			final JClass targetClass = theClass.owner().ref(name);
			if (theClass._extends() == theClass.owner().ref(Object.class)) {
				theClass._extends(targetClass);
			}
		}
	}

	private void generateImplements(JDefinedClass theClass, String name) {
		if (name != null) {
			final JClass targetClass = theClass.owner().ref(name);
			theClass._implements(targetClass);
		}
	}

	private void generateImplements(JDefinedClass theClass, List<String> names) {
		if (names != null && !names.isEmpty()) {
			for (String name : names) {
				generateImplements(theClass, name);
			}
		}
	}

}
