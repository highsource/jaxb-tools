package org.jvnet.hyperjaxb3.ejb.strategy.model.base;

import org.jvnet.hyperjaxb3.ejb.schemas.customizations.JaxbContext;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;
import org.jvnet.hyperjaxb3.xjc.generator.bean.field.SingleMarshallingReferenceField;
import org.jvnet.jaxb2_commons.util.OutlineUtils;

import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.TypeUse;
import com.sun.tools.xjc.outline.FieldOutline;

public class AdaptSingleWildcardReference extends AbstractAdaptBuiltinPropertyInfo {

	public AdaptSingleWildcardReference(TypeUse propertyType) {
		super(propertyType);
	}

	@Override
	public String getDefaultGeneratedPropertyName(ProcessModel context,
			CPropertyInfo propertyInfo) {
		// TODO Allow for customization
		return propertyInfo.getName(true) + "Object";
	}

	@Override
	protected FieldOutline generateField(final ProcessModel context,
			CPropertyInfo core, ClassOutlineImpl classOutline,
			CPropertyInfo propertyInfo) {

		final JaxbContext jaxbContext = context.getCustomizing()
				.getJaxbContext(propertyInfo);

		final String contextPath = (jaxbContext == null || jaxbContext
				.getContextPath() == null) ? OutlineUtils
				.getContextPath(classOutline.parent()) : jaxbContext
				.getContextPath();

		final boolean _final = (jaxbContext == null
				|| jaxbContext.getField() == null || jaxbContext.getField()
				.isFinal() == null) ? true : jaxbContext.getField().isFinal();

		final SingleMarshallingReferenceField fieldOutline = new SingleMarshallingReferenceField(
				classOutline, propertyInfo, core, contextPath, _final);
		fieldOutline.generateAccessors();
		return fieldOutline;
	}
}
