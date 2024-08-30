package org.jvnet.jaxb.annox.parser.tests;

import com.github.javaparser.ast.expr.AnnotationExpr;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.jvnet.jaxb.annox.javaparser.AnnotationExprParser;
import org.jvnet.jaxb.annox.model.XAnnotation;
import org.jvnet.jaxb.annox.parser.XAnnotationParser;

public class JavaTypeParserTest {

    @Test
	public void testParse() throws Exception {

		final String text = "@org.jvnet.jaxb.annox.parser.tests.A("
				+ "booleanField = false,\n"
				+ "byteField = 0,\n"
				+ "charField = 'a',\n"
				+ "classField = java.lang.String.class,\n"
				+ "doubleField = 1,\n"
				+ "enumField = org.jvnet.jaxb.annox.parser.tests.E.ONE,\n"
				+ "floatField = 2.3f,\n"
				+ "intField = 4,\n"
				+ "longField = 5,\n"
				+ "shortField = 6,\n"
				+ "stringField = \"7\",\n"
				+ "annotationField = @org.jvnet.jaxb.annox.parser.tests.B(\n"
				+ "booleanArrayField = { false, true },\n"
				+ "byteArrayField = { 0, 1 },\n"
				+ "charArrayField = { 'a', 'b' },\n"
				+ "classArrayField = {	java.lang.String.class, java.lang.Boolean.class },\n"
				+ "doubleArrayField = { 2, 3 },\n"
				+ "enumArrayField = { org.jvnet.jaxb.annox.parser.tests.E.ONE, org.jvnet.jaxb.annox.parser.tests.E.TWO },\n"
				+ "floatArrayField = { 4.5f, 6.7f },\n"
				+ "intArrayField = { 8, 9 },\n"
				+ "longArrayField = { 10, 11 },\n"
				+ "shortArrayField = { 12, 13 },\n"
				+ "stringArrayField = { \"14\", \"15\", \"16\", \"17\" },\n"
				+ "annotationArrayField = { @org.jvnet.jaxb.annox.parser.tests.B.C, @org.jvnet.jaxb.annox.parser.tests.B.C }))";

		final AnnotationExprParser parser = new AnnotationExprParser();

		List<AnnotationExpr> annotations = parser.parse(text);

		Assertions.assertEquals(1, annotations.size());

		final AnnotationExpr annotationExpr = annotations.get(0);

		final XAnnotationParser xAnnotationParser = new XAnnotationParser();
		XAnnotation<?> xannotation = xAnnotationParser.parse(annotationExpr);
        Assertions.assertNotNull(xannotation);

	}
}
