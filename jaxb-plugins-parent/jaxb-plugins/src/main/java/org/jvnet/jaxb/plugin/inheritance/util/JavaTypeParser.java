package org.jvnet.jaxb.plugin.inheritance.util;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JType;

public class JavaTypeParser {

	static {
//		JavaParser.setCacheParser(false);
	}

	private final TypeToJTypeConvertingVisitor typeToJTypeConvertingVisitor;

	public JavaTypeParser() {
		this(Collections.<String, JClass> emptyMap());
	}

	public JavaTypeParser(Map<String, JClass> knownClasses) {
		Validate.notNull(knownClasses);
		this.typeToJTypeConvertingVisitor = new TypeToJTypeConvertingVisitor(
				knownClasses);

	}

	public JClass parseClass(String _class, JCodeModel codeModel) {
		JType type = parseType(_class, codeModel);
		if (type instanceof JClass) {
			return (JClass) type;
		} else {
			throw new IllegalArgumentException("Type [" + _class
					+ "] is not a class.");
		}
	}

	private JType parseType(String type, JCodeModel codeModel) {
		final String text = "public class Ignored extends " + type + " {}";
		try {
			CompilationUnit compilationUnit = StaticJavaParser.parse(
					new ByteArrayInputStream(text.getBytes("UTF-8")), StandardCharsets.UTF_8);
			final List<TypeDeclaration<?>> typeDeclarations = compilationUnit.getTypes();
			final TypeDeclaration typeDeclaration = typeDeclarations.get(0);
			final ClassOrInterfaceDeclaration classDeclaration = (ClassOrInterfaceDeclaration) typeDeclaration;
			final List<ClassOrInterfaceType> _extended = classDeclaration.getExtendedTypes();
			final ClassOrInterfaceType classOrInterfaceType = _extended.get(0);

			return classOrInterfaceType.accept(
					this.typeToJTypeConvertingVisitor, codeModel);
		} catch (ParseProblemException pex) {
			throw new IllegalArgumentException(
					"Could not parse the type definition [" + type + "].", pex);
		} catch (UnsupportedEncodingException uex) {
			throw new UnsupportedOperationException(uex);
		}
	}
}
