package org.jvnet.hyperjaxb3.ejb.strategy.model.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.strategy.model.CreatePropertyInfos;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;
import org.jvnet.hyperjaxb3.xjc.generator.bean.field.JAXBElementNameField;
import org.jvnet.hyperjaxb3.xjc.generator.bean.field.JAXBElementValueField;
import org.jvnet.hyperjaxb3.xml.XMLConstants;

import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.generator.bean.field.FieldRenderer;
import com.sun.tools.xjc.model.CAttributePropertyInfo;
import com.sun.tools.xjc.model.CBuiltinLeafInfo;
import com.sun.tools.xjc.model.CCustomizations;
import com.sun.tools.xjc.model.CElement;
import com.sun.tools.xjc.model.CElementInfo;
import com.sun.tools.xjc.model.CElementPropertyInfo;
import com.sun.tools.xjc.model.CElementPropertyInfo.CollectionMode;
import com.sun.tools.xjc.model.CNonElement;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CReferencePropertyInfo;
import com.sun.tools.xjc.model.CTypeRef;
import com.sun.tools.xjc.outline.FieldOutline;
import org.glassfish.jaxb.core.v2.model.core.ID;

public class WrapSingleSubstitutedElementReference implements
		CreatePropertyInfos {
	protected Log logger = LogFactory.getLog(getClass());

	public Collection<CPropertyInfo> process(ProcessModel context,
			final CPropertyInfo draftPropertyInfo) {
		assert draftPropertyInfo instanceof CReferencePropertyInfo;
		final CReferencePropertyInfo propertyInfo = (CReferencePropertyInfo) draftPropertyInfo;
		final CReferencePropertyInfo referencePropertyInfo = (CReferencePropertyInfo) propertyInfo;
		assert !referencePropertyInfo.isMixed();
		assert referencePropertyInfo.getWildcard() == null;
		final Set<CElement> elements = context.getGetTypes().getElements(
				context, referencePropertyInfo);
		assert elements.size() == 1;
		final CElementInfo elementInfo = getElementInfo(context,
				referencePropertyInfo);
		final CNonElement type = elementInfo.getContentType();

		final CAttributePropertyInfo name = new CAttributePropertyInfo(
				referencePropertyInfo.getName(true) + "Name", null,
				new CCustomizations(), null, new QName("name"),
				CBuiltinLeafInfo.STRING, new QName(
						XMLConstants.W3C_XML_SCHEMA_NS_URI, "string"), true);

		final CElementPropertyInfo value = new CElementPropertyInfo(

		referencePropertyInfo.getName(true) + "Value",
				CollectionMode.NOT_REPEATED, ID.NONE, null,
				referencePropertyInfo.getSchemaComponent(),
				new CCustomizations(), referencePropertyInfo.getLocator(), true);

		final CTypeRef typeRef = new CTypeRef(type, new QName(
				referencePropertyInfo.getName(true) + "Value"),
				type.getTypeName(), false, null);

		value.getTypes().add(typeRef);

		name.realization = new FieldRenderer() {
			public FieldOutline generate(ClassOutlineImpl context,
					CPropertyInfo prop) {
				final JAXBElementNameField fieldOutline = new JAXBElementNameField(
						context, prop, propertyInfo, value, type);

				fieldOutline.generateAccessors();
				return fieldOutline;
			}
		};

		value.realization = new FieldRenderer() {
			public FieldOutline generate(ClassOutlineImpl context,
					CPropertyInfo prop) {
				final JAXBElementValueField fieldOutline = new JAXBElementValueField(
						context, prop, propertyInfo, name, type);
				fieldOutline.generateAccessors();
				return fieldOutline;
			}
		};

		Customizations.markGenerated(name);
		Customizations.markGenerated(value);
		Customizations.markIgnored(propertyInfo);

		final Collection<CPropertyInfo> newProperties = new ArrayList<CPropertyInfo>(
				2);
		newProperties.add(name);
		newProperties.add(value);
		return newProperties;
	}

	public CElementInfo getElementInfo(ProcessModel context,
			final CReferencePropertyInfo referencePropertyInfo) {
		final CElement element = context.getGetTypes()
				.getElements(context, referencePropertyInfo).iterator().next();
		assert element instanceof CElementInfo;

		final CElementInfo elementInfo = (CElementInfo) element;
		return elementInfo;
	}

}
