package org.jvnet.hyperjaxb3.ejb.test.tests;

import javax.persistence.EntityManager;

import org.jvnet.hyperjaxb3.ejb.test.AbstractEntityManagerTest;

public class EntityManagerTest extends AbstractEntityManagerTest {

	public void testIt() throws Exception {
		final A a1 = new A();
		final B b1 = new B();
		a1.setId("A");
		a1.setB(b1);
		a1.setD("d1");
		b1.setId("B");
		b1.setC("c1");
		save(a1);

		final A a2 = new A();
		final B b2 = new B();
		a2.setId("A");
		a2.setB(b2);
		a2.setD("d2");
		b2.setId("B");
		b2.setC("c2");
		save(a2);

		final A a3 = load("A");

		assertEquals(a3.getD(), a2.getD());
		assertEquals(a3.getB().getC(), a2.getB().getC());
	}

	public void save(A a) {
		final EntityManager em = createEntityManager();
		em.getTransaction().begin();
		em.merge(a);
		em.getTransaction().commit();
		em.clear();
		em.close();
	}

	public A load(String id) {
		final EntityManager em = createEntityManager();
		final A a = em.find(A.class, id);
		em.close();
		return a;
	}
}
