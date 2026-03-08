package org.jvnet.jaxb.test;

import io.spring.guides.gs_producing_web_service.Country;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvnet.jaxb.main.CountryHolder;

class CountryHolderTest {

    @Test
    void createCountryTest() {

        // when
        Country country = CountryHolder.createCountry();

        // then
        Assertions.assertNotNull(country);
    }

}
