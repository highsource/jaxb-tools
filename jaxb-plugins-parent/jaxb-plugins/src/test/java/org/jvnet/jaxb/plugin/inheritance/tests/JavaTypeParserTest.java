package org.jvnet.jaxb.plugin.inheritance.tests;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.Type;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import junit.framework.TestCase;

public class JavaTypeParserTest extends TestCase {

	public void testParse() throws Exception {

		final String text = "public class Dummy implements java.util.Comparator<java.lang.Integer>{}";

		final CompilationUnit compilationUnit = StaticJavaParser.parse(
				new ByteArrayInputStream(text.getBytes("UTF-8")), StandardCharsets.UTF_8);
		final NodeList<TypeDeclaration<?>> typeDeclarations = compilationUnit.getTypes();
		assertEquals(1, typeDeclarations.size());
		final TypeDeclaration typeDeclaration = typeDeclarations.get(0);
		assertTrue(typeDeclaration instanceof ClassOrInterfaceDeclaration);
		final ClassOrInterfaceDeclaration classDeclaration = (ClassOrInterfaceDeclaration) typeDeclaration;
		assertEquals("Dummy", classDeclaration.getNameAsString());
		final List<ClassOrInterfaceType> implementedInterfaces = classDeclaration.getImplementedTypes();
		assertEquals(1, implementedInterfaces.size());
		final ClassOrInterfaceType implementedInterface = implementedInterfaces
				.get(0);
		assertEquals("Comparator", implementedInterface.getNameAsString());
		final List<Type> typeArgs = implementedInterface.getTypeArguments().orElse(null);
		assertEquals(1, typeArgs.size());
		final Type typeArg = typeArgs.get(0);
		assertTrue(typeArg instanceof ReferenceType);
		final ReferenceType referenceTypeArg = (ReferenceType) typeArg;
		final Type referencedType = referenceTypeArg.getElementType();
		assertTrue(referencedType instanceof ClassOrInterfaceType);
		final ClassOrInterfaceType typeArgType = (ClassOrInterfaceType) referencedType;
		assertEquals("Integer", typeArgType.getNameAsString());

	}
}
