package org.jvnet.hyperjaxb3.xjc.generator.bean.field;

import javax.xml.bind.JAXBElement;

import org.jvnet.hyperjaxb3.xml.bind.JAXBElementUtils;

import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JFieldRef;
import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.model.CNonElement;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CReferencePropertyInfo;
import com.sun.tools.xjc.outline.Aspect;

public class JAXBElementValueField extends AbstractWrappingField {

	@SuppressWarnings("unused")
	private final CPropertyInfo nameProperty;

	@SuppressWarnings("unused")
	private final JFieldRef nameField;

	private final CNonElement type;

	public JAXBElementValueField(ClassOutlineImpl context, CPropertyInfo prop,
			CReferencePropertyInfo core, CPropertyInfo nameProperty,
			CNonElement type) {
		super(context, prop, core);
		this.nameProperty = nameProperty;
		this.nameField = JExpr.refthis(nameProperty.getName(false));
		this.type = type;
	}

	@Override
	public JExpression unwrapCondifiton(JExpression source) {
		return source._instanceof(codeModel.ref(JAXBElement.class));
	}

	@Override
	public JExpression wrapCondifiton(JExpression source) {
		return source.ne(JExpr._null());
	}

	@Override
	protected JExpression unwrap(JExpression source) {
		return codeModel.ref(JAXBElementUtils.class).staticInvoke("getValue")
				.arg(
						JExpr.cast(codeModel.ref(JAXBElement.class).narrow(
								type.toType(outline.parent(), Aspect.EXPOSED)
										.boxify()), source));
	}

	@Override
	protected JExpression wrap(JExpression source) {
		final JExpression core = getCore();
		return codeModel.ref(JAXBElementUtils.class).staticInvoke("wrap").arg(
				core).arg(source);
	}

}
