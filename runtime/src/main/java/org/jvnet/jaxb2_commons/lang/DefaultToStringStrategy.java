package org.jvnet.jaxb2_commons.lang;

import static org.jvnet.jaxb2_commons.locator.util.LocatorUtils.item;
import static org.jvnet.jaxb2_commons.locator.util.LocatorUtils.property;

import java.util.Collection;

import org.jvnet.jaxb2_commons.locator.ObjectLocator;

@SuppressWarnings("deprecation")
public class DefaultToStringStrategy implements ToStringStrategy2,
		ToStringStrategy {

	/**
	 * Whether to use the field names, the default is <code>true</code>.
	 */
	private boolean useFieldNames = true;

	/**
	 * Whether to mark default field values, the default is <code>true</code>.
	 */
	private boolean useDefaultFieldValueMarkers = true;

	/**
	 * Whether to use the class name, the default is <code>true</code>.
	 */
	private boolean useClassName = true;

	/**
	 * Whether to use short class names, the default is <code>false</code>.
	 */
	private boolean useShortClassName = false;

	/**
	 * Whether to use the identity hash code, the default is <code>true</code>.
	 */
	private boolean useIdentityHashCode = true;

	/**
	 * The content start <code>'['</code>.
	 */
	private String contentStart = "[";

	/**
	 * The content end <code>']'</code>.
	 */
	private String contentEnd = "]";

	/**
	 * The field name value separator <code>'='</code>.
	 */
	private String fieldNameValueSeparator = "=";

	/**
	 * Marker for the default field values (vs. explicitly set).
	 */
	private String defaultFieldValueMarker = "(default)";

	/**
	 * Whether the field separator should be added before any other fields.
	 */
	private boolean fieldSeparatorAtStart = false;

	/**
	 * Whether the field separator should be added after any other fields.
	 */
	private boolean fieldSeparatorAtEnd = false;

	/**
	 * The field separator <code>','</code>.
	 */
	private String fieldSeparator = ", ";

	/**
	 * The array start <code>'{'</code>.
	 */
	private String arrayStart = "{";

	/**
	 * The array separator <code>','</code>.
	 */
	private String arraySeparator = ",";

	/**
	 * The array end <code>'}'</code>.
	 */
	private String arrayEnd = "}";

	/**
	 * The value to use when fullDetail is <code>null</code>, the default value
	 * is <code>true</code>.
	 */
	private boolean fullDetail = true;

	/**
	 * The <code>null</code> text <code>'&lt;null&gt;'</code>.
	 */
	private String nullText = "<null>";

	/**
	 * The summary size text start <code>'<size'</code>.
	 */
	private String sizeStartText = "<size=";

	/**
	 * The summary size text start <code>'&gt;'</code>.
	 */
	private String sizeEndText = ">";

	public boolean isFullDetail() {
		return fullDetail;
	}

	public boolean isUseIdentityHashCode() {
		return useIdentityHashCode;
	}

	public boolean isUseDefaultFieldValueMarkers() {
		return useDefaultFieldValueMarkers;
	}

	protected String getShortClassName(Class cls) {
		return ClassUtils.getShortClassName(cls);
	}

	/**
	 * <p>
	 * Append to the <code>toString</code> the class name.
	 * </p>
	 * 
	 * @param buffer
	 *            the <code>StringBuilder</code> to populate
	 * @param object
	 *            the <code>Object</code> whose name to output
	 */
	protected void appendClassName(StringBuilder buffer, Object object) {
		if (useClassName && object != null) {
			if (useShortClassName) {
				buffer.append(getShortClassName(object.getClass()));
			} else {
				buffer.append(object.getClass().getName());
			}
		}
	}

	/**
	 * <p>
	 * Append the {@link System#identityHashCode(java.lang.Object)}.
	 * </p>
	 * 
	 * @param buffer
	 *            the <code>StringBuilder</code> to populate
	 * @param object
	 *            the <code>Object</code> whose id to output
	 */
	protected void appendIdentityHashCode(StringBuilder buffer, Object object) {
		if (this.isUseIdentityHashCode() && object != null) {
			buffer.append('@');
			buffer.append(Integer.toHexString(System.identityHashCode(object)));
		}
	}

	/**
	 * <p>
	 * Append to the <code>toString</code> the content start.
	 * </p>
	 * 
	 * @param buffer
	 *            the <code>StringBuilder</code> to populate
	 */
	protected void appendContentStart(StringBuilder buffer) {
		buffer.append(contentStart);
	}

	/**
	 * <p>
	 * Append to the <code>toString</code> the content end.
	 * </p>
	 * 
	 * @param buffer
	 *            the <code>StringBuilder</code> to populate
	 */
	protected void appendContentEnd(StringBuilder buffer) {
		buffer.append(contentEnd);
	}

	protected void appendArrayStart(StringBuilder buffer) {
		buffer.append(arrayStart);
	}

	protected void appendArrayEnd(StringBuilder buffer) {
		buffer.append(arrayEnd);
	}

	protected void appendArraySeparator(StringBuilder buffer) {
		buffer.append(arraySeparator);
	}

	/**
	 * <p>
	 * Append to the <code>toString</code> an indicator for <code>null</code>.
	 * </p>
	 * 
	 * <p>
	 * The default indicator is <code>'&lt;null&gt;'</code>.
	 * </p>
	 * 
	 * @param buffer
	 *            the <code>StringBuilder</code> to populate
	 */
	protected void appendNullText(StringBuilder buffer) {
		buffer.append(nullText);
	}

	/**
	 * <p>
	 * Append to the <code>toString</code> the field start.
	 * </p>
	 * 
	 * @param buffer
	 *            the <code>StringBuilder</code> to populate
	 * @param propertyName
	 *            the field name
	 */
	protected void appendFieldStart(ObjectLocator parentLocator, Object parent,
			String fieldName, StringBuilder buffer) {
		if (useFieldNames && fieldName != null) {
			buffer.append(fieldName);
			buffer.append(fieldNameValueSeparator);
		}
	}

	/**
	 * <p>
	 * Append to the <code>toString</code> the field start.
	 * </p>
	 * 
	 * @param buffer
	 *            the <code>StringBuilder</code> to populate
	 * @param propertyName
	 *            the field name
	 */
	protected void appendFieldStart(ObjectLocator parentLocator, Object parent,
			String fieldName, StringBuilder buffer, boolean valueSet) {
		if (useFieldNames && fieldName != null) {
			buffer.append(fieldName);
			buffer.append(fieldNameValueSeparator);
		}
	}

	/**
	 * <p>
	 * Append to the <code>toString<code> the field end.
	 * </p>
	 * 
	 * @param buffer
	 *            the <code>StringBuilder</code> to populate
	 * @param propertyName
	 *            the field name, typically not used as already appended
	 */
	protected void appendFieldEnd(ObjectLocator parentLocator, Object parent,
			String fieldName, StringBuilder buffer) {
		appendFieldSeparator(buffer);
	}

	/**
	 * <p>
	 * Append to the <code>toString<code> the field end.
	 * </p>
	 * 
	 * @param buffer
	 *            the <code>StringBuilder</code> to populate
	 * @param propertyName
	 *            the field name, typically not used as already appended
	 */
	protected void appendFieldEnd(ObjectLocator parentLocator, Object parent,
			String fieldName, StringBuilder buffer, boolean valueSet) {
		if (!valueSet) {
			if (isUseDefaultFieldValueMarkers()) {
				appendDefaultFieldValueMarker(buffer);
			}
		}
		appendFieldSeparator(buffer);
	}

	/**
	 * <p>
	 * Append to the <code>toString</code> the field separator.
	 * </p>
	 * 
	 * @param buffer
	 *            the <code>StringBuilder</code> to populate
	 */
	protected void appendFieldSeparator(StringBuilder buffer) {
		buffer.append(fieldSeparator);
	}

	protected void appendDefaultFieldValueMarker(StringBuilder buffer) {
		buffer.append(defaultFieldValueMarker);
	}

	/**
	 * <p>
	 * Append to the <code>toString</code> a size summary.
	 * </p>
	 * 
	 * <p>
	 * The size summary is used to summarize the contents of
	 * <code>Collections</code>, <code>Maps</code> and arrays.
	 * </p>
	 * 
	 * <p>
	 * The output consists of a prefix, the passed in size and a suffix.
	 * </p>
	 * 
	 * <p>
	 * The default format is <code>'&lt;size=n&gt;'<code>.
	 * </p>
	 * 
	 * @param buffer
	 *            the <code>StringBuilder</code> to populate
	 * @param propertyName
	 *            the field name, typically not used as already appended
	 * @param size
	 *            the size to append
	 */
	protected void appendSummarySize(ObjectLocator locator,
			StringBuilder buffer, int size) {
		buffer.append(sizeStartText);
		buffer.append(size);
		buffer.append(sizeEndText);
	}

	public StringBuilder appendStart(ObjectLocator parentLocator,
			Object object, StringBuilder buffer) {
		if (object != null) {
			appendClassName(buffer, object);
			appendIdentityHashCode(buffer, object);
			appendContentStart(buffer);
			if (fieldSeparatorAtStart) {
				appendFieldSeparator(buffer);
			}
		}
		return buffer;
	}

	public StringBuilder appendEnd(ObjectLocator parentLocator, Object parent,
			StringBuilder buffer) {
		if (this.fieldSeparatorAtEnd == false) {
			removeLastFieldSeparator(buffer);
		}
		appendContentEnd(buffer);
		return buffer;
	}

	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder buffer, Object value) {
		appendFieldStart(parentLocator, parent, fieldName, buffer);
		append(property(parentLocator, fieldName, value), buffer, value);
		appendFieldEnd(parentLocator, parent, fieldName, buffer);
		return buffer;
	}

	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder buffer, boolean value) {
		appendFieldStart(parentLocator, parent, fieldName, buffer);
		append(property(parentLocator, fieldName, value), buffer, value);
		appendFieldEnd(parentLocator, parent, fieldName, buffer);
		return buffer;
	}

	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder buffer, byte value) {
		appendFieldStart(parentLocator, parent, fieldName, buffer);
		append(property(parentLocator, fieldName, value), buffer, value);
		appendFieldEnd(parentLocator, parent, fieldName, buffer);
		return buffer;
	}

	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder buffer, char value) {
		appendFieldStart(parentLocator, parent, fieldName, buffer);
		append(property(parentLocator, fieldName, value), buffer, value);
		appendFieldEnd(parentLocator, parent, fieldName, buffer);
		return buffer;
	}

	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder buffer, double value) {
		appendFieldStart(parentLocator, parent, fieldName, buffer);
		append(property(parentLocator, fieldName, value), buffer, value);
		appendFieldEnd(parentLocator, parent, fieldName, buffer);
		return buffer;
	}

	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder buffer, float value) {
		appendFieldStart(parentLocator, parent, fieldName, buffer);
		append(property(parentLocator, fieldName, value), buffer, value);
		appendFieldEnd(parentLocator, parent, fieldName, buffer);
		return buffer;
	}

	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder buffer, long value) {
		appendFieldStart(parentLocator, parent, fieldName, buffer);
		append(property(parentLocator, fieldName, value), buffer, value);
		appendFieldEnd(parentLocator, parent, fieldName, buffer);
		return buffer;
	}

	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder buffer, int value) {
		appendFieldStart(parentLocator, parent, fieldName, buffer);
		append(property(parentLocator, fieldName, value), buffer, value);
		appendFieldEnd(parentLocator, parent, fieldName, buffer);
		return buffer;
	}

	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder buffer, short value) {
		appendFieldStart(parentLocator, parent, fieldName, buffer);
		append(property(parentLocator, fieldName, value), buffer, value);
		appendFieldEnd(parentLocator, parent, fieldName, buffer);
		return buffer;
	}

	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder buffer,
			Object[] value) {
		appendFieldStart(parentLocator, parent, fieldName, buffer);
		append(property(parentLocator, fieldName, value), buffer, value);
		appendFieldEnd(parentLocator, parent, fieldName, buffer);
		return buffer;
	}

	public StringBuilder append(ObjectLocator parentLocator, Object parent,
			String fieldName, StringBuilder buffer, Collection value) {
		appendFieldStart(parentLocator, parent, fieldName, buffer);
		append(property(parentLocator, fieldName, value), buffer, value);
		appendFieldEnd(parentLocator, parent, fieldName, buffer);
		return buffer;
	}

	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder buffer,
			boolean[] value) {
		appendFieldStart(parentLocator, parent, fieldName, buffer);
		append(property(parentLocator, fieldName, value), buffer, value);
		appendFieldEnd(parentLocator, parent, fieldName, buffer);
		return buffer;
	}

	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder buffer, byte[] value) {
		appendFieldStart(parentLocator, parent, fieldName, buffer);
		append(property(parentLocator, fieldName, value), buffer, value);
		appendFieldEnd(parentLocator, parent, fieldName, buffer);
		return buffer;
	}

	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder buffer, char[] value) {
		appendFieldStart(parentLocator, parent, fieldName, buffer);
		append(property(parentLocator, fieldName, value), buffer, value);
		appendFieldEnd(parentLocator, parent, fieldName, buffer);
		return buffer;
	}

	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder buffer,
			double[] value) {
		appendFieldStart(parentLocator, parent, fieldName, buffer);
		append(property(parentLocator, fieldName, value), buffer, value);
		appendFieldEnd(parentLocator, parent, fieldName, buffer);
		return buffer;
	}

	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder buffer, float[] value) {
		appendFieldStart(parentLocator, parent, fieldName, buffer);
		append(property(parentLocator, fieldName, value), buffer, value);
		appendFieldEnd(parentLocator, parent, fieldName, buffer);
		return buffer;
	}

	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder buffer, long[] value) {
		appendFieldStart(parentLocator, parent, fieldName, buffer);
		append(property(parentLocator, fieldName, value), buffer, value);
		appendFieldEnd(parentLocator, parent, fieldName, buffer);
		return buffer;
	}

	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder buffer, int[] value) {
		appendFieldStart(parentLocator, parent, fieldName, buffer);
		append(property(parentLocator, fieldName, value), buffer, value);
		appendFieldEnd(parentLocator, parent, fieldName, buffer);
		return buffer;
	}

	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder buffer, short[] value) {
		appendFieldStart(parentLocator, parent, fieldName, buffer);
		append(property(parentLocator, fieldName, value), buffer, value);
		appendFieldEnd(parentLocator, parent, fieldName, buffer);
		return buffer;
	}

	protected StringBuilder appendInternal(ObjectLocator locator,
			StringBuilder buffer, Object value) {
		if (value instanceof Collection) {
			append(locator, buffer, (Collection) value);
		} else if (value instanceof ToString2) {
			((ToString2) value).append(locator, buffer, this);
		} else if (value instanceof ToString) {
			((ToString) value).append(locator, buffer, this);
		} else {
			buffer.append(value.toString());
		}
		return buffer;
	}

	public StringBuilder append(ObjectLocator locator, StringBuilder buffer,
			Object value) {
		if (value == null) {
			appendNullText(buffer);
		} else {
			Class<?> theClass = value.getClass();
			if (!theClass.isArray()) {
				appendInternal(locator, buffer, value);
			}
			// 'Switch' on type of array, to dispatch to the correct handler
			// This handles multi dimensional arrays of the same depth
			else if (value instanceof long[]) {
				append(locator, buffer, (long[]) value);
			} else if (value instanceof int[]) {
				append(locator, buffer, (int[]) value);
			} else if (value instanceof short[]) {
				append(locator, buffer, (short[]) value);
			} else if (value instanceof char[]) {
				append(locator, buffer, (char[]) value);
			} else if (value instanceof byte[]) {
				append(locator, buffer, (byte[]) value);
			} else if (value instanceof double[]) {
				append(locator, buffer, (double[]) value);
			} else if (value instanceof float[]) {
				append(locator, buffer, (float[]) value);
			} else if (value instanceof boolean[]) {
				append(locator, buffer, (boolean[]) value);
			} else {
				// Not an array of primitives
				append(locator, buffer, (Object[]) value);
			}
		}
		return buffer;
	}

	public StringBuilder append(ObjectLocator locator, StringBuilder buffer,
			boolean value) {
		buffer.append(value);
		return buffer;
	}

	public StringBuilder append(ObjectLocator locator, StringBuilder buffer,
			byte value) {
		buffer.append(value);
		return buffer;
	}

	public StringBuilder append(ObjectLocator locator, StringBuilder buffer,
			char value) {
		buffer.append(value);
		return buffer;
	}

	public StringBuilder append(ObjectLocator locator, StringBuilder buffer,
			double value) {
		buffer.append(value);
		return buffer;
	}

	public StringBuilder append(ObjectLocator locator, StringBuilder buffer,
			float value) {
		buffer.append(value);
		return buffer;
	}

	public StringBuilder append(ObjectLocator locator, StringBuilder buffer,
			int value) {
		buffer.append(value);
		return buffer;
	}

	public StringBuilder append(ObjectLocator locator, StringBuilder buffer,
			long value) {
		buffer.append(value);
		return buffer;
	}

	public StringBuilder append(ObjectLocator locator, StringBuilder buffer,
			short value) {
		buffer.append(value);
		return buffer;
	}

	public StringBuilder append(ObjectLocator locator, StringBuilder buffer,
			Object[] array) {
		if (array == null) {
			appendNullText(buffer);

		} else if (isFullDetail()) {
			appendDetail(locator, buffer, array);

		} else {
			appendSummary(locator, buffer, array);
		}
		return buffer;
	}

	public StringBuilder append(ObjectLocator locator, StringBuilder buffer,
			Collection array) {
		if (array == null) {
			appendNullText(buffer);

		} else if (isFullDetail()) {
			appendDetail(locator, buffer, array);

		} else {
			appendSummary(locator, buffer, array);
		}
		return buffer;
	}

	public StringBuilder append(ObjectLocator locator, StringBuilder buffer,
			boolean[] array) {
		if (array == null) {
			appendNullText(buffer);

		} else if (isFullDetail()) {
			appendDetail(locator, buffer, array);

		} else {
			appendSummary(locator, buffer, array);
		}
		return buffer;
	}

	public StringBuilder append(ObjectLocator locator, StringBuilder buffer,
			byte[] array) {
		if (array == null) {
			appendNullText(buffer);

		} else if (isFullDetail()) {
			appendDetail(locator, buffer, array);

		} else {
			appendSummary(locator, buffer, array);
		}
		return buffer;
	}

	public StringBuilder append(ObjectLocator locator, StringBuilder buffer,
			char[] array) {
		if (array == null) {
			appendNullText(buffer);

		} else if (isFullDetail()) {
			appendDetail(locator, buffer, array);

		} else {
			appendSummary(locator, buffer, array);
		}
		return buffer;
	}

	public StringBuilder append(ObjectLocator locator, StringBuilder buffer,
			double[] array) {
		if (array == null) {
			appendNullText(buffer);

		} else if (isFullDetail()) {
			appendDetail(locator, buffer, array);

		} else {
			appendSummary(locator, buffer, array);
		}
		return buffer;
	}

	public StringBuilder append(ObjectLocator locator, StringBuilder buffer,
			float[] array) {
		if (array == null) {
			appendNullText(buffer);

		} else if (isFullDetail()) {
			appendDetail(locator, buffer, array);

		} else {
			appendSummary(locator, buffer, array);
		}
		return buffer;
	}

	public StringBuilder append(ObjectLocator locator, StringBuilder buffer,
			int[] array) {
		if (array == null) {
			appendNullText(buffer);

		} else if (isFullDetail()) {
			appendDetail(locator, buffer, array);

		} else {
			appendSummary(locator, buffer, array);
		}
		return buffer;
	}

	public StringBuilder append(ObjectLocator locator, StringBuilder buffer,
			long[] array) {
		if (array == null) {
			appendNullText(buffer);

		} else if (isFullDetail()) {
			appendDetail(locator, buffer, array);

		} else {
			appendSummary(locator, buffer, array);
		}
		return buffer;
	}

	public StringBuilder append(ObjectLocator locator, StringBuilder buffer,
			short[] array) {
		if (array == null) {
			appendNullText(buffer);

		} else if (isFullDetail()) {
			appendDetail(locator, buffer, array);

		} else {
			appendSummary(locator, buffer, array);
		}
		return buffer;
	}

	protected StringBuilder appendSummary(ObjectLocator locator,
			StringBuilder buffer, boolean[] array) {
		appendSummarySize(locator, buffer, array.length);
		return buffer;
	}

	protected StringBuilder appendSummary(ObjectLocator locator,
			StringBuilder buffer, byte[] array) {
		appendSummarySize(locator, buffer, array.length);
		return buffer;
	}

	protected StringBuilder appendSummary(ObjectLocator locator,
			StringBuilder buffer, char[] array) {
		appendSummarySize(locator, buffer, array.length);
		return buffer;
	}

	protected StringBuilder appendSummary(ObjectLocator locator,
			StringBuilder buffer, double[] array) {
		appendSummarySize(locator, buffer, array.length);
		return buffer;
	}

	protected StringBuilder appendSummary(ObjectLocator locator,
			StringBuilder buffer, float[] array) {
		appendSummarySize(locator, buffer, array.length);
		return buffer;
	}

	protected StringBuilder appendSummary(ObjectLocator locator,
			StringBuilder buffer, int[] array) {
		appendSummarySize(locator, buffer, array.length);
		return buffer;
	}

	protected StringBuilder appendSummary(ObjectLocator locator,
			StringBuilder buffer, long[] array) {
		appendSummarySize(locator, buffer, array.length);
		return buffer;
	}

	protected StringBuilder appendSummary(ObjectLocator locator,
			StringBuilder buffer, short[] array) {
		appendSummarySize(locator, buffer, array.length);
		return buffer;
	}

	protected StringBuilder appendSummary(ObjectLocator locator,
			StringBuilder buffer, Object[] array) {
		appendSummarySize(locator, buffer, array.length);
		return buffer;
	}

	protected StringBuilder appendSummary(ObjectLocator locator,
			StringBuilder buffer, Collection value) {
		appendSummarySize(locator, buffer, value.size());
		return buffer;
	}

	protected StringBuilder appendDetail(ObjectLocator locator,
			StringBuilder buffer, boolean[] array) {
		buffer.append(arrayStart);
		for (int i = 0; i < array.length; i++) {
			if (i > 0) {
				buffer.append(arraySeparator);
			}
			append(item(locator, i, array[i]), buffer, array[i]);
		}
		buffer.append(arrayEnd);
		return buffer;
	}

	protected StringBuilder appendDetail(ObjectLocator locator,
			StringBuilder buffer, byte[] array) {
		buffer.append(arrayStart);
		for (int i = 0; i < array.length; i++) {
			if (i > 0) {
				buffer.append(arraySeparator);
			}
			append(item(locator, i, array[i]), buffer, array[i]);
		}
		buffer.append(arrayEnd);
		return buffer;
	}

	protected StringBuilder appendDetail(ObjectLocator locator,
			StringBuilder buffer, char[] array) {
		buffer.append(arrayStart);
		for (int i = 0; i < array.length; i++) {
			if (i > 0) {
				buffer.append(arraySeparator);
			}
			append(item(locator, i, array[i]), buffer, array[i]);
		}
		buffer.append(arrayEnd);
		return buffer;
	}

	protected StringBuilder appendDetail(ObjectLocator locator,
			StringBuilder buffer, double[] array) {
		buffer.append(arrayStart);
		for (int i = 0; i < array.length; i++) {
			if (i > 0) {
				buffer.append(arraySeparator);
			}
			append(item(locator, i, array[i]), buffer, array[i]);
		}
		buffer.append(arrayEnd);
		return buffer;
	}

	protected StringBuilder appendDetail(ObjectLocator locator,
			StringBuilder buffer, float[] array) {
		buffer.append(arrayStart);
		for (int i = 0; i < array.length; i++) {
			if (i > 0) {
				buffer.append(arraySeparator);
			}
			append(item(locator, i, array[i]), buffer, array[i]);
		}
		buffer.append(arrayEnd);
		return buffer;
	}

	protected StringBuilder appendDetail(ObjectLocator locator,
			StringBuilder buffer, int[] array) {
		buffer.append(arrayStart);
		for (int i = 0; i < array.length; i++) {
			if (i > 0) {
				buffer.append(arraySeparator);
			}
			append(item(locator, i, array[i]), buffer, array[i]);
		}
		buffer.append(arrayEnd);
		return buffer;
	}

	protected StringBuilder appendDetail(ObjectLocator locator,
			StringBuilder buffer, long[] array) {
		buffer.append(arrayStart);
		for (int i = 0; i < array.length; i++) {
			if (i > 0) {
				buffer.append(arraySeparator);
			}
			append(item(locator, i, array[i]), buffer, array[i]);
		}
		buffer.append(arrayEnd);
		return buffer;
	}

	protected StringBuilder appendDetail(ObjectLocator locator,
			StringBuilder buffer, short[] array) {
		buffer.append(arrayStart);
		for (int i = 0; i < array.length; i++) {
			if (i > 0) {
				buffer.append(arraySeparator);
			}
			append(item(locator, i, array[i]), buffer, array[i]);
		}
		buffer.append(arrayEnd);
		return buffer;
	}

	protected StringBuilder appendDetail(ObjectLocator locator,
			StringBuilder buffer, Object[] array) {
		buffer.append(arrayStart);
		for (int i = 0; i < array.length; i++) {
			Object item = array[i];
			if (i > 0) {
				buffer.append(arraySeparator);
			}
			if (item == null) {
				appendNullText(buffer);

			} else {
				append(item(locator, i, array[i]), buffer, array[i]);
			}
		}
		buffer.append(arrayEnd);
		return buffer;
	}

	protected StringBuilder appendDetail(ObjectLocator locator,
			StringBuilder buffer, Collection array) {
		appendArrayStart(buffer);
		int i = 0;
		for (Object item : array) {
			if (i > 0) {
				appendArraySeparator(buffer);
			}
			append(item(locator, i, item), buffer, item);
			i = i + 1;
		}
		appendArrayEnd(buffer);
		return buffer;
	}

	/**
	 * <p>
	 * Remove the last field separator from the buffer.
	 * </p>
	 * 
	 * @param buffer
	 *            the <code>StringBuilder</code> to populate
	 * @since 2.0
	 */
	protected void removeLastFieldSeparator(StringBuilder buffer) {
		int len = buffer.length();
		int sepLen = fieldSeparator.length();
		if (len > 0 && sepLen > 0 && len >= sepLen) {
			boolean match = true;
			for (int i = 0; i < sepLen; i++) {
				if (buffer.charAt(len - 1 - i) != fieldSeparator.charAt(sepLen
						- 1 - i)) {
					match = false;
					break;
				}
			}
			if (match) {
				buffer.setLength(len - sepLen);
			}
		}
	}

	@Override
	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder stringBuilder,
			boolean value, boolean valueSet) {
		appendFieldStart(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		append(property(parentLocator, fieldName, value), stringBuilder, value);
		appendFieldEnd(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		return stringBuilder;
	}

	@Override
	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder stringBuilder,
			byte value, boolean valueSet) {
		appendFieldStart(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		append(property(parentLocator, fieldName, value), stringBuilder, value);
		appendFieldEnd(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		return stringBuilder;
	}

	@Override
	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder stringBuilder,
			char value, boolean valueSet) {
		appendFieldStart(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		append(property(parentLocator, fieldName, value), stringBuilder, value);
		appendFieldEnd(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		return stringBuilder;
	}

	@Override
	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder stringBuilder,
			double value, boolean valueSet) {
		appendFieldStart(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		append(property(parentLocator, fieldName, value), stringBuilder, value);
		appendFieldEnd(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		return stringBuilder;
	}

	@Override
	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder stringBuilder,
			float value, boolean valueSet) {
		appendFieldStart(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		append(property(parentLocator, fieldName, value), stringBuilder, value);
		appendFieldEnd(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		return stringBuilder;
	}

	@Override
	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder stringBuilder,
			int value, boolean valueSet) {
		appendFieldStart(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		append(property(parentLocator, fieldName, value), stringBuilder, value);
		appendFieldEnd(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		return stringBuilder;
	}

	@Override
	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder stringBuilder,
			long value, boolean valueSet) {
		appendFieldStart(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		append(property(parentLocator, fieldName, value), stringBuilder, value);
		appendFieldEnd(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		return stringBuilder;
	}

	@Override
	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder stringBuilder,
			short value, boolean valueSet) {
		appendFieldStart(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		append(property(parentLocator, fieldName, value), stringBuilder, value);
		appendFieldEnd(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		return stringBuilder;
	}

	@Override
	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder stringBuilder,
			Object value, boolean valueSet) {
		appendFieldStart(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		append(property(parentLocator, fieldName, value), stringBuilder, value);
		appendFieldEnd(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		return stringBuilder;
	}

	@Override
	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder stringBuilder,
			boolean[] value, boolean valueSet) {
		appendFieldStart(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		append(property(parentLocator, fieldName, value), stringBuilder, value);
		appendFieldEnd(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		return stringBuilder;
	}

	@Override
	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder stringBuilder,
			byte[] value, boolean valueSet) {
		appendFieldStart(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		append(property(parentLocator, fieldName, value), stringBuilder, value);
		appendFieldEnd(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		return stringBuilder;
	}

	@Override
	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder stringBuilder,
			char[] value, boolean valueSet) {
		appendFieldStart(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		append(property(parentLocator, fieldName, value), stringBuilder, value);
		appendFieldEnd(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		return stringBuilder;
	}

	@Override
	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder stringBuilder,
			double[] value, boolean valueSet) {
		appendFieldStart(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		append(property(parentLocator, fieldName, value), stringBuilder, value);
		appendFieldEnd(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		return stringBuilder;
	}

	@Override
	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder stringBuilder,
			float[] value, boolean valueSet) {
		appendFieldStart(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		append(property(parentLocator, fieldName, value), stringBuilder, value);
		appendFieldEnd(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		return stringBuilder;
	}

	@Override
	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder stringBuilder,
			int[] value, boolean valueSet) {
		appendFieldStart(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		append(property(parentLocator, fieldName, value), stringBuilder, value);
		appendFieldEnd(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		return stringBuilder;
	}

	@Override
	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder stringBuilder,
			long[] value, boolean valueSet) {
		appendFieldStart(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		append(property(parentLocator, fieldName, value), stringBuilder, value);
		appendFieldEnd(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		return stringBuilder;
	}

	@Override
	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder stringBuilder,
			short[] value, boolean valueSet) {
		appendFieldStart(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		append(property(parentLocator, fieldName, value), stringBuilder, value);
		appendFieldEnd(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		return stringBuilder;
	}

	@Override
	public StringBuilder appendField(ObjectLocator parentLocator,
			Object parent, String fieldName, StringBuilder stringBuilder,
			Object[] value, boolean valueSet) {
		appendFieldStart(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		append(property(parentLocator, fieldName, value), stringBuilder, value);
		appendFieldEnd(parentLocator, parent, fieldName, stringBuilder,
				valueSet);
		return stringBuilder;
	}

	public static final DefaultToStringStrategy INSTANCE2 = new DefaultToStringStrategy();
	public static final ToStringStrategy INSTANCE = INSTANCE2;
}