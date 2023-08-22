package org.jvnet.hyperjaxb3.eg.tests;

import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "job", namespace = "urn:test")
public class Job {
	private Collection<Node> nodes;

	@XmlElementWrapper(name = "tasks", namespace = "urn:test")
	@XmlElements( {
			@XmlElement(name = "usertask", namespace = "urn:test", type = UserTask.class),
			@XmlElement(name = "autotask", namespace = "urn:test", type = AutoTask.class) })
	public Collection<Node> getNodes() {
		return nodes;
	}

	public void setNodes(Collection<Node> nodes) {
		this.nodes = nodes;
	}
}
