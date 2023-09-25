package org.jvnet.jaxb.lang;

import org.jvnet.jaxb.locator.ObjectLocator;

public interface ToStringStrategy {
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
