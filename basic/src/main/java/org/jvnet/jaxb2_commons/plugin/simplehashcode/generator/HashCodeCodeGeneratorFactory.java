package org.jvnet.jaxb2_commons.plugin.simplehashcode.generator;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.Validate;
import org.jvnet.jaxb2_commons.codemodel.JCMType;
import org.jvnet.jaxb2_commons.codemodel.JCMTypeFactory;
import org.jvnet.jaxb2_commons.codemodel.generator.TypedCodeGeneratorFactory;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JType;

public class HashCodeCodeGeneratorFactory implements
		TypedCodeGeneratorFactory<HashCodeCodeGenerator> {

	private final JCodeModel codeModel;
	private final JCMTypeFactory typeFactory = new JCMTypeFactory();
	// Known code generators
	private final Map<JCMType<?>, HashCodeCodeGenerator> codeGenerators = new LinkedHashMap<JCMType<?>, HashCodeCodeGenerator>();
	private final HashCodeCodeGenerator defaultCodeGenerator;

	public HashCodeCodeGeneratorFactory(JCodeModel codeModel) {

		this.codeModel = Validate.notNull(codeModel);
		addCodeGenerator(this.codeModel.BOOLEAN,
				new BooleanHashCodeCodeGenerator(this.codeModel));
		addCodeGenerator(this.codeModel.BYTE, new ByteHashCodeCodeGenerator(
				this.codeModel));
		addCodeGenerator(this.codeModel.SHORT, new ShortHashCodeCodeGenerator(
				this.codeModel));
		addCodeGenerator(this.codeModel.CHAR, new CharHashCodeCodeGenerator(
				this.codeModel));
		addCodeGenerator(this.codeModel.INT, new IntHashCodeCodeGenerator(
				this.codeModel));
		addCodeGenerator(this.codeModel.FLOAT, new FloatHashCodeCodeGenerator(
				this.codeModel));
		addCodeGenerator(this.codeModel.LONG, new FloatHashCodeCodeGenerator(
				this.codeModel));
		addCodeGenerator(this.codeModel.DOUBLE,
				new DoubleHashCodeCodeGenerator(this.codeModel));
		// addCodeGenerator(this.codeModel.ref(JAXBElement.class),
		// new JAXBElementHashCodeCodeGenerator(codeModel, this));
		// TODO primitive arrays
		// TODO Collections/Lists
		// TODO JAXBElement
		addCodeGenerator(this.codeModel.ref(Object.class),
				new ObjectHashCodeCodeGenerator(this.codeModel));
		defaultCodeGenerator = new ObjectHashCodeCodeGenerator(this.codeModel);
	}

	private void addCodeGenerator(final JType type,
			HashCodeCodeGenerator hashCodeCodeGenerator) {
		final JCMType<?> factoredType = typeFactory.create(type);
		this.codeGenerators.put(factoredType, hashCodeCodeGenerator);
	}

	@Override
	public HashCodeCodeGenerator getCodeGenerator(JType type) {
		final JCMType<JType> factoredType = typeFactory.create(type);
		for (Entry<JCMType<?>, HashCodeCodeGenerator> entry : codeGenerators
				.entrySet()) {
			if (entry.getKey().matches(factoredType)) {
				return entry.getValue();
			}
		}
		return defaultCodeGenerator;
	}

}
