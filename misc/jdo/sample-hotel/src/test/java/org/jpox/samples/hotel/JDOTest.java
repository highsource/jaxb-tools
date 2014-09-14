package org.jpox.samples.hotel;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JDOTest extends TestCase {
  protected Log logger = LogFactory.getLog(getClass());

  protected Class lastTestClass;

  protected PersistenceManagerFactory entityManagerFactory;

  public PersistenceManagerFactory getPersistenceManagerFactory() {
    return entityManagerFactory;
  }

  public void setPersistenceManagerFactory(PersistenceManagerFactory entityManagerFactory) {
    this.entityManagerFactory = entityManagerFactory;
  }

  public void setUp() throws Exception {
    super.setUp();
    final PersistenceManagerFactory emf = getPersistenceManagerFactory();
    if (emf == null || emf.isClosed() || lastTestClass != getClass()) {
      setPersistenceManagerFactory(createPersistenceManagerFactory());
      lastTestClass = getClass();
    }
  }

  protected PersistenceManagerFactory createPersistenceManagerFactory() {

    final Map properties = getPersistenceManagerFactoryProperties();

    return JDOHelper.getPersistenceManagerFactory(properties);
  }

  public PersistenceManager getPersistenceManager() {
    final PersistenceManagerFactory emf = getPersistenceManagerFactory();
    return emf.getPersistenceManager();
  }

  public void testIt() throws Exception {
    final Object id;
    {
      Transaction tx;
      PersistenceManager pm;
      pm = getPersistenceManager();
      tx = pm.currentTransaction();
      try {
        tx.begin();

        //create the hotel
        Hotel hotel = new Hotel("Ghasemi corp.", 100, "simple desc");
        Hotel hotel2 = new Hotel("Test ", 100, "complex desc");

        pm.makePersistent(hotel);
        pm.makePersistent(hotel2);

        tx.commit();
        id = pm.getObjectId(hotel);
      }
      finally {
        if (tx.isActive()) {
          tx.rollback();
        }
        pm.close();
      }
    }
    {
      Transaction tx = null;
      PersistenceManager pm = null;

      pm = getPersistenceManager();
      tx = pm.currentTransaction();
      try {
        tx.begin();

        Hotel hotel = (Hotel) pm.getObjectById(id);
        hotel.setDescription("changed");

        System.out.println("Persistent : " + JDOHelper.isPersistent(hotel));
        System.out.println("Dirty : " + JDOHelper.isDirty(hotel));
        System.out.println("New : " + JDOHelper.isNew(hotel));
        System.out.println("Transactional : " + JDOHelper.isTransactional(hotel));
        System.out.println("Deleted : " + JDOHelper.isDeleted(hotel));

        tx.commit();
      }
      finally {
        if (tx.isActive()) {
          tx.rollback();
        }
        pm.close();
      }
    }
  }

  public Map getPersistenceManagerFactoryProperties() {

    try {
      final Enumeration<URL> resources = getClass().getClassLoader().getResources(
          getPersistenceManagerFactoryPropertiesResourceName());

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

  public String getPersistenceManagerFactoryPropertiesResourceName() {
    return "jpox.properties";
  }

}
