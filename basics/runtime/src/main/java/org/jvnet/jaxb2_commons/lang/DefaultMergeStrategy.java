package org.jvnet.jaxb2_commons.lang;

import org.jvnet.jaxb2_commons.locator.ObjectLocator;

@SuppressWarnings("deprecation")
public class DefaultMergeStrategy implements MergeStrategy2, MergeStrategy {

	@Override
	public Boolean shouldBeMergedAndSet(ObjectLocator leftLocator,
			ObjectLocator rightLocator, boolean leftSet, boolean rightSet) {
		return leftSet || rightSet;
	}

	protected Object mergeInternal(ObjectLocator leftLocator,
			ObjectLocator rightLocator, Object leftValue, Object rightValue) {
		if (leftValue == null) {
			return rightValue;
		} else if (rightValue == null) {
			return leftValue;
		} else {
			if (leftValue instanceof MergeFrom2) {
				final Object newInstance = ((MergeFrom2) leftValue)
						.createNewInstance();
				((MergeFrom2) newInstance).mergeFrom(leftLocator, rightLocator,
						leftValue, rightValue, this);
				return newInstance;
			} else if (leftValue instanceof MergeFrom) {
				final Object newInstance = ((MergeFrom) leftValue)
						.createNewInstance();
				((MergeFrom) newInstance).mergeFrom(leftLocator, rightLocator,
						leftValue, rightValue, this);
				return newInstance;
			} else if (rightValue instanceof MergeFrom2) {
				final Object newInstance = ((MergeFrom2) rightValue)
						.createNewInstance();
				((MergeFrom2) newInstance).mergeFrom(leftLocator, rightLocator,
						leftValue, rightValue, this);
				return newInstance;
			} else if (rightValue instanceof MergeFrom) {
				final Object newInstance = ((MergeFrom) rightValue)
						.createNewInstance();
				((MergeFrom) newInstance).mergeFrom(leftLocator, rightLocator,
						leftValue, rightValue, this);
				return newInstance;
			} else {
				return leftValue;
			}
		}

	}

	public Object merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			Object left, Object right) {

		if (left == null) {
			return right;
		}
		if (right == null) {
			return left;
		}
		Class<?> lhsClass = left.getClass();
		if (!lhsClass.isArray()) {
			return mergeInternal(leftLocator, rightLocator, left, right);
		} else if (left.getClass() != right.getClass()) {
			// Here when we compare different dimensions, for example: a
			// boolean[][] to a boolean[]
			return false;
		}
		// 'Switch' on type of array, to dispatch to the correct handler
		// This handles multi dimensional arrays of the same depth
		else if (left instanceof long[]) {
			return merge(leftLocator, rightLocator, (long[]) left,
					(long[]) right);
		} else if (left instanceof int[]) {
			return merge(leftLocator, rightLocator, (int[]) left, (int[]) right);
		} else if (left instanceof short[]) {
			return merge(leftLocator, rightLocator, (short[]) left,
					(short[]) right);
		} else if (left instanceof char[]) {
			return merge(leftLocator, rightLocator, (char[]) left,
					(char[]) right);
		} else if (left instanceof byte[]) {
			return merge(leftLocator, rightLocator, (byte[]) left,
					(byte[]) right);
		} else if (left instanceof double[]) {
			return merge(leftLocator, rightLocator, (double[]) left,
					(double[]) right);
		} else if (left instanceof float[]) {
			return merge(leftLocator, rightLocator, (float[]) left,
					(float[]) right);
		} else if (left instanceof boolean[]) {
			return merge(leftLocator, rightLocator, (boolean[]) left,
					(boolean[]) right);
		} else {
			// Not an array of primitives
			return merge(leftLocator, rightLocator, (Object[]) left,
					(Object[]) right);
		}
	}

	public long merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			long leftValue, long rightValue) {
		return leftValue != 0 ? leftValue : rightValue;
	}

	public int merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			int leftValue, int rightValue) {
		return leftValue != 0 ? leftValue : rightValue;
	}

	public short merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			short leftValue, short rightValue) {
		return leftValue != 0 ? leftValue : rightValue;
	}

	public char merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			char leftValue, char rightValue) {
		return leftValue != 0 ? leftValue : rightValue;
	}

	public byte merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			byte leftValue, byte rightValue) {
		return leftValue != 0 ? leftValue : rightValue;
	}

	public double merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			double leftValue, double rightValue) {
		return leftValue != 0 ? leftValue : rightValue;
	}

	public float merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			float leftValue, float rightValue) {
		return leftValue != 0 ? leftValue : rightValue;
	}

	public boolean merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			boolean leftValue, boolean rightValue) {
		return leftValue ? leftValue : rightValue;
	}

	public Object[] merge(ObjectLocator leftLocator,
			ObjectLocator rightLocator, Object[] leftValue, Object[] rightValue) {
		if (leftValue != null) {
			if (rightValue != null) {
				return leftValue.length > 0 ? leftValue : rightValue;

			} else {
				return leftValue;
			}
		} else {
			return rightValue;
		}
	}

	public long[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			long[] leftValue, long[] rightValue) {
		if (leftValue != null) {
			if (rightValue != null) {
				return leftValue.length > 0 ? leftValue : rightValue;

			} else {
				return leftValue;
			}
		} else {
			return rightValue;
		}
	}

	public int[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			int[] leftValue, int[] rightValue) {
		if (leftValue != null) {
			if (rightValue != null) {
				return leftValue.length > 0 ? leftValue : rightValue;

			} else {
				return leftValue;
			}
		} else {
			return rightValue;
		}
	}

	public short[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			short[] leftValue, short[] rightValue) {
		if (leftValue != null) {
			if (rightValue != null) {
				return leftValue.length > 0 ? leftValue : rightValue;

			} else {
				return leftValue;
			}
		} else {
			return rightValue;
		}
	}

	public char[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			char[] leftValue, char[] rightValue) {
		if (leftValue != null) {
			if (rightValue != null) {
				return leftValue.length > 0 ? leftValue : rightValue;

			} else {
				return leftValue;
			}
		} else {
			return rightValue;
		}
	}

	public byte[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			byte[] leftValue, byte[] rightValue) {
		if (leftValue != null) {
			if (rightValue != null) {
				return leftValue.length > 0 ? leftValue : rightValue;

			} else {
				return leftValue;
			}
		} else {
			return rightValue;
		}
	}

	public double[] merge(ObjectLocator leftLocator,
			ObjectLocator rightLocator, double[] leftValue, double[] rightValue) {
		if (leftValue != null) {
			if (rightValue != null) {
				return leftValue.length > 0 ? leftValue : rightValue;

			} else {
				return leftValue;
			}
		} else {
			return rightValue;
		}
	}

	public float[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			float[] leftValue, float[] rightValue) {
		if (leftValue != null) {
			if (rightValue != null) {
				return leftValue.length > 0 ? leftValue : rightValue;

			} else {
				return leftValue;
			}
		} else {
			return rightValue;
		}
	}

	public boolean[] merge(ObjectLocator leftLocator,
			ObjectLocator rightLocator, boolean[] leftValue,
			boolean[] rightValue) {
		if (leftValue != null) {
			if (rightValue != null) {
				return leftValue.length > 0 ? leftValue : rightValue;

			} else {
				return leftValue;
			}
		} else {
			return rightValue;
		}
	}

	@Override
	public boolean merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			boolean left, boolean right, boolean leftSet, boolean rightSet) {
		if (leftSet && !rightSet) {
			return left;
		} else if (!leftSet && rightSet) {
			return right;
		} else {
			return merge(leftLocator, rightLocator, left, right);
		}
	}

	@Override
	public boolean[] merge(ObjectLocator leftLocator,
			ObjectLocator rightLocator, boolean[] left, boolean[] right,
			boolean leftSet, boolean rightSet) {
		if (leftSet && !rightSet) {
			return left;
		} else if (!leftSet && rightSet) {
			return right;
		} else {
			return merge(leftLocator, rightLocator, left, right);
		}
	}

	@Override
	public byte merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			byte left, byte right, boolean leftSet, boolean rightSet) {
		if (leftSet && !rightSet) {
			return left;
		} else if (!leftSet && rightSet) {
			return right;
		} else {
			return merge(leftLocator, rightLocator, left, right);
		}
	}

	@Override
	public byte[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			byte[] left, byte[] right, boolean leftSet, boolean rightSet) {
		if (leftSet && !rightSet) {
			return left;
		} else if (!leftSet && rightSet) {
			return right;
		} else {
			return merge(leftLocator, rightLocator, left, right);
		}
	}

	@Override
	public char merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			char left, char right, boolean leftSet, boolean rightSet) {
		if (leftSet && !rightSet) {
			return left;
		} else if (!leftSet && rightSet) {
			return right;
		} else {
			return merge(leftLocator, rightLocator, left, right);
		}
	}

	@Override
	public char[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			char[] left, char[] right, boolean leftSet, boolean rightSet) {
		if (leftSet && !rightSet) {
			return left;
		} else if (!leftSet && rightSet) {
			return right;
		} else {
			return merge(leftLocator, rightLocator, left, right);
		}
	}

	@Override
	public double merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			double left, double right, boolean leftSet, boolean rightSet) {
		if (leftSet && !rightSet) {
			return left;
		} else if (!leftSet && rightSet) {
			return right;
		} else {
			return merge(leftLocator, rightLocator, left, right);
		}
	}

	@Override
	public double[] merge(ObjectLocator leftLocator,
			ObjectLocator rightLocator, double[] left, double[] right,
			boolean leftSet, boolean rightSet) {
		if (leftSet && !rightSet) {
			return left;
		} else if (!leftSet && rightSet) {
			return right;
		} else {
			return merge(leftLocator, rightLocator, left, right);
		}
	}

	@Override
	public float merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			float left, float right, boolean leftSet, boolean rightSet) {
		if (leftSet && !rightSet) {
			return left;
		} else if (!leftSet && rightSet) {
			return right;
		} else {
			return merge(leftLocator, rightLocator, left, right);
		}
	}

	@Override
	public float[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			float[] left, float[] right, boolean leftSet, boolean rightSet) {
		if (leftSet && !rightSet) {
			return left;
		} else if (!leftSet && rightSet) {
			return right;
		} else {
			return merge(leftLocator, rightLocator, left, right);
		}
	}

	@Override
	public int merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			int left, int right, boolean leftSet, boolean rightSet) {
		if (leftSet && !rightSet) {
			return left;
		} else if (!leftSet && rightSet) {
			return right;
		} else {
			return merge(leftLocator, rightLocator, left, right);
		}
	}

	@Override
	public int[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			int[] left, int[] right, boolean leftSet, boolean rightSet) {
		if (leftSet && !rightSet) {
			return left;
		} else if (!leftSet && rightSet) {
			return right;
		} else {
			return merge(leftLocator, rightLocator, left, right);
		}
	}

	@Override
	public long merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			long left, long right, boolean leftSet, boolean rightSet) {
		if (leftSet && !rightSet) {
			return left;
		} else if (!leftSet && rightSet) {
			return right;
		} else {
			return merge(leftLocator, rightLocator, left, right);
		}
	}

	@Override
	public long[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			long[] left, long[] right, boolean leftSet, boolean rightSet) {
		if (leftSet && !rightSet) {
			return left;
		} else if (!leftSet && rightSet) {
			return right;
		} else {
			return merge(leftLocator, rightLocator, left, right);
		}
	}

	@Override
	public Object[] merge(ObjectLocator leftLocator,
			ObjectLocator rightLocator, Object[] left, Object[] right,
			boolean leftSet, boolean rightSet) {
		if (leftSet && !rightSet) {
			return left;
		} else if (!leftSet && rightSet) {
			return right;
		} else {
			return merge(leftLocator, rightLocator, left, right);
		}
	}

	@Override
	public short merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			short left, short right, boolean leftSet, boolean rightSet) {
		if (leftSet && !rightSet) {
			return left;
		} else if (!leftSet && rightSet) {
			return right;
		} else {
			return merge(leftLocator, rightLocator, left, right);
		}
	}

	@Override
	public short[] merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			short[] left, short[] right, boolean leftSet, boolean rightSet) {
		if (leftSet && !rightSet) {
			return left;
		} else if (!leftSet && rightSet) {
			return right;
		} else {
			return merge(leftLocator, rightLocator, left, right);
		}
	}

	@Override
	public Object merge(ObjectLocator leftLocator, ObjectLocator rightLocator,
			Object left, Object right, boolean leftSet, boolean rightSet) {
		if (leftSet && !rightSet) {
			return left;
		} else if (!leftSet && rightSet) {
			return right;
		} else {
			return merge(leftLocator, rightLocator, left, right);
		}
	}
	
	public static final DefaultMergeStrategy INSTANCE2 = new DefaultMergeStrategy();
	public static final MergeStrategy INSTANCE = INSTANCE2;

	public static DefaultMergeStrategy getInstance() {
		return INSTANCE2;
	}
}