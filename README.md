# JAXB Maven Plugin #

Welcome to the `org.jvnet.jaxb:jaxb-maven-plugin`, the most advanced and feature-full Maven plugin for XML Schema compilation.

## Quick start ##

* Put your schemas (`*.xsd`) and bindings (`*.xjb`) into the `src/main/resources` folder.
* Add the plugin to your `pom.xml`:

```xml
<project ...>
  ...
  <build>
    <plugins>
      <plugin>
        <groupId>org.jvnet.jaxb</groupId>
        <artifactId>jaxb-maven-plugin</artifactId>
        <version>2.0.0</version>
        <executions>
          <execution>
            <goals>
              <goal>generate</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
    </plugins>
  </build>
  ...
</project>
```

### JAXB Versions

The current version 2.X of this plugin supports only JAXB 2.3.

If you need an older JAXB version, you can use one of the following variants, which are no more supported :

* `org.jvnet.jaxb2.maven2:maven-jaxb20-plugin:0.15.2` - JAXB 2.0.
* `org.jvnet.jaxb2.maven2:maven-jaxb21-plugin:0.15.2` - JAXB 2.1.
* `org.jvnet.jaxb2.maven2:maven-jaxb22-plugin:0.15.1` - JAXB 2.2.

### Java versions

Supported Java versions are `8`, and higher.

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
* [JAXB Eclipse Project](https://github.com/eclipse-ee4j/jaxb-ri)

## Disclaimer ##

This project is not developed, supported or in any other way affiliated with Apache. The `org.jvnet.jaxb:jaxb-maven-plugin` is not an Apache product (and does not pretend to be one), it is a completely independent development.

This project is also *not* developed by or affiliated with Oracle or Sun. Even if it is featured on [https://jaxb.java.net/](https://jaxb.java.net) pages, 

**This plugin is in no way _official_ JAXB Maven plugin by Sun or Oracle.**

This is a completely indepentent development. [I](https://github.com/highsource) am *not* an Oracle employee.

## Related ##

This Maven plugin wraps and enhances the [JAXB](https://jaxb.java.net/) [Schema Compiler (XJC)](http://docs.oracle.com/javase/6/docs/technotes/tools/share/xjc.html) and allows
compiling XML Schemas (as well as WSDL, DTDs, RELAX NG) into Java classes in Maven builds.

> If you are interested in the Mojohaus JAXB2 Maven Plugin (`org.codehaus.mojo:jaxb2-maven-plugin`),
> please follow [this link](https://github.com/mojohaus/jaxb2-maven-plugin) to the corresponding website.
