package org.jvnet.jaxb2_commons.plugin.simpleequals.generator;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBElement;

import org.apache.commons.lang3.Validate;
import org.jvnet.jaxb2_commons.codemodel.JCMType;
import org.jvnet.jaxb2_commons.codemodel.JCMTypeFactory;
import org.jvnet.jaxb2_commons.codemodel.generator.TypedCodeGeneratorFactory;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JType;

public class EqualsCodeGeneratorFactory implements
		TypedCodeGeneratorFactory<EqualsCodeGenerator> {

	private final JCodeModel codeModel;
	private final JCMTypeFactory typeFactory = new JCMTypeFactory();
	// Known code generators
	private final Map<JCMType<?>, EqualsCodeGenerator> codeGenerators = new LinkedHashMap<JCMType<?>, EqualsCodeGenerator>();
	private final EqualsCodeGenerator defaultCodeGenerator;

	public EqualsCodeGeneratorFactory(JCodeModel codeModel) {
		this.codeModel = Validate.notNull(codeModel);
		addCodeGenerator(this.codeModel.BOOLEAN,
				new PrimitiveEqualsCodeGenerator(this.codeModel));
		addCodeGenerator(this.codeModel.BYTE, new PrimitiveEqualsCodeGenerator(
				this.codeModel));
		addCodeGenerator(this.codeModel.SHORT,
				new PrimitiveEqualsCodeGenerator(this.codeModel));
		addCodeGenerator(this.codeModel.CHAR, new PrimitiveEqualsCodeGenerator(
				this.codeModel));
		addCodeGenerator(this.codeModel.INT, new PrimitiveEqualsCodeGenerator(
				this.codeModel));
		addCodeGenerator(this.codeModel.FLOAT, new FloatEqualsCodeGenerator(
				this.codeModel));
		addCodeGenerator(this.codeModel.LONG, new PrimitiveEqualsCodeGenerator(
				this.codeModel));
		addCodeGenerator(this.codeModel.DOUBLE, new FloatEqualsCodeGenerator(
				this.codeModel));
		addCodeGenerator(this.codeModel.ref(JAXBElement.class),
				new JAXBElementEqualsCodeGenerator(codeModel, this));
		// TODO primitive arrays
		// TODO Collections/Lists
		// TODO JAXBElement
		defaultCodeGenerator = new ObjectEqualsCodeGenerator(this.codeModel);
	}

	private void addCodeGenerator(final JType type,
			EqualsCodeGenerator equalsCodeGenerator) {
		final JCMType<?> factoredType = typeFactory.create(type);
		this.codeGenerators.put(factoredType, equalsCodeGenerator);
	}

	@Override
	public EqualsCodeGenerator getCodeGenerator(JType type) {
		final JCMType<JType> factoredType = typeFactory.create(type);
		for (Entry<JCMType<?>, EqualsCodeGenerator> entry : codeGenerators
				.entrySet()) {
			if (entry.getKey().matches(factoredType)) {
				return entry.getValue();
			}
		}
		return defaultCodeGenerator;
	}

}
