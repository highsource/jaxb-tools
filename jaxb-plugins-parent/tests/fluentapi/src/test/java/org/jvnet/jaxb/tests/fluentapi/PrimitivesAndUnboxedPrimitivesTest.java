package org.jvnet.jaxb.tests.fluentapi;

import generated.Primitives;
import generated.UnboxedPrimitives;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

        Assertions.assertEquals(true, p.isBoolean());
        Assertions.assertEquals(1, p.getInt());
        Assertions.assertEquals(10L, p.getLong());
        Assertions.assertEquals(20d, p.getDouble(), 0);
        Assertions.assertEquals(40.0f, p.getFloat(), 0);
        Assertions.assertEquals((byte) 80, p.getByte());
        Assertions.assertEquals((short) 160, p.getShort());
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

        Assertions.assertEquals(Boolean.TRUE, u.isUnboxedBoolean());
        Assertions.assertEquals(Integer.valueOf(1), u.getUnboxedInt());
        Assertions.assertEquals(Long.valueOf(10), u.getUnboxedLong());
        Assertions.assertEquals(Double.valueOf(20d), u.getUnboxedDouble());
        Assertions.assertEquals(Float.valueOf(40.0f), u.getUnboxedFloat());
        Assertions.assertEquals(Byte.valueOf((byte) 80), u.getUnboxedByte());
        Assertions.assertEquals(Short.valueOf((short) 160), u.getUnboxedShort());
    }
}
