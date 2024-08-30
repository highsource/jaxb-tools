package org.jvnet.jaxb.util.tests;

import java.io.Externalizable;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvnet.jaxb.util.JClassUtils;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;

public class JClassUtilsTest {

	private JCodeModel codeModel = new JCodeModel();

	@Test
	public void correctlyChecksIsInstanceOf()
			throws JClassAlreadyExistsException {

		final JClass arrayList = codeModel.ref("java.util.ArrayList");
		Assertions.assertTrue(JClassUtils.isInstanceOf(arrayList, Collection.class));
		final JDefinedClass subArrayList = codeModel._class("SubArrayList");
		subArrayList._extends(arrayList);
		Assertions.assertTrue(JClassUtils.isInstanceOf(subArrayList,
				Collection.class));

		final JClass subArrayListOfObjects = subArrayList.narrow(Object.class);
		Assertions.assertTrue(JClassUtils.isInstanceOf(subArrayListOfObjects,
				Collection.class));

		final JDefinedClass subExternalizable = codeModel
				._class("SubExternalizable");
		subExternalizable._implements(Externalizable.class);
		Assertions.assertTrue(JClassUtils.isInstanceOf(subExternalizable,
				Externalizable.class));

		subArrayList._implements(subExternalizable);
		Assertions.assertTrue(JClassUtils.isInstanceOf(subArrayList,
				Externalizable.class));

		Assertions.assertFalse(JClassUtils.isInstanceOf(codeModel.NULL,
				Collection.class));

	}
}
