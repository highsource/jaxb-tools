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
package org.jvnet.jaxb.plugin.removeannotation;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;

import org.jvnet.jaxb.annox.Constants;
import org.jvnet.jaxb.annox.util.StringUtils;
import org.jvnet.jaxb.plugin.AbstractParameterizablePlugin;
import org.jvnet.jaxb.plugin.AnnotationTarget;
import org.jvnet.jaxb.util.CustomizationUtils;
import org.w3c.dom.Element;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.sun.codemodel.JAnnotatable;
import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JClass;
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

public class RemoveAnnotationPlugin extends AbstractParameterizablePlugin {

	public static final QName REMOVE_ANNOTATION_FROM_PROPERTY_QNAME = new QName(
			Constants.NAMESPACE_URI, "removeAnnotationFromProperty");
	public static final QName REMOVE_ANNOTATION_FROM_PROPERTY_GETTER_QNAME = new QName(
			Constants.NAMESPACE_URI, "removeAnnotationFromPropertyGetter");
	public static final QName REMOVE_ANNOTATION_FROM_PROPERTY_SETTER_QNAME = new QName(
			Constants.NAMESPACE_URI, "removeAnnotationFromPropertySetter");
	public static final QName REMOVE_ANNOTATION_FROM_PROPERTY_FIELD_QNAME = new QName(
			Constants.NAMESPACE_URI, "removeAnnotationFromPropertyField");
	public static final QName REMOVE_ANNOTATION_FROM_PROPERTY_SETTER_PARAMETER_QNAME = new QName(
			Constants.NAMESPACE_URI, "removeAnnotationFromPropertySetterParameter");
	public static final QName REMOVE_ANNOTATION_FROM_PACKAGE_QNAME = new QName(
			Constants.NAMESPACE_URI, "removeAnnotationFromPackage");
	public static final QName REMOVE_ANNOTATION_FROM_CLASS_QNAME = new QName(
			Constants.NAMESPACE_URI, "removeAnnotationFromClass");
	public static final QName REMOVE_ANNOTATION_FROM_ELEMENT_QNAME = new QName(
			Constants.NAMESPACE_URI, "removeAnnotationFromElement");
	public static final QName REMOVE_ANNOTATION_FROM_ENUM_QNAME = new QName(
			Constants.NAMESPACE_URI, "removeAnnotationFromEnum");
	public static final QName REMOVE_ANNOTATION_FROM_ENUM_CONSTANT_QNAME = new QName(
			Constants.NAMESPACE_URI, "removeAnnotationFromEnumConstant");
	public static final QName REMOVE_ANNOTATION_FROM_ENUM_VALUE_METHOD_QNAME = new QName(
			Constants.NAMESPACE_URI, "removeAnnotationFromEnumValueMethod");
	public static final QName REMOVE_ANNOTATION_FROM_ENUM_FROM_VALUE_METHOD_QNAME = new QName(
			Constants.NAMESPACE_URI, "removeAnnotationFromEnumFromValueMethod");
	public static final QName REMOVE_ANNOTATION_QNAME = new QName(
			Constants.NAMESPACE_URI, "removeAnnotation");
    public static final QName LEGACY_REMOVE_ANNOTATION_FROM_PROPERTY_QNAME = new QName(
        Constants.LEGACY_NAMESPACE_URI, "removeAnnotationFromProperty");
    public static final QName LEGACY_REMOVE_ANNOTATION_FROM_PROPERTY_GETTER_QNAME = new QName(
        Constants.LEGACY_NAMESPACE_URI, "removeAnnotationFromPropertyGetter");
    public static final QName LEGACY_REMOVE_ANNOTATION_FROM_PROPERTY_SETTER_QNAME = new QName(
        Constants.LEGACY_NAMESPACE_URI, "removeAnnotationFromPropertySetter");
    public static final QName LEGACY_REMOVE_ANNOTATION_FROM_PROPERTY_FIELD_QNAME = new QName(
        Constants.LEGACY_NAMESPACE_URI, "removeAnnotationFromPropertyField");
    public static final QName LEGACY_REMOVE_ANNOTATION_FROM_PROPERTY_SETTER_PARAMETER_QNAME = new QName(
        Constants.LEGACY_NAMESPACE_URI, "removeAnnotationFromPropertySetterParameter");
    public static final QName LEGACY_REMOVE_ANNOTATION_FROM_PACKAGE_QNAME = new QName(
        Constants.LEGACY_NAMESPACE_URI, "removeAnnotationFromPackage");
    public static final QName LEGACY_REMOVE_ANNOTATION_FROM_CLASS_QNAME = new QName(
        Constants.LEGACY_NAMESPACE_URI, "removeAnnotationFromClass");
    public static final QName LEGACY_REMOVE_ANNOTATION_FROM_ELEMENT_QNAME = new QName(
        Constants.LEGACY_NAMESPACE_URI, "removeAnnotationFromElement");
    public static final QName LEGACY_REMOVE_ANNOTATION_FROM_ENUM_QNAME = new QName(
        Constants.LEGACY_NAMESPACE_URI, "removeAnnotationFromEnum");
    public static final QName LEGACY_REMOVE_ANNOTATION_FROM_ENUM_CONSTANT_QNAME = new QName(
        Constants.LEGACY_NAMESPACE_URI, "removeAnnotationFromEnumConstant");
    public static final QName LEGACY_REMOVE_ANNOTATION_FROM_ENUM_VALUE_METHOD_QNAME = new QName(
        Constants.LEGACY_NAMESPACE_URI, "removeAnnotationFromEnumValueMethod");
    public static final QName LEGACY_REMOVE_ANNOTATION_FROM_ENUM_FROM_VALUE_METHOD_QNAME = new QName(
        Constants.LEGACY_NAMESPACE_URI, "removeAnnotationFromEnumFromValueMethod");
    public static final QName LEGACY_REMOVE_ANNOTATION_QNAME = new QName(
        Constants.LEGACY_NAMESPACE_URI, "removeAnnotation");

	public static final Set<QName> CUSTOMIZATION_ELEMENT_QNAMES = Collections.unmodifiableSet(
			new HashSet<QName>(Arrays.asList(
					REMOVE_ANNOTATION_QNAME,
					REMOVE_ANNOTATION_FROM_PACKAGE_QNAME,
					REMOVE_ANNOTATION_FROM_CLASS_QNAME,
					REMOVE_ANNOTATION_FROM_ELEMENT_QNAME,
					REMOVE_ANNOTATION_FROM_PROPERTY_QNAME,
					REMOVE_ANNOTATION_FROM_PROPERTY_FIELD_QNAME,
					REMOVE_ANNOTATION_FROM_PROPERTY_GETTER_QNAME,
					REMOVE_ANNOTATION_FROM_PROPERTY_SETTER_QNAME,
					REMOVE_ANNOTATION_FROM_PROPERTY_SETTER_PARAMETER_QNAME,
					REMOVE_ANNOTATION_FROM_ENUM_QNAME,
					REMOVE_ANNOTATION_FROM_ENUM_CONSTANT_QNAME,
					REMOVE_ANNOTATION_FROM_ENUM_VALUE_METHOD_QNAME,
					REMOVE_ANNOTATION_FROM_ENUM_FROM_VALUE_METHOD_QNAME)));

    public static final Set<QName> LEGACY_CUSTOMIZATION_ELEMENT_QNAMES = Collections.unmodifiableSet(
        new HashSet<QName>(Arrays.asList(
            LEGACY_REMOVE_ANNOTATION_QNAME,
            LEGACY_REMOVE_ANNOTATION_FROM_PACKAGE_QNAME,
            LEGACY_REMOVE_ANNOTATION_FROM_CLASS_QNAME,
            LEGACY_REMOVE_ANNOTATION_FROM_ELEMENT_QNAME,
            LEGACY_REMOVE_ANNOTATION_FROM_PROPERTY_QNAME,
            LEGACY_REMOVE_ANNOTATION_FROM_PROPERTY_FIELD_QNAME,
            LEGACY_REMOVE_ANNOTATION_FROM_PROPERTY_GETTER_QNAME,
            LEGACY_REMOVE_ANNOTATION_FROM_PROPERTY_SETTER_QNAME,
            LEGACY_REMOVE_ANNOTATION_FROM_PROPERTY_SETTER_PARAMETER_QNAME,
            LEGACY_REMOVE_ANNOTATION_FROM_ENUM_QNAME,
            LEGACY_REMOVE_ANNOTATION_FROM_ENUM_CONSTANT_QNAME,
            LEGACY_REMOVE_ANNOTATION_FROM_ENUM_VALUE_METHOD_QNAME,
            LEGACY_REMOVE_ANNOTATION_FROM_ENUM_FROM_VALUE_METHOD_QNAME)));

    public static final Set<QName> ALL_CUSTOMIZATION_ELEMENT_QNAMES = Collections.unmodifiableSet(
        new HashSet<QName>(Arrays.asList(
            REMOVE_ANNOTATION_QNAME,
            REMOVE_ANNOTATION_FROM_PACKAGE_QNAME,
            REMOVE_ANNOTATION_FROM_CLASS_QNAME,
            REMOVE_ANNOTATION_FROM_ELEMENT_QNAME,
            REMOVE_ANNOTATION_FROM_PROPERTY_QNAME,
            REMOVE_ANNOTATION_FROM_PROPERTY_FIELD_QNAME,
            REMOVE_ANNOTATION_FROM_PROPERTY_GETTER_QNAME,
            REMOVE_ANNOTATION_FROM_PROPERTY_SETTER_QNAME,
            REMOVE_ANNOTATION_FROM_PROPERTY_SETTER_PARAMETER_QNAME,
            REMOVE_ANNOTATION_FROM_ENUM_QNAME,
            REMOVE_ANNOTATION_FROM_ENUM_CONSTANT_QNAME,
            REMOVE_ANNOTATION_FROM_ENUM_VALUE_METHOD_QNAME,
            REMOVE_ANNOTATION_FROM_ENUM_FROM_VALUE_METHOD_QNAME,
            LEGACY_REMOVE_ANNOTATION_QNAME,
            LEGACY_REMOVE_ANNOTATION_FROM_PACKAGE_QNAME,
            LEGACY_REMOVE_ANNOTATION_FROM_CLASS_QNAME,
            LEGACY_REMOVE_ANNOTATION_FROM_ELEMENT_QNAME,
            LEGACY_REMOVE_ANNOTATION_FROM_PROPERTY_QNAME,
            LEGACY_REMOVE_ANNOTATION_FROM_PROPERTY_FIELD_QNAME,
            LEGACY_REMOVE_ANNOTATION_FROM_PROPERTY_GETTER_QNAME,
            LEGACY_REMOVE_ANNOTATION_FROM_PROPERTY_SETTER_QNAME,
            LEGACY_REMOVE_ANNOTATION_FROM_PROPERTY_SETTER_PARAMETER_QNAME,
            LEGACY_REMOVE_ANNOTATION_FROM_ENUM_QNAME,
            LEGACY_REMOVE_ANNOTATION_FROM_ENUM_CONSTANT_QNAME,
            LEGACY_REMOVE_ANNOTATION_FROM_ENUM_VALUE_METHOD_QNAME,
            LEGACY_REMOVE_ANNOTATION_FROM_ENUM_FROM_VALUE_METHOD_QNAME)));

	public static final String CLASS_ATTRIBUTE_NAME = "class";

	@Override
	public String getOptionName() {
		return "XremoveAnnotation";
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

		removeAnnotationFromElementOutline(elementOutline.implClass.owner(),
				elementOutline, customizations, errorHandler);
	}

	protected void processEnumOutline(EnumOutline enumOutline, Options options,
			ErrorHandler errorHandler) {
		final CCustomizations customizations = CustomizationUtils
				.getCustomizations(enumOutline);
		removeAnnotationFromEnumOutline(enumOutline.clazz.owner(), enumOutline,
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

		removeAnnotationFromClassOutline(classOutline.ref.owner(), classOutline,
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
		removeAnnotationFromFieldOutline(fieldOutline.parent().ref.owner(), fieldOutline,
				customizations, errorHandler);
	}

	protected void processEnumConstantOutline(EnumOutline enumOutline,
			EnumConstantOutline enumConstantOutline, Options options,
			ErrorHandler errorHandler) {

		final CCustomizations customizations = CustomizationUtils
				.getCustomizations(enumConstantOutline);

		removeAnnotationFromEnumConstantOutline(enumOutline.parent().getCodeModel(),
				enumOutline.parent(), enumConstantOutline, customizations,
				errorHandler);
	}

	protected void removeAnnotationFromElementOutline(final JCodeModel codeModel,
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
					removeAnnotation(codeModel, errorHandler, customization, element,
							annotatable);
				} catch (IllegalArgumentException iaex) {
					logger.error("Error applying the annotation.", iaex);
				}
			}
		}
	}

	protected void removeAnnotationFromEnumOutline(final JCodeModel codeModel,
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
					removeAnnotation(codeModel, errorHandler, customization, element,
							annotatable);
				} catch (IllegalArgumentException iaex) {
					logger.error("Error applying the annotation.", iaex);
				}
			}
		}
	}

	protected void removeAnnotationFromEnumConstantOutline(final JCodeModel codeModel,
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
					removeAnnotation(codeModel, errorHandler, customization, element,
							annotatable);
				} catch (IllegalArgumentException iaex) {
					logger.error("Error applying the annotation.", iaex);
				}

			}
		}
	}

	protected void removeAnnotationFromClassOutline(final JCodeModel codeModel,
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
					removeAnnotation(codeModel, errorHandler, customization, element,
							annotatable);
				} catch (IllegalArgumentException iaex) {
					logger.error("Error applying the annotation.", iaex);
				}
			}
		}
	}

	protected void removeAnnotationFromFieldOutline(final JCodeModel codeModel,
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
					removeAnnotation(codeModel, errorHandler, customization, element,
							annotatable);
				} catch (IllegalArgumentException iaex) {
					logger.error("Error removing the annotation.", iaex);
				}

			}
		}
	}

	private void removeAnnotation(final JCodeModel codeModel,
			ErrorHandler errorHandler,
			final CPluginCustomization customization,
			final Element element,
			final JAnnotatable annotatable) {

		final String aClass = element.getAttribute(CLASS_ATTRIBUTE_NAME);
		if (StringUtils.isBlank(aClass)) {
			try {
			errorHandler.error(new SAXParseException(
					"Could not remove the annotation, annotation class is not specified. "
					+ "Annotation class must be specified using the class attribute of the customization element.",
					customization.locator));
			} catch (SAXException ignored) {
				// Nothing to do
			}
		}
		else {
			JClass annotationClass = codeModel.ref(aClass);

			JAnnotationUse annotationUse = null;
			for (JAnnotationUse annotation : annotatable.annotations()) {
				if (annotationClass.equals(annotation.getAnnotationClass())) {
					annotationUse = annotation;
				}
			}
			if (annotationUse == null) {
				try {
					errorHandler.warning(new SAXParseException(
							MessageFormat.format(
									"Could not remove the annotation, target element is not annotated with annotation class [{0}].",
									annotationClass),
							customization.locator));
					} catch (SAXException ignored) {
						// Nothing to do
					}
			}
			else {
				annotatable.removeAnnotation(annotationUse);
			}
		}
	}

	private boolean isCustomizationElementName(final QName name) {
		return RemoveAnnotationPlugin.ALL_CUSTOMIZATION_ELEMENT_QNAMES.contains(name);
    }

	@Override
	public Collection<QName> getCustomizationElementNames() {
		return ALL_CUSTOMIZATION_ELEMENT_QNAMES;
	}

}
