package org.jvnet.hyperjaxb3.xjc.generator.bean.field;

import java.util.Collection;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.jvnet.hyperjaxb3.xjc.adapters.XmlAdapterXjcUtils;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JType;
import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.model.CElement;
import com.sun.tools.xjc.model.CElementInfo;
import com.sun.tools.xjc.model.CElementPropertyInfo;
import com.sun.tools.xjc.model.CNonElement;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CReferencePropertyInfo;
import  com.sun.tools.xjc.model.Aspect;

public class SingleWrappingReferenceField extends AdaptingWrappingField {

	public SingleWrappingReferenceField(ClassOutlineImpl context,
			CPropertyInfo prop, CReferencePropertyInfo core) {
		super(context, prop, core);
	}

	@Override
	protected JExpression wrap(JExpression target) {

		final JClass declaredType = (JClass) getDeclaredType();

		final JClass scope = getScope();

		final QName name = getName();

		JExpression value = target;

		if (xmlAdapterClass == null) {
			return XmlAdapterXjcUtils.marshallJAXBElement(codeModel,
					declaredType, name, scope, value);

		} else {
			return XmlAdapterXjcUtils.marshallJAXBElement(codeModel,
					xmlAdapterClass, declaredType, name, scope, value);
		}
	}

	protected QName getName() {
		final QName name = getElementInfo().getElementName();
		return name;
	}

	protected JClass getScope() {
		final JClass scope = getScope(getElementInfo().getScope());
		return scope;
	}

	protected JClass getDeclaredType() {

		final CElementPropertyInfo property = getElementInfo().getProperty();

		if (property.getAdapter() == null) {
			@SuppressWarnings("unused")
			final CNonElement type = property.ref().iterator().next();
			final JClass declaredType = (JClass) getType().toType(
					outline.parent(), Aspect.EXPOSED);
			return declaredType;
		}
		else
		{
			return (JClass) property.getAdapter().customType.toType(outline.parent(), Aspect.EXPOSED);
			
		}
	}

	protected CNonElement getType() {
		final CElementPropertyInfo property = getElementInfo().getProperty();
		final CNonElement type = property.ref().iterator().next();
		return type;
	}

	protected CElementInfo getElementInfo() {
		final CReferencePropertyInfo referencePropertyInfo = (CReferencePropertyInfo) core;

		final Collection<CElement> elements = referencePropertyInfo
				.getElements();

		final CElement element = elements.iterator().next();

		final CElementInfo elementInfo = (CElementInfo) element.getType();
		return elementInfo;
	}

	@Override
	protected JExpression unwrap(JExpression source) {

		final JType declaredType = getDeclaredType();

		final JClass elementClass = codeModel.ref(JAXBElement.class).narrow(
				declaredType.boxify().wildcard());

		// TODO remove if cast is not necessary
		final JExpression value = JExpr.cast(elementClass, source);

		if (xmlAdapterClass == null) {
			return XmlAdapterXjcUtils.unmarshallJAXBElement(codeModel, value);

		} else {
			return XmlAdapterXjcUtils.unmarshallJAXBElement(codeModel,
					xmlAdapterClass, value);
		}

	}

}
