package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EnumType;
import javax.xml.namespace.QName;

import org.jvnet.hyperjaxb3.codemodel.util.JTypeUtils;
import org.jvnet.hyperjaxb3.xsd.util.XMLSchemaConstrants;
import org.jvnet.hyperjaxb3.xsom.SimpleTypeAnalyzer;
import org.jvnet.hyperjaxb3.xsom.TypeUtils;
import org.jvnet.jaxb2_commons.util.FieldAccessorUtils;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JType;
import com.sun.java.xml.ns.persistence.orm.AttributeOverride;
import com.sun.java.xml.ns.persistence.orm.Basic;
import com.sun.java.xml.ns.persistence.orm.Column;
import com.sun.java.xml.ns.persistence.orm.EmbeddableAttributes;
import com.sun.java.xml.ns.persistence.orm.Embedded;
import com.sun.java.xml.ns.persistence.orm.Lob;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CEnumLeafInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.Outline;
import com.sun.xml.xsom.XSComponent;

public final class DefaultAttributeMapping implements AttributeMapping {

	public final Column createColumn(Mapping context,
			FieldOutline fieldOutline, Column column) {

		if (column == null) {
			column = new Column();
		}

		if (column.getName() == null || "##default".equals(column.getName())) {
			column.setName(context.getNaming().getColumn$Name(context,
					fieldOutline));
		}

		// If string
		if (column.getLength() == null) {
			column.setLength(createColumn$Length(fieldOutline));
		}

		final Integer defaultPrecision = column.getPrecision();
		final Integer defaultScale = column.getScale();
		final Integer fractionDigits = createColumn$FractionDigits(fieldOutline);
		if (fractionDigits != null && fractionDigits.intValue() != 0) {
			if (defaultPrecision != null) {
				final int integerDigits = defaultPrecision
						- (defaultScale == null ? 0 : defaultScale.intValue());
				final Integer precision = integerDigits
						+ fractionDigits.intValue();
				column.setPrecision(precision);
				column.setScale(fractionDigits);

			}
		}

		return column;
	}

	private Integer createColumn$FractionDigits(FieldOutline fieldOutline) {
		final Integer fractionDigitsAsInteger;
		final Long fractionDigits = SimpleTypeAnalyzer
				.getFractionDigits(fieldOutline.getPropertyInfo()
						.getSchemaComponent());
		if (fractionDigits != null) {
			fractionDigitsAsInteger = fractionDigits.intValue();
		} else {
			fractionDigitsAsInteger = null;
		}
		return fractionDigitsAsInteger;
	}

	// private Integer createColumn$TotalDigits(FieldOutline fieldOutline) {
	// final Integer totalDigitsAsInteger;
	// final Long totalDigits = SimpleTypeAnalyzer.getTotalDigits(fieldOutline
	// .getPropertyInfo().getSchemaComponent());
	// if (totalDigits != null) {
	// totalDigitsAsInteger = totalDigits.intValue();
	// } else {
	// totalDigitsAsInteger = null;
	// }
	// return totalDigitsAsInteger;
	// }

	private Integer createColumn$Length(FieldOutline fieldOutline) {
		final Integer finalLength;
		final Long length = SimpleTypeAnalyzer.getLength(fieldOutline
				.getPropertyInfo().getSchemaComponent());

		if (length != null) {
			finalLength = length.intValue();
		} else {
			final Long maxLength = SimpleTypeAnalyzer.getMaxLength(fieldOutline
					.getPropertyInfo().getSchemaComponent());
			if (maxLength != null) {
				finalLength = maxLength.intValue();
			} else {
				final Long minLength = SimpleTypeAnalyzer
						.getMinLength(fieldOutline.getPropertyInfo()
								.getSchemaComponent());
				if (minLength != null) {
					int intMinLength = minLength.intValue();
					if (intMinLength > 127) {
						finalLength = intMinLength * 2;
					} else {
						finalLength = null;
					}
				} else {
					finalLength = null;
				}
			}
		}
		return finalLength;
	}

	public boolean isTemporal(Mapping context, FieldOutline fieldOutline) {
		final JMethod getter = FieldAccessorUtils.getter(fieldOutline);
		final JType type = getter.type();
		return JTypeUtils.isTemporalType(type);
	}

	public String createTemporalType(Mapping context, FieldOutline fieldOutline) {
		final JMethod getter = FieldAccessorUtils.getter(fieldOutline);
		final JType type = getter.type();
		final JCodeModel codeModel = type.owner();
		// Detect SQL types
		if (codeModel.ref(java.sql.Time.class).equals(type)) {
			return "TIME";
		} else if (codeModel.ref(java.sql.Date.class).equals(type)) {
			return "DATE";
		} else if (codeModel.ref(java.sql.Timestamp.class).equals(type)) {
			return "TIMESTAMP";
		} else if (codeModel.ref(java.util.Calendar.class).equals(type)) {
			return "TIMESTAMP";
		} else {
			final List<QName> typeNames;
			final XSComponent schemaComponent = fieldOutline.getPropertyInfo()
					.getSchemaComponent();
			if (schemaComponent != null) {
				typeNames = TypeUtils.getTypeNames(schemaComponent);
			} else {
				typeNames = Collections.emptyList();
			}
			if (typeNames.contains(XMLSchemaConstrants.DATE_QNAME)
					||
					//
					typeNames.contains(XMLSchemaConstrants.G_YEAR_MONTH_QNAME)
					||
					//
					typeNames.contains(XMLSchemaConstrants.G_YEAR_QNAME) ||
					//
					typeNames.contains(XMLSchemaConstrants.G_MONTH_QNAME) ||
					//
					typeNames.contains(XMLSchemaConstrants.G_MONTH_DAY_QNAME) ||
					//
					typeNames.contains(XMLSchemaConstrants.G_DAY_QNAME)) {
				return "DATE";
			} else if (typeNames.contains(XMLSchemaConstrants.TIME_QNAME)) {
				return "TIME";
			} else if (typeNames.contains(XMLSchemaConstrants.DATE_TIME_QNAME)) {
				return "TIMESTAMP";
			} else {
				return "TIMESTAMP";
			}
		}
	}

	public final boolean isLob(Mapping context, FieldOutline fieldOutline) {
		return false;
	}

	public final Lob createLob(Mapping context, FieldOutline fieldOutline) {
		return new Lob();
	}

	public final boolean isEnumerated(Mapping context, FieldOutline fieldOutline) {
		final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();

		final Collection<? extends CTypeInfo> types = context.getGetTypes()
				.process(context, propertyInfo);

		assert types.size() == 1;

		final CTypeInfo type = types.iterator().next();

		return type instanceof CEnumLeafInfo;
	}

	public String createEnumerated(Mapping context, FieldOutline fieldOutline) {
		return EnumType.STRING.name();
	}

	public void createAttributeOverride(Mapping context,
			FieldOutline fieldOutline,
			final List<AttributeOverride> attributeOverrides) {

		final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();

		final Collection<? extends CTypeInfo> types = context.getGetTypes()
				.process(context, propertyInfo);

		assert types.size() == 1;

		final CTypeInfo typeInfo = types.iterator().next();

		assert typeInfo instanceof CClassInfo;

		final CClassInfo classInfo = (CClassInfo) typeInfo;

		final Outline outline = fieldOutline.parent().parent();

		final ClassOutline classOutline = outline.getClazz(classInfo);

		final Options options = outline.getModel().options;

		final Map<String, AttributeOverride> attributeOverridesMap = new HashMap<String, AttributeOverride>();
		for (final AttributeOverride attributeOverride : attributeOverrides) {
			attributeOverridesMap.put(attributeOverride.getName(),
					attributeOverride);
		}
		Mapping embeddedMapping = context.createEmbeddedMapping(context,
				fieldOutline);

		final EmbeddableAttributes embeddableAttributes = embeddedMapping
				.getEmbeddableAttributesMapping().process(embeddedMapping,
						classOutline, options);

		for (final Basic basic : embeddableAttributes.getBasic()) {
			final String name = basic.getName();
			final AttributeOverride attributeOverride;
			if (!attributeOverridesMap.containsKey(name)) {
				attributeOverride = new AttributeOverride();
				attributeOverride.setName(name);
				attributeOverride.setColumn(basic.getColumn());
				attributeOverridesMap.put(name, attributeOverride);
				attributeOverrides.add(attributeOverride);
			} else {
				attributeOverride = attributeOverridesMap.get(name);
			}

			// TODO Check that column is not null
			if (attributeOverride.getColumn() == null) {
				attributeOverride.setColumn(new Column());
			}
		}
		for (final Embedded embedded : embeddableAttributes.getEmbedded()) {
			final String parentName = embedded.getName();

			for (AttributeOverride embeddedAttributeOverride : embedded
					.getAttributeOverride()) {
				final String childName = embeddedAttributeOverride.getName();
				final String name = parentName + "." + childName;

				final AttributeOverride attributeOverride;
				if (!attributeOverridesMap.containsKey(name)) {
					attributeOverride = new AttributeOverride();
					attributeOverride.setName(name);
					attributeOverride.setColumn(embeddedAttributeOverride
							.getColumn());
					attributeOverridesMap.put(name, attributeOverride);
					attributeOverrides.add(attributeOverride);
				} else {
					attributeOverride = attributeOverridesMap.get(name);
				}

				// TODO Check that column is not null
				if (attributeOverride.getColumn() == null) {
					attributeOverride.setColumn(new Column());
				}
			}
		}
	}

}
