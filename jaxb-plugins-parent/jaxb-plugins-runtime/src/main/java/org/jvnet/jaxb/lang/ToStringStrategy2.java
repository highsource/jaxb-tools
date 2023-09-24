package org.jvnet.jaxb.lang;

import org.jvnet.jaxb.locator.ObjectLocator;

public interface ToStringStrategy2 {
/*
	public StringBuilder append(ObjectLocator locator, StringBuilder stringBuilder, boolean value);
	public StringBuilder append(ObjectLocator locator, StringBuilder stringBuilder, byte value);
	public StringBuilder append(ObjectLocator locator, StringBuilder stringBuilder, char value);
	public StringBuilder append(ObjectLocator locator, StringBuilder stringBuilder, double value);
	public StringBuilder append(ObjectLocator locator, StringBuilder stringBuilder, float value);
	public StringBuilder append(ObjectLocator locator, StringBuilder stringBuilder, int value);
	public StringBuilder append(ObjectLocator locator, StringBuilder stringBuilder, long value);
	public StringBuilder append(ObjectLocator locator, StringBuilder stringBuilder, short value);
	public StringBuilder append(ObjectLocator locator, StringBuilder stringBuilder, Object value);
	public StringBuilder append(ObjectLocator locator, StringBuilder stringBuilder, boolean[] value);
	public StringBuilder append(ObjectLocator locator, StringBuilder stringBuilder, byte[] value);
	public StringBuilder append(ObjectLocator locator, StringBuilder stringBuilder, char[] value);
	public StringBuilder append(ObjectLocator locator, StringBuilder stringBuilder, double[] value);
	public StringBuilder append(ObjectLocator locator, StringBuilder stringBuilder, float[] value);
	public StringBuilder append(ObjectLocator locator, StringBuilder stringBuilder, int[] value);
	public StringBuilder append(ObjectLocator locator, StringBuilder stringBuilder, long[] value);
	public StringBuilder append(ObjectLocator locator, StringBuilder stringBuilder, short[] value);
	public StringBuilder append(ObjectLocator locator, StringBuilder stringBuilder, Object[] value);
*/
	public StringBuilder appendStart(ObjectLocator parentLocator, Object parent, StringBuilder stringBuilder);
	public StringBuilder appendEnd(ObjectLocator parentLocator, Object parent, StringBuilder stringBuilder);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, boolean value, boolean valueSet);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, byte value, boolean valueSet);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, char value, boolean valueSet);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, double value, boolean valueSet);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, float value, boolean valueSet);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, int value, boolean valueSet);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, long value, boolean valueSet);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, short value, boolean valueSet);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, Object value, boolean valueSet);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, boolean[] value, boolean valueSet);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, byte[] value, boolean valueSet);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, char[] value, boolean valueSet);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, double[] value, boolean valueSet);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, float[] value, boolean valueSet);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, int[] value, boolean valueSet);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, long[] value, boolean valueSet);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, short[] value, boolean valueSet);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, Object[] value, boolean valueSet);
}
