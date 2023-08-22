package org.jpox.samples.employee;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jpox.samples.employee.model.Department;
import org.jpox.samples.employee.model.Employee;
import org.jpox.samples.employee.model.Project;

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

  public void testMain() throws Exception
  {
      Department art = new Department();
      art.setId("ART");
      art.setLocation("Hollywood");
      art.setName("Marketing");

      Department TI = new Department();
      TI.setId("TI");
      TI.setLocation("Silicon Valey");
      TI.setName("TI");

      Employee johnWayne = new Employee();
      johnWayne.setDepartment(art);
      johnWayne.setJob("Actor");
      johnWayne.setName("John Wayne");
      johnWayne.setSalary(3400);
      johnWayne.setHiredate(new Date(1907, 5, 26)); // John Wayne birthdate

      /*
       * McLintock Film Cattle baron, banker and model citizen George
       * McLintock's wife Katherine (Maureen O'Hara) returns after 2 years
       * seeking divorce and revenge. 1962. 2 hrs. 6 min. Color.
       */
      Project mcLintock = new Project();
      mcLintock.setId("McLintock");
      mcLintock.setName("McLintock 1962");

      johnWayne.addProject(mcLintock);

      /*
       * Rio Bravo Film Sheriff John. T. Chance and his drunk and crippled
       * deputies stand off an army of gunmen set on freeing one of their
       * sidekicks from their jail. Dean Martin, Walter Brennan, Ricky Nelson.
       * Directed by Howard Hawks. 1958. 2 hrs. 21 min. Color.
       */
      Project rioBravo = new Project();
      rioBravo.setId("Rio Bravo");
      rioBravo.setName("Rio Bravo 1958");

      johnWayne.addProject(rioBravo);

      Employee billGates = new Employee();
      billGates.setDepartment(TI);
      billGates.setJob("Developer");
      billGates.setName("Bill Gates");
      billGates.setSalary(15000);
      billGates.setHiredate(new Date(1955, 10, 28)); // Bill Gates birthdate

      /*
       * Project Windows
       */
      Project windows = new Project();
      windows.setId("windows");
      windows.setName("Windows");

      billGates.addProject(windows);

      /*
       * MS-DOS
       */
      Project msDos = new Project();
      msDos.setId("msdos");
      msDos.setName("MS-DOS");

      billGates.addProject(msDos);

      storeEmployee(new Employee[]{johnWayne, billGates});
  }
  
  private void storeEmployee(Employee[] emp)
  {
      PersistenceManager pm = getPersistenceManager();
      Transaction tx = pm.currentTransaction();
      tx.begin();
      pm.makePersistent(emp[0]);
      pm.makePersistent(emp[1]);
      tx.commit();
      pm.close();
      Set projects = emp[0].getProjects();
      emp[0].getName();
      projects.size();
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
