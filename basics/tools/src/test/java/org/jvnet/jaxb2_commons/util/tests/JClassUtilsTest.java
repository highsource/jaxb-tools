package org.jvnet.jaxb2_commons.util.tests;

import java.io.Externalizable;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.jvnet.jaxb2_commons.util.JClassUtils;

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
		Assert.assertTrue(JClassUtils.isInstanceOf(arrayList, Collection.class));
		final JDefinedClass subArrayList = codeModel._class("SubArrayList");
		subArrayList._extends(arrayList);
		Assert.assertTrue(JClassUtils.isInstanceOf(subArrayList,
				Collection.class));

		final JClass subArrayListOfObjects = subArrayList.narrow(Object.class);
		Assert.assertTrue(JClassUtils.isInstanceOf(subArrayListOfObjects,
				Collection.class));

		final JDefinedClass subExternalizable = codeModel
				._class("SubExternalizable");
		subExternalizable._implements(Externalizable.class);
		Assert.assertTrue(JClassUtils.isInstanceOf(subExternalizable,
				Externalizable.class));

		subArrayList._implements(subExternalizable);
		Assert.assertTrue(JClassUtils.isInstanceOf(subArrayList,
				Externalizable.class));

		Assert.assertFalse(JClassUtils.isInstanceOf(codeModel.NULL,
				Collection.class));

	}
}
