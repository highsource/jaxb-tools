package org.jvnet.hyperjaxb3.xjc.generator.bean.field;

import jakarta.xml.bind.JAXBElement;

import org.jvnet.hyperjaxb3.xml.bind.JAXBElementUtils;

import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JFieldRef;
import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.model.CNonElement;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CReferencePropertyInfo;
import com.sun.tools.xjc.outline.Aspect;

public class JAXBElementNameField extends AbstractWrappingField {

	@SuppressWarnings("unused")
	private final CPropertyInfo valueProperty;

	@SuppressWarnings("unused")
	private final JFieldRef valueField;

	private final CNonElement elementType;

	public JAXBElementNameField(final ClassOutlineImpl context,
			final CPropertyInfo prop, final CReferencePropertyInfo core,
			final CPropertyInfo valueProperty, final CNonElement type) {
		super(context, prop, core);
		this.valueProperty = valueProperty;
		this.valueField = JExpr.refthis(valueProperty.getName(false));
		this.elementType = type;
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
		return codeModel.ref(JAXBElementUtils.class).staticInvoke("getName")
				.arg(
						JExpr.cast(codeModel.ref(JAXBElement.class).narrow(
								elementType.toType(outline.parent(),
										Aspect.EXPOSED).boxify()), source));
	}

	@Override
	protected JExpression wrap(JExpression source) {
		final JExpression core = getCore();
		return codeModel.ref(JAXBElementUtils.class).staticInvoke("wrap").arg(
				core).arg(source).arg(
				elementType.toType(outline.parent(), Aspect.EXPOSED).boxify()
						.dotclass());
	}

}
