package org.jvnet.jaxb2_commons.lang;

import org.jvnet.jaxb2_commons.locator.ObjectLocator;

@Deprecated
public interface MergeStrategy {

	public boolean merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			boolean left, boolean right);

	public byte merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			byte left, byte right);

	public char merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			char left, char right);

	public double merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			double left, double right);

	public float merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			float left, float right);

	public int merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			int left, int right);

	public long merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			long left, long right);

	public short merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			short left, short right);

	public Object merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			Object left, Object right);

	public boolean[] merge(ObjectLocator leftLocator,
			ObjectLocator rightLocator, boolean[] left, boolean[] right);

	public byte[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			byte[] left, byte[] right);

	public char[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			char[] left, char[] right);

	public double[] merge(ObjectLocator leftLocator,
			ObjectLocator rightLocator, double[] left, double[] right);

	public float[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			float[] left, float[] right);

	public int[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			int[] left, int[] right);

	public long[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			long[] left, long[] right);

	public short[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			short[] left, short[] right);

	public Object[] merge(ObjectLocator leftLocator,
			ObjectLocator rightLocator, Object[] left, Object[] right);
}
