package org.jvnet.jaxb.plugin.inheritance.tests;

import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.type.ReferenceType;
import japa.parser.ast.type.Type;

import java.io.ByteArrayInputStream;
import java.util.List;

import junit.framework.TestCase;

public class JavaTypeParserTest extends TestCase {

	public void testParse() throws Exception {

		final String text = "public class Dummy implements java.util.Comparator<java.lang.Integer>{}";

		final CompilationUnit compilationUnit = JavaParser.parse(
				new ByteArrayInputStream(text.getBytes("UTF-8")), "UTF-8");
		final List<TypeDeclaration> typeDeclarations = compilationUnit
				.getTypes();
		assertEquals(1, typeDeclarations.size());
		final TypeDeclaration typeDeclaration = typeDeclarations.get(0);
		assertTrue(typeDeclaration instanceof ClassOrInterfaceDeclaration);
		final ClassOrInterfaceDeclaration classDeclaration = (ClassOrInterfaceDeclaration) typeDeclaration;
		assertEquals("Dummy", classDeclaration.getName());
		final List<ClassOrInterfaceType> implementedInterfaces = classDeclaration
				.getImplements();
		assertEquals(1, implementedInterfaces.size());
		final ClassOrInterfaceType implementedInterface = implementedInterfaces
				.get(0);
		assertEquals("Comparator", implementedInterface.getName());
		final List<Type> typeArgs = implementedInterface.getTypeArgs();
		assertEquals(1, typeArgs.size());
		final Type typeArg = typeArgs.get(0);
		assertTrue(typeArg instanceof ReferenceType);
		final ReferenceType referenceTypeArg = (ReferenceType) typeArg;
		final Type referencedType = referenceTypeArg.getType();
		assertTrue(referencedType instanceof ClassOrInterfaceType);
		final ClassOrInterfaceType typeArgType = (ClassOrInterfaceType) referencedType;
		assertEquals("Integer", typeArgType.getName());

	}
}
