package org.jvnet.hyperjaxb3.xjc.generator.bean.field;

import org.w3c.dom.Element;

import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CReferencePropertyInfo;

public class ElementField extends SingleWrappingField {

	public ElementField(ClassOutlineImpl context, CPropertyInfo prop,
			CReferencePropertyInfo core) {
		super(context, prop, core);
	}

	@Override
	public JExpression unwrapCondifiton(JExpression source) {
		return source._instanceof(codeModel.ref(Element.class));
	}
	
	@Override
	public JExpression wrapCondifiton(JExpression source) {
		return source.ne(JExpr._null());
	}
	

	@Override
	protected JExpression unwrap(JExpression source) {
		return super.unwrap(JExpr.cast(codeModel.ref(Element.class), source));
	}

}
