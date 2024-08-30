/**
 *
 */
package org.jvnet.hyperjaxb3.eg.tests;

import java.io.InputStream;
import java.util.Collection;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author bspies
 * Test using XmlElementWrapper with XmlAnyElement.
 */
public class TestWrapping {
    @Test
	public void testWrapping() throws Exception {
		JAXBContext ctx = JAXBContext.newInstance(Job.class, Node.class, UserTask.class, AutoTask.class);
		InputStream is = TestWrapping.class.getResourceAsStream("test.xml");
		Unmarshaller unmarshaller = ctx.createUnmarshaller();
		Job job = (Job)unmarshaller.unmarshal(is);
		Collection<Node> nodes = job.getNodes();
		Assertions.assertNotNull(nodes, "Nodes are null!");
        Assertions.assertTrue(nodes.size() > 0, "There are no nodes!");
		for (Node n : nodes) {
			System.out.println("Name: " + n.getName());
		}
	}
}
