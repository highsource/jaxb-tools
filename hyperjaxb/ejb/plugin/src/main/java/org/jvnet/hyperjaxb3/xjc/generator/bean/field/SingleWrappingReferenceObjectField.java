package org.jvnet.hyperjaxb3.xjc.generator.bean.field;

import org.jvnet.hyperjaxb3.xml.bind.JAXBContextUtils;

import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JOp;
import com.sun.codemodel.JMod;
import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.model.CElement;
import com.sun.tools.xjc.model.CElementInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CReferencePropertyInfo;

public class SingleWrappingReferenceObjectField extends AbstractWrappingField {

	// private String contextPath;
	private final JExpression contextPath;

	public SingleWrappingReferenceObjectField(ClassOutlineImpl context,
			CPropertyInfo prop, CReferencePropertyInfo core,
			String contextPath, boolean _final) {
		super(context, prop, core);
		this.contextPath = context.implClass.field(JMod.PUBLIC | JMod.STATIC
				| (_final ? JMod.FINAL : JMod.NONE), String.class, prop
				.getName(true)
				+ "ContextPath", JExpr.lit(contextPath));
	}

	@Override
	public JExpression unwrapCondifiton(JExpression source) {

		final CReferencePropertyInfo core = (CReferencePropertyInfo) this.core;

		JExpression predicate = null;
		if (core.getElements().isEmpty()) {
			predicate = null;
		} else {
			for (CElement element : core.getElements()) {
				if (element instanceof CElementInfo) {
					CElementInfo elementinfo = (CElementInfo) element;

					final SingleWrappingReferenceElementInfoField field = new SingleWrappingReferenceElementInfoField(
							outline, prop, core, elementinfo);
					final JExpression condition = field
							.unwrapCondifiton(source);
					predicate = (predicate == null) ? condition : JOp.cor(
							predicate, condition);
				} else {
					// TODO Other cases currently not supported.
				}
			}
		}

		final JExpression isElement = codeModel.ref(JAXBContextUtils.class)
				.staticInvoke("isElement").arg(contextPath).arg(source);
		return predicate == null ? isElement : JOp.cand(JOp.not(predicate),
				isElement);
	}

	@Override
	public JExpression wrapCondifiton(JExpression source) {
		return source.ne(JExpr._null());
	}

	@Override
	protected JExpression wrap(final JExpression target) {

		return codeModel.ref(JAXBContextUtils.class).staticInvoke("unmarshal")
				.arg(contextPath).arg(target);
	}

	@Override
	protected JExpression unwrap(JExpression source) {

		return codeModel.ref(JAXBContextUtils.class).staticInvoke("marshal")
				.arg(contextPath).arg(source);
	}

}
