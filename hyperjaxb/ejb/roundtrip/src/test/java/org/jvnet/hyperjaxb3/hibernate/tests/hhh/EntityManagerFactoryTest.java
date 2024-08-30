package org.jvnet.hyperjaxb3.hibernate.tests.hhh;

import java.io.IOException;
import java.util.Properties;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EntityManagerFactoryTest {

	@Test
	public void entityManagerFactoryCreated() throws IOException {
		final Properties properties = new Properties();
		properties.load(getClass().getClassLoader().getResourceAsStream(
				"persistence.properties"));
		final String persistenceUnitName = getClass().getPackage().getName();
		final EntityManagerFactory entityManagerFactory = Persistence
				.createEntityManagerFactory(persistenceUnitName, properties);
		Assertions.assertNotNull(entityManagerFactory);

	}

	public String getPersistenceUnitName() {
		final Package _package = getClass().getPackage();
		final String name = _package.getName();
		if (name == null) {
			return "root";
		} else {
			return name;
		}
	}
}
