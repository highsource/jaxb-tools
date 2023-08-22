package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import com.sun.java.xml.ns.persistence.orm.Column;
import com.sun.java.xml.ns.persistence.orm.Version;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.FieldOutline;

public class VersionMapping implements FieldOutlineMapping<Version> {

	public Version process(Mapping context, FieldOutline fieldOutline,
			Options options) {

		final Version version = context.getCustomizing().getVersion(
				fieldOutline);

		createVersion$Name(context, fieldOutline, version);
		createVersion$Column(context, fieldOutline, version);
		createVersion$Temporal(context, fieldOutline, version);
		return version;
	}

	public void createVersion$Name(Mapping context, FieldOutline fieldOutline,
			final Version version) {
		version.setName(context.getNaming().getPropertyName(context,
				fieldOutline));
	}

	public void createVersion$Column(Mapping context,
			FieldOutline fieldOutline, final Version version) {
		if (version.getColumn() == null) {
			version.setColumn(new Column());
		}

		version.setColumn(context.getAttributeMapping().createColumn(context, fieldOutline,
				version.getColumn()));
	}

	public void createVersion$Temporal(Mapping context,
			FieldOutline fieldOutline, Version version) {
		if (version.getTemporal() == null && context.getAttributeMapping().isTemporal(context, fieldOutline)) {
			version.setTemporal(context.getAttributeMapping().createTemporalType(context, fieldOutline));
		}
	}

}
