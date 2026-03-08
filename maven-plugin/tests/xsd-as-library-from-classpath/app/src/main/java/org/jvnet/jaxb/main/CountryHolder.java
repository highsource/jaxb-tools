package org.jvnet.jaxb.main;

import io.spring.guides.gs_producing_web_service.Country;

public class CountryHolder {

    public static Country createCountry() {
        return new Country();
    }

}
