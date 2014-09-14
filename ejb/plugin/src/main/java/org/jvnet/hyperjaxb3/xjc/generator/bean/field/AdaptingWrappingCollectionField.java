package org.jvnet.hyperjaxb3.xjc.generator.bean.field;

import java.util.ArrayList;

import org.jvnet.hyperjaxb3.transform.TransformUtils;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldRef;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMod;
import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.model.CPropertyInfo;

public class AdaptingWrappingCollectionField extends
		AbstractWrapCollectionField {

	public AdaptingWrappingCollectionField(ClassOutlineImpl classOutline,
			CPropertyInfo property, CPropertyInfo wrappedProperty,
			CPropertyInfo wrappingProperty) {
		super(classOutline, wrappingProperty, wrappedProperty, wrappingProperty);
	}

	@Override
	protected void fix(JBlock body) {

		body._if(wrappingPropertyField.eq(JExpr._null()))
				._then()
				.assign(wrappingPropertyField,
						JExpr._new(codeModel.ref(ArrayList.class).narrow(
								wrappingPropertyExposedType.boxify())));

		final JClass utilsClass = codeModel.ref(TransformUtils.class);
		//
		// (wrappedProperty instanceof CReferencePropertyInfo &&
		// ((CReferencePropertyInfo) wrappedProperty)
		// .isMixed()) ? codeModel.ref(MixedItemUtils.class) : codeModel
		// .ref(ItemUtils.class);
		body._if(
				utilsClass.staticInvoke("shouldBeWrapped").arg(
						wrappedPropertyField))
				._then()
				.assign(wrappedPropertyField,

						utilsClass
								.staticInvoke("wrap")
								.arg(wrappedPropertyField)
								.arg(wrappingPropertyField)
								.arg(wrappingProperty.getAdapter()
										.getAdapterClass(outline.parent())
										.dotclass()));
	}

	protected JFieldRef createField() {

		final JFieldVar field = outline.implClass.field(JMod.PROTECTED
				+ JMod.TRANSIENT,

		propertyListType, property.getName(false));
		// field.annotate(XmlTransient.class);
		return JExpr._this().ref(field);
	}

}
