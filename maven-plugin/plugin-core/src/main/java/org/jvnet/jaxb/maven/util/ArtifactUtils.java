package org.jvnet.jaxb.maven.util;

import java.util.List;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Exclusion;

public class ArtifactUtils {

	private ArtifactUtils() {
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

		List<Exclusion> exclusions = dep.getExclusions();
		if (exclusions == null || exclusions.isEmpty()) {
			dep.setExclusions(def.getExclusions());
		}
	}
}
