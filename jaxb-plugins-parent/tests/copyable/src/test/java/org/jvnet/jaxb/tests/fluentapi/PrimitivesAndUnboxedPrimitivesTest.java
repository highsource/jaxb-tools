package org.jvnet.jaxb.tests.fluentapi;

import generated.Primitives;
import generated.UnboxedPrimitives;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PrimitivesAndUnboxedPrimitivesTest {

    @Test
    public void testPrimitives() {
        Primitives p1 = new Primitives();
        p1.setBoolean(true);
        p1.setInt(1);
        p1.setLong(10L);
        p1.setDouble(20d);
        p1.setFloat(40.0f);
        p1.setByte((byte) 80);
        p1.setShort((short) 160);

        Primitives p2 = new Primitives();
        p1.copyTo(p2);

        Assertions.assertTrue(p2.isBoolean());
        Assertions.assertEquals(1, p2.getInt());
        Assertions.assertEquals(10L, p2.getLong());
        Assertions.assertEquals(20d, p2.getDouble(), 0);
        Assertions.assertEquals(40.0f, p2.getFloat(), 0);
        Assertions.assertEquals((byte) 80, p2.getByte());
        Assertions.assertEquals((short) 160, p2.getShort());
    }

    @Test
    public void testUnboxedPrimitives() {
        UnboxedPrimitives u = new UnboxedPrimitives();
        u.setUnboxedBoolean(Boolean.TRUE);
        u.setUnboxedInt(Integer.valueOf(1));
        u.setUnboxedLong(Long.valueOf(10));
        u.setUnboxedDouble(Double.valueOf(20d));
        u.setUnboxedFloat(Float.valueOf(40.0f));
        u.setUnboxedByte(Byte.valueOf((byte) 80));
        u.setUnboxedShort(Short.valueOf((short) 160));

        UnboxedPrimitives u2 = new UnboxedPrimitives();
        u.copyTo(u2);

        Assertions.assertEquals(Boolean.TRUE, u2.isUnboxedBoolean());
        Assertions.assertEquals(Integer.valueOf(1), u2.getUnboxedInt());
        Assertions.assertEquals(Long.valueOf(10), u2.getUnboxedLong());
        Assertions.assertEquals(Double.valueOf(20d), u2.getUnboxedDouble());
        Assertions.assertEquals(Float.valueOf(40.0f), u2.getUnboxedFloat());
        Assertions.assertEquals(Byte.valueOf((byte) 80), u2.getUnboxedByte());
        Assertions.assertEquals(Short.valueOf((short) 160), u2.getUnboxedShort());
    }
}
