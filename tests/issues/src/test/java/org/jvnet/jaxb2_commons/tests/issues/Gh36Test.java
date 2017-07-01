package org.jvnet.jaxb2_commons.tests.issues;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonView;

public class Gh36Test {

	@Test
	public void getAttrMethodIsCorrectlyAnnotated() throws Exception {

		Method getAttrMethod = Gh36Type.class.getDeclaredMethod("getAttr");
		JsonView actualAnnotation = getAttrMethod.getAnnotation(JsonView.class);

		assertThat(actualAnnotation, is(notNullValue()));
		assertThat(actualAnnotation.value(), is(equalTo(new Class[]{Object.class})));
	}
}
