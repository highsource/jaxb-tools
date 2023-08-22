package org.jvnet.hyperjaxb3.xjc.generator.bean.field;

import org.jvnet.hyperjaxb3.xjc.adapters.XmlAdapterXjcUtils;

import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.model.CElementInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CReferencePropertyInfo;

public class SingleWrappingReferenceElementInfoField extends
		SingleWrappingReferenceField {

	private final CElementInfo elementInfo;

	@Override
	protected CElementInfo getElementInfo() {
		return elementInfo;
	}

	public SingleWrappingReferenceElementInfoField(ClassOutlineImpl context,
			CPropertyInfo property, CReferencePropertyInfo core, CElementInfo elementInfo) {
		super(context, property, core);
		this.elementInfo = elementInfo;
	}

	@Override
	public JExpression unwrapCondifiton(JExpression source) {
		// if (xmlAdapterClass == null) {
		return XmlAdapterXjcUtils.isJAXBElement(codeModel, getDeclaredType(),
				getName(), getScope(), source);
	}

	@Override
	public JExpression wrapCondifiton(JExpression source) {
		return source.ne(JExpr._null());
	}

}
