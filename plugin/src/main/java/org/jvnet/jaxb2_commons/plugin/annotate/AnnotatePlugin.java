package org.jvnet.jaxb2_commons.plugin.annotate;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;

import javax.xml.namespace.QName;

import org.apache.commons.lang3.StringUtils;
import org.jvnet.annox.Constants;
import org.jvnet.annox.model.XAnnotation;
import org.jvnet.annox.parser.XAnnotationParser;
import org.jvnet.jaxb2_commons.plugin.AbstractParameterizablePlugin;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;
import org.jvnet.jaxb2_commons.util.FieldAccessorUtils;
import org.jvnet.jaxb2_commons.util.OutlineUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.sun.codemodel.JAnnotatable;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JVar;
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

	private static final QName ANNOTATE_PROPERTY_QNAME = new QName(
			Constants.NAMESPACE_URI, "annotateProperty");
	private static final QName ANNOTATE_PACKAGE_QNAME = new QName(
			Constants.NAMESPACE_URI, "annotatePackage");
	private static final QName ANNOTATE_CLASS_QNAME = new QName(
			Constants.NAMESPACE_URI, "annotateClass");
	private static final QName ANNOTATE_ELEMENT_QNAME = new QName(
			Constants.NAMESPACE_URI, "annotateElement");
	private static final QName ANNOTATE_ENUM_QNAME = new QName(
			Constants.NAMESPACE_URI, "annotateEnum");
	private static final QName ANNOTATE_ENUM_CONSTANT_QNAME = new QName(
			Constants.NAMESPACE_URI, "annotateEnumConstant");
	private static final QName ANNOTATE_QNAME = new QName(
			Constants.NAMESPACE_URI, "annotate");

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

	private void processFieldOutline(ClassOutline classOutline,
			FieldOutline fieldOutline, Options options,
			ErrorHandler errorHandler) {

		final CCustomizations customizations = CustomizationUtils
				.getCustomizations(fieldOutline);
		annotate(fieldOutline.parent().ref.owner(), fieldOutline,
				customizations, errorHandler);
	}

	private void processEnumConstantOutline(EnumOutline enumOutline,
			EnumConstantOutline enumConstantOutline, Options options,
			ErrorHandler errorHandler) {

		final CCustomizations customizations = CustomizationUtils
				.getCustomizations(enumConstantOutline);

		annotateEnumConstantOutline(enumOutline.parent().getCodeModel(),
				enumConstantOutline, customizations, errorHandler);
	}

	protected void annotateElementOutline(final JCodeModel codeModel,
			final ElementOutline elementOutline,
			final CCustomizations customizations,
			final ErrorHandler errorHandler) {
		for (final CPluginCustomization customization : customizations) {
			final Element element = customization.element;
			final QName name = new QName(element.getNamespaceURI(),
					element.getLocalName());
			if (Constants.NAMESPACE_URI.equals(name.getNamespaceURI())) {
				customization.markAsAcknowledged();
				if (ANNOTATE_QNAME.equals(name)
						|| ANNOTATE_ELEMENT_QNAME.equals(name)) {

					final JAnnotatable annotatable = elementOutline.implClass;

					annotate(codeModel, errorHandler, customization, element,
							annotatable);
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
			if (Constants.NAMESPACE_URI.equals(name.getNamespaceURI())) {
				customization.markAsAcknowledged();
				if (ANNOTATE_QNAME.equals(name)
						|| ANNOTATE_ENUM_QNAME.equals(name)) {

					final JAnnotatable annotatable = enumOutline.clazz;

					annotate(codeModel, errorHandler, customization, element,
							annotatable);
				}
			}
		}
	}

	protected void annotateEnumConstantOutline(final JCodeModel codeModel,
			final EnumConstantOutline enumConstantOutline,
			final CCustomizations customizations,
			final ErrorHandler errorHandler) {
		for (final CPluginCustomization customization : customizations) {
			final Element element = customization.element;
			final QName name = new QName(element.getNamespaceURI(),
					element.getLocalName());
			if (Constants.NAMESPACE_URI.equals(name.getNamespaceURI())) {
				customization.markAsAcknowledged();
				customization.markAsAcknowledged();
				if (ANNOTATE_QNAME.equals(name)
						|| ANNOTATE_ENUM_CONSTANT_QNAME.equals(name)) {

					final JAnnotatable annotatable = enumConstantOutline.constRef;

					annotate(codeModel, errorHandler, customization, element,
							annotatable);

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
			if (Constants.NAMESPACE_URI.equals(name.getNamespaceURI())) {
				customization.markAsAcknowledged();

				customization.markAsAcknowledged();
				if (ANNOTATE_CLASS_QNAME.equals(name)
						|| ANNOTATE_QNAME.equals(name)) {

					final String draftTarget = element.getAttribute("target");

					final String target;

					if (draftTarget == null || "".equals(draftTarget)) {
						target = null;
					} else {
						target = draftTarget;
					}

					final JAnnotatable annotatable;

					if ("package".equals(target)) {
						annotatable = classOutline.ref._package();
					} else {
						annotatable = classOutline.ref;

					}

					annotate(codeModel, errorHandler, customization, element,
							annotatable);
				}
			}
		}
	}

	protected void annotate(final JCodeModel codeModel,
			final FieldOutline fieldOutline,
			final CCustomizations customizations, ErrorHandler errorHandler) {
		for (final CPluginCustomization customization : customizations) {
			final Element element = customization.element;
			final QName name = new QName(element.getNamespaceURI(),
					element.getLocalName());
			if (Constants.NAMESPACE_URI.equals(name.getNamespaceURI())) {
				customization.markAsAcknowledged();
				customization.markAsAcknowledged();
				if (ANNOTATE_QNAME.equals(name)
						|| ANNOTATE_PROPERTY_QNAME.equals(name)) {

					final JAnnotatable annotatable;

					final String draftTarget = element.getAttribute("target");

					final String target;

					if (draftTarget == null || "".equals(draftTarget)) {
						target = getDefaultFieldTarget();
					} else {
						target = draftTarget;
					}

					if ("package".equals(target)) {
						annotatable = fieldOutline.parent().ref._package();
					} else if ("class".equals(target)) {
						annotatable = fieldOutline.parent().ref;
					} else if ("getter".equals(target)) {
						final JMethod _getter = FieldAccessorUtils
								.getter(fieldOutline);
						if (_getter == null) {
							logger.error(MessageFormat
									.format("Could not annotate the getter of the field outline [{0}], getter method could not be found.",

									OutlineUtils.getFieldName(fieldOutline)));

						}
						annotatable = _getter;
					} else if ("setter".equals(target)) {
						final JMethod _setter = FieldAccessorUtils
								.setter(fieldOutline);
						if (_setter == null) {
							logger.error(MessageFormat
									.format("Could not annotate the setter of the field outline [{0}], setter method could not be found.",

									OutlineUtils.getFieldName(fieldOutline)));
						}
						annotatable = _setter;
					} else if ("setter-parameter".equals(target)) {
						final JMethod _setter = FieldAccessorUtils
								.setter(fieldOutline);

						if (_setter == null) {
							logger.error(MessageFormat
									.format("Could not annotate the setter parameter of the field outline [{0}], setter method could not be found.",

									OutlineUtils.getFieldName(fieldOutline)));
							annotatable = null;
						} else {
							final JVar[] params = _setter.listParams();
							if (params.length != 1) {
								logger.error(MessageFormat
										.format("Could not annotate the setter parameter of the field outline [{0}], setter method must have a single parameter(this setter has {1}).",

												OutlineUtils
														.getFieldName(fieldOutline),
												params.length));
								annotatable = null;
							} else {
								annotatable = FieldAccessorUtils.setter(
										fieldOutline).listParams()[0];
							}
						}
					} else if ("field".equals(target)) {
						// Ok
						final JFieldVar _field = FieldAccessorUtils
								.field(fieldOutline);
						if (_field == null) {
							logger.error(MessageFormat
									.format("Could not annotate the field of the field outline [{0}] since it could not be found.",

									OutlineUtils.getFieldName(fieldOutline)));
						}
						annotatable = _field;

					} else {
						logger.error("Invalid annotation target [" + target
								+ "].");
						annotatable = null;
					}

					if (annotatable != null) {

						annotate(codeModel, errorHandler, customization,
								element, annotatable);
					}
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

	@Override
	public Collection<QName> getCustomizationElementNames() {
		return Arrays.asList(ANNOTATE_QNAME, ANNOTATE_PACKAGE_QNAME,
				ANNOTATE_CLASS_QNAME, ANNOTATE_ELEMENT_QNAME,
				ANNOTATE_PROPERTY_QNAME);
	}

}
