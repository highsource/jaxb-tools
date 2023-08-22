package org.jvnet.hyperjaxb3.xjc.adapters;

import javax.xml.namespace.QName;

import org.jvnet.hyperjaxb3.codemodel.util.JExprUtils;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XmlAdapterUtils;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpression;

public class XmlAdapterXjcUtils {

	public static JExpression unmarshall(JCodeModel codeModel, JExpression value) {
		return value;
	}

	public static JExpression unmarshall(JCodeModel codeModel,
			JClass xmlAdapterClass, JExpression value) {

		return codeModel.ref(XmlAdapterUtils.class).staticInvoke("unmarshall")
				.arg(xmlAdapterClass.dotclass()).arg(value);
	}

	public static JExpression unmarshallJAXBElement(JCodeModel codeModel,
			JClass xmlAdapterClass, JExpression value) {

		return codeModel.ref(XmlAdapterUtils.class).staticInvoke(
				"unmarshallJAXBElement").arg(xmlAdapterClass.dotclass()).arg(
				value);
	}

	public static JExpression unmarshallJAXBElement(JCodeModel codeModel,
			JExpression value) {

		final JExpression argument = value;
		return codeModel.ref(XmlAdapterUtils.class).staticInvoke(
				"unmarshallJAXBElement").arg(argument);
	}

	public static JExpression marshall(JCodeModel codeModel,
			JClass xmlAdapterClass, JExpression value) {

		return codeModel.ref(XmlAdapterUtils.class).staticInvoke("marshall")
				.arg(xmlAdapterClass.dotclass()).arg(value);
	}

	public static JExpression marshall(JCodeModel codeModel, JExpression value) {

		return value;
	}

	public static JExpression marshallJAXBElement(JCodeModel codeModel,
			JClass xmlAdapterClass, JClass declaredType, QName name,
			JClass scope, JExpression value) {

		return codeModel.ref(XmlAdapterUtils.class).staticInvoke(
				"marshallJAXBElement").arg(xmlAdapterClass.dotclass()).arg(
				declaredType.dotclass()).

		arg(JExprUtils.newQName(codeModel, name)).arg(scope.dotclass()).arg(
				value);
	}

	public static JExpression marshallJAXBElement(JCodeModel codeModel,
			JClass declaredType, QName name, JClass scope, JExpression value) {

		return codeModel.ref(XmlAdapterUtils.class).staticInvoke(
				"marshallJAXBElement").arg(declaredType.dotclass()).

		arg(JExprUtils.newQName(codeModel, name)).arg(scope.dotclass()).arg(
				value);
	}
	
	public static JExpression isJAXBElement(JCodeModel codeModel,
			JClass declaredType, QName name, JClass scope, JExpression value) {

		return codeModel.ref(XmlAdapterUtils.class).staticInvoke(
				"isJAXBElement").arg(declaredType.dotclass()).

		arg(JExprUtils.newQName(codeModel, name)).arg(scope.dotclass()).arg(
				value);
	}
	

}
