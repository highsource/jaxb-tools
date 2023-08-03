package org.hisrc.xml.bind.tests.dogs;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "listOfDogs")
public class Dogs {

	private List<JAXBElement<DogType>> dogs = new LinkedList<JAXBElement<DogType>>();

	@XmlElementWrapper(name = "dogs")
	@XmlElementRef(name = "dog")
	public List<JAXBElement<DogType>> getDogs() {
		return this.dogs;
	}

	@Override
	public String toString() {
		return "Dogs [dogs=" + dogs + "]";
	}
}