package org.jvnet.hyperjaxb3.ejb.tests.componentjpa2.tests;


import org.junit.Test;
import org.jvnet.hyperjaxb3.xml.bind.JAXBContextUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.*;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TestHyperJaxb {
    @Test
    public void testMapping() throws Exception {

        // Hibernate configuration
        Map<String, String> hibernateProperties = new HashMap<String, String>();
        hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.DerbyTenSevenDialect");
        hibernateProperties.put("hibernate.connection.driver_class", "org.apache.derby.jdbc.EmbeddedDriver");
        hibernateProperties.put("hibernate.connection.url", "jdbc:derby:target/test-database/database;create=true");
        hibernateProperties.put("hibernate.hbm2ddl.auto", "create");


        // initialise Hibernate
        EntityManagerFactory emf = createEntityManagerFactory(hibernateProperties);//, hibernateProperties);
        EntityManager em = emf.createEntityManager();

        // deserialize test XML document
        JobStream jaxbElement = (JobStream) JAXBContextUtils.unmarshal("org.jvnet.hyperjaxb3.ejb.tests.componentjpa2.tests", readFileAsString("src/test/resources/tests.xml"));
        //JobStream mails = (JobStream) JAXBElementUtils.getValue(jaxbElement);

        // persist object
        em.getTransaction().begin();
        em.persist(jaxbElement);
        em.getTransaction().commit();

        // retrieve persisted object
        JobStream persistedMails =  em.find(JobStream.class,1L);
        System.out.println("persistedObjects = " + persistedMails);

        em.close();
        emf.close();
    }

    private String readFileAsString(String filePath) throws IOException {
        File f = new File(filePath);
        FileInputStream fin = new FileInputStream(f);
        byte[] buffer = new byte[(int) f.length()];
        new DataInputStream(fin).readFully(buffer);
        fin.close();
        return new String(buffer, "UTF-8");
    }

    protected EntityManagerFactory createEntityManagerFactory(Map properties) {

        try {
            final Enumeration<URL> resources = getClass().getClassLoader().getResources(
                    "META-INF/persistence.xml");
            while (resources.hasMoreElements()) {
                final URL resource = resources.nextElement();
                System.out.println("Detected [" + resource.getPath() + "].");

            }

        }
        catch (IOException ignored) {

        }

        //final Map properties = getEntityManagerFactoryProperties();

        if (properties == null) {
            return Persistence.createEntityManagerFactory(getPersistenceUnitName());
        }
        else {
            return Persistence.createEntityManagerFactory(getPersistenceUnitName(), properties);
        }
    }

    private String getPersistenceUnitName() {
        return "org.jvnet.hyperjaxb3.ejb.tests.componentjpa2.tests";
    }

    public Properties getEntityManagerFactoryProperties() {

        try {
            final Enumeration<URL> resources = getClass().getClassLoader().getResources(
                    getEntityManagerFactoryPropertiesResourceName());

            if (!resources.hasMoreElements()) {
                System.out.println("Entity manager factory properties are not set.");
                return null;

            }
            else {
                System.out.println("Loading entity manager factory properties.");
                final Properties properties = new Properties();
                while (resources.hasMoreElements()) {
                    final URL resource = resources.nextElement();
                    System.out.println("Loading entity manager factory properties from [" + resource + "].");

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
}
