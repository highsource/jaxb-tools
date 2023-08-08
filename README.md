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

# JAXB2 Basics

JAXB2 Basics is an [open source](https://github.com/highsource/jaxb2-basics/blob/master/LICENSE) project
which provides useful plugins and tools for [JAXB 2.x reference implementation](https://jaxb.java.net/).

# Documentation

Please refer to the [wiki](https://github.com/highsource/jaxb2-basics/wiki) for documentation.

JAXB2 Basics can only be used with JAXB/XJC 2.3.0 and higher. JAXB/XJC versions 2.2.x and earlier are no longer supported.

JAXB2 Basics can only be used with Java 1.8 and above.

## Using JAXB2 Basics

* [Using JAXB2 Basics Plugins](https://github.com/highsource/jaxb2-basics/wiki/Using-JAXB2-Basics-Plugins)

## JAXB2 Basics Plugins
* [SimpleEquals Plugin](https://github.com/highsource/jaxb2-basics/wiki/JAXB2-SimpleEquals-Plugin) - generates runtime-free reflection-free `equals(...)` methods.
* [SimpleHashCode Plugin](https://github.com/highsource/jaxb2-basics/wiki/JAXB2-SimpleHashCode-Plugin) - generates runtime-free reflection-free `hashCode()` methods.
* Equals Plugin - generates reflection-free strategic `equals(...)` method.
* HashCode Plugin - generates reflection-free strategic `hashCode()` method.
* ToString Plugin - generates reflection-free strategic `toString()` methods.
* Copyable Plugin - generates reflection-free strategic `copy(...)` deep copying.
* Mergeable Plugin - generates reflection-free strategic `merge(...)` methods to merge data from two source objects into the given object.
* Inheritance Plugin - makes schema-derived classes extend certain class or implement certain interfaces.
* Wildcard Plugin - allows you to specify the wildcard mode for the wildcard properties.
* AutoInheritance Plugin - makes classes derived from global elements or complex types extend or implement certain classes or interfaces automatically.
* [Setters Plugin](https://github.com/highsource/jaxb2-basics/wiki/JAXB2-Setters-Plugin) - generates setters for collections.
* [Simplify Plugin](https://github.com/highsource/jaxb2-basics/wiki/JAXB2-Simplify-Plugin) - simplifies weird properties like `aOrBOrC`.
* [EnumValue Plugin](https://github.com/highsource/jaxb2-basics/wiki/JAXB2-EnumValue-Plugin) - makes all the generated enums implement the `EnumValue<T>` interface.
* JAXBIndex Plugin - generated `jaxb.index` files listing schema-derived classes.
* [FixJAXB1058 Plugin](https://github.com/highsource/jaxb2-basics/wiki/JAXB2-FixJAXB1058-Plugin) - fixes [JAXB-1058](https://java.net/jira/browse/JAXB-1058).

## Credits ##

* Many thanks to **James Annesley** for his ideas and help with the [SimpleEquals Plugin](https://github.com/highsource/jaxb2-basics/wiki/JAXB2-SimpleEquals-Plugin) and the [SimpleHashCode Plugin](https://github.com/highsource/jaxb2-basics/wiki/JAXB2-SimpleHashCode-Plugin).
