package org.jvnet.hyperjaxb3.ejb.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * Roundtrip test case.
 *
 * @author Aleksei Valikov
 */
public abstract class AbstractEntityManagerTest {

  protected Log logger = LogFactory.getLog(getClass());

  protected Class lastTestClass;

  protected EntityManagerFactory entityManagerFactory;

  public EntityManagerFactory getEntityManagerFactory() {
    return entityManagerFactory;
  }

  public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
    this.entityManagerFactory = entityManagerFactory;
  }

  @BeforeEach
  public void setUp() throws Exception {
    final EntityManagerFactory emf = getEntityManagerFactory();
    if (emf == null || !emf.isOpen() || lastTestClass != getClass()) {
      setEntityManagerFactory(createEntityManagerFactory());
      lastTestClass = getClass();
    }
  }


  @AfterEach
  public void tearDown() throws Exception {
  }

  public String getPersistenceUnitName() {
    final Package _package = getClass().getPackage();
    final String name = _package.getName();
    if (name == null) {
      return "root";
    }
    else {
      return name;
    }
  }

  public Map getEntityManagerProperties() {
    return null;
  }

  protected EntityManagerFactory createEntityManagerFactory() {

    try {
      final Enumeration<URL> resources = getClass().getClassLoader().getResources(
          "META-INF/persistence.xml");
      while (resources.hasMoreElements()) {
        final URL resource = resources.nextElement();
        logger.debug("Detected [" + resource + "].");
      }

    }
    catch (IOException ignored) {

    }

    final Map properties = getEntityManagerFactoryProperties();

    if (properties == null) {
      return Persistence.createEntityManagerFactory(getPersistenceUnitName());
    }
    else {
      return Persistence.createEntityManagerFactory(getPersistenceUnitName(), properties);
    }
  }

  public Map getEntityManagerFactoryProperties() {

    try {
      final Enumeration<URL> resources = getClass().getClassLoader().getResources(
          getEntityManagerFactoryPropertiesResourceName());

      if (!resources.hasMoreElements()) {
        logger.debug("Entity manager factory properties are not set.");
        return null;

      }
      else {
        logger.debug("Loading entity manager factory properties.");
        final Properties properties = new Properties();
        while (resources.hasMoreElements()) {
          final URL resource = resources.nextElement();
          logger.debug("Loading entity manager factory properties from [" + resource + "].");

          if (resource == null) {
            return null;
          }
          else {
            InputStream is = null;
            try {
              is = resource.openStream();
              properties.load(is);
              return properties;
            }
            catch (IOException ex) {
              return null;
            }
            finally {
              if (is != null) {
                try {
                  is.close();
                }
                catch (IOException ex) {
                  // Ignore
                }
              }
            }
          }
        }
        return properties;
      }
    }
    catch (IOException ex) {
      return null;
    }
  }

  public String getEntityManagerFactoryPropertiesResourceName() {
    return "persistence.properties";
  }

  public EntityManager createEntityManager() {
    final Map properties = getEntityManagerProperties();
    final EntityManagerFactory emf = getEntityManagerFactory();
    if (properties == null) {
      return emf.createEntityManager();
    }
    else {
      return emf.createEntityManager(properties);
    }
  }
}
