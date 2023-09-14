package org.jvnet.hyperjaxb3.xjc.generator.bean.field;

import jakarta.xml.bind.annotation.XmlTransient;

import com.sun.codemodel.JAnnotatable;
import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.model.CPropertyInfo;

public class TransientSingleField extends SingleField {

	public TransientSingleField(ClassOutlineImpl context, CPropertyInfo prop) {
		super(context, prop);
	}
	
	@Override
	protected void annotate(JAnnotatable field) {
		field.annotate(XmlTransient.class);
	}

}
