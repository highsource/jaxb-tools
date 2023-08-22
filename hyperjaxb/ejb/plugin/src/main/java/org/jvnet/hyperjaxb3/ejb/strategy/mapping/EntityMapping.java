package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import javax.persistence.InheritanceType;

import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;

import com.sun.java.xml.ns.persistence.orm.Attributes;
import com.sun.java.xml.ns.persistence.orm.Entity;
import com.sun.java.xml.ns.persistence.orm.Inheritance;
import com.sun.java.xml.ns.persistence.orm.Table;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.ClassOutline;

public class EntityMapping implements ClassOutlineMapping<Entity> {

	// private static Log logger = LogFactory.getLog(EntityMapping.class);

	public Entity process(Mapping context, ClassOutline classOutline,
			Options options) {
		final Entity entity = context.getCustomizing().getEntity(classOutline);
		createEntity(context, classOutline, entity);
		return entity;
	}

	public void createEntity(Mapping context, ClassOutline classOutline,
			final Entity entity) {
		createEntity$Name(context, classOutline, entity);
		createEntity$Class(context, classOutline, entity);

		createEntity$Inheritance(context, classOutline, entity);

		createEntity$Table(context, classOutline, entity);

		createEntity$Attributes(context, classOutline, entity);
	}

	public void createEntity$Name(Mapping context, ClassOutline classOutline,
			final Entity entity) {
		if (entity.getName() == null || "##default".equals(entity.getName())) {
			entity.setName(context.getNaming().getEntityName(context,
					classOutline.parent(), classOutline.target));
		}
	}

	public void createEntity$Class(Mapping context, ClassOutline classOutline,
			final Entity entity) {
		if (entity.getClazz() == null || "##default".equals(entity.getClazz())) {
			entity.setClazz(context.getNaming().getEntityClass(context,
					classOutline.parent(), classOutline.target));
		}
	}

	public void createEntity$Inheritance(Mapping context,
			ClassOutline classOutline, final Entity entity) {
		final InheritanceType inheritanceStrategy = getInheritanceStrategy(
				context, classOutline, entity);

		if (isRootClass(context, classOutline)) {
			if (entity.getInheritance() == null
					|| entity.getInheritance().getStrategy() == null) {
				entity.setInheritance(new Inheritance());
				entity.getInheritance().setStrategy(inheritanceStrategy.name());
			}
		} else {
			if (entity.getInheritance() != null
					&& entity.getInheritance().getStrategy() != null) {
				entity.setInheritance(null);
			}
		}
	}

	private void createEntity$Table(Mapping context, ClassOutline classOutline,
			Entity entity) {
		final InheritanceType inheritanceStrategy = getInheritanceStrategy(
				context, classOutline, entity);
		switch (inheritanceStrategy) {
		case JOINED:
			if (entity.getTable() == null) {
				entity.setTable(new Table());
			}
			createTable(context, classOutline, entity.getTable());
			break;
		case SINGLE_TABLE:
			if (isRootClass(context, classOutline)) {
				if (entity.getTable() == null) {
					entity.setTable(new Table());
				}
				createTable(context, classOutline, entity.getTable());
			} else {
				if (entity.getTable() != null) {
					entity.setTable(null);
				}
			}
			break;
		case TABLE_PER_CLASS:
			if (entity.getTable() == null) {
				entity.setTable(new Table());
			}
			createTable(context, classOutline, entity.getTable());
			break;
		default:
			throw new IllegalArgumentException("Unknown inheritance strategy.");
		}
	}

	public void createTable(Mapping context, ClassOutline classOutline,
			final Table table) {
		if (table.getName() == null || "##default".equals(table.getName())) {
			table.setName(context.getNaming().getEntityTable$Name(context,
					classOutline));
		}
	}

	public void createEntity$Attributes(Mapping context,
			ClassOutline classOutline, final Entity entity) {
		final Attributes attributes = context.getAttributesMapping().process(
				context, classOutline, null);
		entity.setAttributes(attributes);
	}

	public javax.persistence.InheritanceType getInheritanceStrategy(
			Mapping context, ClassOutline classOutline, Entity entity) {
		if (isRootClass(context, classOutline)) {
			if (entity.getInheritance() != null
					&& entity.getInheritance().getStrategy() != null) {
				return InheritanceType.valueOf(entity.getInheritance()
						.getStrategy());
			} else {
				return javax.persistence.InheritanceType.JOINED;
			}
		} else {
			final ClassOutline superClassOutline = getSuperClass(context,
					classOutline);
			final Entity superClassEntity = context.getCustomizing().getEntity(
					superClassOutline);

			return getInheritanceStrategy(context, superClassOutline,
					superClassEntity);
		}
	}

	public ClassOutline getSuperClass(Mapping context, ClassOutline classOutline) {
		return classOutline.getSuperClass();
	}

	/*
	 * public ClassOutline getSuperClassOutline(Mapping context, ClassOutline
	 * classOutline) { return classOutline.getSuperClass(); }
	 * 
	 * public boolean isEntityClassHierarchyRoot(Mapping context, ClassOutline
	 * classOutline) { final ClassOutline superClassOutline =
	 * getSuperClassOutline(context, classOutline);
	 * 
	 * if (superClassOutline == null) { return true; } else if
	 * (CustomizationUtils.containsCustomization(classOutline,
	 * Customizations.MAPPED_SUPERCLASS_ELEMENT_NAME)) { return true; } else if
	 * (context.getIgnoring().isClassOutlineIgnored( superClassOutline)) {
	 * return true; } else { return false; } }
	 */

	public boolean isRootClass(Mapping context, ClassOutline classOutline) {
		if (classOutline.getSuperClass() != null) {
			return !CustomizationUtils.containsCustomization(classOutline,
					Customizations.MAPPED_SUPERCLASS_ELEMENT_NAME)
					&& !isSelfOrAncestorRootClass(context,
							classOutline.getSuperClass());
		} else {
			return !CustomizationUtils.containsCustomization(classOutline,
					Customizations.MAPPED_SUPERCLASS_ELEMENT_NAME);
		}
	}

	public boolean isSelfOrAncestorRootClass(Mapping context,
			ClassOutline classOutline) {
		if (context.getIgnoring().isClassOutlineIgnored(context, classOutline)) {
			return false;
		} else if (isRootClass(context, classOutline)) {
			return true;
		} else if (classOutline.getSuperClass() != null) {
			return isSelfOrAncestorRootClass(context,
					classOutline.getSuperClass());
		} else {
			return !CustomizationUtils.containsCustomization(classOutline,
					Customizations.MAPPED_SUPERCLASS_ELEMENT_NAME);
		}

	}

}
