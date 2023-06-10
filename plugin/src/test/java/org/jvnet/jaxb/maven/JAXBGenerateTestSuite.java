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

package org.jvnet.jaxb.maven;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Created by IntelliJ IDEA. User: Owner Date: Feb 8, 2006 Time: 12:20:24 AM To
 * change this template use File | Settings | File Templates.
 */
public class JAXBGenerateTestSuite
{
    public static Test suite()
    {
        TestSuite suite = new TestSuite();

        // Test all methods
        //	suite.addTestSuite(JAXBGenerateTest.class);

        return suite;
    }

    /** Runs the test suite using the textual runner. */
    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(suite());
    }
}
