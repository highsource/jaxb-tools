package org.jvnet.hyperjaxb3.xjc.generator.bean.field;

import java.util.Collection;

import javax.xml.namespace.QName;

import org.jvnet.hyperjaxb3.codemodel.util.JExprUtils;
import org.jvnet.hyperjaxb3.xml.bind.JAXBContextUtils;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JMod;
import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.model.CElement;
import com.sun.tools.xjc.model.CElementInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CReferencePropertyInfo;

public class SingleMarshallingReferenceField extends AbstractWrappingField {

	private final JExpression contextPath;

	public SingleMarshallingReferenceField(ClassOutlineImpl context,
			CPropertyInfo prop, CPropertyInfo core, String contextPath, boolean _final) {
		super(context, prop, core);
		this.contextPath = context.implClass.field(JMod.PUBLIC | JMod.STATIC
				| (_final ? JMod.FINAL : JMod.NONE), String.class, prop
				.getName(true)
				+ "ContextPath", JExpr.lit(contextPath));
	}

	@Override
	protected JExpression wrap(JExpression target) {

		final CReferencePropertyInfo referencePropertyInfo = (CReferencePropertyInfo) core;

		final Collection<CElement> elements = referencePropertyInfo
				.getElements();

		final CElement element = elements.iterator().next();

		final CElementInfo elementInfo = (CElementInfo) element.getType();

//		final CNonElement type = elementInfo.getProperty().ref().iterator()
//				.next();

		final JClass scope = getScope(elementInfo.getScope());

		final QName name = elementInfo.getElementName();

		return codeModel.ref(JAXBContextUtils.class).staticInvoke(
				"unmarshalJAXBElement").arg(contextPath).arg(
				JExprUtils.newQName(codeModel, name)).arg(scope.dotclass())
				.arg(target);
	}

	@Override
	protected JExpression unwrap(JExpression source) {

		return codeModel.ref(JAXBContextUtils.class).staticInvoke(
				"marshalJAXBElement").arg(contextPath).arg(source);
	}

}
