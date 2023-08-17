package org.jvnet.annox.parser.tests;

@A(
	booleanField = false, 
	byteField = 0,
	charField = 'a',
	classField = String[][][].class,
	doubleField = 1,
	enumField = E.ONE,
	floatField = 2.3f,
	intField = 4,
	longField = 5,
	shortField = 6,
	stringField = "7",
	annotationField = @B(
			annotationArrayField = {@B.C, @B.C },
			booleanArrayField = { false, true },
			byteArrayField = {0, 1 },
			charArrayField = { 'a', 'b' },
			classArrayField = {String.class, Boolean.class },
			doubleArrayField = { 2, 3 },
			enumArrayField = {	E.ONE, E.TWO },
			floatArrayField = { 4.5f, 6.7f },
			intArrayField = { 8, 9 },
			longArrayField = { 10, -9223372036854775808L },
			shortArrayField = { 12, 13 },
			stringArrayField = { "14", "15", "16", "17" }))
public class Two {

}