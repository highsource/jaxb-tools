package org.jvnet.hyperjaxb3.codemodel.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JType;

public class JTypeUtils {

	private JTypeUtils() {
	}

	public static boolean isBasicType(final JType type) {
		final List<JType> basicTypes = JTypeUtils.getBasicTypes(type.owner());

		return basicTypes.contains(type);
	}

	public static boolean isTemporalType(final JType type) {
		final List<JType> temporalTypes = JTypeUtils.getTemporalTypes(type.owner());

		return temporalTypes.contains(type);
	}

	public static List<JType> getBasicTypes(final JCodeModel codeModel) {
		final List<JType> basicTypes = List.of(codeModel.BOOLEAN,
				codeModel.BOOLEAN.boxify(), codeModel.BYTE,
				codeModel.BYTE.boxify(), codeModel.CHAR,
				codeModel.CHAR.boxify(), codeModel.DOUBLE,
				codeModel.DOUBLE.boxify(), codeModel.FLOAT,
				codeModel.FLOAT.boxify(), codeModel.INT,
				codeModel.INT.boxify(), codeModel.LONG,
				codeModel.LONG.boxify(), codeModel.SHORT,
				codeModel.SHORT.boxify(), codeModel.ref(String.class),
				codeModel.ref(BigInteger.class),
				codeModel.ref(BigDecimal.class),
				codeModel.ref(java.util.Date.class),
				codeModel.ref(java.util.Calendar.class),
				codeModel.ref(java.sql.Date.class),
				codeModel.ref(java.sql.Time.class),
				codeModel.ref(java.sql.Timestamp.class),
				codeModel.BYTE.array(),	codeModel.BYTE.boxify().array(),
				codeModel.CHAR.array(),	codeModel.CHAR.boxify().array())

		;
		return basicTypes;
	}

	public static List<JType> getTemporalTypes(final JCodeModel codeModel) {
		final List<JType> basicTypes = List.of(
				codeModel.ref(java.util.Date.class),
				codeModel.ref(java.util.Calendar.class),
				codeModel.ref(java.sql.Date.class),
				codeModel.ref(java.sql.Time.class),
				codeModel.ref(java.sql.Timestamp.class));
		return basicTypes;
	}
}
