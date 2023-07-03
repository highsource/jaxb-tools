package org.jvnet.jaxb2_commons.tests.simple_hashcode_equals_01.customer;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CustomerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		// generate the customer class first.
		Customer2 eclipse = new Customer2();
		Customer jaxb2 = new Customer();
		eclipse.setAddress("address");
		eclipse.setBlueEyes(true);
		eclipse.setFamilyName("familyName");
		eclipse.setGivenName("firstName");
		eclipse.getMiddleInitials().add("m");
		eclipse.setPostCode("pc");
		eclipse.setSingle(true);
		jaxb2.setAddress(eclipse.getAddress());
		jaxb2.setBlueEyes(eclipse.isBlueEyes());
		jaxb2.setFamilyName(eclipse.getFamilyName());
		jaxb2.setGivenName(eclipse.getGivenName());
		jaxb2.getMiddleInitials().addAll(eclipse.getMiddleInitials());
		jaxb2.setPostCode(eclipse.getPostCode());
		jaxb2.setSingle(eclipse.isSingle());

		assertEquals(eclipse.hashCode(), jaxb2.hashCode());
	}

}
