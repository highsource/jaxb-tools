/*
 * Copyright [2006] java.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jvnet.hyperjaxb3.maven;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.maven.model.Resource;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

import com.sun.tools.xjc.Options;
import org.jvnet.jaxb.maven.XJCMojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES, requiresDependencyResolution = ResolutionScope.COMPILE, threadSafe = true)
public class Hyperjaxb3Mojo extends XJCMojo {

    // Maintain compatability with previous log package namespace
    private static final Logger logger = LoggerFactory.getLogger("org.jvnet.hyperjaxb3");
	/**
	 * Target directory for the generated mappings. If left empty, mappings are
	 * generated together with sources.
	 *
	 */
	@Parameter(property = "maven.hj3.target")
	public File target;

	/**
	 * Name of the roundtrip test case. If omitted, no roundtrip test case is
	 * generated.
	 *
	 */
	@Parameter(property = "maven.hj3.roundtripTestClassName")
	public String roundtripTestClassName;

	/**
	 * Patterns for files to be included as resources.
	 *
	 */
	@Parameter
	public String[] resourceIncludes = new String[] { "**/*.hbm.xml",
			"**/*.orm.xml", "**/*.cfg.xml", "META-INF/persistence.xml" };

	/**
	 * Persistence variant. Switches between various persistence
	 * implementations. Possible values are "hibernate" and "ejb-hibernate".
	 *
	 */
	@Parameter(property = "maven.hj3.variant", defaultValue = "ejb")
	public String variant = "ejb";

	/**
	 *
	 * Persistence unit name (EJB3 specific).
	 *
	 */
	@Parameter(property = "maven.hj3.persistenceUnitName")
	public String persistenceUnitName;

	/**
	 *
	 * Persistence unit name (EJB3 specific).
	 *
	 */
	@Parameter(property = "maven.hj3.persistenceXml")
	public File persistenceXml;

    /**
     *
     * ApplicationContext Class Name.
     *
     */
    @Parameter(property = "maven.hj3.applicationContextClassName")
    public String applicationContextClassName;

	/**
	 *
	 * Whether the <code>hashCode()</code> method should be generated.
	 *
	 */
	@Parameter(property = "maven.hj3.generateHashCode", defaultValue = "true")
	public boolean generateHashCode = true;

	/**
	 *
	 * Whether the <code>equals(...)</code> methods should be generated.
	 *
	 */
	@Parameter(property = "maven.hj3.generateEquals", defaultValue = "true")
	public boolean generateEquals = true;

	/**
	 *
	 * Whether the generated id property must be transient.
	 *
	 */
	@Parameter(property = "maven.hj3.generateTransientId", defaultValue = "false")
	public boolean generateTransientId = false;

	/**
	 * Generation result. Possible values are "annotations", "mappingFiles".
	 *
	 */
	@Parameter(property = "maven.hj3.result", defaultValue = "annotations")
	public String result = "annotations";

    /**
     * Configure the maximum identifier length (SQL, etc.).
     */
    @Parameter(property = "maven.hj3.maxIdentifierLength")
    private Integer maxIdentifierLength = null;

    public Integer getMaxIdentifierLength() {
        return maxIdentifierLength;
    }

    public void setMaxIdentifierLength(Integer maxIdentifierLength) {
        this.maxIdentifierLength = maxIdentifierLength;
    }

	@Parameter
	public String[] preArgs = new String[0];

	@Parameter
	public String[] postArgs = new String[0];

	/**
	 * Logs options defined directly as mojo parameters.
	 */
	protected void logConfiguration() throws MojoExecutionException {
		super.logConfiguration();

        if(this.getVerbose()) {
            getLog().info("target:" + target);
            getLog().info("roundtripTestClassName:" + roundtripTestClassName);
            getLog().info("resourceIncludes:" + resourceIncludes);
            getLog().info("variant:" + variant);
            getLog().info("persistenceUnitName:" + persistenceUnitName);
            getLog().info("persistenceXml:" + persistenceXml);
            getLog().info("generateHashCode:" + generateHashCode);
            getLog().info("generateEquals:" + generateEquals);
            getLog().info("generateTransientId:" + generateTransientId);
            getLog().info("result:" + result);
            getLog().info("maxIdentifierLength:" + maxIdentifierLength);
            getLog().info("applicationContextClassName:" + applicationContextClassName);
            getLog().info("preArgs:" + Arrays.toString(preArgs));
            getLog().info("postArgs:" + Arrays.toString(postArgs));
            try {
                getLog().info(
                    "XJC loaded from:"
                        + Options.class.getResource("Options.class")
                        .toURI().toURL().toExternalForm());
            } catch (IOException ignored) {
            } catch (URISyntaxException ignored) {
            }
        } else {
            getLog().debug("target:" + target);
            getLog().debug("roundtripTestClassName:" + roundtripTestClassName);
            getLog().debug("resourceIncludes:" + resourceIncludes);
            getLog().debug("variant:" + variant);
            getLog().debug("persistenceUnitName:" + persistenceUnitName);
            getLog().debug("persistenceXml:" + persistenceXml);
            getLog().debug("generateHashCode:" + generateHashCode);
            getLog().debug("generateEquals:" + generateEquals);
            getLog().debug("generateTransientId:" + generateTransientId);
            getLog().debug("result:" + result);
            getLog().debug("maxIdentifierLength:" + maxIdentifierLength);
            getLog().debug("applicationContextClassName:" + applicationContextClassName);
            getLog().debug("preArgs:" + Arrays.toString(preArgs));
            getLog().debug("postArgs:" + Arrays.toString(postArgs));
            try {
                getLog().debug(
                    "XJC loaded from:"
                        + Options.class.getResource("Options.class")
                        .toURI().toURL().toExternalForm());
            } catch (IOException ignored) {
            } catch (URISyntaxException ignored) {
            }
        }
	}

	protected List<String> getArguments() {
		final List<String> arguments = new ArrayList<String>();

		if (this.preArgs != null) {
			arguments.addAll(Arrays.asList(this.preArgs));
		}

		arguments.addAll(super.getArguments());

		if ("ejb".equals(variant)) {
			arguments.add("-Xhyperjaxb3-ejb");

			if (result != null) {
				arguments.add("-Xhyperjaxb3-ejb-result=" + result);
			}

			if (roundtripTestClassName != null) {
				arguments.add("-Xhyperjaxb3-ejb-roundtripTestClassName="
						+ roundtripTestClassName);
			}
			if (persistenceUnitName != null) {
				arguments.add("-Xhyperjaxb3-ejb-persistenceUnitName="
						+ persistenceUnitName);
			}
			if (persistenceXml != null) {
				arguments.add("-Xhyperjaxb3-ejb-persistenceXml="
						+ persistenceXml.getAbsolutePath());
			}

			if (generateTransientId) {
				arguments.add("-Xhyperjaxb3-ejb-generateTransientId=true");
			}

            if (maxIdentifierLength != null) {
                arguments.add("-Xhyperjaxb3-ejb-maxIdentifierLength=" + maxIdentifierLength);
            }

            if (applicationContextClassName != null) {
                arguments.add("-Xhyperjaxb3-ejb-applicationContextClassName=" + applicationContextClassName);
            }

		} else if ("jpa3".equals(variant)) {
			arguments.add("-Xhyperjaxb3-jpa3");

			if (result != null) {
				arguments.add("-Xhyperjaxb3-jpa3-result=" + result);
			}

			if (roundtripTestClassName != null) {
				arguments.add("-Xhyperjaxb3-jpa3-roundtripTestClassName="
						+ roundtripTestClassName);
			}
			if (persistenceUnitName != null) {
				arguments.add("-Xhyperjaxb3-jpa3-persistenceUnitName="
						+ persistenceUnitName);
			}
			if (persistenceXml != null) {
				arguments.add("-Xhyperjaxb3-jpa3-persistenceXml="
						+ persistenceXml.getAbsolutePath());
			}

            if (maxIdentifierLength != null) {
                arguments.add("-Xhyperjaxb3-jpa3-maxIdentifierLength=" + maxIdentifierLength);
            }

            if (applicationContextClassName != null) {
                arguments.add("-Xhyperjaxb3-ejb-applicationContextClassName=" + applicationContextClassName);
            }
		}

		if (generateEquals) {
			arguments.add("-Xequals");
		}
		if (generateHashCode) {
			arguments.add("-XhashCode");
		}
		arguments.add("-Xinheritance");
        arguments.add("-Xdefault-value");
        arguments.add("-Xdefault-value:all");

		if (this.postArgs != null) {
			arguments.addAll(Arrays.asList(this.postArgs));
		}

		return arguments;
	}

	/**
	 * Updates XJC's compilePath ans resources and update hyperjaxb2's
	 * resources, that is, *.hbm.xml files and hibernate.config.xml file.
	 */
	protected void setupMavenPaths() {
		super.setupMavenPaths();

		final Resource resource = new Resource();
		resource.setDirectory(getGenerateDirectory().getPath());
		for (String resourceInclude : resourceIncludes) {
			resource.addInclude(resourceInclude);
		}
		getProject().addResource(resource);

		if (this.roundtripTestClassName != null) {
			getProject().addTestCompileSourceRoot(
					getGenerateDirectory().getPath());
		}
	}
}
