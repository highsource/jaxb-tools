package org.jvnet.hyperjaxb3.xjc.generator.bean.field;

import org.jvnet.hyperjaxb3.xjc.adapters.XmlAdapterXjcUtils;

import com.sun.codemodel.JExpression;
import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.model.CPropertyInfo;

public class SingleWrappingField extends AdaptingWrappingField {

	public SingleWrappingField(ClassOutlineImpl context, CPropertyInfo prop,
			CPropertyInfo core) {
		super(context, prop, core);
	}

	protected JExpression wrap(final JExpression target) {

		if (xmlAdapterClass == null) {

			return XmlAdapterXjcUtils.marshall(codeModel, target);
		} else {

			return XmlAdapterXjcUtils.marshall(codeModel, xmlAdapterClass,
					target);
		}
	}

	@Override
	protected JExpression unwrap(JExpression source) {
		if (xmlAdapterClass == null) {
			return XmlAdapterXjcUtils.unmarshall(codeModel, source);
		} else {
			return XmlAdapterXjcUtils.unmarshall(codeModel, xmlAdapterClass,
					source);
		}
	}
}
