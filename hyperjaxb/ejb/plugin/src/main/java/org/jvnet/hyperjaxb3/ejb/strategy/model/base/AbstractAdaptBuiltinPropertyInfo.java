package org.jvnet.hyperjaxb3.ejb.strategy.model.base;

import java.util.Collection;
import java.util.Collections;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;

import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.generator.bean.field.FieldRenderer;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.TypeUse;
import com.sun.tools.xjc.model.TypeUseFactory;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.xml.bind.v2.model.core.PropertyKind;

public abstract class AbstractAdaptBuiltinPropertyInfo extends
		AbstractAdaptPropertyInfo {

	public AbstractAdaptBuiltinPropertyInfo(TypeUse type,
			Class<? extends XmlAdapter<?, ?>> adapterClass) {
		this.propertyType = TypeUseFactory.adapt(type, adapterClass, false);
	}

	public AbstractAdaptBuiltinPropertyInfo(TypeUse propertyType) {
		this.propertyType = propertyType;
	}

	public Collection<CPropertyInfo> process(final ProcessModel context,
			final CPropertyInfo core) {
		final CPropertyInfo newPropertyInfo = createPropertyInfo(context, core);

		newPropertyInfo.realization = new FieldRenderer() {

			public FieldOutline generate(ClassOutlineImpl classOutline,
					CPropertyInfo propertyInfo) {
				return generateField(context, core, classOutline, propertyInfo);
			}

		};
		Customizations.markIgnored(core);
		return Collections.singletonList(newPropertyInfo);
	}

	private PropertyKind propertyKind = PropertyKind.ATTRIBUTE;

	@Override
	public PropertyKind getDefaultGeneratedPropertyKind(
			ProcessModel context, CPropertyInfo propertyInfo) {
		return propertyKind;
	}

	private final TypeUse propertyType;

	@Override
	public TypeUse getPropertyType(ProcessModel context,
			CPropertyInfo propertyInfo) {
		return propertyType;
	}

	@Override
	public String getDefaultGeneratedPropertyName(ProcessModel context,
			CPropertyInfo propertyInfo) {
		// TODO Allow for customization
		return propertyInfo.getName(true) + "Item";
	}

	protected abstract FieldOutline generateField(final ProcessModel context,
			final CPropertyInfo core, ClassOutlineImpl classOutline,
			CPropertyInfo propertyInfo);
}
