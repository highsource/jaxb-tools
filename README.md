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
