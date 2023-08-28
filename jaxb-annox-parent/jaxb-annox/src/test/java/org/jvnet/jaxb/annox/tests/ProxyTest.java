package org.jvnet.jaxb.annox.tests;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.Assert;
import junit.framework.TestCase;

public class ProxyTest extends TestCase {

  public void testProxy() throws Exception {


    final InvocationHandler handler = new InvocationHandler() {
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return "a";
      }
    };

    final A a = (A) Proxy.newProxyInstance(
        A.class.getClassLoader(),
        new Class[]{ A.class },
        handler);

    Assert.assertEquals("Wrong value.", "a", a.stringField());
  }

  public static interface A {
    public String stringField();
  }

}
