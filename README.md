<<<<<<< HEAD
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

## [Documentation](https://github.com/highsource/jaxb-tools/wiki) ##

Please refer to the [wiki](https://github.com/highsource/jaxb-tools/wiki) for the full documentation.

* [Jakarta Slack](https://jakarta.ee/connect/) Join the #jaxb channel on Jakarta EE Slack
* [User Guide](https://github.com/highsource/jaxb-tools/wiki/User-Guide)
* Maven Documentation  (Work in progress)
* [Configuration Cheat Sheet](https://github.com/highsource/jaxb-tools/wiki/Configuration-Cheat-Sheet)
* [Common Pitfalls and Problems](https://github.com/highsource/jaxb-tools/wiki/Common-Pitfalls-and-Problems) (Work in progress)
* [Best Practices](https://github.com/highsource/jaxb-tools/wiki/Best-Practices) (Work in progress)
* [FAQ](https://github.com/highsource/jaxb-tools/wiki/FAQ)
* [Sample Projects](https://github.com/highsource/jaxb-tools/wiki/Sample-Projects)
* [Support](https://github.com/highsource/jaxb-tools/wiki/Support)
* [License](https://github.com/highsource/jaxb-tools/blob/master/LICENSE)
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


Annox
=====

Parse Java annotations from text or XML resources.

```java
		// Parse annotation from the string
		XAnnotation<XmlRootElement> xannotation =
			(XAnnotation<XmlRootElement>) XAnnotationParser.INSTANCE.parse
				("@javax.xml.bind.annotation.XmlRootElement(name=\"foo\")");

		// Create an instance of the annotation 
		XmlRootElement xmlRootElement = xannotation.getResult();
		assertEquals("foo", xmlRootElement.name());
		assertEquals("##default", xmlRootElement.namespace());
		
		// Analyze the structure of the annotation
		assertEquals(String.class, xannotation.getFieldsMap().get("name").getType());
		assertEquals("##default", xannotation.getFieldsMap().get("namespace").getResult());
```
=======
[![Maven Central](https://img.shields.io/maven-central/v/org.jvnet.jaxb2_commons/jaxb2-basics-annotate.svg)](https://github.com/highsource/jaxb2-annotate-plugin)

jaxb2-annotate-plugin
=====================

JAXB2 Annotate Plugin is capable of adding or removing arbitrary annotations to/from the generated sources.

Usage overview
--------------

* Annotate your schema using binding files or directly in schema
* Add the plugin to the XJC classpath.
* Add your annotation classes to the XJC classpath.
* Activate the plugin using `-Xannotate`-switch.

Providing annotations
---------------------

You can annotate your schema-derived elements using normal Java annotation syntax.
([Old XML syntax](http://confluence.highsource.org/display/J2B/Annotate+Plugin) is still supported but no longer recommended.)

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
	xmlns:jaxb="http://java.sun.com/xml/ns/jaxb"
	jaxb:version="2.1" 
	xmlns:annox="http://annox.dev.java.net" 
	jaxb:extensionBindingPrefixes="annox">



	<xsd:complexType name="FooType">
		<xsd:annotation>
			<xsd:appinfo>
				<annox:annotate>@java.lang.SuppressWarnings({"unchecked","rawtypes"})</annox:annotate>
				<annox:annotate target="package">@javax.annotation.Generated({"XJC","JAXB2 Annotate Plugin"})</annox:annotate>
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
	xmlns:jaxb="http://java.sun.com/xml/ns/jaxb" xmlns:xs="http://www.w3.org/2001/XMLSchema"
	xmlns:xjc="http://java.sun.com/xml/ns/jaxb/xjc"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:annox="http://annox.dev.java.net"
	xsi:schemaLocation="http://java.sun.com/xml/ns/jaxb http://java.sun.com/xml/ns/jaxb/bindingschema_2_0.xsd"
	jaxb:extensionBindingPrefixes="xjc annox"
	version="2.1">

	<jaxb:bindings schemaLocation="schema.xsd" node="/xs:schema">
		<jaxb:bindings node="xs:complexType[@name='issueJIIB39CType']">
			<annox:annotateClass>@javax.xml.bind.annotation.XmlRootElement(name="IssueJIIB39CType")</annox:annotateClass>
		</jaxb:bindings>
		<jaxb:bindings node="xs:complexType[@name='issueJIIB39CType']/xs:attribute[@name='test']">
			<annox:annotate target="field">@javax.xml.bind.annotation.XmlAttribute(required=false, name="test")</annox:annotate>
		</jaxb:bindings>
	</jaxb:bindings>

</jaxb:bindings>
````

You can use the following customization elements in the `http://annox.dev.java.net` namespace:
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

The `http://annox.dev.java.net` namespace must be declared in the `jaxb:extensionBindingPrefixes` attribute via prefix, ex.:

````
xmlns:annox="http://annox.dev.java.net"
jaxb:extensionBindingPrefixes="xjc annox"
````

Note: yes, I know that `http://annox.dev.java.net` no longer exists. Changing this namespace would break old builds.
This is just a namespace, there must not necessarily be content there. Treat it as a logical identifier, nothing else.

Removing annotations
--------------

* Customize your schema using binding files or directly in schema
* Add the plugin to the XJC classpath.
* Activate the plugin using `-XremoveAnnotation`-switch.

You can remove annotations using customizations directly in schema:

````xml
<xsd:schema
	xmlns:xsd="http://www.w3.org/2001/XMLSchema" 
	xmlns:jaxb="http://java.sun.com/xml/ns/jaxb"
	jaxb:version="2.1" 
	xmlns:annox="http://annox.dev.java.net" 
	jaxb:extensionBindingPrefixes="annox">



	<xsd:complexType name="FooType">
		<xsd:annotation>
			<xsd:appinfo>
				<annox:removeAnnotation class="javax.xml.bind.annotation.XmlType" />
			</xsd:appinfo>
		</xsd:annotation>
		<xsd:sequence>
			<xsd:element name="bar" type="xsd:string"/>
			<xsd:element name="foobar" type="xsd:string">
				<xsd:annotation>
					<xsd:appinfo>
						<annox:removeAnnotation class="javax.xml.bind.annotation.XmlElement" target="field" />
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
	xmlns:jaxb="http://java.sun.com/xml/ns/jaxb" xmlns:xs="http://www.w3.org/2001/XMLSchema"
	xmlns:xjc="http://java.sun.com/xml/ns/jaxb/xjc"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:annox="http://annox.dev.java.net"
	xsi:schemaLocation="http://java.sun.com/xml/ns/jaxb http://java.sun.com/xml/ns/jaxb/bindingschema_2_0.xsd"
	jaxb:extensionBindingPrefixes="xjc annox"
	version="2.1">

	<jaxb:bindings schemaLocation="schema.xsd" node="/xs:schema">
		<jaxb:bindings node="xs:complexType[@name='FooType']">
			<annox:removeAnnotation class="javax.xml.bind.annotation.XmlType" />
		</jaxb:bindings>
		<jaxb:bindings node="xs:complexType[@name='FooType']//xs:element[@name='foobar']">
			<annox:removeAnnotation class="javax.xml.bind.annotation.XmlElement" target="field" />
		</jaxb:bindings>
	</jaxb:bindings>

</jaxb:bindings>
````

You can use the following customization elements in the `http://annox.dev.java.net` namespace:
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

The `http://annox.dev.java.net` namespace must be declared in the `jaxb:extensionBindingPrefixes` attribute via prefix, ex.:

````
xmlns:annox="http://annox.dev.java.net"
jaxb:extensionBindingPrefixes="xjc annox"
````

Note: yes, I know that `http://annox.dev.java.net` no longer exists. Changing this namespace would break old builds.
This is just a namespace, there must not necessarily be content there. Treat it as a logical identifier, nothing else.


Using JAXB2 Annotate Plugin with Maven
--------------------------------------

* Add `org.jvnet.jaxb2_commons:jaxb2-basics-annotate` as XJC plugin
* Turn on the plugin using `-Xannotate` or `-XremoveAnnotation`switch
* Add artifact with your annotations as another XJC plugin

Example:

````xml
<plugin>
	<groupId>org.jvnet.jaxb2.maven2</groupId>
	<artifactId>maven-jaxb2-plugin</artifactId>
	<configuration>
		<extension>true</extension>
		<args>
			<arg>-Xannotate</arg>
			<arg>-XremoveAnnotation</arg>
		</args>
		<plugins>
			<plugin>
				<groupId>org.jvnet.jaxb2_commons</groupId>
				<artifactId>jaxb2-basics-annotate</artifactId>
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
See [this example](https://github.com/highsource/jaxb2-annotate-plugin/tree/master/tests/annox).

Note that annotations are first compiled in the `annotations` module and the added to the classpath of the `maven-jaxb2-plugin` in the `schema` module:

Using JAXB2 Annotate Plugin with Ant
------------------------------------
See [this example](https://github.com/highsource/jaxb2-annotate-plugin/blob/master/samples/annotate/project-build.xml).
>>>>>>> jaxb2-annotate-plugin/master
