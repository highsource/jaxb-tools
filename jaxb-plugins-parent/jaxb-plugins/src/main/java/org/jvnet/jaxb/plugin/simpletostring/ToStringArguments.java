package org.jvnet.jaxb.plugin.simpletostring;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JConditional;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;
import com.sun.tools.xjc.model.CCustomizations;
import com.sun.tools.xjc.outline.ClassOutline;
import org.apache.commons.lang3.Validate;
import org.jvnet.jaxb.plugin.codegenerator.Arguments;

import java.util.Collection;
import java.util.ListIterator;

public class ToStringArguments implements Arguments<ToStringArguments> {

    private final JCodeModel codeModel;
    private final ClassOutline classOutline;
    private final JVar buffer;
    private final String fieldName;
    private final JVar value;
    private final JExpression hasSetValue;
    private final CCustomizations customizations;

    public ToStringArguments(JCodeModel codeModel, ClassOutline classOutline, JVar buffer, String fieldName, JVar value, JExpression hasSetValue, CCustomizations customizations) {
        this.codeModel = Validate.notNull(codeModel);
        this.classOutline = Validate.notNull(classOutline);
        this.buffer = Validate.notNull(buffer);
        this.fieldName = Validate.notNull(fieldName);
        this.value = Validate.notNull(value);
        this.hasSetValue = Validate.notNull(hasSetValue);
        this.customizations = customizations;
    }

    private JCodeModel getCodeModel() {
        return codeModel;
    }

    public JVar buffer() {
        return buffer;
    }

    public ClassOutline classOutline() {
        return classOutline;
    }

    public String fieldName() {
        return fieldName;
    }

    public JVar value() {
        return value;
    }

    public JExpression hasSetValue() {
        return hasSetValue;
    }

    public CCustomizations customizations() {
        return customizations;
    }

    private ToStringArguments spawn(String fieldName, JVar value, JExpression hasSetValue, CCustomizations customizations) {
        return new ToStringArguments(getCodeModel(), classOutline(), buffer(), fieldName, value, hasSetValue, customizations);
    }

    public ToStringArguments property(JBlock block, String propertyName,
                                      String propertyMethod, JType declarablePropertyType,
                                      JType propertyType, Collection<JType> possiblePropertyTypes) {
        final JVar propertyValue = block.decl(JMod.FINAL,
            declarablePropertyType, value().name() + propertyName, value().invoke(propertyMethod));
        // We assume that primitive properties are always set
        boolean isAlwaysSet = propertyType.isPrimitive();
        final JExpression propertyHasSetValue = isAlwaysSet ? JExpr.TRUE : propertyValue.ne(JExpr._null());
        return spawn(propertyName, propertyValue, propertyHasSetValue, null);
    }

    public ToStringArguments iterator(JBlock block, JType elementType) {
        final JVar listIterator = block
            .decl(JMod.FINAL, getCodeModel().ref(ListIterator.class)
                    .narrow(elementType), value().name() + "ListIterator",
                value().invoke("listIterator"));

        return spawn(fieldName(), listIterator, JExpr.TRUE, customizations());
    }

    public ToStringArguments element(JBlock subBlock, JType elementType) {
        final JVar elementValue = subBlock.decl(JMod.FINAL, elementType,
            value().name() + "Element", value().invoke("next"));
        final boolean isElementAlwaysSet = elementType.isPrimitive();
        final JExpression elementHasSetValue = isElementAlwaysSet ? JExpr.TRUE
            : elementValue.ne(JExpr._null());
        return spawn(fieldName(), elementValue, elementHasSetValue, customizations());

    }

    public JExpression _instanceof(JType type) {
        return value()._instanceof(type);
    }

    public ToStringArguments cast(String suffix, JBlock block,
                                  JType jaxbElementType, boolean suppressWarnings) {
        final JVar castedValue = block.decl(JMod.FINAL, jaxbElementType,
            value().name() + suffix, JExpr.cast(jaxbElementType, value()));
        if (suppressWarnings) {
            castedValue.annotate(SuppressWarnings.class).param("value", "unchecked");
        }
        return spawn(fieldName(), castedValue, JExpr.TRUE, customizations());
    }

    public JBlock ifHasSetValue(JBlock block, boolean isAlwaysSet, boolean checkForNullRequired) {
        if (isAlwaysSet || !checkForNullRequired) {
            return block;
        } else {
            return block._if(hasSetValue())._then();
        }
    }

    public JConditional ifConditionHasSetValue(JBlock block, boolean isAlwaysSet, boolean checkForNullRequired) {
        if (isAlwaysSet || !checkForNullRequired) {
            return null;
        } else {
            return block._if(hasSetValue());
        }
    }

    public JBlock _while(JBlock block) {
        final JBlock subBlock = block._while(value().invoke("hasNext")).body();
        return subBlock;
    }

}
