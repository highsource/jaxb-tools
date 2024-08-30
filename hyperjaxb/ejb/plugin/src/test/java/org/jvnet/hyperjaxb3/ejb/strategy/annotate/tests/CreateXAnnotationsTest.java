package org.jvnet.hyperjaxb3.ejb.strategy.annotate.tests;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvnet.hyperjaxb3.ejb.strategy.annotate.CreateXAnnotations;

import jakarta.xml.ns.persistence.orm.AttributeOverride;
import jakarta.xml.ns.persistence.orm.Column;

public class CreateXAnnotationsTest {

	private final CreateXAnnotations createXAnnotations = new CreateXAnnotations();

	@Test
	public void createsAttributeOverrides()
	{
		AttributeOverride ao0 = new AttributeOverride();
		ao0.setName("a");
		ao0.setColumn(new Column());
		ao0.getColumn().setName("column_a");
		AttributeOverride ao1 = new AttributeOverride();
		ao1.setName("b");
		ao1.setColumn(new Column());
		ao1.getColumn().setName("column_b");
		List<AttributeOverride> aos = Arrays.asList(ao0, ao1);
		Assertions.assertNotNull(createXAnnotations.createAttributeOverrides(aos));
	}
}
