package org.jvnet.jaxb.plugin.copyable;

import java.util.Arrays;
import java.util.Collection;

import javax.xml.namespace.QName;

import com.sun.tools.xjc.model.CAttributePropertyInfo;
import org.jvnet.jaxb.lang.CopyStrategy;
import org.jvnet.jaxb.lang.CopyTo;
import org.jvnet.jaxb.lang.JAXBCopyStrategy;
import org.jvnet.jaxb.locator.ObjectLocator;
import org.jvnet.jaxb.locator.util.LocatorUtils;
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
import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JConditional;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JOp;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.Outline;

public class CopyablePlugin extends AbstractParameterizablePlugin {

	@Override
	public String getOptionName() {
		return "Xcopyable";
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

	private String copyStrategyClass = JAXBCopyStrategy.class.getName();

	public void setCopyStrategyClass(final String copyStrategy) {
		this.copyStrategyClass = copyStrategy;
	}

	public String getCopyStrategyClass() {
		return copyStrategyClass;
	}

	public JExpression createCopyStrategy(JCodeModel codeModel) {
		return StrategyClassUtils.createStrategyInstanceExpression(codeModel,
				CopyStrategy.class, getCopyStrategyClass());
	}

    private Ignoring ignoring = new ComposedIgnoring(
        logger,
        new CustomizedIgnoring(
            org.jvnet.jaxb.plugin.copyable.Customizations.IGNORED_ELEMENT_NAME,
            org.jvnet.jaxb.plugin.Customizations.IGNORED_ELEMENT_NAME,
            org.jvnet.jaxb.plugin.Customizations.GENERATED_ELEMENT_NAME),
        new CustomizedIgnoring(
            org.jvnet.jaxb.plugin.copyable.LegacyCustomizations.IGNORED_ELEMENT_NAME,
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
				.asList(org.jvnet.jaxb.plugin.copyable.Customizations.IGNORED_ELEMENT_NAME,
				        org.jvnet.jaxb.plugin.copyable.LegacyCustomizations.IGNORED_ELEMENT_NAME,
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
        return true;
    }

	protected void processClassOutline(ClassOutline classOutline) {
		final JDefinedClass theClass = classOutline.implClass;

		ClassUtils._implements(theClass, theClass.owner().ref(Cloneable.class));
		@SuppressWarnings("unused")
		final JMethod object$clone = generateObject$clone(classOutline,
				theClass);
		ClassUtils._implements(theClass, theClass.owner().ref(CopyTo.class));

		@SuppressWarnings("unused")
		final JMethod copyTo$copyTo = generateCopyTo$copyTo(classOutline,
				theClass);
		@SuppressWarnings("unused")
		final JMethod copyTo$copyTo1 = generateCopyTo$copyTo1(classOutline,
				theClass);
		// @SuppressWarnings("unused")
		// final JMethod copyable$copyTo = generateCopyable$CopyTo(classOutline,
		// theClass);
		// @SuppressWarnings("unused")
		// final JMethod copyable$copyTo1 =
		// generateCopyable$CopyTo1(classOutline,
		// theClass);

		// @SuppressWarnings("unused")
		// final JMethod copyFrom$copyFrom = generateCopyFrom$CopyFrom(
		// classOutline, theClass);
		// @SuppressWarnings("unused")
		// final JMethod copyable$copyFrom = generateCopyable$CopyFrom(
		// classOutline, theClass);
		if (!classOutline.target.isAbstract()) {
			@SuppressWarnings("unused")
			final JMethod createCopy = generateCopyTo$createNewInstance(
					classOutline, theClass);

		}
	}

	protected JMethod generateCopyTo$createNewInstance(
			final ClassOutline classOutline, final JDefinedClass theClass) {

		final JMethod existingMethod = theClass.getMethod("createNewInstance",
				new JType[0]);
		if (existingMethod == null) {

			final JMethod newMethod = theClass.method(JMod.PUBLIC, theClass
					.owner().ref(Object.class), "createNewInstance");
			newMethod.annotate(Override.class);
			{
				final JBlock body = newMethod.body();
				body._return(JExpr._new(theClass));
			}
			return newMethod;
		} else {
			return existingMethod;
		}
	}

	protected JMethod generateObject$clone(final ClassOutline classOutline,
			final JDefinedClass theClass) {

		final JMethod clone = theClass.method(JMod.PUBLIC, theClass.owner()
				.ref(Object.class), "clone");
		clone.annotate(Override.class);
		{
			final JBlock body = clone.body();
			body._return(JExpr.invoke("copyTo").arg(
					JExpr.invoke("createNewInstance")));
		}
		return clone;
	}

	protected JMethod generateCopyTo$copyTo(final ClassOutline classOutline,
			final JDefinedClass theClass) {

		final JCodeModel codeModel = theClass.owner();
		final JMethod copyTo$copyTo = theClass.method(JMod.PUBLIC,
				codeModel.ref(Object.class), "copyTo");
		copyTo$copyTo.annotate(Override.class);
		{
			final JVar target = copyTo$copyTo.param(Object.class, "target");

			final JBlock body = copyTo$copyTo.body();
			final JVar copyStrategy = body.decl(JMod.FINAL,
					codeModel.ref(CopyStrategy.class), "strategy",
					createCopyStrategy(codeModel));

			body._return(JExpr.invoke("copyTo").arg(JExpr._null()).arg(target)
					.arg(copyStrategy));
		}
		return copyTo$copyTo;
	}

	protected JMethod generateCopyTo$copyTo1(ClassOutline classOutline,
			final JDefinedClass theClass) {
		final JCodeModel codeModel = theClass.owner();
		ClassUtils._implements(theClass, codeModel.ref(CopyTo.class));

		final JMethod copyTo = theClass.method(JMod.PUBLIC,
				codeModel.ref(Object.class), "copyTo");
		copyTo.annotate(Override.class);
		{
			final JVar locator = copyTo.param(ObjectLocator.class, "locator");
			final JVar target = copyTo.param(Object.class, "target");
			final JVar copyStrategy = copyTo.param(CopyStrategy.class,
					"strategy");

			final JBlock body = copyTo.body();

			final JVar draftCopy;
			if (!classOutline.target.isAbstract()) {
				draftCopy = body.decl(
						JMod.FINAL,
						codeModel.ref(Object.class),
						"draftCopy",

						JOp.cond(JOp.eq(target, JExpr._null()),
								JExpr.invoke("createNewInstance"), target));
			} else {
				body._if(JExpr._null().eq(target))
						._then()
						._throw(JExpr
								._new(codeModel
										.ref(IllegalArgumentException.class))
								.arg("Target argument must not be null for abstract copyable classes."));
				draftCopy = target;
			}

			Boolean superClassImplementsCopyTo = StrategyClassUtils
					.superClassImplements(classOutline, getIgnoring(),
							CopyTo.class);

			if (superClassImplementsCopyTo == null) {

			} else if (superClassImplementsCopyTo.booleanValue()) {
				body.invoke(JExpr._super(), "copyTo").arg(locator)
						.arg(draftCopy).arg(copyStrategy);

			} else {

			}

			final FieldOutline[] declaredFields = FieldOutlineUtils.filter(
					classOutline.getDeclaredFields(), getIgnoring());

			if (declaredFields.length > 0) {

				final JBlock bl = body._if(draftCopy._instanceof(theClass))
						._then();

				final JVar copy = bl.decl(JMod.FINAL, theClass, "copy",
						JExpr.cast(theClass, draftCopy));

				for (final FieldOutline fieldOutline : declaredFields) {

					final FieldAccessorEx sourceFieldAccessor = getFieldAccessorFactory()
							.createFieldAccessor(fieldOutline, JExpr._this());
					final FieldAccessorEx copyFieldAccessor = getFieldAccessorFactory()
							.createFieldAccessor(fieldOutline, copy);

					if (sourceFieldAccessor.isConstant()) {
						continue;
					}

					final JBlock block = bl.block();

					final JExpression valueIsSet = (sourceFieldAccessor
							.isAlwaysSet() || sourceFieldAccessor.hasSetValue() == null) ? JExpr.TRUE
							: sourceFieldAccessor.hasSetValue();

					final JVar shouldBeCopied = block.decl(codeModel
							.ref(Boolean.class), fieldOutline.getPropertyInfo()
							.getName(false) + "ShouldBeCopiedAndSet",
							copyStrategy.invoke("shouldBeCopiedAndSet").arg(locator)
									.arg(valueIsSet));

					final JConditional ifShouldBeSetConditional = block._if(JOp
							.eq(shouldBeCopied, codeModel.ref(Boolean.class)
									.staticRef("TRUE")));

					final JBlock ifShouldBeSetBlock = ifShouldBeSetConditional
							._then();
					final JConditional ifShouldNotBeSetConditional = ifShouldBeSetConditional
							._elseif(JOp.eq(
									shouldBeCopied,
									codeModel.ref(Boolean.class).staticRef(
											"FALSE")));
					final JBlock ifShouldBeUnsetBlock = ifShouldNotBeSetConditional
							._then();

					final JType copyFieldType = sourceFieldAccessor.getType();
					final JVar sourceField = ifShouldBeSetBlock.decl(
							copyFieldType,
							"source"
									+ fieldOutline.getPropertyInfo().getName(
											true));
					sourceFieldAccessor.toRawValue(ifShouldBeSetBlock,
							sourceField);
					final JExpression builtCopy = JExpr
							.invoke(copyStrategy, "copy")
							.arg(codeModel
									.ref(LocatorUtils.class)
									.staticInvoke("property")
									.arg(locator)
									.arg(fieldOutline.getPropertyInfo()
											.getName(false)).arg(sourceField))
							.arg(sourceField).arg(valueIsSet);
					final JVar copyField = ifShouldBeSetBlock.decl(
							copyFieldType,
							"copy"
									+ fieldOutline.getPropertyInfo().getName(
											true),
							copyFieldType.isPrimitive() ? builtCopy :

							JExpr.cast(copyFieldType, builtCopy));
					if (copyFieldType instanceof JClass
							&& ((JClass) copyFieldType).isParameterized()) {
						copyField.annotate(SuppressWarnings.class).param(
								"value", "unchecked");
					}
					copyFieldAccessor.fromRawValue(ifShouldBeSetBlock, "unique"
							+ fieldOutline.getPropertyInfo().getName(true),
							copyField);

					copyFieldAccessor.unsetValues(ifShouldBeUnsetBlock);
				}
			}

			body._return(draftCopy);
		}
		return copyTo;
	}

}
