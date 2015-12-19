package org.jvnet.jaxb2_commons.lang;

import org.jvnet.jaxb2_commons.locator.ObjectLocator;

public interface MergeStrategy2 extends MergeStrategy {

	public Boolean shouldBeSet(ObjectLocator leftLocator,
			ObjectLocator rightLocator, boolean leftSet, boolean rightSet);

	public boolean merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			boolean left, boolean leftSet, boolean right, boolean rightSet);

	public byte merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			byte left, boolean leftSet, byte right, boolean rightSet);

	public char merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			char left, boolean leftSet, char right, boolean rightSet);

	public double merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			double left, boolean leftSet, double right, boolean rightSet);

	public float merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			float left, boolean leftSet, float right, boolean rightSet);

	public int merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			int left, boolean leftSet, int right, boolean rightSet);

	public long merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			long left, boolean leftSet, long right, boolean rightSet);

	public short merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			short left, boolean leftSet, short right, boolean rightSet);

	public Object merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			Object left, boolean leftSet, Object right, boolean rightSet);

	public boolean[] merge(ObjectLocator leftLocator,
			ObjectLocator rightLocator, boolean[] left, boolean leftSet,
			boolean[] right, boolean rightSet);

	public byte[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			byte[] left, boolean leftSet, byte[] right, boolean rightSet);

	public char[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			char[] left, boolean leftSet, char[] right, boolean rightSet);

	public double[] merge(ObjectLocator leftLocator,
			ObjectLocator rightLocator, double[] left, boolean leftSet,
			double[] right, boolean rightSet);

	public float[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			float[] left, boolean leftSet, float[] right, boolean rightSet);

	public int[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			int[] left, boolean leftSet, int[] right, boolean rightSet);

	public long[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			long[] left, boolean leftSet, long[] right, boolean rightSet);

	public short[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			short[] left, boolean leftSet, short[] right, boolean rightSet);

	public Object[] merge(ObjectLocator leftLocator,
			ObjectLocator rightLocator, Object[] left, boolean leftSet,
			Object[] right, boolean rightSet);
}
