package com.example.customerservice.test;

/*
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.xjc.runtime.DataTypeAdapter;
import org.hisrc.hifaces20.testing.webappenvironment.WebAppEnvironment;
import org.hisrc.hifaces20.testing.webappenvironment.annotations.PropertiesWebAppEnvironmentConfig;
import org.hisrc.hifaces20.testing.webappenvironment.testing.junit4.WebAppEnvironmentRule;

import com.example.customerservice.model.Customer;
import com.example.customerservice.model.CustomerType;
import com.example.customerservice.service.CustomerService;

public class CustomerServiceIT {

	@Rule
	public MethodRule webAppEnvironmentRule = WebAppEnvironmentRule.INSTANCE;

	@PropertiesWebAppEnvironmentConfig("src/test/resources/test-web.properties")
	public WebAppEnvironment webAppEnvironment;

	@Test
	public void checkCustomerService() throws Exception {

		final JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
		jaxWsProxyFactoryBean.setServiceClass(CustomerService.class);
		jaxWsProxyFactoryBean.setAddress(webAppEnvironment.getBaseUrl()
				+ "/CustomerServicePort");

		final CustomerService customerService = (CustomerService) jaxWsProxyFactoryBean
				.create();

		final Customer originalCustomer = new Customer();

		// originalCustomer.setCustomerId(1);
		originalCustomer.setName("Scott Tiger");
		originalCustomer.getAddress().add("Hauptstr. 6");
		originalCustomer.getAddress().add("76133 Karlsruhe");
		originalCustomer.getAddress().add("Germany");
		originalCustomer.setNumOrders(15);
		originalCustomer.setRevenue(1234.56);
		originalCustomer.setTest(BigDecimal.valueOf(7890));
		originalCustomer.setBirthDate(DataTypeAdapter.parseDate("1970-01-01"));
		originalCustomer.setType(CustomerType.BUSINESS);

		final Integer customerId = customerService
				.updateCustomer(originalCustomer);

		assertNotNull(customerId);

		final Customer retrievedCustomer = customerService
				.getCustomerById(customerId);

		assertEquals(originalCustomer.getName(), retrievedCustomer.getName());
		assertEquals(originalCustomer.getAddress(), retrievedCustomer
				.getAddress());

		customerService.deleteCustomerById(retrievedCustomer.getCustomerId());

	}
}
*/
public class CustomerServiceIT {
	// FIXME
}
