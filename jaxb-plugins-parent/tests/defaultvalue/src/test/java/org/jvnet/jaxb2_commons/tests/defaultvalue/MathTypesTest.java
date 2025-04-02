package org.jvnet.jaxb2_commons.tests.defaultvalue;

import generated.MathTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

public class MathTypesTest {

    @Test
    public void testMathTypes() {
        MathTypes m = new MathTypes();
        Assertions.assertEquals(new BigInteger("1000"), m.getBigint());
        Assertions.assertEquals(new BigDecimal("1.0"), m.getBigdec());
    }
}
