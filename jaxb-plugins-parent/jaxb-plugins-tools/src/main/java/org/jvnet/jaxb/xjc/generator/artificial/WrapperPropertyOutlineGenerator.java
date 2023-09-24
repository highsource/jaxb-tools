package org.jvnet.jaxb.xjc.generator.artificial;

import org.jvnet.jaxb.xjc.generator.MPropertyOutlineGenerator;
import org.jvnet.jaxb.xjc.outline.MClassOutline;
import org.jvnet.jaxb.xjc.outline.MPropertyAccessor;
import org.jvnet.jaxb.xjc.outline.MPropertyAccessorFactory;
import org.jvnet.jaxb.xjc.outline.MPropertyOutline;
import org.jvnet.jaxb.xjc.outline.concrete.CMPropertyOutline;
import org.jvnet.jaxb.xml.bind.model.MModelInfo;
import org.jvnet.jaxb.xml.bind.model.MPropertyInfo;

import com.sun.codemodel.JExpression;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;

public class WrapperPropertyOutlineGenerator implements
		MPropertyOutlineGenerator {

	public MPropertyOutline generate(MClassOutline classOutline,
			MModelInfo<NType, NClass> modelInfo,
			MPropertyInfo<NType, NClass> propertyInfo) {
		return new CMPropertyOutline(classOutline, propertyInfo,
				new MPropertyAccessorFactory() {

					public MPropertyAccessor createPropertyAccessor(
							JExpression target) {
						// TODO
						throw new UnsupportedOperationException();
					}
				});
	}

}
