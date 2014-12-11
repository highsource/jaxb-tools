/*
 * Copyright [2006] java.net
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 		http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */

package org.jvnet.jaxb2.maven2;

import java.net.URL;
import java.net.URLClassLoader;

public class ParentFirstClassLoader extends URLClassLoader {

	public ParentFirstClassLoader(URL[] urls) {
		super(urls);
	}

	public ParentFirstClassLoader(URL[] urls, ClassLoader parent) {
		super(urls, parent);
	}

	public void addURL(URL url) {
		super.addURL(url);
	}

	public Class<?> loadClass(String name) throws ClassNotFoundException {
		return loadClass(name, false);
	}

	protected Class<?> loadClass(String name, boolean resolve)
			throws ClassNotFoundException {

		// First, check if the class has already been loaded
		@SuppressWarnings("rawtypes")
		Class c = findLoadedClass(name);

		// if not loaded, search the system class loader
		if (c == null) {
			try {
				c = getSystemClassLoader().loadClass(name);
			} catch (ClassNotFoundException cnfe) {
				// ignore
			}
		}

		// then the parent class loader
		if (c == null) {
			try {
				c = getParent().loadClass(name);
			} catch (ClassNotFoundException cnfe) {
			}
		}

		// then the child class loader
		if (c == null) {
			try {
				c = findClass(name);
			} catch (ClassNotFoundException cnfe) {
				throw cnfe;
			}
		}

		if (resolve) {
			resolveClass(c);
		}
		return c;
	}

}