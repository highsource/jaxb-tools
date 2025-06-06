package org.jvnet.jaxb2_commons.xjc.generator.concrete;

import java.util.Objects;
import org.jvnet.jaxb2_commons.util.FieldAccessorFactory;
import org.jvnet.jaxb2_commons.util.PropertyFieldAccessorFactory;
import org.jvnet.jaxb2_commons.xjc.generator.MPropertyOutlineGenerator;
import org.jvnet.jaxb2_commons.xjc.outline.MClassOutline;
import org.jvnet.jaxb2_commons.xjc.outline.MPropertyAccessorFactory;
import org.jvnet.jaxb2_commons.xjc.outline.MPropertyOutline;
import org.jvnet.jaxb2_commons.xjc.outline.concrete.CMPropertyAccessorFactory;
import org.jvnet.jaxb2_commons.xjc.outline.concrete.CMPropertyOutline;
import org.jvnet.jaxb2_commons.xml.bind.model.MModelInfo;
import org.jvnet.jaxb2_commons.xml.bind.model.MPropertyInfo;

import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.Outline;

public class CMPropertyOutlineGenerator implements MPropertyOutlineGenerator {

	private final Outline outline;

	private final CPropertyInfo propertyInfo;

	private final FieldAccessorFactory fieldAccessorFactory = PropertyFieldAccessorFactory.INSTANCE;

	public CMPropertyOutlineGenerator(Outline outline,
			CPropertyInfo propertyInfo) {
		Objects.requireNonNull(outline, "Outline must not be null.");
		Objects.requireNonNull(propertyInfo, "Property info must not be null.");
		this.outline = outline;
		this.propertyInfo = propertyInfo;
	}

	public MPropertyOutline generate(MClassOutline classOutline,
			MModelInfo<NType, NClass> modelInfo,
			MPropertyInfo<NType, NClass> propertyInfo) {

		final FieldOutline fieldOutline = outline.getField(this.propertyInfo);

		final MPropertyAccessorFactory propertyAccessorFactory = new CMPropertyAccessorFactory(
				this.fieldAccessorFactory, fieldOutline);
		return new CMPropertyOutline(classOutline, propertyInfo,
				propertyAccessorFactory);
	}

}
