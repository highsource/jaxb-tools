package org.jvnet.jaxb.tests.mapinit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvnet.jaxb.example.undefined.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.WeakHashMap;

public class EmployeeUndefinedTest {

    @Test
    public void testEmployee() {
        Employee a = new Employee();
        Assertions.assertNotNull(a.getDataMap());
        Assertions.assertTrue(a.getDataMap() instanceof HashMap);
        Assertions.assertNotNull(a.getWeakDataMap());
        Assertions.assertTrue(a.getWeakDataMap() instanceof WeakHashMap);
        Assertions.assertNull(a.getIgnoredDataMap());
        Assertions.assertNull(a.getValue());
        Assertions.assertNotNull(a.getListValue());
        Assertions.assertTrue(a.getListValue() instanceof ArrayList);
    }
}
