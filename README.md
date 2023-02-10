# JAXB2 Maven Plugin #

Welcome to the `org.jvnet.jaxb:jaxb40-maven-plugin`, the most advanced and feature-full Maven plugin for XML Schema compilation.

This Maven plugin wraps and enhances the [JAXB](https://jaxb.java.net/) [Schema Compiler (XJC)](http://docs.oracle.com/javase/6/docs/technotes/tools/share/xjc.html) and allows
compiling XML Schemas (as well as WSDL, DTDs, RELAX NG) into Java classes in Maven builds.

> If you are interested in the Mojohaus JAXB2 Maven Plugin (`org.codehaus.mojo:jaxb2-maven-plugin`),
> please follow [this link](https://github.com/mojohaus/jaxb2-maven-plugin) to the corresponding website.

## Quick start ##

* Put your schemas (`*.xsd`) and bindings (`*.xjb`) into the `src/main/resources` folder.
* Add the plugin to your `pom.xml`:

```xml
<project ...>
	...
	<build>
		<plugins>
			...
			<plugin>
				<groupId>org.jvnet.jaxb</groupId>
				<artifactId>jaxb23-maven-plugin</artifactId>
				<version>4.0.0</version>
				<executions>
					<execution>
						<goals>
							<goal>generate</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
			...
		</plugins>
	</build>
	...
</project>
```

### JAXB Versions

If you need a specific JAXB version, you can explicitly use one of the following variants:

* `org.jvnet.jaxb:jaxb40-maven-plugin` - JAXB 4.0.
* `org.jvnet.jaxb:jaxb30-maven-plugin` - JAXB 3.0.
* `org.jvnet.jaxb:jaxb23-maven-plugin` - JAXB 2.3.

### Java versions

Supported Java versions are `1.8` (only for JAXB 2.3 and JAXB 3.0), `11`, `17`

Java version `1.6`, `1.7` is no longer supported (since version `4.0.0`).

## [Documentation](https://github.com/highsource/maven-jaxb2-plugin/wiki) ##

Please refer to the [wiki](https://github.com/highsource/maven-jaxb2-plugin/wiki) for the full documentation.


* [User Guide](https://github.com/highsource/maven-jaxb2-plugin/wiki/User-Guide)
* Maven Documentation  (Work in progress)
* [Configuration Cheat Sheet](https://github.com/highsource/maven-jaxb2-plugin/wiki/Configuration-Cheat-Sheet)
* [Common Pitfalls and Problems](https://github.com/highsource/maven-jaxb2-plugin/wiki/Common-Pitfalls-and-Problems) (Work in progress)
* [Best Practices](https://github.com/highsource/maven-jaxb2-plugin/wiki/Best-Practices) (Work in progress)
* [FAQ](https://github.com/highsource/maven-jaxb2-plugin/wiki/FAQ)
* [Sample Projects](https://github.com/highsource/maven-jaxb2-plugin/wiki/Sample-Projects)
* [Support](https://github.com/highsource/maven-jaxb2-plugin/wiki/Support)
* [License](https://github.com/highsource/maven-jaxb2-plugin/blob/master/LICENSE)


## Disclaimer ##

This project is not developed, supported or in any other way affiliated with Apache. The `org.jvnet.jaxb:jaxb40-maven-plugin` is not an Apache product (and does not pretend to be one), it is a completely independent development.

This project is also *not* developed by or affiliated with Oracle or Sun. Even if it is featured on [https://jaxb.java.net/](https://jaxb.java.net) pages, 

**This plugin is in no way _official_ JAXB2 Maven plugin by Sun or Oracle.**

This is a completely indepentent development. [I](https://github.com/highsource) am *not* an Oracle employee.