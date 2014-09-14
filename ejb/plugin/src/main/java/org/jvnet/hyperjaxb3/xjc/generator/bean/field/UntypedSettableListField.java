package org.jvnet.hyperjaxb3.xjc.generator.bean.field;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JVar;
import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.generator.bean.MethodWriter;
import com.sun.tools.xjc.model.CPropertyInfo;

public class UntypedSettableListField extends UntypedListField {

	/** List getFIELD() method. */
	private JMethod $set;

	protected UntypedSettableListField(ClassOutlineImpl classOutline,
			CPropertyInfo propertyInfo, JClass coreList) {
		super(classOutline, propertyInfo, coreList);
	}

	@Override
	public void generateAccessors() {
		super.generateAccessors();

		final MethodWriter writer = outline.createMethodWriter();
		$set = writer.declareMethod(codeModel.VOID, "set" + prop.getName(true));
		JVar var = writer.addParameter(listT, prop.getName(false));
		writer.javadoc().append(prop.javadoc);
		JBlock block = $set.body();
		block.assign(JExpr._this().ref(field), var);
	}
}
