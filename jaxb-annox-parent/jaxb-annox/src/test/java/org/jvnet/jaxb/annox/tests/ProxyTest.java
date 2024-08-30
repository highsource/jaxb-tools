package org.jvnet.jaxb.annox.tests;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProxyTest {

    @Test
    public void testProxy() throws Exception {


        final InvocationHandler handler = new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return "a";
            }
        };

        final A a = (A) Proxy.newProxyInstance(
            A.class.getClassLoader(),
            new Class[]{A.class},
            handler);

        Assertions.assertEquals("a", a.stringField(), "Wrong value.");
    }

    public static interface A {
        public String stringField();
    }

}
