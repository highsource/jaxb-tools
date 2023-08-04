package org.jvnet.jaxb2_commons.reflection.util.test;

import java.net.URI;
import java.net.URISyntaxException;

public class URIInternalTest {
    private transient String scheme;            // null ==> relative URI

    public URIInternalTest(String uri) throws URISyntaxException {
        URI internalUri = new URI(uri);
        this.scheme = internalUri.getScheme();
    }

    public String getScheme() {
        return scheme;
    }
}
