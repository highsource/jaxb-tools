package org.jvnet.jaxb.plugin.simpletostring;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;

import com.sun.codemodel.JType;
import com.sun.tools.xjc.BadCommandLineException;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CCustomizations;
import com.sun.tools.xjc.outline.Aspect;
import org.jvnet.jaxb.plugin.ComposedIgnoring;
import org.jvnet.jaxb.plugin.CustomizedIgnoring;
import org.jvnet.jaxb.plugin.Ignoring;
import org.jvnet.jaxb.plugin.codegenerator.AbstractCodeGeneratorPlugin;
import org.jvnet.jaxb.plugin.codegenerator.CodeGenerator;
import org.jvnet.jaxb.plugin.util.FieldOutlineUtils;
import org.jvnet.jaxb.plugin.util.StrategyClassUtils;
import org.jvnet.jaxb.util.CustomizationUtils;
import org.jvnet.jaxb.util.FieldAccessorFactory;
import org.jvnet.jaxb.util.FieldUtils;
import org.jvnet.jaxb.util.PropertyFieldAccessorFactory;
import org.jvnet.jaxb.xjc.outline.FieldAccessorEx;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JVar;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;

public class SimpleToStringPlugin extends
    AbstractCodeGeneratorPlugin<ToStringArguments> {
    private static final String TOSTRING_STYLE_PARAM = "-XsimpleToString:DefaultDateStyle=";
    private String defaultDateFormatterRef = null;
    private String defaultDateFormatterPattern = null;

    @Override
    public String getOptionName() {
        return "XsimpleToString";
    }

    @Override
    public String getUsage() {
        // TODO
        return "TBD";
    }

    private FieldAccessorFactory fieldAccessorFactory = PropertyFieldAccessorFactory.INSTANCE;

    public FieldAccessorFactory getFieldAccessorFactory() {
        return fieldAccessorFactory;
    }

    public void setFieldAccessorFactory(FieldAccessorFactory fieldAccessorFactory) {
        this.fieldAccessorFactory = fieldAccessorFactory;
    }

    @Override
    protected QName getSpecialIgnoredElementName() {
        return org.jvnet.jaxb.plugin.tostring.Customizations.IGNORED_ELEMENT_NAME;
    }

    private Ignoring ignoring = new ComposedIgnoring(
        logger,
        new CustomizedIgnoring(
            org.jvnet.jaxb.plugin.tostring.Customizations.IGNORED_ELEMENT_NAME,
            org.jvnet.jaxb.plugin.Customizations.IGNORED_ELEMENT_NAME,
            org.jvnet.jaxb.plugin.Customizations.GENERATED_ELEMENT_NAME),
        new CustomizedIgnoring(
            org.jvnet.jaxb.plugin.tostring.LegacyCustomizations.IGNORED_ELEMENT_NAME,
            org.jvnet.jaxb.plugin.LegacyCustomizations.IGNORED_ELEMENT_NAME,
            org.jvnet.jaxb.plugin.LegacyCustomizations.GENERATED_ELEMENT_NAME));

    @Override
    public Ignoring getIgnoring() {
        return ignoring;
    }

    @Override
    public void setIgnoring(Ignoring ignoring) {
        this.ignoring = ignoring;
    }

    @Override
    public Collection<QName> getCustomizationElementNames() {
        return Arrays.asList(
            org.jvnet.jaxb.plugin.tostring.Customizations.IGNORED_ELEMENT_NAME,
            org.jvnet.jaxb.plugin.tostring.Customizations.MASKED_ELEMENT_NAME,
            org.jvnet.jaxb.plugin.tostring.Customizations.DATE_FORMAT_PATTERN,
            org.jvnet.jaxb.plugin.tostring.LegacyCustomizations.IGNORED_ELEMENT_NAME,
            org.jvnet.jaxb.plugin.Customizations.IGNORED_ELEMENT_NAME,
            org.jvnet.jaxb.plugin.Customizations.GENERATED_ELEMENT_NAME,
            org.jvnet.jaxb.plugin.LegacyCustomizations.IGNORED_ELEMENT_NAME,
            org.jvnet.jaxb.plugin.LegacyCustomizations.GENERATED_ELEMENT_NAME);
    }

    @Override
    protected CodeGenerator<ToStringArguments> createCodeGenerator(JCodeModel codeModel) {
        return new ToStringCodeGenerator(codeModel, defaultDateFormatterRef, defaultDateFormatterPattern);
    }

    @Override
    protected void generate(ClassOutline classOutline, JDefinedClass theClass) {
        final JCodeModel codeModel = theClass.owner();
        final JMethod object$toString = theClass.method(JMod.PUBLIC,
            codeModel.ref(String.class), "toString");
        object$toString.annotate(Override.class);
        {
            final JBlock body = object$toString.body();

            final JVar buffer = body.decl(JMod.FINAL,
                codeModel.ref(StringBuilder.class), "buffer",
                JExpr._new(codeModel.ref(StringBuilder.class)));

            body.invoke(buffer, "append").arg(theClass.dotclass().invoke("getSimpleName"));
            body.invoke(buffer, "append").arg("@");
            body.invoke(buffer, "append").arg(codeModel.ref(Integer.class).staticInvoke("toHexString").arg(JExpr._this().invoke("hashCode")));
            body.invoke(buffer, "append").arg("[");

            List<FieldOutline> fields = FieldOutlineUtils.filterToList(classOutline.getDeclaredFields(), getIgnoring());

            appendFieldsIn(fields, classOutline, theClass, body, buffer);

            body.invoke(buffer, "append").arg("]");

            body._return(buffer.invoke("toString"));
        }
    }

    private void appendFieldsIn(List<FieldOutline> declaredFields, ClassOutline classOutline, JDefinedClass theClass, JBlock body, JVar buffer) {
        Boolean superClassNotIgnored = StrategyClassUtils.superClassNotIgnored(classOutline, ignoring);
        ClassOutline superClass = classOutline.getSuperClass();
        if (Boolean.TRUE.equals(superClassNotIgnored) && superClass != null) {
            final JBlock block = body.block();
            block.invoke(buffer, "append").arg(JExpr._super().invoke("toString"));
            if (declaredFields.size() > 0) {
                block.invoke(buffer, "append").arg(ToStringCodeGenerationImplementor.FIELD_SEPARATOR);
            }
        }

        final JCodeModel codeModel = theClass.owner();
        if (declaredFields.size() > 0) {
            Iterator<FieldOutline> fieldIterator = declaredFields.iterator();
            while (fieldIterator.hasNext()) {
                final FieldOutline fieldOutline = fieldIterator.next();
                final String privateFieldName = fieldOutline.getPropertyInfo().getName(false);
                final JBlock block = body.block();
                final FieldAccessorEx fieldAccessor = getFieldAccessorFactory().createFieldAccessor(fieldOutline, JExpr._this());
                // declare var and affect it with field value
                final JVar theValue = block.decl(
                    fieldAccessor.getType(),
                    "the" + fieldOutline.getPropertyInfo().getName(true));
                fieldAccessor.toRawValue(block, theValue);
                final JType exposedType = fieldAccessor.getType();

                final Collection<JType> possibleTypes = FieldUtils.getPossibleTypes(fieldOutline, Aspect.EXPOSED);
                final boolean isAlwaysSet = fieldAccessor.isAlwaysSet();

                final JExpression hasSetValue = (fieldAccessor.isAlwaysSet() || fieldAccessor
                    .hasSetValue() == null) ? JExpr.TRUE
                    : fieldAccessor.hasSetValue();

                CCustomizations customizations = CustomizationUtils.getCustomizations(fieldOutline);
                getCodeGenerator().generate(
                    block,
                    exposedType,
                    possibleTypes,
                    isAlwaysSet,
                    new ToStringArguments(codeModel, classOutline, buffer,
                        privateFieldName, theValue, hasSetValue, customizations));
                if (fieldIterator.hasNext()) {
                    block.invoke(buffer, "append").arg(ToStringCodeGenerationImplementor.FIELD_SEPARATOR);
                }
            }
        }
    }

    @Override
    public int parseArgument(Options opt, String[] args, int i)
        throws BadCommandLineException
    {
        // eg. -XsimpleToString:DefaultDateStyle=SIMPLE_STYLE
        String arg = args[i].trim();

        if (arg.startsWith(TOSTRING_STYLE_PARAM)) {
            defaultDateFormatterRef = arg.substring(TOSTRING_STYLE_PARAM.length());
            try {
                DateTimeFormatter.class.getField(defaultDateFormatterRef);
                return 1;
            } catch (SecurityException e) {
                throw new BadCommandLineException(e.getMessage());
            } catch (NoSuchFieldException ignore) {
            }
            try {
                DateTimeFormatter.ofPattern(defaultDateFormatterRef);
                defaultDateFormatterPattern = defaultDateFormatterRef;
                defaultDateFormatterRef = null;
            } catch (IllegalArgumentException e) {
                throw new BadCommandLineException(e.getMessage());
            }
            return 1;
        }
        return 0;
    }
}
