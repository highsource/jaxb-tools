package org.jvnet.jaxb.maven.resolver.tools;

import org.apache.maven.plugin.logging.Log;

/**
 * This interface allow Maven XJC Mojo to pass it's logger to the CatalogResolver
 * in order to allow debugging with appropriate maven flags.
 */
public interface LoggingCatalogResolver {
    void setLog(Log log);
}
