package org.jvnet.jaxb.tests.enumtostring;

import generated.Console;
import generated.Model;
import generated.Vendor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConsoleTest {

    @Test
    public void testConsole() {
        Console c = new Console();
        c.setModel(Model.PS_5);
        c.setVendor(Vendor.SONY);

        String cToString = c.toString();
        Assertions.assertTrue(cToString.startsWith("generated.Console"));
        Assertions.assertTrue(cToString.endsWith("[model=PS5, vendor=SONY]"));
    }

    @Test
    public void testModel() {
        Model m = Model.PS_3;
        Assertions.assertEquals(Model.PS_3.value(), m.toString());
    }

    @Test
    public void testVendor() {
        Vendor v = Vendor.MICROSOFT;
        Assertions.assertEquals(Vendor.MICROSOFT.name(), v.toString());
    }
}
