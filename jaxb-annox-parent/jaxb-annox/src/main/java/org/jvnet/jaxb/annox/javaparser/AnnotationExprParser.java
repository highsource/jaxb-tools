package org.jvnet.jaxb.annox.javaparser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;

import java.io.StringReader;
import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.lang3.Validate;

public class AnnotationExprParser {

	public List<AnnotationExpr> parse(String text) throws ParseException {
		Validate.notNull(text);
		final String classText = text + "\n" + "public class Dummy{}";
		final StringReader reader = new StringReader(classText);
		final CompilationUnit compilationUnit = JavaParser.parse(reader, true);
		final List<TypeDeclaration> typeDeclarations = compilationUnit
				.getTypes();
		if (typeDeclarations.size() > 1) {
			throw new ParseException(
					MessageFormat
							.format("Annotation [{0}] could not be parsed, it contains an unexpected type declaration.",
									text));
		}
		final TypeDeclaration typeDeclaration = typeDeclarations.get(0);
		if (!(typeDeclaration instanceof ClassOrInterfaceDeclaration)) {
			throw new ParseException(MessageFormat.format(
					"Expected [{0}] as type declaration.",
					ClassOrInterfaceDeclaration.class.getName()));
		}
		final ClassOrInterfaceDeclaration classDeclaration = (ClassOrInterfaceDeclaration) typeDeclaration;
		if (!"Dummy".equals(classDeclaration.getName())) {
			throw new ParseException(MessageFormat.format(
					"Expected [{0}] as type declaration.", "Dummy"));
		}

		final List<AnnotationExpr> annotations = typeDeclaration
				.getAnnotations();

		if (annotations == null || annotations.isEmpty()) {
			throw new ParseException(
					MessageFormat
							.format("Annotation [{0}] could not be parsed, it does not seem to contain an annotation declaration.",
									text));
		}
		return annotations;
	}
}
