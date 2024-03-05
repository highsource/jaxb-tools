package org.jvnet.jaxb.tests.mapinit;

import org.junit.Assert;
import org.junit.Test;
import org.jvnet.jaxb.example.undefined.Employee;

import java.util.ArrayList;
import java.util.HashMap;

public class EmployeeUndefinedTest {

    @Test
    public void testEmployee() {
        Employee a = new Employee();
        Assert.assertNotNull(a.getDataMap());
        Assert.assertTrue(a.getDataMap() instanceof HashMap);
        Assert.assertNull(a.getIgnoredDataMap());
        Assert.assertNull(a.getValue());
        Assert.assertNotNull(a.getListValue());
        Assert.assertTrue(a.getListValue() instanceof ArrayList);
    }
}
