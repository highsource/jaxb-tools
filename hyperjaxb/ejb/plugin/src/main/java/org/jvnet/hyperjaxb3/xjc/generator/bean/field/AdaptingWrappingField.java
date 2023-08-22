package org.jvnet.hyperjaxb3.xjc.generator.bean.field;

import com.sun.codemodel.JClass;
import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.model.CAdapter;
import com.sun.tools.xjc.model.CPropertyInfo;

public abstract class AdaptingWrappingField extends AbstractWrappingField {

	protected final JClass xmlAdapterClass;

	public AdaptingWrappingField(ClassOutlineImpl context, CPropertyInfo prop,
			CPropertyInfo core) {
		this(context, prop, core, prop.getAdapter());
	}

	public AdaptingWrappingField(ClassOutlineImpl context, CPropertyInfo prop,
			CPropertyInfo core, CAdapter adapter) {
		super(context, prop, core);

		this.xmlAdapterClass = adapter == null ? null : adapter
				.getAdapterClass(context.parent());

	}

}
