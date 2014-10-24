# JAXB2 Basics

JAXB2 Basics is an [open source](https://github.com/highsource/jaxb2-basics/blob/master/LICENSE) project
which provides useful plugins and tools for [JAXB 2.x reference implementation](https://jaxb.java.net/).

Please refer to the [[wiki|Home]] for documentation.

## JAXB2 Basics Plugins
* Equals Plugin - generates reflection-free `equals(...)` method.
* HashCode Plugin - generates reflection-free `hashCode()` method.
* ToString Plugin - generates reflection-free `toString()` methods.
* Copyable Plugin - generates reflection-free `copy(...)` deep copying.
* Mergeable Plugin - generates reflection-free `merge(...)` methods to merge data from two source objects into the given object.
* Inheritance Plugin - makes schema-derived classes extend certain class or implement certain interfaces.
* Wildcard Plugin - allows you to specify the wildcard mode for the wildcard properties.
* AutoInheritance Plugin - makes classes derived from global elements or complex types extend or implement certain classes or interfaces automatically.
* Setters Plugin - generates setters for collections.
* [[Simplify Plugin|JAXB2 Simplify Plugin]] - simplifies weird properties like `aOrBOrC`.
* [[EnumValue Plugin|JAXB2 EnumValue Plugin]] - makes all the generated enums implement the `EnumValue<T>` interface.
* JAXBIndex Plugin - generated `jaxb.index` files listing schema-derived classes.
