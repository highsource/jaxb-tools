# JAXB Tools

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.jvnet.jaxb/jaxb-tools-project/badge.svg?gav=true)](https://maven-badges.herokuapp.com/maven-central/org.jvnet.jaxb/jaxb-tools-project/?gav=true)
[![GitHub Actions Status](https://github.com/highsource/jaxb-tools/workflows/Maven%20CI/badge.svg)](https://github.com/highsource/jaxb-tools/actions)
[![CodeQL](https://github.com/highsource/jaxb-tools/workflows/CodeQL/badge.svg)](https://github.com/highsource/jaxb-tools/actions/workflows/codeql-analysis.yml?query=workflow%3ACodeQL)

Welcome to the most advanced and feature-full sets of [JAXB](https://github.com/eclipse-ee4j/jaxb-ri)-related tools.

The project is currently containing the following tools :
* [JAXB Maven Plugin](#jaxb-maven-plugin)
* [JAXB Plugins](#jaxb-plugins)
* [Annox](#annox)
* [JAXB Annotate Plugin](#jaxb-annotate-plugin)
* [JAXB Hyperjaxb3](#jaxb-hyperjaxb) (Preview)

Please check our [Migration Guide](https://github.com/highsource/jaxb-tools/wiki/JAXB-Tools-Migration-Guide) for any questions about migrating
from previous releases to newers one.

## Maven versions
Starting from 4.0.1, this plugin requires maven 3.1.0 as minimal version.

If you still need maven 2.x or maven 3.0.x, you can still use the previous released versions
but you should consider upgrading maven to at least 3.1.0.

## Java versions

This project supports Java 11 and higher.

The build is tested against JDK11 and JDK17.

## [Documentation](https://github.com/highsource/jaxb-tools/wiki)

Please refer to the [wiki](https://github.com/highsource/jaxb-tools/wiki) for the full documentation.

* [Jakarta Slack](https://jakarta.ee/connect/) Join the #jaxb channel on Jakarta EE Slack
* [User Guide](https://github.com/highsource/jaxb-tools/wiki/User-Guide)
* [Maven Documentation](https://github.com/highsource/jaxb-tools/wiki/Maven-Documentation)
* [Configuration Cheat Sheet](https://github.com/highsource/jaxb-tools/wiki/Configuration-Cheat-Sheet)
* [Common Pitfalls and Problems](https://github.com/highsource/jaxb-tools/wiki/Common-Pitfalls-and-Problems) (Work in progress)
* [Best Practices](https://github.com/highsource/jaxb-tools/wiki/Best-Practices) (Work in progress)
* [FAQ](https://github.com/highsource/jaxb-tools/wiki/FAQ)
* [Sample Projects](https://github.com/highsource/jaxb-tools/wiki/Sample-Projects)
* [Support](https://github.com/highsource/jaxb-tools/wiki/Support)
* [License](https://github.com/highsource/jaxb-tools/blob/master/LICENSE)
* [JAXB Eclipse Project](https://github.com/eclipse-ee4j/jaxb-ri)

## Disclaimer

This project is not developed, supported or in any other way affiliated with Apache, Eclipse or Oracle.

It was a completely independent development by its creator, [Alexey Valikov](https://github.com/highsource).

It is now maintained by a group of people who are interested in keeping jaxb-tools working with future versions of Java and Jakarta.

# JAXB Maven Plugin

This Maven plugin generates Java classes during Maven builds from XML Schemas (as well as WSDL, DTDs, RELAX NG formats).

It wraps and enhances the [JAXB Schema Compiler (XJC)](https://docs.oracle.com/javase/8/docs/technotes/tools/unix/xjc.html) with its own set of plugins and customization points.

## Quick start

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
        <version>4.0.0</version>
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

The current version 4.X of this plugin supports only JAXB 4.X (Jakarta based).

Versions 2.X and 3.X supports JAXB 2.3 and JAXB 3.0 with JDK8 support and baseline :
* [`org.jvnet.jaxb:jaxb-maven-plugin:3.0.1`](https://github.com/highsource/jaxb-tools/tree/jaxb-tools-3.x) - JAXB 3.0
* [`org.jvnet.jaxb:jaxb-maven-plugin:2.0.9`](https://github.com/highsource/jaxb-tools/tree/jaxb-tools-2.x) - JAXB 2.3

If you need an older JAXB version, you can use one of the following variants, which are no longer supported :
* `org.jvnet.jaxb2.maven2:maven-jaxb22-plugin:0.15.1` - JAXB 2.2.
* `org.jvnet.jaxb2.maven2:maven-jaxb21-plugin:0.15.2` - JAXB 2.1.
* `org.jvnet.jaxb2.maven2:maven-jaxb20-plugin:0.15.2` - JAXB 2.0.

## Similar Projects

If you experience issues with the Mojohaus JAXB2 Maven Plugin (`org.codehaus.mojo:jaxb2-maven-plugin`),
please file it [on their project page](https://github.com/mojohaus/jaxb2-maven-plugin).

# JAXB Plugins

JAXB Plugins (former JAXB Basics) is an [open source](https://github.com/highsource/jaxb-tools/blob/master/LICENSE) project
which provides useful plugins and tools for [JAXB 4.x reference implementation](https://github.com/eclipse-ee4j/jaxb-ri/tree/4.0.2-RI).

For the current version 4.X and the previous version 3.X, its artifacts are named `org.jvnet.jaxb:jaxb-plugins`, while the previous verions 2.X were named `org.jvnet.jaxb:jaxb2-basics`. So, please replace all "jaxb2-basics" with "jaxb-plugins" in the following documentations (also in wiki) if you use versions 3.X and 4.X.

## Documentation

Please refer to the [wiki](https://github.com/highsource/jaxb-tools/wiki/JAXB2-Basic) for documentation.

JAXB Basics can only be used with JAXB/XJC 4.x.

## Using JAXB Basics

* [Using JAXB Basics Plugins](https://github.com/highsource/jaxb-tools/wiki/Using-JAXB2-Basics-Plugins)

## JAXB Basics Plugins
* [SimpleEquals Plugin](https://github.com/highsource/jaxb-tools/wiki/JAXB2-SimpleEquals-Plugin) - generates runtime-free reflection-free `equals(...)` methods.
* [SimpleHashCode Plugin](https://github.com/highsource/jaxb-tools/wiki/JAXB2-SimpleHashCode-Plugin) - generates runtime-free reflection-free `hashCode()` methods.
* [Equals Plugin](https://github.com/highsource/jaxb-tools/wiki/JAXB2-Equals-Plugin) - generates reflection-free strategic `equals(...)` method.
* [HashCode Plugin](https://github.com/highsource/jaxb-tools/wiki/JAXB2-HashCode-Plugin) - generates reflection-free strategic `hashCode()` method.
* [ToString Plugin](https://github.com/highsource/jaxb-tools/wiki/JAXB2-ToString-Plugin) - generates reflection-free strategic `toString()` methods.
* [Copyable Plugin](https://github.com/highsource/jaxb-tools/wiki/JAXB2-Copyable-Plugin) - generates reflection-free strategic `copy(...)` deep copying.
* [Mergeable Plugin](https://github.com/highsource/jaxb-tools/wiki/JAXB2-Mergeable-Plugin) - generates reflection-free strategic `merge(...)` methods to merge data from two source objects into the given object.
* [Inheritance Plugin](https://github.com/highsource/jaxb-tools/wiki/JAXB2-Inheritance-Plugin) - makes schema-derived classes extend certain class or implement certain interfaces.
* [Wildcard Plugin](https://github.com/highsource/jaxb-tools/wiki/JAXB2-Wildcard-Plugin) - allows you to specify the wildcard mode for the wildcard properties.
* [AutoInheritance Plugin](https://github.com/highsource/jaxb-tools/wiki/JAXB2-AutoInheritance-Plugin) - makes classes derived from global elements or complex types extend or implement certain classes or interfaces automatically.
* [Setters Plugin](https://github.com/highsource/jaxb-tools/wiki/JAXB2-Setters-Plugin) - generates setters for collections.
* [Simplify Plugin](https://github.com/highsource/jaxb2-tools/wiki/JAXB2-Simplify-Plugin) - simplifies weird properties like `aOrBOrC`.
* [EnumValue Plugin](https://github.com/highsource/jaxb2-tools/wiki/JAXB2-EnumValue-Plugin) - makes all the generated enums implement the `EnumValue<T>` interface.
* [JAXBIndex Plugin](https://github.com/highsource/jaxb-tools/wiki/JAXB2-JAXBIndex-Plugin) - generated `jaxb.index` files listing schema-derived classes.
* [FixJAXB1058 Plugin](https://github.com/highsource/jaxb-tools/wiki/JAXB2-FixJAXB1058-Plugin) - fixes [JAXB-1058](https://github.com/eclipse-ee4j/jaxb-ri/issues/1058).
* [Commons Lang Plugin](https://github.com/highsource/jaxb-tools/wiki/JAXB2-Commons-Lang-Plugin) - generates the `toString()`, `hashCode()` and `equals()` methods using Apache commons-lang3.
* [Default Value Plugin](https://github.com/highsource/jaxb-tools/wiki/JAXB2-Default-Value-Plugin) - modifies the JAXB code model to set default values to the schema `default` attribute.
* [Fluent API Plugin](https://github.com/highsource/jaxb-tools/wiki/JAXB2-Fluent-API-Plugin) - support a fluent api in addition to the default (JavaBean) setter methods.
* [Namespace Prefix Plugin](https://github.com/highsource/jaxb-tools/wiki/JAXB2-Namespace-Prefix-Plugin) - adds `jakarta.xml.bind.annotation.XmlNs` annotations to `package-info.java` files
* [Value Constructor Plugin](https://github.com/highsource/jaxb-tools/wiki/JAXB2-Value-Constructor-Plugin) - generates another constructor, taking an argument for each field in the class and initialises the field with the argument value.
* [Boolean Getter Plugin](https://github.com/highsource/jaxb-tools/wiki/JAXB-Boolean-Getter-Plugin) - generates getters for boolean property in `getXXX` instead of `isXXX`.
* [CamelCase Plugin](https://github.com/highsource/jaxb-tools/wiki/JAXB-CamelCase-Plugin) - modifies the classes name generated by XJC to always be in CamelCase.
* [Xml ElementWrapper Plugin](https://github.com/highsource/jaxb-tools/wiki/JAXB-XML-ElementWrapper-Plugin) - generates `jakarta.xml.bind.annotation.XmlElementWrapper` annotation to simplify generated structure.
* [Parent Pointer Plugin](https://github.com/highsource/jaxb-tools/wiki/JAXB-Parent-Pointer-Plugin) - generates getter in child elements to get the parent object (depends on `jaxb-plugins-runtime`)
* [Property Listener Injector Plugin](https://github.com/highsource/jaxb-tools/wiki/JAXB-Property-Listener-Injector-Plugin) - adds methods in order to configure the generation of events on each `setXXX` method

## Credits

* Many thanks to **James Annesley** for his ideas and help with the [SimpleEquals Plugin](https://github.com/highsource/jaxb-tools/wiki/JAXB2-SimpleEquals-Plugin) and the [SimpleHashCode Plugin](https://github.com/highsource/jaxb-tools/wiki/JAXB2-SimpleHashCode-Plugin).

# Annox

Parse Java annotations from text or XML resources.

Please refer to the [wiki](https://github.com/highsource/jaxb-tools/wiki/Annox-Home) for documentation.

```java
// Parse annotation from the string
XAnnotation<XmlRootElement> xannotation =
	(XAnnotation<XmlRootElement>) XAnnotationParser.INSTANCE.parse
		("@jakarta.xml.bind.annotation.XmlRootElement(name=\"foo\")");

// Create an instance of the annotation
XmlRootElement xmlRootElement = xannotation.getResult();
assertEquals("foo", xmlRootElement.name());
assertEquals("##default", xmlRootElement.namespace());

// Analyze the structure of the annotation
assertEquals(String.class, xannotation.getFieldsMap().get("name").getType());
assertEquals("##default", xannotation.getFieldsMap().get("namespace").getResult());
```

# JAXB Annotate Plugin

JAXB Annotate Plugin is capable of adding or removing arbitrary annotations to/from the generated sources.

It is also capable of removing all XML related annotations from the generated sources and also the `ObjectFactory`. This new capability can be used in order to generate plain Java POJO from XSD without depending on JAXB artifacts.

Please refer to the [wiki](https://github.com/highsource/jaxb-tools/wiki/JAXB-Annotate-Home) for documentation.

## Usage overview

* Annotate your schema using binding files or directly in schema
* Add the plugin to the XJC classpath.
* Add your annotation classes to the XJC classpath.
* Activate the plugin using `-Xannotate`-switch.

## Providing annotations

You can annotate your schema-derived elements using normal Java annotation syntax.
([Old XML syntax](https://github.com/highsource/jaxb-tools/wiki/JAXB-Annotate-Home) is still supported but no longer recommended.)

**Current limitations:**
* Annotation classes must be known in compile time. I.e. annotation classes must be made available in the XJC classpath.
If you want to use your own annotations, you have to pre-compile them and add your annotation classes to the XJC classpath.
* As a consequence, currently you can't use schema-derived enums in your annotations. Since you have to compile annotations
before compiling the schema - and as your enums are first generated from the schema, this is a chicken-and-egg-problem.
* All class names (classes of annotations or classes you use as values in annotations) must be fully qualified.
Inner classes should use the dot (`.`) as delimiter (not `$`).

You can put your annotations directly in schema:

````xml
<xsd:schema
        xmlns:xsd="http://www.w3.org/2001/XMLSchema"
        xmlns:jaxb="https://jakarta.ee/xml/ns/jaxb"
        jaxb:version="3.0"
        xmlns:annox="urn:jaxb.jvnet.org:annox"
        jaxb:extensionBindingPrefixes="annox">


    <xsd:complexType name="FooType">
        <xsd:annotation>
            <xsd:appinfo>
                <annox:annotate>@java.lang.SuppressWarnings({"unchecked","rawtypes"})</annox:annotate>
                <annox:annotate target="package">@javax.annotation.Generated({"XJC","JAXB Annotate Plugin"})</annox:annotate>
            </xsd:appinfo>
        </xsd:annotation>
        <xsd:sequence>
            <xsd:element name="bar" type="xsd:string"/>
            <xsd:element name="foobar" type="xsd:string">
                <xsd:annotation>
                    <xsd:appinfo>
                        <annox:annotate>@java.lang.SuppressWarnings({"unchecked","rawtypes"})</annox:annotate>
                        <annox:annotate target="setter">@java.lang.Deprecated</annox:annotate>
                        <annox:annotate target="setter-parameter">@java.lang.Deprecated</annox:annotate>
                        <annox:annotate target="getter">@java.lang.Deprecated</annox:annotate>
                        <annox:annotate target="field">@java.lang.Deprecated</annox:annotate>
                        <annox:annotate target="class">@java.lang.Deprecated</annox:annotate>
                    </xsd:appinfo>
                </xsd:annotation>
            </xsd:element>
        </xsd:sequence>
    </xsd:complexType>

    <xs:simpleType name="FooEnum">
        <xs:annotation>
            <xs:appinfo>
                <annox:annotateEnumValueMethod>@java.lang.Deprecated</annox:annotateEnumValueMethod>
            </xs:appinfo>
        </xs:annotation>

        <xs:restriction base="xs:string">
            <xs:enumeration value="BAR"/>
            <xs:enumeration value="BAZ"/>
        </xs:restriction>
    </xs:simpleType>

</xsd:schema>
````

Or in binding files:

````xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<jaxb:bindings
        xmlns:jaxb="https://jakarta.ee/xml/ns/jaxb" xmlns:xs="http://www.w3.org/2001/XMLSchema"
        xmlns:xjc="http://java.sun.com/xml/ns/jaxb/xjc"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:annox="urn:jaxb.jvnet.org:annox"
        xsi:schemaLocation="https://jakarta.ee/xml/ns/jaxb https://jakarta.ee/xml/ns/jaxb/bindingschema_3_0.xsd"
        jaxb:extensionBindingPrefixes="xjc annox"
        version="3.0">

    <jaxb:bindings schemaLocation="schema.xsd" node="/xs:schema">
        <jaxb:bindings node="xs:complexType[@name='issueJIIB39CType']">
            <annox:annotateClass>@jakarta.xml.bind.annotation.XmlRootElement(name="IssueJIIB39CType")</annox:annotateClass>
        </jaxb:bindings>
        <jaxb:bindings node="xs:complexType[@name='issueJIIB39CType']/xs:attribute[@name='test']">
            <annox:annotate target="field">@javax.xml.bind.annotation.XmlAttribute(required=false, name="test")</annox:annotate>
        </jaxb:bindings>
    </jaxb:bindings>

</jaxb:bindings>
````

You can use the following customization elements in the `urn:jaxb.jvnet.org:annox` namespace:
* `annotate` with the optional `target` attribute
 * `package`
 * `class`
 * `getter`
 * `setter`
 * `setter-parameter`
 * `field`
 * `enum-value-method`
 * `enum-fromValue-method`
* `annotateProperty`
* `annotatePackage`
* `annotateClass`
* `annotateElement`
* `annotateEnum`
* `annotateEnumConstant`
* `annotateEnumValueMethod` - annotate the `value()` method of the enum
* `annotateEnumFromValueMethod` - annotate the `fromValue(String)` method of the enum

The `urn:jaxb.jvnet.org:annox` namespace must be declared in the `jaxb:extensionBindingPrefixes` attribute via prefix, ex.:

````
xmlns:annox="urn:jaxb.jvnet.org:annox"
jaxb:extensionBindingPrefixes="xjc annox"
````

Note: the previous `http://annox.dev.java.net` namespace is still supported but no longer exists. We decided to change this namespace
but keep older one too to avoid breaking older builds. Please migrate to new namespace of the plugin `urn:jaxb.jvnet.org:annox`.

## Removing annotations

* Customize your schema using binding files or directly in schema
* Add the plugin to the XJC classpath.
* Activate the plugin using `-XremoveAnnotation`-switch.

You can remove annotations using customizations directly in schema:

````xml
<xsd:schema
        xmlns:xsd="http://www.w3.org/2001/XMLSchema"
        xmlns:jaxb="https://jakarta.ee/xml/ns/jaxb"
        jaxb:version="3.0"
        xmlns:annox="urn:jaxb.jvnet.org:annox"
        jaxb:extensionBindingPrefixes="annox">

    <xsd:complexType name="FooType">
        <xsd:annotation>
            <xsd:appinfo>
                <annox:removeAnnotation class="jakarta.xml.bind.annotation.XmlType" />
            </xsd:appinfo>
        </xsd:annotation>
        <xsd:sequence>
            <xsd:element name="bar" type="xsd:string"/>
            <xsd:element name="foobar" type="xsd:string">
                <xsd:annotation>
                    <xsd:appinfo>
                        <annox:removeAnnotation class="jakarta.xml.bind.annotation.XmlElement" target="field" />
                    </xsd:appinfo>
                </xsd:annotation>
            </xsd:element>
        </xsd:sequence>
    </xsd:complexType>

</xsd:schema>
````

Or in binding files:

````xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<jaxb:bindings
        xmlns:jaxb="https://jakarta.ee/xml/ns/jaxb" xmlns:xs="http://www.w3.org/2001/XMLSchema"
        xmlns:xjc="http://java.sun.com/xml/ns/jaxb/xjc"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:annox="urn:jaxb.jvnet.org:annox"
        xsi:schemaLocation="https://jakarta.ee/xml/ns/jaxb https://jakarta.ee/xml/ns/jaxb/bindingschema_3_0.xsd"
        jaxb:extensionBindingPrefixes="xjc annox"
        version="3.0">

    <jaxb:bindings schemaLocation="schema.xsd" node="/xs:schema">
        <jaxb:bindings node="xs:complexType[@name='FooType']">
            <annox:removeAnnotation class="jakarta.xml.bind.annotation.XmlType" />
        </jaxb:bindings>
        <jaxb:bindings node="xs:complexType[@name='FooType']//xs:element[@name='foobar']">
            <annox:removeAnnotation class="jakarta.xml.bind.annotation.XmlElement" target="field" />
        </jaxb:bindings>
    </jaxb:bindings>

</jaxb:bindings>
````

You can use the following customization elements in the `urn:jaxb.jvnet.org:annox` namespace:
* `removeAnnotation` with the optional `target` attribute:
 * `package`
 * `class`
 * `getter`
 * `setter`
 * `setter-parameter`
 * `field`
 * `enum-value-method`
 * `enum-fromValue-method`
* `removeAnnotationFromProperty`
* `removeAnnotationFromPackage`
* `removeAnnotationFromClass`
* `removeAnnotationFromElement`
* `removeAnnotationFromeEnum`
* `removeAnnotationFromEnumConstant`
* `removeAnnotationFromEnumValueMethod` - removes annotation from the `value()` method of the enum
* `removeAnnotationFromEnumFromValueMethod` - removes annotation from the `fromValue(String)` method of the enum

The `urn:jaxb.jvnet.org:annox` namespace must be declared in the `jaxb:extensionBindingPrefixes` attribute via prefix, ex.:

````
xmlns:annox="urn:jaxb.jvnet.org:annox"
jaxb:extensionBindingPrefixes="xjc annox"
````

Note: the previous `http://annox.dev.java.net` namespace is still supported but no longer exists. We decided to change this namespace
but keep older one too to avoid breaking older builds. Please migrate to new namespace of the plugin `urn:jaxb.jvnet.org:annox`.


## Using Annotate JAXB Plugin with Maven

* Add `org.jvnet.jaxb:jaxb-plugin-annotate` as XJC plugin
* Turn on the plugin using `-Xannotate` or `-XremoveAnnotation`switch
* Add artifact with your annotations as another XJC plugin

Example:

````xml
<plugin>
    <groupId>org.jvnet.jaxb</groupId>
    <artifactId>jaxb-maven-plugin</artifactId>
    <configuration>
        <extension>true</extension>
        <args>
            <arg>-Xannotate</arg>
            <arg>-XremoveAnnotation</arg>
        </args>
        <plugins>
            <plugin>
                <groupId>org.jvnet.jaxb</groupId>
                <artifactId>jaxb-plugin-annotate</artifactId>
            </plugin>
            <!-- Artifact with custom annotations -->
            <plugin>
                <groupId>com.acme.foo</groupId>
                <artifactId>my-custom-annotations</artifactId>
            </plugin>
        </plugins>
    </configuration>
</plugin>
````
See [this example](https://github.com/highsource/jaxb-tools/tree/master/jaxb-annotate-parent/tests/annox).

Note that annotations are first compiled in the `annotations` module and the added to the classpath of the `jaxb-maven-plugin` in the `schema` module:

## Using JAXB Annotate Plugin with Ant

See [this example](https://github.com/highsource/jaxb-tools/blob/master/jaxb-annotate-parent/samples/annotate/project-build.xml).

# JAXB Hyperjaxb

Hyperjaxb3 provides relational persistence for JAXB objects.
This is preview feature (please give feedback if any problems).
