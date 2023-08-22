package org.jvnet.hyperjaxb3.xml.bind.annotation.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;

public class DurationAsString extends XmlAdapter<Duration, String> {

	@Override
	public String unmarshal(Duration duration) throws Exception {
		if (duration == null) {
			return null;
		} else {
			return duration.toString();
		}
	}

	@Override
	public Duration marshal(String duration) throws Exception {
		if (duration == null)
		{
			return null;
		}
		else
		{
			return DatatypeFactory.newInstance().newDuration(duration);
		}
	}

}
