package org.hisrc.xml.bind.tests.dogs;

import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

	public Dogs createDogs() {
		return new Dogs();
	}

	@XmlElementDecl(name = "dog")
	public Dog createDog(DogType value) {
		return new Dog(value);
	}

	@XmlElementDecl(name = "fido", substitutionHeadName = "dog", substitutionHeadNamespace = "")
	public Dog createFido(DogType value) {
		return new Dog("fido", value);
	}

	@XmlElementDecl(name = "barks", substitutionHeadName = "dog", substitutionHeadNamespace = "")
	public Dog createBarks(DogType value) {
		return new Dog("barks", value);
	}
}