package org.jvnet.jaxb.annox.parser.tests;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface M {
	K[] value();

}
