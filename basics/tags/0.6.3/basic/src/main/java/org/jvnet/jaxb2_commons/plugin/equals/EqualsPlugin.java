package org.jvnet.jaxb2_commons.plugin.equals;

import java.util.Arrays;
import java.util.Collection;

import javax.xml.namespace.QName;

import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.EqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
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
import com.sun.codemodel.JVar;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.Outline;

public class EqualsPlugin extends AbstractParameterizablePlugin {

	@Override
	public String getOptionName() {
		return "Xequals";
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

	private String equalsStrategyClass = JAXBEqualsStrategy.class.getName();

	public void setEqualsStrategyClass(
			String equalsStrategyClass) {
		this.equalsStrategyClass = equalsStrategyClass;
	}

	public String getEqualsStrategyClass() {
		return equalsStrategyClass;
	}

	public JExpression createEqualsStrategy(JCodeModel codeModel) {
		return StrategyClassUtils.createStrategyInstanceExpression(codeModel,
				EqualsStrategy.class, getEqualsStrategyClass());
	}

	private Ignoring ignoring = new CustomizedIgnoring(
			org.jvnet.jaxb2_commons.plugin.equals.Customizations.IGNORED_ELEMENT_NAME,
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
				.asList(org.jvnet.jaxb2_commons.plugin.equals.Customizations.IGNORED_ELEMENT_NAME,
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
		ClassUtils._implements(theClass, theClass.owner().ref(Equals.class));

		// @SuppressWarnings("unused")
		// final JMethod equals0 = generateEquals$Equals0(classOutline,
		// theClass);
		@SuppressWarnings("unused")
		final JMethod equals = generateEquals$equals(classOutline, theClass);
		@SuppressWarnings("unused")
		final JMethod objectEquals = generateObject$equals(classOutline,
				theClass);
	}

	protected JMethod generateObject$equals(final ClassOutline classOutline,
			final JDefinedClass theClass) {
		final JCodeModel codeModel = theClass.owner();
		final JMethod objectEquals = theClass.method(JMod.PUBLIC,
				codeModel.BOOLEAN, "equals");
		{
			final JVar object = objectEquals.param(Object.class, "object");
			final JBlock body = objectEquals.body();
			final JVar equalsStrategy = body.decl(JMod.FINAL,
					codeModel.ref(EqualsStrategy.class), "strategy",
					createEqualsStrategy(codeModel));
			body._return(JExpr.invoke("equals").arg(JExpr._null())
					.arg(JExpr._null()).arg(object).arg(equalsStrategy));
		}
		return objectEquals;
	}

	// protected JMethod generateEquals$Equals0(final ClassOutline classOutline,
	// final JDefinedClass theClass) {
	// final JMethod equalsEquals0 = theClass.method(JMod.PUBLIC, theClass
	// .owner().BOOLEAN, "equals");
	// {
	// final JVar object = equalsEquals0.param(Object.class, "object");
	// final JVar equalsStrategy = equalsEquals0.param(
	// EqualsStrategy.class, "strategy");
	// final JBlock body = equalsEquals0.body();
	//
	// body._return(JExpr.invoke("equals").arg(JExpr._null()).arg(
	// JExpr._null()).arg(object).arg(equalsStrategy));
	// }
	// return equalsEquals0;
	// }

	protected JMethod generateEquals$equals(ClassOutline classOutline,
			final JDefinedClass theClass) {

		final JCodeModel codeModel = theClass.owner();
		final JMethod equals = theClass.method(JMod.PUBLIC, codeModel.BOOLEAN,
				"equals");
		{
			final JBlock body = equals.body();
			final JVar leftLocator = equals.param(ObjectLocator.class,
					"thisLocator");
			final JVar rightLocator = equals.param(ObjectLocator.class,
					"thatLocator");
			final JVar object = equals.param(Object.class, "object");
			final JVar equalsStrategy = equals.param(EqualsStrategy.class,
					"strategy");

			final JConditional ifNotInstanceof = body._if(JOp.not(object
					._instanceof(theClass)));
			ifNotInstanceof._then()._return(JExpr.FALSE);

			//
			body._if(JExpr._this().eq(object))._then()._return(JExpr.TRUE);

			final Boolean superClassImplementsEquals = StrategyClassUtils
					.superClassImplements(classOutline, getIgnoring(),
							Equals.class);

			if (superClassImplementsEquals == null) {
				// No superclass
			} else if (superClassImplementsEquals.booleanValue()) {
				body._if(
						JOp.not(JExpr._super().invoke("equals")
								.arg(leftLocator).arg(rightLocator).arg(object)
								.arg(equalsStrategy)))._then()
						._return(JExpr.FALSE);

			} else {
				body._if(JOp.not(JExpr._super().invoke("equals").arg(object)))
						._then()._return(JExpr.FALSE);
			}

			final JExpression _this = JExpr._this();

			final FieldOutline[] declaredFields = FieldOutlineUtils.filter(
					classOutline.getDeclaredFields(), getIgnoring());

			if (declaredFields.length > 0) {

				final JVar _that = body.decl(JMod.FINAL, theClass, "that",
						JExpr.cast(theClass, object));

				for (final FieldOutline fieldOutline : declaredFields) {

					final FieldAccessorEx leftFieldAccessor = getFieldAccessorFactory()
							.createFieldAccessor(fieldOutline, _this);
					final FieldAccessorEx rightFieldAccessor = getFieldAccessorFactory()
							.createFieldAccessor(fieldOutline, _that);

					if (leftFieldAccessor.isConstant()
							|| rightFieldAccessor.isConstant()) {
						continue;
					}

					final JBlock block = body.block();

					final String name = fieldOutline.getPropertyInfo().getName(
							true);

					final JVar lhsValue = block.decl(
							leftFieldAccessor.getType(), "lhs" + name);
					leftFieldAccessor.toRawValue(block, lhsValue);

					final JVar rhsValue = block.decl(
							rightFieldAccessor.getType(), "rhs" + name);
					rightFieldAccessor.toRawValue(block, rhsValue);

					final JExpression leftFieldLocator = codeModel
							.ref(LocatorUtils.class).staticInvoke("property")
							.arg(leftLocator)
							.arg(fieldOutline.getPropertyInfo().getName(false))
							.arg(lhsValue);
					final JExpression rightFieldLocator = codeModel
							.ref(LocatorUtils.class).staticInvoke("property")
							.arg(rightLocator)
							.arg(fieldOutline.getPropertyInfo().getName(false))
							.arg(rhsValue);
					block._if(
							JOp.not(JExpr.invoke(equalsStrategy, "equals")
									.arg(leftFieldLocator)
									.arg(rightFieldLocator).arg(lhsValue)
									.arg(rhsValue)))._then()
							._return(JExpr.FALSE);
				}
			}
			body._return(JExpr.TRUE);
		}
		return equals;
	}
}
