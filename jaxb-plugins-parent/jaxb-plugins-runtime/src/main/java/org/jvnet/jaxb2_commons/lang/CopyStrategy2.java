package org.jvnet.jaxb2_commons.lang;

import org.jvnet.jaxb2_commons.locator.ObjectLocator;

public interface CopyStrategy2 {

	public Boolean shouldBeCopiedAndSet(ObjectLocator locator, boolean valueSet);

	public boolean copy(ObjectLocator locator, boolean value, boolean valueSet);

	public byte copy(ObjectLocator locator, byte value, boolean valueSet);

	public char copy(ObjectLocator locator, char value, boolean valueSet);

	public double copy(ObjectLocator locator, double value, boolean valueSet);

	public float copy(ObjectLocator locator, float value, boolean valueSet);

	public int copy(ObjectLocator locator, int value, boolean valueSet);

	public long copy(ObjectLocator locator, long value, boolean valueSet);

	public short copy(ObjectLocator locator, short value, boolean valueSet);

	public Object copy(ObjectLocator locator, Object value, boolean valueSet);

	public boolean[] copy(ObjectLocator locator, boolean[] value, boolean valueSet);

	public byte[] copy(ObjectLocator locator, byte[] value, boolean valueSet);

	public char[] copy(ObjectLocator locator, char[] value, boolean valueSet);

	public double[] copy(ObjectLocator locator, double[] value, boolean valueSet);

	public float[] copy(ObjectLocator locator, float[] value, boolean valueSet);

	public int[] copy(ObjectLocator locator, int[] value, boolean valueSet);

	public long[] copy(ObjectLocator locator, long[] value, boolean valueSet);

	public short[] copy(ObjectLocator locator, short[] value, boolean valueSet);

	public Object[] copy(ObjectLocator locator, Object[] value, boolean valueSet);

}
