//package org.jvnet.hyperjaxb3.ejb.tests.po;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.lang.reflect.Type;
//import java.util.Collections;
//
//import javax.xml.namespace.QName;
//
//import junit.framework.TestCase;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//import com.sun.xml.bind.v2.model.annotation.RuntimeInlineAnnotationReader;
//import com.sun.xml.bind.v2.model.core.ElementInfo;
//import com.sun.xml.bind.v2.model.core.Ref;
//import com.sun.xml.bind.v2.model.core.TypeInfoSet;
//import com.sun.xml.bind.v2.model.impl.ModelBuilder;
//import com.sun.xml.bind.v2.model.nav.Navigator;
//import com.sun.xml.bind.v2.runtime.IllegalAnnotationsException;
//
//public class ReflectionTest extends TestCase {
//
//	public static final Log logger = LogFactory.getLog(ReflectionTest.class);
//
//	public static TypeInfoSet<Type, Class, Field, Method> create(
//			Class... classes) throws IllegalAnnotationsException {
//
//		ModelBuilder<Type, Class, Field, Method> builder = new ModelBuilder<Type, Class, Field, Method>(
//				new RuntimeInlineAnnotationReader(), Navigator.REFLECTION,
//				Collections.<Class, Class> emptyMap(), null);
//		IllegalAnnotationsException.Builder errorHandler = new IllegalAnnotationsException.Builder();
//		builder.setErrorHandler(errorHandler);
//		for (Class c : classes)
//			builder.getTypeInfo(new Ref<Type, Class>(Navigator.REFLECTION
//					.use(c)));
//		errorHandler.check();
//
//		return builder.link();
//	}
//
//	public void testReflection() throws Exception {
//
//		TypeInfoSet<Type, Class, Field, Method> set = create(ObjectFactory.class);
//
//		for (ElementInfo<Type, Class> elementInfo : set.getAllElements()) {
//			final QName elementName = elementInfo.getElementName();
//			final Type type = elementInfo.getContentInMemoryType();
//			logger.debug("Element [" + elementName + "] is mapped onto ["
//					+ type + "].");
//		}
//
//	}
//
//}
