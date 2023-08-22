package org.jvnet.hyperjaxb3.xjc.generator.bean.field;

import com.sun.codemodel.JClass;
import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.generator.bean.field.FieldRenderer;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.outline.FieldOutline;

public final class UntypedListFieldRenderer implements FieldRenderer {

    private JClass coreList;

    public UntypedListFieldRenderer( JClass coreList ) {
        this.coreList = coreList;
    }
    
    public FieldOutline generate(ClassOutlineImpl context, CPropertyInfo prop) {
        return new UntypedSettableListField(context,prop,coreList);
    }
}
