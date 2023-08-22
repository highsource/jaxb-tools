package org.jvnet.hyperjaxb3.xjc.generator.bean.field;

import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JOp;
import com.sun.codemodel.JType;
import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import  com.sun.tools.xjc.model.Aspect;

public class SingleWrappingClassInfoField extends AbstractWrappingField {

	@SuppressWarnings("unused")
	private final CClassInfo classInfo;

	private final JType _class;

	public SingleWrappingClassInfoField(ClassOutlineImpl context,
			CPropertyInfo prop, CPropertyInfo core, CClassInfo classInfo) {
		super(context, prop, core);

		// assert prop instanceof CElementPropertyInfo;
		//		
		// final CElementPropertyInfo elementPropertyInfo =
		// (CElementPropertyInfo) prop;
		//		
		// assert elementPropertyInfo.getTypes().size() == 1;
		//		
		// final CTypeRef typeRef = elementPropertyInfo.getTypes().get(0);

		this.classInfo = classInfo;
		this._class = classInfo.toType(context.parent(), Aspect.EXPOSED);
	}

	@Override
	protected JExpression unwrap(JExpression source) {

		return JExpr.cast(_class, source);
	}

	@Override
	public JExpression unwrapCondifiton(JExpression source) {
		return JOp._instanceof(source, _class);
	}

	@Override
	protected JExpression wrap(JExpression target) {
		return target;
	}

	@Override
	public JExpression wrapCondifiton(JExpression source) {
		return JOp.ne(source, JExpr._null());
	}

}
