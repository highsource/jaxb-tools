package org.jvnet.jaxb2.maven2.tests;

import org.junit.Test;
import org.jvnet.jaxb2.maven2.DependencyResource;

public class DependencyResourceTest {

	@Test
	public void correctlyParsesDepenencyResource() {
		
		{
			DependencyResource dependencyResource = DependencyResource
					.valueOf("a:b");
		}
		{
			DependencyResource dependencyResource = DependencyResource
					.valueOf("a:b:");
		}
		{
			DependencyResource dependencyResource = DependencyResource
					.valueOf("a:b:c");
		}
		{
			DependencyResource dependencyResource = DependencyResource
					.valueOf("a:b:c:");
		}
		{
			DependencyResource dependencyResource = DependencyResource
					.valueOf("a:b:c:d");
		}
		{
			DependencyResource dependencyResource = DependencyResource
					.valueOf("a:b:c:d:");
		}
		{
			DependencyResource dependencyResource = DependencyResource
					.valueOf("a:b:c:d:e");
		}

		{
			DependencyResource dependencyResource = DependencyResource
					.valueOf("a:b!/c");
		}
		{
			DependencyResource dependencyResource = DependencyResource
					.valueOf("a:b:!/c");
		}
		{
			DependencyResource dependencyResource = DependencyResource
					.valueOf("a:b:c!/d");
		}
		{
			DependencyResource dependencyResource = DependencyResource
					.valueOf("a:b:c:!/d");
		}
		{
			DependencyResource dependencyResource = DependencyResource
					.valueOf("a:b:c:d!/e");
		}
		{
			DependencyResource dependencyResource = DependencyResource
					.valueOf("a:b::d!/e");
		}
		{
			DependencyResource dependencyResource = DependencyResource
					.valueOf("a:b:c:d:!/e");
		}
		{
			DependencyResource dependencyResource = DependencyResource
					.valueOf("a:b:c:d:e!/f");
		}

	}
}
