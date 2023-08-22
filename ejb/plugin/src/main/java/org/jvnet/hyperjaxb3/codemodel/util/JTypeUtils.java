package org.jvnet.hyperjaxb3.codemodel.util;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.commons.lang3.ArrayUtils;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JType;

public class JTypeUtils {

	private JTypeUtils() {
	}

	public static boolean isBasicType(final JType type) {
		final JType[] basicTypes = JTypeUtils.getBasicTypes(type.owner());

		return ArrayUtils.contains(basicTypes, type);
	}

	public static boolean isTemporalType(final JType type) {
		final JType[] temporalTypes = JTypeUtils.getTemporalTypes(type.owner());

		return ArrayUtils.contains(temporalTypes, type);
	}

	public static JType[] getBasicTypes(final JCodeModel codeModel) {
		final JType[] basicTypes = new JType[] { codeModel.BOOLEAN,
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
				codeModel.CHAR.array(),	codeModel.CHAR.boxify().array() }

		;
		return basicTypes;
	}

	public static JType[] getTemporalTypes(final JCodeModel codeModel) {
		final JType[] basicTypes = new JType[] {
				codeModel.ref(java.util.Date.class),
				codeModel.ref(java.util.Calendar.class),
				codeModel.ref(java.sql.Date.class),
				codeModel.ref(java.sql.Time.class),
				codeModel.ref(java.sql.Timestamp.class) };
		return basicTypes;
	}
}
