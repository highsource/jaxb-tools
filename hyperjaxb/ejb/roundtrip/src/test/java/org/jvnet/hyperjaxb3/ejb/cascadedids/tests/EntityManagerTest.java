package org.jvnet.hyperjaxb3.ejb.cascadedids.tests;

import java.io.Serializable;

import jakarta.persistence.EntityManager;

import org.jvnet.hyperjaxb3.ejb.cascadedids.tests.Department.DepartmentId;
import org.jvnet.hyperjaxb3.ejb.test.AbstractEntityManagerTest;

public class EntityManagerTest extends AbstractEntityManagerTest {

	public void testIt() throws Exception {

		final Company company = new Company();
		company.setId(1);
		company.setName("Company");
		final Department department1 = new Department();
		department1.setDepartmentId(1001);
		department1.setCompany(company);
		department1.setName("Department 1");
		company.getDepartments().add(department1);
		save(company);

		final Company company_ = (Company) load(Company.class, 1L);
		final Department department1_ = (Department) load(Department.class,
				new DepartmentId(1, 1001));

		final Company department1Company_ = department1_.getCompany();
	}

	public void save(Object object) {
		final EntityManager em = createEntityManager();
		em.getTransaction().begin();
		em.merge(object);
		em.getTransaction().commit();
		em.clear();
		em.close();
	}

	public Object load(Class<?> theClass, Serializable id) {
		final EntityManager em = createEntityManager();
		final Object object = em.find(theClass, id);
		em.close();
		return object;
	}
}
