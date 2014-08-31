package org.jvnet.jaxb2_commons.lang;

import org.jvnet.jaxb2_commons.locator.ObjectLocator;

public interface ToStringStrategy {

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

	public StringBuilder appendStart(ObjectLocator parentLocator, Object parent, StringBuilder stringBuilder);
	public StringBuilder appendEnd(ObjectLocator parentLocator, Object parent, StringBuilder stringBuilder);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, boolean value);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, byte value);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, char value);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, double value);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, float value);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, int value);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, long value);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, short value);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, Object value);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, boolean[] value);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, byte[] value);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, char[] value);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, double[] value);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, float[] value);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, int[] value);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, long[] value);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, short[] value);
	public StringBuilder appendField(ObjectLocator parentLocator, Object parent, String fieldName, StringBuilder stringBuilder, Object[] value);
}
