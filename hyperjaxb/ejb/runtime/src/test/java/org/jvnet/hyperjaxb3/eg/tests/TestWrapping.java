/**
 * 
 */
package org.jvnet.hyperjaxb3.eg.tests;

import java.io.InputStream;
import java.util.Collection;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

import junit.framework.TestCase;

/**
 * @author bspies
 * Test using XmlElementWrapper with XmlAnyElement.
 */
public class TestWrapping extends TestCase 
{
	public void testWrapping() throws Exception {
		JAXBContext ctx = JAXBContext.newInstance(Job.class, Node.class, UserTask.class, AutoTask.class);
		InputStream is = TestWrapping.class.getResourceAsStream("test.xml");
		Unmarshaller unmarshaller = ctx.createUnmarshaller();
		Job job = (Job)unmarshaller.unmarshal(is);
		Collection<Node> nodes = job.getNodes();
		assertNotNull("Nodes are null!", nodes);
		assertTrue("There are no nodes!", nodes.size()>0);
		for(Node n : nodes) {
			System.out.println("Name: " + n.getName());
		}
	}
	
	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestWrapping.class);
	}
}
