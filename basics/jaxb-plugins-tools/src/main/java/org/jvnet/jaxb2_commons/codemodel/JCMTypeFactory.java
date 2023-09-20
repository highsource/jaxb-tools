package org.jvnet.jaxb2_commons.codemodel;

import java.text.MessageFormat;

import org.apache.commons.lang3.Validate;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JNullType;
import com.sun.codemodel.JPrimitiveType;
import com.sun.codemodel.JType;
import com.sun.codemodel.JTypeVar;

public class JCMTypeFactory {

	public static final JCMTypeFactory INSTANCE = new JCMTypeFactory();

	public <JT extends JType> JCMType<JT> create(JT type) {
		Validate.notNull(type);
		if (type.isArray()) {
			Validate.isInstanceOf(JClass.class, type);
			@SuppressWarnings("unchecked")
			final JCMType<JT> result = (JCMType<JT>) new JCMArrayClass(this,
					(JClass) type);
			return result;
		} else if (type instanceof JTypeVar) {
			@SuppressWarnings("unchecked")
			final JCMType<JT> result = (JCMType<JT>) new JCMTypeVar(this,
					(JTypeVar) type);
			return result;
		} else if (type instanceof JNullType) {
			@SuppressWarnings("unchecked")
			final JCMType<JT> result = (JCMType<JT>) new JCMNullType(this,
					(JNullType) type);
			return result;
		} else if ("com.sun.codemodel.JTypeWildcard".equals(type.getClass()
				.getName())) {
			@SuppressWarnings("unchecked")
			final JCMType<JT> result = (JCMType<JT>) new JCMTypeWildcard(this,
					(JClass) type);
			return result;
		} else if (type instanceof JClass) {
			@SuppressWarnings("unchecked")
			final JCMType<JT> result = (JCMType<JT>) new JCMClass(this,
					(JClass) type);
			return result;

		} else if (type instanceof JPrimitiveType) {
			@SuppressWarnings("unchecked")
			final JCMType<JT> result = (JCMType<JT>) new JCMPrimitiveType(this,
					(JPrimitiveType) type);
			return result;
		} else {
			throw new IllegalArgumentException(MessageFormat.format(
					"Unsupported type [{0}].", type.toString()));
		}
	}
}
