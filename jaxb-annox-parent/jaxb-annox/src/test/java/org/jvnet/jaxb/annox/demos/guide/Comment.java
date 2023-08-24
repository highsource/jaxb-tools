package org.jvnet.jaxb.annox.demos.guide;

public @interface Comment {
	public String lang() default "en";
	public String value();
}
