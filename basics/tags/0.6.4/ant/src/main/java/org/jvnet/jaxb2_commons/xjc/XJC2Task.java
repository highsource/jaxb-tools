package org.jvnet.jaxb2_commons.xjc;

import java.io.File;
import java.lang.reflect.Field;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.Path;

public class XJC2Task extends com.sun.tools.xjc.XJC2Task {

	@Override
	public void execute() throws BuildException {

		hack();
		super.execute();
	}

	protected void hack() {
		try {
			final Field declaredField = getClass().getSuperclass()
					.getDeclaredField("classpath");
			declaredField.setAccessible(true);
			final Path path = (Path) declaredField.get(this);
			if (path != null) {
				for (String pathElement : path.list()) {
					options.classpaths.add(new File(pathElement).toURI()
							.toURL());
				}
			}
		} catch (Exception ex) {
			throw new BuildException(ex);
		}
	}
}
