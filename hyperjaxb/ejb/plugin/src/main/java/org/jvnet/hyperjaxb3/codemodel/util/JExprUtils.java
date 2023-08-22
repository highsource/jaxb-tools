package org.jvnet.hyperjaxb3.codemodel.util;

import javax.xml.namespace.QName;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JInvocation;

public class JExprUtils {

	public static JExpression newQName(JCodeModel codeModel, QName elementName) {
		final JInvocation name = JExpr._new(codeModel.ref(QName.class)).arg(

		JExpr.lit(elementName.getNamespaceURI())

		).arg(JExpr.lit(elementName.getLocalPart()));
		return name;

	}

}
