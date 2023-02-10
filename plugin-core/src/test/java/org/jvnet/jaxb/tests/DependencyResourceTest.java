package org.jvnet.jaxb.tests;

import org.junit.Test;
import org.jvnet.jaxb.DependencyResource;

public class DependencyResourceTest {

	@Test
	public void correctlyParsesDepenencyResource() {
		
		{
			@SuppressWarnings("unused")
			DependencyResource dependencyResource = DependencyResource
					.valueOf("a:b");
		}
		{
			@SuppressWarnings("unused")
			DependencyResource dependencyResource = DependencyResource
					.valueOf("a:b:");
		}
		{
			@SuppressWarnings("unused")
			DependencyResource dependencyResource = DependencyResource
					.valueOf("a:b:c");
		}
		{
			@SuppressWarnings("unused")
			DependencyResource dependencyResource = DependencyResource
					.valueOf("a:b:c:");
		}
		{
			@SuppressWarnings("unused")
			DependencyResource dependencyResource = DependencyResource
					.valueOf("a:b:c:d");
		}
		{
			@SuppressWarnings("unused")
			DependencyResource dependencyResource = DependencyResource
					.valueOf("a:b:c:d:");
		}
		{
			@SuppressWarnings("unused")
			DependencyResource dependencyResource = DependencyResource
					.valueOf("a:b:c:d:e");
		}

		{
			@SuppressWarnings("unused")
			DependencyResource dependencyResource = DependencyResource
					.valueOf("a:b!/c");
		}
		{
			@SuppressWarnings("unused")
			DependencyResource dependencyResource = DependencyResource
					.valueOf("a:b:!/c");
		}
		{
			@SuppressWarnings("unused")
			DependencyResource dependencyResource = DependencyResource
					.valueOf("a:b:c!/d");
		}
		{
			@SuppressWarnings("unused")
			DependencyResource dependencyResource = DependencyResource
					.valueOf("a:b:c:!/d");
		}
		{
			@SuppressWarnings("unused")
			DependencyResource dependencyResource = DependencyResource
					.valueOf("a:b:c:d!/e");
		}
		{
			@SuppressWarnings("unused")
			DependencyResource dependencyResource = DependencyResource
					.valueOf("a:b::d!/e");
		}
		{
			@SuppressWarnings("unused")
			DependencyResource dependencyResource = DependencyResource
					.valueOf("a:b:c:d:!/e");
		}
		{
			@SuppressWarnings("unused")
			DependencyResource dependencyResource = DependencyResource
					.valueOf("a:b:c:d:e!/f");
		}

	}
}
