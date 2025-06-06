package org.jvnet.hyperjaxb3.xml.bind.annotation.adapters;

import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import org.jvnet.jaxb2_commons.lang.StringUtils;

public class StringListAsString extends XmlAdapter<List<String>, String> {

	@Override
	public String unmarshal(List<String> list) throws Exception {

		if (list == null) {
			return null;
		} else {
			return StringUtils.join(list.iterator(), " ");
		}
	}

	@Override
	public List<String> marshal(String list) throws Exception {
		if (list == null) {
			return null;
		} else {
			return Arrays.asList(StringUtils.split(list, ' '));

		}
	}

}
