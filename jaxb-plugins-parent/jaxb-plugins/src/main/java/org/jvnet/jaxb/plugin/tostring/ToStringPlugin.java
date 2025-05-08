package org.jvnet.jaxb.plugin.tostring;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import javax.xml.namespace.QName;

import com.sun.codemodel.JFieldVar;
import com.sun.tools.xjc.outline.EnumOutline;
import org.jvnet.jaxb.lang.JAXBToStringStrategy;
import org.jvnet.jaxb.lang.ToString;
import org.jvnet.jaxb.lang.ToStringStrategy;
import org.jvnet.jaxb.locator.ObjectLocator;
import org.jvnet.jaxb.plugin.AbstractParameterizablePlugin;
import org.jvnet.jaxb.plugin.ComposedIgnoring;
import org.jvnet.jaxb.plugin.CustomizedIgnoring;
import org.jvnet.jaxb.plugin.Ignoring;
import org.jvnet.jaxb.plugin.util.FieldOutlineUtils;
import org.jvnet.jaxb.plugin.util.StrategyClassUtils;
import org.jvnet.jaxb.util.ClassUtils;
import org.jvnet.jaxb.util.FieldAccessorFactory;
import org.jvnet.jaxb.util.PropertyFieldAccessorFactory;
import org.jvnet.jaxb.xjc.outline.FieldAccessorEx;
import org.xml.sax.ErrorHandler;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JVar;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.Outline;

public class ToStringPlugin extends AbstractParameterizablePlugin {

	@Override
	public String getOptionName() {
		return "XtoString";
	}

	@Override
	public String getUsage() {
		return "TBD";
	}

	private FieldAccessorFactory fieldAccessorFactory = PropertyFieldAccessorFactory.INSTANCE;

	public FieldAccessorFactory getFieldAccessorFactory() {
		return fieldAccessorFactory;
	}

	public void setFieldAccessorFactory(
			FieldAccessorFactory fieldAccessorFactory) {
		this.fieldAccessorFactory = fieldAccessorFactory;
	}

	private String toStringStrategyClass = JAXBToStringStrategy.class.getName();;

	public void setToStringStrategyClass(String toStringStrategy) {
		this.toStringStrategyClass = toStringStrategy;
	}

	public String getToStringStrategyClass() {
		return toStringStrategyClass;
	}

    private boolean toStringEnums = false;

    public void setToStringEnums(boolean toStringEnums) {
        this.toStringEnums = toStringEnums;
    }

    public boolean isToStringEnums() {
        return toStringEnums;
    }

    public JExpression createToStringStrategy(JCodeModel codeModel) {
		return StrategyClassUtils.createStrategyInstanceExpression(codeModel,
				ToStringStrategy.class, getToStringStrategyClass());
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

	public Ignoring getIgnoring() {
		return ignoring;
	}

	public void setIgnoring(Ignoring ignoring) {
		this.ignoring = ignoring;
	}

	@Override
	public Collection<QName> getCustomizationElementNames() {
		return Arrays
				.asList(org.jvnet.jaxb.plugin.tostring.Customizations.IGNORED_ELEMENT_NAME,
				        org.jvnet.jaxb.plugin.tostring.Customizations.MASKED_ELEMENT_NAME,
				        org.jvnet.jaxb.plugin.tostring.LegacyCustomizations.IGNORED_ELEMENT_NAME,
				        org.jvnet.jaxb.plugin.Customizations.IGNORED_ELEMENT_NAME,
			            org.jvnet.jaxb.plugin.Customizations.GENERATED_ELEMENT_NAME,
			            org.jvnet.jaxb.plugin.LegacyCustomizations.IGNORED_ELEMENT_NAME,
			            org.jvnet.jaxb.plugin.LegacyCustomizations.GENERATED_ELEMENT_NAME);
	}

    @Override
    public boolean run(Outline outline, Options opt, ErrorHandler errorHandler) {
        for (final ClassOutline classOutline : outline.getClasses()) {
            if (!getIgnoring().isIgnored(classOutline)) {
                processClassOutline(classOutline);
            }
        }
        if (isToStringEnums()) {
            for (final EnumOutline enumOutline : outline.getEnums()) {
                if (!getIgnoring().isIgnored(enumOutline)) {
                    processEnumOutline(enumOutline);
                }
            }
        }
        return true;
    }

	protected void processClassOutline(ClassOutline classOutline) {
		final JDefinedClass theClass = classOutline.implClass;
		ClassUtils._implements(theClass, theClass.owner().ref(ToString.class));

		@SuppressWarnings("unused")
		final JMethod object$toString = generateObject$toString(theClass);
		@SuppressWarnings("unused")
		final JMethod toString$append = generateToString$append(theClass);
		@SuppressWarnings("unused")
		final JMethod toString$appendFields = generateToString$appendFields(classOutline, theClass);
	}

    protected void processEnumOutline(EnumOutline enumOutline) {
        final JDefinedClass theClass = enumOutline.clazz;
        ClassUtils._implements(theClass, theClass.owner().ref(ToString.class));

        @SuppressWarnings("unused")
        final JMethod object$toString = generateObject$toString(theClass);
        @SuppressWarnings("unused")
        final JMethod toString$append = generateToString$append(theClass);
        @SuppressWarnings("unused")
        final JMethod toString$appendFields = generateToString$appendFields(enumOutline, theClass);
    }

	protected JMethod generateObject$toString(final JDefinedClass theClass) {
		final JCodeModel codeModel = theClass.owner();
		final JMethod object$toString = theClass.method(JMod.PUBLIC,
				codeModel.ref(String.class), "toString");
		object$toString.annotate(Override.class);
		{
			final JBlock body = object$toString.body();

			final JVar toStringStrategy =

			body.decl(JMod.FINAL, codeModel.ref(ToStringStrategy.class),
					"strategy", createToStringStrategy(codeModel));

			final JVar buffer = body.decl(JMod.FINAL,
					codeModel.ref(StringBuilder.class), "buffer",
					JExpr._new(codeModel.ref(StringBuilder.class)));
			body.invoke("append").arg(JExpr._null()).arg(buffer)
					.arg(toStringStrategy);
			body._return(buffer.invoke("toString"));
		}
		return object$toString;
	}

	protected JMethod generateToString$append(final JDefinedClass theClass) {
		final JCodeModel codeModel = theClass.owner();
		final JMethod toString$append = theClass.method(JMod.PUBLIC,
				codeModel.ref(StringBuilder.class), "append");
		toString$append.annotate(Override.class);
		{

			final JVar locator = toString$append.param(ObjectLocator.class,
					"locator");
			final JVar buffer = toString$append.param(StringBuilder.class,
					"buffer");
			final JVar toStringStrategy = toString$append.param(
					ToStringStrategy.class, "strategy");

			final JBlock body = toString$append.body();

			body.invoke(toStringStrategy, "appendStart").arg(locator)
					.arg(JExpr._this()).arg(buffer);
			body.invoke("appendFields").arg(locator).arg(buffer)
					.arg(toStringStrategy);
			body.invoke(toStringStrategy, "appendEnd").arg(locator)
					.arg(JExpr._this()).arg(buffer);
			body._return(buffer);
		}
		return toString$append;
	}

	protected JMethod generateToString$appendFields(ClassOutline classOutline,
			final JDefinedClass theClass) {
		final JCodeModel codeModel = theClass.owner();

		final JMethod toString$appendFields = theClass.method(JMod.PUBLIC,
				codeModel.ref(StringBuilder.class), "appendFields");
		toString$appendFields.annotate(Override.class);
		{
			final JVar locator = toString$appendFields.param(
					ObjectLocator.class, "locator");
			final JVar buffer = toString$appendFields.param(
					StringBuilder.class, "buffer");
			final JVar toStringStrategy = toString$appendFields.param(
					ToStringStrategy.class, "strategy");
			final JBlock body = toString$appendFields.body();

			final Boolean superClassImplementsToString = StrategyClassUtils
					.superClassImplements(classOutline, ignoring,
							ToString.class);

			if (superClassImplementsToString == null) {
				// No superclass
			} else if (superClassImplementsToString.booleanValue()) {
				body.invoke(JExpr._super(), "appendFields").arg(locator)
						.arg(buffer).arg(toStringStrategy);
			} else {
				// Superclass does not implement ToString
			}

			final FieldOutline[] declaredFields = FieldOutlineUtils.filter(
					classOutline.getDeclaredFields(), getIgnoring());

			if (declaredFields.length > 0) {

				for (final FieldOutline fieldOutline : declaredFields) {
					final JBlock block = body.block();
					final FieldAccessorEx fieldAccessor = getFieldAccessorFactory()
							.createFieldAccessor(fieldOutline, JExpr._this());
					final JVar theValue = block.decl(
							fieldAccessor.getType(),
							"the"
									+ fieldOutline.getPropertyInfo().getName(
											true));

					final JExpression valueIsSet = (fieldAccessor.isAlwaysSet() || fieldAccessor
							.hasSetValue() == null) ? JExpr.TRUE
							: fieldAccessor.hasSetValue();

					fieldAccessor.toRawValue(block, theValue);

					block.invoke(toStringStrategy, "appendField")
							.arg(locator)
							.arg(JExpr._this())
							.arg(JExpr.lit(fieldOutline.getPropertyInfo()
									.getName(false))).arg(buffer).arg(theValue)
							.arg(valueIsSet);
				}
			}
			body._return(buffer);
		}
		return toString$appendFields;
	}

    protected JMethod generateToString$appendFields(EnumOutline enumOutline,
                                                    final JDefinedClass theClass) {
        final JCodeModel codeModel = theClass.owner();

        final JMethod toString$appendFields = theClass.method(JMod.PUBLIC,
            codeModel.ref(StringBuilder.class), "appendFields");
        {
            final JVar locator = toString$appendFields.param(
                ObjectLocator.class, "locator");
            final JVar buffer = toString$appendFields.param(
                StringBuilder.class, "buffer");
            final JVar toStringStrategy = toString$appendFields.param(
                ToStringStrategy.class, "strategy");
            final JBlock body = toString$appendFields.body();

            body._return(buffer);
        }
        return toString$appendFields;
    }
}
