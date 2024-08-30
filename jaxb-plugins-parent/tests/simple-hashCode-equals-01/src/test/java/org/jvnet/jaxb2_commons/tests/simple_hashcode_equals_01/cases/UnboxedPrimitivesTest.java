package org.jvnet.jaxb2_commons.tests.simple_hashcode_equals_01.cases;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UnboxedPrimitivesTest {

    @Test
    public void equalsPrimitives() {
        Assertions.assertEquals(new UnboxedPrimitives(), new UnboxedPrimitives());
    }

}
