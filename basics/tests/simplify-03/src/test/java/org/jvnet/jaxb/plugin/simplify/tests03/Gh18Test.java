package org.jvnet.jaxb.plugin.simplify.tests03;

import org.junit.Test;

public class Gh18Test {

	@Test
	public void compiles() {
		final Gh18 item = new Gh18();
		item.getA();
		item.getChildren();
        item.getChildrenLength();
		item.getFeet();
        item.getFeetLength();
		item.getFoos();
        item.getFoosLength();
	}
}
