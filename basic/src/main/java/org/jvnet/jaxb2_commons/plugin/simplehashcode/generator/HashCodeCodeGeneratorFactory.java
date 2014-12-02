package org.jvnet.jaxb2_commons.plugin.simplehashcode.generator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBElement;

import org.apache.commons.lang3.Validate;
import org.jvnet.jaxb2_commons.codemodel.JCMType;
import org.jvnet.jaxb2_commons.codemodel.JCMTypeFactory;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JType;

public class HashCodeCodeGeneratorFactory implements
		TypedHashCodeCodeGeneratorFactory {

	private final JCodeModel codeModel;
	private final JCMTypeFactory typeFactory = new JCMTypeFactory();
	// Known code generators
	private final Map<JCMType<?>, HashCodeCodeGenerator> codeGenerators = new LinkedHashMap<JCMType<?>, HashCodeCodeGenerator>();
	private final HashCodeCodeGenerator defaultCodeGenerator;

	private final int initial = 1;
	private final int multiplier = 31;

	public HashCodeCodeGeneratorFactory(JCodeModel codeModel) {

		this.codeModel = Validate.notNull(codeModel);
		addCodeGenerator(this.codeModel.BOOLEAN,
				new BooleanHashCodeCodeGenerator(this, this.codeModel));
		addCodeGenerator(this.codeModel.BYTE,
				new CastToIntHashCodeCodeGenerator(this, this.codeModel));
		addCodeGenerator(this.codeModel.SHORT,
				new CastToIntHashCodeCodeGenerator(this, this.codeModel));
		addCodeGenerator(this.codeModel.CHAR,
				new CastToIntHashCodeCodeGenerator(this, this.codeModel));
		addCodeGenerator(this.codeModel.INT, new IdentityHashCodeCodeGenerator(
				this, this.codeModel));
		addCodeGenerator(this.codeModel.FLOAT, new FloatHashCodeCodeGenerator(
				this, this.codeModel));
		addCodeGenerator(this.codeModel.LONG, new FloatHashCodeCodeGenerator(
				this, this.codeModel));
		addCodeGenerator(this.codeModel.DOUBLE,
				new DoubleHashCodeCodeGenerator(this, this.codeModel));

		addCodeGenerator(this.codeModel.BOOLEAN.array(),
				new ArrayHashCodeGenerator(this, this.codeModel));
		addCodeGenerator(this.codeModel.BYTE.array(),
				new ArrayHashCodeGenerator(this, this.codeModel));
		addCodeGenerator(this.codeModel.SHORT.array(),
				new ArrayHashCodeGenerator(this, this.codeModel));
		addCodeGenerator(this.codeModel.CHAR.array(),
				new ArrayHashCodeGenerator(this, this.codeModel));
		addCodeGenerator(this.codeModel.INT.array(),
				new ArrayHashCodeGenerator(this, this.codeModel));
		addCodeGenerator(this.codeModel.FLOAT.array(),
				new ArrayHashCodeGenerator(this, this.codeModel));
		addCodeGenerator(this.codeModel.LONG.array(),
				new ArrayHashCodeGenerator(this, this.codeModel));
		addCodeGenerator(this.codeModel.DOUBLE.array(),
				new ArrayHashCodeGenerator(this, this.codeModel));
		addCodeGenerator(this.codeModel.ref(Object.class).array(),
				new ArrayHashCodeGenerator(this, this.codeModel));

		addCodeGenerator(
				this.codeModel.ref(JAXBElement.class).narrow(Object.class),
				new JAXBElementHashCodeCodeGenerator(this, this.codeModel,
						this.typeFactory));

		addCodeGenerator(this.codeModel.ref(List.class).narrow(Object.class),
				new ListHashCodeCodeGenerator(this, this.codeModel));

		addCodeGenerator(this.codeModel.ref(Object.class),
				new ObjectHashCodeCodeGenerator(this, this.codeModel,
						this.typeFactory));
		defaultCodeGenerator = new ObjectHashCodeCodeGenerator(this,
				this.codeModel, this.typeFactory);
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

	@Override
	public int getInitial() {
		return initial;
	}

	@Override
	public int getMultiplier() {
		return multiplier;
	}

}
