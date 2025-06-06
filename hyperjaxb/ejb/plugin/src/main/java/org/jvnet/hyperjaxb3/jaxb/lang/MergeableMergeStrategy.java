package org.jvnet.hyperjaxb3.jaxb.lang;

import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Mergeable;
import org.jvnet.jaxb.lang.MergeStrategy;
import org.jvnet.jaxb.locator.ObjectLocator;

import java.util.Objects;

public class MergeableMergeStrategy implements MergeStrategy {

	private final MergeStrategy mergeStrategy;

	public MergeableMergeStrategy(MergeStrategy mergeStrategy) {
		Objects.requireNonNull(mergeStrategy, "Merge strategy must not be null.");
		this.mergeStrategy = mergeStrategy;
	}

	@Override
	public Boolean shouldBeMergedAndSet(ObjectLocator leftLocator, ObjectLocator rightLocator,
										boolean leftSet, boolean rightSet) {
		return mergeStrategy.shouldBeMergedAndSet(leftLocator, rightLocator, leftSet, rightSet);
	}

	public boolean merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
						 boolean left, boolean right, boolean leftSet, boolean rightSet) {
		return mergeStrategy.merge(leftLocator, rightLocator, left, right, leftSet, rightSet);
	}

	public byte merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			byte left, byte right, boolean leftSet, boolean rightSet) {
		return mergeStrategy.merge(leftLocator, rightLocator, left, right, leftSet, rightSet);
	}

	public char merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			char left, char right, boolean leftSet, boolean rightSet) {
		return mergeStrategy.merge(leftLocator, rightLocator, left, right, leftSet, rightSet);
	}

	public double merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			double left, double right, boolean leftSet, boolean rightSet) {
		return mergeStrategy.merge(leftLocator, rightLocator, left, right, leftSet, rightSet);
	}

	public float merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			float left, float right, boolean leftSet, boolean rightSet) {
		return mergeStrategy.merge(leftLocator, rightLocator, left, right, leftSet, rightSet);
	}

	public int merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			int left, int right, boolean leftSet, boolean rightSet) {
		return mergeStrategy.merge(leftLocator, rightLocator, left, right, leftSet, rightSet);
	}

	public long merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			long left, long right, boolean leftSet, boolean rightSet) {
		return mergeStrategy.merge(leftLocator, rightLocator, left, right, leftSet, rightSet);
	}

	public short merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			short left, short right, boolean leftSet, boolean rightSet) {
		return mergeStrategy.merge(leftLocator, rightLocator, left, right, leftSet, rightSet);
	}

	public Object merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			Object left, Object right, boolean leftSet, boolean rightSet) {
		if (left instanceof Mergeable) {
			final Mergeable mergeable = (Mergeable) left;
			if (mergeable.isMerge()) {
				return mergeStrategy.merge(leftLocator, rightLocator, left, right, leftSet, rightSet);
			} else {
				// Do not merge
				return mergeStrategy.merge(leftLocator, rightLocator, left, null, leftSet, rightSet);
			}
		} else {
			return mergeStrategy.merge(leftLocator, rightLocator, left, right, leftSet, rightSet);
		}
	}

	public boolean[] merge(ObjectLocator leftLocator,
			ObjectLocator rightLocator, boolean[] left, boolean[] right, boolean leftSet, boolean rightSet) {
		return mergeStrategy.merge(leftLocator, rightLocator, left, right, leftSet, rightSet);
	}

	public byte[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			byte[] left, byte[] right, boolean leftSet, boolean rightSet) {
		return mergeStrategy.merge(leftLocator, rightLocator, left, right, leftSet, rightSet);
	}

	public char[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			char[] left, char[] right, boolean leftSet, boolean rightSet) {
		return mergeStrategy.merge(leftLocator, rightLocator, left, right, leftSet, rightSet);
	}

	public double[] merge(ObjectLocator leftLocator,
			ObjectLocator rightLocator, double[] left, double[] right, boolean leftSet, boolean rightSet) {
		return mergeStrategy.merge(leftLocator, rightLocator, left, right, leftSet, rightSet);
	}

	public float[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			float[] left, float[] right, boolean leftSet, boolean rightSet) {
		return mergeStrategy.merge(leftLocator, rightLocator, left, right, leftSet, rightSet);
	}

	public int[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			int[] left, int[] right, boolean leftSet, boolean rightSet) {
		return mergeStrategy.merge(leftLocator, rightLocator, left, right, leftSet, rightSet);
	}

	public long[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			long[] left, long[] right, boolean leftSet, boolean rightSet) {
		return mergeStrategy.merge(leftLocator, rightLocator, left, right, leftSet, rightSet);
	}

	public short[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			short[] left, short[] right, boolean leftSet, boolean rightSet) {
		return mergeStrategy.merge(leftLocator, rightLocator, left, right, leftSet, rightSet);
	}

	public Object[] merge(ObjectLocator leftLocator,
			ObjectLocator rightLocator, Object[] left, Object[] right, boolean leftSet, boolean rightSet) {
		return mergeStrategy.merge(leftLocator, rightLocator, left, right, leftSet, rightSet);
	}

}
