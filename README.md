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
