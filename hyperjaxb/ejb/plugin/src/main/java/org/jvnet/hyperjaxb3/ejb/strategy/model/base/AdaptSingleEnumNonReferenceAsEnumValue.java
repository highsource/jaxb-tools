package org.jvnet.hyperjaxb3.ejb.strategy.model.base;

import java.util.Collection;

import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;
import org.jvnet.hyperjaxb3.xjc.generator.bean.field.SingleEnumValueWrappingField;

import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.model.CEnumLeafInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.model.TypeUse;
import com.sun.tools.xjc.outline.FieldOutline;

public class AdaptSingleEnumNonReferenceAsEnumValue extends
		AbstractAdaptEnumPropertyInfo {

	public AdaptSingleEnumNonReferenceAsEnumValue() {
		super();
	}

	@Override
	public TypeUse getPropertyType(ProcessModel context,
			CPropertyInfo propertyInfo) {
		Collection<? extends CTypeInfo> types = context.getGetTypes().process(
				context, propertyInfo);
		return ((CEnumLeafInfo) types.iterator().next()).base;
	}

	@Override
	protected FieldOutline generateField(CPropertyInfo core,
			ClassOutlineImpl classOutline, CPropertyInfo propertyInfo) {
		final SingleEnumValueWrappingField fieldOutline = new SingleEnumValueWrappingField(
				classOutline, propertyInfo, core);
		fieldOutline.generateAccessors();
		return fieldOutline;

	}

}
