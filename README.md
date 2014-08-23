jaxb2-annotate-plugin
=====================

JAXB2 Annotate Plugin is capable of adding arbitrary annotations to the generated sources.

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
You can use the following elements:
* `annotate` with the optional `target` attribute
** `package`
** `class`
** `getter`
** `setter`
** `setter-parameter`
** `field`
* `annotateProperty`
* `annotatePackage`
* `annotateClass`
* `annotateElement`
* `annotateEnum`
* `annotateEnumConstant`

Using JAXB2 Annotate Plugin with Maven
--------------------------------------
See [this example](https://github.com/highsource/jaxb2-annotate-plugin/tree/master/tests/annox).

Note that annotations are first compiled in the `annotations` module and the added to the classpath of the `maven-jaxb2-plugin` in the `schema` module:

````xml
			<plugin>
				<groupId>org.jvnet.jaxb2.maven2</groupId>
				<artifactId>maven-jaxb2-plugin</artifactId>
				<configuration>
					<extension>true</extension>
					<args>
						<arg>-Xannotate</arg>
					</args>
					<plugins>
						<plugin>
							<groupId>org.jvnet.jaxb2_commons</groupId>
							<artifactId>jaxb2-basics-annotate</artifactId>
						</plugin>
						<plugin>
							<groupId>org.jvnet.jaxb2_commons</groupId>
							<artifactId>jaxb2-annotate-plugin-test-annox-annotations</artifactId>
						</plugin>
					</plugins>
				</configuration>
			</plugin>
````

Using JAXB2 Annotate Plugin with Ant
------------------------------------
See [this example](https://github.com/highsource/jaxb2-annotate-plugin/blob/master/samples/annotate/project-build.xml).