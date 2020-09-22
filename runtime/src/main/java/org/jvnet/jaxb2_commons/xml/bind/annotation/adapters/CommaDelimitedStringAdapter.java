package org.jvnet.jaxb2_commons.xml.bind.annotation.adapters;

import java.util.LinkedList;
import java.util.List;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import org.jvnet.jaxb2_commons.lang.StringUtils;

public class CommaDelimitedStringAdapter extends
		XmlAdapter<String, List<String>> {

	@Override
	public String marshal(List<String> value) throws Exception {
		if (value == null) {
			return null;
		} else {
			return StringUtils.join(value.iterator(), ", ");
		}
	}

	@Override
	public List<String> unmarshal(String text) throws Exception {

		if (text == null) {
			return null;
		} else

		{
			final List<String> value = new LinkedList<String>();
			final String[] items = StringUtils.split(text, ',');
			for (String item : items) {
				final String trimmedItem = item.trim();
				if (!StringUtils.isEmpty(trimmedItem)) {
					value.add(trimmedItem);
				}
			}
			return value;
		}
	}

}