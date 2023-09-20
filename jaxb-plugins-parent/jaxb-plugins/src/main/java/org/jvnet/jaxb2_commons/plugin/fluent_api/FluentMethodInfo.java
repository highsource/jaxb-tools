/*
 * Copyright 2006 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jvnet.jaxb2_commons.plugin.fluent_api;

import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JMethod;

/**
 * Information used to generate a fluent API method.
 *
 * @author Hanson Char
 */
public class FluentMethodInfo {
	// Original method to used to derive the fluent API method.
	private final JMethod jmethod;
	// True if this method will be overriding a parent method; false otherwise.
	private final boolean isOverride;
	// Type of fluent API method to be generated.
	private final FluentMethodType fluentMethodType;

	public FluentMethodInfo(JMethod jmethod, FluentMethodType fluentMethodType, boolean isOverride)
	{
		this.jmethod = jmethod;
		this.fluentMethodType = fluentMethodType;
		this.isOverride = isOverride;
	}

	/**
	 * Creates a fluent API method in the given class.
	 *
	 * @param implClass the class to create fluent-api
	 */
	public void createFluentMethod(JDefinedClass implClass) {
		fluentMethodType.createFluentMethod(implClass, this);
	}

	/**
	 * Returns true if the fluent API method is one overriding a parent method.
	 *
	 * @return <code>true</code> if fluent api method is one overriding a parent method
	 */
	public boolean isOverride() {
		return isOverride;
	}

	/**
	 * Returns the original method for which a fluent API method will be generated.
	 *
	 * @return original JMethod for which a fluent-api method will be generated
	 */
	public JMethod getJmethod() {
		return jmethod;
	}
}
