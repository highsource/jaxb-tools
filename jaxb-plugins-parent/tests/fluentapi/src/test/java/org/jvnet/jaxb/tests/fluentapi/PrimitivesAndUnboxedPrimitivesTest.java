package org.jvnet.jaxb.tests.fluentapi;

import generated.Primitives;
import generated.UnboxedPrimitives;
import org.junit.Assert;
import org.junit.Test;

public class PrimitivesAndUnboxedPrimitivesTest {

    @Test
    public void testPrimitives() {
        Primitives p = new Primitives()
            .withBoolean(true)
            .withInt(1)
            .withLong(10L)
            .withDouble(20d)
            .withFloat(40.0f)
            .withByte((byte) 80)
            .withShort((short) 160);

        Assert.assertEquals(true, p.isBoolean());
        Assert.assertEquals(1, p.getInt());
        Assert.assertEquals(10L, p.getLong());
        Assert.assertEquals(20d, p.getDouble(), 0);
        Assert.assertEquals(40.0f, p.getFloat(), 0);
        Assert.assertEquals((byte) 80, p.getByte());
        Assert.assertEquals((short) 160, p.getShort());
    }

    @Test
    public void testUnboxedPrimitives() {
        UnboxedPrimitives u = new UnboxedPrimitives()
            .withUnboxedBoolean(Boolean.TRUE)
            .withUnboxedInt(Integer.valueOf(1))
            .withUnboxedLong(Long.valueOf(10))
            .withUnboxedDouble(Double.valueOf(20d))
            .withUnboxedFloat(Float.valueOf(40.0f))
            .withUnboxedByte(Byte.valueOf((byte) 80))
            .withUnboxedShort(Short.valueOf((short) 160));

        Assert.assertEquals(Boolean.TRUE, u.isUnboxedBoolean());
        Assert.assertEquals(Integer.valueOf(1), u.getUnboxedInt());
        Assert.assertEquals(Long.valueOf(10), u.getUnboxedLong());
        Assert.assertEquals(Double.valueOf(20d), u.getUnboxedDouble());
        Assert.assertEquals(Float.valueOf(40.0f), u.getUnboxedFloat());
        Assert.assertEquals(Byte.valueOf((byte) 80), u.getUnboxedByte());
        Assert.assertEquals(Short.valueOf((short) 160), u.getUnboxedShort());
    }
}
