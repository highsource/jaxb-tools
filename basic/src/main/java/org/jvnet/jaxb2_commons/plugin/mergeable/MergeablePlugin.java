package org.jvnet.jaxb2_commons.plugin.mergeable;

import java.util.Arrays;
import java.util.Collection;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.lang.JAXBMergeStrategy;
import org.jvnet.jaxb2_commons.lang.MergeFrom2;
import org.jvnet.jaxb2_commons.lang.MergeStrategy2;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.jvnet.jaxb2_commons.locator.util.LocatorUtils;
import org.jvnet.jaxb2_commons.plugin.AbstractParameterizablePlugin;
import org.jvnet.jaxb2_commons.plugin.Customizations;
import org.jvnet.jaxb2_commons.plugin.CustomizedIgnoring;
import org.jvnet.jaxb2_commons.plugin.Ignoring;
import org.jvnet.jaxb2_commons.plugin.util.FieldOutlineUtils;
import org.jvnet.jaxb2_commons.plugin.util.StrategyClassUtils;
import org.jvnet.jaxb2_commons.util.ClassUtils;
import org.jvnet.jaxb2_commons.util.FieldAccessorFactory;
import org.jvnet.jaxb2_commons.util.PropertyFieldAccessorFactory;
import org.jvnet.jaxb2_commons.xjc.outline.FieldAccessorEx;
import org.xml.sax.ErrorHandler;

import com.sun.codemodel.JBlock;
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

public class MergeablePlugin extends AbstractParameterizablePlugin {

	@Override
	public String getOptionName() {
		return "Xmergeable";
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

	private String mergeStrategyClass = JAXBMergeStrategy.class.getName();

	public void setMergeStrategyClass(final String mergeStrategyClass) {
		this.mergeStrategyClass = mergeStrategyClass;
	}

	public String getMergeStrategyClass() {
		return mergeStrategyClass;
	}

	public JExpression createMergeStrategy(JCodeModel codeModel) {
		return StrategyClassUtils.createStrategyInstanceExpression(codeModel,
				MergeStrategy2.class, getMergeStrategyClass());
	}

	private Ignoring ignoring = new CustomizedIgnoring(
			org.jvnet.jaxb2_commons.plugin.mergeable.Customizations.IGNORED_ELEMENT_NAME,
			Customizations.IGNORED_ELEMENT_NAME,
			Customizations.GENERATED_ELEMENT_NAME);

	public Ignoring getIgnoring() {
		return ignoring;
	}

	public void setIgnoring(Ignoring ignoring) {
		this.ignoring = ignoring;
	}

	@Override
	public Collection<QName> getCustomizationElementNames() {
		return Arrays
				.asList(org.jvnet.jaxb2_commons.plugin.mergeable.Customizations.IGNORED_ELEMENT_NAME,
						Customizations.IGNORED_ELEMENT_NAME,
						Customizations.GENERATED_ELEMENT_NAME);
	}

	@Override
	public boolean run(Outline outline, Options opt, ErrorHandler errorHandler) {
		for (final ClassOutline classOutline : outline.getClasses())
			if (!getIgnoring().isIgnored(classOutline)) {
				processClassOutline(classOutline);
			}
		return true;
	}

	protected void processClassOutline(ClassOutline classOutline) {
		final JDefinedClass theClass = classOutline.implClass;
		ClassUtils
				._implements(theClass, theClass.owner().ref(MergeFrom2.class));

		@SuppressWarnings("unused")
		final JMethod mergeFrom$mergeFrom0 = generateMergeFrom$mergeFrom0(
				classOutline, theClass);
		@SuppressWarnings("unused")
		final JMethod mergeFrom$mergeFrom = generateMergeFrom$mergeFrom(
				classOutline, theClass);

		if (!classOutline.target.isAbstract()) {
			@SuppressWarnings("unused")
			final JMethod createCopy = generateMergeFrom$createNewInstance(
					classOutline, theClass);

		}
	}

	protected JMethod generateMergeFrom$mergeFrom0(
			final ClassOutline classOutline, final JDefinedClass theClass) {

		JCodeModel codeModel = theClass.owner();
		final JMethod mergeFrom$mergeFrom = theClass.method(JMod.PUBLIC,
				codeModel.VOID, "mergeFrom");
		mergeFrom$mergeFrom.annotate(Override.class);
		{
			final JVar left = mergeFrom$mergeFrom.param(Object.class, "left");
			final JVar right = mergeFrom$mergeFrom.param(Object.class, "right");
			final JBlock body = mergeFrom$mergeFrom.body();

			final JVar mergeStrategy = body.decl(JMod.FINAL,
					codeModel.ref(MergeStrategy2.class), "strategy",
					createMergeStrategy(codeModel));

			body.invoke("mergeFrom").arg(JExpr._null()).arg(JExpr._null())
					.arg(left).arg(right).arg(mergeStrategy);
		}
		return mergeFrom$mergeFrom;
	}

	protected JMethod generateMergeFrom$mergeFrom(ClassOutline classOutline,
			final JDefinedClass theClass) {
		final JCodeModel codeModel = theClass.owner();

		final JMethod mergeFrom = theClass.method(JMod.PUBLIC, codeModel.VOID,
				"mergeFrom");
		mergeFrom.annotate(Override.class);
		{
			final JVar leftLocator = mergeFrom.param(ObjectLocator.class,
					"leftLocator");
			final JVar rightLocator = mergeFrom.param(ObjectLocator.class,
					"rightLocator");
			final JVar left = mergeFrom.param(Object.class, "left");
			final JVar right = mergeFrom.param(Object.class, "right");

			final JVar mergeStrategy = mergeFrom.param(MergeStrategy2.class,
					"strategy");

			final JBlock methodBody = mergeFrom.body();

			Boolean superClassImplementsMergeFrom = StrategyClassUtils
					.superClassImplements(classOutline, getIgnoring(),
							MergeFrom2.class);

			if (superClassImplementsMergeFrom == null) {

			} else if (superClassImplementsMergeFrom.booleanValue()) {
				methodBody.invoke(JExpr._super(), "mergeFrom").arg(leftLocator)
						.arg(rightLocator).arg(left).arg(right)
						.arg(mergeStrategy);
			} else {

			}

			final FieldOutline[] declaredFields = FieldOutlineUtils.filter(
					classOutline.getDeclaredFields(), getIgnoring());

			if (declaredFields.length > 0) {

				final JBlock body = methodBody._if(right._instanceof(theClass))
						._then();

				JVar target = body.decl(JMod.FINAL, theClass, "target",
						JExpr._this());
				JVar leftObject = body.decl(JMod.FINAL, theClass, "leftObject",
						JExpr.cast(theClass, left));
				JVar rightObject = body.decl(JMod.FINAL, theClass,
						"rightObject", JExpr.cast(theClass, right));
				for (final FieldOutline fieldOutline : declaredFields) {
					final FieldAccessorEx leftFieldAccessor = getFieldAccessorFactory()
							.createFieldAccessor(fieldOutline, leftObject);
					final FieldAccessorEx rightFieldAccessor = getFieldAccessorFactory()
							.createFieldAccessor(fieldOutline, rightObject);
					if (leftFieldAccessor.isConstant()
							|| rightFieldAccessor.isConstant()) {
						continue;
					}

					final JBlock block = body.block();

					final JExpression leftFieldHasSetValue = (leftFieldAccessor
							.isAlwaysSet() || leftFieldAccessor.hasSetValue() == null) ? JExpr.TRUE
							: leftFieldAccessor.hasSetValue();

					final JExpression rightFieldHasSetValue = (rightFieldAccessor
							.isAlwaysSet() || rightFieldAccessor.hasSetValue() == null) ? JExpr.TRUE
							: rightFieldAccessor.hasSetValue();

					final JVar shouldBeSet = block.decl(
							codeModel.ref(Boolean.class),
							fieldOutline.getPropertyInfo().getName(false)
									+ "ShouldBeMergedAndSet",
							mergeStrategy.invoke("shouldBeMergedAndSet")
									.arg(leftLocator).arg(rightLocator)
									.arg(leftFieldHasSetValue)
									.arg(rightFieldHasSetValue));

					final JConditional ifShouldBeSetConditional = block._if(JOp
							.eq(shouldBeSet, codeModel.ref(Boolean.class)
									.staticRef("TRUE")));

					final JBlock ifShouldBeSetBlock = ifShouldBeSetConditional
							._then();
					final JConditional ifShouldNotBeSetConditional = ifShouldBeSetConditional
							._elseif(JOp.eq(
									shouldBeSet,
									codeModel.ref(Boolean.class).staticRef(
											"FALSE")));
					final JBlock ifShouldBeUnsetBlock = ifShouldNotBeSetConditional
							._then();
					// final JBlock ifShouldBeIgnoredBlock =
					// ifShouldNotBeSetConditional
					// ._else();

					final JVar leftField = ifShouldBeSetBlock.decl(
							leftFieldAccessor.getType(),
							"lhs"
									+ fieldOutline.getPropertyInfo().getName(
											true));
					leftFieldAccessor.toRawValue(ifShouldBeSetBlock, leftField);
					final JVar rightField = ifShouldBeSetBlock.decl(
							rightFieldAccessor.getType(),
							"rhs"
									+ fieldOutline.getPropertyInfo().getName(
											true));

					rightFieldAccessor.toRawValue(ifShouldBeSetBlock,
							rightField);

					final JExpression leftFieldLocator = codeModel
							.ref(LocatorUtils.class).staticInvoke("property")
							.arg(leftLocator)
							.arg(fieldOutline.getPropertyInfo().getName(false))
							.arg(leftField);
					final JExpression rightFieldLocator = codeModel
							.ref(LocatorUtils.class).staticInvoke("property")
							.arg(rightLocator)
							.arg(fieldOutline.getPropertyInfo().getName(false))
							.arg(rightField);

					final FieldAccessorEx targetFieldAccessor = getFieldAccessorFactory()
							.createFieldAccessor(fieldOutline, target);
					final JExpression mergedValue = JExpr.cast(
							targetFieldAccessor.getType(),
							mergeStrategy.invoke("merge").arg(leftFieldLocator)
									.arg(rightFieldLocator).arg(leftField)
									.arg(rightField).arg(leftFieldHasSetValue)
									.arg(rightFieldHasSetValue));

					final JVar merged = ifShouldBeSetBlock.decl(
							rightFieldAccessor.getType(),
							"merged"
									+ fieldOutline.getPropertyInfo().getName(
											true), mergedValue);

					targetFieldAccessor.fromRawValue(
							ifShouldBeSetBlock,
							"unique"
									+ fieldOutline.getPropertyInfo().getName(
											true), merged);

					targetFieldAccessor.unsetValues(ifShouldBeUnsetBlock);
				}
			}
		}
		return mergeFrom;
	}

	protected JMethod generateMergeFrom$createNewInstance(
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
}
