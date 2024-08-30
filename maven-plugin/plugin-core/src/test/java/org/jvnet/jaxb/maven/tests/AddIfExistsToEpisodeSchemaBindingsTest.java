package org.jvnet.jaxb.maven.tests;

import java.io.InputStream;

import org.codehaus.plexus.util.IOUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvnet.jaxb.maven.RawXJCMojo;

public class AddIfExistsToEpisodeSchemaBindingsTest {

	@Test
	public void transformationResourceIsAccessible() {
		InputStream is = RawXJCMojo.class
				.getResourceAsStream(RawXJCMojo.ADD_IF_EXISTS_TO_EPISODE_SCHEMA_BINDINGS_TRANSFORMATION_RESOURCE_NAME);
		Assertions.assertNotNull(is);
		IOUtil.close(is);
	}
}
