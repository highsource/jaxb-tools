package org.jvnet.annox.parser.tests;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface K {
	String[] value();

}
