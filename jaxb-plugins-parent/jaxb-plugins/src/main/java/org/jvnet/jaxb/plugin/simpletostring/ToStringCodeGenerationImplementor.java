package org.jvnet.jaxb.plugin.simpletostring;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JConditional;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMod;
import com.sun.tools.xjc.model.CCustomizations;
import com.sun.tools.xjc.model.CPluginCustomization;
import org.jvnet.jaxb.plugin.codegenerator.AbstractCodeGenerationImplementor;
import org.jvnet.jaxb.plugin.tostring.Customizations;
import org.jvnet.jaxb.plugin.tostring.DateFormatClass;
import org.jvnet.jaxb.util.CustomizationUtils;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class ToStringCodeGenerationImplementor extends
    AbstractCodeGenerationImplementor<ToStringArguments> {

    private static final JExpression EQ_EXPR = JExpr.lit("=");
    private static final JExpression NULL_EXPR = JExpr.lit("<null>");
    private static final JExpression MASKED_EXPR = JExpr.lit("****");
    public static final String FIELD_SEPARATOR = ", ";
    public static final Map<String, String> DATE_TIME_FORMATTER_BY_PATTERN = new TreeMap<>();
    public int dateTimeFormatterIndex = 0;
    private final String defaultDateFormatterRef;
    private final String defaultDateFormatterPattern;
    public ToStringCodeGenerationImplementor(JCodeModel codeModel, String defaultDateFormatterRef, String defaultDateFormatterPattern) {
        super(codeModel);
        this.defaultDateFormatterRef = defaultDateFormatterRef;
        this.defaultDateFormatterPattern = defaultDateFormatterPattern;
    }

    private void ifHasSetValueAppendToStringElseAppendNull(
        ToStringArguments arguments, JBlock block,
        JExpression valueToString, boolean isAlwaysSet,
        boolean checkForNullRequired) {
        block.invoke(arguments.buffer(), "append").arg(arguments.fieldName());
        block.invoke(arguments.buffer(), "append").arg(EQ_EXPR);

        JConditional conditionalHasSetValue = arguments.ifConditionHasSetValue(block, isAlwaysSet, checkForNullRequired);
        CCustomizations customizations = arguments.customizations();

        // date / temporal specific treatments
        if (arguments.value().type() instanceof JClass) {
            valueToString = handlePossibleDateField(arguments, valueToString);
        }
        boolean isMasked = false;
        CPluginCustomization maskedCustomization = customizations == null ? null
            : customizations.find(Customizations.MASKED_ELEMENT_NAME.getNamespaceURI(), Customizations.MASKED_ELEMENT_NAME.getLocalPart());

        if (maskedCustomization != null) {
            isMasked = true;
            maskedCustomization.markAsAcknowledged();
        }
        if (conditionalHasSetValue == null) {
            block.invoke(arguments.buffer(), "append").arg(isMasked ? MASKED_EXPR : valueToString);
        } else {
            conditionalHasSetValue._then().invoke(arguments.buffer(), "append").arg(isMasked ? MASKED_EXPR : valueToString);
            conditionalHasSetValue._else().invoke(arguments.buffer(), "append").arg(NULL_EXPR);
        }
    }

    private JExpression handlePossibleDateField(ToStringArguments arguments, JExpression valueToString) {
        boolean isDate = getCodeModel().ref(Date.class).isAssignableFrom((JClass) arguments.value().type());
        boolean isCalendar = getCodeModel().ref(Calendar.class).isAssignableFrom(((JClass) arguments.value().type()));
        boolean isXMLCalendar = getCodeModel().ref(XMLGregorianCalendar.class).isAssignableFrom(((JClass) arguments.value().type()));
        boolean isTemporal = getCodeModel().ref(Temporal.class).isAssignableFrom(((JClass) arguments.value().type()));
        if (isDate || isCalendar || isXMLCalendar || isTemporal) {
            CCustomizations customizations = arguments.customizations();
            CPluginCustomization formatDateCustomization = customizations == null ? null
                : customizations.find(Customizations.DATE_FORMAT_PATTERN.getNamespaceURI(), Customizations.DATE_FORMAT_PATTERN.getLocalPart());
            if (formatDateCustomization != null || defaultDateFormatterRef != null || defaultDateFormatterPattern != null) {
                JExpression defaultExpr = arguments.value();
                if (isXMLCalendar) {
                    defaultExpr = defaultExpr.invoke("toGregorianCalendar");
                }
                if (!isTemporal) {
                    defaultExpr = getCodeModel().ref(ZonedDateTime.class)
                        .staticInvoke("ofInstant")
                        .arg(defaultExpr.invoke("toInstant"))
                        .arg(getCodeModel().ref(ZoneId.class).staticInvoke("systemDefault"));
                }
                DateFormatClass dateFormatClass = formatDateCustomization == null ?
                    null : (DateFormatClass) CustomizationUtils.unmarshall(Customizations.getContext(), formatDateCustomization);
                String formatRef = dateFormatClass == null ? defaultDateFormatterRef : dateFormatClass.getFormatRef();
                String format = dateFormatClass == null ? defaultDateFormatterPattern : dateFormatClass.getFormat();
                if (formatRef != null) {
                    try {
                        // validate the ref
                        DateTimeFormatter.class.getField(formatRef);
                        valueToString = getCodeModel().ref(DateTimeFormatter.class).staticRef(formatRef)
                            .invoke("format").arg(defaultExpr);
                        if (formatDateCustomization != null) {
                            formatDateCustomization.markAsAcknowledged();
                        }
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    }
                } else if (format != null) {
                    // validate the pattern
                    DateTimeFormatter.ofPattern(format);
                    String staticFieldName = DATE_TIME_FORMATTER_BY_PATTERN.get(format);
                    if (staticFieldName == null) {
                        staticFieldName = "DATE_TIME_FORMATTER_" + dateTimeFormatterIndex++;
                        DATE_TIME_FORMATTER_BY_PATTERN.put(format, staticFieldName);
                    }
                    JFieldVar field = arguments.classOutline().ref.fields().get(staticFieldName);
                    if (field == null) {
                        field = arguments.classOutline().ref.field(JMod.STATIC | JMod.FINAL | JMod.PRIVATE,
                            getCodeModel().ref(DateTimeFormatter.class),
                            staticFieldName,
                            getCodeModel().ref(DateTimeFormatter.class).staticInvoke("ofPattern").arg(format));
                    }
                    valueToString = defaultExpr.invoke("format").arg(field);
                    if (formatDateCustomization != null) {
                        formatDateCustomization.markAsAcknowledged();
                    }
                }
            }
        }
        return valueToString;
    }

    @Override
    public void onArray(JBlock block, boolean isAlwaysSet, ToStringArguments arguments) {
        ifHasSetValueAppendToStringElseAppendNull(
            arguments,
            block,
            getCodeModel().ref(Arrays.class).staticInvoke("toString")
                .arg(arguments.value()), isAlwaysSet, true);
    }

    @Override
    public void onBoolean(ToStringArguments arguments, JBlock block, boolean isAlwaysSet) {
        ifHasSetValueAppendToStringElseAppendNull(arguments, block,
            arguments.value(), isAlwaysSet, true);
    }

    @Override
    public void onByte(ToStringArguments arguments, JBlock block, boolean isAlwaysSet) {
        ifHasSetValueAppendToStringElseAppendNull(arguments, block,
            arguments.value(), isAlwaysSet, true);
    }

    @Override
    public void onChar(ToStringArguments arguments, JBlock block, boolean isAlwaysSet) {
        ifHasSetValueAppendToStringElseAppendNull(arguments, block,
            arguments.value(), isAlwaysSet, true);
    }

    @Override
    public void onDouble(ToStringArguments arguments, JBlock block, boolean isAlwaysSet) {
        ifHasSetValueAppendToStringElseAppendNull(arguments, block,
            arguments.value(), isAlwaysSet, true);
    }

    @Override
    public void onFloat(ToStringArguments arguments, JBlock block, boolean isAlwaysSet) {
        ifHasSetValueAppendToStringElseAppendNull(arguments, block,
            arguments.value(), isAlwaysSet, true);
    }

    @Override
    public void onInt(ToStringArguments arguments, JBlock block,
                      boolean isAlwaysSet) {
        ifHasSetValueAppendToStringElseAppendNull(arguments, block,
            arguments.value(), isAlwaysSet, true);
    }

    @Override
    public void onLong(ToStringArguments arguments, JBlock block, boolean isAlwaysSet) {
        ifHasSetValueAppendToStringElseAppendNull(arguments, block,
            arguments.value(), isAlwaysSet, true);
    }

    @Override
    public void onShort(ToStringArguments arguments, JBlock block, boolean isAlwaysSet) {
        ifHasSetValueAppendToStringElseAppendNull(arguments, block,
            arguments.value(), isAlwaysSet, true);
    }

    @Override
    public void onObject(ToStringArguments arguments, JBlock block, boolean isAlwaysSet) {
        ifHasSetValueAppendToStringElseAppendNull(arguments, block,
            arguments.value(), isAlwaysSet, true);
    }
}
