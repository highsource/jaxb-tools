package org.jvnet.jaxb2_commons.xjc.outline.artificial;

import org.apache.commons.lang3.Validate;
import org.jvnet.jaxb2_commons.xjc.outline.MClassOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MModelOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MPropertyAccessor;
import org.jvnet.jaxb2_commons.xjc.outline.MPropertyOutline;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfo;

import com.sun.codemodel.JAnnotatable;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JType;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.outline.Outline;

public abstract class AbstractPropertyOutline implements MPropertyOutline {

	protected final Outline outline;

	protected final MModelOutline modelOutline;

	protected final MClassOutline classOutline;

	protected final MPropertyInfo<NType, NClass> propertyInfo;

	protected final JCodeModel codeModel;

	protected final JDefinedClass referenceClass;

	protected final JDefinedClass implementationClass;

	protected final JClass implementationReferenceClass;

	protected final JType type;

	public AbstractPropertyOutline(Outline outline, MClassOutline classOutline,
			MPropertyInfo<NType, NClass> target) {
		Validate.notNull(outline);
		Validate.notNull(classOutline);
		Validate.notNull(target);
		this.outline = outline;
		this.modelOutline = classOutline.getParent();
		this.classOutline = classOutline;
		this.propertyInfo = target;
		this.codeModel = classOutline.getParent().getCode();

		this.referenceClass = classOutline.getReferenceCode();
		this.implementationClass = classOutline.getImplementationCode();
		this.implementationReferenceClass = classOutline
				.getImplementationReferenceCode();

		this.type = generateType();
	}

	protected JType generateType() {
		return this.propertyInfo
				.acceptPropertyInfoVisitor(new PropertyTypeVisitor(
						this.modelOutline));
	}

	protected void annotate(JAnnotatable annotatable) {
		this.propertyInfo
				.acceptPropertyInfoVisitor(new AnnotatePropertyVisitor(
						annotatable));
	}

	// protected JType getType(final Aspect aspect) {
	//
	// final List<JType> types = new ArrayList<JType>();
	//
	// propertyInfo
	// .acceptPropertyInfoVisitor(new DefaultPropertyInfoVisitor<NType, NClass,
	// Void>() {
	// public Void visitAnyAttributePropertyInfo(
	// MAnyAttributePropertyInfo<NType, NClass> info) {
	//
	// switch (aspect) {
	// case EXPOSED:
	// types.add(codeModel.ref(Map.class).narrow(QName.class)
	// .narrow(Object.class));
	// break;
	//
	// default:
	// types.add(codeModel.ref(Map.class).narrow(QName.class)
	// .narrow(Object.class));
	// break;
	// }
	//
	// return null;
	// }
	//
	// @Override
	// public Void visitAnyElementPropertyInfo(
	// MAnyElementPropertyInfo<NType, NClass> info) {
	//
	// types
	// // if
	// // TODO Auto-generated method stub
	// return super.visitAnyElementPropertyInfo(info);
	// }
	// });
	//
	// final class TypeList extends ArrayList<JType> {
	// private static final long serialVersionUID = 1L;
	//
	// void add(CTypeInfo t) {
	// add(t.getType().toType(outline, Aspect.EXPOSED));
	// if (t instanceof CElementInfo) {
	// // UGLY. element substitution is implemented in a way that
	// // the derived elements are not assignable to base elements.
	// // so when we compute the signature, we have to take derived
	// // types
	// // into account
	// add(((CElementInfo) t).getSubstitutionMembers());
	// }
	// }
	//
	// void add(Collection<? extends CTypeInfo> col) {
	// for (CTypeInfo typeInfo : col)
	// add(typeInfo);
	// }
	// }
	// TypeList r = new TypeList();
	// r.add(prop.ref());
	//
	// JType t;
	// t = TypeUtil.getCommonBaseType(codeModel, r);
	//
	// // if item type is unboxable, convert t=Integer -> t=int
	// // the in-memory data structure can't have primitives directly,
	// // but this guarantees that items cannot legal hold null,
	// // which helps us improve the boundary signature between our
	// // data structure and user code
	// if (prop.isUnboxable())
	// t = t.unboxify();
	// return t;
	// }

	public MClassOutline getClassOutline() {
		return classOutline;
	}

	public MPropertyInfo<NType, NClass> getTarget() {
		return propertyInfo;
	}

	protected abstract class PropertyAccessor implements MPropertyAccessor {

		protected final JExpression target;

		public PropertyAccessor(JExpression target) {
			Validate.notNull(target);
			this.target = target;
		}
		
		public JType getType() {
			return AbstractPropertyOutline.this.type;
		}

		public boolean isConstant() {
			return false;
		}
		
		public boolean isVirtual() {
			return false;
		}
	}
}
