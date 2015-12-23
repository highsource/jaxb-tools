package org.jvnet.jaxb2_commons.lang;

import static org.jvnet.jaxb2_commons.locator.util.LocatorUtils.item;

import org.jvnet.jaxb2_commons.locator.ObjectLocator;

public class DefaultHashCodeStrategy implements HashCodeStrategy2,
		HashCodeStrategy {

	private int iConstant;

	public DefaultHashCodeStrategy() {
		this.iConstant = 37;
	}

	public DefaultHashCodeStrategy(int multiplierNonZeroOddNumber) {

		if (multiplierNonZeroOddNumber == 0) {
			throw new IllegalArgumentException(
					"HashCodeStrategy requires a non zero multiplier.");
		}
		if (multiplierNonZeroOddNumber % 2 == 0) {
			throw new IllegalArgumentException(
					"HashCodeStrategy requires an odd multiplier.");
		}
		this.iConstant = multiplierNonZeroOddNumber;
	}

	public int hashCode(ObjectLocator locator, int hashCode, Object object) {
		if (object == null) {
			return hashCode * iConstant;
		} else {
			if (object.getClass().isArray() == false) {
				return hashCodeInternal(locator, hashCode, object);
			} else {
				// 'Switch' on type of array, to dispatch to the correct handler
				// This handles multi dimensional arrays
				if (object instanceof long[]) {
					return hashCode(locator, hashCode, (long[]) object);
				} else if (object instanceof int[]) {
					return hashCode(locator, hashCode, (int[]) object);
				} else if (object instanceof short[]) {
					return hashCode(locator, hashCode, (short[]) object);
				} else if (object instanceof char[]) {
					return hashCode(locator, hashCode, (char[]) object);
				} else if (object instanceof byte[]) {
					return hashCode(locator, hashCode, (byte[]) object);
				} else if (object instanceof double[]) {
					return hashCode(locator, hashCode, (double[]) object);
				} else if (object instanceof float[]) {
					return hashCode(locator, hashCode, (float[]) object);
				} else if (object instanceof boolean[]) {
					return hashCode(locator, hashCode, (boolean[]) object);
				} else if (object instanceof HashCode2[]) {
					return hashCodeInternal(locator, hashCode,
							(HashCode2[]) object);
				} else if (object instanceof HashCode[]) {
					return hashCodeInternal(locator, hashCode,
							(HashCode[]) object);
				} else if (object instanceof Enum[]) {
					return hashCodeInternal(locator, hashCode,
							(Enum<?>[]) object);
				} else {
					// Not an array of primitives
					return hashCode(locator, hashCode, (Object[]) object);
				}
			}
		}
	}

	protected int hashCodeInternal(ObjectLocator locator, int hashCode,
			Object value) {
		if (value == null) {
			return hashCode * iConstant;
		} else if (value instanceof HashCode2) {
			return hashCodeInternal(locator, hashCode, (HashCode2) value);
		} else if (value instanceof HashCode) {
			return hashCodeInternal(locator, hashCode, (HashCode) value);
		} else if (value instanceof Enum) {
			return hashCodeInternal(locator, hashCode, (Enum<?>) value);
		} else {
			return hashCode * iConstant + value.hashCode();
		}
	}

	protected int hashCodeInternal(ObjectLocator locator, int hashCode,
			Enum<?> value) {
		if (value == null) {
			return hashCode * iConstant;
		} else if (value instanceof HashCode2) {
			return hashCodeInternal(locator, hashCode, (HashCode2) value);
		} else if (value instanceof HashCode) {
			return hashCodeInternal(locator, hashCode, (HashCode) value);
		} else {
			return hashCode * iConstant + value.hashCode();
		}
	}

	protected int hashCodeInternal(ObjectLocator locator, int hashCode,
			HashCode2 object) {
		if (object == null) {
			return hashCode * iConstant;
		} else {
			return hashCode * iConstant + object.hashCode(locator, this);
		}
	}

	protected int hashCodeInternal(ObjectLocator locator, int hashCode,
			HashCode object) {
		if (object == null) {
			return hashCode * iConstant;
		} else {
			return hashCode * iConstant + object.hashCode(locator, this);
		}
	}

	public int hashCode(ObjectLocator locator, int hashCode, Object[] value) {
		if (value == null) {
			return hashCode * iConstant;
		} else {
			int currentHashCode = hashCode * iConstant + 1;
			for (int i = 0; i < value.length; i++) {
				currentHashCode = hashCode(item(locator, i, value[i]),
						currentHashCode, value[i]);
			}
			return currentHashCode;
		}
	}

	protected int hashCodeInternal(ObjectLocator locator, int hashCode,
			Enum<?>[] value) {
		if (value == null) {
			return hashCode * iConstant;
		} else {
			int currentHashCode = hashCode * iConstant + 1;
			for (int i = 0; i < value.length; i++) {
				currentHashCode = hashCodeInternal(item(locator, i, value[i]),
						currentHashCode, value[i]);
			}
			return currentHashCode;
		}
	}

	protected int hashCodeInternal(ObjectLocator locator, int hashCode,
			HashCode2[] value) {
		if (value == null) {
			return hashCode * iConstant;
		} else {
			int currentHashCode = hashCode * iConstant + 1;
			for (int i = 0; i < value.length; i++) {
				currentHashCode = hashCodeInternal(item(locator, i, value[i]),
						currentHashCode, value[i]);
			}
			return currentHashCode;
		}
	}

	protected int hashCodeInternal(ObjectLocator locator, int hashCode,
			HashCode[] value) {
		if (value == null) {
			return hashCode * iConstant;
		} else {
			int currentHashCode = hashCode * iConstant + 1;
			for (int i = 0; i < value.length; i++) {
				currentHashCode = hashCodeInternal(item(locator, i, value[i]),
						currentHashCode, value[i]);
			}
			return currentHashCode;
		}
	}

	public int hashCode(ObjectLocator locator, int hashCode, boolean value) {
		return hashCode * iConstant + (value ? 0 : 1);

	}

	public int hashCode(ObjectLocator locator, int hashCode, byte value) {
		return hashCode * iConstant + value;

	}

	public int hashCode(ObjectLocator locator, int hashCode, char value) {
		return hashCode * iConstant + value;

	}

	public int hashCode(ObjectLocator locator, int hashCode, double value) {
		return hashCode(locator, hashCode, Double.doubleToLongBits(value));

	}

	public int hashCode(ObjectLocator locator, int hashCode, float value) {
		return hashCode(locator, hashCode, Float.floatToIntBits(value));
	}

	public int hashCode(ObjectLocator locator, int hashCode, int value) {
		return hashCode * iConstant + value;

	}

	public int hashCode(ObjectLocator locator, int hashCode, long value) {
		return hashCode * iConstant + ((int) (value ^ (value >> 32)));

	}

	public int hashCode(ObjectLocator locator, int hashCode, short value) {
		return hashCode * iConstant + value;

	}

	public int hashCode(ObjectLocator locator, int hashCode, boolean[] value) {
		if (value == null) {
			return hashCode * iConstant;
		} else {
			int currentHashCode = hashCode;
			for (int i = 0; i < value.length; i++) {
				currentHashCode = hashCode(item(locator, i, value[i]),
						currentHashCode, value[i]);
			}
			return currentHashCode;
		}
	}

	public int hashCode(ObjectLocator locator, int hashCode, byte[] value) {
		if (value == null) {
			return hashCode * iConstant;
		} else {
			int currentHashCode = hashCode;
			for (int i = 0; i < value.length; i++) {
				currentHashCode = hashCode(item(locator, i, value[i]),
						currentHashCode, value[i]);
			}
			return currentHashCode;
		}

	}

	public int hashCode(ObjectLocator locator, int hashCode, char[] value) {
		if (value == null) {
			return hashCode * iConstant;
		} else {
			int currentHashCode = hashCode;
			for (int i = 0; i < value.length; i++) {
				currentHashCode = hashCode(item(locator, i, value[i]),
						currentHashCode, value[i]);
			}
			return currentHashCode;
		}

	}

	public int hashCode(ObjectLocator locator, int hashCode, double[] value) {
		if (value == null) {
			return hashCode * iConstant;
		} else {
			int currentHashCode = hashCode;
			for (int i = 0; i < value.length; i++) {
				currentHashCode = hashCode(item(locator, i, value[i]),
						currentHashCode, value[i]);
			}
			return currentHashCode;
		}

	}

	public int hashCode(ObjectLocator locator, int hashCode, float[] value) {
		if (value == null) {
			return hashCode * iConstant;
		} else {
			int currentHashCode = hashCode;
			for (int i = 0; i < value.length; i++) {
				currentHashCode = hashCode(item(locator, i, value[i]),
						currentHashCode, value[i]);
			}
			return currentHashCode;
		}

	}

	public int hashCode(ObjectLocator locator, int hashCode, int[] value) {
		if (value == null) {
			return hashCode * iConstant;
		} else {
			int currentHashCode = hashCode;
			for (int i = 0; i < value.length; i++) {
				currentHashCode = hashCode(item(locator, i, value[i]),
						currentHashCode, value[i]);
			}
			return currentHashCode;
		}

	}

	public int hashCode(ObjectLocator locator, int hashCode, long[] value) {
		if (value == null) {
			return hashCode * iConstant;
		} else {
			int currentHashCode = hashCode;
			for (int i = 0; i < value.length; i++) {
				currentHashCode = hashCode(item(locator, i, value[i]),
						currentHashCode, value[i]);
			}
			return currentHashCode;
		}

	}

	public int hashCode(ObjectLocator locator, int hashCode, short[] value) {
		if (value == null) {
			return hashCode * iConstant;
		} else {
			int currentHashCode = hashCode;
			for (int i = 0; i < value.length; i++) {
				currentHashCode = hashCode(item(locator, i, value[i]),
						currentHashCode, value[i]);
			}
			return currentHashCode;
		}
	}

	@Override
	public int hashCode(ObjectLocator locator, int hashCode, boolean value,
			boolean valueSet) {
		return valueSet ? (hashCode * iConstant + 1) : hashCode(locator,
				hashCode * iConstant, value);
	}

	@Override
	public int hashCode(ObjectLocator locator, int hashCode, byte value,
			boolean valueSet) {
		return valueSet ? (hashCode * iConstant + 1) : hashCode(locator,
				hashCode * iConstant, value);
	}

	@Override
	public int hashCode(ObjectLocator locator, int hashCode, char value,
			boolean valueSet) {
		return valueSet ? (hashCode * iConstant + 1) : hashCode(locator,
				hashCode * iConstant, value);
	}

	@Override
	public int hashCode(ObjectLocator locator, int hashCode, double value,
			boolean valueSet) {
		return valueSet ? (hashCode * iConstant + 1) : hashCode(locator,
				hashCode * iConstant, value);
	}

	@Override
	public int hashCode(ObjectLocator locator, int hashCode, float value,
			boolean valueSet) {
		return valueSet ? (hashCode * iConstant + 1) : hashCode(locator,
				hashCode * iConstant, value);
	}

	@Override
	public int hashCode(ObjectLocator locator, int hashCode, int value,
			boolean valueSet) {
		return valueSet ? (hashCode * iConstant + 1) : hashCode(locator,
				hashCode * iConstant, value);
	}

	@Override
	public int hashCode(ObjectLocator locator, int hashCode, long value,
			boolean valueSet) {
		return valueSet ? (hashCode * iConstant + 1) : hashCode(locator,
				hashCode * iConstant, value);
	}

	@Override
	public int hashCode(ObjectLocator locator, int hashCode, short value,
			boolean valueSet) {
		return valueSet ? (hashCode * iConstant + 1) : hashCode(locator,
				hashCode * iConstant, value);
	}

	@Override
	public int hashCode(ObjectLocator locator, int hashCode, Object value,
			boolean valueSet) {
		return valueSet ? (hashCode * iConstant + 1) : hashCode(locator,
				hashCode * iConstant, value);
	}

	@Override
	public int hashCode(ObjectLocator locator, int hashCode, boolean[] value,
			boolean valueSet) {
		return valueSet ? (hashCode * iConstant + 1) : hashCode(locator,
				hashCode * iConstant, value);
	}

	@Override
	public int hashCode(ObjectLocator locator, int hashCode, byte[] value,
			boolean valueSet) {
		return valueSet ? (hashCode * iConstant + 1) : hashCode(locator,
				hashCode * iConstant, value);
	}

	@Override
	public int hashCode(ObjectLocator locator, int hashCode, char[] value,
			boolean valueSet) {
		return valueSet ? (hashCode * iConstant + 1) : hashCode(locator,
				hashCode * iConstant, value);
	}

	@Override
	public int hashCode(ObjectLocator locator, int hashCode, double[] value,
			boolean valueSet) {
		return valueSet ? (hashCode * iConstant + 1) : hashCode(locator,
				hashCode * iConstant, value);
	}

	@Override
	public int hashCode(ObjectLocator locator, int hashCode, float[] value,
			boolean valueSet) {
		return valueSet ? (hashCode * iConstant + 1) : hashCode(locator,
				hashCode * iConstant, value);
	}

	@Override
	public int hashCode(ObjectLocator locator, int hashCode, int[] value,
			boolean valueSet) {
		return valueSet ? (hashCode * iConstant + 1) : hashCode(locator,
				hashCode * iConstant, value);
	}

	@Override
	public int hashCode(ObjectLocator locator, int hashCode, long[] value,
			boolean valueSet) {
		return valueSet ? (hashCode * iConstant + 1) : hashCode(locator,
				hashCode * iConstant, value);
	}

	@Override
	public int hashCode(ObjectLocator locator, int hashCode, short[] value,
			boolean valueSet) {
		return valueSet ? (hashCode * iConstant + 1) : hashCode(locator,
				hashCode * iConstant, value);
	}

	@Override
	public int hashCode(ObjectLocator locator, int hashCode, Object[] value,
			boolean valueSet) {
		return valueSet ? (hashCode * iConstant + 1) : hashCode(locator,
				hashCode * iConstant, value);
	}

	public static final DefaultHashCodeStrategy INSTANCE = new DefaultHashCodeStrategy();

}
