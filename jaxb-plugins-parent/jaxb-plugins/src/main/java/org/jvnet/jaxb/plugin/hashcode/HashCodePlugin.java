package org.jvnet.jaxb.plugin.hashcode;

import java.util.Arrays;
import java.util.Collection;

import javax.xml.namespace.QName;

import org.jvnet.jaxb.lang.HashCode;
import org.jvnet.jaxb.lang.HashCodeStrategy;
import org.jvnet.jaxb.lang.JAXBHashCodeStrategy;
import org.jvnet.jaxb.locator.ObjectLocator;
import org.jvnet.jaxb.locator.util.LocatorUtils;
import org.jvnet.jaxb.plugin.AbstractParameterizablePlugin;
import org.jvnet.jaxb.plugin.Customizations;
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

public class HashCodePlugin extends AbstractParameterizablePlugin {

	@Override
	public String getOptionName() {
		return "XhashCode";
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

	private String hashCodeStrategyClass = JAXBHashCodeStrategy.class.getName();

	public void setHashCodeStrategyClass(String hashCodeStrategy) {
		this.hashCodeStrategyClass = hashCodeStrategy;
	}

	public String getHashCodeStrategyClass() {
		return hashCodeStrategyClass;
	}

	public JExpression createHashCodeStrategy(JCodeModel codeModel) {
		return StrategyClassUtils.createStrategyInstanceExpression(codeModel,
				HashCodeStrategy.class, getHashCodeStrategyClass());
	}

	private Ignoring ignoring = new CustomizedIgnoring(
			org.jvnet.jaxb.plugin.hashcode.Customizations.IGNORED_ELEMENT_NAME,
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
				.asList(org.jvnet.jaxb.plugin.hashcode.Customizations.IGNORED_ELEMENT_NAME,
						Customizations.IGNORED_ELEMENT_NAME,
						Customizations.GENERATED_ELEMENT_NAME);
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
		ClassUtils._implements(theClass, theClass.owner().ref(HashCode.class));

		@SuppressWarnings("unused")
		final JMethod hashCode$hashCode = generateHashCode$hashCode(
				classOutline, theClass);
		@SuppressWarnings("unused")
		final JMethod object$hashCode = generateObject$hashCode(classOutline,
				theClass);
	}

	protected JMethod generateObject$hashCode(final ClassOutline classOutline,
			final JDefinedClass theClass) {
		return generateObject$hashCode(theClass);
	}

	private JMethod generateObject$hashCode(final JDefinedClass theClass) {
		final JMethod object$hashCode = theClass.method(JMod.PUBLIC,
				theClass.owner().INT, "hashCode");
		object$hashCode.annotate(Override.class);
		{
			final JBlock body = object$hashCode.body();
			final JVar hashCodeStrategy = body.decl(JMod.FINAL, theClass
					.owner().ref(HashCodeStrategy.class), "strategy",
					createHashCodeStrategy(theClass.owner()));
			body._return(JExpr._this().invoke("hashCode").arg(JExpr._null())
					.arg(hashCodeStrategy));
		}
		return object$hashCode;
	}

	protected JMethod generateHashCode$hashCode(ClassOutline classOutline,
			final JDefinedClass theClass) {

		JCodeModel codeModel = theClass.owner();
		final JMethod hashCode$hashCode = theClass.method(JMod.PUBLIC,
				codeModel.INT, "hashCode");
		hashCode$hashCode.annotate(Override.class);
		{
			final JVar locator = hashCode$hashCode.param(ObjectLocator.class,
					"locator");
			final JVar hashCodeStrategy = hashCode$hashCode.param(
					HashCodeStrategy.class, "strategy");
			final JBlock body = hashCode$hashCode.body();

			final JExpression currentHashCodeExpression;

			final Boolean superClassImplementsHashCode = StrategyClassUtils
					.superClassImplements(classOutline, ignoring,
							HashCode.class);

			if (superClassImplementsHashCode == null) {
				currentHashCodeExpression = JExpr.lit(1);
			} else if (superClassImplementsHashCode.booleanValue()) {
				currentHashCodeExpression = JExpr._super().invoke("hashCode")
						.arg(locator).arg(hashCodeStrategy);
			} else {
				currentHashCodeExpression = JExpr._super().invoke("hashCode");
			}

			final JVar currentHashCode = body.decl(codeModel.INT,
					"currentHashCode", currentHashCodeExpression);

			final FieldOutline[] declaredFields = FieldOutlineUtils.filter(
					classOutline.getDeclaredFields(), getIgnoring());

			if (declaredFields.length > 0) {

				for (final FieldOutline fieldOutline : declaredFields) {
					final FieldAccessorEx fieldAccessor = getFieldAccessorFactory()
							.createFieldAccessor(fieldOutline, JExpr._this());
					if (fieldAccessor.isConstant()) {
						continue;
					}
					final JBlock block = body.block();

					final JVar theValue = block.decl(
							fieldAccessor.getType(),
							"the"
									+ fieldOutline.getPropertyInfo().getName(
											true));

					final JExpression valueIsSet = (fieldAccessor.isAlwaysSet() || fieldAccessor
							.hasSetValue() == null) ? JExpr.TRUE
							: fieldAccessor.hasSetValue();

					fieldAccessor.toRawValue(block, theValue);

					block.assign(
							currentHashCode,
							hashCodeStrategy
									.invoke("hashCode")
									.arg(codeModel
											.ref(LocatorUtils.class)
											.staticInvoke("property")
											.arg(locator)
											.arg(fieldOutline.getPropertyInfo()
													.getName(false))
											.arg(theValue))
									.arg(currentHashCode).arg(theValue)
									.arg(valueIsSet));
				}
			}
			body._return(currentHashCode);
		}
		return hashCode$hashCode;
	}
}
