package org.jvnet.hyperjaxb3.hibernate.tests.hhh;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Properties;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.junit.Test;

public class EntityManagerFactoryTest {

	@Test
	public void entityManagerFactoryCreated() throws IOException {
		final Properties properties = new Properties();
		properties.load(getClass().getClassLoader().getResourceAsStream(
				"persistence.properties"));
		final String persistenceUnitName = getClass().getPackage().getName();
		final EntityManagerFactory entityManagerFactory = Persistence
				.createEntityManagerFactory(persistenceUnitName, properties);
		assertNotNull(entityManagerFactory);

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
