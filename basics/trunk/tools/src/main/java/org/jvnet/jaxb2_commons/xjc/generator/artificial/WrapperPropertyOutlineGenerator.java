package org.jvnet.jaxb2_commons.xjc.generator.artificial;

import org.jvnet.jaxb2_commons.xjc.generator.MPropertyOutlineGenerator;
import org.jvnet.jaxb2_commons.xjc.outline.MClassOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MPropertyAccessor;
import org.jvnet.jaxb2_commons.xjc.outline.MPropertyAccessorFactory;
import org.jvnet.jaxb2_commons.xjc.outline.MPropertyOutline;
import org.jvnet.jaxb2_commons.xjc.outline.concrete.CMPropertyOutline;
import org.jvnet.jaxb2_commons.xml.bind.model.MModelInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfo;

import com.sun.codemodel.JExpression;

public class WrapperPropertyOutlineGenerator implements
		MPropertyOutlineGenerator {

	@Override
	public MPropertyOutline generate(MClassOutline classOutline,
			MModelInfo modelInfo, MPropertyInfo propertyInfo) {
		return new CMPropertyOutline(classOutline, propertyInfo,
				new MPropertyAccessorFactory() {

					@Override
					public MPropertyAccessor createPropertyAccessor(
							JExpression target) {
						// TODO
						throw new UnsupportedOperationException();
					}
				});
	}

}
