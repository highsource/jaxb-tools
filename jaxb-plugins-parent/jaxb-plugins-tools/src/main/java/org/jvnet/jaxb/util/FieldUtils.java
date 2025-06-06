package org.jvnet.jaxb.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.xml.namespace.QName;

import com.sun.codemodel.JType;
import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.generator.bean.field.FieldRendererFactory;
import com.sun.tools.xjc.model.CAttributePropertyInfo;
import com.sun.tools.xjc.model.CCustomizations;
import com.sun.tools.xjc.model.CElementInfo;
import com.sun.tools.xjc.model.CNonElement;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.outline.Aspect;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.Outline;

/**
 * Field utilities.
 *
 * @author valikov
 */
public class FieldUtils {

	/** Hidden constructor. */
	private FieldUtils() {
	}

	//
	// public static FieldItem[] getFieldItems(ClassContext classContext) {
	// final List fieldItems = (List) classContext.target
	// .visit(new FieldGatheringVisitor(classContext));
	// return (FieldItem[]) fieldItems.toArray(new
	// FieldItem[fieldItems.size()]);
	// }
	//
	// public static String getFieldName(ClassContext classContext, FieldItem
	// fieldItem) {
	// return ClassUtils.getClassName(classContext.target) + "." +
	// fieldItem.name;
	// }
	//
	// public static String getFieldPropertyName(FieldItem fieldItem) {
	// return fieldItem.name;
	// }
	//
	// public static String getCommonFieldPropertyName(FieldItem fieldItem) {
	// final String draftName = fieldItem.name;
	// return Introspector.decapitalize(draftName);
	// }
	//
	// public static FieldItem getFieldItem(FieldUse fieldUse) {
	// if (fieldUse.items.isEmpty()) {
	// return null;
	// }
	// else {
	// return (FieldItem) fieldUse.items.iterator().next();
	// }
	// }
	//
	// public static FieldUse getFieldUse(ClassContext classContext, FieldItem
	// fieldItem) {
	// return classContext.target.getField(fieldItem.name);
	// }
	//
	// /**
	// * Returns a new unique name of the field in the given class, based on the
	// given prefix. If required, the prefix will
	// * be appended with an integer to make it unique
	// *
	// * @param theClass class to create field in.
	// * @param prefix field name prefix.
	// * @return Unique name of the new field.
	// */
	// public static String generateUniqueFieldName(final JDefinedClass
	// theClass, final String prefix) {
	// final String name;
	// if (null == getField(theClass, prefix)) {
	// name = prefix;
	// }
	// else {
	// int index = 0;
	// while (null != getField(theClass, prefix + index)) {
	// index++;
	// }
	// name = prefix + index;
	// }
	// return name;
	// }
	//
	// /**
	// * Retrievs a named field of the given class.
	// *
	// * @param theClass class to search a field in.
	// * @param name name of the field.
	// * @return Requested field of the given class.
	// */
	// public static JVar getField(final JDefinedClass theClass, final String
	// name) {
	// JFieldVar result = null;
	// for (Iterator iterator = theClass.fields(); iterator.hasNext();) {
	// final JFieldVar var = (JFieldVar) iterator.next();
	// if (name.equals(var.name())) {
	// result = var;
	// }
	// }
	// // todo : if not found???
	// return result;
	// }
	//
	public static FieldOutline createAttributeField(
			ClassOutlineImpl classOutline, String name, final QName attName,
			final CNonElement typeInfo, final boolean required
	// ,
	// final XSDatatype datatype,
	// final JType type
	) {

		final CPropertyInfo propertyInfo = new CAttributePropertyInfo(
		// name
				name,
				// source
				null, new CCustomizations(),
				// locator
				null,
				// attName
				attName,
				// typeUse
				typeInfo,
				// typeName
				typeInfo.getTypeName(),
				// required
				required);

		propertyInfo.realization = new FieldRendererFactory().getDefault();
		final FieldOutline fieldOutline =

		propertyInfo.realization.generate(classOutline, propertyInfo);

		return fieldOutline;

	}

	// public static boolean isConstantField(ClassContext classContext,
	// FieldItem fieldItem) {
	// return AccessorUtils.get(classContext, fieldItem) == null;
	// }
	//
	// public static FieldUse[] getFieldUses(final ClassItem classItem) {
	// if (classItem.getSuperClass() == null)
	// return classItem.getDeclaredFieldUses();
	// else {
	// final FieldUse[] superFieldUses =
	// FieldUtils.getFieldUses(classItem.getSuperClass());
	// final FieldUse[] declaredFieldUses = classItem.getDeclaredFieldUses();
	// final FieldUse[] fieldUses = new FieldUse[superFieldUses.length +
	// declaredFieldUses.length];
	// System.arraycopy(superFieldUses, 0, fieldUses, 0, superFieldUses.length);
	// System.arraycopy(
	// declaredFieldUses,
	// 0,
	// fieldUses,
	// superFieldUses.length,
	// declaredFieldUses.length);
	// return fieldUses;
	// }
	// }

	public static Set<JType> getPossibleTypes(FieldOutline fieldOutline,
			Aspect aspect) {
		Objects.requireNonNull(fieldOutline, "Field outline must not be null.");
		final ClassOutline classOutline = fieldOutline.parent();
		final Outline outline = classOutline.parent();
		final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();

		final Set<JType> types = new HashSet<JType>();

		if (propertyInfo.getAdapter() != null) {
			types.add(propertyInfo.getAdapter().customType.toType(fieldOutline
					.parent().parent(), aspect));
		} else if (propertyInfo.baseType != null) {
			types.add(propertyInfo.baseType);
		} else {
			Collection<? extends CTypeInfo> typeInfos = propertyInfo.ref();
			for (CTypeInfo typeInfo : typeInfos) {
				types.addAll(getPossibleTypes(outline, aspect, typeInfo));
			}
		}
		return types;
	}

	public static Set<JType> getPossibleTypes(Outline outline, Aspect aspect,
			CTypeInfo typeInfo) {

		final Set<JType> types = new HashSet<JType>();

		types.add(typeInfo.getType().toType(outline, aspect));
		if (typeInfo instanceof CElementInfo) {

			final CElementInfo elementInfo = (CElementInfo) typeInfo;
			for (CElementInfo substitutionMember : elementInfo
					.getSubstitutionMembers()) {
				types.addAll(getPossibleTypes(outline, aspect,
						substitutionMember));
			}
		}
		return types;
	}

}
