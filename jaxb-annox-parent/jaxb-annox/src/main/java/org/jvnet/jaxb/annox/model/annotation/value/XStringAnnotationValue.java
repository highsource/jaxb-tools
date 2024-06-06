package org.jvnet.jaxb.annox.model.annotation.value;

import org.jvnet.jaxb.annox.util.StringUtils;

public class XStringAnnotationValue extends XStaticAnnotationValue<String> {

	public XStringAnnotationValue(String value) {
        // fix JAP-9 issue : \ needs to be espaced for javaparser to work
        // but will then be escaped again by codemodel to generate annotation
        super(StringUtils.unescapeJava(value));
	}

	@Override
	public <P> P accept(XAnnotationValueVisitor<P> visitor) {
		return visitor.visit(this);
	}
}
