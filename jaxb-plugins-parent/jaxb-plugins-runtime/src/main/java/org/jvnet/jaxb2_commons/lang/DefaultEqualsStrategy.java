package org.jvnet.jaxb2_commons.lang;

import static org.jvnet.jaxb2_commons.locator.util.LocatorUtils.item;

import org.jvnet.jaxb2_commons.locator.ObjectLocator;

@SuppressWarnings("deprecation")
public class DefaultEqualsStrategy implements EqualsStrategy2, EqualsStrategy {

	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, Object lhs, Object rhs) {

		if (lhs == rhs) {
			return true;
		}
		if (lhs == null || rhs == null) {
			return false;
		}
		Class<?> lhsClass = lhs.getClass();
		if (!lhsClass.isArray()) {
			return equalsInternal(leftLocator, rightLocator, lhs, rhs);
		} else if (lhs.getClass() != rhs.getClass()) {
			// Here when we compare different dimensions, for example: a
			// boolean[][] to a boolean[]
			return false;
		}
		// 'Switch' on type of array, to dispatch to the correct handler
		// This handles multi dimensional arrays of the same depth
		else if (lhs instanceof long[]) {
			return equals(leftLocator, rightLocator, (long[]) lhs, (long[]) rhs);
		} else if (lhs instanceof int[]) {
			return equals(leftLocator, rightLocator, (int[]) lhs, (int[]) rhs);
		} else if (lhs instanceof short[]) {
			return equals(leftLocator, rightLocator, (short[]) lhs,
					(short[]) rhs);
		} else if (lhs instanceof char[]) {
			return equals(leftLocator, rightLocator, (char[]) lhs, (char[]) rhs);
		} else if (lhs instanceof byte[]) {
			return equals(leftLocator, rightLocator, (byte[]) lhs, (byte[]) rhs);
		} else if (lhs instanceof double[]) {
			return equals(leftLocator, rightLocator, (double[]) lhs,
					(double[]) rhs);
		} else if (lhs instanceof float[]) {
			return equals(leftLocator, rightLocator, (float[]) lhs,
					(float[]) rhs);
		} else if (lhs instanceof boolean[]) {
			return equals(leftLocator, rightLocator, (boolean[]) lhs,
					(boolean[]) rhs);
		} else if (lhs instanceof Equals2[]) {
			return equalsInternal(leftLocator, rightLocator, (Equals2[]) lhs,
					(Equals2[]) rhs);
		} else if (lhs instanceof Equals[]) {
			return equalsInternal(leftLocator, rightLocator, (Equals[]) lhs,
					(Equals[]) rhs);
		} else if (lhs instanceof Enum[]) {
			return equalsInternal(leftLocator, rightLocator, (Enum<?>[]) lhs,
					(Enum<?>[]) rhs);
		} else {
			// Not an array of primitives
			return equals(leftLocator, rightLocator, (Object[]) lhs,
					(Object[]) rhs);
		}
	}

	protected boolean equalsInternal(ObjectLocator leftLocator,
			ObjectLocator rightLocator, Object lhs, Object rhs) {
		if (lhs == rhs) {
			return true;
		}
		if (lhs == null || rhs == null) {
			return false;
		}
		if (lhs instanceof Equals2 && rhs instanceof Equals2) {
			return equalsInternal(leftLocator, rightLocator, (Equals2) lhs,
					(Equals2) rhs);
		} else if (lhs instanceof Equals && rhs instanceof Equals) {
			return equalsInternal(leftLocator, rightLocator, (Equals) lhs,
					(Equals) rhs);
		} else if (lhs instanceof Enum<?> && rhs instanceof Enum<?>) {
			return equalsInternal(leftLocator, rightLocator, (Enum<?>) lhs,
					(Enum<?>) rhs);
		} else {
			return lhs.equals(rhs);
		}
	}

	protected boolean equalsInternal(ObjectLocator leftLocator,
			ObjectLocator rightLocator, Enum<?> lhs, Enum<?> rhs) {
		if (lhs == rhs) {
			return true;
		}
		if (lhs == null || rhs == null) {
			return false;
		}
		if (lhs instanceof Equals2 && rhs instanceof Equals2) {
			return equalsInternal(leftLocator, rightLocator, (Equals2) lhs,
					(Equals2) rhs);
		} else if (lhs instanceof Equals && rhs instanceof Equals) {
			return equalsInternal(leftLocator, rightLocator, (Equals) lhs,
					(Equals) rhs);
		} else {
			return lhs.equals(rhs);
		}
	}

	protected boolean equalsInternal(ObjectLocator leftLocator,
			ObjectLocator rightLocator, Equals2 lhs, Equals2 rhs) {
		if (lhs == rhs) {
			return true;
		}
		if (lhs == null || rhs == null) {
			return false;
		}
		return lhs.equals(leftLocator, rightLocator, rhs, this);
	}

	protected boolean equalsInternal(ObjectLocator leftLocator,
			ObjectLocator rightLocator, Equals lhs, Equals rhs) {
		if (lhs == rhs) {
			return true;
		}
		if (lhs == null || rhs == null) {
			return false;
		}
		return lhs.equals(leftLocator, rightLocator, rhs, this);
	}

	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, boolean left, boolean right) {
		return left == right;
	}

	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, byte left, byte right) {
		return left == right;
	}

	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, char left, char right) {
		return left == right;
	}

	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, double left, double right) {

		return equals(leftLocator, rightLocator, Double.doubleToLongBits(left),
				Double.doubleToLongBits(right));
	}

	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, float left, float right) {
		return equals(leftLocator, rightLocator, Float.floatToIntBits(left),
				Float.floatToIntBits(right));
	}

	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, long left, long right) {
		return left == right;
	}

	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, int left, int right) {
		return left == right;
	}

	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, short left, short right) {
		return left == right;
	}

	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, Object[] left, Object[] right) {

		if (left == right) {
			return true;
		}
		if (left == null || right == null) {
			return false;
		}
		if (left.length != right.length) {
			return false;
		}
		for (int i = 0; i < left.length; ++i) {
			if (!equals(item(leftLocator, i, left[i]),
					item(rightLocator, i, right[i]), left[i], right[i])) {
				return false;
			}
		}
		return true;
	}

	protected boolean equalsInternal(ObjectLocator leftLocator,
			ObjectLocator rightLocator, Equals2[] left, Equals2[] right) {

		if (left == right) {
			return true;
		}
		if (left == null || right == null) {
			return false;
		}
		if (left.length != right.length) {
			return false;
		}
		for (int i = 0; i < left.length; ++i) {
			if (!equalsInternal(item(leftLocator, i, left[i]),
					item(rightLocator, i, right[i]), left[i], right[i])) {
				return false;
			}
		}
		return true;
	}

	protected boolean equalsInternal(ObjectLocator leftLocator,
			ObjectLocator rightLocator, Equals[] left, Equals[] right) {

		if (left == right) {
			return true;
		}
		if (left == null || right == null) {
			return false;
		}
		if (left.length != right.length) {
			return false;
		}
		for (int i = 0; i < left.length; ++i) {
			if (!equalsInternal(item(leftLocator, i, left[i]),
					item(rightLocator, i, right[i]), left[i], right[i])) {
				return false;
			}
		}
		return true;
	}

	protected boolean equalsInternal(ObjectLocator leftLocator,
			ObjectLocator rightLocator, Enum<?>[] left, Enum<?>[] right) {

		if (left == right) {
			return true;
		}
		if (left == null || right == null) {
			return false;
		}
		if (left.length != right.length) {
			return false;
		}
		for (int i = 0; i < left.length; ++i) {
			if (!equalsInternal(item(leftLocator, i, left[i]),
					item(rightLocator, i, right[i]), left[i], right[i])) {
				return false;
			}
		}
		return true;
	}

	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, boolean[] left, boolean[] right) {
		if (left == right) {
			return true;
		}
		if (left == null || right == null) {
			return false;
		}
		if (left.length != right.length) {
			return false;
		}
		for (int i = 0; i < left.length; ++i) {
			if (!equals(item(leftLocator, i, left[i]),
					item(rightLocator, i, right[i]), left[i], right[i])) {
				return false;
			}
		}
		return true;
	}

	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, byte[] left, byte[] right) {
		if (left == right) {
			return true;
		}
		if (left == null || right == null) {
			return false;
		}
		if (left.length != right.length) {
			return false;
		}
		for (int i = 0; i < left.length; ++i) {
			if (!equals(item(leftLocator, i, left[i]),
					item(rightLocator, i, right[i]), left[i], right[i])) {
				return false;
			}
		}
		return true;
	}

	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, char[] left, char[] right) {
		if (left == right) {
			return true;
		}
		if (left == null || right == null) {
			return false;
		}
		if (left.length != right.length) {
			return false;
		}
		for (int i = 0; i < left.length; ++i) {
			if (!equals(item(leftLocator, i, left[i]),
					item(rightLocator, i, right[i]), left[i], right[i])) {
				return false;
			}
		}
		return true;
	}

	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, double[] left, double[] right) {
		if (left == right) {
			return true;
		}
		if (left == null || right == null) {
			return false;
		}
		if (left.length != right.length) {
			return false;
		}
		for (int i = 0; i < left.length; ++i) {
			if (!equals(item(leftLocator, i, left[i]),
					item(rightLocator, i, right[i]), left[i], right[i])) {
				return false;
			}
		}
		return true;
	}

	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, float[] left, float[] right) {
		if (left == right) {
			return true;
		}
		if (left == null || right == null) {
			return false;
		}
		if (left.length != right.length) {
			return false;
		}
		for (int i = 0; i < left.length; ++i) {
			if (!equals(item(leftLocator, i, left[i]),
					item(rightLocator, i, right[i]), left[i], right[i])) {
				return false;
			}
		}
		return true;
	}

	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, long[] left, long[] right) {
		if (left == right) {
			return true;
		}
		if (left == null || right == null) {
			return false;
		}
		if (left.length != right.length) {
			return false;
		}
		for (int i = 0; i < left.length; ++i) {
			if (!equals(item(leftLocator, i, left[i]),
					item(rightLocator, i, right[i]), left[i], right[i])) {
				return false;
			}
		}
		return true;
	}

	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, int[] left, int[] right) {
		if (left == right) {
			return true;
		}
		if (left == null || right == null) {
			return false;
		}
		if (left.length != right.length) {
			return false;
		}
		for (int i = 0; i < left.length; ++i) {
			if (!equals(item(leftLocator, i, left[i]),
					item(rightLocator, i, right[i]), left[i], right[i])) {
				return false;
			}
		}
		return true;
	}

	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, short[] left, short[] right) {
		if (left == right) {
			return true;
		}
		if (left == null || right == null) {
			return false;
		}
		if (left.length != right.length) {
			return false;
		}
		for (int i = 0; i < left.length; ++i) {
			if (!equals(item(leftLocator, i, left[i]),
					item(rightLocator, i, right[i]), left[i], right[i])) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, boolean left, boolean right,
			boolean leftSet, boolean rightSet) {
		return (leftSet && rightSet) ? equals(leftLocator, rightLocator, left,
				right) : leftSet == rightSet;
	}

	@Override
	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, byte left, byte right, boolean leftSet,
			boolean rightSet) {
		return (leftSet && rightSet) ? equals(leftLocator, rightLocator, left,
				right) : leftSet == rightSet;
	}

	@Override
	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, char left, char right, boolean leftSet,
			boolean rightSet) {
		return (leftSet && rightSet) ? equals(leftLocator, rightLocator, left,
				right) : leftSet == rightSet;
	}

	@Override
	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, double left, double right,
			boolean leftSet, boolean rightSet) {
		return (leftSet && rightSet) ? equals(leftLocator, rightLocator, left,
				right) : leftSet == rightSet;
	}

	@Override
	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, float left, float right,
			boolean leftSet, boolean rightSet) {
		return (leftSet && rightSet) ? equals(leftLocator, rightLocator, left,
				right) : leftSet == rightSet;
	}

	@Override
	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, int left, int right, boolean leftSet,
			boolean rightSet) {
		return (leftSet && rightSet) ? equals(leftLocator, rightLocator, left,
				right) : leftSet == rightSet;
	}

	@Override
	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, long left, long right, boolean leftSet,
			boolean rightSet) {
		return (leftSet && rightSet) ? equals(leftLocator, rightLocator, left,
				right) : leftSet == rightSet;
	}

	@Override
	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, short left, short right,
			boolean leftSet, boolean rightSet) {
		return (leftSet && rightSet) ? equals(leftLocator, rightLocator, left,
				right) : leftSet == rightSet;
	}

	@Override
	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, Object left, Object right,
			boolean leftSet, boolean rightSet) {
		return (leftSet && rightSet) ? equals(leftLocator, rightLocator, left,
				right) : leftSet == rightSet;
	}

	@Override
	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, boolean[] left, boolean[] right,
			boolean leftSet, boolean rightSet) {
		return (leftSet && rightSet) ? equals(leftLocator, rightLocator, left,
				right) : leftSet == rightSet;
	}

	@Override
	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, byte[] left, byte[] right,
			boolean leftSet, boolean rightSet) {
		return (leftSet && rightSet) ? equals(leftLocator, rightLocator, left,
				right) : leftSet == rightSet;
	}

	@Override
	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, char[] left, char[] right,
			boolean leftSet, boolean rightSet) {
		return (leftSet && rightSet) ? equals(leftLocator, rightLocator, left,
				right) : leftSet == rightSet;
	}

	@Override
	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, double[] left, double[] right,
			boolean leftSet, boolean rightSet) {
		return (leftSet && rightSet) ? equals(leftLocator, rightLocator, left,
				right) : leftSet == rightSet;
	}

	@Override
	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, float[] left, float[] right,
			boolean leftSet, boolean rightSet) {
		return (leftSet && rightSet) ? equals(leftLocator, rightLocator, left,
				right) : leftSet == rightSet;
	}

	@Override
	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, int[] left, int[] right,
			boolean leftSet, boolean rightSet) {
		return (leftSet && rightSet) ? equals(leftLocator, rightLocator, left,
				right) : leftSet == rightSet;
	}

	@Override
	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, long[] left, long[] right,
			boolean leftSet, boolean rightSet) {
		return (leftSet && rightSet) ? equals(leftLocator, rightLocator, left,
				right) : leftSet == rightSet;
	}

	@Override
	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, short[] left, short[] right,
			boolean leftSet, boolean rightSet) {
		return (leftSet && rightSet) ? equals(leftLocator, rightLocator, left,
				right) : leftSet == rightSet;
	}

	@Override
	public boolean equals(ObjectLocator leftLocator,
			ObjectLocator rightLocator, Object[] left, Object[] right,
			boolean leftSet, boolean rightSet) {
		return (leftSet && rightSet) ? equals(leftLocator, rightLocator, left,
				right) : leftSet == rightSet;
	}

	public static DefaultEqualsStrategy INSTANCE2 = new DefaultEqualsStrategy();
	public static EqualsStrategy INSTANCE = INSTANCE2;

	public static DefaultEqualsStrategy getInstance() {
		return INSTANCE2;
	}
}
