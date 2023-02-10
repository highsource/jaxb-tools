package org.jvnet.jaxb.tests.rnc;

import org.junit.Test;
import org.kohsuke.rngom.dt.builtin.BuiltinDatatypeLibraryFactory;
import org.relaxng.datatype.DatatypeLibraryFactory;
import org.relaxng.datatype.helpers.DatatypeLibraryLoader;

public class DatatypeLibraryLoaderTest {

	@Test
	public void testDatatypeLibrary()
	{
		final DatatypeLibraryFactory b = new BuiltinDatatypeLibraryFactory(new DatatypeLibraryLoader());
		b.createDatatypeLibrary("");
		b.createDatatypeLibrary("http://www.w3.org/2001/XMLSchema-datatypes");
		b.createDatatypeLibrary("http://www.w3.org/2001/XMLSchema-datatypes");
		
	}
}
