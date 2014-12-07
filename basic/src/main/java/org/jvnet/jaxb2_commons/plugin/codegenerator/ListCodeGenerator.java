package org.jvnet.jaxb2_commons.plugin.codegenerator;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.JAXBElement;

import org.apache.commons.lang3.Validate;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JType;

public class ListCodeGenerator<A extends Arguments<A>> extends
		AbstractCodeGenerator<A> {

	public ListCodeGenerator(CodeGenerator<A> codeGenerator,
			CodeGenerationImplementor<A> implementor) {
		super(codeGenerator, implementor);
	}

	@Override
	public void generate(JBlock block, JType type,
			Collection<JType> possibleTypes, boolean isAlwaysSet, A arguments) {
		Validate.isInstanceOf(JClass.class, type);
		final JClass _class = (JClass) type;

		final JClass jaxbElementClass = getCodeModel().ref(JAXBElement.class);
		final Set<JType> arrays = new HashSet<JType>();
		final Collection<JClass> jaxbElements = new HashSet<JClass>();
		final Set<JType> otherTypes = new HashSet<JType>();
		for (final JType possibleType : possibleTypes) {
			if (possibleType.isArray()) {
				arrays.add(possibleType);
			} else if (possibleType instanceof JClass
					&& jaxbElementClass
							.isAssignableFrom(((JClass) possibleType).erasure())) {
				jaxbElements.add((JClass) possibleType);
			} else {
				otherTypes.add(possibleType);
			}
		}
		// If list items are not arrays or JAXBElements, just delegate to the
		// hashCode of the list
		if (arrays.isEmpty() && jaxbElements.isEmpty()) {
			getImplementor().onObject(arguments, block, false);
		} else {
			final JClass elementType = getElementType(_class);

			block = arguments.ifHasSetValue(block, isAlwaysSet, true);

			final A iterator = arguments.iterator(block, elementType);

			// while(e1.hasNext() && e2.hasNext()) {
			final JBlock whileBlock = iterator._while(block);
			// E o1 = e1.next();
			// Object o2 = e2.next();
			final boolean isElementAlwaysSet = elementType.isPrimitive();
			getCodeGenerator().generate(whileBlock, elementType, possibleTypes,
					isElementAlwaysSet,
					iterator.element(whileBlock, elementType));
			// }
			// return !(e1.hasNext() || e2.hasNext());
		}
	}

	private JClass getElementType(final JClass _class) {
		final JClass elementType;
		if (_class.getTypeParameters().size() == 1) {
			elementType = _class.getTypeParameters().get(0);
		} else {
			elementType = getCodeModel().ref(Object.class);
		}
		return elementType;
	}

}
