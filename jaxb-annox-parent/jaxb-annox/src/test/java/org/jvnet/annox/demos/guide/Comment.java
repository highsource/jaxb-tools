package org.jvnet.annox.demos.guide;

public @interface Comment {
	public String lang() default "en";
	public String value();
}
