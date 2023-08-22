package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.hyperjaxb3.codemodel.util.JTypeUtils;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.xjc.model.CTypeInfoUtils;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;
import org.jvnet.jaxb2_commons.util.FieldAccessorUtils;

import com.sun.codemodel.JMethod;
import com.sun.codemodel.JType;
import com.sun.java.xml.ns.persistence.orm.Attributes;
import com.sun.java.xml.ns.persistence.orm.Basic;
import com.sun.java.xml.ns.persistence.orm.ElementCollection;
import com.sun.java.xml.ns.persistence.orm.Embedded;
import com.sun.java.xml.ns.persistence.orm.EmbeddedId;
import com.sun.java.xml.ns.persistence.orm.Id;
import com.sun.java.xml.ns.persistence.orm.ManyToMany;
import com.sun.java.xml.ns.persistence.orm.ManyToOne;
import com.sun.java.xml.ns.persistence.orm.OneToMany;
import com.sun.java.xml.ns.persistence.orm.OneToOne;
import com.sun.java.xml.ns.persistence.orm.Transient;
import com.sun.java.xml.ns.persistence.orm.Version;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CClass;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CEnumLeafInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import  com.sun.tools.xjc.model.Aspect;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;

public class AttributesMapping implements ClassOutlineMapping<Attributes> {

	protected Log logger = LogFactory.getLog(getClass());

	public Attributes process(Mapping context, ClassOutline classOutline,
			Options options) {

		final Attributes attributes = new Attributes();

		final FieldOutline[] fieldOutlines = classOutline.getDeclaredFields();
		for (final FieldOutline fieldOutline : fieldOutlines) {

			final Object attributeMapping = getAttributeMapping(context,
					fieldOutline, options).process(context, fieldOutline,
					options);

			if (attributeMapping instanceof Id) {
				if (attributes.getEmbeddedId() == null) {
					attributes.getId().add((Id) attributeMapping);
				} else {
					logger.error("Could not add an id element to the attributes of the class ["
							+

							fieldOutline.parent().target.getName()
							+ "] because they already contain an embedded-id element.");
				}
			} else if (attributeMapping instanceof EmbeddedId) {
				if (!attributes.getId().isEmpty()) {
					logger.error("Could not add an embedded-id element to the attributes of the class ["
							+

							fieldOutline.parent().target.getName()
							+ "] because they already contain an id element.");
				} else if (attributes.getEmbeddedId() != null) {
					logger.error("Could not add an embedded-id element to the attributes of the class ["
							+

							fieldOutline.parent().target.getName()
							+ "] because they already contain an embedded-id element.");
				} else {
					attributes.setEmbeddedId((EmbeddedId) attributeMapping);
				}
			} else if (attributeMapping instanceof Basic) {
				attributes.getBasic().add((Basic) attributeMapping);
			} else if (attributeMapping instanceof Version) {
				attributes.getVersion().add((Version) attributeMapping);
			} else if (attributeMapping instanceof ManyToOne) {
				attributes.getManyToOne().add((ManyToOne) attributeMapping);
			} else if (attributeMapping instanceof OneToMany) {
				attributes.getOneToMany().add((OneToMany) attributeMapping);
			} else if (attributeMapping instanceof OneToOne) {
				attributes.getOneToOne().add((OneToOne) attributeMapping);
			} else if (attributeMapping instanceof ManyToMany) {
				attributes.getManyToMany().add((ManyToMany) attributeMapping);
			} else if (attributeMapping instanceof ElementCollection) {
				attributes.getElementCollection().add(
						(ElementCollection) attributeMapping);
			} else if (attributeMapping instanceof Embedded) {
				attributes.getEmbedded().add((Embedded) attributeMapping);
			} else if (attributeMapping instanceof Transient) {
				attributes.getTransient().add((Transient) attributeMapping);
			}
		}
		return attributes;
	}

	public FieldOutlineMapping<?> getAttributeMapping(Mapping context,
			FieldOutline fieldOutline, Options options) {
		if (context.getIgnoring().isFieldOutlineIgnored(context, fieldOutline)) {
			return context.getTransientMapping();
		} else if (isFieldOutlineId(fieldOutline)) {
			return context.getIdMapping();
		} else if (isFieldOutlineVersion(fieldOutline)) {
			return context.getVersionMapping();
		} else {

			final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();
			if (!propertyInfo.isCollection()) {
				logger.trace("Field outline  [" + propertyInfo.getName(true)
						+ "] is a single field.");

				final Collection<? extends CTypeInfo> types = context
						.getGetTypes().process(context, propertyInfo);

				if (types.size() == 1) {
					logger.trace("Field outline  ["
							+ propertyInfo.getName(true)
							+ "] is a homogeneous single field.");

					if (isFieldOutlineBasic(context, fieldOutline)) {
						return context.getBasicMapping();
					} else

					if (isFieldOutlineComplex(context, fieldOutline)) {
						logger.trace("Field outline  ["
								+ propertyInfo.getName(true)
								+ "] is a complex field.");
						if (isFieldOutlineEmbeddedId(context, fieldOutline)) {
							logger.trace("Field outline  ["
									+ propertyInfo.getName(true)
									+ "] is an embedded-id complex field.");
							return context.getEmbeddedIdMapping();
						} else if (isFieldOutlineEmbedded(context, fieldOutline)) {
							logger.trace("Field outline  ["
									+ propertyInfo.getName(true)
									+ "] is an embedded complex field.");
							return context.getEmbeddedMapping();
						} else {
							return context.getToOneMapping();
						}
					}
				} else {
					logger.warn("Field outline  [" + propertyInfo.getName(true)
							+ "] is a heterogeneous single field.");
				}
			} else {
				logger.trace("Field outline [" + propertyInfo.getName(true)
						+ "] is a collection field.");

				if (isFieldOutlineSingletypedHomogeneous(context, fieldOutline)) {
					logger.debug("Field outline  ["
							+ propertyInfo.getName(true)
							+ "] is a homogeneous collection field.");
					if (isFieldOutlineElementCollection(context, fieldOutline)) {
						return context.getElementCollectionMapping();
					} else

					if (isFieldOutlineComplex(context, fieldOutline)) {
						logger.debug("Field outline  ["
								+ propertyInfo.getName(true)
								+ "] is a complex homogeneous collection field.");
						return context.getToManyMapping();
					}
				} else if (isFieldOutlineMultitypedHomogeneous(context,
						fieldOutline)) {
					logger.debug("Field outline  ["
							+ propertyInfo.getName(true)
							+ "] is a multityped homogeneous collection field.");
					if (isFieldOutlineComplex(context, fieldOutline)) {
						logger.debug("Field outline  ["
								+ propertyInfo.getName(true)
								+ "] is a complex multityped homogeneous collection field.");
						return context.getToManyMapping();
					}
				} else {
					logger.warn("Field outline  [" + propertyInfo.getName(true)
							+ "] is a heterogenous collection field.");
				}

			}

			logger.error("Field outline  [" +

			((CClassInfo) propertyInfo.parent()).getName() + "."
					+ propertyInfo.getName(true)
					+ "] could not be annotated. It will be made transient.");
			return context.getTransientMapping();
		}
	}

	public boolean isFieldOutlineId(FieldOutline fieldOutline) {
		return CustomizationUtils.containsCustomization(fieldOutline,
				Customizations.ID_ELEMENT_NAME);
	}

	public boolean isFieldOutlineVersion(FieldOutline fieldOutline) {

		return CustomizationUtils.containsCustomization(fieldOutline,
				Customizations.VERSION_ELEMENT_NAME);
	}

	public boolean isFieldOutlineBasic(Mapping context,
			FieldOutline fieldOutline) {

		return isFieldOutlineCore(context, fieldOutline)
				|| isFieldOutlineEnumerated(context, fieldOutline);
	}

	public boolean isFieldOutlineCore(Mapping context, FieldOutline fieldOutline) {
		final JMethod getter = FieldAccessorUtils.getter(fieldOutline);

		final JType type = getter.type();
		return JTypeUtils.isBasicType(type);
	}

	public boolean isFieldOutlineEnumerated(Mapping context,
			FieldOutline fieldOutline) {
		final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();

		final Collection<? extends CTypeInfo> types = context.getGetTypes()
				.process(context, propertyInfo);

		if (types.size() == 1) {

			final CTypeInfo type = types.iterator().next();

			return type instanceof CEnumLeafInfo;
		} else {
			return false;
		}
	}

	public boolean isFieldOutlineSingletypedHomogeneous(Mapping context,
			FieldOutline fieldOutline) {

		final Collection<? extends CTypeInfo> types = context.getGetTypes()
				.process(context, fieldOutline.getPropertyInfo());

		return types.size() == 1;

	}

	public boolean isFieldOutlineMultitypedHomogeneous(Mapping context,
			FieldOutline fieldOutline) {

		return getCommonBaseTypeInfo(context, fieldOutline) != null;
	}

	public CTypeInfo getCommonBaseTypeInfo(Mapping context,
			FieldOutline fieldOutline) {
		final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();

		final Collection<? extends CTypeInfo> types = context.getGetTypes()
				.process(context, propertyInfo);

		return CTypeInfoUtils.getCommonBaseTypeInfo(types);
	}

	public boolean isFieldOutlineElementCollection(Mapping context,
			FieldOutline fieldOutline) {

		return isFieldOutlineCore2(context, fieldOutline)
				|| isFieldOutlineEnumerated(context, fieldOutline);
	}

	public boolean isFieldOutlineCore2(Mapping context,
			FieldOutline fieldOutline) {

		final CTypeInfo type = getCommonBaseTypeInfo(context, fieldOutline);

		assert type != null;

		return JTypeUtils.isBasicType(type.toType(fieldOutline.parent()
				.parent(), Aspect.EXPOSED));
	}

	public boolean isFieldOutlineComplex(Mapping context,
			FieldOutline fieldOutline) {

		final CTypeInfo type = getCommonBaseTypeInfo(context, fieldOutline);

		assert type != null;

		return type instanceof CClass;
	}

	public boolean isFieldOutlineEmbedded(Mapping context,
			FieldOutline fieldOutline) {

		final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();

		final Collection<? extends CTypeInfo> types = context.getGetTypes()
				.process(context, propertyInfo);

		assert types.size() == 1;

		final CTypeInfo type = types.iterator().next();

		return (type instanceof CClass && CustomizationUtils
				.containsCustomization(fieldOutline,
						Customizations.EMBEDDED_ELEMENT_NAME))
				//
				||
				//
				(type instanceof CClassInfo && CustomizationUtils
						.containsCustomization(((CClassInfo) type),
								Customizations.EMBEDDABLE_ELEMENT_NAME))

		;
	}

	public boolean isFieldOutlineEmbeddedId(Mapping context,
			FieldOutline fieldOutline) {

		final Collection<? extends CTypeInfo> types = context.getGetTypes()
				.process(context, fieldOutline.getPropertyInfo());

		assert types.size() == 1;

		final CTypeInfo type = types.iterator().next();

		return (type instanceof CClass && CustomizationUtils
				.containsCustomization(fieldOutline,
						Customizations.EMBEDDED_ID_ELEMENT_NAME));
	}

}
