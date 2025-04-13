package org.jvnet.jaxb2_commons.tests.defaultvalue;

import generated.MathTypes;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

public class MathTypesTest {

    @Test
    public void testMathTypes() {
        MathTypes m = new MathTypes();
        Assert.assertEquals(new BigInteger("1000"), m.getBigint());
        Assert.assertEquals(new BigInteger("9223372036854775807000"), m.getBigbigint());
        Assert.assertEquals(new BigInteger("-1000"), m.getNegbigint());
        Assert.assertEquals(new BigDecimal("1.0"), m.getBigdec());
        Assert.assertEquals(new BigDecimal("9223372036854775807000.0123456789"), m.getBigbigdec());
        Assert.assertEquals(new BigDecimal("-1.0"), m.getNegbigdec());
    }
}
