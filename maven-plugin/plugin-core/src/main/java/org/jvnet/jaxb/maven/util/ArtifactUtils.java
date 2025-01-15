package org.jvnet.jaxb.maven.util;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.metadata.ArtifactMetadataSource;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.artifact.resolver.ArtifactResolutionRequest;
import org.apache.maven.artifact.resolver.ArtifactResolutionResult;
import org.apache.maven.artifact.resolver.filter.ExclusionSetFilter;
import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.artifact.InvalidDependencyVersionException;
import org.apache.maven.project.artifact.MavenMetadataSource;
import org.apache.maven.repository.RepositorySystem;
import org.jvnet.jaxb.maven.util.CollectionUtils.Function;

public class ArtifactUtils {

	private ArtifactUtils() {

	}
    public static Collection<Artifact> resolveTransitively(
        final ArtifactFactory artifactFactory,
        final RepositorySystem artifactResolver,
        final ArtifactRepository localRepository,
        final ArtifactMetadataSource artifactMetadataSource,
        final Dependency[] dependencies, final MavenProject project)
            throws InvalidDependencyVersionException,
            ArtifactResolutionException, ArtifactNotFoundException {
        return resolveTransitively(
            artifactFactory, artifactResolver,
            localRepository, artifactMetadataSource,
            dependencies, project,
            new SystemStreamLog(),
            null);
    }

	public static Collection<Artifact> resolveTransitively(
			final ArtifactFactory artifactFactory,
			final RepositorySystem artifactResolver,
			final ArtifactRepository localRepository,
			final ArtifactMetadataSource artifactMetadataSource,
			final Dependency[] dependencies, final MavenProject project,
            final Log log,
            final String[] artifactExcludes)
			throws InvalidDependencyVersionException,
			ArtifactResolutionException, ArtifactNotFoundException {
		if (dependencies == null || dependencies.length == 0) {
			return Collections.emptyList();
		}

		final Set<Artifact> artifacts = MavenMetadataSource.createArtifacts(
				artifactFactory, Arrays.asList(dependencies), "runtime", null,
				project);

        ArtifactResolutionRequest request = new ArtifactResolutionRequest();
        request.setResolveTransitively(true);
        request.setResolveRoot(false);
        request.setArtifact(project.getArtifact());
        request.setArtifactDependencies(artifacts);
        if (artifactExcludes != null && artifactExcludes.length > 0) {
            // remove dependencies from resolution
            request.setCollectionFilter(new ExclusionSetFilter(artifactExcludes));
        }
        request.setRemoteRepositories(project.getRemoteArtifactRepositories());
        request.setLocalRepository(localRepository);

        final ArtifactResolutionResult artifactResolutionResult = artifactResolver.resolve(request);

        if (!artifactResolutionResult.isSuccess()) {
            if (artifactResolutionResult.hasMissingArtifacts()) {
                log.error("artifactResolutionResult has missing artifacts : " + artifactResolutionResult.getMissingArtifacts());
                throw new RuntimeException("Artifacts - missing artifacts");
            } else if (artifactResolutionResult.hasExceptions()) {
                log.error("artifactResolutionResult has exceptions : " + artifactResolutionResult.getExceptions());
                throw new RuntimeException("Artifacts - exceptions", artifactResolutionResult.getExceptions().get(0));
            } else {
                log.error("artifactResolutionResult status is not success");
                throw new RuntimeException("Artifacts - not in success");
            }
        }
        return artifactResolutionResult.getArtifacts();
	}

	public static Collection<Artifact> resolve(
			final ArtifactFactory artifactFactory,
			final RepositorySystem artifactResolver,
			final ArtifactRepository localRepository,
			final ArtifactMetadataSource artifactMetadataSource,
			final Dependency[] dependencies, final MavenProject project)
			throws InvalidDependencyVersionException,
			ArtifactResolutionException, ArtifactNotFoundException {
		if (dependencies == null || dependencies.length == 0) {
			return Collections.emptyList();
		}

		@SuppressWarnings("unchecked")
		final Set<Artifact> artifacts = MavenMetadataSource.createArtifacts(
				artifactFactory, Arrays.asList(dependencies), "runtime", null,
				project);

		for (Artifact artifact : artifacts) {
            ArtifactResolutionRequest request = new ArtifactResolutionRequest();
            request.setArtifact(artifact);
            request.setRemoteRepositories(project.getRemoteArtifactRepositories());
            request.setLocalRepository(localRepository);
			artifactResolver.resolve(request);
		}

		final Set<Artifact> resolvedArtifacts = artifacts;
		return resolvedArtifacts;
	}

	public static final Function<Artifact, File> GET_FILE = new Function<Artifact, File>() {
		public File eval(Artifact argument) {
			return argument.getFile();
		}
	};

	public static final Collection<File> getFiles(Collection<Artifact> artifacts) {
		return CollectionUtils.apply(artifacts, ArtifactUtils.GET_FILE);
	}

	public static void mergeDependencyWithDefaults(Dependency dep, Dependency def) {
		if (dep.getScope() == null && def.getScope() != null) {
			dep.setScope(def.getScope());
			dep.setSystemPath(def.getSystemPath());
		}

		if (dep.getVersion() == null && def.getVersion() != null) {
			dep.setVersion(def.getVersion());
		}

		if (dep.getClassifier() == null && def.getClassifier() != null) {
			dep.setClassifier(def.getClassifier());
		}

		if (dep.getType() == null && def.getType() != null) {
			dep.setType(def.getType());
		}

		@SuppressWarnings("rawtypes")
		List exclusions = dep.getExclusions();
		if (exclusions == null || exclusions.isEmpty()) {
			dep.setExclusions(def.getExclusions());
		}
	}
}
