package org.jvnet.jaxb2_commons.lang;

import static org.jvnet.jaxb2_commons.locator.util.LocatorUtils.item;

import org.jvnet.jaxb2_commons.locator.ObjectLocator;

public class DefaultEqualsStrategy implements EqualsStrategy {

	protected boolean equalsInternal(ObjectLocator leftLocator,
			ObjectLocator rightLocator, Object lhs, Object rhs) {
		if (lhs == rhs) {
			return true;
		}
		if (lhs == null || rhs == null) {
			return false;
		}
		if (lhs instanceof Equals) {
			return ((Equals) lhs).equals(leftLocator, rightLocator, rhs, this);
		} else {
			return lhs.equals(rhs);
		}
	}

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
		} else {
			// Not an array of primitives
			return equals(leftLocator, rightLocator, (Object[]) lhs,
					(Object[]) rhs);
		}
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
			if (!equals(item(leftLocator, i, left[i]), item(rightLocator, i,
					right[i]), left[i], right[i])) {
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
			if (!equals(item(leftLocator, i, left[i]), item(rightLocator, i,
					right[i]), left[i], right[i])) {
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
			if (!equals(item(leftLocator, i, left[i]), item(rightLocator, i,
					right[i]), left[i], right[i])) {
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
			if (!equals(item(leftLocator, i, left[i]), item(rightLocator, i,
					right[i]), left[i], right[i])) {
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
			if (!equals(item(leftLocator, i, left[i]), item(rightLocator, i,
					right[i]), left[i], right[i])) {
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
			if (!equals(item(leftLocator, i, left[i]), item(rightLocator, i,
					right[i]), left[i], right[i])) {
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
			if (!equals(item(leftLocator, i, left[i]), item(rightLocator, i,
					right[i]), left[i], right[i])) {
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
			if (!equals(item(leftLocator, i, left[i]), item(rightLocator, i,
					right[i]), left[i], right[i])) {
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
			if (!equals(item(leftLocator, i, left[i]), item(rightLocator, i,
					right[i]), left[i], right[i])) {
				return false;
			}
		}
		return true;
	}

	public static EqualsStrategy INSTANCE = new DefaultEqualsStrategy();
}
