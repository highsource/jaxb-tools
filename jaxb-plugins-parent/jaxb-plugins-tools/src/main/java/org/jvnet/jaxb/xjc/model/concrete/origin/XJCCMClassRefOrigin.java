package org.jvnet.jaxb.xjc.model.concrete.origin;

import org.jvnet.jaxb.lang.Validate;
import org.jvnet.jaxb.xml.bind.model.origin.MClassRefOrigin;

import com.sun.tools.xjc.model.CClassRef;

public class XJCCMClassRefOrigin implements MClassRefOrigin {

	private final CClassRef source;

	public XJCCMClassRefOrigin(CClassRef source) {
		Validate.notNull(source);
		this.source = source;
	}

	public CClassRef getSource() {
		return source;
	}

}
