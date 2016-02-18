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

import java.text.MessageFormat;

import javax.xml.namespace.QName;

import org.apache.commons.lang3.Validate;
import org.jvnet.jaxb2_commons.util.FieldAccessorUtils;
import org.jvnet.jaxb2_commons.util.OutlineUtils;
import org.w3c.dom.Element;

import com.sun.codemodel.JAnnotatable;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;
import com.sun.tools.xjc.model.CEnumLeafInfo;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.ElementOutline;
import com.sun.tools.xjc.outline.EnumConstantOutline;
import com.sun.tools.xjc.outline.EnumOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.Outline;

public enum AnnotationTarget {

	//
	PACKAGE("package", AnnotatePlugin.ANNOTATE_PACKAGE_QNAME) {
		@Override
		public JAnnotatable getAnnotatable(Outline outline,
				FieldOutline fieldOutline) {
			return fieldOutline.parent().ref._package();
		}

		@Override
		public JAnnotatable getAnnotatable(Outline outline,
				ClassOutline classOutline) throws IllegalArgumentException,
				UnsupportedOperationException {
			return classOutline.ref._package();
		}

		@Override
		public JAnnotatable getAnnotatable(Outline outline,
				ElementOutline elementOutline) throws IllegalArgumentException,
				UnsupportedOperationException {
			return elementOutline.implClass._package();
		}

		@Override
		public JAnnotatable getAnnotatable(Outline outline,
				EnumConstantOutline enumConstantOutline)
				throws IllegalArgumentException, UnsupportedOperationException {
			final CEnumLeafInfo enclosingClass = enumConstantOutline.target
					.getEnclosingClass();
			return getAnnotatable(outline, outline.getEnum(enclosingClass));
		}

		@Override
		public JAnnotatable getAnnotatable(Outline outline,
				EnumOutline enumOutline) throws IllegalArgumentException,
				UnsupportedOperationException {
			return enumOutline.clazz._package();
		}
	},
	//
	CLASS("class", AnnotatePlugin.ANNOTATE_CLASS_QNAME) {
		@Override
		public JAnnotatable getAnnotatable(Outline outline,
				FieldOutline fieldOutline) {
			return fieldOutline.parent().ref;
		}

		@Override
		public JAnnotatable getAnnotatable(Outline outline,
				ClassOutline classOutline) throws IllegalArgumentException,
				UnsupportedOperationException {
			return classOutline.ref;
		}

	},
	//
	PROPERTY_GETTER("getter", AnnotatePlugin.ANNOTATE_PROPERTY_GETTER_QNAME) {
		@Override
		public JAnnotatable getAnnotatable(Outline outline,
				FieldOutline fieldOutline) {
			final JMethod _getter = FieldAccessorUtils.getter(fieldOutline);
			if (_getter == null) {
				throw new IllegalArgumentException(
						MessageFormat
								.format("Could not annotate the getter of the field outline [{0}], getter method could not be found.",
										OutlineUtils.getFieldName(fieldOutline)));
			}
			return _getter;
		}
	},
	//
	PROPERTY_SETTER("setter", AnnotatePlugin.ANNOTATE_PROPERTY_SETTER_QNAME) {
		@Override
		public JAnnotatable getAnnotatable(Outline outline,
				FieldOutline fieldOutline) {
			final JMethod _setter = FieldAccessorUtils.setter(fieldOutline);
			if (_setter == null) {
				throw new IllegalArgumentException(
						MessageFormat
								.format("Could not annotate the setter of the field outline [{0}], setter method could not be found.",

								OutlineUtils.getFieldName(fieldOutline)));
			}
			return _setter;
		}
	},
	//
	PROPERTY_FIELD("field", AnnotatePlugin.ANNOTATE_PROPERTY_FIELD_QNAME) {
		@Override
		public JAnnotatable getAnnotatable(Outline outline,
				FieldOutline fieldOutline) {

			// Ok
			final JFieldVar _field = FieldAccessorUtils.field(fieldOutline);
			if (_field == null) {
				throw new IllegalArgumentException(
						MessageFormat
								.format("Could not annotate the field of the field outline [{0}] since it could not be found.",

								OutlineUtils.getFieldName(fieldOutline)));
			}
			return _field;
		}
	},
	//
	PROPERTY_SETTER_PARAMETER("setter-parameter",
			AnnotatePlugin.ANNOTATE_PROPERTY_SETTER_PARAMETER_QNAME) {
		@Override
		public JAnnotatable getAnnotatable(Outline outline,
				FieldOutline fieldOutline) {
			final JMethod _setter = FieldAccessorUtils.setter(fieldOutline);

			if (_setter == null) {
				throw new IllegalArgumentException(
						MessageFormat
								.format("Could not annotate the setter parameter of the field outline [{0}], setter method could not be found.",

								OutlineUtils.getFieldName(fieldOutline)));
			} else {
				final JVar[] params = _setter.listParams();
				if (params.length != 1) {
					throw new IllegalArgumentException(
							MessageFormat
									.format("Could not annotate the setter parameter of the field outline [{0}], setter method must have a single parameter(this setter has {1}).",

									OutlineUtils.getFieldName(fieldOutline),
											params.length));
				} else {
					return params[0];
				}
			}
		}
	},
	//
	ENUM("enum", AnnotatePlugin.ANNOTATE_ENUM_QNAME) {
		@Override
		public JAnnotatable getAnnotatable(Outline outline,
				EnumConstantOutline enumConstantOutline)
				throws IllegalArgumentException, UnsupportedOperationException {
			final CEnumLeafInfo enclosingClass = enumConstantOutline.target
					.getEnclosingClass();
			return getAnnotatable(outline, outline.getEnum(enclosingClass));
		}

		@Override
		public JAnnotatable getAnnotatable(Outline outline,
				EnumOutline enumOutline) throws IllegalArgumentException,
				UnsupportedOperationException {
			return enumOutline.clazz;
		}
	},
	//
	ENUM_CONSTANT("enum-constant", AnnotatePlugin.ANNOTATE_ENUM_CONSTANT_QNAME) {
		@Override
		public JAnnotatable getAnnotatable(Outline outline,
				EnumConstantOutline enumConstantOutline)
				throws IllegalArgumentException, UnsupportedOperationException {
			return enumConstantOutline.constRef;
		}
	},
	//
	ENUM_VALUE_METHOD("enum-value-method", AnnotatePlugin.ANNOTATE_ENUM_VALUE_METHOD_QNAME) {
		@Override
		public JAnnotatable getAnnotatable(Outline outline,
										   EnumOutline enumOutline)
				throws IllegalArgumentException, UnsupportedOperationException {
			final JMethod valueMethod = enumOutline.clazz.getMethod("value", new JType[0]);
			if (null == valueMethod) {
				throw new UnsupportedOperationException(MessageFormat.format(
						"Method value() not found in enum [{0}]",
						enumOutline.clazz.name()));
			}
			return valueMethod;
		}
	},
	//
	ENUM_FROM_VALUE_METHOD("enum-fromValue-method", AnnotatePlugin.ANNOTATE_ENUM_FROM_VALUE_METHOD_QNAME) {
		@Override
		public JAnnotatable getAnnotatable(Outline outline,
										   EnumOutline enumOutline)
				throws IllegalArgumentException, UnsupportedOperationException {
			final JCodeModel codeModel = enumOutline.clazz.owner();
			final JType jTypeString = codeModel._ref(String.class);
			final JMethod fromValueMethod = enumOutline.clazz.getMethod("fromValue", new JType[]{jTypeString});
			if (null == fromValueMethod) {
				throw new UnsupportedOperationException(MessageFormat.format(
						"Method fromValue(String) not found in enum [{0}]",
						enumOutline.clazz.name()));
			}
			return fromValueMethod;
		}
	},
	//
	ELEMENT("element", AnnotatePlugin.ANNOTATE_ELEMENT_QNAME) {
		@Override
		public JAnnotatable getAnnotatable(Outline outline,
				ElementOutline elementOutline) throws IllegalArgumentException,
				UnsupportedOperationException {
			return elementOutline.implClass;
		}
	};

	private final String target;
	private final QName name;

	AnnotationTarget(String target, QName name) {
		this.target = target;
		this.name = name;
	}

	public QName getName() {
		return name;
	}

	public String getTarget() {
		return target;
	}

	public JAnnotatable getAnnotatable(Outline outline, EnumOutline enumOutline)
			throws IllegalArgumentException, UnsupportedOperationException {
		throw new UnsupportedOperationException(MessageFormat.format(
				"Annotation target [{0}] cannot be applied to an enum.",
				getTarget()));
	}

	public JAnnotatable getAnnotatable(Outline outline,
			EnumConstantOutline enumConstantOutline)
			throws IllegalArgumentException, UnsupportedOperationException {
		throw new UnsupportedOperationException(
				MessageFormat
						.format("Annotation target [{0}] cannot be applied to an enum constant.",
								getTarget()));
	}

	public JAnnotatable getAnnotatable(Outline outline,
			ClassOutline classOutline) throws IllegalArgumentException,
			UnsupportedOperationException {
		throw new UnsupportedOperationException(MessageFormat.format(
				"Annotation target [{0}] cannot be applied to a class.",
				getTarget()));
	}

	public JAnnotatable getAnnotatable(Outline outline,
			FieldOutline fieldOutline) throws IllegalArgumentException,
			UnsupportedOperationException {
		throw new UnsupportedOperationException(MessageFormat.format(
				"Annotation target [{0}] cannot be applied to a field.",
				getTarget()));
	}

	public JAnnotatable getAnnotatable(Outline outline,
			ElementOutline elementOutline) throws IllegalArgumentException,
			UnsupportedOperationException {
		throw new UnsupportedOperationException(MessageFormat.format(
				"Annotation target [{0}] cannot be applied to an element.",
				getTarget()));
	}

	public static AnnotationTarget getAnnotationTarget(final Element element,
			AnnotationTarget defaultAnnotationTarget) {
		Validate.notNull(element);
		Validate.notNull(defaultAnnotationTarget);

		final QName name = new QName(element.getNamespaceURI(),
				element.getLocalName());

		if (AnnotatePlugin.ANNOTATE_QNAME.equals(name)
				|| AnnotatePlugin.ANNOTATE_PROPERTY_QNAME.equals(name)) {
			final String target = element.getAttribute("target");
			if (target == null || "".equals(target)) {
				return defaultAnnotationTarget;
			} else {
				return AnnotationTarget.getAnnotationTarget(target);
			}
		} else {
			for (AnnotationTarget possibleAnnotationTarget : AnnotationTarget
					.values()) {
				if (possibleAnnotationTarget.getName().equals(name)) {
					return possibleAnnotationTarget;
				}
			}
			throw new IllegalArgumentException(MessageFormat.format(
					"Unknown annotation element name [{0}].", name));
		}

	}

	public static AnnotationTarget getAnnotationTarget(final String target) {
		for (AnnotationTarget possibleAnnotationTarget : AnnotationTarget
				.values()) {
			if (possibleAnnotationTarget.getTarget().equals(target)) {
				return possibleAnnotationTarget;
			}
		}
		throw new IllegalArgumentException(MessageFormat.format(
				"Unknown annotation target [{0}].", target));
	}
}
