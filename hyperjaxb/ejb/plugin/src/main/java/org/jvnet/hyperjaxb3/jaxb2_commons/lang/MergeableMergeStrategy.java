package org.jvnet.hyperjaxb3.jaxb2_commons.lang;

import org.apache.commons.lang3.Validate;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Mergeable;
import org.jvnet.jaxb2_commons.lang.MergeStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;

public class MergeableMergeStrategy implements MergeStrategy {

	private final MergeStrategy mergeStrategy;

	public MergeableMergeStrategy(MergeStrategy mergeStrategy) {
		Validate.notNull(mergeStrategy);
		this.mergeStrategy = mergeStrategy;
	}

	public boolean merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			boolean left, boolean right) {
		return mergeStrategy.merge(leftLocator, rightLocator, left, right);
	}

	public byte merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			byte left, byte right) {
		return mergeStrategy.merge(leftLocator, rightLocator, left, right);
	}

	public char merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			char left, char right) {
		return mergeStrategy.merge(leftLocator, rightLocator, left, right);
	}

	public double merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			double left, double right) {
		return mergeStrategy.merge(leftLocator, rightLocator, left, right);
	}

	public float merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			float left, float right) {
		return mergeStrategy.merge(leftLocator, rightLocator, left, right);
	}

	public int merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			int left, int right) {
		return mergeStrategy.merge(leftLocator, rightLocator, left, right);
	}

	public long merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			long left, long right) {
		return mergeStrategy.merge(leftLocator, rightLocator, left, right);
	}

	public short merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			short left, short right) {
		return mergeStrategy.merge(leftLocator, rightLocator, left, right);
	}

	public Object merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			Object left, Object right) {
		if (left instanceof Mergeable) {
			final Mergeable mergeable = (Mergeable) left;
			if (mergeable.isMerge()) {
				return mergeStrategy.merge(leftLocator, rightLocator, left, right);
			} else {
				// Do not merge
				return mergeStrategy.merge(leftLocator, rightLocator, left, null);
			}
		} else {
			return mergeStrategy.merge(leftLocator, rightLocator, left, right);
		}
	}

	public boolean[] merge(ObjectLocator leftLocator,
			ObjectLocator rightLocator, boolean[] left, boolean[] right) {
		return mergeStrategy.merge(leftLocator, rightLocator, left, right);
	}

	public byte[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			byte[] left, byte[] right) {
		return mergeStrategy.merge(leftLocator, rightLocator, left, right);
	}

	public char[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			char[] left, char[] right) {
		return mergeStrategy.merge(leftLocator, rightLocator, left, right);
	}

	public double[] merge(ObjectLocator leftLocator,
			ObjectLocator rightLocator, double[] left, double[] right) {
		return mergeStrategy.merge(leftLocator, rightLocator, left, right);
	}

	public float[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			float[] left, float[] right) {
		return mergeStrategy.merge(leftLocator, rightLocator, left, right);
	}

	public int[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			int[] left, int[] right) {
		return mergeStrategy.merge(leftLocator, rightLocator, left, right);
	}

	public long[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			long[] left, long[] right) {
		return mergeStrategy.merge(leftLocator, rightLocator, left, right);
	}

	public short[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			short[] left, short[] right) {
		return mergeStrategy.merge(leftLocator, rightLocator, left, right);
	}

	public Object[] merge(ObjectLocator leftLocator,
			ObjectLocator rightLocator, Object[] left, Object[] right) {
		return mergeStrategy.merge(leftLocator, rightLocator, left, right);
	}

}
