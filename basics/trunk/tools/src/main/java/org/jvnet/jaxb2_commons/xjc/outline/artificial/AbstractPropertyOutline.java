package org.jvnet.jaxb2_commons.xjc.outline.artificial;

import org.apache.commons.lang.Validate;
import org.jvnet.jaxb2_commons.xjc.outline.MClassOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MPropertyAccessor;
import org.jvnet.jaxb2_commons.xjc.outline.MPropertyOutline;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfo;

import com.sun.codemodel.JAnnotatable;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JType;

public abstract class AbstractPropertyOutline implements MPropertyOutline {

	protected final MClassOutline classOutline;

	protected final MPropertyInfo propertyInfo;

	protected final JCodeModel codeModel;

	protected final JDefinedClass referenceClass;

	protected final JDefinedClass implementationClass;

	protected final JClass implementationReferenceClass;

	protected final JType implementationType;

	protected final JType referenceType;

	public AbstractPropertyOutline(MClassOutline classOutline,
			MPropertyInfo target) {
		Validate.notNull(classOutline);
		Validate.notNull(target);
		this.classOutline = classOutline;
		this.propertyInfo = target;
		this.codeModel = classOutline.getParent().getCode();

		this.referenceClass = classOutline.getReferenceCode();
		this.implementationClass = classOutline.getImplementationCode();
		this.implementationReferenceClass = classOutline
				.getImplementationReferenceCode();
		this.implementationType = createImplementationType(target);
		this.referenceType = createReferenceType(target);
	}

	protected JType createImplementationType(MPropertyInfo propertyInfo) {
		throw new UnsupportedOperationException();
	}

	protected JType createReferenceType(MPropertyInfo propertyInfo) {
		throw new UnsupportedOperationException();
	}

	protected void annotate(JAnnotatable annotatable) {
		throw new UnsupportedOperationException();
	}

	public MClassOutline getClassOutline() {
		return classOutline;
	}

	public MPropertyInfo getTarget() {
		return propertyInfo;
	}

	protected abstract class PropertyAccessor implements MPropertyAccessor {

		protected final JExpression target;

		public PropertyAccessor(JExpression target) {
			Validate.notNull(target);
			this.target = target;
		}

		public boolean isConstant() {
			return false;
		}
	}
}
