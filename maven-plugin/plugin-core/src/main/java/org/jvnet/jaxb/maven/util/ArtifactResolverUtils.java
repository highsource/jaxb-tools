package org.jvnet.jaxb.maven.util;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.collection.CollectRequest;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.graph.Exclusion;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResult;
import org.eclipse.aether.resolution.DependencyRequest;
import org.eclipse.aether.resolution.DependencyResult;
import org.eclipse.aether.util.artifact.JavaScopes;
import org.eclipse.aether.util.filter.DependencyFilterUtils;
import org.eclipse.aether.util.filter.ExclusionsDependencyFilter;
import org.jvnet.jaxb.maven.util.CollectionUtils.Function;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ArtifactResolverUtils {

	private ArtifactResolverUtils() {

	}

    public static Collection<Artifact> resolveTransitively(
            final RepositorySystem repositorySystem,
            final RepositorySystemSession repositorySystemSession,
            final MavenProject project,
            final org.apache.maven.model.Dependency[] dependencies,
            final Log log,
            final String[] artifactExcludes) {
        if (dependencies == null || dependencies.length == 0) {
            return Collections.emptyList();
        }

        CollectRequest collectRequest = new CollectRequest();
        collectRequest.setRootArtifact(
            new DefaultArtifact(
                project.getGroupId(),
                project.getArtifactId(),
                project.getPackaging(),
                project.getVersion()
            )
        );

        final List<Dependency> aetherDependencies = new ArrayList<>(dependencies.length);
        for (org.apache.maven.model.Dependency dependency : dependencies) {
            aetherDependencies.add(toAetherDependency(dependency));
        }
        collectRequest.setDependencies(aetherDependencies);
        collectRequest.setRepositories(project.getRemoteProjectRepositories());

        DependencyRequest request =
            new DependencyRequest(
                collectRequest,
                DependencyFilterUtils.andFilter(
                    new ExclusionsDependencyFilter(artifactExcludes == null ? Collections.emptyList() : List.of(artifactExcludes)),
                    DependencyFilterUtils.classpathFilter(JavaScopes.RUNTIME)
                )
            );

        try {
            DependencyResult result =
                repositorySystem.resolveDependencies(repositorySystemSession, request);
            List<Artifact> artifacts = new ArrayList<>();
            for (ArtifactResult artifactResult : result.getArtifactResults()) {
                if (artifactResult.getArtifact() != null) {
                    artifacts.add(artifactResult.getArtifact());
                }
            }
            return artifacts;
        } catch (Exception e) {
            log.error("Error resolving dependencies transitively: " + e.getMessage(), e);
            throw new RuntimeException("Error resolving dependencies transitively", e);
        }
    }

	public static Collection<Artifact> resolve(
			final RepositorySystem repositorySystem,
			final RepositorySystemSession repositorySystemSession,
            final MavenProject project,
			final org.apache.maven.model.Dependency[] dependencies) {
		if (dependencies == null || dependencies.length == 0) {
			return Collections.emptyList();
		}

		List<Artifact> resolvedArtifacts = new ArrayList<>();
		for (org.apache.maven.model.Dependency dependency : dependencies) {
			Artifact artifact = new DefaultArtifact(dependency.getGroupId(), dependency.getArtifactId(), dependency.getClassifier(), dependency.getType(), dependency.getVersion());
			ArtifactRequest artifactRequest = new ArtifactRequest();
			artifactRequest.setArtifact(artifact);
			artifactRequest.setRepositories(project.getRemoteProjectRepositories());
			try {
				ArtifactResult artifactResult = repositorySystem.resolveArtifact(repositorySystemSession, artifactRequest);
				resolvedArtifacts.add(artifactResult.getArtifact());
			} catch (Exception e) {
				throw new RuntimeException("Error resolving artifact: " + artifact, e);
			}
		}

		return resolvedArtifacts;
	}

	private static Dependency toAetherDependency(org.apache.maven.model.Dependency dependency) {
		Artifact artifact = new DefaultArtifact(dependency.getGroupId(), dependency.getArtifactId(), dependency.getClassifier(), dependency.getType(), dependency.getVersion());
		List<Exclusion> exclusions = null;
		if (dependency.getExclusions() != null) {
            exclusions = new ArrayList<>();
			for (org.apache.maven.model.Exclusion exclusion : dependency.getExclusions()) {
				exclusions.add(new Exclusion(exclusion.getGroupId(), exclusion.getArtifactId(), "*", "*"));
			}
		}
		return new Dependency(artifact, dependency.getScope() != null ? dependency.getScope() : JavaScopes.RUNTIME, dependency.isOptional(), exclusions);
	}

	public static final Function<Artifact, File> GET_FILE = new Function<Artifact, File>() {
		public File eval(Artifact argument) {
			return argument.getFile();
		}
	};

	public static final Collection<File> getFiles(Collection<Artifact> artifacts) {
		return CollectionUtils.apply(artifacts, ArtifactResolverUtils.GET_FILE);
	}
}
