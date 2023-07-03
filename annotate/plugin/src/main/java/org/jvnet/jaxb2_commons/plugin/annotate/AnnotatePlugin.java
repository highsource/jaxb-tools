/**
 * Copyright © 2005-2015, Alexey Valikov
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those
 * of the authors and should not be interpreted as representing official policies,
 * either expressed or implied, of the FreeBSD Project.
 */
package org.jvnet.jaxb2_commons.plugin.annotate;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;

import org.apache.commons.lang3.StringUtils;
import org.jvnet.annox.Constants;
import org.jvnet.annox.model.XAnnotation;
import org.jvnet.annox.parser.XAnnotationParser;
import org.jvnet.jaxb2_commons.plugin.AbstractParameterizablePlugin;
import org.jvnet.jaxb2_commons.plugin.AnnotationTarget;
import org.jvnet.jaxb2_commons.plugin.removeannotation.RemoveAnnotationPlugin;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.sun.codemodel.JAnnotatable;
import com.sun.codemodel.JCodeModel;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CCustomizations;
import com.sun.tools.xjc.model.CElementInfo;
import com.sun.tools.xjc.model.CPluginCustomization;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.ElementOutline;
import com.sun.tools.xjc.outline.EnumConstantOutline;
import com.sun.tools.xjc.outline.EnumOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.Outline;

public class AnnotatePlugin extends AbstractParameterizablePlugin {

	public static final QName ANNOTATE_PROPERTY_QNAME = new QName(
			Constants.NAMESPACE_URI, "annotateProperty");
	public static final QName ANNOTATE_PROPERTY_GETTER_QNAME = new QName(
			Constants.NAMESPACE_URI, "annotatePropertyGetter");
	public static final QName ANNOTATE_PROPERTY_SETTER_QNAME = new QName(
			Constants.NAMESPACE_URI, "annotatePropertySetter");
	public static final QName ANNOTATE_PROPERTY_FIELD_QNAME = new QName(
			Constants.NAMESPACE_URI, "annotatePropertyField");
	public static final QName ANNOTATE_PROPERTY_SETTER_PARAMETER_QNAME = new QName(
			Constants.NAMESPACE_URI, "annotatePropertySetterParameter");
	public static final QName ANNOTATE_PACKAGE_QNAME = new QName(
			Constants.NAMESPACE_URI, "annotatePackage");
	public static final QName ANNOTATE_CLASS_QNAME = new QName(
			Constants.NAMESPACE_URI, "annotateClass");
	public static final QName ANNOTATE_ELEMENT_QNAME = new QName(
			Constants.NAMESPACE_URI, "annotateElement");
	public static final QName ANNOTATE_ENUM_QNAME = new QName(
			Constants.NAMESPACE_URI, "annotateEnum");
	public static final QName ANNOTATE_ENUM_CONSTANT_QNAME = new QName(
			Constants.NAMESPACE_URI, "annotateEnumConstant");
	public static final QName ANNOTATE_ENUM_VALUE_METHOD_QNAME = new QName(
			Constants.NAMESPACE_URI, "annotateEnumValueMethod");
	public static final QName ANNOTATE_ENUM_FROM_VALUE_METHOD_QNAME = new QName(
			Constants.NAMESPACE_URI, "annotateEnumFromValueMethod");
	public static final QName ANNOTATE_QNAME = new QName(
			Constants.NAMESPACE_URI, "annotate");
	
	public static final Set<QName> CUSTOMIZATION_ELEMENT_QNAMES = Collections.unmodifiableSet(
			new HashSet<QName>(Arrays.asList(
					ANNOTATE_QNAME,
					ANNOTATE_PACKAGE_QNAME,
					ANNOTATE_CLASS_QNAME,
					ANNOTATE_ELEMENT_QNAME,
					ANNOTATE_PROPERTY_QNAME,
					ANNOTATE_PROPERTY_FIELD_QNAME,
					ANNOTATE_PROPERTY_GETTER_QNAME,
					ANNOTATE_PROPERTY_SETTER_QNAME,
					ANNOTATE_PROPERTY_SETTER_PARAMETER_QNAME,
					ANNOTATE_ENUM_QNAME,
					ANNOTATE_ENUM_CONSTANT_QNAME,
					ANNOTATE_ENUM_VALUE_METHOD_QNAME,
					ANNOTATE_ENUM_FROM_VALUE_METHOD_QNAME)));
	

	@Override
	public String getOptionName() {
		return "Xannotate";
	}

	@Override
	public String getUsage() {
		return "TBD";
	}

	private String defaultFieldTarget = "getter";

	public String getDefaultFieldTarget() {
		return defaultFieldTarget;
	}

	public void setDefaultFieldTarget(String defaultFieldTarget) {
		if ("getter".equals(defaultFieldTarget)
				|| "setter".equals(defaultFieldTarget)
				|| "setter-parameter".equals(defaultFieldTarget)
				|| "field".equals(defaultFieldTarget)) {
			this.defaultFieldTarget = defaultFieldTarget;
		} else {
			throw new IllegalArgumentException("Invalid default field target.");
		}
	}

	private XAnnotationParser annotationParser = XAnnotationParser.INSTANCE;

	public XAnnotationParser getAnnotationParser() {
		return annotationParser;
	}

	public void setAnnotationParser(XAnnotationParser annotationParser) {
		this.annotationParser = annotationParser;
	}

	private Annotator annotator = new Annotator();

	public Annotator getAnnotator() {
		return annotator;
	}

	public void setAnnotator(Annotator annotator) {
		this.annotator = annotator;
	}

	@Override
	public boolean run(Outline outline, Options options,
			ErrorHandler errorHandler) {

		for (final CElementInfo elementInfo : outline.getModel()
				.getAllElements()) {
			final ElementOutline elementOutline = outline
					.getElement(elementInfo);
			if (elementOutline != null) {
				processElementOutline(elementOutline, options, errorHandler);
			}
		}

		for (final ClassOutline classOutline : outline.getClasses()) {
			processClassOutline(classOutline, options, errorHandler);
		}
		for (final EnumOutline enumOutline : outline.getEnums()) {
			processEnumOutline(enumOutline, options, errorHandler);
		}
		return true;
	}

	protected void processElementOutline(ElementOutline elementOutline,
			Options options, ErrorHandler errorHandler) {

		final CCustomizations customizations = CustomizationUtils
				.getCustomizations(elementOutline);

		annotateElementOutline(elementOutline.implClass.owner(),
				elementOutline, customizations, errorHandler);
	}

	protected void processEnumOutline(EnumOutline enumOutline, Options options,
			ErrorHandler errorHandler) {
		final CCustomizations customizations = CustomizationUtils
				.getCustomizations(enumOutline);
		annotateEnumOutline(enumOutline.clazz.owner(), enumOutline,
				customizations, errorHandler);

		for (final EnumConstantOutline enumConstantOutline : enumOutline.constants) {
			processEnumConstantOutline(enumOutline, enumConstantOutline,
					options, errorHandler);
		}

	}

	protected void processClassOutline(ClassOutline classOutline,
			Options options, ErrorHandler errorHandler) {

		final CCustomizations customizations = CustomizationUtils
				.getCustomizations(classOutline);

		annotateClassOutline(classOutline.ref.owner(), classOutline,
				customizations, errorHandler);

		for (final FieldOutline fieldOutline : classOutline.getDeclaredFields()) {
			processFieldOutline(classOutline, fieldOutline, options,
					errorHandler);
		}

	}

	protected void processFieldOutline(ClassOutline classOutline,
			FieldOutline fieldOutline, Options options,
			ErrorHandler errorHandler) {
		final CCustomizations customizations = CustomizationUtils
				.getCustomizations(fieldOutline);
		annotateFieldOutline(fieldOutline.parent().ref.owner(), fieldOutline,
				customizations, errorHandler);
	}

	protected void processEnumConstantOutline(EnumOutline enumOutline,
			EnumConstantOutline enumConstantOutline, Options options,
			ErrorHandler errorHandler) {

		final CCustomizations customizations = CustomizationUtils
				.getCustomizations(enumConstantOutline);

		annotateEnumConstantOutline(enumOutline.parent().getCodeModel(),
				enumOutline.parent(), enumConstantOutline, customizations,
				errorHandler);
	}

	protected void annotateElementOutline(final JCodeModel codeModel,
			final ElementOutline elementOutline,
			final CCustomizations customizations,
			final ErrorHandler errorHandler) {
		for (final CPluginCustomization customization : customizations) {
			final Element element = customization.element;
			final QName name = new QName(element.getNamespaceURI(),
					element.getLocalName());
			if (isCustomizationElementName(name)) {
				customization.markAsAcknowledged();
				final AnnotationTarget annotationTarget = AnnotationTarget
						.getAnnotationTarget(element, AnnotationTarget.ELEMENT);
				try {
					final JAnnotatable annotatable = annotationTarget
							.getAnnotatable(elementOutline.parent(),
									elementOutline);
					annotate(codeModel, errorHandler, customization, element,
							annotatable);
				} catch (IllegalArgumentException iaex) {
					logger.error("Error applying the annotation.", iaex);
				}
			}
		}
	}

	protected void annotateEnumOutline(final JCodeModel codeModel,
			final EnumOutline enumOutline,
			final CCustomizations customizations,
			final ErrorHandler errorHandler) {
		for (final CPluginCustomization customization : customizations) {
			final Element element = customization.element;
			final QName name = new QName(element.getNamespaceURI(),
					element.getLocalName());
			if (isCustomizationElementName(name)) {
				customization.markAsAcknowledged();
				final AnnotationTarget annotationTarget = AnnotationTarget
						.getAnnotationTarget(element, AnnotationTarget.ENUM);
				try {
					final JAnnotatable annotatable = annotationTarget
							.getAnnotatable(enumOutline.parent(), enumOutline);
					annotate(codeModel, errorHandler, customization, element,
							annotatable);
				} catch (IllegalArgumentException iaex) {
					logger.error("Error applying the annotation.", iaex);
				}
			}
		}
	}

	protected void annotateEnumConstantOutline(final JCodeModel codeModel,
			final Outline outline,
			final EnumConstantOutline enumConstantOutline,
			final CCustomizations customizations,
			final ErrorHandler errorHandler) {
		for (final CPluginCustomization customization : customizations) {
			final Element element = customization.element;
			final QName name = new QName(element.getNamespaceURI(),
					element.getLocalName());
			if (isCustomizationElementName(name)) {
				customization.markAsAcknowledged();
				final AnnotationTarget annotationTarget = AnnotationTarget
						.getAnnotationTarget(element,
								AnnotationTarget.ENUM_CONSTANT);
				try {
					final JAnnotatable annotatable = annotationTarget
							.getAnnotatable(outline, enumConstantOutline);
					annotate(codeModel, errorHandler, customization, element,
							annotatable);
				} catch (IllegalArgumentException iaex) {
					logger.error("Error applying the annotation.", iaex);
				}

			}
		}
	}

	protected void annotateClassOutline(final JCodeModel codeModel,
			final ClassOutline classOutline,
			final CCustomizations customizations, ErrorHandler errorHandler) {
		for (final CPluginCustomization customization : customizations) {
			final Element element = customization.element;
			final QName name = new QName(element.getNamespaceURI(),
					element.getLocalName());
			if (isCustomizationElementName(name)) {
				customization.markAsAcknowledged();
				final AnnotationTarget annotationTarget = AnnotationTarget
						.getAnnotationTarget(element, AnnotationTarget.CLASS);
				try {
					final JAnnotatable annotatable = annotationTarget
							.getAnnotatable(classOutline.parent(), classOutline);
					annotate(codeModel, errorHandler, customization, element,
							annotatable);
				} catch (IllegalArgumentException iaex) {
					logger.error("Error applying the annotation.", iaex);
				}
			}
		}
	}

	protected void annotateFieldOutline(final JCodeModel codeModel,
			final FieldOutline fieldOutline,
			final CCustomizations customizations, ErrorHandler errorHandler) {
		for (final CPluginCustomization customization : customizations) {
			final Element element = customization.element;
			final QName name = new QName(element.getNamespaceURI(),
					element.getLocalName());
			if (isCustomizationElementName(name)) {
				customization.markAsAcknowledged();

				final AnnotationTarget annotationTarget = AnnotationTarget
						.getAnnotationTarget(element, AnnotationTarget
								.getAnnotationTarget(getDefaultFieldTarget()));

				try {
					final JAnnotatable annotatable = annotationTarget
							.getAnnotatable(fieldOutline.parent().parent(),
									fieldOutline);
					annotate(codeModel, errorHandler, customization, element,
							annotatable);
				} catch (IllegalArgumentException iaex) {
					logger.error("Error applying the annotation.", iaex);
				}

			}
		}
	}

	private void annotate(final JCodeModel codeModel,
			ErrorHandler errorHandler,
			final CPluginCustomization customization, final Element element,
			final JAnnotatable annotatable) {
		final NodeList elements = element.getChildNodes();
		for (int index = 0; index < elements.getLength(); index++) {
			final Node node = elements.item(index);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				final Element child = (Element) node;

				try {
					final XAnnotation<?> annotation = getAnnotationParser()
							.parse(child);
					getAnnotator().annotate(codeModel, annotatable, annotation);
				} catch (Exception ex) {
					try {
						errorHandler.error(new SAXParseException(
								"Error parsing annotation.",
								customization.locator, ex));
					} catch (SAXException ignored) {
						// Nothing to do
					}
				}
			} else if (node.getNodeType() == Node.TEXT_NODE) {
				final String nodeValue = node.getNodeValue();
				if (nodeValue != null && StringUtils.isNotBlank(nodeValue)) {
					try {
						final XAnnotation<?> annotation = getAnnotationParser()
								.parse(nodeValue);
						getAnnotator().annotate(codeModel, annotatable,
								annotation);

					} catch (Exception ex) {
						try {
							errorHandler.error(new SAXParseException(
									"Error parsing annotation.",
									customization.locator, ex));
						} catch (SAXException ignored) {
							// Nothing to do
						}
					}
				}
			}
		}
	}
	
	private boolean isCustomizationElementName(final QName name) {
		return name != null &&
				Constants.NAMESPACE_URI.equals(name.getNamespaceURI()) &&
				!RemoveAnnotationPlugin.CUSTOMIZATION_ELEMENT_QNAMES.contains(name);
	}

	@Override
	public Collection<QName> getCustomizationElementNames() {
		return CUSTOMIZATION_ELEMENT_QNAMES;
	}

}
