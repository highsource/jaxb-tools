package org.jvnet.jaxb2_commons.plugin.fixjaxb1058;

import org.jvnet.jaxb2_commons.reflection.util.Accessor;
import org.jvnet.jaxb2_commons.reflection.util.FieldAccessor;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.Plugin;
import com.sun.tools.xjc.generator.bean.field.DummyListField;
import com.sun.tools.xjc.generator.bean.field.IsSetField;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.Outline;

public class FixJAXB1058Plugin extends Plugin {

	@Override
	public String getOptionName() {
		return "XfixJAXB1058";
	}

	@Override
	public String getUsage() {
		return "  -XfixJAXB1058 :  Fixes JAXB-1058 (see https://java.net/jira/browse/JAXB-1058)";
	}

	private Accessor<JMethod> DummyListField_$get;
	private Accessor<FieldOutline> IsSetField_core;
	private Accessor<JFieldVar> AbstractListField_field;
	private Accessor<JClass> AbstractListField_listT;
	private Accessor<JClass> DummyListField_coreList;

	@Override
	public boolean run(Outline outline, Options opt, ErrorHandler errorHandler)
			throws SAXException {
		try {
			DummyListField_$get = new FieldAccessor<JMethod>(
					DummyListField.class, "$get", JMethod.class);

			IsSetField_core = new FieldAccessor<FieldOutline>(IsSetField.class,
					"core", FieldOutline.class);
			AbstractListField_field = new FieldAccessor<JFieldVar>(
					DummyListField.class.getSuperclass(), "field",
					JFieldVar.class);
			AbstractListField_listT = new FieldAccessor<JClass>(
					DummyListField.class.getSuperclass(), "listT", JClass.class);
			DummyListField_coreList = new FieldAccessor<JClass>(
					DummyListField.class, "coreList",
					JClass.class);
		} catch (Exception ex) {
			throw new SAXException("Could not create field accessors. "
					+ "This plugin can not be used in this environment.", ex);
		}

		for (ClassOutline classOutline : outline.getClasses()) {
			for (FieldOutline fieldOutline : classOutline.getDeclaredFields()) {
				fixFieldOutline(fieldOutline);
			}
		}
		return false;
	}

	private void fixFieldOutline(FieldOutline fieldOutline) {
		if (fieldOutline instanceof DummyListField) {
			fixDummyListField((DummyListField) fieldOutline);
		} else if (fieldOutline instanceof IsSetField) {
			fixIsSetField((IsSetField) fieldOutline);
		}
	}

	private void fixDummyListField(DummyListField fieldOutline) {
		if (DummyListField_$get.get(fieldOutline) == null) {
			final JFieldVar field = AbstractListField_field.get(fieldOutline);
			final JType listT = AbstractListField_listT.get(fieldOutline);
			final JClass coreList = DummyListField_coreList
					.get(fieldOutline);
			final JMethod $get = fieldOutline.parent().implClass.method(
					JMod.PUBLIC, listT, "get" +
					fieldOutline.getPropertyInfo().getName(true));
			JBlock block = $get.body();
			block._if(field.eq(JExpr._null()))._then()
					.assign(field, JExpr._new(coreList));
			block._return(JExpr._this().ref(field));
			DummyListField_$get.set(fieldOutline, $get);
		}
	}

	private void fixIsSetField(IsSetField isSetField) {
		final FieldOutline core = IsSetField_core.get(isSetField);
		fixFieldOutline(core);
	}
}
