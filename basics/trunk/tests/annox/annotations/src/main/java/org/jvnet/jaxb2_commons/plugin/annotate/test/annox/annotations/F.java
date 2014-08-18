package org.jvnet.jaxb2_commons.plugin.annotate.test.annox.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface F {
	String value();

}
