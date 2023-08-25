package org.jvnet.hyperjaxb3.xjc.generator.bean.field;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JOp;
import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.model.CEnumLeafInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.outline.Aspect;

public class SingleEnumValueWrappingField extends AbstractWrappingField {

	private final CEnumLeafInfo enumType;
	private final JClass enumClass;

	public SingleEnumValueWrappingField(ClassOutlineImpl context,
			CPropertyInfo prop, CPropertyInfo core) {
		super(context, prop, core);

		// Single
		assert !core.isCollection();
		// Builtin
		assert core.ref().size() == 1;
		assert core.ref().iterator().next() instanceof CEnumLeafInfo;

		this.enumType = (CEnumLeafInfo) core.ref().iterator().next();
		this.enumClass = this.enumType.toType(context.parent(), Aspect.EXPOSED);
	}

	@Override
	protected JExpression unwrap(JExpression source) {
		return JOp.cond(source.eq(JExpr._null()), JExpr._null(), source
				.invoke("value"));
	}

	@Override
	protected JExpression wrap(JExpression target) {
		return JOp.cond(target.eq(JExpr._null()), JExpr._null(), this.enumClass
				.staticInvoke("fromValue").arg(target));
	}

}
