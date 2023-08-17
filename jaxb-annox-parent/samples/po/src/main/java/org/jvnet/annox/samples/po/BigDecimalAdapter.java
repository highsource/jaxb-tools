package org.jvnet.annox.samples.po;

import java.math.BigDecimal;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class BigDecimalAdapter extends XmlAdapter<String, BigDecimal> {

	@Override
	public BigDecimal unmarshal(String v) throws Exception {
		return v == null ? null : new BigDecimal(v);
	}

	@Override
	public String marshal(BigDecimal v) throws Exception {
		return v == null ? null : v.toString();
	}

}
