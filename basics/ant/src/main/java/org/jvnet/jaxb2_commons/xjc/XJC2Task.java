package org.jvnet.jaxb2_commons.xjc;

import org.apache.tools.ant.BuildException;

public class XJC2Task extends com.sun.tools.xjc.XJC2Task {

	private boolean disableXmlSecurity = true;

	public void setDisableXmlSecurity(boolean disableXmlSecurity) {
		this.disableXmlSecurity = disableXmlSecurity;
	}

	private String accessExternalSchema = "all";

	public void setAccessExternalSchema(String accessExternalSchema) {
		this.accessExternalSchema = accessExternalSchema;
	}

	private String accessExternalDTD = "all";

	public void setAccessExternalDTD(String accessExternalDTD) {
		this.accessExternalDTD = accessExternalDTD;
	}

	@Override
	public void execute() throws BuildException {
		this.options.disableXmlSecurity = this.disableXmlSecurity;
		if (accessExternalSchema != null) {
			System.setProperty("javax.xml.accessExternalSchema",
					accessExternalSchema);
		}
		if (accessExternalDTD != null) {
			System.setProperty("javax.xml.accessExternalDTD", accessExternalDTD);
		}
		super.execute();
	}

}
